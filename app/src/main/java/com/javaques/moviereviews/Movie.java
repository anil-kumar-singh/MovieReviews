package com.javaques.moviereviews;

/**
 * Created by Anil on 05-Sep-15.
 */
public class Movie {
    private String poster;
    private String name;
    private String review;
    private String link;

    public Movie(String poster, String name, String review, String link) {
        this.poster = poster;
        this.name = name;
        this.review = review;
        this.link = link;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
