<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><% 
 response.setHeader("Cache-Control","no-cache");
 response.setHeader("Pragma","no-cache"); 
 response.setDateHeader("Expires",0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="com.netand.bookstore.worker.WorkManager"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>WorkList</title>
</head>
<body>
<br></br>
<center>Netand Book Store WorkList</center>
<br></br>
<pre>
<%=WorkManager.getInstance().workProgress()%>
</pre>
</body>
</html>