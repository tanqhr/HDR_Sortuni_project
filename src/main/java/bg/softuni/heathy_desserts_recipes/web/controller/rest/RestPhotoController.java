package bg.softuni.heathy_desserts_recipes.web.controller.rest;


import bg.softuni.heathy_desserts_recipes.common.CloudException;
import bg.softuni.heathy_desserts_recipes.common.CustomErrors;
import bg.softuni.heathy_desserts_recipes.common.NoSuchTempPhotoException;
import bg.softuni.heathy_desserts_recipes.common.messages.Message;
import bg.softuni.heathy_desserts_recipes.model.entity.photo.dto.PhotoBM;
import bg.softuni.heathy_desserts_recipes.model.entity.photo.dto.PhotoDto;
import bg.softuni.heathy_desserts_recipes.model.entity.photo.dto.PhotoViewModel;
import bg.softuni.heathy_desserts_recipes.service.PhotoService;
import bg.softuni.heathy_desserts_recipes.service.TempPhotoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/photos")
public class RestPhotoController {


    private final PhotoService photoService;
    private final TempPhotoService tempPhotoService;


    public RestPhotoController(PhotoService photoService,
                               TempPhotoService tempPhotoService) {

        this.photoService = photoService;
        this.tempPhotoService = tempPhotoService;
    }

    @PostMapping(path = "/temp/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PhotoViewModel> upload (@Valid PhotoBM photoBM) {

        final PhotoViewModel photoVM = this.tempPhotoService.save(photoBM);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(photoVM);
    }

    @DeleteMapping("/temp/delete/{id}")
    public ResponseEntity<Object> delete (@PathVariable Long id) {

        try {
            this.tempPhotoService.delete(id);

            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .build();

        } catch (NoSuchTempPhotoException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Message.from(e.getMessage()));

        } catch (CloudException e) {

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(CustomErrors.INTERNAL_SERVER_ERROR.getErrorMessage());
        }
    }

}