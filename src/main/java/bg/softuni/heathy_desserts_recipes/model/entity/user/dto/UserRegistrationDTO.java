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

    @Size(min = MIN_FIRSTNAME_LENGTH, message ="{first_name}")
    @Size(max = MAX_FIRSTNAME_LENGTH, message = "{firstName_max}")

    private String firstName;

    @Size(min = MIN_LASTNAME_LENGTH, message = "{last_name}")
    @Size(max = MAX_LASTNAME_LENGTH, message = "{lastName_max}")
    private String lastName;

    @Size(min = MIN_USERNAME_LENGTH, message = "{username}")
    @Size(max = MAX_USERNAME_LENGTH, message = "{username_max}")
    private String username;

    @Email(regexp = EMAIL_REG_EXP, message = "{email_validation}")
    @UniqueEmail(message = "{email-unique}")
    private String email;


    @Size(min = MIN_PASSWORD_LENGTH, message = "{password}")
    private String password;

    @Size(min = MIN_PASSWORD_LENGTH, message = "{password}")
    private String confirmPassword;

}
