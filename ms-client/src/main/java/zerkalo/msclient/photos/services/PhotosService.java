package zerkalo.msclient.photos.services;

import org.springframework.web.client.RestTemplate;
import zerkalo.msclient.common.services.BaseMicroservice;
import zerkalo.msclient.exceptions.MicroServiceException;
import zerkalo.msclient.photos.interfaces.IPhotosService;
import zerkalo.msclient.photos.models.PhotosRequest;
import zerkalo.msclient.photos.models.PhotosResponse;

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
