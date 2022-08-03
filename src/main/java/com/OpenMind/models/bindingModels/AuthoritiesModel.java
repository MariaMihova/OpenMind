package com.OpenMind.models.bindingModels;

import com.OpenMind.models.enums.Role;
import com.OpenMind.utils.ValidateString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AuthoritiesModel {

    private String username;
    private Role role;
    private boolean doseNotExist;

    public AuthoritiesModel(){
    }

    @NotNull(message = "Username can not be empty!")
    @Size(min = 2, max = 200)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotNull
    @ValidateString(acceptedValues = {"ADMIN", "USER"})
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isDoseNotExist() {
        return doseNotExist;
    }

    public void setDoseNotExist(boolean doseNotExist) {
        this.doseNotExist = doseNotExist;
    }
}
