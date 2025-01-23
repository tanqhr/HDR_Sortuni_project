package bg.softuni.heathy_desserts_recipes.common.error.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotAuthorizedException extends ResponseStatusException {

    public NotAuthorizedException (String reason) {

        super(HttpStatus.FORBIDDEN, reason);
    }
}
