package face.io.profiles.endpoints;

import face.io.profiles.services.ProfileBatchSaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfilesAdminEndpoint {

    @Autowired
    private ProfileBatchSaver profileBatchSaver;

    @RequestMapping(value = "/start", method = RequestMethod.POST)
    public void start() {
        profileBatchSaver.start();
    }

    @RequestMapping(value = "/stop", method = RequestMethod.POST)
    public void stop() {
        profileBatchSaver.stop();
    }
}
