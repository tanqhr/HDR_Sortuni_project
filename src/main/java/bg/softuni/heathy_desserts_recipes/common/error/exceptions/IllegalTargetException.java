package bg.softuni.heathy_desserts_recipes.common.error.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class IllegalTargetException extends ResponseStatusException {


    public IllegalTargetException (String reason) {

        super(HttpStatus.UNPROCESSABLE_ENTITY, reason);
    }
}
