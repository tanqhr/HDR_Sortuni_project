package bg.softuni.heathy_desserts_recipes.model.entity.user.dto;

import bg.softuni.heathy_desserts_recipes.common.error.validation.annotations.PasswordsMatch;
import bg.softuni.heathy_desserts_recipes.common.error.validation.annotations.UniqueEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static bg.softuni.heathy_desserts_recipes.common.enums.Constants.Registration.*;


@Getter
@Setter
@NoArgsConstructor
@PasswordsMatch()
public class UserRegistrationDTO {

    @Size(min = MIN_FIRSTNAME_LENGTH, message = MIN_FIRST_NAME)
    @Size(max = MAX_FIRSTNAME_LENGTH, message = MAX_FIRST_NAME)


    private String firstName;

    @Size(min = MIN_LASTNAME_LENGTH, message = MIN_LAST_NAME)
    @Size(max = MAX_LASTNAME_LENGTH, message = MAX_LAST_NAME)
    private String lastName;

    @Size(min = MIN_USERNAME_LENGTH, message = MIN_USERNAME_NAME)
    @Size(max = MAX_USERNAME_LENGTH, message = MAX_USERNAME_NAME)
    private String username;

    @Email(regexp = EMAIL_REG_EXP, message = NOT_WELL_FORMATTED_EMAIL)
    @UniqueEmail(message = EMAIL_NOT_AVAILABLE)
    private String email;


    @Size(min = MIN_PASSWORD_LENGTH, message = MIN_PASSWORD_LENGTH_MSG)
    private String password;

    @Size(min = MIN_PASSWORD_LENGTH, message = MIN_PASSWORD_LENGTH_MSG)
    private String confirmPassword;

}
