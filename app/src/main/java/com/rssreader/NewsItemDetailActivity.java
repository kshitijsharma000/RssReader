package com.rssreader;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.rssreader.netutils.Appcontroller;
import com.rssreader.utils.BaseActivity;

public class NewsItemDetailActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "newsItemDetailActivity";
    private final int REQUEST_CODE = 999;
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
        String mDesc = getDesc(intent.getStringExtra("desc"));

        mNewsItemDesc.setText(Html.fromHtml(mDesc));

        if (HostActivity.SUPPORTS_IMAGE) {
            String url = getImageUrl(intent.getStringExtra("desc"));
            if (url == null)
                mNewsItemImage.setImageResource(R.mipmap.jagran_icon);
            else
                mNewsItemImage.setImageUrl(url, Appcontroller.getmInstance().getImageLoader());
        } else {
            switch (HostActivity.mCurrentTab) {
                case Constants.CHANNEL_NAME_JAGRAN:
                    mNewsItemImage.setImageResource(R.mipmap.jagran_icon);
                    break;
                case Constants.CHANNEL_NAME_JAGRANJOSH:
                    mNewsItemImage.setImageResource(R.mipmap.jagranjosh);
            }
        }


    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_news_item_detail;
    }

    private String getImageUrl(String combinedStr) {
        //   Log.d(TAG, combinedStr);
        if ((combinedStr.indexOf('<') == -1 || combinedStr.indexOf('>') == -1)
                || combinedStr.substring(combinedStr.indexOf('<') + 1, combinedStr.indexOf('>')).length() < 2)
            return null;
        else
            return combinedStr.substring(combinedStr.indexOf('<') + 1, combinedStr.indexOf('>')).split("=")[1];
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, " detail option item selected");
        switch (item.getItemId()) {
            case R.id.menu_share:
                Log.d(TAG, "sharing button pressed");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private String getDesc(String combinedStr) {
        if (HostActivity.mCurrentTab == Constants.CHANNEL_NAME_JAGRAN) {
            int index = combinedStr.indexOf(">");
            if (combinedStr.length() > 1) {
                if (index == -1)
                    return combinedStr;
                else
                    return combinedStr.substring(combinedStr.indexOf('>') + 1, combinedStr.length());
            } else
                return "";
        }
        return combinedStr;
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
                Intent intent = new Intent(this, NewsItemWebview.class);
                intent.putExtra("link", this.intent.getStringExtra("link"));
                startActivityForResult(intent, REQUEST_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "On activity result called");
        if (requestCode == REQUEST_CODE) {
            Log.d(TAG, "Finished web view");
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_news_detail, menu);
        return true;
    }
}
