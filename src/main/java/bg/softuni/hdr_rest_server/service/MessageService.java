package bg.softuni.hdr_rest_server.service;

import bg.softuni.hdr_rest_server.model.dto.AddMessageDto;
import bg.softuni.hdr_rest_server.model.dto.MessageDto;


import java.util.List;

public interface MessageService {
    Long createMessage(AddMessageDto addMessageDto);

    void deleteAllMessages();

    void deleteMessageById(Long messageId);

    MessageDto getMessageById(Long id);

    List<MessageDto> getAllMessages();


}
