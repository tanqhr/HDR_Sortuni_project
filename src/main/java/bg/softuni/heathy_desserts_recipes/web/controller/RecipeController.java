package bg.softuni.heathy_desserts_recipes.web.controller;


import bg.softuni.heathy_desserts_recipes.model.entity.recipe.dto.RecipeDto;
import bg.softuni.heathy_desserts_recipes.model.security.CurrentUser;
import bg.softuni.heathy_desserts_recipes.service.utility.RecipeForm;
import jakarta.validation.Valid;
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
    private final RecipeForm recipeForm;

    public RecipeController(RecipeForm recipeForm) {
        this.recipeForm = recipeForm;
    }



    @ModelAttribute("units")
    public void initUnits(Model model) {

        model.addAttribute("units", recipeForm.getDistinctUnitNames());
    }

    @ModelAttribute("recipeDto")
    public void initRecipe(Model model) {

        model.addAttribute("recipeDto", new RecipeDto());
    }
    private static void checkUniqueTitle (BindingResult bindingResult) {

        if (bindingResult.getGlobalErrors()
                .stream()
                .anyMatch(error -> Objects.requireNonNull(error.getCode()).contains("UniqueRecipeForUser"))) {

            bindingResult.addError(new FieldError("recipeDto",
                    "title",
                    "You already have recipe with the same title."));
        }
    }
    @GetMapping("/recipes/add")
    public String addRecipe(Model model,
                            @AuthenticationPrincipal CurrentUser author) {
        final RecipeDto recipeDto = (RecipeDto) model.getAttribute("recipeDto");
        if (null != recipeDto &&
                null == recipeDto.getTempRecipeId()) {

            recipeDto
                    .setTempRecipeId(UUID.randomUUID())
                    .setAuthorId(author.getId());
        }

        return "add";
    }

    @PostMapping("/recipes/add")
    public ModelAndView addRecipe(@Valid RecipeDto recipeDto,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes,
                                  @AuthenticationPrincipal CurrentUser author) {

        this.recipeForm.process(recipeDto);

        checkUniqueTitle(bindingResult);


        if (bindingResult.hasErrors()) {

            redirectAttributes.addFlashAttribute("recipeDto", recipeDto);
            redirectAttributes.addFlashAttribute(BINDING_RESULT_PATH.concat("recipeDto"), bindingResult);

            return new ModelAndView("redirect:/recipes/add", HttpStatus.BAD_REQUEST);
        }
        final Long recipeId = this.recipeForm.save(recipeDto, author.getId());

        return new ModelAndView("redirect:/recipes/%d".formatted(recipeId),
                HttpStatus.FOUND);
    }

        @GetMapping("/recipes/{id}")
        public String getRecipe (@PathVariable Long id,
                Model model,
                @AuthenticationPrincipal CurrentUser currentUser) {

            model.addAttribute("recipeViewModel", this.recipeForm.getRecipeVMForUser(id, currentUser));
            model.addAttribute("contextAuthorities", currentUser.getContextAuthorities());
            model.addAttribute("contextRoles", currentUser.getContextRoles());

            return "show";
        }
    }

