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
    public static String mCurrentTab;
    public static boolean SUPPORTS_IMAGE;
    public static boolean mDebugEnabled;
    private ArrayList<String> mChannelTypes;
    private ArrayList<String> mUrls;
    private ArrayList<String> mTitles;
    private Boolean updateRequired;
    private TabLayout tabLayout;
    private DrawerLayout drawer;
    private ProgressBar progressBar;
    private ReaderPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDebugEnabled = true;

        //setContentView(R.layout.activity_host);
        //Default channel jagran feeds
        {
            mUrls = Constants.mUrlsJagran;
            mChannelTypes = Constants.mChannelTypesJagran;
            mTitles = Constants.mTitlesJagran;

            mCurrentTab = Constants.CHANNEL_NAME_JAGRAN;
            SUPPORTS_IMAGE = true;
            setCurrentTitle(Constants.CHANNEL_JAGRAN_TITLE);
        }

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
        drawer.openDrawer(GravityCompat.START);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //Default settings
        navigationView.setCheckedItem(R.id.nav_jagranHindi);

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        setupViewPager(viewPager, mUrls, mChannelTypes, mTitles);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        progressBar = (ProgressBar) findViewById(R.id.progressView);
        progressBar.setIndeterminate(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Logger.print("Homeactivity", "on create options menu");
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_home, menu);
        return true;
    }

    private void setCurrentTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Logger.print(TAG, "home activity on options selected");
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

        switch (id) {
            case R.id.nav_jagranHindi:
                if (mCurrentTab.equals(Constants.CHANNEL_NAME_JAGRAN)) {
                    mUrls = Constants.mUrlsJagran;
                    mChannelTypes = Constants.mChannelTypesJagran;
                    mTitles = Constants.mTitlesJagran;
                    SUPPORTS_IMAGE = true;

                    mCurrentTab = Constants.CHANNEL_NAME_JAGRAN;
                    setCurrentTitle(Constants.CHANNEL_JAGRAN_TITLE);

                    pagerAdapter.setItems(mUrls, mChannelTypes, mTitles);
                    Logger.print(TAG, "Selected jagran hindi tab");

                    pagerAdapter.notifyDataSetChanged();
                    tabLayout.setTabsFromPagerAdapter(pagerAdapter);
                }
                break;
            case R.id.nav_jagranJosh:
                if (mCurrentTab.equals(Constants.CHANNEL_NAME_JAGRANJOSH)) {
                    mUrls = Constants.mUrlsJagranJosh;
                    mChannelTypes = Constants.mChannelTypesJagranJosh;
                    mTitles = Constants.mTitlesJagranJosh;
                    SUPPORTS_IMAGE = false;

                    mCurrentTab = Constants.CHANNEL_NAME_JAGRANJOSH;
                    setCurrentTitle(Constants.CHANNEL_JAGRANJOSH_TITLE);

                    pagerAdapter.setItems(mUrls, mChannelTypes, mTitles);
                    Logger.print(TAG, "Selected jagran josh tab");

                    pagerAdapter.notifyDataSetChanged();
                    tabLayout.setTabsFromPagerAdapter(pagerAdapter);
                }
                break;

            case R.id.nav_dainikBhaskar:
                if (mCurrentTab.equals(Constants.CHANNEL_NAME_DAINIKBHASKAR)) {
                    mUrls = Constants.mUrlsDainikBhaskar;
                    mChannelTypes = Constants.mChannelTypesDainikBhaskar;
                    mTitles = Constants.mTitlesDainikBhaskar;
                    SUPPORTS_IMAGE = false;

                    mCurrentTab = Constants.CHANNEL_NAME_DAINIKBHASKAR;
                    setCurrentTitle(Constants.CHANNEL_DAINIKBHASKAR_TITLE);

                    pagerAdapter.setItems(mUrls, mChannelTypes, mTitles);
                    Logger.print(TAG, "Selected dainik bhaskar tab");

                    pagerAdapter.notifyDataSetChanged();
                    tabLayout.setTabsFromPagerAdapter(pagerAdapter);
                }
                break;
            case R.id.nav_share:
                break;
            case R.id.nav_send:
                break;
            default:
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void setupViewPager(ViewPager viewPager, ArrayList<String> mUrls, ArrayList<String> mChannelTypes, ArrayList<String> mTitles) {
        pagerAdapter = new ReaderPagerAdapter(getSupportFragmentManager(), mUrls, mChannelTypes, mTitles);
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
            Logger.print("worker", "request Started");
        }

        @Override
        public void dataRecieved(JSONObject jsonObject) {
        }

        @Override
        public void dataRecieved(final String stringObject) {
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
