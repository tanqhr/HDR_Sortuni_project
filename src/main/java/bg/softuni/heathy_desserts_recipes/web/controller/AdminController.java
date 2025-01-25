package bg.softuni.heathy_desserts_recipes.web.controller;


import bg.softuni.heathy_desserts_recipes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController (UserService userService) {

        this.userService = userService;
    }

    @GetMapping("/admin/board")
    public String getDashboard () {

        return "admin/board";
    }

    @GetMapping("/admin/view")
    public String getUsers (Model model) {

        model.addAttribute("users", this.userService.getAllUsers());

        return "admin/view";
    }
   //* @DeleteMapping("/admin/users/delete/{id}")
   @RequestMapping(value = "/admin/users/delete/{id}", method = {RequestMethod.GET, RequestMethod.DELETE})
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteUser(@PathVariable Long id) {
        this.userService.deleteUser(id);
        return "redirect:/admin/view";
    }
}
