package com.flurry.example.hnreader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.checkthread.annotations.ThreadConfined;

@ThreadConfined("other")
public final class IO
{
	private IO() {}

	static String getHttp(String url) throws IOException
	{
		InputStream in = null;
		StringBuilder builder = new StringBuilder();
		try
		{
			HttpURLConnection http = (HttpURLConnection) new URL(url).openConnection();
			http.setRequestMethod("GET");
			in = http.getInputStream();
			int code = http.getResponseCode();
			if (code >= 200 && code < 300)
			{
				Reader reader = new InputStreamReader(in, "UTF-8");
				char[] buffer = new char[1024];
				int len = 0;
				while ((len = reader.read(buffer)) > 0)
				{
					builder.append(buffer, 0, len);
				}
			}
		}
		finally
		{
			if(null != in)
			{
				in.close();
			}
		}
		return builder.toString();
	}

}
