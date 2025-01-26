package bg.softuni.heathy_desserts_recipes.web.controller;

import bg.softuni.heathy_desserts_recipes.service.WeatherService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class HomeController {
    private final WeatherService weatherService;

    public HomeController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/")
    public String getHome(Model model) {
        JsonNode weatherData = weatherService.getWeather();
        model.addAttribute("temperature", weatherData.path("main").path("temp").asText());
        model.addAttribute("wind", weatherData.path("wind").path("speed").asText());

        String rain = "без валеж";
        if (weatherData.has("rain") && weatherData.path("rain").has("1h")) {
            rain = weatherData.path("rain").path("1h").asText();
        }
        model.addAttribute("rain", rain);

        return "home";
    }
    @GetMapping("/contact")
    public String getContact(){
        return "contact";

}
}