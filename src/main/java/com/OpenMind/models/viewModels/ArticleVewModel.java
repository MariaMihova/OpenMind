package com.OpenMind.models.viewModels;

public class ArticleVewModel {

    private Long id;
    private String title;
    private String summery;
    private String username;

    public ArticleVewModel() {
    }

    public ArticleVewModel(Long id, String title, String summery, String username) {
        this.id = id;
        this.title = title;
        this.summery = summery;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummery() {
        return summery;
    }

    public void setSummery(String summery) {
        this.summery = summery;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String toString(){
        StringBuilder builder = new StringBuilder();

        builder.append(this.getTitle())
                .append(System.lineSeparator())
                .append(this.getSummery())
                .append("...")
                .append(System.lineSeparator())
                .append("By ")
                .append(this.getUsername());
        return builder.toString();
    }
}
