package dentist.office;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DentistOfficeApplication {

    public static void main(String[] args) {
        SpringApplication.run(DentistOfficeApplication.class, args);
    }

}
