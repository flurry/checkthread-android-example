package com.flurry.example.hnreader;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * This code has a bug in it. CheckThread will catch the bug
 * 
 * @author aki
 * 
 */
public class HackerNewsActivityV1Buggy extends Activity
{
	private String kHackerNewsRssUrl = "http://news.ycombinator.com/rss";

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		LinearLayout linearLayout = new LinearLayout(this);
		setContentView(linearLayout);
		try
		{
			// doing IO on the UI thread -- oops
			String feed = IO.getHttp(kHackerNewsRssUrl);
			List<String> titles = HackerNewsRSSParser.parseTitles(feed);
			for (String t : titles)
			{
				// updating the UI from the UI thread -- ok
				TextView textView = new TextView(this);
				textView.setText(t);
				linearLayout.addView(textView);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
