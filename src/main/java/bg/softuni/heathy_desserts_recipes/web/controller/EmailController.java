package bg.softuni.heathy_desserts_recipes.web.controller;

import bg.softuni.heathy_desserts_recipes.model.entity.email.dto.EmailDto;
import bg.softuni.heathy_desserts_recipes.model.entity.user.dto.UserRegistrationDTO;
import bg.softuni.heathy_desserts_recipes.model.repository.RecipeRepository;
import bg.softuni.heathy_desserts_recipes.service.EmailService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static bg.softuni.heathy_desserts_recipes.common.enums.Constants.BINDING_RESULT_PATH;

@Controller
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/about")
    public ModelAndView doSign (@Valid EmailDto emailDto,
                                    BindingResult bindingResult,
                                    Model model) {

        this.emailService.register(emailDto);
        model.addAttribute("emailDto", emailDto);

        return new ModelAndView("signFor");
    }
}
