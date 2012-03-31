<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Post A Thread</title>
</head>
<body>
<table>
<tr><td colspan=2> <%@ include file="pageElements/HeaderFile.html"%>
	<tr><td>
	<%@ include file="pageElements/LeftMenuFile.jsp"%>			
<td>	
<table>
<tr><td> 
<form action="PostThreadServlet" method="get">
	<tr><td>
Title:
<td><input type="threadText" name="title">
<tr><td colspan=2>
<textarea name="thred" rows="10" cols="35"></textarea>
<tr><td colspan=2 align=center>
<input type="submit" value="post">
<input type="reset" value="reset">
</form>
</table>

<tr><td colspan=2> <%@ include file="pageElements/FooterFile.html"%>

</body>
</html>