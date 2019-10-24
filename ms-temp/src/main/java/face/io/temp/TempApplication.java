package face.io.temp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "face.io")
public class TempApplication {

    public static void main(String[] args) {
        SpringApplication.run(TempApplication.class, args);
    }

}
