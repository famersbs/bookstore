<%@page import="com.netand.bookstore.worker.WorkManager"%><%@page import="java.io.FileNotFoundException"%><%@page import="java.net.URLEncoder"%><%@page import="java.io.File"%><%@page import="java.io.FileInputStream"%><%@page import="java.io.PrintStream"%><%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%

	//String filename = new String( request.getParameter("filename").getBytes("ISO-8859-1"), "utf-8" );
    String filename = request.getParameter("filename");
    String realPath = WorkManager.getStorePath() + filename;

	/** 다운로드 구현시 주의 사항
	 * 보통 어플리케이션 서버를 root계정으로 돌리는 경우가 많다.
	 * 이경우 잘못 하면 해킹의 기회를 제공 하는 경우가 있다.
	 * 어떤 사이트를 보면 실제화일명을 파라미터로 받아서 처리 하는 경우 보안상 위험 할 수 있다.
	 * 예를 들어 dowload.jsp?file=download/attach/../../../../../../etc/passwd
	 * 위와같은 경우로 해커가 파라미터를 조작하면 계정 및 패스워드 화일외에 시스템 정보를
	 * 얼마든지 획득 할 수 있다. 편의를 제공하기 우해 만든 다운로드 프로그램이.
	 * 판단미스로 본 사이트를 해킹하는 툴로 사용 될 수 있다는 것이다.
	 *  여기에서 아래와 같이 문자를 찾아서 해킹시도를 막는다. */
 	if(realPath.indexOf("..") != -1 ) {
  		return;
 	}
  	
	PrintStream printstream = new PrintStream(response.getOutputStream(), true);
  	FileInputStream fin = null;
  	ServletOutputStream outs = null;
  	
 	try{
		 //이부분에서 화일객체 생성시 해킹에 사용 될 수 있다.
		// 가급적이면 화일명을 파라미터가 아니 DB에 가져오도록 로직을 구현하는게 좋다.
		//웹상에 보여주는 화일명과 실제화일명을 DB에 넣어 숨기는 것이 좋다.
		//여기서는 단순 다운로드 구현을 설명 하고자 걍 사용 하기로 한다.
  		File file = new File( realPath );
  		fin = new FileInputStream(file);
  
  		int ifilesize = (int)file.length();
  		byte b[] = new byte[ifilesize];
  		String strClient = request.getHeader("User-Agent");
  		response.setContentLength(ifilesize);
  		response.reset() ;
  		response.setContentType("application/octet-stream");
  
  		if(strClient.indexOf("MSIE 5.5") > -1) {
       		response.setHeader("Content-Disposition", "filename=" + filename.trim() + ";");
  		} else {
       		response.setHeader("Content-Disposition", 
       				"attachment;filename=" + filename.trim() + ";");
  		}
		response.setHeader("Content-Length", ""+ifilesize );
  		outs = response.getOutputStream();
  		response.setHeader("Content-Transfer-Encoding", "binary;");
  		response.setHeader("Pragma", "no-cache;");
  		response.setHeader("Expires", "-1;");
  		
  		if (ifilesize > 0 && file.isFile()) {
     		int read = 0;
     		while((read = fin.read(b)) != -1) {
         		outs.write(b,0,read);
     		}
  		}
  		
	} catch (FileNotFoundException fnfe) {
   		System.out.println(fnfe.toString());
	} catch (Exception e) {
   		System.out.println(e.toString());
	} finally{
  		try {
      		if(outs != null) outs.close();
      		if(fin != null) fin.close();
 		} catch (Exception e) {}
	} 	
%>