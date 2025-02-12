package bg.softuni.heathy_desserts_recipes.service;


import bg.softuni.heathy_desserts_recipes.common.error.exceptions.RecipeNotFoundException;
import bg.softuni.heathy_desserts_recipes.model.entity.recipe.RecipeEntity;
import bg.softuni.heathy_desserts_recipes.model.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {

        this.recipeRepository = recipeRepository;
    }

    public boolean isAvailableRecipeTitle(String recipeTitle, Long principalId) {

        return !this.recipeRepository.existsByAuthor_IdAndTitle(principalId, recipeTitle);
    }
    public RecipeEntity findById (long recipeId) {

        return this.recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RecipeNotFoundException("No recipe with id %d found!".formatted(recipeId)));
    }

    public RecipeEntity save (RecipeEntity recipeEntity) {

        return recipeRepository.saveAndFlush(recipeEntity);
    }


    public void saveAndFlush (RecipeEntity recipeEntity) {

        this.recipeRepository.saveAndFlush(recipeEntity);
    }

}