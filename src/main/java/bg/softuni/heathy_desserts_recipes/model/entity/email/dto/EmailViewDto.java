package bg.softuni.heathy_desserts_recipes.model.entity.email.dto;

import bg.softuni.heathy_desserts_recipes.model.entity.email.Email;
import bg.softuni.heathy_desserts_recipes.model.entity.recipe.RecipeEntity;
import bg.softuni.heathy_desserts_recipes.model.entity.recipe.dto.RecipeShortDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class EmailViewDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(unique = true, nullable = false)
    private String email;



    public static EmailViewDto fromEntity (Email email) {

        return new EmailViewDto()
                .setId(email.getId())
                .setEmail(email.getEmail());
    }

}
