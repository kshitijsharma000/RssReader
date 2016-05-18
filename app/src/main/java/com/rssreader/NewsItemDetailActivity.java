package com.rssreader;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.rssreader.netutils.Appcontroller;

public class NewsItemDetailActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "newsItemDetailActivity";

    ImageButton rightNav;
    ImageButton leftNav;
    FrameLayout contentFrameLayout;
    TextView mNewsItemTitle;
    TextView mNewsItemDesc;
    NetworkImageView mNewsItemImage;
    TextView mLinkMore;
    Intent intent;

    Animation animationOFF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_news_item_detail);

        getSupportActionBar().setTitle(R.string.app_name);

        rightNav = (ImageButton) findViewById(R.id.newsItemDetailRightNav);
        leftNav = (ImageButton) findViewById(R.id.newsItemDetailLeftNav);

        setNavigationAnim();
        rightNav.startAnimation(animationOFF);
        leftNav.startAnimation(animationOFF);

        rightNav.setOnClickListener(this);
        leftNav.setOnClickListener(this);

        contentFrameLayout = (FrameLayout) findViewById(R.id.newsItemDetailContentFrame);
        contentFrameLayout.setOnClickListener(this);

        mLinkMore = (TextView) findViewById(R.id.newsItemDetailClickMore);
        mLinkMore.setOnClickListener(this);

        mNewsItemTitle = (TextView) findViewById(R.id.newsItemDetailTitle);
        mNewsItemDesc = (TextView) findViewById(R.id.newsItemDetailDescription);
        mNewsItemImage = (NetworkImageView) findViewById(R.id.newsItemDetailThumbnail);

        intent = getIntent();
        mNewsItemTitle.setText(intent.getStringExtra("title"));
        mNewsItemDesc.setText(getDesc(intent.getStringExtra("desc")));

        String url = getImageUrl(intent.getStringExtra("desc"));
        if (url == null)
            mNewsItemImage.setImageResource(R.mipmap.jagran_icon);
        else
            mNewsItemImage.setImageUrl(url, Appcontroller.getmInstance().getImageLoader());
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_news_item_detail;
    }

    private String getImageUrl(String combinedStr) {
        if (combinedStr.indexOf('<') == -1 || combinedStr.indexOf('>') == -1)
            return null;
        else
            return combinedStr.substring(combinedStr.indexOf('<') + 1, combinedStr.indexOf('>'))
                    .split("=")[1].replace("_s","");
    }


    private String getDesc(String combinedStr) {
        int index = combinedStr.indexOf(">");
        if (combinedStr.length() > 1) {
            if (index == -1)
                return combinedStr;
            else
                return combinedStr.substring(combinedStr.indexOf('>') + 1, combinedStr.length());
        } else
            return "";
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
        switch (v.getId()) {
            case R.id.newsItemDetailRightNav:
                Log.d(TAG, "clicked right nav key");
                break;
            case R.id.newsItemDetailLeftNav:
                Log.d(TAG, "Clicked left Nav key");
                break;
            case R.id.newsItemDetailContentFrame:
                rightNav.setVisibility(View.VISIBLE);
                rightNav.setAlpha(1.0f);
                rightNav.startAnimation(animationOFF);

                leftNav.setVisibility(View.VISIBLE);
                leftNav.setAlpha(1.0f);
                leftNav.startAnimation(animationOFF);
                break;

            case R.id.newsItemDetailClickMore:
                Log.d(TAG, "open web view from here");
                Intent intent = new Intent(this,NewsItemWebview.class);
                intent.putExtra("link",this.intent.getStringExtra("link"));
                startActivity(intent);
                break;
        }
    }
}
