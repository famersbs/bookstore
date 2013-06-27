package com.netand.loaddoc;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.xssf.extractor.XSSFExcelExtractor;
import org.apache.xmlbeans.XmlException;

class AddDocXLSX implements IAddDoc {


	public String parse( String filename )
	{
		String txt = "";
		try {			
			XSSFExcelExtractor extractor = new XSSFExcelExtractor(filename);			
			txt = extractor.getText();			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlException e) {
			e.printStackTrace();
		} catch (OpenXML4JException e) {
			e.printStackTrace();
		}

		
		return txt;
	}
}
