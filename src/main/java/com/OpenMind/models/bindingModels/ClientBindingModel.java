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

    @NotNull(message = "Initials can not be empty!")
    @Size(min = 2, max = 3, message = "The size of the initials must be between 2 and 3 characters")
    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    @NotNull(message = "Age can not be empty!")
    @Positive(message = "Age must be sa positive number.")
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

    @NotNull(message = "Initial Request can not be empty1")
    @Size(min = 20, message = "Initial Request must be at last 20 characters.")
    public String getInitialRequest() {
        return initialRequest;
    }

    public void setInitialRequest(String initialRequest) {
        this.initialRequest = initialRequest;
    }
}
