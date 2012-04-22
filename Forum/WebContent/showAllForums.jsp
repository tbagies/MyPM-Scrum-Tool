<%@ page import="model.ShowAllForums"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="pageElements/scrumpm.css" type="text/css"></link>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Show Forum</title>
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
	if(userSession!=null){
		String forums = ShowAllForums.showForums(userSession);
		out.println(forums);
	}
%>
</center>
<tr><td>
<%@ include file="pageElements/FooterFile.html"%>
</table>
</body>
</html>