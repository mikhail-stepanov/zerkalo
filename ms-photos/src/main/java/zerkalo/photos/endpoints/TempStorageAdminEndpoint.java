package zerkalo.photos.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import zerkalo.msclient.exceptions.MicroServiceException;
import zerkalo.photos.services.TempStorageService;

@RestController
public class TempStorageAdminEndpoint {

    @Autowired
    TempStorageService tempStorageService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void startSaver() throws MicroServiceException {
        tempStorageService.savePhotosForAll();
    }
}
