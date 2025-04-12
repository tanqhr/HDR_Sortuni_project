package bg.softuni.heathy_desserts_recipes.web.controller;

import bg.softuni.heathy_desserts_recipes.config.MailConfiguration;
import bg.softuni.heathy_desserts_recipes.model.entity.user.UserEntity;
import bg.softuni.heathy_desserts_recipes.model.repository.UserRepository;
import bg.softuni.heathy_desserts_recipes.testUtils.UserTestData;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")

class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Value("localhost")
    private String mailHost;

    @Value("1025")
    private Integer mailPort;
    @Value("test@example.com")
    private String mailUsername;
    @Value("password")
    private String mailPassword;

    private GreenMail greenMail;

    @Autowired
    private UserRepository userRepository;
    


    //@BeforeEach
   // void setUp() {
   //     greenMail = new GreenMail(new ServerSetup(mailPort, mailHost, "smtp"));
   //     greenMail.start();
   //     greenMail.setUser(mailUsername, mailPassword);
  //  }

  //  @AfterEach
  //  void tearDown() {
    //    greenMail.stop();
  // }



    @Test
    void doRegister_withInvalidData_returnsRegistrationFormWithErrors () throws Exception {

        mockMvc.perform(post("/registration")
                        .param("firstName", "")
                        .param("lastName", "")
                        .param("username", "")
                        .param("email", "testtestmailcom")
                        .param("password", "t")
                        .param("confirmPassword", "test1234")
                        .with(csrf())
                )
                .andExpect(status().isBadRequest())
                .andExpect(model().attributeHasErrors("userRegistrationDTO"))
                .andExpect(view().name("/registration"));
    }



    @Test
    @WithAnonymousUser
    void getLoginForm_byAnonymous_returnsCorrectView () throws Exception {

        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    @WithMockUser
    void getLoginForm_byLoggedUser_returnsForbiddenStatus () throws Exception {

        mockMvc.perform(get("/login"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithAnonymousUser
    void getOwnProfile_byAnonymous_redirectsToLoginPage () throws Exception {

        mockMvc.perform(get("/users/me"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithUserDetails(value = "taico1@abv.bg", userDetailsServiceBeanName = "userDetailsService")
    void getOwnProfile_byLoggedUser_returnsCorrectView () throws Exception {
     //  Optional<UserEntity> user=userRepository.findByEmail("taico@abv.bg");
        mockMvc.perform(get("/users/me"))
                .andExpect(status().isOk())
                .andExpect(view().name("owner"))
                .andExpect(model().attribute("displayName", "taico1"));
    }

    @Test
    @WithAnonymousUser
    void getUserProfileOfExistingUser_byAnonymousUser_returnForbiddenStatus () throws Exception {

        mockMvc.perform(get("/users/{id}", "id=1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithAnonymousUser
    void getSuccessPage_returnsCorrectView () throws Exception {

        mockMvc.perform(get("/registration/success"))
                .andExpect(status().isOk())
                .andExpect(view().name("registration-success"))
                .andExpect(content().string(not(containsString("Congratulations"))));
    }

    @Test
    @WithUserDetails(
            value = "taico1@abv.bg",
            userDetailsServiceBeanName = "userDetailsService",
            setupBefore = TestExecutionEvent.TEST_EXECUTION
    )
    void getUserProfileOfExistingUser_byLoggedUser_returnCorrectView () throws Exception {
        Optional<UserEntity> user= userRepository.findUserByEmail("taico1@abv.bg");

        mockMvc.perform(get("/users/{id}", user.get().getId()))
                .andExpect(status().isOk())
                .andExpect(model().hasNoErrors())
                .andExpect(model().attribute("displayName", user.get().getUsername()))
                .andExpect(model().attribute("userId", user.get().getId()))
                .andExpect(view().name("other"));
    }


    @Test
    @WithAnonymousUser
    void getRegistrationForm_byAnonymous_returnsProperView () throws Exception {

        mockMvc.perform(get("/registration"))
                .andExpect(status().isOk())
                .andExpect(view().name("registration"));
    }

    @Test
    @WithMockUser
    void getRegistrationForm_byUser_returnsStatusForbidden () throws Exception {

        mockMvc.perform(get("/registration"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithAnonymousUser
    void testLikeUser_returnForbidden() throws Exception {
        mockMvc.perform(post("/like/1").contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }


   }