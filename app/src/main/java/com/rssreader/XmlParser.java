package com.rssreader;

import android.util.Log;
import android.util.Xml;

import com.rssreader.netutils.Constants;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;

/**
 * Created by kshitij.sharma on 5/12/2016.
 */
public class XmlParser {

    private static final String TAG = "XML Parser";
    private static XmlParser instance = new XmlParser();

    private XmlPullParser parser;

    private XmlParser() {
        Log.d(TAG, "Parser created ");
    }

    public static XmlParser getParser() {
        return instance;
    }

    public Channel parse(String xml) {
        try {
            parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_VALIDATION, true);
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(new StringReader(xml));
            parser.nextTag();
            return readFeed(parser);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Channel readFeed(XmlPullParser parser) throws IOException, XmlPullParserException {
        Channel mChannel = new Channel();

        parser.require(XmlPullParser.START_TAG, null, "channel");

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG)
                continue;

            String name = parser.getName();
            if (name.equals(Constants.Title)) {
                mChannel.setTitle(Channel.readText(parser));
            } else if (name.equals(Constants.Description)) {
                mChannel.setDescription(Channel.readText(parser));
            } else if (name.equals(Constants.Link)) {
                mChannel.setLink(Channel.readText(parser));
            }

        }
        return null;
    }

    private void checkTag(String name) {

    }

}
