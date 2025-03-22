package bg.softuni.heathy_desserts_recipes.service.schedule;

import bg.softuni.heathy_desserts_recipes.model.repository.MessageRepository;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;

public class CleanOldMessages {

    private final MessageRepository messageRepository;

    public CleanOldMessages(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }


    @Transactional
    @Scheduled(cron = "00 08 * * 01")
    public void deleteOldMessages(){
        messageRepository.deleteAll();

    }
}
