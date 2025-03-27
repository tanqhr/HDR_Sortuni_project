package bg.softuni.heathy_desserts_recipes.web.controller.rest;



import bg.softuni.heathy_desserts_recipes.model.entity.email.Email;
import bg.softuni.heathy_desserts_recipes.model.entity.email.dto.EmailDto;
import bg.softuni.heathy_desserts_recipes.model.entity.email.dto.EmailViewDto;
import bg.softuni.heathy_desserts_recipes.model.entity.recipe.dto.RecipeShortDto;
import bg.softuni.heathy_desserts_recipes.service.EmailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/about")
public class RestEmailController {

    private EmailService emailService;

    public RestEmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    // Create a new brand with categories
    //   @PostMapping("/api/about")
//    public ResponseEntity<Email> createEmail(@Valid @RequestBody EmailDto emailDto) {
    //      Email createdEmail = emailService.createEmail(emailDto);
    //      return ResponseEntity.status(HttpStatus.CREATED).body(createdEmail);
    //  }
    @PostMapping
    public ResponseEntity<EmailDto> createEmail(
            @RequestBody EmailDto emailDto,
            UriComponentsBuilder uriComponentsBuilder) {

        long emailId = emailService.createEmail(emailDto);
        // return ResponseEntity
        //        .created(URI.create(String.format("/api/about/%d", emailId)))
        //     .body(comment);
        return ResponseEntity.created(
                uriComponentsBuilder.path("/api/about/{id}").buildAndExpand(emailId).toUri()
        ).build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<EmailDto> deleteEmailById(@PathVariable("id") Long emailId) {
        this.emailService.deleteById(emailId);
        return ResponseEntity.
                noContent().
                build();
    }

    @GetMapping("/emails")
    public ResponseEntity<List<EmailViewDto>> getAllEmails() {
        return ResponseEntity.
                ok(emailService.getAllEmails());
    }
}







