package com.rssreader;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageButton;

public class NewsItemDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "newsItemDetailActivity";
    Toolbar toolbar;
    ActionBar actionBar;
    ImageButton rightNav;
    ImageButton leftNav;
    FrameLayout contentFrameLayout;

    Animation animationOFF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_item_detail);

        toolbar = (Toolbar) findViewById(R.id.newsItemDetail_toolbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.app_name);

        rightNav = (ImageButton) findViewById(R.id.newsItemDetailRightNav);
        leftNav = (ImageButton) findViewById(R.id.newsItemDetailLeftNav);

        setNavigationAnim();
        rightNav.startAnimation(animationOFF);
        leftNav.startAnimation(animationOFF);

        rightNav.setOnClickListener(this);
        leftNav.setOnClickListener(this);

        contentFrameLayout = (FrameLayout) findViewById(R.id.newsItemDetailContentFrame);
        contentFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rightNav.setVisibility(View.VISIBLE);
                rightNav.setAlpha(1.0f);
                rightNav.startAnimation(animationOFF);

                leftNav.setVisibility(View.VISIBLE);
                leftNav.setAlpha(1.0f);
                leftNav.startAnimation(animationOFF);
            }
        });
    }

    private void setNavigationAnim() {
        animationOFF = new AlphaAnimation(1.0f, 0.0f);
        animationOFF.setDuration(2000);
        animationOFF.setStartOffset(2000);

        animationOFF.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                rightNav.setVisibility(View.GONE);
                leftNav.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.newsItemDetailRightNav:
                Log.d(TAG,"clicked right nav key");
                break;
            case R.id.newsItemDetailLeftNav:
                Log.d(TAG,"Clicked left Nav key");
                break;
        }
    }
}
