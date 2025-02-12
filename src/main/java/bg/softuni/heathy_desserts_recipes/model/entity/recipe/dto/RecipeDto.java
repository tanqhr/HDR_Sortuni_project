package bg.softuni.heathy_desserts_recipes.model.entity.recipe.dto;

import bg.softuni.heathy_desserts_recipes.common.enums.VisibilityStatus;
import bg.softuni.heathy_desserts_recipes.model.entity.ingredient.dto.IngredientDto;
import bg.softuni.heathy_desserts_recipes.model.entity.photo.dto.PhotoViewModel;
import bg.softuni.heathy_desserts_recipes.model.entity.recipe.RecipeEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)

public class RecipeDto {

    private UUID tempRecipeId;

    @NotEmpty(message = "Title is required.")
    private String title;

    private List<PhotoViewModel> photoVMList;

    private Long primaryPhotoId;

    private List<@Valid IngredientDto> listIngredientDto;

    @PositiveOrZero(message = "Preparation hours should not be negative.")
    private int preparationHours;

    @PositiveOrZero(message = "Preparation minutes should not be negative.")
    private int preparationMinutes;

    @PositiveOrZero(message = "Cooking hours should not be negative.")
    private int cookingHours;

    @PositiveOrZero(message = "Cooking minutes should not be negative.")
    private int cookingMinutes;

    @Positive(message = "Servings should be positive number.")
    private int servings;

    @NotEmpty(message = "Description is required.")
    private String description;


    private VisibilityStatus visibilityStatus;

    private Long authorId;

    public RecipeDto() {

        this.photoVMList = new ArrayList<>();
        this.listIngredientDto = new ArrayList<>();
    }

    public RecipeEntity toEntity () {
        return new RecipeEntity()
                .setTitle(this.getTitle())
                .resetPreparationTime()
                .setPreparationTime(this.getPreparationMinutes())
                .resetCookingTime()
                .setCookingTime(this.getCookingMinutes())
                .setServings(this.getServings())
                .setDescription(this.getDescription())
                .setVisibilityStatus(this.getVisibilityStatus());
    }

}
