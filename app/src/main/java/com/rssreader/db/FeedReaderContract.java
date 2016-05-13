package com.rssreader.db;

import android.provider.BaseColumns;

/**
 * Created by kshitij.sharma on 5/13/2016.
 */
public final class FeedReaderContract {

    public FeedReaderContract(){
    }

    public static abstract class FeedReaderEntry implements BaseColumns{
        public static final String TABLE_NAME = "feedstable";
    }
}
