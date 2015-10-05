package com.javaques.moviereviews;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

public class ReviewActivity extends AppCompatActivity {

    private static final String TAG = "ReviewActivity";
    String fullReview;
    String movieTitle;
    String link;
    int websiteId;
    WebView webview;
    private Toolbar mToolbar;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_review);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //getting extras
        Bundle bundle = getIntent().getExtras();
        fullReview = bundle.getString("full_review");
        movieTitle = bundle.getString("movie_title");
        link = bundle.getString("link");
        websiteId = bundle.getInt("website_id");
        getSupportActionBar().setTitle(movieTitle);
        webview = (WebView) findViewById(R.id.webView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        display();

    }

    private void display() {

        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append("<head>");
        sb.append("<style>");
        sb.append("img{ max-width: 100%}");
        sb.append("</style>");
        sb.append("</head>");

        sb.append("<body>");
        sb.append("<head>");
        sb.append("<style>");
        sb.append("body{text-align: justify}");
        sb.append("</style>");
        sb.append("</head>");
        sb.append("<p>" + fullReview + "</p>");
        sb.append("</body>");
        sb.append("</html>");
        //Log.d(TAG, "" + fullReview);
        //Log.d(TAG, "" + link);



        webview.getSettings().setJavaScriptEnabled(true);
        progressBar.setProgress(0);
        final Activity activity = this;
        webview.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                // Activities and WebViews measure progress with different scales.
                // The progress meter will automatically disappear when we reach 100%
                //activity.setProgress(progress * 1000);
                progressBar.setProgress(progress);

            }
        });
        webview.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(activity, "Oh no! " + description, Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }
        });



        if (websiteId == WebsitesData.NDTV_MOVIES || websiteId == WebsitesData.NOW_RUNNING ) {

            webview.loadUrl(link);

        } else {

            webview.loadDataWithBaseURL(null, sb.toString(), "text/html", "utf-8", null);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_review, menu);
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
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
