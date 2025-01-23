package bg.softuni.heathy_desserts_recipes.common.error.validation.validator;


import bg.softuni.heathy_desserts_recipes.common.error.validation.annotations.UniqueEmail;
import bg.softuni.heathy_desserts_recipes.service.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final UserService userService;

    public UniqueEmailValidator (UserService userService) {

        this.userService = userService;
    }

    @Override
    public void initialize (UniqueEmail constraintAnnotation) {

        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid (String email, ConstraintValidatorContext context) {

        return !this.userService.existsByEmail(email);
    }
}
