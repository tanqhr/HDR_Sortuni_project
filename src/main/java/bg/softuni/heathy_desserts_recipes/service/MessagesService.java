package bg.softuni.heathy_desserts_recipes.service;


import bg.softuni.heathy_desserts_recipes.model.entity.message.dto.ViewMessageDto;
import bg.softuni.heathy_desserts_recipes.model.entity.message.dto.MessageDto;
import bg.softuni.heathy_desserts_recipes.util.RestTemplateResponseErrorHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
public class MessagesService{

    private final RestClient restClient;
    private final ModelMapper modelMapper;
    private final Logger LOGGER = LoggerFactory.getLogger(MessagesService.class);
    private final RestTemplate restTemplate;


    public MessagesService(RestClient restClient, RestTemplateBuilder restTemplateBuilder, ModelMapper modelMapper) {
        this.restClient = restClient;
        this.restTemplate = restTemplateBuilder
                .errorHandler(new RestTemplateResponseErrorHandler())
                .build();
        this.modelMapper = modelMapper;
    }

    public ViewMessageDto createMessage(ViewMessageDto viewMessageDto) {
        //ViewMessageDto viewMessageDto=modelMapper.map(messageDto, ViewMessageDto.class);
        LOGGER.info("Creating new message {}", viewMessageDto);
        ResponseEntity<ViewMessageDto> messageEntity=restClient.post()
                .uri("/messages/add")
                .body(viewMessageDto)
                .retrieve()
                .toEntity(ViewMessageDto.class);

        log.info("Status Code: "+ messageEntity.getStatusCode().value());
ViewMessageDto info=messageEntity.getBody();
return info;
    }


    public List<ViewMessageDto> getAllMessages() {
        List<ViewMessageDto> messages = restClient.get()
                .uri("http://localhost:8081/messages/all-messages")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });


        return messages;
    }


    public void deleteMessageById(Long id) {
        LOGGER.info("Delete: {}", id);
        restClient.delete()
                .uri("/messages/delete/{id}", id)
                .retrieve()
                .toBodilessEntity();
    }

}
