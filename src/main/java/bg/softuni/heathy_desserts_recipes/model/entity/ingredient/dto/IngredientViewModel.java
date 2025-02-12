package bg.softuni.heathy_desserts_recipes.model.entity.ingredient.dto;


import bg.softuni.heathy_desserts_recipes.model.entity.ingredient.IngredientEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class IngredientViewModel {

    private String ingredientName;

    private String quantity;

    private String unitName;

    public static IngredientViewModel fromEntity (IngredientEntity entity) {

        return new IngredientViewModel()
                .setIngredientName(entity.getIngredientName().getName())
                .setQuantity(entity.getQuantity())
                .setUnitName(entity.getUnit().getName());
    }

}
