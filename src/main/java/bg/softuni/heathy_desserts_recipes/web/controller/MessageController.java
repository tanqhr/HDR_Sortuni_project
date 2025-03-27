package bg.softuni.heathy_desserts_recipes.web.controller;


import bg.softuni.heathy_desserts_recipes.common.error.exceptions.EmailProblemException;
import bg.softuni.heathy_desserts_recipes.model.entity.message.dto.MessageDto;

import bg.softuni.heathy_desserts_recipes.model.entity.message.dto.ViewMessageDto;
import bg.softuni.heathy_desserts_recipes.service.MessageService;
import bg.softuni.heathy_desserts_recipes.service.MessagesService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static bg.softuni.heathy_desserts_recipes.common.enums.Constants.BINDING_RESULT_PATH;

@Controller
@RequestMapping("/messages")
public class MessageController {


    private final MessagesService messagesService;

    public MessageController(MessagesService messagesService) {
        this.messagesService = messagesService;
    }

    @ModelAttribute(name = "messageDto")
    public MessageDto addMessageDto(){
        return new MessageDto();
    }

    @ModelAttribute(name = "viewMessageDto")
    public ViewMessageDto reviewDto(){
        return new ViewMessageDto();
    }
    @PostMapping("/add")
   // @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView doContact(@Valid ViewMessageDto messageDto,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes,
                                  Model model) {


        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("messageDto", messageDto);
            redirectAttributes.addFlashAttribute(BINDING_RESULT_PATH.concat("messageDto"), bindingResult);

            return new ModelAndView("redirect:/contact", HttpStatus.BAD_REQUEST);
        }
        model.addAttribute("messageDto", messageDto);
        try {
           messagesService.createMessage(messageDto);
        } catch (EmailProblemException e) {
            throw new RuntimeException(e);
        }
        //this.messagesService.createMessage(messageDto);

           return new ModelAndView("message");
    }



   // @PostMapping("/contacts")
//    public String sendEmail(@Valid MessageDto messageDto,
    //                        BindingResult bindingResult,
    //                        RedirectAttributes redirectAttributes){

   //     if (bindingResult.hasErrors()){
   //         redirectAttributes.addFlashAttribute("messageDto", messageDto);
   //         redirectAttributes.addFlashAttribute(BINDING_RESULT_PATH.concat("messageDto"), bindingResult);
   //         return "redirect:/contacts";
   //     }

    //    try {
   //         messageService.sendEmail(messageDto.getName(),
   //                 messageDto.getEmail(),
   //                 messageDto.getText());
   //     } catch (EmailProblemException e) {
   //         throw new RuntimeException(e);
    //    }
//
   //     return "redirect: message";
  //  }


    @GetMapping("/all-messages")
    public String viewAllReviews(Model model){

        List<ViewMessageDto> allMessages = messagesService.getAllMessages();

        model.addAttribute("allMessages", allMessages);
        return "all-messages";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteMessageById(@PathVariable Long id) {

        messagesService.deleteMessageById(id);
        return "redirect:/messages/all-messages";
    }

}