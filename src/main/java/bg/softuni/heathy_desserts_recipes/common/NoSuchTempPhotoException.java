package bg.softuni.heathy_desserts_recipes.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NoSuchTempPhotoException extends ResponseStatusException {

    public NoSuchTempPhotoException(String reason) {

        super(HttpStatus.NOT_FOUND, reason);
    }
}
