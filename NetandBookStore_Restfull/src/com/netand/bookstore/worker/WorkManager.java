package com.netand.bookstore.worker;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.apache.lucene.index.IndexWriter;

import com.netand.bookstore.NADIndexerLucene;

public class WorkManager {

	private synchronizedQueue<IWorker> normal_worklist = new synchronizedQueue<IWorker>();
	private Queue<IWorker> write_worklist = new LinkedList<IWorker>();
	private List<IWorker> full_work_list = new LinkedList<IWorker>();

    private static String store_path = "";
    private static String index_path = "";
    
    /** storepath 리턴 **/
    public static String getStorePath(){
    	return store_path;
    }
    
    // Lucene 객체
    private NADIndexerLucene lucene;
    
    public NADIndexerLucene getLucene() {
		return lucene;
	}


	private static WorkManager instance = null;
    
    public static WorkManager init( int thread_cnt, String store_path, String index_path){
    	if( null == instance ){
    		WorkManager.store_path = store_path;
    		WorkManager.index_path = index_path;
			instance = new WorkManager(thread_cnt);
		}
		return instance;
    }
    public static void stop(){
    	
    }
    
    public static WorkManager getInstance(){
    	return instance;
    }
		
	private WorkManager( int work_thread_cnt ){
		
		// Lucene 객체 생성
		lucene = new NADIndexerLucene( index_path, store_path );
		lucene.init();
		
		// 기본 work Thread 띄운다.
		for( int i = 0 ; i< work_thread_cnt ; ++ i ){
			new Thread( new normalWorkThread() ).start();
		}
		
		// Write Work Thread 띄운다.
		new Thread( new writeWorkThread() ).start();
		
	}
	
	public void addNormalWork( IWorker work ){
		normal_worklist.add( work );
		synchronized( full_work_list ){
			full_work_list.add( work );
		}
	}
	public void endWork( IWorker work ){
		synchronized( full_work_list ){
			if( full_work_list.contains(work) )
				full_work_list.remove( work );
		}
	}
	
	public String workProgress(){
		StringBuffer ret = new StringBuffer();
		
		synchronized( full_work_list ){
			if( full_work_list.size() == 0 ){
				ret.append("Work is Empty");
			}else{
				Iterator<IWorker> i = full_work_list.iterator();
				while( i.hasNext() ){
					ret.append( i.next().work_state() ).append("\r\n");
				}
			}
		}
		
		return ret.toString();
	}
	
	// normal 작업 하나 리턴 하기
	protected IWorker getNormalWork(){
		return normal_worklist.pop();
	}
	
	/**
	 * 기본 작업을 수행 합니다.
	 * @author Administrator
	 *
	 */
	class normalWorkThread implements Runnable{
		@Override
		public void run() {
			while( true ){
				IWorker curr_work = getNormalWork();
				
				if( curr_work.work() ){
					if( curr_work.isWrite() ){
						
						// writeWork 큐에 집어넣기
						synchronized(write_worklist){
							write_worklist.add( curr_work );
							write_worklist.notify();
						}
						
					}else{
						endWork( curr_work );	// 작업의 종료를 알린다.
					}
				}
			}			
		}		
	}
	
	class writeWorkThread implements Runnable{
		@Override
		public void run() {
			
			//NADIndexerLucene lucene = NADIndexerLucene.getInstance( index_path, store_path );
			
			while( true ){
				
				synchronized( write_worklist ){
					if( write_worklist.isEmpty() ){
						try {
							write_worklist.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
							continue;
						}
					}
				}
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				// 모든 리스트를 순회 하여 처리 
					// write stream 열고
				IndexWriter writer = lucene.openWriter();
				
				while( !write_worklist.isEmpty() ){
					IWorker curr_work = null;
					synchronized( write_worklist ){
						curr_work = write_worklist.poll();
					}
					
					if( null != curr_work ){
						// write_stream을 전달하여 문서를 push 하라고 예기 한다.
						curr_work.work_write( writer );
						endWork( curr_work );	// 작업의 종료를 알린다.
					}
					
				}
					// writestream 닫고
				lucene.closeWriter( writer );
			}			
		}	
	}	
	
}
