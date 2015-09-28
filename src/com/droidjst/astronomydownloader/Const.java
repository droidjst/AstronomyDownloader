
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

public class Const
{
    public final static String URL_NASA_APOD = "http://apod.nasa.gov/apod/";
    
    public final static String DB_NAME = "apod.db";
    
    public final static String FILE_SEP = File.separator;
    
    public final static String ARCHIVE_DIR = FILE_SEP + "archive" + FILE_SEP;
    public final static String IMAGE_DIR = FILE_SEP + "image" + FILE_SEP;
}
