
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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class WebImageUtil
{
    public Thread download(String url_string)
    {
        URL url = null;
        
        BufferedInputStream bistream = null;
        BufferedOutputStream bostream = null;
        
        final String PATH = new File("").getAbsolutePath();
        
        File file = null;
        
        try
        {
            url = new URL(url_string);
            
            bistream = new BufferedInputStream(url.openStream(), 16 * 1024);
            
            String filename = url_string.substring(1 + url_string.lastIndexOf("/"));
            
            file = new File(PATH + Const.IMAGES_DIR + filename);
            
            bostream = new BufferedOutputStream(new FileOutputStream(file), 16 * 1024);
            
            byte[] data = new byte[4 * 1024];
            
            int len = -1;
            
            while((len = bistream.read(data)) != -1)
            {
                bostream.write(data, 0, len);
            }
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(bostream != null)
            {
                try
                {
                    bostream.flush();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    try
                    {
                        bostream.close();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            
            if(bistream != null)
            {
                try
                {
                    bistream.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        
        return null;
    }
}
