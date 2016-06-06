package com.rssreader.utils;

import android.util.Log;
import android.util.Xml;

import com.rssreader.Constants;
import com.rssreader.HostActivity;
import com.rssreader.Model.Channel;
import com.rssreader.Model.Channel.Image;
import com.rssreader.Model.Channel.Item.Guid;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by kshitij.sharma on 5/12/2016.
 */
public class XmlParser {

    private static final String TAG = "XML Parser";
    private static XmlParser instance = new XmlParser();
    Channel mChannel = null;
    Channel.Item mItem = null;
    private XmlPullParser parser;

    private XmlParser() {
        Log.d(TAG, "Parser created ");
    }

    public static XmlParser getParser() {
        return instance;
    }

    public synchronized Channel parse(String xml) {
        Log.d(TAG, "parse called : ");
        try {
            parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(new StringReader(xml));
            Log.d(TAG, parser.getEventType() + " ");
            parser.next();
            Log.d(TAG, parser.getEventType() + " " + parser.getName());
            parser.next();

            //parser modification required only for Jagran josh
            if (HostActivity.mCurrentTab == Constants.CHANNEL_NAME_JAGRANJOSH)
                parser.next();

            Log.d(TAG, parser.getEventType() + " " + parser.getName());
            return readFeed(parser);
        } catch (XmlPullParserException e) {
            Log.d(TAG, " xml parse exception : " + e.toString());
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Channel readFeed(XmlPullParser parser) throws IOException, XmlPullParserException {
        mChannel = new Channel();
        ArrayList<Channel.Item> items = new ArrayList();

        //  Log.d(TAG, parser.getEventType() + " ");
        parser.require(XmlPullParser.START_TAG, null, "channel");

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG)
                continue;

            String name = parser.getName();
            //     Log.d(TAG, name);
            if (name.equals(Constants.Title)) {
                mChannel.setTitle(readTag(parser, Constants.Title));
            } else if (name.equals(Constants.Description)) {
                mChannel.setDescription(readTag(parser, Constants.Description));
            } else if (name.equals(Constants.Language)) {
                mChannel.setDescription(readTag(parser, Constants.Language));
            } else if (name.equals(Constants.Link)) {
                mChannel.setLink(readTag(parser, Constants.Link));
            } else if (name.equals(Constants.Image)) {
                mChannel.setImage(readImage(parser));
            } else if (name.equals(Constants.Item)) {
                //   Log.d(TAG, "inside read Feed, adding item");
                items.add(readItem(parser));
                //   Log.d(TAG, "inside read Feed, added item : " + items.size());
            } else {
                skip(parser);
            }
        }
        mChannel.setItems(items);
        return mChannel;
    }

    private Channel.Item readItem(XmlPullParser parser) throws IOException, XmlPullParserException {
        //   Log.d(TAG, "inside read item ");
        parser.require(XmlPullParser.START_TAG, null, "item");

        mItem = mChannel.new Item();

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            //     Log.d(TAG, "read item : " + name);

            if (name.equals(Constants.Title)) {
                mItem.setTitle(readTag(parser, Constants.Title));
            } else if (name.equals(Constants.Link)) {
                mItem.setLink(readTag(parser, Constants.Link));
            } else if (name.equals(Constants.Description)) {
                mItem.setDescription(readTag(parser, Constants.Description));
            } else if (name.equals(Constants.PubDate)) {
                mItem.setPubDate(readTag(parser, Constants.PubDate));
            } else if (name.equals(Constants.Guid)) {
                mItem.setGuid(readGuid(parser));
            } else {
                skip(parser);
            }
        }
        //  Log.d(TAG, "read item done");
        return mItem;
    }

    private Guid readGuid(XmlPullParser parser) throws IOException, XmlPullParserException {
        //  Log.d(TAG, "inside read Guid");
        parser.require(XmlPullParser.START_TAG, null, Constants.Guid);
        String tag = parser.getName();
        Guid guid = null;
        if (tag.equals(Constants.Guid)) {
            //Reading Guid attributes..
            guid = mItem.new Guid();
            guid.setAttrisPermaLink(parser.getAttributeValue(null, Constants.IsPermanLink));
            guid.setVal(readText(parser));
        }
        //parser.nextTag();
        parser.require(XmlPullParser.END_TAG, null, Constants.Guid);
        //   Log.d(TAG, "read Guid done : " + guid.getVal() + " " + guid.getAttrisPermaLink());
        return guid;
    }


    private String readTag(XmlPullParser parser, String mtagName) throws IOException, XmlPullParserException {
        //   Log.d(TAG, "inside read tag : " + mtagName);
        parser.require(XmlPullParser.START_TAG, null, mtagName);
        String text = readText(parser);
        parser.require(XmlPullParser.END_TAG, null, mtagName);
        //   Log.d(TAG, "read tag done : " + text);
        return text;
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = null;
        //   Log.d(TAG, "inside read text");
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        //   Log.d(TAG, "read text done : " + result);
        return result;
    }

    private Image readImage(XmlPullParser parser) throws IOException, XmlPullParserException {
        //  Log.d(TAG, "read Image ");
        parser.require(XmlPullParser.START_TAG, null, "image");

        Image image = mChannel.new Image();

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            //      Log.d(TAG, "read image" + name);
            if (name.equals(Constants.Title)) {
                image.setTitle(readTag(parser, Constants.Title));
            } else if (name.equals(Constants.Url)) {
                image.setUrl(readTag(parser, Constants.Url));
            } else if (name.equals(Constants.Link)) {
                image.setLink(readTag(parser, Constants.Link));
            } else {
                skip(parser);
            }
        }
        return image;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        //  Log.d(TAG, ">> skip called..");
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }


}
