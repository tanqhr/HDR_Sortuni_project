package bg.softuni.heathy_desserts_recipes.common.error.validation.annotations;

import bg.softuni.heathy_desserts_recipes.common.error.validation.validator.PasswordsMatchValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordsMatchValidator.class)
@Target( { ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordsMatch {

    String message() default "{password_match}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
