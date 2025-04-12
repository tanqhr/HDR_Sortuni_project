package bg.softuni.heathy_desserts_recipes.service;


import bg.softuni.heathy_desserts_recipes.common.enums.Role;
import bg.softuni.heathy_desserts_recipes.model.entity.recipe.RecipeEntity;
import bg.softuni.heathy_desserts_recipes.model.entity.role.RoleEntity;
import bg.softuni.heathy_desserts_recipes.model.entity.user.UserEntity;
import bg.softuni.heathy_desserts_recipes.model.entity.user.dto.UserRegistrationDTO;
import bg.softuni.heathy_desserts_recipes.model.entity.user.dto.UserShortViewModel;
import bg.softuni.heathy_desserts_recipes.model.entity.user.dto.UserUpdateDto;
import bg.softuni.heathy_desserts_recipes.model.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private final String NOT_EXISTING_EMAIL = "test@example.com";
    private static final String EXISTING_EMAIL = "kosta@abv.bg";
    private final String NEW_USERNAME = "testov";
    private final String RAW_PASSWORD = "12345";
    private final String ENCODED_PASSWORD = "%($)GGPPP3fdfd";

    private final Long VALID_ID = 1L;
    private final Long INVALID_ID = 300L;

    private final String FIRST_NAME = "Boris";
    private final String LAST_NAME = "Boev";
    private final String NON_EXISTING_USERNAME = "gosho";

    @InjectMocks
    private UserService testUserService;

    private AppUserDetailsService toTest;
    private RoleEntity testRoleEntity;
    private UserEntity userEntity;

    private UserShortViewModel userShortViewModel;

    @Mock
    private ModelMapper mockMapper;
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private PasswordEncoder mockPasswordEncoder;
    @Captor
    private ArgumentCaptor<UserEntity> userEntityArgumentCaptor;

    @Captor
    public ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);

    private static UserEntity user() {
        UserEntity user = new UserEntity();
        RoleEntity userRole = new RoleEntity();
        userRole.setRole(Role.ADMIN);
        user.setId(1L);
        user.setFirstName("Test111");
        user.setLastName("Testov111");
        user.setUsername("testov111");
        user.setRoles(List.of(userRole));
        user.setEmail("test111@gmail.com");
        user.setPassword("11111");
        return user;
    }

    @BeforeEach
    void setUp() {
        toTest = new AppUserDetailsService(mockUserRepository);
        userEntity = new UserEntity().setFirstName(FIRST_NAME).
                setUsername(LAST_NAME)
                .setUsername(NEW_USERNAME)
                .setPassword(mockPasswordEncoder.encode(RAW_PASSWORD))
                .setEmail(NOT_EXISTING_EMAIL)
                .setId(17L);

        userShortViewModel = new UserShortViewModel().setUsername(NEW_USERNAME).setId(17L);


    }


    @Test
    void testUserNotFound() {
        Assertions.assertThrows(
                UsernameNotFoundException.class,
                () -> toTest.loadUserByUsername(NOT_EXISTING_EMAIL)
        );
    }

    @Test
    void testGetAllUsers() {
        lenient().when(mockUserRepository.findAll()).thenReturn(List.of(userEntity));
        lenient().when(mockMapper.map(userEntity, UserShortViewModel.class)).thenReturn(userShortViewModel);

        Assertions.assertEquals(userEntity.getUsername(), userShortViewModel.getUsername());

    }

    @Test
    void testUserUpdate() {

        UserUpdateDto testUpdateDto = new UserUpdateDto();
        testUpdateDto.setFirstName(FIRST_NAME);
        testUpdateDto.setLastName(LAST_NAME);
        testUpdateDto.setUsername(NON_EXISTING_USERNAME);


    }

    @Test
    void testIsEmailUnique() {
        UserEntity user = new UserEntity();
        when(mockUserRepository.existsByEmail(user.getEmail())).thenReturn(true);
        Assertions.assertFalse(!testUserService.existsByEmail(user.getEmail()));
    }

    @Test
    void testIsEmailNotUnique() {
        UserEntity user = new UserEntity();
        when(mockUserRepository.existsByEmail(user.getEmail())).thenReturn(true);
        Assertions.assertTrue(testUserService.existsByEmail(user.getEmail()));
    }

     @Test
    public void creatNewUser() throws Exception {

         UserEntity user = new UserEntity().setFirstName("Test").setLastName("Testov").setUsername("test").
                 setEmail("testov@abv.bg").setPassword("12345");
     }

}