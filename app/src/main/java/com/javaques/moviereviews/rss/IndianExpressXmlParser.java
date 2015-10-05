package com.javaques.moviereviews.rss;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by deadman on 18/9/15.
 */
public class IndianExpressXmlParser extends FeedXmlParser{
    private static final String ns = null;
    private static final String TAG = "IndianExpress";
    @Override
    public List<RSSItem> parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            parser.nextTag();
            return readFeedItems(parser);
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

    private List<RSSItem> readFeedItems(XmlPullParser parser) throws XmlPullParserException, IOException {


        List entries = new ArrayList();
        parser.require(XmlPullParser.START_TAG, ns, "channel");

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("item")) {
                entries.add(readItem(parser));
            } else {
                skip(parser);
            }
        }
        return entries;

    }
    private RSSItem readItem(XmlPullParser parser) throws XmlPullParserException, IOException {

        parser.require(XmlPullParser.START_TAG, ns, "item");
        String title = null;
        String link = null;
        String description = null;
        String author = null;
        String pubDate = null;
        String posterLink = null;
        String content = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            //Log.d(TAG, "item: " + name);
            if (name.equals("title")) {
                title = readTitle(parser);
            } else if (name.equals("link")) {
                link = readLink(parser);
            } else if (name.equals("description")) {
                description = readDescription(parser);

            }else if (name.equals("content:encoded")) {
                content = readContent(parser);
            } else if (name.equals("media:thumbnail")) {
                posterLink = readMediaContentImage(parser);
            } else if (name.equals("author")) {
                author = readAuthor(parser);
            } else if (name.equals("pubDate")) {
                pubDate = readPubDate(parser);
            }else {
                skip(parser);
            }

        }
        Log.d(TAG, "" + posterLink);
        return new RSSItem(title, posterLink,link, description,content, author, pubDate);
    }

    // Processes image url tags in the feed.
    private String readMediaContentImage(XmlPullParser parser) throws IOException, XmlPullParserException {
        String link = "";
        parser.require(XmlPullParser.START_TAG, ns, "media:thumbnail");
        String tag = parser.getName();

        if (tag.equals("media:thumbnail")) {

                link = parser.getAttributeValue(null, "url");
                parser.nextTag();
        }

        parser.require(XmlPullParser.END_TAG, ns, "media:thumbnail");
        return link;
    }

    // Processes title tags in the feed.
    private String readTitle(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "title");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "title");
        return title;
    }

    // Processes link tags in the feed.
    private String readLink(XmlPullParser parser) throws IOException, XmlPullParserException {
        String link = "";

        parser.require(XmlPullParser.START_TAG, ns, "link");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "link");

        return link;
    }

    private String readDescription(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "description");
        String description = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "description");
        return description;
    }
    private String readContent(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "content:encoded");
        String content = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "content:encoded");
        return content;
    }


    private String readPubDate(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "pubDate");
        String pubDate = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "pubDate");
        return pubDate;
    }


    private String readAuthor(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "author");
        String author = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "author");
        return author;
    }
    // Processes title tags in the feed.
    private String readGenerator(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "generator");
        String generator = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "generator");
        return generator;
    }
}
