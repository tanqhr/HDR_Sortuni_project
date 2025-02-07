package bg.softuni.heathy_desserts_recipes.web.controller;


import bg.softuni.heathy_desserts_recipes.model.entity.recipe.dto.RecipeDto;
import bg.softuni.heathy_desserts_recipes.model.security.CurrentUser;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static bg.softuni.heathy_desserts_recipes.common.enums.Constants.BINDING_RESULT_PATH;


@Controller
public class RecipeController {
    @ModelAttribute("recipeDto")
    public void initRecipeBM (Model model) {

        model.addAttribute("recipeDto", new RecipeDto());
    }

    @GetMapping("/recipes/add")
    public String addRecipe (Model model,
                             @AuthenticationPrincipal CurrentUser author) {

        return "add";
    }

    @PostMapping("/recipes/add")
    public ModelAndView addRecipe (@Valid RecipeDto recipeDto,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttrs,
                                   @AuthenticationPrincipal CurrentUser author) {


        if (bindingResult.hasErrors()) {

            redirectAttrs.addFlashAttribute("recipeDto", recipeDto);
            redirectAttrs.addFlashAttribute(BINDING_RESULT_PATH.concat("recipeDto"), bindingResult);

            return new ModelAndView("redirect:/recipes/add", HttpStatus.BAD_REQUEST);
        }

        final Long recipeId = recipeDto.getAuthorId();

        return new ModelAndView("redirect:/recipes/%d".formatted(recipeId),
                HttpStatus.FOUND);
    }




}
