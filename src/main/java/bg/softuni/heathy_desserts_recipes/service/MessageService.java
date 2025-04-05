package bg.softuni.heathy_desserts_recipes.service;


import bg.softuni.heathy_desserts_recipes.common.error.exceptions.EmailProblemException;
import bg.softuni.heathy_desserts_recipes.model.entity.message.dto.MessageDto;
import bg.softuni.heathy_desserts_recipes.model.event.RegistrationUserEvent;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
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

    private final ModelMapper modelMapper;
    private static final String recipientEmail = "tanikalpazani@gmail.com";
    private final static String subjectAddUser = "New user registration in system !";

    public MessageService(JavaMailSender javaMailSender, TemplateEngine templateEngine, ModelMapper modelMapper) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
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

}
