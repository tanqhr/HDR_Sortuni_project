package bg.softuni.heathy_desserts_recipes.service;


import bg.softuni.heathy_desserts_recipes.common.error.exceptions.EmailProblemException;
import bg.softuni.heathy_desserts_recipes.model.entity.message.Message;
import bg.softuni.heathy_desserts_recipes.model.entity.message.dto.MessageDto;
import bg.softuni.heathy_desserts_recipes.model.event.RegistrationUserEvent;
import bg.softuni.heathy_desserts_recipes.model.repository.MessageRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import java.io.UnsupportedEncodingException;
import java.util.Locale;


@Service

public class MessageService {


    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    private final MessageRepository messageRepository;

    private final ModelMapper modelMapper;
    private static final String recipientEmail = "tanikalpazani@gmail.com";
    private final static String subjectAddUser = "New user registration in system !";

    public MessageService(JavaMailSender javaMailSender, TemplateEngine templateEngine, MessageRepository messageRepository, ModelMapper modelMapper) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
        this.messageRepository = messageRepository;
        this.modelMapper = modelMapper;
    }


    public void sendRegistrationEmail(
            String userEmail,
            String userName) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        try {
            mimeMessageHelper.setFrom("hdp@example.com");
            mimeMessageHelper.setTo(userEmail);
            //TODO: i18n
            mimeMessageHelper.setSubject("Welcome to HDR!");
            mimeMessageHelper.setText(generateEmailText(userName), true);

            javaMailSender.send(mimeMessageHelper.getMimeMessage());

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    private String generateEmailText(String userName) {
        Context ctx = new Context();
        ctx.setLocale(Locale.getDefault());
        ctx.setVariable("userName", userName);

        return templateEngine.process("email-confirm", ctx);
    }






    @EventListener(RegistrationUserEvent.class)
    public void onRegisterUser(RegistrationUserEvent evt){

        String emailContent = "Congratulations, you have registered successfully!" ;

        sendEmail(recipientEmail, subjectAddUser, emailContent);

    }


    public void sendEmail(String emailSender, String subject, String content) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

       MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        try {
            mimeMessageHelper.setFrom(new InternetAddress(emailSender, "App"));
            mimeMessageHelper.setTo(recipientEmail);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(content);

           javaMailSender.send(mimeMessageHelper.getMimeMessage());

        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new EmailProblemException();
        }
    }
    public void sendSimpleEmail(String to) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("tanikalpazani@gmail.com");
        message.setTo(to);
        message.setSubject("New user registration in system !");
        message.setText("Congratulations, you have registered successfully!");
        javaMailSender.send(message);
    }



  public void sendMessage(MessageDto messageDto) {

        Message message = modelMapper.map(messageDto, Message.class);
         messageRepository.saveAndFlush(message);

   }


}
