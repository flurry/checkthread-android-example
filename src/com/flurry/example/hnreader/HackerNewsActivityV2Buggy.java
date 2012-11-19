package com.flurry.example.hnreader;

import java.io.IOException;
import java.util.List;

import org.checkthread.annotations.ThreadConfined;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * This code also has a bug in it. CheckThread will catch the bug
 * 
 * @author aki
 *
 */
public class HackerNewsActivityV2Buggy extends Activity
{
	private String kHackerNewsRssUrl = "http://news.ycombinator.com/rss";

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		final LinearLayout linearLayout = new LinearLayout(this);
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		setContentView(linearLayout);

		new Thread() {
			@ThreadConfined("other")
			public void run()
			{
				try
				{
					// doing IO off the UI thread -- ok
					String feed = IO.getHttp(kHackerNewsRssUrl);
					List<String> titles = HackerNewsRSSParser.parseTitles(feed);
					for (String t : titles)
					{
						// updating the UI from off the UI thread -- this MAY blow up, so you shouldn't do it
						TextView textView = new TextView(HackerNewsActivityV2Buggy.this);
						textView.setText(t);
						linearLayout.addView(textView);
					}
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}.start();
	}
}
