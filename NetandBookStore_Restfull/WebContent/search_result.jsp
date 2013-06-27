<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%response.setHeader("Cache-Control","no-cache");
 response.setHeader("Pragma","no-cache"); 
 response.setDateHeader("Expires",0);%>
<%@page import="com.netand.bookstore.NADIndexerLucene"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%><%
	String search_str = new String( request.getParameter("search_str").getBytes("ISO-8859-1"), "utf-8" );
	String catagory = request.getParameter("catagory");

	WorkManager man = WorkManager.getInstance();
 	
	WorkSelectDoc work = new WorkSelectDoc( search_str );	
 	man.addNormalWork( work );
 	work.await();
 	
 	List<VO_NBS> list = work.getResult();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="com.netand.bookstore.VO_NBS"%>
<%@page import="com.netand.bookstore.worker.WorkManager"%>
<%@page import="com.netand.bookstore.worker.WorkSelectDoc"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="module/search_bar.jsp" %>
<title> [<%=search_str%>]검색 결과 창</title>
</head>
<body>
<p>
[<%=search_str%>] 검색 결과
</p>
<hr />
<%
	Iterator<VO_NBS> i = list.iterator();
	
	while( i.hasNext() ){
		VO_NBS nbs = i.next();
%>
<div>
<p>
	<a href="./download.jsp?filename=<%=nbs.getFilename()%>" > 
		<b><%=nbs.getFilename() %></b>
	</a><br/>
	<%=nbs.getHighlight() %>
</p>
</div>
<br/>
<%
	}
%>
</body>
</html>