package bg.softuni.heathy_desserts_recipes.web.controller.rest;


import bg.softuni.heathy_desserts_recipes.common.messages.Message;
import bg.softuni.heathy_desserts_recipes.model.entity.recipe.dto.RecipeShortDto;
import bg.softuni.heathy_desserts_recipes.model.security.CurrentUser;
import bg.softuni.heathy_desserts_recipes.service.RecipeService;
import bg.softuni.heathy_desserts_recipes.service.utility.RecipeForm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
public class RestRecipeController {

    private final RecipeForm recipeForm;

    private final RecipeService recipeService;

    public RestRecipeController(RecipeForm recipeForm, RecipeService recipeService) {

        this.recipeForm = recipeForm;
        this.recipeService = recipeService;
    }

    @GetMapping("/api/recipes/isAvailable/{title}")
    public Boolean recipeExists (@PathVariable String title,
                                 @AuthenticationPrincipal CurrentUser author) {

        return recipeForm.isAvailableRecipeTitleForAuthor(title, author);
    }

    @PostMapping("api/users/like/{id}")
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


    @GetMapping("api/recipes")
    public ResponseEntity<List<RecipeShortDto>> getAllRecipes() {
        return ResponseEntity.
                ok(recipeService.getAllRecipes());
    }


    @GetMapping("/api/recipes/{id}")
    public ResponseEntity<RecipeShortDto> getRecipeById(@PathVariable("id") Long recipeId) {
        Optional<RecipeShortDto> theRecipe = recipeService.findRecipeById(recipeId);

        return
                theRecipe.
                        map(ResponseEntity::ok).
                        orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/api/recipes/{id}")
    public ResponseEntity<RecipeShortDto> deleteRecipeById(@PathVariable("id") Long recipeId) {
        recipeService.deleteById(recipeId);

        return ResponseEntity.
                noContent().
                build();
    }




    @GetMapping("/api/users/allRecipes")
    public ResponseEntity<List<RecipeShortDto>> getAll() {
        return ResponseEntity.ok(recipeService.getAllRecipes());
    }


}
