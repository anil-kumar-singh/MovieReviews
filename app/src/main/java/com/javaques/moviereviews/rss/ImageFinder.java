package com.javaques.moviereviews.rss;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Anil on 04-Oct-15.
 */
public class ImageFinder {

    private static final String URL = "http://www.nowrunning.com/cgi-bin/rss/moviestills_hindi.xml";

    public String find(String name){

        return null;
    }

    private InputStream downloadUrlStream() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(URL)
                .build();
        InputStream stream = null;
        Response response = null;

        try {
            response = client.newCall(request).execute();
            stream = response.body().byteStream();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        return stream;

    }
}
