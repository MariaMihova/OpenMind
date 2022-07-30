package com.OpenMind.models.viewModels;

public class ClientViewModel {

    private  Long id;
    private String initials;
    private int age;
    private String initialRequest;

    public ClientViewModel(Long id, String initials, int age, String initialRequest) {
        this.id = id;
        this.initials = initials;
        this.age = age;
        this.initialRequest = initialRequest;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getInitialRequest() {
        return initialRequest;
    }

    public void setInitialRequest(String initialRequest) {
        this.initialRequest = initialRequest;
    }
}
