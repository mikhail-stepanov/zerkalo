package face.io.photos.endpoints;


import com.google.gson.Gson;
import face.io.common.endpoints.AbstractMicroservice;
import face.io.msclient.exceptions.MicroServiceException;
import face.io.msclient.photos.interfaces.IPhotosService;
import face.io.msclient.photos.models.PhotosRequest;
import face.io.msclient.photos.models.PhotosResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Api("Service for download profile photos")
public class SavePhotosEndpoint extends AbstractMicroservice implements IPhotosService {


    @ApiOperation("Method to save profile photos by Instagram nickname")
    @RequestMapping(value = PHOTOS_SAVE, method = RequestMethod.POST)
    public PhotosResponse info(@RequestBody PhotosRequest request) throws MicroServiceException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(INSTAGRAM + request.getUsername(), String.class);

        Document html = Jsoup.parse(response.toString());
        Gson gson = new Gson();

        Element targetData = html.body().child(118);

        System.out.println(response.toString());
        return null;
    }
}