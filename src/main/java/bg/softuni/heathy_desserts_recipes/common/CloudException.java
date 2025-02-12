package bg.softuni.heathy_desserts_recipes.common;

import java.io.IOException;

public class CloudException extends RuntimeException {

    public CloudException(IOException e) {
        super(e.getMessage());
    }
}
