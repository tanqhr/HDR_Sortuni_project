package bg.softuni.heathy_desserts_recipes.web.controller;


import bg.softuni.heathy_desserts_recipes.model.entity.user.UserEntity;
import bg.softuni.heathy_desserts_recipes.model.repository.UserRepository;
import bg.softuni.heathy_desserts_recipes.testUtils.UserTestData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProfileControllerIT {



    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTestData userTestData;

    private UserEntity testUser;
   // @AfterEach
   // void tearDown() {
   //     userRepository.deleteAll();

  //  }

    @Test
    @WithAnonymousUser
    void testGetProfileMustRedirectToLoginWithAnonymous() throws Exception {
        mockMvc.perform(get("/users/profile")
                )
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithUserDetails(
            value = "taico@abv.bg",
            userDetailsServiceBeanName = "userDetailsService",
            setupBefore = TestExecutionEvent.TEST_EXECUTION
    )
    void getProfile_byLoggedUser_returnsOK () throws Exception {

        mockMvc.perform(get("/users/profile"))
                .andExpect(status().isOk())
                .andExpect(view().name("profile"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    @WithMockUser(username = "taico@abv.bg")
    void testUpdateUserWithIncorrectData() throws Exception {
        mockMvc.perform(post("/users/edit/{id}", "1")
                        .param("firstName", "1")
                        .param("lastName", "1")
                        .param("username", "1")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/edit/1"));


    }

    @Test
    @WithMockUser(username = "taico@abv.bg")
    void testUpdateUser() throws Exception {
       // Optional<UserEntity>user=userRepository.findUserByEmail("taico@abv.bg");
        mockMvc.perform(post("/users/edit/{id}", "1")
                        .param("firstName", "Taico")
                        .param("lastName", "Taico")
                        .param("username", "taico")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/profile"));

        Optional<UserEntity> optionalUser = userRepository.findByEmail("taico@abv.bg");

        Assertions.assertTrue(optionalUser.isPresent());
        UserEntity user1 = optionalUser.get();
        Assertions.assertEquals("Taico", user1.getFirstName());
        Assertions.assertEquals("Taico", user1.getLastName());
        Assertions.assertEquals("taico", user1.getUsername());
    }


}
