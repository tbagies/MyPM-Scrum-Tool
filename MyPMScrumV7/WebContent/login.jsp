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
	<%@ include file="pageElements/LeftMenuFile.html"%>
			
	<td>	
<form action="LoginServlet" method="get">
<table>
<tr><td> 
<%
String userName = (String) session.getAttribute("userName");
String userPassword = (String)session.getAttribute("userPassword");
model.PMSession pmSession = new PMSession(userName, userPassword);
if(!pmSession.isSession())
	out.println("a new user");
else
	out.println("Welcome back" + session.getAttribute("userName"));
%>
<tr><td>
User Name:
<td><input type="text" name="userName">
<tr><td>Password:
<td>
<input type="password" name="password">
<tr><td colspan=2 align=center>
<input type="submit" value="login">

<input type="reset" value="reset">
</table>
</form>
<tr><td colspan=2> <%@ include file="pageElements/FooterFile.html"%>
			
</table>
</body>
</html>