package com.OpenMind.serveces.Impl;

import com.OpenMind.models.bindingModels.PictureBindingModel;
import com.OpenMind.models.entitis.Picture;
import com.OpenMind.models.entitis.UserEntity;
import com.OpenMind.models.viewModels.PictureViewModel;
import com.OpenMind.repositories.PictureRepository;
import com.OpenMind.serveces.PictureService;
import com.OpenMind.serveces.UserService;
import com.OpenMind.serveces.cloudinary.CloudinaryPicture;
import com.OpenMind.serveces.cloudinary.CloudinaryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.Principal;

@Service
public class PictureServiceImpl implements PictureService {

    private final PictureRepository pictureRepository;
    private final CloudinaryService cloudinaryService;
    private final UserService userService;
    private final ModelMapper modelMapper;


    public PictureServiceImpl(PictureRepository pictureRepository,
                              CloudinaryService cloudinaryService,
                              UserService userService, ModelMapper modelMapper) {
        this.pictureRepository = pictureRepository;
        this.cloudinaryService = cloudinaryService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean createPicture(PictureBindingModel pictureBindingModel, Principal principal) throws IOException {

        Picture picture = new Picture();
       try {
           this.processPicture(picture, principal, pictureBindingModel);

       }catch(Exception e) {
           return false;
        }

        return true;

    }

    @Override
    public void delete(String public_id) {
        if(this.cloudinaryService.delete(public_id)){
            this.pictureRepository.deleteByPublicId(public_id);
        }
    }



    @Override
    public PictureViewModel getProfilePicture(String username) {
        Picture picture = this.pictureRepository.findFirstByUserUsername(username);
        if(picture == null){
            picture = new Picture();
            picture.setTitle("No profile picture");
            picture.setUrl("images/defalt-user");
            picture.setPublicId("");
        }

        return modelMapper.map(picture, PictureViewModel.class);

    }



    @Override
    public boolean editPicture(PictureBindingModel pictureBindingModel, Principal principal) throws IOException {
        Picture picture = pictureRepository.findFirstByUserUsername(principal.getName());
        String publicId = picture.getPublicId();
        try {
            this.processPicture(picture, principal, pictureBindingModel);
        }catch(Exception e) {
            return false;
        }
        this.cloudinaryService.delete(publicId);
        return true;
    }


    private void processPicture(Picture picture, Principal principal, PictureBindingModel pictureBindingModel) throws IOException {

        CloudinaryPicture uploaded = this.cloudinaryService.upload(pictureBindingModel.getPicture());
        picture.setPublicId(uploaded.getPublicId());
        picture.setTitle(pictureBindingModel.getTitle());
        picture.setUrl(uploaded.getUrl());

        UserEntity user = this.userService.findByUsername(principal.getName());
        picture.setUser(user);
        userService.updateUser(user);
        try {
            this.pictureRepository.save(picture);
        }catch (Exception e){
            throw new RuntimeException("Picture not saved");
        }

        user.setPicture(picture);
        userService.updateUser(user);
    }

}
