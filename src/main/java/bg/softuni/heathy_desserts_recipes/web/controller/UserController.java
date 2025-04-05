package bg.softuni.heathy_desserts_recipes.web.controller;

import bg.softuni.heathy_desserts_recipes.model.entity.user.dto.UserRegistrationDTO;
import bg.softuni.heathy_desserts_recipes.model.entity.user.dto.UserViewModel;
import bg.softuni.heathy_desserts_recipes.model.security.CurrentUser;
import bg.softuni.heathy_desserts_recipes.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;

import static bg.softuni.heathy_desserts_recipes.common.enums.Constants.BINDING_RESULT_PATH;

@Controller
public class UserController {

    private final UserService userService;
    private final SecurityContextRepository securityContextRepository;

    @Autowired
    public UserController(UserService userService, SecurityContextRepository securityContextRepository) {

        this.userService = userService;
        this.securityContextRepository = securityContextRepository;
    }

    @ModelAttribute("userRegistrationDTO")
    public void initUserRegistrationDTO(Model model) {

        model.addAttribute("userRegistrationDTO", new UserRegistrationDTO());
    }

    @GetMapping("/registration")
    public String getRegistrationForm () {


        return "registration";
    }

    @PostMapping("/registration")
    public ModelAndView doRegister (@Valid UserRegistrationDTO userRegistrationDTO,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttrs, HttpServletRequest request,
                                    HttpServletResponse response) {

        checkPasswordsMatchError(bindingResult);

        if (bindingResult.hasErrors()) {
            redirectAttrs.addFlashAttribute("userRegistrationDTO", userRegistrationDTO);
            redirectAttrs.addFlashAttribute(BINDING_RESULT_PATH + userRegistrationDTO, bindingResult);
            return new ModelAndView ("/registration", HttpStatusCode.valueOf(400));
        }

        //auto login
        this.userService.register(userRegistrationDTO, successfulAuth -> {

            SecurityContextHolderStrategy strategy = SecurityContextHolder.getContextHolderStrategy();

            SecurityContext context = strategy.createEmptyContext();
            context.setAuthentication(successfulAuth);

            strategy.setContext(context);

            securityContextRepository.saveContext(context, request, response);
        });

        return new ModelAndView("redirect:/");
        //  redirectAttrs.addFlashAttribute("username", userRegistrationDTO.getUsername());

        //  return new ModelAndView("redirect:/registration/success");
    }

    @GetMapping("/registration/success")
    public String getSuccessPage () {

        return "registration-success";
    }

    @GetMapping("/login")
    public String getLoginForm () {

        return "login";
    }

    @GetMapping(value = "/users/me", consumes = MediaType.ALL_VALUE)
    public String getOwnProfile (Model model,
                                 @AuthenticationPrincipal CurrentUser currentUser) {

        model.addAttribute("displayName", currentUser.getUsername());

        return "owner";
    }



    @GetMapping("/users/{id}")
    public String getUserProfile (Model model,
                                  @PathVariable Long id) {

        final UserViewModel userVM = this.userService.getUserById(id);
        model.addAttribute("displayName", userVM.getUsername());
        model.addAttribute("userId", userVM.getId());

        return "other";
    }

    private static void checkPasswordsMatchError (BindingResult bindingResult) {

        final Predicate<ObjectError> hasPasswordsMatchErrorCode =
                err -> Arrays.asList(Objects.requireNonNull(err.getCodes())).contains("PasswordsMatch");

        bindingResult
                .getGlobalErrors()
                .stream()
                .filter(hasPasswordsMatchErrorCode)
                .findAny()
                .ifPresent(objectError -> bindingResult
                        .rejectValue("confirmPassword",
                                     Objects.requireNonNull(objectError.getCode()),
                                     Objects.requireNonNull(objectError.getDefaultMessage())));
    }

}
