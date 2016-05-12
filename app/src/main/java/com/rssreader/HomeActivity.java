package com.rssreader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.rssreader.netutils.DataRetriever;

import org.json.JSONObject;

public class HomeActivity extends AppCompatActivity implements DataRetriever.DataListener {

    private static final String TAG = "HomeActivity";
    DataRetriever mDataRetriever;
    XmlParser parser;
    TextView textView;
    String url = "http://rss.jagran.com/rss/news/national.xml";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        textView = (TextView) findViewById(R.id.textview);
        mDataRetriever = new DataRetriever(this);

        mDataRetriever.makeStringRequest(url);
    }

    @Override
    public void requestStart() {
        Log.d(TAG, "Request started");
    }

    @Override
    public void dataRecieved(JSONObject jsonObject) {
        Log.d(TAG, "data recieved : " + jsonObject.toString());
    }

    @Override
    public void dataRecieved(String stringObject) {
        Log.d(TAG, "data recieved : " + stringObject.toString());
        parser = XmlParser.getParser();
        parser.parse(stringObject);

        textView.setText(stringObject.toString());
    }

    @Override
    public void error(VolleyError error) {
        Log.d(TAG, "Error occured : " + error.getMessage());
    }
}
