package bg.softuni.heathy_desserts_recipes.model.entity.ingredient.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class IngredientDto {

    @NotBlank(message = "Ingredient name should not be blank")
    private String ingredientName;

    private String quantity;

    private String unitName;

}
