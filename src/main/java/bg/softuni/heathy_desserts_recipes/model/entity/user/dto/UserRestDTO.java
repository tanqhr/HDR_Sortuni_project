package bg.softuni.heathy_desserts_recipes.model.entity.user.dto;

import bg.softuni.heathy_desserts_recipes.model.entity.user.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString
public class UserRestDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String username;


    public static UserRestDTO fromEntity (UserEntity entity) {

        return new UserRestDTO()
                .setId(entity.getId())
                .setFirstName(entity.getFirstName())
                .setLastName(entity.getLastName())
                .setEmail(entity.getEmail())
                .setUsername(entity.getUsername());
    }




}
