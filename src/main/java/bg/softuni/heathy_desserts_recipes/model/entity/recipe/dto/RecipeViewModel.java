package bg.softuni.heathy_desserts_recipes.model.entity.recipe.dto;

import bg.softuni.heathy_desserts_recipes.common.enums.VisibilityStatus;
import bg.softuni.heathy_desserts_recipes.model.entity.ingredient.dto.IngredientViewModel;
import bg.softuni.heathy_desserts_recipes.model.entity.photo.dto.PhotoViewModel;
import bg.softuni.heathy_desserts_recipes.model.entity.recipe.RecipeEntity;
import bg.softuni.heathy_desserts_recipes.model.entity.user.dto.UserShortViewModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class RecipeViewModel {

    private Long id;

    private String title;

    private List<PhotoViewModel> listPhotoVM;

    private List<IngredientViewModel> listIngredientVM;

    private String preparationTime;

    private String cookingTime;

    private Integer servings;

    private List<UserShortViewModel> likes;

    private boolean isLiked;

    private String description;

    private UserShortViewModel author;

    private VisibilityStatus visibilityStatus;

    private LocalDateTime addedOn;

    private LocalDateTime lastUpdated;

    public static RecipeViewModel fromEntity (RecipeEntity entity) {
        return new RecipeViewModel()
                .setId(entity.getId())
                .setTitle(entity.getTitle())
                .setListPhotoVM(entity.getPhotos().stream()
                        .map(PhotoViewModel::fromEntity)
                        .toList())
                .setListIngredientVM(entity.getIngredients().stream()
                        .map(IngredientViewModel::fromEntity)
                        .toList())
                .setPreparationTime("%02d:%02d".formatted(
                        entity.getPreparationTime(),
                        entity.getPreparationTime()
                ))
                .setCookingTime("%02d:%02d".formatted(
                        entity.getCookingTime(),
                        entity.getCookingTime()
                ))
                .setServings(entity.getServings())
                .setLikes(entity.getLikes().stream()
                        .map(UserShortViewModel::fromEntity)
                        .toList())
                .setLiked(Boolean.FALSE)
                .setDescription(entity.getDescription())
                .setAuthor(UserShortViewModel.fromEntity(entity.getAuthor()))
                .setVisibilityStatus(entity.getVisibilityStatus())
                .setAddedOn(entity.getAddedOn())
                .setLastUpdated(entity.getLastUpdated());
    }

    public RecipeViewModel setLiked (boolean liked) {

        isLiked = liked;
        return this;
    }
}
