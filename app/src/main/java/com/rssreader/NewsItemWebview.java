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
    private String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_news_item_webview);

        getSupportActionBar().setTitle(R.string.app_name);

        webView = (WebView) findViewById(R.id.newsItemWebView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new NewsWebViewClient());

        mUrl = getIntent().getStringExtra("link");
        webView.loadUrl(mUrl);

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

    }

}
