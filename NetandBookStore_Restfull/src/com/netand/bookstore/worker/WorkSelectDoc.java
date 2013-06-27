package com.netand.bookstore.worker;

import java.util.List;

import org.apache.lucene.index.IndexWriter;

import com.netand.bookstore.VO_NBS;

public class WorkSelectDoc implements IWorker {

	private boolean isend = false;
	private String query = "";
	private List<VO_NBS> result = null;
	private Object sycn = new Object();
	private String status = "wait";
	
	public List<VO_NBS> getResult() {
		return result;
	}

	public WorkSelectDoc( String query ){
		this.query = query;
	}	

	@Override
	public boolean isWrite() {	return false;}

	@Override
	public boolean work() {
		
		status = "working";
		
		result = WorkManager.getInstance().getLucene().getDocumentHighlight( query );
		
		/*
		if( query.equals("use*") ){
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		*/
		synchronized( sycn ){
			isend = true;
			sycn.notify();
		}
		
		return ( result != null );
	}	

	@Override
	public void work_write( IndexWriter writer ){}
	
	public void await(){
		
		synchronized( sycn ){
			while( !isend ){				
				try {
					sycn.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("await...");
			}
		}
	}
	
	public String work_state(){
		return "WorkSelectDoc : [" + query + "] " + status;
	}
	
}
