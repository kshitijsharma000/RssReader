package com.rssreader;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;

/**
 * Created by kshitij.sharma on 5/12/2016.
 */
public class XmlParser {

    private static final String TAG = "XML Parser";
    private static XmlParser instance = new XmlParser();

    private XmlPullParserFactory factory;
    private XmlPullParser xpp;

    private XmlParser() {
        Log.d(TAG, "Parser created ");
    }

    public static XmlParser getParser() {
        return instance;
    }

    public void parse(String xml) {
        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);

            xpp = factory.newPullParser();
            xpp.setInput(new StringReader(xml));

            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_DOCUMENT) {

                    System.out.println("Start document");

                } else if (eventType == XmlPullParser.START_TAG) {

                    System.out.println("Start Tag :: " + xpp.getName());

                } else if (eventType == XmlPullParser.END_TAG) {

                    System.out.println("End Tag :: " + xpp.getName());

                } else if (eventType == XmlPullParser.TEXT) {

                    System.out.println("Text :: " + xpp.getText());

                }
                eventType = xpp.next();
            }
            System.out.println("End document");
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
