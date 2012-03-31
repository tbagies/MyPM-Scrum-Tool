<%@ page import="model.PMSession"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>MyPMScrumTool- Login</title>
</head>
<body>
<table>
<tr><td colspan=2> <%@ include file="pageElements/HeaderFile.html"%>
	<tr><td>
	<%@ include file="pageElements/LeftMenuFile.jsp"%>
			
	<td>	

<table>
<tr><td> 
<%
Integer userID = (Integer) session.getAttribute("userID");
model.PMSession pmSession = new PMSession(userID);
if(!pmSession.isSession()){
	out.println("<tr><td colspan=2 align=center> Please enter your username and password:");
	%>
	<form action="LoginServlet" method="get">
	<tr><td>
User Name:
<td><input type="text" name="userName">
<tr><td>Password:
<td>
<input type="password" name="password">
<tr><td colspan=2 align=center>
<input type="submit" value="login">
<input type="reset" value="reset">
</form>
	<%
}
	
else{
	out.println("Welcome back " + session.getAttribute("userName"));
%>
<p>You will be redirect within 2 seconds, if not happened
<a href='dashboardAdmin.jsp'>click here</a>						
<META HTTP-EQUIV="refresh" CONTENT="1;URL=index.jsp">
<% } %>
</table>


<tr><td colspan=2> <%@ include file="pageElements/FooterFile.html"%>
			
</table>
</body>
</html>