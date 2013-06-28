<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>책 등록</title>
</head>
<body>
<center>
	<p>
	Netand Book Store 책등록
	</p>
	<br/>
	<form id="search_bar" action="./book" enctype="multipart/form-data" method="post"  >
		제목 : <input type="text" id="title" name="title"></input><br/>
		<input type="file" name="book"> <br/>
		<input type="submit" value="Upload"></input>
	</form>
</center>
</body>
</html>