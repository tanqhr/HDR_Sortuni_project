package bg.softuni.heathy_desserts_recipes.model.repository;

import bg.softuni.heathy_desserts_recipes.model.entity.photo.TempPhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TempPhotoRepository extends JpaRepository<TempPhotoEntity, Long> {

    List<TempPhotoEntity> findAllByTempRecipeId (UUID tempRecipeId);
}
