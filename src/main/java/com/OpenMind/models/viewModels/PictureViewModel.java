package com.OpenMind.models.viewModels;

public class PictureViewModel {

    private String title;
    private String url;
    private String public_id;
    private boolean edit;

    public PictureViewModel(){}

    public PictureViewModel(String title, String url, String public_id) {
        this.title = title;
        this.url = url;
        this.public_id = public_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPublic_id() {
        return public_id;
    }

    public void setPublic_id(String public_id) {
        this.public_id = public_id;
    }

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }
}
