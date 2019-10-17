package face.io.profiles.endpoints;

import face.io.profiles.services.ProfileBatchSaver;
import face.io.profiles.services.ProfilesCollector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfilesAdminEndpoint {

    @Autowired
    private ProfileBatchSaver profileBatchSaver;

    @Autowired
    private ProfilesCollector profilesCollector;

    @RequestMapping(value = "/saver/start", method = RequestMethod.POST)
    public void startSaver() {
        profileBatchSaver.start();
    }

    @RequestMapping(value = "/saver/stop", method = RequestMethod.POST)
    public void stopSaver() {
        profileBatchSaver.stop();
    }

    @RequestMapping(value = "/collector/start", method = RequestMethod.POST)
    public void startCollector() {
        profilesCollector.start();
    }

    @RequestMapping(value = "/collector/stop", method = RequestMethod.POST)
    public void stopCollector() {
        profilesCollector.stop();
    }
}
