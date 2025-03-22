package bg.softuni.heathy_desserts_recipes.service;

import bg.softuni.heathy_desserts_recipes.model.entity.email.Email;
import bg.softuni.heathy_desserts_recipes.model.entity.email.dto.EmailDto;
import bg.softuni.heathy_desserts_recipes.model.repository.EmailRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.modelmapper.ModelMapper;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Locale;

@Service
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    private final EmailRepository emailRepository;
    private final ModelMapper modelMapper;
    public EmailService(JavaMailSender javaMailSender, TemplateEngine templateEngine, EmailRepository emailRepository, ModelMapper modelMapper) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
        this.emailRepository = emailRepository;
        this.modelMapper = modelMapper;
    }

    public void register (EmailDto emaildto) {

        Email email = modelMapper.map(emaildto, Email.class);
        emailRepository.saveAndFlush(email);
        sendSignForEmail(email.getEmail());

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


    public void sendSignForEmail(
            String userEmail) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        try {
            mimeMessageHelper.setFrom("hdp@example.com");
            mimeMessageHelper.setTo(userEmail);
            //TODO: i18n
            mimeMessageHelper.setSubject("HDR newsletter");
            mimeMessageHelper.setText(generateEmailSignText(), true);

            javaMailSender.send(mimeMessageHelper.getMimeMessage());

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    private String generateEmailSignText() {
        Context ctx = new Context();
        ctx.setLocale(Locale.getDefault());

        return templateEngine.process("email-newsletter", ctx);
    }

}
