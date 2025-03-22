package bg.softuni.heathy_desserts_recipes.service.AOP;

import bg.softuni.heathy_desserts_recipes.model.entity.user.dto.UserRegistrationDTO;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Optional;

@Aspect
@Component
public class StatisticAOP {

    private static final String BASE_PATH = ".\\src\\main\\resources\\registrations-statistic.txt";

    @Pointcut("execution(* bg.softuni.heathy_desserts_recipes.service.UserService.register(..))")
    void register(){}

    @After("register()")
    void afterRegister(JoinPoint joinPoint){
        Optional<Object> arg = Arrays.stream(joinPoint.getArgs())
                .filter(a -> a.getClass()
                        .getSimpleName()
                        .equals("UserRegistrationDTO"))
                .findFirst();

        if (arg.isEmpty()){
            System.out.println("The method don't have args!");
            return;
        }

        UserRegistrationDTO userRegistrationDTO = (UserRegistrationDTO) arg.get();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
        String date = formatter.format(LocalDateTime.now());

        String message = String.format("A new user registered in the system with email: %s on date: %s!%n",
                userRegistrationDTO.getEmail(), date
        );

        try {
            File file = new File(BASE_PATH);

            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(message);
            bw.close();

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}