package com.javaques.moviereviews.rss;



/**
 * Created by Anil on 29-Aug-15.
 */
public class RSSItem {
    private String title;
    private String posterLink;
    private String link;
    private String description;
    private String content;
    private String author;
    private String pubDate;

    public RSSItem() {
    }

    /* public RSSItem(String title, String link, String description, String author, String pubDate) {
         this.title = title;
         this.link = link;
         this.description = description;
         this.author = author;
         this.pubDate = pubDate;
     }*/
    public RSSItem(String title, String posterLink, String link, String description, String content, String author, String pubDate) {
        this.title = title;
        this.posterLink = posterLink;
        this.link = link;
        this.description = description;
        this.content = content;
        this.author = author;
        this.pubDate = pubDate;
    }

    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterLink() {
        return posterLink;
    }

    public void setPosterLink(String posterLink) {
        this.posterLink = posterLink;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }



}
