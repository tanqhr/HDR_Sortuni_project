package bg.softuni.heathy_desserts_recipes.model.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class RegistrationUserEvent extends ApplicationEvent {
        private Long userId;

        public RegistrationUserEvent(Object source) {
            super(source);
        }



}
