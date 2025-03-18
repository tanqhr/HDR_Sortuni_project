package bg.softuni.heathy_desserts_recipes.model.entity.message;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


    @Getter
    @Setter
    @Accessors(chain = true)
    @Entity
    @Table(name = "messages")
    public class Message {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String name;

        @Column(nullable = false)
        private String email;

        @Column(nullable = false, columnDefinition = "TEXT")
        private String text;
}
