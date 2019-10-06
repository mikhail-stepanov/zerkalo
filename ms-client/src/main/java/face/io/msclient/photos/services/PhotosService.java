package face.io.msclient.photos.services;

import face.io.msclient.common.services.BaseMicroservice;
import face.io.msclient.exceptions.MicroServiceException;
import face.io.msclient.photos.interfaces.IPhotosService;
import face.io.msclient.photos.models.PhotosRequest;
import face.io.msclient.photos.models.PhotosResponse;
import org.springframework.web.client.RestTemplate;

public class PhotosService extends BaseMicroservice implements IPhotosService {

    public PhotosService(RestTemplate restTemplate) {
        super("ms-photos", restTemplate);
    }

    @Override
    public PhotosResponse loadById(PhotosRequest request) throws MicroServiceException {
        return retry(() -> restTemplate.postForEntity(buildUrl(PHOTOS_SAVE_ID), request, PhotosResponse.class).getBody());
    }

    @Override
    public PhotosResponse loadByName(PhotosRequest request) throws MicroServiceException {
        return retry(() -> restTemplate.postForEntity(buildUrl(PHOTOS_SAVE_NAME), request, PhotosResponse.class).getBody());
    }
}
