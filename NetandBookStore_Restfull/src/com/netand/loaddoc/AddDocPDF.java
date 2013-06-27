package com.netand.loaddoc;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

class AddDocPDF implements IAddDoc {


	public String parse( String filename )
	{
		File f = new File( filename );
		if( ! f.canRead() ){
			return "";
		}
		PDDocument doc = null ;
		String txt = "";
		
		try{
			doc = PDDocument.load(f);
			PDFTextStripper stripper = new PDFTextStripper();
			txt = stripper.getText(doc);
						
		}catch( Exception e ){
			e.printStackTrace();
		}finally{
			if( null != doc ){
				try {
					doc.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return txt;
	}
}
