package face.io.profiles;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "face.io")
public class MsProfilesApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsProfilesApplication.class, args);
    }

}
