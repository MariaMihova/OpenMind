package com.OpenMind.models.entitis;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "articles")
public class Article extends BaseEntity {

    private String title;
    private String content;
    private LocalDate created;
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

    @Lob
    @Column(nullable = false, length = 10000)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(nullable = false)
    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
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
