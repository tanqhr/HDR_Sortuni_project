package bg.softuni.heathy_desserts_recipes.model.entity.photo.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class PhotoBM {

    @NotNull(message = "Please select file!")
    private MultipartFile fileData;

    @NotEmpty(message = "Photo description is required!")
    private String description;

    @NotNull
    private UUID tempRecipeId;

}
