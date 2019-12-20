package zerkalo.msclient.photos.interfaces;

import zerkalo.msclient.exceptions.MicroServiceException;
import zerkalo.msclient.photos.models.PhotosRequest;
import zerkalo.msclient.photos.models.PhotosResponse;

public interface IPhotosService {

    String PHOTOS_SAVE_ID = "/photos/save/id";
    String PHOTOS_SAVE_NAME = "/photos/save/name";
    String INSTAGRAM = "https://www.instagram.com/";

    PhotosResponse loadById(PhotosRequest request) throws MicroServiceException;

    PhotosResponse loadByName(PhotosRequest request) throws MicroServiceException;


}
