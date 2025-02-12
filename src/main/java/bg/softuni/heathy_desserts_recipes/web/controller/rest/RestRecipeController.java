package bg.softuni.heathy_desserts_recipes.web.controller.rest;


import bg.softuni.heathy_desserts_recipes.common.messages.Message;
import bg.softuni.heathy_desserts_recipes.model.security.CurrentUser;
import bg.softuni.heathy_desserts_recipes.service.utility.RecipeForm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestRecipeController {

    private final RecipeForm recipeForm;

    public RestRecipeController(RecipeForm recipeForm) {

        this.recipeForm = recipeForm;
    }

    @GetMapping("/api/recipes/isAvailable/{title}")
    public Boolean recipeExists (@PathVariable String title,
                                 @AuthenticationPrincipal CurrentUser author) {

        return recipeForm.isAvailableRecipeTitleForAuthor(title, author);
    }

    @PostMapping("api/recipes/{id}/like")
    public ResponseEntity<Message> like (@PathVariable Long id,
                                         @AuthenticationPrincipal CurrentUser visitor) {

        final int likesCount = this.recipeForm.addLike(id, visitor);
        return ResponseEntity.status(HttpStatus.OK).body(Message.from(likesCount));
    }

    @PostMapping("api/recipes/{id}/unlike")
    public ResponseEntity<Message> unlike (@PathVariable Long id,
                                           @AuthenticationPrincipal CurrentUser visitor) {

        final int likesCount = this.recipeForm.removeLike(id, visitor);
        return ResponseEntity.status(HttpStatus.OK).body(Message.from(likesCount));
    }

}
