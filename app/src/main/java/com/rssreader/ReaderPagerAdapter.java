package com.rssreader;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Kshitij on 5/13/2016.
 */
public class ReaderPagerAdapter extends FragmentStatePagerAdapter {

    private static final String TAG = "pagerAdapter";
    private ArrayList<String> mTitles;
    private ArrayList<String> mUrls;
    private ArrayList<String> mChannelTypes;


    public ReaderPagerAdapter(FragmentManager fm, ArrayList<String> mUrls, ArrayList<String> mChannelTypes, ArrayList<String> mTitles) {
        super(fm);
        this.mUrls = mUrls;
        this.mChannelTypes = mChannelTypes;
        this.mTitles = mTitles;
    }

    public void setItems(ArrayList<String> mUrls, ArrayList<String> mChannelTypes, ArrayList<String> mTitles) {
        this.mUrls = mUrls;
        this.mChannelTypes = mChannelTypes;
        this.mTitles = mTitles;
    }

    @Override
    public int getItemPosition(Object object) {
        Logger.print(TAG, "inside get item position");
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {
        Logger.print(TAG, "inside get fragment " + position);
        Fragment fragment = new ReaderFragment();
        Bundle args = new Bundle();
        args.putString("url", mUrls.get(position));
        args.putString("channeltype", mChannelTypes.get(position));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
       // Logger.print(TAG, "get count : " + mUrls.size());
        return mUrls.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Logger.print(TAG, "inside get title " + mTitles.get(position));
        return mTitles.get(position);
    }
}
