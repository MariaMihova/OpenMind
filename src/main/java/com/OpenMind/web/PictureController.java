package com.OpenMind.web;

import com.OpenMind.models.bindingModels.MeetingBindingModel;
import com.OpenMind.models.bindingModels.PictureBindingModel;
import com.OpenMind.serveces.PictureService;
import com.OpenMind.serveces.cloudinary.CloudinaryService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;

@Controller
public class PictureController {

    private final CloudinaryService cloudinaryService;
    private final PictureService pictureService;

    public PictureController(CloudinaryService cloudinaryService, PictureService pictureService) {
        this.cloudinaryService = cloudinaryService;
        this.pictureService = pictureService;
    }

    @ModelAttribute
    private PictureBindingModel pictureBindingModel(){
        return new PictureBindingModel();
    }

    @GetMapping("/add-picture")
    public String addPicturePage(){
        return "/add-picture";
    }

    @PostMapping("/add-picture")
    public String addPictureMethod(@Valid PictureBindingModel pictureBindingModel, BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes, Principal principal) throws IOException {
        if(bindingResult.hasErrors()){
           redirectIfError(pictureBindingModel, bindingResult, redirectAttributes);
            return "redirect:add-picture";
        }

       if(!pictureService.createPicture(pictureBindingModel, principal)){
            pictureBindingModel.setNotValid(true);
           redirectIfError(pictureBindingModel, bindingResult, redirectAttributes);
            return "redirect:add-picture";
        }

        return "redirect:/home";
    }


    @DeleteMapping("/delete-picture")
    @Transactional
    public String deletePicture(@RequestParam("public_id") String public_id){
        pictureService.delete(public_id);
        return "redirect:/profile";
    }


    @GetMapping("/edit-picture")
    public String editPicturePage(){

        return "/edit-picture";
    }


    @PostMapping("/edit-picture")
    public String editPictureMethod(PictureBindingModel pictureBindingModel,
                                    BindingResult bindingResult, RedirectAttributes redirectAttributes,
                                    Principal principal) throws IOException {
        if(bindingResult.hasErrors()){
            redirectIfError(pictureBindingModel, bindingResult, redirectAttributes);
            return "redirect:edit-picture";
        }

        if(!pictureService.createPicture(pictureBindingModel, principal)){
            pictureBindingModel.setNotValid(true);
            redirectIfError(pictureBindingModel, bindingResult, redirectAttributes);
            return "redirect:edit-picture";
        }

        pictureService.editPicture(pictureBindingModel, principal);


        return "redirect:/profile";
    }

    private void redirectIfError(PictureBindingModel pictureBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("pictureBindingModel", pictureBindingModel)
                .addFlashAttribute("org.springframework.validation.BindingResult.pictureBindingModel", bindingResult);
    }
}
