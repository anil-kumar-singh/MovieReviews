package com.javaques.moviereviews.rss;

import java.util.List;

/**
 * Created by Anil on 29-Aug-15.
 */
public class RSSFeed {
    // xml nodes
    String title;
    String description;
    String link;
    String generator;
    List<RSSItem> items;

    public RSSFeed(String title, String description, String link, String generator, List<RSSItem> items) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.generator = generator;
        this.items = items;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }

    public String getGenerator() {
        return generator;
    }

    public List<RSSItem> getItems() {
        return items;
    }
}
