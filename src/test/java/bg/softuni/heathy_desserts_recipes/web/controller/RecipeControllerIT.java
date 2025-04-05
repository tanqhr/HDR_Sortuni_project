package bg.softuni.heathy_desserts_recipes.web.controller;

import bg.softuni.heathy_desserts_recipes.model.entity.recipe.RecipeEntity;
import bg.softuni.heathy_desserts_recipes.model.entity.recipe.dto.RecipeAdd;
import bg.softuni.heathy_desserts_recipes.model.entity.recipe.dto.RecipeDto;
import bg.softuni.heathy_desserts_recipes.model.entity.recipe.dto.RecipeViewModel;
import bg.softuni.heathy_desserts_recipes.model.entity.user.UserEntity;
import bg.softuni.heathy_desserts_recipes.model.repository.RecipeRepository;
import bg.softuni.heathy_desserts_recipes.model.repository.UserRepository;
import bg.softuni.heathy_desserts_recipes.model.security.CurrentUser;
import bg.softuni.heathy_desserts_recipes.service.RecipeService;
import bg.softuni.heathy_desserts_recipes.testUtils.DBTestData;
import bg.softuni.heathy_desserts_recipes.testUtils.UserTestData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.FlashMap;

import java.io.File;
import java.nio.file.Files;
import java.time.Duration;
import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class RecipeControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DBTestData dbTestData;

    @Autowired
    private UserTestData userTestData;

    @Test
    @WithAnonymousUser
    void getAddRecipe_byAnonymous_redirectsToLogin() throws Exception {

        mockMvc.perform(get("/recipes/add"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithUserDetails(value = "taico@abv.bg")
    void getAddRecipe_byLoggedUser_returnsProperView() throws Exception {

        mockMvc.perform(get("/recipes/add"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("units"))
                .andExpect(model().attributeExists("recipeDto"))
                .andExpect(view().name("add"));
    }

    @Test
    @WithAnonymousUser
    void postAddRecipe_byAnonymousUser_redirectsToLogin() throws Exception {

        mockMvc.perform(post("/recipes/add")
                        .param("title", "testTitle")
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }


    @Test
    @WithAnonymousUser
    void getAll_byAnonymousUser_returnsRedirect() throws Exception {

        mockMvc.perform(get("/recipes/all"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }


    @Test
    @WithUserDetails(
            value = "taico@abv.bg",
            userDetailsServiceBeanName = "userDetailsService",
            setupBefore = TestExecutionEvent.TEST_EXECUTION
    )
    void getAll_byLoggedUser_returnsOK() throws Exception {

        mockMvc.perform(get("/recipes/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("all"))
                .andExpect(model().attributeExists("allRecipes"));
    }


    @Test
    @WithAnonymousUser
    void getOwn_byAnonymous_redirectsToLogin() throws Exception {
        mockMvc.perform(get("/recipes/mine"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithUserDetails(
            value = "taico@abv.bg",
            userDetailsServiceBeanName = "userDetailsService",
            setupBefore = TestExecutionEvent.TEST_EXECUTION
    )
    void getMine_byLoggedUser_returnsOK() throws Exception {

         mockMvc.perform(get("/recipes/mine"))
                .andExpect(status().isOk())
                .andExpect(view().name("all"))
                .andExpect(model().attributeExists("allRecipes"));


    }


    @Test
    @WithAnonymousUser
    void getByRecipeId_byAnonymous_redirectsToLogin() throws Exception {

        mockMvc.perform(get("/recipes/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }


    @Test
    @WithUserDetails(
            value = "taico@abv.bg",
            userDetailsServiceBeanName = "userDetailsService",
            setupBefore = TestExecutionEvent.TEST_EXECUTION
    )
    void postAddRecipe_byLoggedUserWithCompleteData_returnsRedirectToNewlyCreated() throws Exception {
        RecipeDto recipeBM = new RecipeDto()
                .setAuthorId(1L)
                .setTempRecipeId(UUID.randomUUID());

        mockMvc.perform(post("/recipes/add")
                        .param("title", "veganCream")
                        .param("preparationMinutes", "15")
                        .param("cookingMinutes", "20")
                        .param("servings", "6")
                        .param("listIngredientBM[0].ingredientName", "almond milk")
                        .param("listIngredientBM[0].quantity", "200")
                        .param("listIngredientBM[0].unitName", "ml.")
                        .param("listIngredientBM[1].ingredientName", "agave")
                        .param("listIngredientBM[1].quantity", "100")
                        .param("listIngredientBM[1].unitName", "g.")
                        .param("listIngredientBM[2].ingredientName", "coconut butter")
                        .param("listIngredientBM[2].quantity", "50")
                        .param("listIngredientBM[2].unitName", "ml.")
                        .param("description",
                                "mix all.")
                        .flashAttr("recipeBM", recipeBM)
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeCount(0))
                .andExpect(redirectedUrlPattern("/recipes/{[0-9]*}"));


    }

    @Test
    @WithUserDetails(value = "kosta@abv.bg", userDetailsServiceBeanName = "userDetailsService")
    public void testDeleteRecipe() throws Exception {

        Optional<UserEntity> user = userRepository.findByEmail("kosta@abv.bg");
        var actualRecipe = recipeRepository.save(new RecipeEntity().setTitle("Vegan Cream").setDescription("mix").
                setServings(3).setCookingTime(Duration.ofMinutes(20)).setPreparationTime((Duration.ofMinutes(20))).setAuthor(user.get()));
        mockMvc.perform(delete("/recipes/delete/{id}", actualRecipe.getId()).contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

    }

}

