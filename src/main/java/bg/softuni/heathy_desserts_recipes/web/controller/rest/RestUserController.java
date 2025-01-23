package bg.softuni.heathy_desserts_recipes.web.controller.rest;

import bg.softuni.heathy_desserts_recipes.model.entity.user.dto.UserShortViewModel;
import bg.softuni.heathy_desserts_recipes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestUserController {

    private final UserService userService;

    @Autowired
    public RestUserController (UserService userService) {

        this.userService = userService;
    }

    @GetMapping("api/users/{id}")
    public UserShortViewModel userProfile(@PathVariable Long id) {

        return this.userService.findUserProfileById(id);
    }

}
