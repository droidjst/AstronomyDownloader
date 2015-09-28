
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
import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;

public class Driver
{
    private static Database database;
    private static DatabaseUtil dbutil;
    private static JsoupUtil jsouputil;
    private static ImageUtil imageutil;
    
    private static Exception exception;
    
    private static void init()
    {
        database = new Database();
        dbutil = new DatabaseUtil();
        
        jsouputil = new JsoupUtil();
        imageutil = new ImageUtil();
    }
    
    public static void main(String[] args)
    {
        init();
        
        if(database.isAvailable() == false)
        {
            try
            {
                database.createSQLiteTable();
                
                dbutil.addBasicArchive();
            }
            catch (SQLException e)
            {
                exception = e;
            }
            
            if(exception != null)
            {
                System.err.println(exception.getMessage());
                
                System.exit(1);
            }
        }
        
        createApplicationFolders();
        
        downloadImages();
    }
    
    private static void downloadImages()
    {
        String html;
        
        String image_src;
        String credit;
        String explanation;
        
        int index = 0;
        
        String uri = new File("").getAbsolutePath() + Const.FILE_SEP + "current_index";
        
        try
        {
            String contents = FileUtil.getContents(uri);
            
            index = Integer.parseInt(contents) - 1;
        }
        catch (IOException e)
        {
            
        }
        
        System.out.println("starting at index " + index);
        
        String url;
        
        String[] urls = dbutil.getURLs();
        
        final String PATH = new File("").getAbsolutePath();
        
        String temp;
        
        while(index < urls.length)
        {
            url = urls[index];
            
            html = jsouputil.getHTML(Const.URL_NASA_APOD + url);
            
            image_src = HTMLUtil.getImageSource(html);
            credit = HTMLUtil.getImageCredit(html);
            explanation = HTMLUtil.getExplanation(html);
            
            imageutil.download(image_src);
            
            int[] width_height = imageutil.getImageDimensions(PATH + Const.FILE_SEP + image_src);
            
            dbutil.addExtendedArchiveItem(image_src, width_height, url.substring(2, 8), credit, explanation);
            
            index++;
            
            if(index % 25 == 0)
            {
                try
                {
                    FileUtil.setContents(uri, Integer.toString(index));
                }
                catch (IOException e)
                {
                    System.out.println("count not set index to file");
                }
            }
        }
    }
    
    private static void createApplicationFolders()
    {
        File file;
        
        file = new File(new File("").getAbsolutePath() + Const.ARCHIVE_DIR);
        
        if(file.exists() == false)
        {
            file.mkdirs();
        }
        
        file = new File(new File("").getAbsolutePath() + Const.IMAGE_DIR);
        
        if(file.exists() == false)
        {
            file.mkdirs();
        }
    }
}
