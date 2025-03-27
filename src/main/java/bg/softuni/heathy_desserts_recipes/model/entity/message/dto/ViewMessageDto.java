package bg.softuni.heathy_desserts_recipes.model.entity.message.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ViewMessageDto {

    private Long id;

    private String name;


    private String email;


    private String text;
}