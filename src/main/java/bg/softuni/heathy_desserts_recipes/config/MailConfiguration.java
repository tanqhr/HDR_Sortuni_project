package bg.softuni.heathy_desserts_recipes.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;


import java.util.Properties;

@Configuration
    public class MailConfiguration {


    @Bean
        public JavaMailSender javaMailSender(
        @Value("smtp.gmail.com") String mailHost,
        @Value("587") Integer mailPort,
        @Value("${email_username}") String mailUsername,
        @Value("${password_email}") String mailPassword)

    {


        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
            javaMailSender.setHost(mailHost);
            javaMailSender.setPort(mailPort);
            javaMailSender.setUsername(mailUsername);
            javaMailSender.setPassword(mailPassword);
            javaMailSender.setJavaMailProperties(mailProperties());
            javaMailSender.setDefaultEncoding("UTF-8");


            return javaMailSender;
        }

        private Properties mailProperties(){
            Properties properties = new Properties();

            properties.setProperty("mail.smtp.auth", "true");
            properties.setProperty("mail.transport.protocol", "smtp");

           properties.setProperty("mail.smtp.starttls.enable", "true");

            return properties;
        }


    }
