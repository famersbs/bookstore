package com.netand.bookstore.worker;

import java.io.File;

import org.apache.lucene.index.IndexWriter;

public class WorkFullAddDoc implements IWorker {

	private String store_path = "";
	private StringBuffer progress = new StringBuffer(); 
		
	//private Queue<WorkAddDoc> filelist = new LinkedList<WorkAddDoc>();
	
	public WorkFullAddDoc(  ){
		this.store_path = WorkManager.getStorePath();
		progress.append("WorkFullAddDoc\r\n");
	}
	
	
	@Override
	public boolean work() {
		return true;
	}
	
	private void writeProgress( String p ){
		synchronized(progress){
			progress.append(p);
		}		
	}	
	
	@Override
	public void work_write( IndexWriter writer ){
		
		File list = new File( store_path );
		
		if( !list.canRead() ) return;
		if( !list.isDirectory() ) return;
		
		File[] file_list = list.listFiles();
		
		for( int i = 0 ; i < file_list.length ; ++ i ){
			File curr = file_list[i];
			
			
			
			if( curr.isFile() ){
				WorkAddDoc doc = new WorkAddDoc( curr.getName(), "0" );
				
				writeProgress( "\t" + doc.toString() );
				
				if( doc.work() ){
					doc.work_write( writer );
					writeProgress( " complete\r\n");
				}else{
					writeProgress( " fail\r\n");
				}
			}
		}
	}

	@Override
	public boolean isWrite() {
		return true;
	}

	public String work_state(){
		synchronized(progress){
			return progress.toString();
		}
	}
	
}
