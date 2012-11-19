package com.flurry.example.hnreader;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;

public final class HackerNewsRSSParser
{
	static final String kLogTag = HackerNewsRSSParser.class.getSimpleName();

	private static final String ITEM = "item";
	private static final String TITLE = "title";
	private static final String LINK = "link";

	private static XmlPullParser parser;

	private HackerNewsRSSParser() {}

	public static List<String> parseTitles(String rss)
	{
		if(rss.length() < 1)
		{
			return Collections.emptyList();
		}
		if ((parser = getNewParser()) == null)
		{
			return null;
		}
		List<String> items = new ArrayList<String>();
		try
		{
			parser.setInput(new StringReader(rss));
			int event = parser.getEventType();
			while (event != XmlPullParser.END_DOCUMENT)
			{
				if(event == XmlPullParser.START_TAG && parser.getName().equalsIgnoreCase(ITEM))
				{
					items.add(parseItem(parser));
				}
				event = parser.next();
			}
		}
		catch (Exception e)
		{
			Log.e(kLogTag, e.toString());
		}

		return items;
	}

	private static String parseItem(XmlPullParser parser) throws XmlPullParserException, IOException
	{
		assert (parser.getEventType() == XmlPullParser.START_TAG);
		int event = parser.getEventType();
		event = parser.next();
		String title = "", link = "";
		while (!isAtTag(parser, XmlPullParser.END_TAG, ITEM))
		{
			if (isAtTag(parser, XmlPullParser.START_TAG, TITLE))
			{
				event = parser.next();
				if (event == XmlPullParser.TEXT)
				{
					title = parser.getText();
				}
			}
			else if (isAtTag(parser, XmlPullParser.START_TAG, LINK))
			{
				event = parser.next();
				if (event == XmlPullParser.TEXT)
				{
					link = parser.getText();
				}
			}

			event = parser.next();
		}
		return title;
	}

	private static boolean isAtTag(XmlPullParser parser, int type, String tag) throws XmlPullParserException
	{
		int event = parser.getEventType();
		String name = parser.getName();
		if (name == null)
		{
			return false;
		}
		else
		{
			return event == type && name.equalsIgnoreCase(tag);
		}
	}

	private static XmlPullParser getNewParser()
	{
		if (parser == null)
		{
			try
			{
				XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
				parser = factory.newPullParser();
			}
			catch (XmlPullParserException xe)
			{
				System.out.println(xe.toString());
			}
		}
		return parser;
	}
}