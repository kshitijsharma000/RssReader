package com.rssreader;

import android.util.Log;

/**
 * Created by kshitij.sharma on 6/7/2016.
 */
public class Logger {
    public static void print(String className, String str) {
        if (HostActivity.mDebugEnabled) {
            Log.d(className, str);
        }
    }

    public static void print(int debugLevel, String str) {
        if (HostActivity.mDebugEnabled) {
            Log.d("Logger", str);
        }
    }
}
