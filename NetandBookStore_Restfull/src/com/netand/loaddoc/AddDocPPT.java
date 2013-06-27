package com.netand.loaddoc;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hslf.HSLFSlideShow;
import org.apache.poi.hslf.model.Slide;
import org.apache.poi.hslf.model.TextRun;
import org.apache.poi.hslf.usermodel.SlideShow;

class AddDocPPT implements IAddDoc {


	public String parse( String filename )
	{
		
		SlideShow src;
		    
		StringBuffer txt = new StringBuffer();
		
		try {
			
			src = new SlideShow( new HSLFSlideShow(filename));
			
			Slide[] sl = src.getSlides();

		    for (int i = 0; i < sl.length; i++) { 
		        Slide s = sl[i];
		        TextRun[] trs = s.getTextRuns();
		        for (int k = 0; k < trs.length; k++) {
		            TextRun tr = trs[k];
		            txt.append( tr.getText() );
		        }
		    }
	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return txt.toString();
	}
}
