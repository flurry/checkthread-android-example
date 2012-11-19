package com.flurry.example.hnreader;

import java.io.IOException;
import java.util.List;

import org.checkthread.annotations.ThreadConfined;
import org.checkthread.annotations.ThreadName;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * This code may or may not have bugs in it. CheckThread will not raise an
 * error, but remember it can't prove the absence of bugs, only the presence of
 * them
 * 
 * @author aki
 * 
 */
public class HackerNewsActivityV3LessBuggy extends Activity
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
					String feed = IO.getHttp(kHackerNewsRssUrl);
					final List<String> titles = HackerNewsRSSParser.parseTitles(feed);
					HackerNewsActivityV3LessBuggy.this.runOnUiThread(new Runnable()
					{
						@ThreadConfined(ThreadName.MAIN)
						public void run()
						{
							for (String t : titles)
							{
								TextView textView = new TextView(HackerNewsActivityV3LessBuggy.this);
								textView.setText(t);
								linearLayout.addView(textView);
							}
						}
					});
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}.start();
	}
}
