package com.rssreader.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rssreader.Logger;
import com.rssreader.Model.Channel;

import java.util.ArrayList;

/**
 * Created by kshitij.sharma on 5/20/2016.
 */
public class FeedReaderDBController {
    private static final String TAG = "FeedReaderDBCOntroller";
    private FeedReaderDbHelper mDbHelper;
    private SQLiteDatabase db;
    private Context context;

    public FeedReaderDBController(Context context) {
        this.context = context;
        mDbHelper = new FeedReaderDbHelper(context);
    }

    private void getWritableDatabase() throws SQLiteException {
        db = mDbHelper.getWritableDatabase();
    }

    private void getReadableDatabase() throws SQLiteException {
        db = mDbHelper.getReadableDatabase();
    }

    public void closeDB() {
        db.close();
    }

    public void updateDB() {
        getWritableDatabase();
        mDbHelper.onUpgrade(db, FeedReaderDbHelper.DATABASE_VERSION, FeedReaderDbHelper.DATABASE_VERSION);
    }

    public Long insertChanneltoDB(Channel mChannel, String mChannelType) {
        if (mChannel == null || mChannelType == null || mChannelType.equals(""))
            return -1L;

        long newRowId;
        ContentValues values = new ContentValues();
        Gson gson = new Gson();

        getWritableDatabase();

        //Should specify the current channel.. i.e. national, world, sports etc..
        values.put(FeedReaderContract.FeedReaderEntry.COL_TITLE, mChannelType);
        values.put(FeedReaderContract.FeedReaderEntry.COL_DESCRIPTION, mChannel.getTitle());
        values.put(FeedReaderContract.FeedReaderEntry.COL_LANGUAGE, mChannel.getTitle());
        values.put(FeedReaderContract.FeedReaderEntry.COL_LINK, mChannel.getTitle());
        values.put(FeedReaderContract.FeedReaderEntry.COL_IMAGE_TITLE, mChannel.getTitle());
        values.put(FeedReaderContract.FeedReaderEntry.COL_IMAGE_URL, mChannel.getTitle());
        values.put(FeedReaderContract.FeedReaderEntry.COL_COPYRIGHT, mChannel.getTitle());
        values.put(FeedReaderContract.FeedReaderEntry.COL_PUBDATE, mChannel.getTitle());

        values.put(FeedReaderContract.FeedReaderEntry.COL_ITEMS, gson.toJsonTree(mChannel.getItems()).toString());

        long ifExisted = db.update(FeedReaderContract.FeedReaderEntry.TABLE_NAME, values,
                FeedReaderContract.FeedReaderEntry.COL_TITLE + "=?", new String[]{mChannelType});

        if (ifExisted <= 0) {
            newRowId = db.insert(FeedReaderContract.FeedReaderEntry.TABLE_NAME, null, values);
            Logger.print(TAG, "written new row to DB : " + newRowId);
            db.close();
            return newRowId;
        } else {
            Logger.print(TAG, "Record updated : " + ifExisted);
        }
        db.close();
        return ifExisted;
    }

    public Channel readChannelFromDB(String mChannelType) {

        Channel mChannel;
        getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from " + FeedReaderContract.FeedReaderEntry.TABLE_NAME
                + " where " + FeedReaderContract.FeedReaderEntry.COL_TITLE
                + "=" + "\"" + mChannelType + "\"" + ";", null);

        Logger.print(TAG, "Cursor returned : " + cursor.getCount() + " values");
        if (cursor.getCount() == 0)
            return null;

        cursor.moveToFirst();
        mChannel = new Channel();
        Channel.Image mImage = mChannel.new Image();
        Gson gson = new Gson();

        mChannel.setTitle(cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedReaderEntry.COL_TITLE)));
        mChannel.setDescription(cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedReaderEntry.COL_DESCRIPTION)));
        mChannel.setLanguage(cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedReaderEntry.COL_LANGUAGE)));
        mChannel.setLink(cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedReaderEntry.COL_LINK)));

        mImage.setTitle(cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedReaderEntry.COL_IMAGE_TITLE)));
        mImage.setUrl(cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedReaderEntry.COL_IMAGE_URL)));

        mChannel.setImage(mImage);
        mChannel.setCopyRight(cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedReaderEntry.COL_COPYRIGHT)));
        mChannel.setPubDate(cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedReaderEntry.COL_PUBDATE)));

        String mItemsStr = cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedReaderEntry.COL_ITEMS));

        ArrayList<Channel.Item> items = gson.fromJson(mItemsStr, new TypeToken<ArrayList<Channel.Item>>() {
        }.getType());

        Logger.print(TAG, "Items found in DB : " + items.size());

        mChannel.setItems(items);

        cursor.close();
        db.close();
        return mChannel;
    }
}
