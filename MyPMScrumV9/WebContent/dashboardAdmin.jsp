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

<table>
<tr><td> 
<%
String userName = (String) session.getAttribute("userName");
String userPassword = (String)session.getAttribute("userPassword");
model.PMSession pmSession = new PMSession(userName, userPassword);
if(!pmSession.isSession()){
	out.println("<tr><td colspan=2 align=center> Please login first by using this link:<a href='login.jsp'>login</a>" );
}
	
else
{
%>
<tr><td>
<a href="inviteUser.jsp">Invite user</a>
<tr><td>
<a href="createUserProfile.jsp">create user profile</a>
<% } %>
</table>


<tr><td colspan=2> <%@ include file="pageElements/FooterFile.html"%>
			
</table>
</body>
</html>