package com.javaques.moviereviews.rss;

import android.util.Log;
import android.util.Xml;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anil on 29-Aug-15.
 */
public abstract class FeedXmlParser {
    private static final String ns = null;
    private static final String TAG = "FeedXmlParser";

    public abstract List<RSSItem> parse(InputStream in) throws XmlPullParserException, IOException;







    public String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }



    public void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
    public String getImgUrl(String str) {

        String imageUrl = null;
        if (str == null) {
            return null;
        }
        // 1. From string:
        Document doc = Jsoup.parse(str);
        // Then select images inside it:
        Elements images = doc.select("img");
        // Then iterate
        for (Element el : images) {
            imageUrl = el.attr("src");
        }
        return imageUrl;
    }
}
