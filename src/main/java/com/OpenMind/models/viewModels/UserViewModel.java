package com.OpenMind.models.viewModels;

import java.util.List;

public class UserViewModel {

    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private List<ArticleVewModel> articles;
    private PictureViewModel picture;
    private boolean isOwner;

    public UserViewModel(){}

    public UserViewModel(Long id, String firstName, String lastName, String username) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return String.format("%s %s", this.getFirstName(), this.getLastName());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<ArticleVewModel> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticleVewModel> articles) {
        this.articles = articles;
    }

    public PictureViewModel getPicture() {
        return picture;
    }

    public void setPicture(PictureViewModel picture) {
        this.picture = picture;
    }

    public boolean isOwner() {
        return isOwner;
    }

    public void setOwner(boolean owner) {
        isOwner = owner;
    }
}
