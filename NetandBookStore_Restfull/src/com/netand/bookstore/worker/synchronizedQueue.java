package com.netand.bookstore.worker;

import java.util.LinkedList;
import java.util.Queue;

public class synchronizedQueue<T> {
	
	private Queue<T> q = new LinkedList<T>();
		
	public synchronizedQueue(){
		
	}

	public T pop(){
		T ret = null;
		
		synchronized( q ){
			while( q.isEmpty() ){
				try {
					q.wait();
				} catch (InterruptedException e) { e.printStackTrace();	}
			}
			
			ret = q.poll();
		}
		
		return ret;
	}
	
	public void add( T val ){
		synchronized( q ){			
			q.add( val );
			q.notify();
		}
	}

}
