package nbs;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.netand.bookstore.VO_NBS;
import com.netand.bookstore.worker.WorkAddDoc;
import com.netand.bookstore.worker.WorkManager;
import com.netand.bookstore.worker.WorkSelectDoc;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

/**
 * Servlet implementation class BSBook
 */
@WebServlet("/book/search/*")
public class BSBookSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BSBookSearch() {
        super();
    }
    
    public static String base64Encode(String str)  throws java.io.IOException {
	  if ( str == null || str.equals("") ) {
	     return "";
	  } else {
	     sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
	     byte[] b1 = str.getBytes();
	     String result = encoder.encode(b1);
	     return result;
	  }
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//response.setContentType("application/json");
		
		StringBuffer ret = new StringBuffer();
		PrintWriter out = response.getWriter();
		
		// 검색 문자열 받아오기
		//String keyword = new String( request.getParameter("keyword").getBytes("ISO-8859-1"), "utf-8" );
		String keyword = new String( request.getParameter("keyword") );
		WorkManager man = WorkManager.getInstance();
	 	
		WorkSelectDoc work = new WorkSelectDoc( keyword );	
	 	man.addNormalWork( work );
	 	work.await();
	 	
	 	List<VO_NBS> list = work.getResult();
		
	 	
	 	ret.append("[");
	 	Iterator<VO_NBS> i = list.iterator();
		int cnt = 0;
		while( i.hasNext() ){
			VO_NBS nbs = i.next();
			
			ret.append(
	 				"{" +
	 					"\"title\":\""+nbs.getFilename()+"\", " +
	 					"\"book_id\":\""+nbs.getFilename()+"\", " +
	 					"\"link\":\"./download.jsp?filename="+nbs.getFilename() +"\", " +
	 					"\"highlight\":\""+ base64Encode( nbs.getHighlight() ).replace( System.getProperty( "line.separator" ), "" ) +"\" " +
	 				"},");
			++ cnt;
		}
		if( cnt > 0 )
			ret.delete( ret.length() - 1, ret.length() );
		
	 	ret.append("]");
		
		out.print( ret.toString() );
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// NO !!!
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
