package bg.softuni.heathy_desserts_recipes.model.entity.recipe.dto;


import bg.softuni.heathy_desserts_recipes.model.entity.photo.PhotoEntity;
import bg.softuni.heathy_desserts_recipes.model.entity.recipe.RecipeEntity;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
public class RecipeAdd {

    private Long entityId;

    private String title;

    private String authorName;

    private Long authorId;

    private LocalDateTime lastUpdated;

    private String coverPhotoUrl;

    @Column
    private Integer cookingTime;

    public static RecipeAdd fromEntity (RecipeEntity entity) {

        return new RecipeAdd()
                .setEntityId(entity.getId())
                .setTitle(entity.getTitle())
                .setAuthorName(entity.getAuthor().getUsername())
                .setAuthorId(entity.getAuthor().getId())
                .setLastUpdated(entity.getLastUpdated())
                .setCookingTime(entity.getCookingTime())
                .setCoverPhotoUrl(entity.getPhotos().isEmpty()
                        ? "/images/system/no_photo.webp"
                        : entity.getPhotos().stream()
                        .filter(PhotoEntity::isPrimary)
                        .findFirst()
                        .orElse(entity.getPhotos().get(0)).getUrl())
                ;
    }

}
