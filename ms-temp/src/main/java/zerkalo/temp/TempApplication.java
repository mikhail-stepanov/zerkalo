package zerkalo.temp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "zerkalo")
public class TempApplication {

    public static void main(String[] args) {
        SpringApplication.run(TempApplication.class, args);
    }

}
