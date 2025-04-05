package bg.softuni.heathy_desserts_recipes.web.controller;

import bg.softuni.heathy_desserts_recipes.common.enums.Role;
import bg.softuni.heathy_desserts_recipes.model.entity.recipe.RecipeEntity;
import bg.softuni.heathy_desserts_recipes.model.entity.role.RoleEntity;
import bg.softuni.heathy_desserts_recipes.model.entity.user.UserEntity;
import bg.softuni.heathy_desserts_recipes.model.repository.UserRepository;
import bg.softuni.heathy_desserts_recipes.testUtils.DBTestData;
import bg.softuni.heathy_desserts_recipes.testUtils.UserTestData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.nio.file.Files.delete;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AdminControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserTestData userTestData;

    @Autowired
    private DBTestData dbTestData;


    @Autowired
    private UserRepository userRepository;


   // @BeforeEach
   // void setUp() {
  //      userTestData.createTestAdmin("taicoooo@abv.bg");
   // }

   // @AfterEach
   // void tearDown() {
  //      dbTestData.cleanUp();
 //       userTestData.cleanUp();
 //   }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testGetUsersList() throws Exception {


        mockMvc.perform(get("/admin/view"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("users"))
                .andExpect(view().name("admin/view"));

    }
    @Test
    @WithAnonymousUser
    void getDashboard_byAnonymous_returnsUnauthorized () throws Exception {

        mockMvc.perform(get("/admin/board"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithMockUser
    void getDashboard_byLoggedUser_returnsUnauthorized () throws Exception {

        mockMvc.perform(get("/admin/board"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "testModerator", roles = {"MODERATOR"})
    void getDashboard_byModerator_returnsUnauthorized () throws Exception {

        mockMvc.perform(get("/admin/board"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "testAdmin", roles = {"ADMIN"})
    void getDashboard_byLoggedAdmin_returnsCorrectView () throws Exception {

        mockMvc.perform(get("/admin/board"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/board"));
                //.andExpect(content().string(containsString("testAdmin")));
    }

    @Test
    @WithAnonymousUser
    void getUsers_byAnonymous_returnsUnauthorized () throws Exception {

        mockMvc.perform(get("/admin/view"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithMockUser
    void getUsers_byLoggedUser_returnsUnauthorized () throws Exception {

        mockMvc.perform(get("/admin/view"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "testModerator", roles = {"MODERATOR"})
    void getUsers_byModerator_returnsUnauthorized () throws Exception {

        mockMvc.perform(get("/admin/view"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "testAdmin", roles = {"ADMIN"})
    void getUsers_byLoggedAdmin_returnsCorrectView () throws Exception {
        mockMvc.perform(get("/admin/view"))
                .andDo(print())
               .andExpect(status().isOk())
                .andExpect(view().name("admin/view"))
                .andExpect(model().attributeExists("users"));
                //.andExpect(content().string(containsString("testAdmin")));
    }

    @Test
    @WithMockUser
    void testPromoteUser() throws Exception {
        mockMvc.perform(post("/admin/moderators/3/promote").contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())).
              andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    void testDemoteUser() throws Exception {
        mockMvc.perform(post("/admin/users/3/deactivate").contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())).
                andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "testAdmin", roles = {"ADMIN"})
    void testPromoteUser_returnOk() throws Exception {
        mockMvc.perform(post("/admin/moderators/3/promote").contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/view"));
    }

    @Test
    @WithMockUser(username = "testAdmin", roles = {"ADMIN"})
    void testDemoteUser_returnOk() throws Exception {
        mockMvc.perform(post("/admin/users/3/deactivate").contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/view"));
    }

    @Test
    @WithMockUser(username = "testAdmin", roles = {"ADMIN"})
    void testDeleteAdminUser() throws Exception {
        var user = userTestData.createTestUser("test@abv.bg");
        mockMvc.perform(delete("/admin/users/delete/{id}",user.getId()).contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/view"));
        userRepository.delete(user);
    }
}