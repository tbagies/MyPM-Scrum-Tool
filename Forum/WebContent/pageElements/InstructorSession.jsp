<%@ page import="model.PMSession"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%@ include file="UserSession.jsp" %>
<%
	String userRole = (String) session.getAttribute("userRole");
if(userRole == null || !(userRole.equalsIgnoreCase("Instructor")))
{
		out.println("You are not allowed to see this page becuase you should be the instructor");
		out.print("<META HTTP-EQUIV='refresh' CONTENT='1;URL=login.jsp'>");
}
%>
</body>
</html>