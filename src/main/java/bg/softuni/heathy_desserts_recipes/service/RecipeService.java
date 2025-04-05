package bg.softuni.heathy_desserts_recipes.service;


import bg.softuni.heathy_desserts_recipes.common.error.exceptions.RecipeNotFoundException;
import bg.softuni.heathy_desserts_recipes.model.entity.recipe.RecipeEntity;
import bg.softuni.heathy_desserts_recipes.model.entity.recipe.dto.RecipeAdd;
import bg.softuni.heathy_desserts_recipes.model.entity.recipe.dto.RecipeDto;
import bg.softuni.heathy_desserts_recipes.model.entity.recipe.dto.RecipeShortDto;
import bg.softuni.heathy_desserts_recipes.model.entity.recipe.dto.RecipeViewModel;
import bg.softuni.heathy_desserts_recipes.model.entity.user.UserEntity;
import bg.softuni.heathy_desserts_recipes.model.entity.user.dto.UserShortViewModel;
import bg.softuni.heathy_desserts_recipes.model.repository.RecipeRepository;
import bg.softuni.heathy_desserts_recipes.model.repository.UserRepository;
import bg.softuni.heathy_desserts_recipes.model.security.CurrentUser;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository, UserRepository userRepository) {

        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
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



    private static boolean isAuthor(CurrentUser currentUser, RecipeEntity recipeEntity) {

        return currentUser.getId().equals(recipeEntity.getAuthor().getId());
    }


    public Boolean checkCanAdd(CurrentUser currentUser) {

        Optional<UserEntity> userEntity = userRepository.findById(currentUser.getId());

        if (userEntity.get().isActive()) {

            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    public Boolean checkCanDelete(CurrentUser currentUser, long recipeId) {

        final RecipeEntity recipeEntity = findById(recipeId);

        if (isAuthor(currentUser, recipeEntity) || currentUser.isAdmin() || currentUser.isModerator()) {

            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }
@Transactional
    public void deleteRecipe(Long id) {
        recipeRepository.deleteById(id);
    }

    public List<String> findAllRecipeTitlesByAuthor (UserEntity author) {
        return this.recipeRepository
                .findAllByAuthor(author)
                .stream()
                .map(RecipeEntity::getTitle)
                .toList();
    }




    public List<RecipeAdd> getAll (CurrentUser currentUser) {

        return this.recipeRepository.findAll().stream()
                .map(RecipeAdd::fromEntity)
                .toList();
    }

    public List<RecipeAdd> getOwn (CurrentUser currentUser) {

        return this.recipeRepository.findByAuthor_Id(currentUser.getId()).stream()
                .map(RecipeAdd::fromEntity)
                .toList();
    }


    private UserEntity createNewAuthor(String authorName) {
        return userRepository.save(new UserEntity().setUsername(authorName));
    }



    @Transactional
    public void like(Long id, long recipeId) {
        Optional<UserEntity> userOpt = userRepository.findById(id);

        if (userOpt.isEmpty()) {
            return;
        }

        Optional<RecipeEntity> recipeOpt = recipeRepository.findById(recipeId);
        if (recipeOpt.isEmpty()|| userOpt.get().getUsername().equals(recipeOpt.get().getAuthor().getUsername())) {
            return;
        }
        if (!userOpt.get().getLikedRecipes().contains(recipeOpt.get())) {
            userOpt.get().likeRecipe(recipeOpt.get());
            userRepository.save(userOpt.get());
        }

        else {
            userOpt.get().unlikeRecipe(recipeOpt.get());
            userRepository.save(userOpt.get());
        }
    }


}




