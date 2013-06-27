<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import="java.util.*"%>
 <%@ page import="com.oreilly.servlet.MultipartRequest"%>
 <%@ page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%><%
 	String message = "Success";
  	String fileName = "";
  	String title = "";
  	String fullPath = WorkManager.getStorePath();
  	try{
  		
 	    MultipartRequest multi = new MultipartRequest(request, WorkManager.getStorePath(), 30*1024*1024, "utf-8", new DefaultFileRenamePolicy() );

 	    fileName=multi.getFilesystemName("upfile");
 	    title = multi.getParameter("title");
 	    
 	    fullPath += fileName;
 	    
 	 } catch(Exception e) {
 	     message = "fail to upload file";
 	    e.printStackTrace();
 	 } 
 	 
 	WorkManager man = WorkManager.getInstance(); 	
 	man.addNormalWork( new WorkAddDoc( fileName, "0" ) );
 	
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.netand.bookstore.NADIndexerLucene"%>
<%@page import="com.netand.bookstore.worker.WorkManager"%>
<%@page import="com.netand.bookstore.worker.WorkAddDoc"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title> 업로드 결과 </title>
</head>
<body>
<p>Title : <%=title %></p>
<p>filename : <%=fileName %></p>
<p>result : <%=message %></p>
<%=fullPath %>
<%@include file="module/search_bar.jsp" %>
</body>
</html>