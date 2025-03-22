package bg.softuni.heathy_desserts_recipes.service.schedule;


import bg.softuni.heathy_desserts_recipes.model.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClearDeActiveUsers {



    private final UserRepository userRepository;


    @Transactional
    @Scheduled(cron = "@monthly")
    public void deleteDeActiveUsers(){
        userRepository.deleteAllByActiveIsFalse();

    }
}