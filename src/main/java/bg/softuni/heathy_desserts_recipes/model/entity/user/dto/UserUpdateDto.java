package bg.softuni.heathy_desserts_recipes.model.entity.user.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import static bg.softuni.heathy_desserts_recipes.common.enums.Constants.Registration.*;
import static bg.softuni.heathy_desserts_recipes.common.enums.Constants.Registration.MAX_USERNAME_NAME;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDto {
    private Long id;
    @Size(min = MIN_USERNAME_LENGTH, message = MIN_USERNAME_NAME)
    @Size(max = MAX_USERNAME_LENGTH, message = MAX_USERNAME_NAME)
    private String username;
    @NotBlank(message = MIN_FIRST_NAME)
    private String firstName;
    @NotBlank(message = MIN_LAST_NAME)
    private String lastName;
}
