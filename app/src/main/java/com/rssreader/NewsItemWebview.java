package com.rssreader;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.rssreader.utils.BaseActivity;

public class NewsItemWebview extends BaseActivity {

    private static final String TAG = "WebView";
    WebView webView;
    private String mUrl;
    private ProgressDialog dialog;
    private Switch navSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_news_item_webview);

        setupProgressBar();

        webView = (WebView) findViewById(R.id.newsItemWebView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(true);
        webSettings.setLoadsImagesAutomatically(false);
        webSettings.setBlockNetworkImage(true);

        webView.setWebViewClient(new NewsWebViewClient());

        mUrl = getIntent().getStringExtra("link");
        webView.loadUrl(mUrl);

        navSwitch = (Switch) findViewById(R.id.nav_switch);
        navSwitch.setTextOn("On");
        navSwitch.setTextOff("Off");

        navSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Snackbar.make(navSwitch, "Web navigation turned ON", Snackbar.LENGTH_LONG).show();
                } else
                    Snackbar.make(navSwitch, "Web navigation turned OFF", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void setupProgressBar() {
        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setCancelable(true);
        dialog.setMessage("Connecting to news.\nPlease wait");
        dialog.setTitle("Loading");
    }

    private void showProgressBar() {
        dialog.show();
    }

    private void hideProgressBar() {
        if (dialog.isShowing()) {
            dialog.hide();
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_news_item_webview;
    }

    private String findKey(String in) {
        String[] arr = in.split("/");
        return in.split("/")[arr.length - 1];
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                hideProgressBar();
                finish();
                return true;
            case R.id.webViewNavigationSwitch:
                Log.d(TAG, "web navigation clicked");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class NewsWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d(TAG, "url menu_host : " + Uri.parse(url) + " " + Uri.parse(url).getHost());
            Log.d(TAG, findKey(url));
            Log.d(TAG, findKey(mUrl));
            if (findKey(url).equals(findKey(mUrl))) {
                return false;
            }
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            showProgressBar();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            hideProgressBar();
        }
    }
}
