package bg.softuni.heathy_desserts_recipes.web.controller.rest;


import bg.softuni.heathy_desserts_recipes.model.entity.email.Email;
import bg.softuni.heathy_desserts_recipes.model.entity.email.dto.EmailDto;
import bg.softuni.heathy_desserts_recipes.service.EmailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/about")
public class RestEmailController {

    private EmailService emailService;

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

        return ResponseEntity.created(
                uriComponentsBuilder.path("/api/about/{id}").buildAndExpand(emailId).toUri()
        ).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EmailDto> deleteEmailById(@PathVariable("id") Long emailId) {
        emailService.deleteById(emailId);

        return ResponseEntity.
                noContent().
                build();
    }




}
