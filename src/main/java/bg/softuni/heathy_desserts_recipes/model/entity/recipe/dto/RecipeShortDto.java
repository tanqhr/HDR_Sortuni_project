package bg.softuni.heathy_desserts_recipes.model.entity.recipe.dto;

import bg.softuni.heathy_desserts_recipes.model.entity.recipe.RecipeEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Getter
@Setter
@Accessors(chain = true)
public class RecipeShortDto {


        private Long id;

        private String title;

        private String authorName;

        private String description;


        public static RecipeShortDto fromEntity (RecipeEntity entity) {

            return new RecipeShortDto()
                    .setId(entity.getId())
                    .setTitle(entity.getTitle())
                    .setAuthorName(entity.getAuthor().getUsername())
                    .setDescription(entity.getDescription());
        }
}
