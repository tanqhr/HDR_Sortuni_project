package bg.softuni.hdr_rest_server.web;


import bg.softuni.hdr_rest_server.model.dto.AddMessageDto;
import bg.softuni.hdr_rest_server.model.dto.MessageDto;
import bg.softuni.hdr_rest_server.service.MessageService;
import bg.softuni.hdr_rest_server.service.exception.ObjectNotFountException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/add")
    public ResponseEntity<MessageDto> createMessage(@RequestBody AddMessageDto messageDto) {
        Long messageId = messageService.createMessage(messageDto);

        return ResponseEntity.created(ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}").buildAndExpand(messageId).toUri()).build();
    }

    @ModelAttribute(name = "addReviewDto")
    public AddMessageDto addReviewDto(){
        return new AddMessageDto();
    }

    @ModelAttribute(name = "reviewDto")
    public MessageDto reviewDto(){
        return new MessageDto();
    }



    @GetMapping("all-messages")
    public ResponseEntity<List<MessageDto>> getAllMessages(){
        return ResponseEntity.ok(messageService.getAllMessages());
    }


    @DeleteMapping("delete/{id}")
    public ResponseEntity<MessageDto> deleteById(@PathVariable("id") Long id){
        messageService.deleteMessageById(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{id}")
    public ResponseEntity<MessageDto> findById(@PathVariable("id") Long id){
        return ResponseEntity.ok(messageService.getMessageById(id));
    }



    @ExceptionHandler(ObjectNotFountException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public BaseExceptionInfo objectNotFountException(ObjectNotFountException objectNotFountException){
        return new BaseExceptionInfo(objectNotFountException.getMessage(), objectNotFountException.getUrl());
    }
}

