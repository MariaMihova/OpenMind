package com.OpenMind.models.bindingModels;

import com.OpenMind.utils.ValidateString;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Set;

public class ClientBindingModel {

    private String initials;
    private int age;
    private String gender;
    private String initialRequest;


    public ClientBindingModel(){}

    @NotNull
    @Size(min = 2, max = 3)
    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    @NotNull
    @Positive
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    @ValidateString(acceptedValues = {"MAN", "WOMAN", "LGBTIQ"})
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @NotNull
    @Size(min = 50)
    public String getInitialRequest() {
        return initialRequest;
    }

    public void setInitialRequest(String initialRequest) {
        this.initialRequest = initialRequest;
    }
}
