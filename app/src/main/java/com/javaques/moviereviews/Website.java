package com.javaques.moviereviews;

/**
 * Created by Anil on 01-Sep-15.
 */
public class Website {
    private int id;
    private int logo;
    private String title;
    private String url;

    public Website(int id, int logo, String title, String url) {
        this.id = id;
        this.logo = logo;
        this.title = title;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
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
}
