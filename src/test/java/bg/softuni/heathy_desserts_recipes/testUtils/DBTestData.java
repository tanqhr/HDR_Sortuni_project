package bg.softuni.heathy_desserts_recipes.testUtils;

import bg.softuni.heathy_desserts_recipes.model.entity.recipe.RecipeEntity;
import bg.softuni.heathy_desserts_recipes.model.entity.user.UserEntity;
import bg.softuni.heathy_desserts_recipes.model.repository.EmailRepository;
import bg.softuni.heathy_desserts_recipes.model.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;

@Component
public class DBTestData {

    public static final long TEST_RECIPE_ID = 1L;

    @Autowired
    private RecipeRepository recipeRepository;
    
    @Autowired
    private EmailRepository emailRepository;


    public RecipeEntity createRecipe(UserEntity user) {
        RecipeEntity recipeEntity = new RecipeEntity();
        recipeEntity.setTitle("Vegan Cream").setDescription("mix").
                               setServings(3).setCookingTime(Duration.ofMinutes(20)).setPreparationTime((Duration.ofMinutes(20))).setAuthor(user);

        return recipeRepository.save(recipeEntity);
    }


    public void cleanUp() {
        recipeRepository.deleteAll();
        emailRepository.deleteAll();
    }



    public RecipeEntity getRecipeById(Long id) {
        return recipeRepository.getReferenceById(id);
    }
}


