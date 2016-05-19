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
        public static final String COL_TITLE = "title";
        public static final String COL_LINK = "link";
        public static final String COL_DESCRIPTION = "description";
        public static final String COL_LANGUAGE = "language";
        public static final String COL_COPYRIGHT = "copyRight";
        public static final String COL_IMAGE = "image";
        public static final String COL_PUBDATE = "pubDate";
        public static final String COL_ARR_NATIONAL = "itemsNational";
        public static final String COL_ARR_WORLD = "itemsWorld";
        public static final String COL_ARR_BUSINESS = "itemsBusiness";
        public static final String COL_ARR_SPORT = "itemsSport";
        public static final String COL_ARR_ODD = "itemOdd";
    }
}
