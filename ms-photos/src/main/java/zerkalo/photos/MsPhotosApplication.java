package zerkalo.photos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "zerkalo")
public class MsPhotosApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsPhotosApplication.class, args);
    }

}
