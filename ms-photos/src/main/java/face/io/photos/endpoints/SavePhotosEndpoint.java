package face.io.photos.endpoints;


import face.io.common.endpoints.AbstractMicroservice;
import face.io.msclient.exceptions.MicroServiceException;
import face.io.msclient.photos.interfaces.IPhotosService;
import face.io.msclient.photos.models.PhotosRequest;
import face.io.msclient.photos.models.PhotosResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api("Service for download profile photos")
public class SavePhotosEndpoint extends AbstractMicroservice implements IPhotosService {

    @Override
    @ApiOperation("Method to save profile photos by Instagram nickname")
    @RequestMapping(value = PHOTOS_SAVE, method = RequestMethod.POST)
    public PhotosResponse info(PhotosRequest request) throws MicroServiceException {
        return null;
    }
}