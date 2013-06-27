package com.netand.loaddoc;

public class AddDocFactory {

	private AddDocFactory(){
		
	}
	
	public static IAddDoc getWorker( String filename )
	{
		IAddDoc w = null;
		String compare_filename =  filename.toLowerCase();
			
		if( compare_filename.lastIndexOf( "pdf" ) > 0 ){
			w = new AddDocPDF();
		}else if( compare_filename.lastIndexOf("doc") > 0 
				|| compare_filename.lastIndexOf("docx") > 0 ){
			w = new AddDocDoc();
		}else if( compare_filename.lastIndexOf("xlsx") > 0 ){
			w = new AddDocXLSX();
		}else if (  compare_filename.lastIndexOf("xls") > 0 ){
			w = new AddDocXLS();		
		}else if ( compare_filename.lastIndexOf("ppt") > 0 ){
			w = new AddDocPPT();
		}else{
			w = new AddDocText();
		}
		
		return w;
	}
	
}
