package com.OpenMind.web;

import com.OpenMind.models.bindingModels.MeetingBindingModel;
import com.OpenMind.serveces.MeetingService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class MeetingController {

    private final MeetingService meetingService;

    public MeetingController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }


    @ModelAttribute
    private MeetingBindingModel meetingBindingModel(){
        return new MeetingBindingModel();
    }



    @GetMapping("/add-meeting")
    public String addMeetingPage(){
        return "add-meeting";
    }

    @PostMapping("add-meeting")
    public String addMeetingMethod(@Valid MeetingBindingModel meetingBindingModel, BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes, Principal principal){

        if(bindingResult.hasErrors()){
            redirectIfError(meetingBindingModel, bindingResult, redirectAttributes);
            return "redirect:add-meeting";
        }

        if(!meetingService.addNewMeeting(meetingBindingModel, principal)){
            redirectIfError(meetingBindingModel, bindingResult, redirectAttributes);
            return "redirect:add-meeting";
        }

        return "redirect:/profile#meetings";
    }

    private void redirectIfError(MeetingBindingModel meetingBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
            redirectAttributes.addFlashAttribute("meetingBindingModel", meetingBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.meetingBindingModel", bindingResult);
    }
}
