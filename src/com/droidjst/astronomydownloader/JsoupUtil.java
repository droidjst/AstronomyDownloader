
/*
    Copyright 2015 Joseph Tranquillo {name of copyright owner}

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
 */


package com.droidjst.astronomydownloader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupUtil
{
	public static int JSOUP_CALLS = 0;
	
	/*
	enum SaveMode
	{
		PER_DAY,
		PER_WEEK,
		PER_MONTH,
		PER_YEAR,
	}
	*/
	
	public String getHTML(String url)
	{
		Document jdoc = null;
		
		try
		{
			jdoc = Jsoup.connect(url).get();
			
			System.out.printf("DEBUG  > Jsoup Call # %d - JsoupUtil.getHTML %n", ++JSOUP_CALLS);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return jdoc == null ? null : jdoc.html();
	}
	
	public String getHTMLBody(String url)
	{
		Document jdoc = null;
		
			try
			{
				jdoc = Jsoup.connect(url).get();
				
				System.out.printf("DEBUG  > Jsoup Call # %d - JsoupUtil.getHTMLBody %n", ++JSOUP_CALLS);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		
		return jdoc == null ? null : jdoc.body().html();
	}
	
	public String getHTMLBodyText(String url)
	{
		Document jdoc = null;
		
			try
			{
				jdoc = Jsoup.connect(url).get();
				
				System.out.printf("DEBUG  > Jsoup Call # %d - JsoupUtil.getHTMLBodyText %n", ++JSOUP_CALLS);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		
		return jdoc == null ? null : jdoc.body().text();
	}
	
	public String savePage(String url, String filename)
	{
		FileOutputStream fostream = null;
		
		String html = null;
		
		try
		{
			Response response = Jsoup.connect(url).execute();
			
			System.out.printf("DEBUG  > Jsoup Call # %d - JsoupUtil.savePage %n", ++JSOUP_CALLS);
			
			String charset = response.charset();
			
			html = response.body();
			
			fostream = new FileOutputStream(new File("").getAbsolutePath() + Const.ARCHIVE_DIR + filename);
			
			fostream.write(html.getBytes(charset));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(fostream != null)
			{
				try
				{
					fostream.flush();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				finally
				{
					try
					{
						fostream.close();
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
				}
			}
		}
		
		return html;
	}
	
	public ArrayList<String[]> getHTMLRefs(String url)
	{
		Document jdoc;
		
		ArrayList<String[]> archive = null;
		
		try
		{
			jdoc = Jsoup.connect(url).get();
			
			System.out.printf("DEBUG  > Jsoup Call # %d - JsoupUtil.getHTMLRefs %n", ++JSOUP_CALLS);
			
			Elements links = jdoc.select("a[href~=ap[0-9]{6}\\.html]");
			
			archive = new ArrayList<>();
			
			for(Element href : links)
			{
				archive.add(new String[] { href.attr("href"), href.text() });
			}
			
			archive.trimToSize();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return archive;
	}
}
