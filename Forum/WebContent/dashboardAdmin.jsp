<%@ page import="model.PMSession,domainModel.User"%>
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
<%@ include file="pageElements/InstructorSession.jsp" %>
		<tr><td>
		<a href="inviteUser.jsp">Invite user</a>
		<tr><td>
		<a href="showAllUsers.jsp">Show All Users</a>
		<tr><td>
		<a href="showAllForums.jsp">Show All Forums</a>
		<tr><td>
		<a href="createForum.jsp">Create a new Forum</a>
<%
	
%>



<%
%>
</table>


<tr><td colspan=2> <%@ include file="pageElements/FooterFile.html"%>
			
</table>
</body>
</html>