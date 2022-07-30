package com.OpenMind.web;

import com.OpenMind.models.bindingModels.ContactsBindingModel;
import com.OpenMind.models.bindingModels.MeetingBindingModel;
import com.OpenMind.serveces.ContactsService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class ContactsController {

    private final ContactsService contactsService;


    public ContactsController(ContactsService contactsService) {
        this.contactsService = contactsService;
    }

    @ModelAttribute
    private ContactsBindingModel contactsBindingModel(){
        return new ContactsBindingModel();
    }
    @GetMapping("add-contacts")
    public String addContactsPage(){
        return "/add-contacts";
    }


    @PostMapping("/add-contacts")
    public String addContactsMethod(@Valid ContactsBindingModel contactsBindingModel, BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes, Principal principal){

        if(bindingResult.hasErrors()){
            redirectIfError(contactsBindingModel, bindingResult, redirectAttributes);
            return "redirect:add-contacts";
        }

        if(!contactsService.addNewContacts(contactsBindingModel, principal)){
           redirectIfError(contactsBindingModel, bindingResult, redirectAttributes);
            return "redirect:add-contacts";
        }

        return "redirect:/add-picture";
    }

    private void redirectIfError(ContactsBindingModel contactsBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("contactsBindingModel", contactsBindingModel)
                .addFlashAttribute("org.springframework.validation.BindingResult.contactsBindingModel", bindingResult);
    }
}
