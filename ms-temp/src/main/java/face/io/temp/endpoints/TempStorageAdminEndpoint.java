package face.io.temp.endpoints;

import face.io.msclient.exceptions.MicroServiceException;
import face.io.temp.services.TempStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TempStorageAdminEndpoint {

    @Autowired
    TempStorageService tempStorageService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void startSaver() throws MicroServiceException {
        tempStorageService.savePhotosForAll();
    }
}
