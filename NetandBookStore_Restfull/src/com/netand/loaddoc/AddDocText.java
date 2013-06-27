package com.netand.loaddoc;

import java.io.File;
import java.io.FileReader;

class AddDocText implements IAddDoc {

	public String parse( String filename )
	{
		File f = new File( filename );
		if( ! f.canRead() ){
			return "";
		}
		try{		
			
			FileReader fr = new FileReader(f);
		    char[] cbuf = new char[1024];
		    StringBuffer buf = new StringBuffer();
		    while( -1 != fr.read(cbuf) ){
		    	buf.append( cbuf );
		    }
			return buf.toString();
			
		}catch( Exception e ){
			e.printStackTrace();
		}
		
		return null;
	}
	
	
}
