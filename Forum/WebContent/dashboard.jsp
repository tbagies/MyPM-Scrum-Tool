<%@ page import="model.PMSession,domainModel.User,domainModel.Role"%>
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
<%@ include file="pageElements/UserSession.jsp" %>
<%
if(userSession!=null){
	if(userSession.getRole().getAccessLevelId().equals(Role.INSTRUCTOR) || userSession.getRole().getAccessLevelId().equals(Role.ADMINISTRATOR)){
		%>
		<a href="showAllUsers.jsp">List Users</a>
		<tr><td>
		<a href="showAllProjects.jsp">List Projects</a>
		<tr><td>
		<a href="showAllForums.jsp">List Forums</a>
<% 
	}
	else if(userSession.getRole().getAccessLevelId().equals(Role.STUDENT)){
	%>
		<a href="listProjectMembers.jsp">List Project Memebers</a>
		<tr><td>
		<a href="editProject.jsp">Edit Project</a>
		<tr><td>
		<a href="showAllTasks.jsp">List Tasks</a>
		<tr><td>
		<a href="showAllScrumReports.jsp">List Scrum Reports</a>
		<tr><td>
		<a href="showAllCustomersReports.jsp">List Customers Reports</a>
<% 	}
	else if(userSession.getRole().getAccessLevelId().equals(Role.CUSTOMER)){
	%>
		<a href="listProjectMembers.jsp">List Project Memebers</a>
<% 	}
	else
		out.println("undefinyed Role");
	%>
	<tr><td>
	<a href="editeUserProfile.jsp">Edit User Profile</a>
<% 	
}
%>
</table>


<tr><td colspan=2> <%@ include file="pageElements/FooterFile.html"%>
			
</table>
</body>
</html>