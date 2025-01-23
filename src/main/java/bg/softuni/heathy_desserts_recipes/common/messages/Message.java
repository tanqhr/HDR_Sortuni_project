package bg.softuni.heathy_desserts_recipes.common.messages;

import lombok.Getter;

@Getter
public class Message {

    private final String message;

    private Message (Object message) {

        this.message = message.toString();
    }

    public static Message from (Object message) {

        return new Message(message);
    }

}
