package bg.softuni.heathy_desserts_recipes.service.utility;

import bg.softuni.heathy_desserts_recipes.model.entity.ingredient.IngredientEntity;
import bg.softuni.heathy_desserts_recipes.model.entity.ingredient.dto.IngredientDto;
import bg.softuni.heathy_desserts_recipes.model.entity.ingredient.dto.IngredientViewModel;
import bg.softuni.heathy_desserts_recipes.model.entity.photo.PhotoEntity;
import bg.softuni.heathy_desserts_recipes.model.entity.photo.dto.PhotoDto;
import bg.softuni.heathy_desserts_recipes.model.entity.photo.dto.PhotoViewModel;
import bg.softuni.heathy_desserts_recipes.model.entity.recipe.RecipeEntity;
import bg.softuni.heathy_desserts_recipes.model.entity.recipe.dto.RecipeAdd;
import bg.softuni.heathy_desserts_recipes.model.entity.recipe.dto.RecipeDto;
import bg.softuni.heathy_desserts_recipes.model.entity.recipe.dto.RecipeViewModel;
import bg.softuni.heathy_desserts_recipes.model.entity.user.UserEntity;
import bg.softuni.heathy_desserts_recipes.model.security.CurrentUser;
import bg.softuni.heathy_desserts_recipes.service.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Component
public class RecipeForm {
    private final UnitService unitService;
    private final PhotoService photoService;
    private final UserService userService;
    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final IngredientNameService ingredientNameService;
    private final TempPhotoService tempPhotoService;
    private final CloudUtility cloudUtility;

    public RecipeForm(UnitService unitService, PhotoService photoService, UserService userService, RecipeService recipeService, IngredientService ingredientService, IngredientNameService ingredientNameService, TempPhotoService tempPhotoService, CloudUtility cloudUtility) {
        this.unitService = unitService;
        this.photoService = photoService;
        this.userService = userService;
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.ingredientNameService = ingredientNameService;
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

    public RecipeViewModel getRecipeVMForUser (Long id, CurrentUser currentUser) {

        final UserEntity userEntity = this.userService.findById(currentUser.getId());
        final RecipeEntity recipeEntity = this.recipeService.findById(id);

        return RecipeViewModel.fromEntity(recipeEntity);
    }
    public Long save (RecipeDto recipeDto, Long authorId) {

        RecipeEntity newRecipeEntity = recipeDto.toEntity();

        final UserEntity author = this.userService.findById(authorId);
        newRecipeEntity.setAuthor(author);

        final RecipeEntity savedRecipeEntity = this.recipeService.save(newRecipeEntity);

        final List<IngredientEntity> ingredientEntities = recipeDto.getListIngredientDto()
                .stream()
                .map(this::IngredientEntityFromDto)
                .map(ingredientEntity -> ingredientEntity.setRecipe(savedRecipeEntity))
                .toList();

        final List<PhotoDto> photoDTOList = this.tempPhotoService.getListPhotoDto(recipeDto.getTempRecipeId());

        final List<PhotoEntity> photoEntities = this.cloudUtility
                .moveAll(photoDTOList, savedRecipeEntity.getId())
                .stream()
                .map(PhotoDto::toEntity)
                .map(photoEntity -> photoEntity.setRecipe(savedRecipeEntity))
                .toList();

        final RecipeEntity completeRecipeEntity = savedRecipeEntity
                .setIngredients(this.ingredientService.saveAllAndFlush(ingredientEntities))
                .setPhotos(this.photoService.saveAllAndFlush(photoEntities));

        return this.recipeService.save(completeRecipeEntity).getId();
    }

    private IngredientEntity IngredientEntityFromDto (@Valid IngredientDto dto) {

        return new IngredientEntity()
                .setIngredientName(ingredientNameService.getOrCreateByName(dto.getIngredientName()))
                .setQuantity(dto.getQuantity())
                .setUnit(unitService.getOrCreateByName(dto.getUnitName()));
    }
    public int addLike (Long recipeId, CurrentUser visitor) {

        final UserEntity visitorEntity = this.userService.findById(visitor.getId());
        final RecipeEntity recipeEntity = this.recipeService.findById(recipeId);

        if (!recipeEntity.addLike(visitorEntity) || !visitorEntity.likeRecipe(recipeEntity)) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        userService.saveAndFlush(visitorEntity);
        recipeService.saveAndFlush(recipeEntity);

        return recipeEntity.getLikes().size();
    }

    public int removeLike (Long recipeId, CurrentUser visitor) {

        final UserEntity visitorEntity = this.userService.findById(visitor.getId());
        final RecipeEntity recipeEntity = this.recipeService.findById(recipeId);

        if (!recipeEntity.removeLike(visitorEntity) || !visitorEntity.unlikeRecipe(recipeEntity)) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        userService.saveAndFlush(visitorEntity);
        recipeService.saveAndFlush(recipeEntity);

        return recipeEntity.getLikes().size();
    }

    public boolean isAvailableRecipeTitleForAuthor (String title, CurrentUser author) {

        return recipeService.isAvailableRecipeTitle(title, author.getId());
    }

    public List<RecipeAdd> getAllRecipes (CurrentUser currentUser) {
        return this.recipeService.getAll(currentUser);
    }

    public List<RecipeAdd> getOwnRecipes (CurrentUser currentUser) {

        return this.recipeService.getOwn(currentUser);
    }

}



