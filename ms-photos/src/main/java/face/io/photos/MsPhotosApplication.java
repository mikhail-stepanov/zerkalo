package face.io.photos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "face.io")
public class MsPhotosApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsPhotosApplication.class, args);
    }

}
