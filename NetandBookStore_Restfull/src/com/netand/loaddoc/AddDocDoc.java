package com.netand.loaddoc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hwpf.extractor.WordExtractor;

class AddDocDoc implements IAddDoc {


	public String parse( String filename )
	{
		File f = new File( filename );
		if( ! f.canRead() ){
			return "";
		}
		String txt = "";
		try {
			InputStream is = new FileInputStream(f);
			WordExtractor wd = new WordExtractor(is);
			txt = wd.getText();			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		
		return txt;
	}
}
