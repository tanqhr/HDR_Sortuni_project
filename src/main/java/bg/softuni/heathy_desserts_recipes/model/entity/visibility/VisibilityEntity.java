package bg.softuni.heathy_desserts_recipes.model.entity.visibility;

import bg.softuni.heathy_desserts_recipes.common.enums.VisibilityStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "visibility")
public class VisibilityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "visibilityStatus")
    private VisibilityStatus visibilityStatus;


    public VisibilityEntity (VisibilityStatus visibilityStatus) {

        this.visibilityStatus = visibilityStatus;
    }
}
