package bg.softuni.heathy_desserts_recipes.testUtils;

import bg.softuni.heathy_desserts_recipes.common.enums.Role;
import bg.softuni.heathy_desserts_recipes.model.entity.role.RoleEntity;
import bg.softuni.heathy_desserts_recipes.model.entity.user.UserEntity;
import bg.softuni.heathy_desserts_recipes.model.repository.RoleRepository;
import bg.softuni.heathy_desserts_recipes.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserTestData {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public UserEntity createTestUser(String email) {
        return createUser(email, List.of(Role.USER));
    }

    public UserEntity createTestAdmin(String email) {
        return createUser(email, List.of(Role.ADMIN));
    }

    private UserEntity createUser(String email, List<Role> roles) {

        var roleEntities = roleRepository.findAllByRoleIn(roles);

        UserEntity newUser = new UserEntity()
                .setActive(true)
                .setUsername("test123")
                .setEmail(email)
                .setPassword("12345")
                .setFirstName("Test user first")
                .setLastName("Test user last")
                .setRoles(
                        roleEntities
                );

        return userRepository.save(newUser);
    }

    public void cleanUp() {
        userRepository.deleteAll();
    }

}