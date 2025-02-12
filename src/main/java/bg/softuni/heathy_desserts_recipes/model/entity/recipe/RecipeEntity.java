package bg.softuni.heathy_desserts_recipes.model.entity.recipe;

import bg.softuni.heathy_desserts_recipes.common.enums.VisibilityStatus;
import bg.softuni.heathy_desserts_recipes.model.entity.ingredient.IngredientEntity;
import bg.softuni.heathy_desserts_recipes.model.entity.photo.PhotoEntity;
import bg.softuni.heathy_desserts_recipes.model.entity.user.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "recipes")
public class RecipeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @OneToMany(
            mappedBy = "recipe",
            cascade = CascadeType.ALL
    )
    private List<PhotoEntity> photos;

    @OneToMany(
            mappedBy = "recipe",
            cascade = CascadeType.ALL
    )
    private List<IngredientEntity> ingredients;

    @Column
    private Integer preparationTime;

    @Column
    private Integer cookingTime;

    @Column
    private Integer servings;

    @ManyToMany(mappedBy = "likedRecipes")
    private Set<UserEntity> likes;

    @Column
    private String description;

    @Enumerated(EnumType.STRING)
    private VisibilityStatus visibilityStatus;

    @ManyToOne
    private UserEntity author;

    @CreationTimestamp
    private LocalDateTime addedOn;

    @UpdateTimestamp
    private LocalDateTime lastUpdated;

    public RecipeEntity () {

        this.likes = new HashSet<>();
        this.photos = new ArrayList<>();
        this.ingredients = new ArrayList<>();

        this.preparationTime = 0;
        this.cookingTime = 0;
    }

    public RecipeEntity resetPreparationTime () {

        this.preparationTime = 0;

        return this;
    }




    public RecipeEntity resetCookingTime () {

        this.cookingTime = 0;

        return this;
    }

    public RecipeEntity addPhotos (List<PhotoEntity> photos) {

        this.photos.addAll(photos);
        return this;
    }

    public RecipeEntity addProducts (List<IngredientEntity> products) {

        this.ingredients.addAll(products);
        return this;
    }

    public boolean addLike(UserEntity userEntity) {

        return this.likes.add(userEntity);
    }

    public boolean removeLike (UserEntity userEntity) {

        return this.likes.remove(userEntity);
    }
}
