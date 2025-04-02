package bg.softuni.hdr_rest_server.service;

import bg.softuni.hdr_rest_server.model.dto.AddMessageDto;
import bg.softuni.hdr_rest_server.model.dto.MessageDto;
import bg.softuni.hdr_rest_server.model.entity.Message;
import bg.softuni.hdr_rest_server.repository.MessageRepository;
import bg.softuni.hdr_rest_server.service.exception.ObjectNotFountException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import java.util.List;
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final ModelMapper modelMapper;

    @Override
    public Long createMessage(AddMessageDto addMessageDto) {
        Message message = modelMapper.map(addMessageDto, Message.class);
        return messageRepository.save(message).getId();
    }

    @Override
    @Transactional
    public void deleteAllMessages() {
        this.messageRepository.deleteAll();
    }

    @Override
    public void deleteMessageById(Long messageId) {
        this.messageRepository.deleteById(messageId);
    }

    @Override
    public MessageDto getMessageById(Long id) {
        return messageRepository.findById(id)
                .map(message -> modelMapper.map(message, MessageDto.class))
                .orElseThrow(()-> new ObjectNotFountException("Message with id " + id + " is not found!", "/message/add/" + id));
    }

    @Override
    public List<MessageDto> getAllMessages() {
        return this.messageRepository.findAll()
                .stream().map(message -> modelMapper.map(message, MessageDto.class))
                .toList();
    }


}
