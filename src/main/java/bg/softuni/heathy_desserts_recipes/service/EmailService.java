package bg.softuni.heathy_desserts_recipes.service;

import bg.softuni.heathy_desserts_recipes.model.entity.email.Email;
import bg.softuni.heathy_desserts_recipes.model.entity.email.dto.EmailDto;
import bg.softuni.heathy_desserts_recipes.model.repository.EmailRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final EmailRepository emailRepository;
    private final ModelMapper modelMapper;
    public EmailService(EmailRepository emailRepository, ModelMapper modelMapper) {
        this.emailRepository = emailRepository;
        this.modelMapper = modelMapper;
    }

    public void register (EmailDto emaildto) {

        Email email = modelMapper.map(emaildto, Email.class);
        emailRepository.saveAndFlush(email);

    }


  //  public Email createEmail(EmailDto emailDto) {
    //    Email emailEntity = modelMapper.map(emailDto, Email.class);
    //    Email savedEmail = emailRepository.save(emailEntity);
   //     return modelMapper.map(savedEmail, Email.class);
  //  }

    public Long createEmail(EmailDto emailDto) {


        Email newEmail = new Email()
                .setEmail(emailDto.getEmail());
        newEmail = emailRepository.save(newEmail);

        return newEmail.getId();
    }

    public void deleteById(Long bookId) {
        emailRepository.
                findById(bookId).
                ifPresent(emailRepository::delete);
    }
}
