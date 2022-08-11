package com.OpenMind.models.bindingModels;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PictureBindingModel {

    private String title;
    private MultipartFile picture;
    private boolean isNotValid;

    public PictureBindingModel(){}

    @NotBlank(message = "Please, enter picture name!")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MultipartFile getPicture() {
        return picture;
    }

    public void setPicture(MultipartFile picture) {
        this.picture = picture;
    }

    public boolean isNotValid() {
        return isNotValid;
    }

    public void setNotValid(boolean valid) {
        isNotValid = valid;
    }
}
