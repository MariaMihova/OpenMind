package com.OpenMind.web;

import com.OpenMind.models.bindingModels.PictureBindingModel;
import com.OpenMind.models.exceptions.ResourceNotFoundException;
import com.OpenMind.models.viewModels.*;
import com.OpenMind.serveces.*;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@RestController
public class UserProfileController {

    private final UserService userService;
    private final ArticleService articleService;
    private final ClientService clientService;
    private final MeetingService meetingService;
    private final PictureService pictureService;
    private final ContactsService contactsService;



    public UserProfileController(UserService userService, ArticleService articleService,
                                 ClientService clientService, MeetingService meetingService,
                                 PictureService pictureService, ContactsService contactsService) {
        this.userService = userService;
        this.articleService = articleService;
        this.clientService = clientService;
        this.meetingService = meetingService;
        this.pictureService = pictureService;
        this.contactsService = contactsService;
    }



    @GetMapping("/profile")
    public ModelAndView profilePage(ModelAndView modelAndView, Principal principal) {

        List<ArticleVewModel> userArticles = articleService.findArticlesByUser(principal.getName());
        List<ClientViewModel> clients = clientService.findClientsBySpecialist(principal.getName());
        List<MeetingViewModel> meetings = meetingService.findMeetingsByUsername(principal.getName());
        PictureViewModel pictureViewModel = pictureService.getProfilePicture(principal.getName());
        pictureViewModel.setEdit(!pictureViewModel.getTitle().equalsIgnoreCase("No profile picture"));
        ContactViewModel contactViewModel = contactsService.findContactByUserName(principal.getName());

//        modelAndView.addObject("principal", principal);
        modelAndView.addObject("userArticles", userArticles);
        modelAndView.addObject("clients", clients);
        modelAndView.addObject("meetings", meetings);
        modelAndView.addObject("pictureViewModel", pictureViewModel);
        modelAndView.addObject("contactViewModel", contactViewModel);



        modelAndView.setViewName("profile");

        return modelAndView;
    }

    @GetMapping("/profile/clients")
    public ResponseEntity<Model> profilePage(Model modelAndView, Principal principal) {

        List<ClientViewModel> clients = clientService.findClientsBySpecialist(principal.getName());
        modelAndView.addAttribute("clients", clients);

        return ResponseEntity.ok(modelAndView);
    }

    @GetMapping("/profile-details/{id}")
    public ModelAndView profileDetailsPage(@PathVariable("id") long id, HttpSession httpSession, ModelAndView modelAndView, Principal principal) {

       UserViewModel userViewDetails = userService.findById(id);
       List<ArticleVewModel> userArticles = articleService.findArticlesByUser(userService.findById(id).getUsername());
        PictureViewModel pictureViewModel = pictureService.getProfilePicture(userService.findById(id).getUsername());
        ContactViewModel contactViewModel = contactsService.findContactByUserId(id);

        if(userViewDetails != null){
            if(userViewDetails.getUsername().equalsIgnoreCase(principal.getName())){

                return this.profilePage(modelAndView, principal);
            }
            modelAndView.addObject("userViewDetails", userViewDetails);
            modelAndView.addObject("userArticles", userArticles);
            modelAndView.addObject("pictureViewModel", pictureViewModel);
            modelAndView.addObject("contactViewModel", contactViewModel);
            modelAndView.setViewName("profile-details");
        }else {
            throw new ResourceNotFoundException();
        }

        return modelAndView;
    }

}
