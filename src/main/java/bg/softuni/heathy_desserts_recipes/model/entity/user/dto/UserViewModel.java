package bg.softuni.heathy_desserts_recipes.model.entity.user.dto;

import bg.softuni.heathy_desserts_recipes.common.enums.Role;
import bg.softuni.heathy_desserts_recipes.model.entity.role.RoleEntity;
import bg.softuni.heathy_desserts_recipes.model.entity.user.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class UserViewModel {

    private Long id;

    private String firstName;

    private String lastName;

    private String username;

    private String email;

    private boolean active;

    private List<Role> roles;

    public static UserViewModel fromEntity (UserEntity entity) {

        return new UserViewModel()
                .setId(entity.getId())
                .setFirstName(entity.getFirstName())
                .setLastName(entity.getLastName())
                .setUsername(entity.getUsername())
                .setEmail(entity.getEmail())
                .setActive(entity.isActive())
                .setRoles(entity.getRoles().stream().map(RoleEntity::getRole).toList());
    }

    public boolean isModerator () {
        return this.roles.contains(Role.MODERATOR);
    }

    public boolean isAdmin () {
        return this.roles.contains(Role.ADMIN);
    }



}
