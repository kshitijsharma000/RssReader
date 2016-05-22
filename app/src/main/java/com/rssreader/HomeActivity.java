package com.rssreader;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.android.volley.VolleyError;
import com.rssreader.Model.Channel;
import com.rssreader.db.FeedReaderDBController;
import com.rssreader.netutils.DataRetriever;
import com.rssreader.utils.BaseActivity;
import com.rssreader.utils.XmlParser;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class HomeActivity extends BaseActivity {

    private static final String TAG = "HomeActivity";
    private static final int MAX_THREADS = 5;
    ArrayList<String> mChannelTypes;
    ArrayList<String> mUrls;
    Boolean updateRequired;

    TabLayout tabLayout;
    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        viewPager = (ViewPager) findViewById(R.id.pager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        mUrls = new ArrayList<>(Arrays.asList(Constants.JAGRAN_NATIONAL_URL, Constants.JAGRAN_WORLD_URL,
                Constants.JAGRAN_BUSINESS_URL, Constants.JAGRAN_SPORTS_URL, Constants.JAGRAN_ODDNEWS_URL));

        mChannelTypes = new ArrayList<>(Arrays.asList(Constants.JAGRAN_NATIONAL, Constants.JAGRAN_WORLD,
                Constants.JAGRAN_BUSINESS, Constants.JAGRAN_SPORTS, Constants.JAGRAN_ODDNEWS));

        startBackground();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("Homeactivity", "on create options menu");
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_home, menu);
        return true;
    }

    private void startBackground() {
        updateRequired = true;
        ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREADS);

        for (int i = 0; i < mUrls.size(); i++) {
            executorService.submit(new Worker(mUrls.get(i), mChannelTypes.get(i)));
        }

        executorService.shutdown();

        try {
            executorService.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_home;
    }

    private void setupViewPager(ViewPager viewPager) {
        ReaderPagerAdapter pagerAdapter = new ReaderPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "home activity on options selected");
        if (item.getItemId() == R.id.menu_settings) {
            Snackbar.make(tabLayout, "Oops!! Check Internet", Snackbar.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private class Worker implements Runnable, DataRetriever.DataListener {

        private String mUrl;
        private String mChannelType;
        private DataRetriever dataRetriever;
        private FeedReaderDBController dbController;
        private Channel mChannel;

        public Worker(String mUrl, String mChannelType) {
            this.mUrl = mUrl;
            this.mChannelType = mChannelType;
        }

        @Override
        public void run() {
            dataRetriever = new DataRetriever(this);
            dataRetriever.makeStringRequest(mUrl);

            dbController = new FeedReaderDBController(getApplicationContext());
        }

        @Override
        public void requestStart() {
            Log.d("worker", "request Started");
        }

        @Override
        public void dataRecieved(JSONObject jsonObject) {
        }

        @Override
        public void dataRecieved(String stringObject) {
            mChannel = XmlParser.getParser().parse(stringObject);
            if (mChannel != null && updateRequired) {
                dbController.updateDB();
                updateRequired = false;
            }
            dbController.insertChanneltoDB(mChannel, mChannelType);
        }

        @Override
        public void error(VolleyError error) {
        }
    }
}
