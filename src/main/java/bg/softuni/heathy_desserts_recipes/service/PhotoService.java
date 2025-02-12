package bg.softuni.heathy_desserts_recipes.service;


import bg.softuni.heathy_desserts_recipes.model.entity.photo.PhotoEntity;
import bg.softuni.heathy_desserts_recipes.model.repository.PhotoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhotoService {


    private final PhotoRepository photoRepository;

    public PhotoService(PhotoRepository photoRepository) {

        this.photoRepository = photoRepository;
    }


    public List<PhotoEntity> saveAllAndFlush (List<PhotoEntity> photoEntities) {

        return this.photoRepository.saveAllAndFlush(photoEntities);
    }
}
