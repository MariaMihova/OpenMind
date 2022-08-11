package com.OpenMind.web;

import com.OpenMind.models.bindingModels.AuthoritiesModel;
import com.OpenMind.models.entitis.UserRole;
import com.OpenMind.models.enums.Role;
import com.OpenMind.models.viewModels.StatView;
import com.OpenMind.models.viewModels.UserViewModel;
import com.OpenMind.repositories.ArticleRepository;
import com.OpenMind.serveces.StatService;
import com.OpenMind.serveces.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
public class AdminController {

    private final StatService statService;
    private final UserService userService;
    private final ArticleRepository articleRepository;

    public AdminController(StatService statService, UserService userService, ArticleRepository articleRepository) {
        this.statService = statService;
        this.userService = userService;
        this.articleRepository = articleRepository;
    }

    @ModelAttribute
    private AuthoritiesModel authoritiesModel(){
        return new AuthoritiesModel();
    }

    @GetMapping("/admin")
    public ModelAndView adminPage(){

        ModelAndView modelAndView = new ModelAndView();
        List<UserViewModel> administrators = userService.getAllByRole(Role.ADMIN);
        StatView statView = statService.getStats();
        modelAndView.addObject("statView", statView);
        modelAndView.addObject("administrators", administrators);

        modelAndView.setViewName("admin");

        return modelAndView;
    }


    @PostMapping("/set-authorities")
    public String setAuthorities(@Valid AuthoritiesModel authoritiesModel, BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes, Principal principal){


        if(bindingResult.hasErrors()){
           redirectIfError(authoritiesModel, bindingResult, redirectAttributes);
           return "redirect:admin#meetings";
        }

        if(!userService.existsByUsername(authoritiesModel.getUsername())){
            authoritiesModel.setDoseNotExist(true);
            redirectIfError(authoritiesModel, bindingResult, redirectAttributes);
            return "redirect:admin#meetings";
           }


        userService.setAuthorities(authoritiesModel);

        return "redirect:/admin";

    }

    private void redirectIfError(AuthoritiesModel authoritiesModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("authoritiesModel", authoritiesModel)
                .addFlashAttribute("org.springframework.validation.BindingResult.authoritiesModel", bindingResult);
    }
}
