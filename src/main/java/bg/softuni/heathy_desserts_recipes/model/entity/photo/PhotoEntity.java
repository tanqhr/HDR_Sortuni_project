package bg.softuni.heathy_desserts_recipes.model.entity.photo;

import bg.softuni.heathy_desserts_recipes.model.entity.recipe.RecipeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "photos")
public class PhotoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String publicId;

    @Column
    private String url;

    @Column
    private String description;

    @Column
    private String filename;

    @Column
    private boolean isPrimary;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private RecipeEntity recipe;

}
