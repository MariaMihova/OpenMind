package com.OpenMind.web;

import com.OpenMind.models.bindingModels.MeetingBindingModel;
import com.OpenMind.models.bindingModels.RegisterBindingModel;


import com.OpenMind.models.serviceModels.RegisterServiceModel;
import com.OpenMind.serveces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class RegisterController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public RegisterController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @ModelAttribute
    public RegisterBindingModel registerBindingModel(){
        return new RegisterBindingModel();
    }


    @GetMapping("/registration")
    public String register() {
        return "/registration";
    }

    @GetMapping("/registration-error")
    public String register(Model model) {
        model.addAttribute("registrationError", true);
        return "/registration";
    }

    @PostMapping("/registration")
    public String registerMethod(@Valid RegisterBindingModel registerBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes){

        if(!registerBindingModel.getRowPassword().equals(registerBindingModel.getConfirmPassword()) || bindingResult.hasErrors()){
           redirectIfError(registerBindingModel, bindingResult, redirectAttributes);
            return "redirect:registration";
        }

        if(userService.existsByUsername(registerBindingModel.getUsername())){
            redirectAttributes.addFlashAttribute("notAvailable", true);
           redirectIfError(registerBindingModel, bindingResult, redirectAttributes);
            return "redirect:registration";
        }

        if(!userService.registerNewUser(modelMapper.map(registerBindingModel, RegisterServiceModel.class))){
            return "redirect:registration";
        }

//        return "redirect:/home";
        return "redirect:/add-contacts";
    }

    private void redirectIfError(RegisterBindingModel registerBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("registerBindingModel", registerBindingModel)
                .addFlashAttribute("org.springframework.validation.BindingResult.registerBindingModel", bindingResult);
    }

}





