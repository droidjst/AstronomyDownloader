
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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;

public class FileUtil
{
    public static String getContents(String uri) throws IOException
    {
        File file;
        
        BufferedReader breader = null;
        
        StringBuilder string = null;
        
        int len;
        char[] cbuf;
        
        try
        {
            file = new File(uri);
            
            breader = new BufferedReader(new FileReader(file), 16 * 1024);
            
            string = new StringBuilder();
            
            len = -1;
            
            cbuf = new char[4 * 1024];
            
            while((len = breader.read(cbuf)) != -1)
            {
                string.append(cbuf, 0, len);
            }
            
            string.trimToSize();
        }
        catch (FileNotFoundException e)
        {
            throw e;
        }
        catch (IOException e)
        {
            throw e;
        }
        finally
        {
            if(breader != null)
            {
                try
                {
                    breader.close();
                }
                catch (IOException e)
                {
                    throw e;
                }
            }
        }
        
        return string.toString();
    }
    
    public static void setContents(String uri, String contents) throws IOException
    {
        File file;
        
        BufferedWriter bwriter = null;
        
        StringReader sreader = null;
        
        int len;
        char[] cbuf;
        
        try
        {
            file = new File(uri);
            
            bwriter = new BufferedWriter(new FileWriter(file), 8 * 1024);
            
            sreader = new StringReader(contents);
            
            len = -1;
            
            cbuf = new char[4 * 1024];
            
            while((len = sreader.read(cbuf)) != -1)
            {
                bwriter.write(cbuf, 0, len);
            }
        }
        catch (IOException e)
        {
            throw e;
        }
        finally
        {
            if(bwriter != null)
            {
                try
                {
                    bwriter.flush();
                }
                catch (IOException e)
                {
                    throw e;
                }
                finally
                {
                    try
                    {
                        bwriter.close();
                    }
                    catch (IOException e)
                    {
                        throw e;
                    }
                }
            }
            
            if(sreader != null)
            {
                sreader.close();
            }
        }
    }
}
