package bg.softuni.heathy_desserts_recipes.model.entity.user;

import bg.softuni.heathy_desserts_recipes.model.entity.recipe.RecipeEntity;
import bg.softuni.heathy_desserts_recipes.model.entity.role.RoleEntity;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Cascade;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Accessors(chain = true)
@Entity

@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,name = "first_name",length = 30)
    private String firstName;

    @Column(nullable = false,name = "last_name",length = 30)
    private String lastName;

    @Column(nullable = false,length = 30)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column
    private boolean active;


    @ManyToMany(fetch = FetchType.EAGER)
    private List<RoleEntity> roles;

    @ManyToMany()
    @JoinTable(name = "recipes_likes",
            joinColumns = @JoinColumn(name = "user_id"),
           inverseJoinColumns = @JoinColumn(name = "recipe_id"))


    private List<RecipeEntity> likedRecipes;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<RecipeEntity> recipes;


   public UserEntity () {
       this.roles = new ArrayList<>();
       this.likedRecipes = new ArrayList<>();

    }

    public UserEntity(String firstName, String lastName, String username, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public UserEntity addRoles (RoleEntity... roles) {

        this.roles.addAll(List.of(roles));
        return this;
    }

    public boolean removeRoles (RoleEntity... roles) {

        return this.roles.removeAll(List.of(roles));
    }



    public boolean likeRecipe (RecipeEntity recipe) {

        return this.likedRecipes.add(recipe);
    }

    public boolean unlikeRecipe (RecipeEntity recipe) {

        return this.likedRecipes.remove(recipe);
    }




}
