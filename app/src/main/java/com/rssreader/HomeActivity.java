package com.rssreader;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.rssreader.netutils.DataRetriever;

import org.json.JSONObject;

public class HomeActivity extends AppCompatActivity implements DataRetriever.DataListener {

    private static final String TAG = "HomeActivity";
    DataRetriever mDataRetriever;
    XmlParser xmlParser;
    TextView textView;

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;

    String url = "http://rss.jagran.com/rss/news/national.xml";
    //String url = "http://rss.jagran.com/local/uttar-pradesh/kanpur-city.xml";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mDataRetriever = new DataRetriever(this);
        // mDataRetriever.makeStringRequest(url);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.pager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager) {
        ReaderPagerAdapter pagerAdapter = new ReaderPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
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
    public void dataRecieved(final String stringObject) {
        Log.d(TAG, "data recieved : " + stringObject.toString());
        xmlParser = XmlParser.getParser();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                xmlParser.parse(stringObject);
            }
        });
        thread.start();

        textView.setText(stringObject.toString());
    }

    @Override
    public void error(VolleyError error) {
        Log.d(TAG, "Error occured : " + error.getMessage());
    }
}
