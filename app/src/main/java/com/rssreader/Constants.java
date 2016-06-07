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


    /*
    * 1st channel added dainik jagran
    * By default all available channels are present
    * User will be given an option to enable or disable tabs
    */
    public static final String CHANNEL_NAME_JAGRAN = "jagran";
    public static final String CHANNEL_JAGRAN_TITLE = "दैनिक जागरण";

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


    /*
    * 2nd channel added Jagran Josh
    * By default all available channels are present
    * User will be given an option to enable or disable tabs
    */
    public static final String CHANNEL_NAME_JAGRANJOSH = "jagranjosh";
    public static final String CHANNEL_JAGRANJOSH_TITLE = "जागरण जोश";

    public static final String JAGRAN_JOSH_ARTICLE_URL = "http://www.jagranjosh.com/rss/josh/article_hindi.xml";
    public static final String JAGRAN_JOSH_CURRENTAFFAIRS_URL = "http://www.jagranjosh.com/rss/josh/current_affairs_hindi.xml";

    public static final String JAGRANJOSH_ARTICLE = "joshArticles";
    public static final String JAGRANJOSH_CURRENT = "joshCurrent";

    public static final ArrayList<String> mUrlsJagranJosh = new ArrayList<>(Arrays.asList(Constants.JAGRAN_JOSH_ARTICLE_URL, Constants.JAGRAN_JOSH_CURRENTAFFAIRS_URL));

    public static final ArrayList<String> mChannelTypesJagranJosh = new ArrayList<>(Arrays.asList(Constants.JAGRANJOSH_ARTICLE, Constants.JAGRANJOSH_CURRENT));

    public static final ArrayList<String> mTitlesJagranJosh = new ArrayList<>(Arrays.asList("आर्टिकल्स", "करंट अफेयर्स"));


    /*
    * 3rd channel added dainik bhaskar
    * By default all available channels are present
    * User will be given an option to enable or disable tabs
    */
    public static final String CHANNEL_NAME_DAINIKBHASKAR = "dainikbhaskar";
    public static final String CHANNEL_DAINIKBHASKAR_TITLE = "दैनिक भास्कर";

    public static final String DAINIKBHASKAR_TOPNEWS_URL = "http://www.bhaskar.com/rss-feed/521/";
    public static final String DAINIKBHASKAR_INTERNATIONAL_URL = "http://www.bhaskar.com/rss-feed/2338/";
    public static final String DAINIKBHASKAR_NATIONALNEWS_URL = "http://www.bhaskar.com/rss-feed/2322/";
    public static final String DAINIKBHASKAR_BHASKARSPECIAL_URL = "http://www.bhaskar.com/rss-feed/4587/";
    public static final String DAINIKBHASKAR_EDITORIAL_URL = "http://www.bhaskar.com/rss-feed/2089/";
    public static final String DAINIKBHASKAR_MAGAZINE_URL = "http://www.bhaskar.com/rss-feed/1057/";
    public static final String DAINIKBHASKAR_ENTERTAINMENT_URL = "http://www.bhaskar.com/rss-feed/3998/";
    public static final String DAINIKBHASKAR_GADGETS_URL = "http://www.bhaskar.com/rss-feed/5707/";
    public static final String DAINIKBHASKAR_HUMOR_URL = "http://www.bhaskar.com/rss-feed/4867/";
    public static final String DAINIKBHASKAR_REG_BHOPAL_URL = "http://www.bhaskar.com/rss-feed/1700/";
    public static final String DAINIKBHASKAR_REG_INDORE_URL = "http://www.bhaskar.com/rss-feed/1701/";
    public static final String DAINIKBHASKAR_REG_JAIPUR_URL = "http://www.bhaskar.com/rss-feed/1709/";
    public static final String DAINIKBHASKAR_REG_JODHPUR_URL = "http://www.bhaskar.com/rss-feed/1710/";
    public static final String DAINIKBHASKAR_REG_UDAIPUR_URL = "http://www.bhaskar.com/rss-feed/1711/";
    public static final String DAINIKBHASKAR_REG_RAIPUR_URL = "http://www.bhaskar.com/rss-feed/1719/";
    public static final String DAINIKBHASKAR_REG_LUDHIANA_URL = "http://www.bhaskar.com/rss-feed/1728/";
    public static final String DAINIKBHASKAR_REG_CHANDIGARH_URL = "http://www.bhaskar.com/rss-feed/1738/";
    public static final String DAINIKBHASKAR_REG_MP_URL = "http://www.bhaskar.com/rss-feed/1739/";
    public static final String DAINIKBHASKAR_REG_RAJASTHAN_URL = "http://www.bhaskar.com/rss-feed/1740/";
    public static final String DAINIKBHASKAR_REG_CHATTISGARH_URL = "http://www.bhaskar.com/rss-feed/1741/";
    public static final String DAINIKBHASKAR_REG_HARAYANA_URL = "http://www.bhaskar.com/rss-feed/1742/";
    public static final String DAINIKBHASKAR_REG_PUNJAB_URL = "http://www.bhaskar.com/rss-feed/1743/";
    public static final String DAINIKBHASKAR_REG_HP_URL = "http://www.bhaskar.com/rss-feed/1744/";
    public static final String DAINIKBHASKAR_REG_DELHI_URL = "http://www.bhaskar.com/rss-feed/1756/";
    public static final String DAINIKBHASKAR_REG_GUJARAT_URL = "http://www.bhaskar.com/rss-feed/2313/";
    public static final String DAINIKBHASKAR_REG_MAHARASHTRA_URL = "http://www.bhaskar.com/rss-feed/2318/";
    public static final String DAINIKBHASKAR_REG_JHARKHAND_URL = "http://www.bhaskar.com/rss-feed/3682/";
    public static final String DAINIKBHASKAR_REG_BIHAR_URL = "http://www.bhaskar.com/rss-feed/3679/";
    public static final String DAINIKBHASKAR_REG_RANCHI_URL = "http://www.bhaskar.com/rss-feed/4845/";

    public static final String DAINIKBHASKAR_TOPNEWS = "bhaskarTopnews";
    public static final String DAINIKBHASKAR_INTERNATIONAL = "bhaskarInternational";
    public static final String DAINIKBHASKAR_NATIONAL = "bhaskarNational";
    public static final String DAINIKBHASKAR_BHASKARSPECIAL = "bhaskarSpecial";
    public static final String DAINIKBHASKAR_EDITORIAL = "bhaskarEditorial";
    public static final String DAINIKBHASKAR_MAGAZINE = "bhaskarMagazine";
    public static final String DAINIKBHASKAR_ENTERTAINMENT = "bhaskarEntertainment";
    public static final String DAINIKBHASKAR_GADETS = "bhaskarGadgets";
    public static final String DAINIKBHASKAR_HUMOR = "bhaskarHumor";
    public static final String DAINIKBHASKAR_BHOPAL = "bhaskarBhopal";
    public static final String DAINIKBHASKAR_INDORE = "bhaskarIndore";
    public static final String DAINIKBHASKAR_JAIPUR = "bhaskarJaipur";
    public static final String DAINIKBHASKAR_JODHPUR = "bhaskarJodhpur";
    public static final String DAINIKBHASKAR_UDAIPUR = "bhaskarUdaipur";
    public static final String DAINIKBHASKAR_RAIPUR = "bhaskarRaipur";
    public static final String DAINIKBHASKAR_LUDHIANA = "bhaskarLudhiana";
    public static final String DAINIKBHASKAR_CHANDIGARH = "bhaskarChandigarh";
    public static final String DAINIKBHASKAR_MP = "bhaskarMP";
    public static final String DAINIKBHASKAR_RAJASTHAN = "bhaskarRajasthan";
    public static final String DAINIKBHASKAR_CHATTISGARH = "bhaskarChattisgarh";
    public static final String DAINIKBHASKAR_HARAYANA = "bhaskarHarayana";
    public static final String DAINIKBHASKAR_PUNJAB = "bhaskarPunjab";
    public static final String DAINIKBHASKAR_HP = "bhaskarHP";
    public static final String DAINIKBHASKAR_DELHI = "bhaskarDelhi";
    public static final String DAINIKBHASKAR_GUJARAT = "bhaskarGujarat";
    public static final String DAINIKBHASKAR_MAHARASHTRA = "bhaskarMaharashtra";
    public static final String DAINIKBHASKAR_JHARKHAND = "bhaskarJharkhand";
    public static final String DAINIKBHASKAR_BIHAR = "bhaskarBihar";
    public static final String DAINIKBHASKAR_RANCHI = "bhaskarRanchi";

    public static final ArrayList<String> mUrlsDainikBhaskar = new ArrayList<>(
            Arrays.asList(DAINIKBHASKAR_TOPNEWS_URL, DAINIKBHASKAR_INTERNATIONAL_URL, DAINIKBHASKAR_NATIONALNEWS_URL,
                    DAINIKBHASKAR_BHASKARSPECIAL_URL, DAINIKBHASKAR_EDITORIAL_URL, DAINIKBHASKAR_MAGAZINE_URL,
                    DAINIKBHASKAR_ENTERTAINMENT_URL, DAINIKBHASKAR_GADGETS_URL, DAINIKBHASKAR_HUMOR_URL
            ));

    public static final ArrayList<String> mUrlsDainikBhaskar_reg = new ArrayList<>(Arrays.asList(
            DAINIKBHASKAR_REG_BHOPAL_URL, DAINIKBHASKAR_REG_INDORE_URL, DAINIKBHASKAR_REG_JAIPUR_URL, DAINIKBHASKAR_REG_JODHPUR_URL, DAINIKBHASKAR_REG_UDAIPUR_URL, DAINIKBHASKAR_REG_RAIPUR_URL,
            DAINIKBHASKAR_REG_LUDHIANA_URL, DAINIKBHASKAR_REG_CHANDIGARH_URL, DAINIKBHASKAR_REG_MP_URL, DAINIKBHASKAR_REG_RAJASTHAN_URL, DAINIKBHASKAR_REG_CHATTISGARH_URL, DAINIKBHASKAR_REG_HARAYANA_URL,
            DAINIKBHASKAR_REG_PUNJAB_URL, DAINIKBHASKAR_REG_HP_URL, DAINIKBHASKAR_REG_DELHI_URL, DAINIKBHASKAR_REG_GUJARAT_URL, DAINIKBHASKAR_REG_MAHARASHTRA_URL, DAINIKBHASKAR_REG_JHARKHAND_URL,
            DAINIKBHASKAR_REG_BIHAR_URL, DAINIKBHASKAR_REG_RANCHI_URL));

    public static final ArrayList<String> mChannelTypesDainikBhaskar = new ArrayList<>(
            Arrays.asList(DAINIKBHASKAR_TOPNEWS, DAINIKBHASKAR_INTERNATIONAL, DAINIKBHASKAR_NATIONAL, DAINIKBHASKAR_BHASKARSPECIAL, DAINIKBHASKAR_EDITORIAL, DAINIKBHASKAR_MAGAZINE,
                    DAINIKBHASKAR_ENTERTAINMENT, DAINIKBHASKAR_GADETS, DAINIKBHASKAR_HUMOR));


    public static final ArrayList<String> mChannelTypesDainikBhaskar_reg = new ArrayList<>(
            Arrays.asList(DAINIKBHASKAR_BHOPAL, DAINIKBHASKAR_INDORE, DAINIKBHASKAR_JAIPUR, DAINIKBHASKAR_JODHPUR, DAINIKBHASKAR_UDAIPUR, DAINIKBHASKAR_RAIPUR,
                    DAINIKBHASKAR_LUDHIANA, DAINIKBHASKAR_CHANDIGARH, DAINIKBHASKAR_MP, DAINIKBHASKAR_RAJASTHAN, DAINIKBHASKAR_CHATTISGARH, DAINIKBHASKAR_HARAYANA,
                    DAINIKBHASKAR_PUNJAB, DAINIKBHASKAR_HP, DAINIKBHASKAR_DELHI, DAINIKBHASKAR_GUJARAT, DAINIKBHASKAR_MAHARASHTRA, DAINIKBHASKAR_JHARKHAND,
                    DAINIKBHASKAR_BIHAR, DAINIKBHASKAR_RANCHI));

    public static final ArrayList<String> mTitlesDainikBhaskar = new ArrayList<>(Arrays.asList("मुख्य समाचार", "अंतरराष्ट्रीय", "राष्ट्रीय", "भास्कर विशेष", "संपादकीय", "पत्रिका", "मनोरंजन", "गैजेट", "मनोवृत्ति"));

    public static final ArrayList<String> mTitlesDainikBhaskar_reg = new ArrayList<>(Arrays.asList("भोपाल", "इंदौर", "जयपुर", "जोधपुर", "उदयपुर", "रायपुर", "लुधिआना", "चण्डीगढ़", "मध्य प्रदेश", "राजस्थान", "छत्तीसगढ़", "हरियाणा", "पंजाब", "हिमाचल प्रदेश", "दिल्ली", "गुजरात", "महाराष्ट्र", "झारखण्ड", "बिहार", "राँची"));

}
