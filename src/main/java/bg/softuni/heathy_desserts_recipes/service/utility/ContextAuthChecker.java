package bg.softuni.heathy_desserts_recipes.service.utility;


import bg.softuni.heathy_desserts_recipes.common.enums.ContextAuthority;
import bg.softuni.heathy_desserts_recipes.common.enums.ContextRole;
import bg.softuni.heathy_desserts_recipes.model.entity.user.UserEntity;
import bg.softuni.heathy_desserts_recipes.model.security.CurrentUser;
import bg.softuni.heathy_desserts_recipes.service.RecipeService;
import bg.softuni.heathy_desserts_recipes.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class ContextAuthChecker {

    private final RecipeService recipeService;
    private final UserService userService;

    public ContextAuthChecker(RecipeService recipeService, UserService userService) {

        this.recipeService = recipeService;
        this.userService = userService;
    }

    public Boolean check (ContextAuthority authority, CurrentUser currentUser, long recipeId) {

        switch (authority) {
            case VIEW -> {
                return this.recipeService.checkCanView(currentUser, recipeId);
            }
            case EDIT -> {
                return this.recipeService.checkCanEdit(currentUser, recipeId);
            }
            case DELETE -> {
                return this.recipeService.checkCanDelete(currentUser, recipeId);
            }

            default -> throw new IllegalStateException("Unexpected value: " + authority);
        }

    }

    public Boolean check (ContextRole contextRole, CurrentUser currentUser, long recipeId) {

        final UserEntity author = this.recipeService.findById(recipeId).getAuthor();

        switch (contextRole) {
            case AUTHOR -> {
                return author.getId().equals(currentUser.getId());
            }
            case FOLLOWER -> {

                return author.isFollowedBy(this.userService.findById(currentUser.getId()));
            }
            case BLOCKED -> {

                return author.hasBlocked(this.userService.findById(currentUser.getId()));
            }

            default -> throw new IllegalStateException("Unexpected value: " + contextRole);
        }
    }
}
