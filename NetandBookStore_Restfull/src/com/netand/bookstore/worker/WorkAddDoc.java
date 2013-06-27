package com.netand.bookstore.worker;

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;

import com.netand.bookstore.VO_NBS;
import com.netand.loaddoc.AddDocFactory;
import com.netand.loaddoc.IAddDoc;

public class WorkAddDoc implements IWorker {

	private String store_path = "";
	private String filename = "";
	private String category = ""; 
	private String contents = "";
	private String status = "wait";
	
	public WorkAddDoc(  String filename , String category ){
		this.store_path = WorkManager.getStorePath();
		this.filename = filename;
		this.category = category;
		
	}
	
	
	@Override
	public void work_write( IndexWriter writer ){
		
		status = "working(create Index...)";
		
		Document doc = new Document();
		
	    doc.add(new Field(VO_NBS.CONTENTS, contents, Field.Store.YES, Field.Index.ANALYZED));
	    doc.add(new Field(VO_NBS.CATEGORY, category , Field.Store.YES, Field.Index.ANALYZED ));
	    doc.add(new Field(VO_NBS.FILENAME, filename , Field.Store.YES, Field.Index.ANALYZED ));
	    
	    try {
			writer.addDocument(doc);
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public boolean isWrite() {
		// TODO Auto-generated method stub
		if( !"".equals(contents) ){
			return true;
		}
		return false;
	}

	@Override
	public boolean work() {
		status = "parse document";
		
		IAddDoc w = AddDocFactory.getWorker( filename );
		
		contents = w.parse( store_path + filename);
		if( ! "".equals(contents) ){
			return true;
		}
		
		return false;
	}	
	
	public String work_state(){
		return "WorkAddDoc : [" + filename + "] " + status;
	}
	
	public String toString(){
		return "WorkAddDoc : [" + filename +"]";
	}
}
