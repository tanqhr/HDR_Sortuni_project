package bg.softuni.heathy_desserts_recipes.model.security;

import bg.softuni.heathy_desserts_recipes.common.enums.ContextRole;
import bg.softuni.heathy_desserts_recipes.common.enums.Role;
import bg.softuni.heathy_desserts_recipes.model.entity.role.RoleEntity;
import bg.softuni.heathy_desserts_recipes.model.entity.user.UserEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static bg.softuni.heathy_desserts_recipes.common.enums.Constants.ROLE_PREFIX;

@Getter
@Setter
@Accessors(chain = true)
public class CurrentUser extends User {

    private Long id;

    private String firstName;

    private String lastName;

    private String username;


    private Map<ContextRole, Boolean> contextRoles;

    public CurrentUser (String username, String password, Collection<? extends GrantedAuthority> authorities) {

        super(username, password, authorities);
        this.contextRoles = new HashMap<>();
        resetContextRoles();
    }

    public static CurrentUser fromEntity (UserEntity userEntity) {

        return new CurrentUser(userEntity.getEmail(),
                               userEntity.getPassword(),
                               getRolesArray(userEntity.getRoles()))
                .setId(userEntity.getId())
                .setUsername(userEntity.getUsername())
                .setFirstName(userEntity.getFirstName())
                .setLastName(userEntity.getLastName());
    }

    public boolean isAdmin() {

        return getAuthorities()
                .stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().contains(Role.ADMIN.name()));
    }

    public boolean isModerator() {

        return getAuthorities()
                .stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().contains(Role.MODERATOR.name()));
    }

    private static List<GrantedAuthority> getRolesArray (List<RoleEntity> roles) {

        return AuthorityUtils.createAuthorityList(roles.stream()
                .map(entity -> ROLE_PREFIX.concat(entity.getRole().name()))
                .toArray(String[]::new));
    }

    public void resetContextRoles () {

        this.contextRoles.put(ContextRole.AUTHOR, Boolean.FALSE);
    }





}

