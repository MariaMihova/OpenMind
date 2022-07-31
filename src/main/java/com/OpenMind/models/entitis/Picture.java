package com.OpenMind.models.entitis;

import jdk.jfr.Enabled;

import javax.persistence.*;

@Entity
@Table(name = "pictures")
public class Picture extends BaseEntity {

    private String title;
    private String url;
    private String publicId;
    private UserEntity user;

    public Picture(){}

    @Column(nullable = false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Lob
    @Column(nullable = false)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Column(nullable = false)
    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    @OneToOne
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
