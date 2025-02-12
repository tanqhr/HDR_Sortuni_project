package bg.softuni.heathy_desserts_recipes.config;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class
CloudinaryConfiguration {


    @Value("#{environment.getProperty('CLOUDINARY_URL')}")
    String cloudinaryUrl;

    @Bean
    @ConditionalOnExpression("${cloud.service.mocked} == false")
    public Cloudinary cloudinary () {

        final Cloudinary cloudinary = new Cloudinary(cloudinaryUrl);
        cloudinary.config.secure = true;
        return cloudinary;
    }



}
