package bg.softuni.heathy_desserts_recipes.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

@Component
public class Run implements CommandLineRunner {

    private final RestClient restClient;
    private final RestTemplate restTemplate;

    @Autowired
    public Run(RestClient restClient, RestTemplateBuilder restTemplateBuilder) {
        this.restClient = restClient;
        this.restTemplate = restTemplateBuilder
                .build();
    }

    @Override
    public void run(String... args) {


        restClient.delete()
                .uri("http://localhost:8081/messages/delete/{id}", 1)
                .retrieve()
                .toBodilessEntity();

    }

}
