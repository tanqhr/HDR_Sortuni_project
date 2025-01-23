package bg.softuni.heathy_desserts_recipes.service;

import bg.softuni.heathy_desserts_recipes.model.repository.UserRepository;
import bg.softuni.heathy_desserts_recipes.model.security.CurrentUser;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static bg.softuni.heathy_desserts_recipes. common.enums.Constants.FORMAT_USER_WITH_EMAIL_NOT_FOUND;


public class AppUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;


    public AppUserDetailsService (UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername (String email) throws UsernameNotFoundException {

        return this.userRepository
                .findUserByEmail(email)
                .map(CurrentUser::fromEntity)
                .orElseThrow(() -> getUserNotFoundException(email));
    }

    private UsernameNotFoundException getUserNotFoundException (String email) {

        return new UsernameNotFoundException(FORMAT_USER_WITH_EMAIL_NOT_FOUND.formatted(email));
    }
}
