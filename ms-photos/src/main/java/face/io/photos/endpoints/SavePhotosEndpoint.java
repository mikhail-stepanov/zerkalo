package face.io.photos.endpoints;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.TextNode;
import face.io.common.endpoints.AbstractMicroservice;
import face.io.msclient.exceptions.MicroServiceException;
import face.io.msclient.exceptions.MsInternalErrorException;
import face.io.msclient.photos.interfaces.IPhotosService;
import face.io.msclient.photos.models.PhotosRequest;
import face.io.msclient.photos.models.PhotosResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

@RestController
@Api("Service for download profile photos")
public class SavePhotosEndpoint extends AbstractMicroservice implements IPhotosService {

    @Value("${tempfiles.directory:/var/instaPhotos/}")
    private String temDir;

    private String graphQL = "https://www.instagram.com/graphql/query/?query_hash=58b6785bea111c67129decbe6a448951&variables={variables}";

    private RestTemplate restTemplate;
    private ObjectMapper mapper;

    @PostConstruct
    private void initialize() {
        restTemplate = new RestTemplate();
        mapper = new ObjectMapper();
    }

    @ApiOperation("Method to save profile photos by Instagram ID")
    @RequestMapping(value = PHOTOS_SAVE_ID, method = RequestMethod.POST)
    public PhotosResponse loadById(@RequestBody PhotosRequest request) throws MicroServiceException {
        try {

            boolean nextPage = true;
            String endCursor = "";
            int count = 0;

            while (nextPage) {
                String variables = "{\"id\":\"" + request.getId() + "\",\"first\":50,\"after\":\"" + endCursor + "\"}";
                ResponseEntity<String> response = restTemplate.getForEntity(graphQL, String.class, variables);
                JsonNode responseNode = mapper.readTree(response.getBody());
                JsonNode mediaNode = responseNode.get("data").get("user").get("edge_owner_to_timeline_media");

                nextPage = mediaNode.get("page_info").get("has_next_page").booleanValue();
                if (nextPage)
                    endCursor = mediaNode.get("page_info").get("end_cursor").textValue();

                JsonNode jsonEdges = mediaNode.get("edges");
                for (JsonNode edge : jsonEdges) {
                    if (!edge.get("node").get("is_video").booleanValue()) {
                        TextNode src = (TextNode) edge.get("node").get("display_url");
                        File tmpFile = new File(temDir + request.getId() + "/" + count++ + ".jpg");
                        tmpFile.getParentFile().mkdirs();
                        tmpFile.createNewFile();

                        URL url = new URL(src.asText());
                        ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
                        FileOutputStream fileOutputStream = new FileOutputStream(tmpFile);
                        fileOutputStream.getChannel()
                                .transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
                    } else {
                        TextNode src = (TextNode) edge.get("node").get("video_url");
                        File tmpFile = new File(temDir + request.getId() + "/" + count++ + ".mp4");
                        tmpFile.getParentFile().mkdirs();
                        tmpFile.createNewFile();

                        URL url = new URL(src.asText());
                        ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
                        FileOutputStream fileOutputStream = new FileOutputStream(tmpFile);
                        fileOutputStream.getChannel()
                                .transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
                    }
                }
            }

            return PhotosResponse.builder()
                    .success(1)
                    .build();
        } catch (Exception e) {
            throw new MsInternalErrorException("Photos save exception." + e.getLocalizedMessage());
        }
    }

    @ApiOperation("Method to save profile photos by Instagram nickname")
    @RequestMapping(value = PHOTOS_SAVE_NAME, method = RequestMethod.POST)
    public PhotosResponse loadByName(@RequestBody PhotosRequest request) throws MicroServiceException {
        try {

            ResponseEntity<String> responseProfile = restTemplate.getForEntity(INSTAGRAM + request.getUsername(), String.class);

            Document html = Jsoup.parse(responseProfile.toString());
            DataNode targetData = html.body().child(118).dataNodes().get(0);
            String json = targetData.toString().substring(21, targetData.toString().length() - 1);

            JsonNode jsonNode = mapper.readTree(json);
            String accountId = jsonNode.get("entry_data").get("ProfilePage").get(0).get("graphql").get("user").get("id").textValue();

            boolean nextPage = true;
            String endCursor = "";
            int count = 0;

            while (nextPage) {
                String variables = "{\"id\":\"" + accountId + "\",\"first\":50,\"after\":\"" + endCursor + "\"}";
                ResponseEntity<String> response = restTemplate.getForEntity(graphQL, String.class, variables);

                JsonNode responseNode = mapper.readTree(response.getBody());
                JsonNode mediaNode = responseNode.get("data").get("user").get("edge_owner_to_timeline_media");

                nextPage = mediaNode.get("page_info").get("has_next_page").booleanValue();
                if (nextPage)
                    endCursor = mediaNode.get("page_info").get("end_cursor").textValue();

                JsonNode jsonEdges = mediaNode.get("edges");
                for (JsonNode edge : jsonEdges) {
                    if (!edge.get("node").get("is_video").booleanValue()) {
                        TextNode src = (TextNode) edge.get("node").get("display_url");
                        File tmpFile = new File(temDir + request.getId() + "/" + count++ + ".jpg");
                        tmpFile.getParentFile().mkdirs();
                        tmpFile.createNewFile();

                        URL url = new URL(src.asText());
                        ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
                        FileOutputStream fileOutputStream = new FileOutputStream(tmpFile);
                        fileOutputStream.getChannel()
                                .transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
                    } else {
                        TextNode src = (TextNode) edge.get("node").get("video_url");
                        File tmpFile = new File(temDir + request.getId() + "/" + count++ + ".mp4");
                        tmpFile.getParentFile().mkdirs();
                        tmpFile.createNewFile();

                        URL url = new URL(src.asText());
                        ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
                        FileOutputStream fileOutputStream = new FileOutputStream(tmpFile);
                        fileOutputStream.getChannel()
                                .transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
                    }
                }
            }

            return PhotosResponse.builder()
                    .success(1)
                    .build();
        } catch (Exception e) {
            throw new MsInternalErrorException("Photos save exception." + e.getLocalizedMessage());
        }
    }
}