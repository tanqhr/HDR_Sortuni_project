package bg.softuni.heathy_desserts_recipes.model.repository;


import bg.softuni.heathy_desserts_recipes.model.entity.ingredient.IngredientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<IngredientEntity, Long> {

}
