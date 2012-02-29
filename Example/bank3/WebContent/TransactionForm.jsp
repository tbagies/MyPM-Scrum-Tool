<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Transaction Form</title>
</head>
<body>
<% 
String fileName;
if (request.getParameter("n").equals("1")){
	fileName = "WithdrawServlet";
}
else
{
	fileName="DepositeServlet";	
}
%>

<form action=<%= fileName %> method="get">
<table>
<tr><td>
user ID
<td>
<input type="text" name="userId">
<tr><td>
Amount
<td>
<input type="text" name="amount">
<tr><td colspan="2">
<input type="submit" value="submit">
</table>
</form>
</body>
</html>