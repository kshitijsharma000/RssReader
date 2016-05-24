package com.rssreader;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by kshitij.sharma on 5/12/2016.
 */
public final class Constants {
    public static final String Title = "title";
    public static final String Description = "description";
    public static final String Language = "language";
    public static final String Link = "link";
    public static final String Image = "image";
    public static final String Url = "url";
    public static final String Item = "item";
    public static final String PubDate = "pubDate";
    public static final String Guid = "guid";
    public static final String IsPermanLink = "isPermaLink";

    public static final String CHANNEL_NAME_JAGRAN = "jagran";

    public static final String JAGRAN_NATIONAL_URL = "http://rss.jagran.com/rss/news/national.xml";
    public static final String JAGRAN_WORLD_URL = "http://rss.jagran.com/rss/news/world.xml";
    public static final String JAGRAN_BUSINESS_URL = "http://rss.jagran.com/rss/news/business.xml";
    public static final String JAGRAN_SPORTS_URL = "http://rss.jagran.com/rss/news/sports.xml";
    public static final String JAGRAN_ODDNEWS_URL = "http://rss.jagran.com/rss/news/oddnews.xml";

    public static final String JAGRAN_NATIONAL = "jagranNational";
    public static final String JAGRAN_WORLD = "jagranWorld";
    public static final String JAGRAN_BUSINESS = "jagranBusiness";
    public static final String JAGRAN_SPORTS = "jagranSports";
    public static final String JAGRAN_ODDNEWS = "jagranOdd";

    public static final ArrayList<String> mUrlsJagran = new ArrayList<>(Arrays.asList(Constants.JAGRAN_NATIONAL_URL, Constants.JAGRAN_WORLD_URL,
            Constants.JAGRAN_BUSINESS_URL, Constants.JAGRAN_SPORTS_URL, Constants.JAGRAN_ODDNEWS_URL));

    public static final ArrayList<String> mChannelTypesJagran = new ArrayList<>(Arrays.asList(Constants.JAGRAN_NATIONAL, Constants.JAGRAN_WORLD,
            Constants.JAGRAN_BUSINESS, Constants.JAGRAN_SPORTS, Constants.JAGRAN_ODDNEWS));

    public static final ArrayList<String> mTitlesJagran = new ArrayList<>(Arrays.asList("राष्ट्रीय", "दुनिया", "बिजनेस", "खेल", "जरा हटके"));


    public static final String JAGRAN_JOSH_ARTICLE_URL = "http://www.jagranjosh.com/rss/josh/article_hindi.xml";
    public static final String JAGRAN_JOSH_CURRENTAFFAIRS_URL = "http://www.jagranjosh.com/rss/josh/current_affairs_hindi.xml";

}
