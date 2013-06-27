package com.netand.bookstore;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.netand.bookstore.worker.WorkManager;

public class startup implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		WorkManager.stop();
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		ServletContext sc = event.getServletContext();		
		
		// 인자값 파싱
		String store_path = sc.getInitParameter("store_path");
		String index_path = sc.getInitParameter("index_path");
		int work_thread_cnt = Integer.parseInt( sc.getInitParameter("work_thread_cnt" ) );
		
		// work manager 초기화
		WorkManager.init( work_thread_cnt, store_path, index_path );
	}

}
