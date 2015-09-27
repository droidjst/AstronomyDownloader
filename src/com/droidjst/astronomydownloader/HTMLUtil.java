
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

import org.jsoup.Jsoup;

public class HTMLUtil
{
	private static final String IMG_SOURCE = "<IMG SRC=";
	private static final String IMAGE_CREDIT = "Image Credit:";
	private static final String PICTURE_CREDIT = "Picture Credit:";
	private static final String CREDIT_COPYRIGHT = "Credit and Copyright";
	private static final String CREDIT_AND_COPYRIGHT = "Credit & Copyright";
	private static final String CREDIT = "Credit";
	private static final String COPYRIGHT = "Copyright";
	private static final String COURTESY = "Courtesy";
	private static final String EXPLANATION = "Explanation";
	private static final String TOMORROWS_PIC = "Tomorrow's picture";
	private static final String WE_KEEP_AN_ARCHIVE = "We keep an archive";
	
	public static String getImageSource(String html)
	{
		return Jsoup.parse(html).select("a > img").attr("src");
	}
	
	public static String getImageCredit(String html)
	{
		int indexof_imagecredit = -1;
		int indexof_picturecredit = -1;
		int indexof_creditcopywrite = -1;
		int indexof_creditandcopywrite = -1;
		int indexof_copyright = -1;
		int indexof_credit = -1;
		int indexof_courtesy = -1;
		int indexof_explanation = -1;
		
		indexof_imagecredit = html.indexOf(IMAGE_CREDIT);
		indexof_picturecredit = html.indexOf(PICTURE_CREDIT);
		indexof_creditcopywrite = html.indexOf(CREDIT_COPYRIGHT);
		indexof_creditandcopywrite = html.indexOf(CREDIT_AND_COPYRIGHT);
		indexof_copyright = html.indexOf(COPYRIGHT);
		indexof_credit = html.indexOf(CREDIT);
		indexof_explanation = html.indexOf(EXPLANATION);
		indexof_courtesy = html.indexOf(COURTESY);
		
		if(indexof_explanation == -1 || 
				(indexof_imagecredit == -1 && 
				indexof_picturecredit == -1 && 
				indexof_creditcopywrite == -1 && 
				indexof_creditandcopywrite == -1 &&
				indexof_copyright == -1 &&
				indexof_credit == -1 && 
				indexof_courtesy == -1))
		{
			return null;
		}
		
		String text = Jsoup.parse(html).text();
		
		indexof_imagecredit = text.indexOf(IMAGE_CREDIT);
		indexof_picturecredit = text.indexOf(PICTURE_CREDIT);
		indexof_creditcopywrite = text.indexOf(CREDIT_COPYRIGHT);
		indexof_creditandcopywrite = text.indexOf(CREDIT_AND_COPYRIGHT);
		indexof_copyright = text.indexOf(COPYRIGHT);
		indexof_credit = text.indexOf(CREDIT);
		indexof_explanation = text.indexOf(EXPLANATION);
		indexof_courtesy = text.indexOf(COURTESY);
		
		String credit = null;
		
		if(indexof_imagecredit != -1)
		{
			credit = text.substring(indexof_imagecredit + IMAGE_CREDIT.length(), indexof_explanation);
		}
		else if(indexof_picturecredit != -1)
		{
			credit = text.substring(indexof_picturecredit + PICTURE_CREDIT.length(), indexof_explanation);
		}
		else if(indexof_creditcopywrite != -1)
		{
			credit = text.substring(1 + indexof_creditcopywrite + CREDIT_COPYRIGHT.length(), indexof_explanation);
		}
		else if(indexof_creditandcopywrite != -1)
		{
			credit = text.substring(1 + indexof_creditandcopywrite + CREDIT_AND_COPYRIGHT.length(), indexof_explanation);
		}
		else if(indexof_copyright != -1)
		{
			credit = text.substring(1 + indexof_copyright + COPYRIGHT.length(), indexof_explanation);
		}
		else if(indexof_credit != -1)
		{
			credit = text.substring(1 + indexof_credit + CREDIT.length(), indexof_explanation);
		}
		else if(indexof_courtesy != -1)
		{
			credit = text.substring(1 + indexof_courtesy + COURTESY.length(), indexof_explanation);
		}
		
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
