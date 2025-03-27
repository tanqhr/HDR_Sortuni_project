package bg.softuni.heathy_desserts_recipes.web.controller;

import bg.softuni.heathy_desserts_recipes.model.entity.message.dto.MessageDto;
import bg.softuni.heathy_desserts_recipes.service.MessagesService;
import bg.softuni.heathy_desserts_recipes.service.WeatherService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import static org.springframework.data.jpa.domain.JpaSort.path;


@Controller
public class HomeController {
    private final WeatherService weatherService;
    private final MessagesService messagesService;

    public HomeController(WeatherService weatherService, MessagesService messagesService) {
        this.weatherService = weatherService;
        this.messagesService = messagesService;
    }

    @GetMapping("/")
    public String getHome(Model model) {
        JsonNode weatherData = weatherService.getWeather();
        model.addAttribute("temperature", weatherData.path("main").path("temp"));
        model.addAttribute("wind", weatherData.path("wind").path("speed").asText());

        return "home";
    }

    @GetMapping("/contact")
    public String getContact(@ModelAttribute("messageDto") MessageDto messageDto) {
        return "contact";

    }

    @GetMapping("/about")
    public String getAbout() {
        return "about";

    }

    @GetMapping("/terms")
    public String getTerm() {
        return "terms";

    }

    @GetMapping("/faq")
    public String getFaq() {
      return "faq";


    }
}