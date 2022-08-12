package com.OpenMind.models.bindingModels;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ContactsBindingModel {

    private String country;
    private String city;
    private String phoneNumber;
    private String email;

    public ContactsBindingModel(){}

    @NotNull(message = "Country can not be empty!")
    @Size(min = 2, max = 20, message = "The size of country name must be between 2 and 20 characters.")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @NotNull(message = "City can not be empty!")
    @Size(min = 2, max = 20, message = "The size of city name must be between 2 and 20 characters.")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    @NotBlank(message = "Please, enter phone number!")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @NotBlank(message = "Please, enter email address!")
    @Email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
