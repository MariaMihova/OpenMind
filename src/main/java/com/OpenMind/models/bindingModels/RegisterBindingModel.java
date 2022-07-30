package com.OpenMind.models.bindingModels;

import com.OpenMind.models.entitis.ProfessionalField;
import com.OpenMind.utils.ValidateString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RegisterBindingModel {

    private String username;
    private String rowPassword;
    private String confirmPassword;
    private String firstName;
    private String lastName;
    private String professionalField;

    public RegisterBindingModel(){}

    @NotNull(message = "Username can not be empty!")
    @Size(min = 2, max = 20)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotNull(message = "Password can not be empty!")
    @Size(min= 6)
    public String getRowPassword() {
        return rowPassword;
    }

    public void setRowPassword(String password) {
        this.rowPassword = password;
    }

    @NotBlank(message = "Password can not be empty!")
    @Size(min= 6)
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @NotBlank(message = "First name can not be empty!")
    @Size(min = 2, max = 20)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
//        System.out.println("Setter was called");
        this.firstName = firstName;
    }

    @NotBlank(message = "Last name can not be empty!")
    @Size(min = 2, max = 20)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
