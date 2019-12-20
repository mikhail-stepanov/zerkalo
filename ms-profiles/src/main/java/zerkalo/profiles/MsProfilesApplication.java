package zerkalo.profiles;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "zerkalo")
public class MsProfilesApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsProfilesApplication.class, args);
    }

}
