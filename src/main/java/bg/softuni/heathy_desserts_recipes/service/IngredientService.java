package bg.softuni.heathy_desserts_recipes.service;

import bg.softuni.heathy_desserts_recipes.model.entity.ingredient.IngredientEntity;
import bg.softuni.heathy_desserts_recipes.model.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {

    IngredientRepository ingredientRepository;

    @Autowired
    public IngredientService(IngredientRepository ingredientRepository) {

        this.ingredientRepository = ingredientRepository;
    }

    public List<IngredientEntity> saveAllAndFlush (List<IngredientEntity> ingredientEntities) {

        return this.ingredientRepository.saveAllAndFlush(ingredientEntities);
    }
}
