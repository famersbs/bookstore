<%
	String search_bar_str = "";
	try{
		search_bar_str = new String( request.getParameter("search_str").getBytes("ISO-8859-1"), "utf-8" );
	}catch(Exception ex ){}
%><center>
	<form id="search_bar" action="search_result.jsp" method="get" >
		<SELECT name="catagory" size = "1" >
			<option value='1'>All</option>
		</SELECT>
		<input type="text" id="search_str" name="search_str" value="<%=( search_bar_str != null ? search_bar_str : "") %>"></input>
		<input type="submit" value="Search"></input>
		<a href = "./upload.jsp"> UPLOAD </a>&nbsp;
		<a href = "./help.html" target="_blank"> HELP </a>
	</form>
</center>