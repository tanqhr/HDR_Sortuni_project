package bg.softuni.heathy_desserts_recipes.web.controller.rest;

import bg.softuni.heathy_desserts_recipes.model.entity.recipe.dto.RecipeShortDto;
import bg.softuni.heathy_desserts_recipes.model.entity.recipe.dto.RecipeViewModel;
import bg.softuni.heathy_desserts_recipes.model.entity.user.dto.UserRestDTO;
import bg.softuni.heathy_desserts_recipes.model.entity.user.dto.UserShortViewModel;
import bg.softuni.heathy_desserts_recipes.model.entity.user.dto.UserViewModel;
import bg.softuni.heathy_desserts_recipes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class RestUserController {

    private final UserService userService;

    @Autowired
    public RestUserController (UserService userService) {

        this.userService = userService;
    }

    @GetMapping("/{id}")
    public UserRestDTO userProfile(@PathVariable Long id) {

        return this.userService.findUserProfileById(id);
    }
    @GetMapping("/all")
    public ResponseEntity<List<UserViewModel>> getAll() {
        return ResponseEntity.ok(userService.getAllUsers());
    }


}
