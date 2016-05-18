package com.rssreader;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class NewsItemWebview extends BaseActivity {

    private static final String TAG = "WebView";
    WebView webView;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_news_item_webview);

        getSupportActionBar().setTitle(R.string.app_name);

        webView = (WebView) findViewById(R.id.newsItemWebView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new NewsWebViewClient());

        url = getIntent().getStringExtra("link");
        webView.loadUrl(url);

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_news_item_webview;
    }

    private class NewsWebViewClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (Uri.parse(url).getHost().equals(url)) {
                Log.d(TAG,"")
                return false;
            }
            return true;
        }

    }
}
