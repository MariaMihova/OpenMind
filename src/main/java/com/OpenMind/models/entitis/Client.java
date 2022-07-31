package com.OpenMind.models.entitis;

import com.OpenMind.models.enums.Gender;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "clients")
public class Client extends BaseEntity{


    private String initials;
    private int age;
    private Gender gender;
    private String initialRequest;


    public Client(){}


    @Column(nullable = false)
    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    @Column(nullable = false)
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Enumerated(EnumType.STRING)
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Lob
    @Column(name = "initial_request", nullable = false)
    public String getInitialRequest() {
        return initialRequest;
    }

    public void setInitialRequest(String initialRequest) {
        this.initialRequest = initialRequest;
    }

}
