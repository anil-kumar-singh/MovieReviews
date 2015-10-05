package com.javaques.moviereviews;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anil on 01-Sep-15.
 */
public class WebsitesData {
    public static final int BOLLYWOOD_HUNGAMA = 1;
    public static final int FILMFARE = 2;
    public static final int MID_DAY = 3;
    public static final int NDTV_MOVIES = 4;
    public static final int INDIAN_EXPRESS = 5;
    public static final int NOW_RUNNING = 6;


    public static List<Website> getData() {
        int[] ids = {
                BOLLYWOOD_HUNGAMA,
                FILMFARE,
                MID_DAY,
                NDTV_MOVIES,
                INDIAN_EXPRESS,
                NOW_RUNNING
        };
        int[] logos = {
                R.drawable.logo_bollywood_hungama,
                R.drawable.logo_filmfare,
                R.drawable.logo_mid_day,
                R.drawable.logo_ndtv_movies,
                R.drawable.logo_the_indian_express,
                R.drawable.logo_now_running
        };
        String[] titles = {
                "Bollywood Hungama",
                "Filmfare",
                "Mid-Day",
                "NDTV Movies",
                "The Indian Express",
                "nowrunning.com"};
        String[] urls = {
                "http://www.bollywoodhungama.com/rss/movie_reviews.xml",
                "http://www.filmfare.com/feeds/feeds-reviews.xml",
                "http://www.mid-day.com/Resources/midday/rss/entertainment-reviews.xml",
                "http://feeds.feedburner.com/ndtvmovies-moviereviews?format=xml",
                "http://indianexpress.com/section/entertainment/movie-review/feed/",
                "http://www.nowrunning.com/cgi-bin/rss/reviews_hindi.xml"

        };
        List<Website> websites = new ArrayList<Website>();
        for (int i = 0; i < logos.length; i++) {
            websites.add(new Website(ids[i], logos[i], titles[i], urls[i]));
        }

        return websites;
    }
}
