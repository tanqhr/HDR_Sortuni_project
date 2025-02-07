package bg.softuni.heathy_desserts_recipes.common.error.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class RecipeNotFoundException extends ResponseStatusException {

    public RecipeNotFoundException(String reason) {

        super(HttpStatus.NOT_FOUND, reason);
    }


}
