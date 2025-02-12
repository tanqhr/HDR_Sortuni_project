package bg.softuni.heathy_desserts_recipes.model.repository;

import bg.softuni.heathy_desserts_recipes.model.entity.ingredient_name.IngredientNameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IngredientNameRepository extends JpaRepository<IngredientNameEntity, Long> {

    Optional<IngredientNameEntity> findByName (String name);

}
