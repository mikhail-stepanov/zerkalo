package face.io.msclient.photos.interfaces;

import face.io.msclient.exceptions.MicroServiceException;
import face.io.msclient.photos.models.PhotosRequest;
import face.io.msclient.photos.models.PhotosResponse;

public interface IPhotosService {

    String PHOTOS_SAVE = "/photos/save";
    String INSTAGRAM = "https://www.instagram.com/";

    PhotosResponse info(PhotosRequest request) throws MicroServiceException;

}
