package com.OpenMind.web;

import com.OpenMind.models.bindingModels.ClientBindingModel;
import com.OpenMind.models.bindingModels.MeetingBindingModel;
import com.OpenMind.serveces.ClientService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }


    @ModelAttribute
    private ClientBindingModel clientBindingModel(){
        return new ClientBindingModel();
    }

    @GetMapping("/add-client")
    public String addClientPage(){
        return "/add-client";
    }


    @PostMapping("/add-client")
    public String addClientMethod(@Valid ClientBindingModel clientBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes, Principal principal){

        if(bindingResult.hasErrors()){
            redirectIfError(clientBindingModel, bindingResult,redirectAttributes);
            return "redirect:add-client";
        }


        if(!clientService.addNewClient(clientBindingModel, principal)){
            redirectIfError(clientBindingModel, bindingResult, redirectAttributes);
            return "redirect:add-client";
        }

        return "redirect:/profile#clients";
    }


    private void redirectIfError(ClientBindingModel clientBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("clientBindingModel", clientBindingModel)
                .addFlashAttribute("org.springframework.validation.BindingResult.clientBindingModel", bindingResult);
    }


}
