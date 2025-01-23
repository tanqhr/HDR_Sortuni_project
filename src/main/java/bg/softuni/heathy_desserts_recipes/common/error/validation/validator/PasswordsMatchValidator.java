package bg.softuni.heathy_desserts_recipes.common.error.validation.validator;

import bg.softuni.heathy_desserts_recipes.common.error.validation.annotations.PasswordsMatch;
import bg.softuni.heathy_desserts_recipes.model.entity.user.dto.UserRegistrationDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordsMatchValidator implements ConstraintValidator<PasswordsMatch, UserRegistrationDTO> {

    @Override
    public void initialize (PasswordsMatch constraintAnnotation) {

        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid (UserRegistrationDTO candidate, ConstraintValidatorContext context) {

        final String password = candidate.getPassword();
        final String confirmPassword = candidate.getConfirmPassword();

        return password.equals(confirmPassword);
    }
}
