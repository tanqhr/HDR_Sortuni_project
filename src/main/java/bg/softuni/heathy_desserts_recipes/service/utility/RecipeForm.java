package bg.softuni.heathy_desserts_recipes.service.utility;

import bg.softuni.heathy_desserts_recipes.model.entity.ingredient.IngredientEntity;
import bg.softuni.heathy_desserts_recipes.model.entity.photo.PhotoEntity;
import bg.softuni.heathy_desserts_recipes.model.entity.photo.dto.PhotoDto;
import bg.softuni.heathy_desserts_recipes.model.entity.photo.dto.PhotoViewModel;
import bg.softuni.heathy_desserts_recipes.model.entity.recipe.RecipeEntity;
import bg.softuni.heathy_desserts_recipes.model.entity.recipe.dto.RecipeDto;
import bg.softuni.heathy_desserts_recipes.model.entity.user.UserEntity;
import bg.softuni.heathy_desserts_recipes.service.RecipeService;
import bg.softuni.heathy_desserts_recipes.service.TempPhotoService;
import bg.softuni.heathy_desserts_recipes.service.UnitService;
import bg.softuni.heathy_desserts_recipes.service.UserService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class RecipeForm {
    private final UnitService unitService;
    private final UserService userService;
    private final RecipeService recipeService;
    private final TempPhotoService tempPhotoService;
    private final CloudUtility cloudUtility;

    public RecipeForm(UnitService unitService, UserService userService, RecipeService recipeService, TempPhotoService tempPhotoService, CloudUtility cloudUtility) {
        this.unitService = unitService;
        this.userService = userService;
        this.recipeService = recipeService;
        this.tempPhotoService = tempPhotoService;
        this.cloudUtility = cloudUtility;
    }

    public List<String> getDistinctUnitNames () {

        return this.unitService.getDistinctUnitNames();
    }

    public void process (RecipeDto recipeDto) {

        final UUID tempRecipeId = recipeDto.getTempRecipeId();
        final Long primaryPhotoId = recipeDto.getPrimaryPhotoId();

        this.tempPhotoService.updatePrimaryFlag(tempRecipeId, primaryPhotoId);

        final List<PhotoViewModel> photoVMList = this.tempPhotoService.getListPhotoVM(tempRecipeId);
        recipeDto.setPhotoVMList(photoVMList);
    }
}
