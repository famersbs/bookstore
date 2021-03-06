package nbs;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.netand.bookstore.worker.WorkAddDoc;
import com.netand.bookstore.worker.WorkManager;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

/**
 * Servlet implementation class BSBook
 */
@WebServlet("/book/*")
public class BSBook extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BSBook() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		boolean result = true;
		String reason = "unknown";
		String doc_id= "";
		
		// 파라매터 파싱 및 처리
	  	String fileName = "";
	  	String title = "";
	  	String fullPath = WorkManager.getStorePath();
	  	try{
	  		
	 	    MultipartRequest multi = new MultipartRequest(request, WorkManager.getStorePath(), 30*1024*1024, "utf-8", new DefaultFileRenamePolicy() );

	 	    fileName=multi.getFilesystemName("book");
	 	    title = multi.getParameter("title");
	 	    
	 	    fullPath += fileName;
	 	    
	 	 } catch(Exception e) {
	 		reason = "fail to upload file";
	 	    e.printStackTrace();
	 	   result = false;
	 	 } 
	 	 
	 	WorkManager man = WorkManager.getInstance(); 	
	 	man.addNormalWork( new WorkAddDoc( fileName, "0" ) );
		
		// 결과 전달
		StringBuffer ret = new StringBuffer();
		PrintWriter out = response.getWriter();
		ret.append(
		"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
		 "<result>" +
  			"<result>" + (result? "SUCCESS" : "FAIL" ) +  "</result>" + 
  			"<id>"+doc_id+"</id>" +
  			"<reason>" + reason + "</reason>" +
  		"</result>"
		);
		
		out.print( ret.toString() );
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// NO!!!
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// NO !!! 
	}

}
