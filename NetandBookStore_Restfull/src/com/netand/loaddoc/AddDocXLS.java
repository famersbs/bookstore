package com.netand.loaddoc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.extractor.ExcelExtractor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

class AddDocXLS implements IAddDoc {


	public String parse( String filename )
	{
		File f = new File( filename );
		if( ! f.canRead() ){
			return "";
		}
		String txt = "";
		try {
			InputStream is = new FileInputStream(f);
			ExcelExtractor extractor = new ExcelExtractor(new POIFSFileSystem(is));
			txt = extractor.getText();			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		
		return txt;
	}
}
