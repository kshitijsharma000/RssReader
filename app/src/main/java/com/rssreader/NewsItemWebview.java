package com.rssreader;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.rssreader.utils.BaseActivity;

public class NewsItemWebview extends BaseActivity {

    private static final String TAG = "WebView";
    WebView webView;
    private String mUrl;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_news_item_webview);

        setupProgressBar();

        webView = (WebView) findViewById(R.id.newsItemWebView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new NewsWebViewClient());

        mUrl = getIntent().getStringExtra("link");
        webView.loadUrl(mUrl);

    }

    private void setupProgressBar(){
        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setCancelable(true);
        dialog.setMessage("Connecting to news.\nPlease wait");
        dialog.setTitle("Loading");
    }

    private void showProgressBar(){
        dialog.show();
    }

    private void hideProgressBar(){
        if(dialog.isShowing()){
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

    private class NewsWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d(TAG, "url host : " + Uri.parse(url) + " " + Uri.parse(url).getHost());
            Log.d(TAG,findKey(url));
            Log.d(TAG,findKey(mUrl));
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                hideProgressBar();
                finish();
                break;
        }
        return true;
    }
}
