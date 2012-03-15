<%@ page import="model.PMSession"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>MyPMScrumTool- All users</title>
</head>
<body>
<table>
<tr><td colspan=2> <%@ include file="pageElements/HeaderFile.html"%>
	<tr><td>
	<%@ include file="pageElements/LeftMenuFile.html"%>
			
	<td>	
<% 
String userName = (String) session.getAttribute("userName");
String userPassword = (String)session.getAttribute("userPassword");
model.PMSession pmSession = new PMSession(userName, userPassword);
if(!pmSession.isSession()){
%>
<META HTTP-EQUIV="refresh" CONTENT="1;URL=login.jsp">
<%
}
else{
	if(request.getAttribute("isEmpty") != null){
			if(request.getAttribute("isEmpty") == "true")
				out.println("No users registered");
			else{
				out.println(request.getAttribute("records"));
			}
	}
	else {
		RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/ShowAllUsersServlet");
		requestDispatcher.include(request, response);
	}
		
	}
%>
<tr><td colspan=2> <%@ include file="pageElements/FooterFile.html"%>
		</table>
</body>
</html>