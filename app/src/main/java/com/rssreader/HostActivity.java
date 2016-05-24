package com.rssreader;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.VolleyError;
import com.rssreader.Model.Channel;
import com.rssreader.db.FeedReaderDBController;
import com.rssreader.netutils.DataRetriever;
import com.rssreader.utils.BaseActivity;
import com.rssreader.utils.XmlParser;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class HostActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, ReaderFragment.UpdateListener {

    private static final String TAG = "HostActivity";
    private static final int MAX_THREADS = 5;

    ArrayList<String> mChannelTypes;
    ArrayList<String> mUrls;
    String mChannelName;
    ArrayList<String> mTitles;

    Boolean updateRequired;

    TabLayout tabLayout;
    ViewPager viewPager;
    DrawerLayout drawer;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_host);
        //Todo code for settings channel specific values;
        setupValues(Constants.CHANNEL_NAME_JAGRAN);

        startBackground();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,
                toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        viewPager = (ViewPager) findViewById(R.id.pager);
        setupViewPager(viewPager, mUrls, mChannelTypes, mTitles);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        progressBar = (ProgressBar) findViewById(R.id.progressView);
        progressBar.setIndeterminate(true);
    }

    private void setupValues(String mChannelName) {
        switch (mChannelName) {
            case Constants.CHANNEL_NAME_JAGRAN:
                mUrls = Constants.mUrlsJagran;
                mChannelTypes = Constants.mChannelTypesJagran;
                mTitles = Constants.mTitlesJagran;
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("Homeactivity", "on create options menu");
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "home activity on options selected");
        if (item.getItemId() == R.id.menu_settings) {
            Snackbar.make(tabLayout, "Settings not done yet. WIP", Snackbar.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void setupViewPager(ViewPager viewPager, ArrayList<String> mUrls, ArrayList<String> mChannelTypes, ArrayList<String> mTitles) {
        ReaderPagerAdapter pagerAdapter = new ReaderPagerAdapter(getSupportFragmentManager(), mUrls, mChannelTypes, mTitles);
        viewPager.setAdapter(pagerAdapter);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_host;
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
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
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
