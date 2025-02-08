package bg.softuni.heathy_desserts_recipes.service.utility;

import bg.softuni.heathy_desserts_recipes.service.UnitService;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class RecipeForm {
    private final UnitService unitService;

    public RecipeForm(UnitService unitService) {
        this.unitService = unitService;
    }

    public List<String> getDistinctUnitNames () {

        return this.unitService.getDistinctUnitNames();
    }
}
