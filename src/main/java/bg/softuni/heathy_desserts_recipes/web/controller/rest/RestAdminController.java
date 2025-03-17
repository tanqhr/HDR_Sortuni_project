package bg.softuni.heathy_desserts_recipes.web.controller.rest;


import bg.softuni.heathy_desserts_recipes.common.error.exceptions.IllegalTargetException;
import bg.softuni.heathy_desserts_recipes.common.error.exceptions.NotAuthorizedException;
import bg.softuni.heathy_desserts_recipes.common.messages.Message;
import bg.softuni.heathy_desserts_recipes.model.entity.user.dto.UserViewModel;
import bg.softuni.heathy_desserts_recipes.model.security.CurrentUser;
import bg.softuni.heathy_desserts_recipes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RestAdminController {

    private final UserService userService;

    @Autowired
    public RestAdminController (UserService userService) {

        this.userService = userService;
    }

    @GetMapping("api/admin/users")
    public List<UserViewModel> getUsers () {

        return this.userService.getAllUsers();
    }



}
