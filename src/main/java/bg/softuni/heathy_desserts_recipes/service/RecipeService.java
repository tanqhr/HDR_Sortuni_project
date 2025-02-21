package bg.softuni.heathy_desserts_recipes.service;


import bg.softuni.heathy_desserts_recipes.common.error.exceptions.RecipeNotFoundException;
import bg.softuni.heathy_desserts_recipes.model.entity.recipe.RecipeEntity;
import bg.softuni.heathy_desserts_recipes.model.repository.RecipeRepository;
import bg.softuni.heathy_desserts_recipes.model.security.CurrentUser;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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

    public RecipeEntity findById(long recipeId) {

        return this.recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RecipeNotFoundException("No recipe with id %d found!".formatted(recipeId)));
    }

    public RecipeEntity save(RecipeEntity recipeEntity) {

        return recipeRepository.saveAndFlush(recipeEntity);
    }


    public void saveAndFlush(RecipeEntity recipeEntity) {

        this.recipeRepository.saveAndFlush(recipeEntity);
    }

    public boolean isAuthor (CurrentUser currentUser, long recipeId) {

        return isAuthor(currentUser, findById(recipeId));
    }

    private static boolean isAuthor(CurrentUser currentUser, RecipeEntity recipeEntity) {

        return currentUser.getId().equals(recipeEntity.getAuthor().getId());
    }

    public boolean checkCanView(CurrentUser currentUser, Long recipeId) {

        final RecipeEntity recipeEntity = findById(recipeId);

        return checkCanView(currentUser, recipeEntity.getId());
    }

    public Boolean checkCanEdit(CurrentUser currentUser, long recipeId) {

        final RecipeEntity recipeEntity = findById(recipeId);

        if (isAuthor(currentUser, recipeEntity) || currentUser.isAdmin()) {

            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    public Boolean checkCanDelete(CurrentUser currentUser, long recipeId) {

        final RecipeEntity recipeEntity = findById(recipeId);

        if (isAuthor(currentUser, recipeEntity) || currentUser.isAdmin()) {

            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }
@Transactional
    public void deleteRecipe(Long id) {
        recipeRepository.deleteById(id);
    }
}

