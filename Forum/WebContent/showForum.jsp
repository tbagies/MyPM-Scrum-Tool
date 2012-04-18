<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="pageElements/scrumpm.css" type="text/css"></link>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>show Forum</title>
</head>
<body>
 <%@ include file="pageElements/UserSession.jsp"%>
<table>
<tr><td>
<%@ include file="pageElements/HeaderFile.html"%>
<tr><td>
<table>
<tr><td><%@ include file="pageElements/LeftMenuFile.jsp"%>
<tr><td>
<center>
<% 
if(request.getAttribute("isEmpty") != null){
%>
<table>
<tr><td> 
<form action="PostThreadServlet" method="get">
<input type="hidden" name="forumID" value=<%=request.getParameter("forumID") %>>
<input type="hidden" name="userID" value=<%=session.getAttribute("userID") %>>
	
	<tr><td>
Title:
<td><input type="Text" name="title">
<tr><td colspan=2>
<textarea name="threadText" rows="10" cols="35"></textarea>
<tr><td colspan=2 align=center>
<input type="submit" value="post">
<input type="reset" value="reset">
</form>
</table>
<% 
	if(request.getAttribute("isEmpty") == "true")
		out.println("No Threads");
	else{
		out.println(request.getAttribute("records"));
	}
}
else
{
	if(!redirect){
		RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/ShowForumServlet");
		requestDispatcher.include(request, response);
	}
}
%>
</center>
<tr><td>
<%@ include file="pageElements/FooterFile.html"%>
</table>
</body>
</html>