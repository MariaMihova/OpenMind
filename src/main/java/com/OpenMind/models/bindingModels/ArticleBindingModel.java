package com.OpenMind.models.bindingModels;

import com.OpenMind.utils.ValidateString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ArticleBindingModel {

    private String title;
    private String content;
    private String professionalField;

    public ArticleBindingModel(){}

    @NotNull(message = "Title can not be empty!")
    @Size(min = 3, message = "The size of the title must be at last 3 symbols.")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NotNull(message = "Content can not be empty!")
    @Size(min = 200, message = "Content must be at last 200 characters.")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @NotBlank
    @ValidateString(acceptedValues = {"PSYCHOLOGY", "SOCIAL_WORK", "SPEECH_THERAPY"})
    public String getProfessionalField() {
        return professionalField;
    }

    public void setProfessionalField(String professionalField) {
        this.professionalField = professionalField;
    }
}
