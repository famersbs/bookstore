
<%@page import="com.netand.bookstore.worker.WorkFullAddDoc"%>
<%@page import="com.netand.bookstore.worker.WorkManager"%><%
	String result = "fail...";

	WorkManager.getInstance().addNormalWork( new WorkFullAddDoc() );
%>