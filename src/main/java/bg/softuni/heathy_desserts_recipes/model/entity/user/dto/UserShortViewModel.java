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
public class UserShortViewModel {

    private Long id;

    private String username;

    public static UserShortViewModel fromEntity (UserEntity entity) {

        return new UserShortViewModel()
                .setId(entity.getId())
                .setUsername(entity.getUsername());
    }


}
