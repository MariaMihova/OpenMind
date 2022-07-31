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

    @NotNull
    @Size(min = 3)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NotNull
    @Size(min = 20)
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
