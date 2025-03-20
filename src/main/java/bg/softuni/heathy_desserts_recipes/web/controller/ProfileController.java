package bg.softuni.heathy_desserts_recipes.web.controller;

import bg.softuni.heathy_desserts_recipes.model.entity.user.dto.UserUpdateDto;
import bg.softuni.heathy_desserts_recipes.model.entity.user.view.UserProfileViewModel;
import bg.softuni.heathy_desserts_recipes.model.security.CurrentUser;
import bg.softuni.heathy_desserts_recipes.service.UserService;
import jakarta.validation.Valid;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static bg.softuni.heathy_desserts_recipes.common.enums.Constants.BINDING_RESULT_PATH;

@Controller
public class ProfileController {
    private final UserService userService;


    public ProfileController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/users/profile")
    public String profile(@AuthenticationPrincipal CurrentUser currentUser, Model model) {
        Long id = currentUser.getId();
        UserProfileViewModel userInfo = userService.getUserInfo(id);

        model.addAttribute("user", userInfo);
        return "profile";
    }

    @PostMapping("/users/edit/{id}")
    public String updateUser(@PathVariable("id") Long id, @ModelAttribute("user") @Valid UserUpdateDto userDetails, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("user", userDetails);
            redirectAttributes.addFlashAttribute(BINDING_RESULT_PATH+ userDetails, bindingResult);

            return "redirect:/users/edit/" + id;
        }
        redirectAttributes.addFlashAttribute("successEdit", true);
        userService.updateUser(id, userDetails);

        return "redirect:/users/profile";
    }


}