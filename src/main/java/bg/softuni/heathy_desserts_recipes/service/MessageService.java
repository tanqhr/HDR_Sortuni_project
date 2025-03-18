package bg.softuni.heathy_desserts_recipes.service;


import bg.softuni.heathy_desserts_recipes.model.entity.message.Message;
import bg.softuni.heathy_desserts_recipes.model.entity.message.dto.MessageDto;
import bg.softuni.heathy_desserts_recipes.model.repository.MessageRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
public class MessageService {
    private final MessageRepository messageRepository;

    private final ModelMapper modelMapper;
    public MessageService(MessageRepository messageRepository, ModelMapper modelMapper) {
        this.messageRepository = messageRepository;
        this.modelMapper = modelMapper;
    }

    public void sendMessage(MessageDto messageDto) {

        Message message = modelMapper.map(messageDto, Message.class);
          messageRepository.saveAndFlush(message);

    }
}
