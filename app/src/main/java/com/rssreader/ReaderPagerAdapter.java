package com.rssreader;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Kshitij on 5/13/2016.
 */
public class ReaderPagerAdapter extends FragmentStatePagerAdapter {

    private static final int NUM = 10;
    private ArrayList<String> mTitles;
    private ArrayList<String> mUrls;


    public ReaderPagerAdapter(FragmentManager fm) {
        super(fm);
        mUrls = new ArrayList<>(Arrays.asList(Constants.JAGRAN_NATIONAL, Constants.JAGRAN_WORLD,
                Constants.JAGRAN_BUSINESS, Constants.JAGRAN_SPORTS, Constants.JAGRAN_ODDNEWS));

        mTitles = new ArrayList<>(Arrays.asList("राष्ट्रीय", "दुनिया", "बिजनेस", "खेल", "जरा हटके"));
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new ReaderFragment();
        Bundle args = new Bundle();
        args.putString("url", mUrls.get(position));
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
