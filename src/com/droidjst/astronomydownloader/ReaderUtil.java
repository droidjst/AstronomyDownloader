
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

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import javax.imageio.ImageReader;

public class ReaderUtil
{
    public static void finalizeReaders(Reader ... readers) throws IOException
    {
        for(Reader reader : readers)
        {
            if(reader != null)
            {
                try
                {
                    reader.close();
                }
                catch (IOException e)
                {
                    throw e;
                }
            }
        }
    }
    
    public static void finalizeWriters(Writer ... writers) throws IOException
    {
        for(Writer writer : writers)
        {
            if(writer != null)
            {
                try
                {
                    writer.flush();
                }
                catch (IOException e)
                {
                    throw e;
                }
                finally
                {
                    try
                    {
                        writer.close();
                    }
                    catch (IOException e)
                    {
                        throw e;
                    }
                }
            }
        }
    }
    
    public static class Image
    {
        public static void finalizeReaders(ImageReader ... ireaders) throws IOException
        {
            for(ImageReader reader : ireaders)
            {
                if(reader != null)
                {
                    reader.dispose();
                }
            }
        }
    }
}
