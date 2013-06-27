package com.netand.bookstore.worker;


import org.apache.lucene.index.IndexWriter;

public interface IWorker {
	
	public boolean work();
	
	public boolean isWrite();
	
	public void work_write( IndexWriter writer);
	
	public String work_state();
		
	
}
