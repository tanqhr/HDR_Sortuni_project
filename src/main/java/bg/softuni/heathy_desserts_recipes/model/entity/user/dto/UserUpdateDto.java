package bg.softuni.heathy_desserts_recipes.model.entity.user.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import static bg.softuni.heathy_desserts_recipes.common.enums.Constants.Registration.*;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor

public class UserUpdateDto {
    private Long id;
    @Size(min = MIN_USERNAME_LENGTH, message = "{username}")
    @Size(max = MAX_USERNAME_LENGTH, message ="{username_max}")
    private String username;
    @Size(min = MIN_FIRSTNAME_LENGTH, message = "{first_name}")
    @Size(max = MAX_FIRSTNAME_LENGTH, message = "{firstName_max}")
    private String firstName;
    @Size(min = MIN_LASTNAME_LENGTH, message = "{last_name}")
    @Size(max = MAX_LASTNAME_LENGTH, message = "{lastName_max}")
    private String lastName;
}
