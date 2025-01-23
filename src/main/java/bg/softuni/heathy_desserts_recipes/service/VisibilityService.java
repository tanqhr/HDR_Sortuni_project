package bg.softuni.heathy_desserts_recipes.service;


import bg.softuni.heathy_desserts_recipes.model.repository.VisibilityRepository;
import org.springframework.stereotype.Service;

@Service
public class VisibilityService {

    private final VisibilityRepository visibilityRepository;

    public VisibilityService (VisibilityRepository visibilityRepository) {

        this.visibilityRepository = visibilityRepository;
    }
}
