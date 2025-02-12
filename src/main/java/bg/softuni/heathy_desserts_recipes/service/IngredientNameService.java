package bg.softuni.heathy_desserts_recipes.service;


import bg.softuni.heathy_desserts_recipes.model.entity.ingredient_name.IngredientNameEntity;
import bg.softuni.heathy_desserts_recipes.model.repository.IngredientNameRepository;
import org.springframework.stereotype.Service;

@Service
public class IngredientNameService {

    private final IngredientNameRepository ingredientNameRepository;

    public IngredientNameService(IngredientNameRepository ingredientNameRepository) {

        this.ingredientNameRepository = ingredientNameRepository;
    }

    public IngredientNameEntity getOrCreateByName (String name) {

        if (this.ingredientNameRepository.findByName(name).isEmpty()) {
            final IngredientNameEntity entity = new IngredientNameEntity(name);
            ingredientNameRepository.saveAndFlush(entity);
        }

        return this.ingredientNameRepository.findByName(name).get();
    }
}
