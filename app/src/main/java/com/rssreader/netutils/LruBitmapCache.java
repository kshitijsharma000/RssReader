package com.rssreader.netutils;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by Kshitij on 11/7/2015.
 */
public class LruBitmapCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache {

    public LruBitmapCache(int sizeinKB) {
        super(sizeinKB);
    }

    public LruBitmapCache() {
        super(getDefaultLruCachesize());
    }

    /**
     * maxSize for caches that do not override {@link #sizeOf}, this is
     * the maximum number of entries in the cache. For all other caches,
     * this is the maximum sum of the sizes of the entries in this cache.
     */

    public static int getDefaultLruCachesize() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int Cachesize = maxMemory / 8;
        System.out.println("Cache size : " + Cachesize);
        return Cachesize;
    }

    @Override
    public Bitmap getBitmap(String url) {
        return get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        put(url, bitmap);
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight() / 1024;
    }

}
