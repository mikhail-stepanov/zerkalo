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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

@RestController
@Api("Service for download profile photos")
public class SavePhotosEndpoint extends AbstractMicroservice implements IPhotosService {

    //    @Value("${tempfiles.directory:/var/tmp}")
    private String temDir = "C:/TEST/";

    @ApiOperation("Method to save profile photos by Instagram nickname")
    @RequestMapping(value = PHOTOS_SAVE, method = RequestMethod.POST)
    public PhotosResponse info(@RequestBody PhotosRequest request) throws MicroServiceException {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.getForEntity(INSTAGRAM + request.getUsername(), String.class);

            Document html = Jsoup.parse(response.toString());
            DataNode targetData = html.body().child(118).dataNodes().get(0);
            String json = targetData.toString().substring(21, targetData.toString().length() - 1);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(json);
            JsonNode jsonEdges = jsonNode.get("entry_data").get("ProfilePage").get(0).get("graphql").get("user").get("edge_owner_to_timeline_media").get("edges");
            int count = 0;

            for (JsonNode edge : jsonEdges) {
                if (!edge.get("node").get("is_video").asBoolean()) {
                    TextNode src = (TextNode) edge.get("node").get("thumbnail_src");
                    File tmpFile = new File(temDir + request.getUsername() + "/" + count++ + ".jpg");
                    tmpFile.getParentFile().mkdirs();
                    tmpFile.createNewFile();

                    URL url = new URL(src.asText());
                    ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
                    FileOutputStream fileOutputStream = new FileOutputStream(tmpFile);
                    fileOutputStream.getChannel()
                            .transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
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