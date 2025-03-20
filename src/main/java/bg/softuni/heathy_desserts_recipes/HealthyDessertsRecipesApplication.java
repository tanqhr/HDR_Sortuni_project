package bg.softuni.heathy_desserts_recipes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HealthyDessertsRecipesApplication {

    public static void main(String[] args) {
        SpringApplication.run(HealthyDessertsRecipesApplication.class, args);
    }

}
