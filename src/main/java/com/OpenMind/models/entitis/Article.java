package com.OpenMind.models.entitis;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "articles")
public class Article extends BaseEntity {

    private String title;
    private String content;
    private LocalDate date;
    private ProfessionalField professionalField;
    private UserEntity user;


    public Article(){}

    @Column(nullable = false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(nullable = false, columnDefinition = "TEXT")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(nullable = false)
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @ManyToOne
    public ProfessionalField getProfessionalField() {
        return professionalField;
    }

    public void setProfessionalField(ProfessionalField professionalField) {
        this.professionalField = professionalField;
    }

    @ManyToOne
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
