package bg.softuni.heathy_desserts_recipes.service;


import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.io.File;

@Service
public class WeatherService {

    @Value("${whether.api-key}")
    private String API_KEY;
    private static final String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&appid=%s&units=metric";

    public JsonNode getWeather() {
        double longitude = 23.32250;
        double latitude = 42.69917;

        String url = String.format(WEATHER_URL, latitude, longitude, API_KEY);
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readTree(response);
        } catch (Exception e) {
            return null;
        }
    }
}
