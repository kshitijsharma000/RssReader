package com.rssreader;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class HomeActivity extends BaseActivity {

    private static final String TAG = "HomeActivity";

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_PROGRESS);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_home);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        setProgressBarIndeterminateVisibility(true);
        setProgressBarVisibility(true);

        viewPager = (ViewPager) findViewById(R.id.pager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_home;
    }

    private void setupViewPager(ViewPager viewPager) {
        ReaderPagerAdapter pagerAdapter = new ReaderPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
    }
}
