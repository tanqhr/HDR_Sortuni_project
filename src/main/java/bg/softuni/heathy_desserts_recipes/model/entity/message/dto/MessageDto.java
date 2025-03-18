package bg.softuni.heathy_desserts_recipes.model.entity.message.dto;


import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MessageDto {

    private String name;

    private String email;

    private String text;


}
