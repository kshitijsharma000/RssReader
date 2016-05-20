package com.rssreader;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.android.volley.VolleyError;
import com.rssreader.db.FeedReaderDBController;
import com.rssreader.netutils.DataRetriever;

import org.json.JSONObject;

public class BGPullService extends IntentService implements DataRetriever.DataListener {

    DataRetriever dataRetriever;
    Context context;
    FeedReaderDBController dbController;
    String mUrl;

    public BGPullService(Context context) {
        super("BGPullService");
        this.context = context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        dbController = new FeedReaderDBController(context);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            startDownloading(intent.getStringExtra("url"));
        }
    }

    private void startDownloading(String mUrl) {
        dataRetriever = new DataRetriever(this);
        dataRetriever.makeStringRequest(mUrl);
    }

    @Override
    public void requestStart() {

    }

    @Override
    public void dataRecieved(JSONObject jsonObject) {

    }

    @Override
    public void dataRecieved(final String stringObject) {
        XmlParser.getParser().parse(stringObject);
    }

    @Override
    public void error(VolleyError error) {

    }
}
