package com.droidjst.astronomydownloader;

import java.io.File;

public class Driver
{
	public static void main(String[] args)
	{
		Database database = new Database();
		
		if(database.isAvailable() == false)
		{
			boolean created = database.createSQLiteTable();
			
			if(created)
			{
				DatabaseUtil dbutil = new DatabaseUtil();
				
				boolean completed = dbutil.addBasicArchive();
				
				if(completed)
				{
					System.out.println("entires= " + database.getCount());
				}
				else
				{
					// unable to add archive to database
				}
			}
			else
			{
				// unable to create database
			}
		}
		else
		{
			DatabaseUtil dbutil = new DatabaseUtil();
			
			JsoupUtil jsouputil = new JsoupUtil();
			
			File archive_dir = new File(new File("").getAbsolutePath() + Const.ARCHIVE_DIR);
			
			if(archive_dir.exists() == false)
			{
				archive_dir.mkdirs();
			}
			
			String[] urls = dbutil.getURLs();
			
			String html;
			
			String image_src;
			String credit;
			String explanation;
			
			int index = 258;
			
			String url;
			
			while(index < urls.length)
			{
				url = urls[index];
				
				/*
				 * html = jsouputil.savePage(Const.URL_NASA_APOD + url, url);
				 */
				
				html = jsouputil.getHTML(Const.URL_NASA_APOD + url);
				
				if(html == null)
				{
					System.out.println("url data null= " + url);
					
					continue;
				}
				
				image_src = HTMLUtil.getImageSource(html);
				credit = HTMLUtil.getImageCredit(html);
				explanation = HTMLUtil.getExplanation(html);
				
				System.out.println("--------");
				
				dbutil.addExtendedArchiveItem(image_src, url.substring(2, 8), credit, explanation);
				
				index++;
			}
			
		}
	}
	
	
}
