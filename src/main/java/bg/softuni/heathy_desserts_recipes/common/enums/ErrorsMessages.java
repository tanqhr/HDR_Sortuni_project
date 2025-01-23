package bg.softuni.heathy_desserts_recipes.common.enums;


import bg.softuni.heathy_desserts_recipes.common.messages.Message;

public enum ErrorsMessages {
    BAD_REQUEST ("400 BAD REQUEST! " +
            "You may have typed the address incorrectly or you may have used outdated link." +
            "If the problem persists, please contact us."),
    UNPROCESSABLE_ENTITY("402 UNPROCESSABLE ENTITY!"),
    FORBIDDEN ("403 FORBIDDEN! " +
            "Access to this resource on the server is denied!"),
    NOT_FOUND ("404 NOT FOUND! " +
            "The resource requested could not be found on this server!"),
    INTERNAL_SERVER_ERROR ("500 SERVER ERROR! " +
            "Oops something went wrong." +
            "Try to refresh this page or feel free to contact us if the problem persists.");

    public final String message;


    ErrorsMessages(String message) {

        this.message = message;
    }

    public String getMessage () {
        return this.message;
    }

    public Message getErrorMessage () {

        return Message.from(this.message);
    }
}
