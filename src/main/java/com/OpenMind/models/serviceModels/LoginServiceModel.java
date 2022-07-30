package com.OpenMind.models.serviceModels;

public class LoginServiceModel {

    private String username;
    private String password;

    public LoginServiceModel(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
