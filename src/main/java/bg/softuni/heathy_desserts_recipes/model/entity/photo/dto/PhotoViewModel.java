package bg.softuni.heathy_desserts_recipes.model.entity.photo.dto;


import bg.softuni.heathy_desserts_recipes.model.entity.photo.PhotoEntity;
import bg.softuni.heathy_desserts_recipes.model.entity.photo.TempPhotoEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class PhotoViewModel {

    private Long id;

    private Long tempId;

    private boolean isPrimary;

    private String url;

    private String description;

    private String filename;

    private Long recipeId;

    private UUID tempRecipeId;

    public static PhotoViewModel fromTempEntity (TempPhotoEntity tempPhotoEntity) {

        return new PhotoViewModel()
                .setTempId(tempPhotoEntity.getId())
                .setPrimary(tempPhotoEntity.isPrimary())
                .setUrl(tempPhotoEntity.getUrl())
                .setDescription(tempPhotoEntity.getDescription())
                .setFilename(tempPhotoEntity.getFilename())
                .setTempRecipeId(tempPhotoEntity.getTempRecipeId());
    }

    public static PhotoViewModel fromEntity (PhotoEntity entity) {

        return new PhotoViewModel()
                .setId(entity.getId())
                .setPrimary(entity.isPrimary())
                .setUrl(entity.getUrl())
                .setDescription(entity.getDescription())
                .setFilename(entity.getFilename())
                .setRecipeId(entity.getRecipe().getId());
    }
}
