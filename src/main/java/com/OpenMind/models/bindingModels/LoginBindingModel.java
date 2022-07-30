package com.OpenMind.models.bindingModels;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class LoginBindingModel {

    private String username;
    private String password;
    private boolean isNotValid;

    public LoginBindingModel(){}

    @NotBlank
    @Size(min= 3, max = 20, message = "Username must be between 3 and 20")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotBlank
    @Size(min= 3, max = 20, message = "Password must be between 3 and 20")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isNotValid() {
        return isNotValid;
    }

    public void setNotValid(boolean notValid) {
        isNotValid = notValid;
    }
}
