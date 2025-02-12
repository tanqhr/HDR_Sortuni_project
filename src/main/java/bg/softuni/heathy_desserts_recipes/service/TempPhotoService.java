package bg.softuni.heathy_desserts_recipes.service;

import bg.softuni.heathy_desserts_recipes.common.CloudException;
import bg.softuni.heathy_desserts_recipes.common.NoSuchTempPhotoException;
import bg.softuni.heathy_desserts_recipes.model.entity.photo.TempPhotoEntity;
import bg.softuni.heathy_desserts_recipes.model.entity.photo.dto.PhotoBM;
import bg.softuni.heathy_desserts_recipes.model.entity.photo.dto.PhotoDto;
import bg.softuni.heathy_desserts_recipes.model.entity.photo.dto.PhotoViewModel;
import bg.softuni.heathy_desserts_recipes.model.repository.TempPhotoRepository;
import bg.softuni.heathy_desserts_recipes.service.utility.CloudUtility;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import static bg.softuni.heathy_desserts_recipes.common.Constants.FORMAT_NO_SUCH_TEMP_PHOTO;


@Service
public class TempPhotoService {

    private final TempPhotoRepository tempPhotoRepository;
    private final CloudUtility cloudUtility;

    public TempPhotoService(TempPhotoRepository tempPhotoRepository,
                            CloudUtility cloudUtility) {

        this.tempPhotoRepository = tempPhotoRepository;
        this.cloudUtility = cloudUtility;
    }

    public PhotoViewModel save (PhotoBM photoBM) {

        final TempPhotoEntity newEntity = this.cloudUtility
                .saveTemporary(photoBM)
                .toTempEntity();

        final TempPhotoEntity savedEntity = this.tempPhotoRepository
                .saveAndFlush(newEntity);

        return PhotoViewModel
                .fromTempEntity(savedEntity);
    }

    public void updatePrimaryFlag (UUID tempRecipeId, Long primaryPhotoId) {

        final Function<TempPhotoEntity, TempPhotoEntity> setPrimaryFunction =
                entity -> entity.setPrimary(Objects.equals(primaryPhotoId, entity.getId()));

        final List<TempPhotoEntity> tempPhotoEntities = getAllByTempRecipeId(tempRecipeId)
                    .stream()
                    .map(setPrimaryFunction)
                    .toList();

        final long primaryPhotosCount = tempPhotoEntities.stream()
                .filter(TempPhotoEntity::isPrimary)
                .count();

        if (primaryPhotosCount != 1) {
            tempPhotoEntities.stream()
                    .map(tempPhotoEntity -> tempPhotoEntity.setPrimary(Boolean.FALSE))
                    .findFirst()
                    .ifPresent(savedPhotoDTO -> savedPhotoDTO.setPrimary(Boolean.TRUE));
        }

        this.tempPhotoRepository.saveAllAndFlush(tempPhotoEntities);
    }

    public List<PhotoViewModel> getListPhotoVM (UUID tempRecipeId) {

        return getAllByTempRecipeId(tempRecipeId)
                .stream()
                .map(PhotoViewModel::fromTempEntity)
                .toList();
    }

    public List<PhotoDto> getListPhotoDto (UUID tempRecipeId) {

        return getAllByTempRecipeId(tempRecipeId)
                .stream()
                .map(PhotoDto::fromTempEntity)
                .toList();
    }

    private List<TempPhotoEntity> getAllByTempRecipeId (UUID tempRecipeId) {

        return this.tempPhotoRepository.findAllByTempRecipeId(tempRecipeId);
    }

    public void delete (Long id) {

        final Optional<TempPhotoEntity> optionalTempPhoto = this.tempPhotoRepository.findById(id);
        final TempPhotoEntity tempPhotoEntity = optionalTempPhoto
                .orElseThrow(() -> new NoSuchTempPhotoException(FORMAT_NO_SUCH_TEMP_PHOTO.formatted(id)));

        delete(tempPhotoEntity);
    }

    public void delete (TempPhotoEntity tempPhotoEntity) {

        try {
            this.cloudUtility.delete(tempPhotoEntity.getPublicId()) ;
            this.tempPhotoRepository.delete(tempPhotoEntity);
        } catch (IOException e) {
            throw new CloudException(e);
        }

    }

    public List<TempPhotoEntity> getAll () {

        return this.tempPhotoRepository.findAll();
    }
}
