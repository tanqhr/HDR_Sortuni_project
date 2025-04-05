package bg.softuni.heathy_desserts_recipes.web.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
class HomeControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithAnonymousUser
    void getHome_withAnonymousUser_returnsCorrectView () throws Exception {

       mockMvc.perform(get("/"))
               .andExpect(status().isOk())
               .andExpect(view().name("home"))
                .andExpect(content().string(not(containsString("TestUser"))));
    }

    @Test
    @WithMockUser(value = "TestUser")
   void getHome_withLoggedUser_returnsCorrectView () throws Exception {

        mockMvc.perform(get("/"))
               .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(content().string(containsString("TestUser")));
   }

    @Test
    void testGetContactMustReturnRightView() throws Exception {
        mockMvc.perform(get("/contact"))
                .andExpect(view().name("contact"));
    }

    @Test
    void testGetAboutMustReturnRightView() throws Exception {
        mockMvc.perform(get("/about"))
                .andExpect(view().name("about"));
    }

    @Test
    void testGetTermsMustReturnRightView() throws Exception {
        mockMvc.perform(get("/terms"))
                .andExpect(view().name("terms"));
    }

    @Test
    void testGetFaqMustReturnRightView() throws Exception {
        mockMvc.perform(get("/faq"))
                .andExpect(view().name("faq"));
    }
}