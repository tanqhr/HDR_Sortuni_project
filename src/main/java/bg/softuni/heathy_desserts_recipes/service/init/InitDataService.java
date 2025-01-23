package bg.softuni.heathy_desserts_recipes.service.init;

import bg.softuni.heathy_desserts_recipes.common.enums.Role;
import bg.softuni.heathy_desserts_recipes.model.entity.role.RoleEntity;
import bg.softuni.heathy_desserts_recipes.model.entity.user.UserEntity;
import bg.softuni.heathy_desserts_recipes.model.repository.RoleRepository;
import bg.softuni.heathy_desserts_recipes.model.repository.UserRepository;
import bg.softuni.heathy_desserts_recipes.model.repository.VisibilityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class InitDataService implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final VisibilityRepository visibilityRepository;
    private final PasswordEncoder passwordEncoder;

    public InitDataService (RoleRepository roleRepository,
                            UserRepository userRepository,
                            VisibilityRepository visibilityRepository, PasswordEncoder passwordEncoder) {

        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.visibilityRepository = visibilityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run (String... args) {

        if (this.roleRepository.findAll().isEmpty()) initRoles();
        if (this.userRepository.findAll().isEmpty()) initUsers();
    }

    public void initRoles () {

        final List<RoleEntity> roleEntities = Arrays.stream(Role.values())
                .map(RoleEntity::new)
                .toList();
        this.roleRepository.saveAllAndFlush(roleEntities);
    }

    private void initUsers () {

        var roleUser = roleRepository.getByRole(Role.USER);
        var roleModerator = roleRepository.getByRole(Role.MODERATOR);
        var roleAdmin = roleRepository.getByRole(Role.ADMIN);
        List<UserEntity> users = new ArrayList<>();

        users.add(new UserEntity()
                .setEmail("taico@abv.bg")
                .setPassword(this.passwordEncoder.encode("12345"))
                .setFirstName("Taico")
                .setLastName("Taico")
                .setUsername("taico")
                .setActive(true)
                .addRoles(roleUser)
        );

        users.add(new UserEntity()
                .setEmail("kosta@abv.bg")
                .setPassword(this.passwordEncoder.encode("12345"))
                .setFirstName("Kosta")
                .setLastName("Kosta")
                .setUsername("kosta")
                .setActive(true)
                .addRoles(roleUser, roleModerator, roleAdmin)
        );

        users.add(new UserEntity()
                .setEmail("taico1@abv.bg")
                .setPassword(this.passwordEncoder.encode("12345"))
                .setFirstName("Taico1")
                .setLastName("Taico1")
                .setUsername("taico1")
                .setActive(true)
                .addRoles(roleUser, roleModerator)
        );

        this.userRepository.saveAllAndFlush(users);
    }


}
