package bg.softuni.heathy_desserts_recipes.web.controller;


import bg.softuni.heathy_desserts_recipes.common.error.exceptions.EmailProblemException;
import bg.softuni.heathy_desserts_recipes.model.entity.message.dto.MessageDto;
import bg.softuni.heathy_desserts_recipes.service.MessageService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static bg.softuni.heathy_desserts_recipes.common.enums.Constants.BINDING_RESULT_PATH;

@Controller
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }


    @PostMapping("/contact")
    public ModelAndView doContact(@Valid MessageDto messageDto,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes,
                                  Model model) {


        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("messageDto", messageDto);
           redirectAttributes.addFlashAttribute(BINDING_RESULT_PATH.concat("messageDto"), bindingResult);

           return new ModelAndView("redirect:/contact", HttpStatus.BAD_REQUEST);
        }
       model.addAttribute("messagesDto", messageDto);
        this.messageService.sendMessage(messageDto);
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

}