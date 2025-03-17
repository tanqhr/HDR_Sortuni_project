package bg.softuni.heathy_desserts_recipes.service;

import bg.softuni.heathy_desserts_recipes.common.enums.Role;
import bg.softuni.heathy_desserts_recipes.common.error.exceptions.NotAuthorizedException;
import bg.softuni.heathy_desserts_recipes.common.error.exceptions.UserNotFoundException;
import bg.softuni.heathy_desserts_recipes.model.entity.role.RoleEntity;
import bg.softuni.heathy_desserts_recipes.model.entity.user.UserEntity;
import bg.softuni.heathy_desserts_recipes.model.entity.user.dto.*;
import bg.softuni.heathy_desserts_recipes.model.repository.RoleRepository;
import bg.softuni.heathy_desserts_recipes.model.repository.UserRepository;
import bg.softuni.heathy_desserts_recipes.model.security.CurrentUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper userMapper;
    private final PasswordEncoder encoder;
    private final ModelMapper modelMapper;

    public UserService (UserRepository userRepository,
                        RoleRepository roleRepository,
                        @Qualifier("userMapper") ModelMapper userMapper,
                        PasswordEncoder passwordEncoder, ModelMapper modelMapper) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
        this.encoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }



    public void register (UserRegistrationDTO userDTO) {

        UserEntity userEntity = this.userMapper.map(userDTO, UserEntity.class);
        userEntity.addRoles(roleRepository.getByRole(Role.USER));
        userEntity.setPassword(encoder.encode(userDTO.getPassword()));
        //TODO email activation
        userEntity.setActive(true);

        this.userRepository.saveAndFlush(userEntity);
    }


    public UserEntity findById (Long id) {

        return this.userRepository.findUserById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found!"));
    }

    public UserRestDTO findUserProfileById (Long id) {

        return UserRestDTO.fromEntity(this.findById(id));
    }

    public boolean existsByEmail (String email) {

        return this.userRepository.existsByEmail(email);
    }

    public boolean existsById (Long id) {

        return this.userRepository.findUserById(id).isPresent();
    }

    public void saveAndFlush (UserEntity userEntity) {

        this.userRepository.saveAndFlush(userEntity);
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


    private static void ensureAdminOrModerator (CurrentUser requester) {

        if (!requester.isAdmin() && !requester.isModerator()) {
            throw new NotAuthorizedException("What are you trying to do?");
        }
    }

    private static void ensureAdmin (CurrentUser requester) {

        if (!requester.isAdmin()) {
            throw new NotAuthorizedException("What are you trying to do?");
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


    public long createUser(UserRestDTO newUser) {

        UserEntity newUserEntity = new UserEntity().
                setFirstName(newUser.getFirstName()).
                setLastName(newUser.getLastName()).
                setUsername(newUser.getUsername()).
                setEmail(newUser.getEmail());

        return userRepository.save(newUserEntity).getId();
    }

   // public UserUpdateDto editUser(UserUpdateDto userUpdateBindingModel, String loggedInUserEmail) {

   //     UserEntity loggedInUser = userRepository.findUserByEmail(loggedInUserEmail)
//                .orElseThrow();

  //      UserEntity userToEdit;

   //     userToEdit = loggedInUser;


     //   if (!userToEdit.getFirstName().equals(userUpdateBindingModel.getFirstName())) {
     //       userToEdit.setFirstName(userUpdateBindingModel.getFirstName()
    //                .toUpperCase());
    //    }

     //   if (!userToEdit.getLastName().equals(userUpdateBindingModel.getLastName())) {
     //       userToEdit.setLastName(userUpdateBindingModel.getLastName());
     //   }

    //    if (!userToEdit.getUsername().equals(userUpdateBindingModel.getUsername())) {
    //       userToEdit.setUsername(userUpdateBindingModel.getUsername());
    //    }


   //     return null;
  //  }


    public UserEntity getUserByUsername(String username) {
        return userRepository.findUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }
}


