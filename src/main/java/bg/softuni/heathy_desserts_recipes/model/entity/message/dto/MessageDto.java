package bg.softuni.heathy_desserts_recipes.model.entity.message.dto;


import jakarta.persistence.Column;
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
public class MessageDto {
    @NotNull
    private String name;
    @NotNull
    @Email
    private String email;
    @NotNull
    private String text;

    @Override
    public String toString() {
        return "MessageDto{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
