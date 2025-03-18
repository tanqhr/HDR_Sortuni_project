package bg.softuni.heathy_desserts_recipes.model.entity.user.view;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;


@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileViewModel {

    private Long id;
    @NotBlank
    @Size(min = 7, max = 20)
    private String firstName;
    @NotBlank
    @Size(min = 7, max = 20)
    private String lastName;
    @NotBlank
    @Size(min = 10)
    private String username;

}
