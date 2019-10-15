package face.io.profiles.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import face.io.common.services.MessageService;
import face.io.msclient.message.interfaces.IMessageService;
import face.io.msclient.profiles.models.ProfileCollectRequest;
import face.io.msclient.profiles.models.ProfileSaveRequest;
import face.io.profiles.models.ProfileShortModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.Closeable;
import java.io.IOException;

@Service
public class ProfilesCollector {

    @Value("${profiles.saver.threadCount:#{10}}")
    private Integer threadCount;

    private String graphQL = "https://www.instagram.com/graphql/query/?query_hash=58b6785bea111c67129decbe6a448951&variables={variables}";
    private String instagram = "https://www.instagram.com/";
    private RestTemplate restTemplate;
    private ObjectMapper mapper;

    @PostConstruct
    private void initialize() {
        restTemplate = new RestTemplate();
        mapper = new ObjectMapper();
    }

    @Autowired
    private IMessageService messageService;

    private Closeable queueSubscriber;

    private static final Logger logger = LoggerFactory.getLogger(ProfilesCollector.class);

    public void start() {
        queueSubscriber = messageService.subscribe(MessageService.ID_TO_COLLECT_QUEUE, 1, ProfileCollectRequest.class, this::handleMessage);
    }

    public void stop() {
        try {
            if (queueSubscriber != null)
                queueSubscriber.close();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    @PreDestroy
    private void destroy() {
        try {
            if (queueSubscriber != null)
                queueSubscriber.close();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    private boolean handleMessage(ProfileCollectRequest message) {
        try {
            String variables = "{\"id\":\"" + message.getId() + "\",\"first\":50,\"after\":\"\"}";
            ResponseEntity<String> response = restTemplate.getForEntity(graphQL, String.class, variables);
            JsonNode responseNode = mapper.readTree(response.getBody());
            JsonNode mediaNode = responseNode.get("data").get("user").get("edge_owner_to_timeline_media");
            JsonNode jsonEdges = mediaNode.get("edges");

            publishProfiles(jsonEdges);

            return true;
        } catch (Exception ex) {
            logger.error("Exception while processing message from queue: " + ex.getLocalizedMessage());
            return false;

        }
    }

    private void publishProfiles(JsonNode jsonEdges) {
        for (JsonNode edge : jsonEdges) {
            JsonNode commentEdges = edge.get("edge_media_to_comment").get("edges");
            commentEdges.forEach(commentEdge -> {
                ProfileShortModel profile = ProfileShortModel.builder()
                        .id(commentEdge.get("node").get("owner").get("id").asText())
                        .username(commentEdge.get("node").get("owner").get("username").asText())
                        .build();

                ResponseEntity<String> userInfo = restTemplate.getForEntity(instagram + commentEdge.get("node").get("owner").get("username").asText() + "/?__a=1", String.class);
                try {
                    JsonNode userNode = mapper.readTree(userInfo.getBody()).get("graphql").get("user");

                    messageService.publish(MessageService.SAVE_PROFILE_QUEUE, ProfileSaveRequest.builder()
                            .id(userNode.get("id").asText())
                            .username(userNode.get("username").asText())
                            .isPrivate(userNode.get("is_private").asBoolean())
                            .isBusiness(userNode.get("is_business_account").asBoolean())
                            .businessCategoryName(userNode.get("business_category_name").asText())
                            .isVerified(userNode.get("is_verified").asBoolean())
                            .profilePicUrlHd(userNode.get("profile_pic_url_hd").asText())
                            .fullName(userNode.get("full_name").asText())
                            .connectedFbPage(userNode.get("connected_fb_page").asText())
                            .externalUrl(userNode.get("external_url").asText())
                            .externalUrlLinkshimmed(userNode.get("external_url_linkshimmed").asText())
                            .isJoinedRecently(userNode.get("is_joined_recently").asBoolean())
                            .edgeFollowBy(userNode.get("edge_followed_by").get("count").asInt())
                            .edgeFollower(userNode.get("edge_follow").get("count").asInt())
                            .edgeOwnerToTimelineMedia(userNode.get("edge_owner_to_timeline_media").get("count").asInt())
                            .build());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        }
    }

}