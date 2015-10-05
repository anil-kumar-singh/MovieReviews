package com.javaques.moviereviews;

import android.os.AsyncTask;
import android.os.PersistableBundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.javaques.moviereviews.rss.BHXmlParser;
import com.javaques.moviereviews.rss.FeedXmlParser;
import com.javaques.moviereviews.rss.FilmfareXmlParser;
import com.javaques.moviereviews.rss.IndianExpressXmlParser;
import com.javaques.moviereviews.rss.MidDayXmlParser;
import com.javaques.moviereviews.rss.NDTVXmlParser;
import com.javaques.moviereviews.rss.NowRunningXmlParser;
import com.javaques.moviereviews.rss.RSSItem;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MoviesActivity extends AppCompatActivity {
    private static final String TAG = "MoviesActivity";
    private Toolbar mToolbar;
    RecyclerView recyclerView;
    MoviesAdapter adapter;
    List<Movie> data;
    FeedXmlParser parser;
    InputStream input;
    int websiteId;
    String websiteTitle;
    String feedUrl;
    ProgressBar progressBar;
    TextView refresh;

    public static final String STATE_WEBSITE_ID = "website_id";
    public static final String STATE_WEBSITE_NAME = "website_name";
    public static final String STATE_WEBSITE_URL = "feed_url";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        refresh = (TextView) findViewById(R.id.refresh);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Check whether we're recreating a previously destroyed instance
        if (savedInstanceState != null) {
            Log.d(TAG, "not first time");
            // Restore value of members from saved state
            websiteId = savedInstanceState.getInt(STATE_WEBSITE_ID);
            websiteTitle = savedInstanceState.getString(STATE_WEBSITE_NAME);
            feedUrl = savedInstanceState.getString(STATE_WEBSITE_URL);
        } else {
            Log.d(TAG, "first time");
            Bundle extras = getIntent().getExtras();
            websiteId = extras.getInt("website_id");
            websiteTitle = extras.getString("website_name");
            feedUrl = extras.getString("feed_url");
        }


        getSupportActionBar().setTitle(websiteTitle);


        loadData();


        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });


    }

    private void loadData() {
        if (Util.isConnected(this)) {
            if (refresh.getVisibility() == View.VISIBLE) {
                refresh.setVisibility(View.GONE);
            }
            (new DownloadUrlTask()).execute(feedUrl);
        } else {
            if (progressBar.getVisibility() == View.VISIBLE) {
                progressBar.setVisibility(View.GONE);
            }
            if (refresh.getVisibility() == View.GONE) {
                refresh.setVisibility(View.VISIBLE);
            }
            Snackbar.make(mToolbar, "No Interner Connection available. Connect and try again.", Snackbar.LENGTH_LONG).show();
        }


    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putInt(STATE_WEBSITE_ID, websiteId);
        savedInstanceState.putString(STATE_WEBSITE_NAME, websiteTitle);
        savedInstanceState.putString(STATE_WEBSITE_URL, feedUrl);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }


    private void showMoviesList(List<RSSItem> items) {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        data = new ArrayList<Movie>();
        String description = null;
        String content = null;
        String link = null;

        String review = null;

        if (items != null) {
            for (RSSItem item : items) {
                description = item.getDescription(); // summury
                content = item.getContent();        // full story

                if (websiteId == WebsitesData.BOLLYWOOD_HUNGAMA || websiteId == WebsitesData.NDTV_MOVIES || websiteId == WebsitesData.NOW_RUNNING) {
                    review = description;
                }
                if (websiteId == WebsitesData.FILMFARE) {
                    review = content;
                }
                if (websiteId == WebsitesData.MID_DAY) {
                    review = "<b>" + description + "</b>" + content;
                }
                if (websiteId == WebsitesData.INDIAN_EXPRESS) {
                    review = content;
                }

                Log.d(TAG, "Review Link: " + item.getLink());
                data.add(new Movie(item.getPosterLink(), item.getTitle(), review, item.getLink()));
            }


            adapter = new MoviesAdapter(this, websiteId, data);
            recyclerView.setAdapter(adapter);
            StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(staggeredGridLayoutManager);
        } else {

            recyclerView.setVisibility(View.GONE);
            refresh.setVisibility(View.VISIBLE);


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movies, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        // Respond to the action bar's Up/Home button
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class DownloadUrlTask extends AsyncTask<String, Void, List<RSSItem>> {
        @Override
        protected void onPreExecute() {
            if (progressBar.getVisibility() == View.GONE) {
                progressBar.setVisibility(View.VISIBLE);
            }
            super.onPreExecute();
        }

        @Override
        protected List<RSSItem> doInBackground(String[] params) {

            if (websiteId == WebsitesData.BOLLYWOOD_HUNGAMA) {
                parser = new BHXmlParser();
            }
            if (websiteId == WebsitesData.FILMFARE) {
                parser = new FilmfareXmlParser();
            }
            if (websiteId == WebsitesData.MID_DAY) {
                parser = new MidDayXmlParser();
            }
            if (websiteId == WebsitesData.NDTV_MOVIES) {
                parser = new NDTVXmlParser();
            }
            if (websiteId == WebsitesData.INDIAN_EXPRESS) {
                parser = new IndianExpressXmlParser();
            }
            if (websiteId == WebsitesData.NOW_RUNNING) {
                parser = new NowRunningXmlParser();
            }
            List<RSSItem> items = null;

            try {
                InputStream in = downloadUrlStream(params[0]);
                if (in != null) {
                    items = parser.parse(in);
                }


            } catch (XmlPullParserException e) {
                Log.e(TAG, e.getMessage());
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }


            return items;
        }

        @Override
        protected void onPostExecute(List<RSSItem> rssItems) {
            if (progressBar.getVisibility() == View.VISIBLE) {
                progressBar.setVisibility(View.GONE);
            }
            showMoviesList(rssItems);
        }
    }

    private InputStream downloadUrlStream(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
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
