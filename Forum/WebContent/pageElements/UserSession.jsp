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
<%
Integer userID = (Integer) session.getAttribute("userID");
model.PMSession pmSession = new PMSession(userID);
if(!pmSession.isSession()){
	out.print("<META HTTP-EQUIV='refresh' CONTENT='0;URL=login.jsp'>");
}

%>
</body>
</html>