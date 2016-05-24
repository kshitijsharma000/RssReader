package com.rssreader;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Kshitij on 5/13/2016.
 */
public class ReaderPagerAdapter extends FragmentPagerAdapter {

    private static final int NUM = 10;
    private ArrayList<String> mTitles;
    private ArrayList<String> mUrls;
    private ArrayList<String> mChannelTypes;


    public ReaderPagerAdapter(FragmentManager fm, ArrayList<String> mUrls, ArrayList<String> mChannelTypes, ArrayList<String> mTitles) {
        super(fm);
        this.mUrls = mUrls;
        this.mChannelTypes = mChannelTypes;
        this.mTitles = mTitles;
    }
    
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new ReaderFragment();
        Bundle args = new Bundle();
        args.putString("url", mUrls.get(position));
        args.putString("channeltype", mChannelTypes.get(position));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return mUrls.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
