
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

import java.util.ArrayList;

import org.jsoup.Jsoup;

public class HTMLUtil
{
    private static final String EXPLANATION = "Explanation";
    private static final String TOMORROWS_PIC = "Tomorrow's picture";
    private static final String WE_KEEP_AN_ARCHIVE = "We keep an archive";
    
    private final static String[] CREDIT_STRINGS =
        {
            "Image Credit:",
            "Picture Credit:",
            "Credit and Copyright",
            "Credit & Copyright",
            "Credit",
            "Copyright",
            "Courtesy",
            "Image Data",
        };
    
    public static String getImageSource(String html)
    {
        return Jsoup.parse(html).select("a > img").attr("src");
    }
    
    private static ArrayList<Integer> indices;
    
    public static String getImageCredit(String html)
    {
        String text = Jsoup.parse(html).text();
        
        indices = new ArrayList<>();
        
        int credit_index = -1;
        int credit_length = -1;
        
        for(String credit : CREDIT_STRINGS)
        {
            indices.add(new Integer(credit_index = text.indexOf(credit)));
            
            if(credit_index != -1)
            {
                credit_length = credit.length();
                
                break;
            }
        }
        
        if(credit_index == -1)
        {
            return null;
        }
        
        int indexof_explanation = text.indexOf(EXPLANATION);
        
        String credit = null;
        
        credit = text.substring(credit_index + credit_length, indexof_explanation);
        
        credit = credit.replace("\n", " ");
        credit = credit.replace("[s]+", " ");
        credit = credit.trim();
        
        return credit;
    }
    
    public static String getExplanation(String html)
    {
        String text = Jsoup.parse(html).text();
        
        int indexof_explanation = -1;
        int indexof_tomorrowspic = -1;
        int indexof_wekeepanarchive = -1;
        
        indexof_explanation = text.indexOf(EXPLANATION);
        indexof_tomorrowspic = text.indexOf(TOMORROWS_PIC);
        indexof_wekeepanarchive = text.indexOf(WE_KEEP_AN_ARCHIVE);
        
        if(indexof_explanation == -1 || (indexof_tomorrowspic == -1 && indexof_wekeepanarchive == -1))
        {
            return null;
        }
        
        String explanation = null;
        
        if(indexof_tomorrowspic != -1)
        {
            explanation = text.substring(1 + indexof_explanation + EXPLANATION.length(), indexof_tomorrowspic);
        }
        else if(indexof_wekeepanarchive != -1)
        {
            explanation = text.substring(1 + indexof_explanation + EXPLANATION.length(), indexof_wekeepanarchive);
        }
        
        explanation = explanation.replace("\n", " ");
        explanation = explanation.replace("[s]+", " ");
        explanation = explanation.trim();
        
        return explanation;
    }
}
