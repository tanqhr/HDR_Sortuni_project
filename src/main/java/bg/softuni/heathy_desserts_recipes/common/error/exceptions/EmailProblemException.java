package bg.softuni.heathy_desserts_recipes.common.error.exceptions;

public class EmailProblemException extends RuntimeException {
    public EmailProblemException() {
        super("Problem sending email! Please excuse us!");
    }
}
