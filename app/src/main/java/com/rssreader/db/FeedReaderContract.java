package com.rssreader.db;

import android.provider.BaseColumns;

/**
 * Created by kshitij.sharma on 5/13/2016.
 */
public final class FeedReaderContract {

    protected static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedReaderEntry.TABLE_NAME;
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    protected static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedReaderEntry.TABLE_NAME + " (" +
                    FeedReaderEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedReaderEntry.COL_TITLE + TEXT_TYPE + COMMA_SEP +
                    FeedReaderEntry.COL_LINK + TEXT_TYPE + COMMA_SEP +
                    FeedReaderEntry.COL_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                    FeedReaderEntry.COL_LANGUAGE + TEXT_TYPE + COMMA_SEP +
                    FeedReaderEntry.COL_COPYRIGHT + TEXT_TYPE + COMMA_SEP +
                    FeedReaderEntry.COL_IMAGE_TITLE + TEXT_TYPE + COMMA_SEP +
                    FeedReaderEntry.COL_IMAGE_URL + TEXT_TYPE + COMMA_SEP +
                    FeedReaderEntry.COL_PUBDATE + TEXT_TYPE + COMMA_SEP +
                    FeedReaderEntry.COL_ITEMS + TEXT_TYPE +
                    " )";

    public FeedReaderContract() {
    }

    public static abstract class FeedReaderEntry implements BaseColumns {
        public static final String TABLE_NAME = "feedstable";
        public static final String COL_TITLE = "title";
        public static final String COL_LINK = "link";
        public static final String COL_DESCRIPTION = "description";
        public static final String COL_LANGUAGE = "language";
        public static final String COL_COPYRIGHT = "copyRight";
        public static final String COL_IMAGE_TITLE = "imageTitle";
        public static final String COL_IMAGE_URL = "imageUrl";
        public static final String COL_PUBDATE = "pubDate";
        public static final String COL_ITEMS = "items";
    }
}
