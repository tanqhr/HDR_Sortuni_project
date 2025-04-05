package bg.softuni.heathy_desserts_recipes.service;

import bg.softuni.heathy_desserts_recipes.common.enums.Role;
import bg.softuni.heathy_desserts_recipes.common.error.exceptions.NotAuthorizedException;
import bg.softuni.heathy_desserts_recipes.common.error.exceptions.UserNotFoundException;
import bg.softuni.heathy_desserts_recipes.model.entity.role.RoleEntity;
import bg.softuni.heathy_desserts_recipes.model.entity.user.UserEntity;
import bg.softuni.heathy_desserts_recipes.model.entity.user.dto.*;
import bg.softuni.heathy_desserts_recipes.model.entity.user.view.UserProfileViewModel;
import bg.softuni.heathy_desserts_recipes.model.event.RegistrationUserEvent;
import bg.softuni.heathy_desserts_recipes.model.repository.RoleRepository;
import bg.softuni.heathy_desserts_recipes.model.repository.UserRepository;
import bg.softuni.heathy_desserts_recipes.model.security.CurrentUser;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper userMapper;
    private final PasswordEncoder encoder;
    private final ModelMapper modelMapper;
private final MessageService messageService;
    private final UserDetailsService userDetailsService;
    private final ApplicationEventPublisher applicationEventPublisher;


    public UserService (UserRepository userRepository,
                        RoleRepository roleRepository,
                        @Qualifier("userMapper") ModelMapper userMapper,
                        PasswordEncoder passwordEncoder, ModelMapper modelMapper, MessageService messageService, UserDetailsService userDetailsService, ApplicationEventPublisher applicationEventPublisher) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
        this.encoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.messageService = messageService;
        this.userDetailsService = userDetailsService;
        this.applicationEventPublisher = applicationEventPublisher;
    }



    public void register (UserRegistrationDTO userDTO, Consumer<Authentication> successfulLoginProcessor) {

        UserEntity userEntity = this.userMapper.map(userDTO, UserEntity.class);
        userEntity.addRoles(roleRepository.getByRole(Role.USER));
        userEntity.setPassword(encoder.encode(userDTO.getPassword()));

        userEntity.setActive(true);

        UserEntity savedUser = this.userRepository.saveAndFlush(userEntity);
        messageService.sendRegistrationEmail(savedUser.getEmail(), savedUser.getUsername());

        UserDetails userDetails = userDetailsService.loadUserByUsername(userDTO.getEmail());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );

        successfulLoginProcessor.accept(authentication);


    }


    public UserEntity findById (Long id) {

        return this.userRepository.findUserById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found!"));
    }


    public boolean existsByEmail (String email) {

        return this.userRepository.existsByEmail(email);
    }


    public List<UserViewModel> getAllUsers () {

        return this.userRepository.findAll()
                .stream()
                .map(UserViewModel::fromEntity)
                .toList();
    }

    public UserViewModel getUserById (Long userId) {

        return UserViewModel.fromEntity(findById(userId));
    }

    public void deactivate(Long id) {


        final UserEntity userEntity = this.findById(id);

        if (userEntity.isActive()) {
            userEntity.setActive(Boolean.FALSE);

            this.userRepository.saveAndFlush(userEntity);
        } else {

            userEntity.setActive(Boolean.TRUE);

            this.userRepository.saveAndFlush(userEntity);
        }
    }


    public void promoteModerator (Long id){

        final UserEntity userEntity = this.findById(id);

        if (hasRole(userEntity, Role.MODERATOR)) {
            final RoleEntity roleEntity = getRole(Role.MODERATOR);
            userEntity.removeRoles(roleEntity);
            this.userRepository.saveAndFlush(userEntity);
        }else {

            final RoleEntity roleEntity = getRole(Role.MODERATOR);
            userEntity.addRoles(roleEntity);

            this.userRepository.saveAndFlush(userEntity);
        }
    }

    private static boolean hasRole (UserEntity userEntity, Role role) {

        return userEntity.getRoles().stream().anyMatch(r -> r.getRole().equals(role));
    }

    private RoleEntity getRole (Role role) {

        return this.roleRepository.getByRole(role);
    }

    public void deleteUser(Long id) {
       userRepository.deleteById(id);
    }




@Transactional
    public void updateUser(Long id, UserUpdateDto userDetails) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));

        user.setUsername(userDetails.getUsername());
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        userRepository.saveAndFlush(user);
    }

    public UserProfileViewModel getUserInfo(Long id) {
        return modelMapper.map(userRepository.findById(id), UserProfileViewModel.class);
    }
}


