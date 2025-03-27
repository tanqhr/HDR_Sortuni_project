package bg.softuni.heathy_desserts_recipes.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ConfigurationProperties(prefix = "spring.mvc.hiddenmethod.filter.message.api")
@Component
@Getter
@Setter
public class MessageApiConfig {
    private String baseUrl;
    @Bean
    public RestClient restClient(MessageApiConfig messageApiConfig){
        return RestClient
                .builder()
                .baseUrl(messageApiConfig.getBaseUrl())
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

}
