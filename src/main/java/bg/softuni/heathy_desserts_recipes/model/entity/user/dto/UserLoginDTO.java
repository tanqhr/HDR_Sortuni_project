package bg.softuni.heathy_desserts_recipes.model.entity.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserLoginDTO {

    private String email;

    private String password;

}
