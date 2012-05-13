<%@ page import="model.ShowAllForums,domainModel.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="pageElements/scrumpm.css" type="text/css"></link>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Show All Forums</title>
</head>
<body>
 <%@ include file="pageElements/UserSession.jsp"%>
<table>
<tr><td colspan=2>
<%@ include file="pageElements/HeaderFile.html"%>
<tr><td>
<table>
<tr><td><%@ include file="pageElements/LeftMenuFile.jsp"%>
<td>
<center>
<% 
	if(userSession!=null){
		%>
		<form action="CreateForumServlet" method="post">
		<table>
		<tr><td>Forum Name:
		<td><input type="text" name="forumName">
		<tr><td valign="top">project:
		<td>

		<%
		pmPersistence.RetrieveResult<Project> result = Project.getAll( new pmPersistence.Database("jdbc:mysql://localhost:3306/", "com.mysql.jdbc.Driver", "mypmscrumdb", "root", "scrumPM2012"));
		domainModel.Project projectObj = result.next();
		if(projectObj==null){
			out.println("There is no project in the system");
		}
		else{
			out.println("<select name='projectID'>");
			while(projectObj!=null){
				out.println("<option value='" + projectObj.getProjectId() + "'>" + projectObj.getName());
				out.println("</option>");
				projectObj = result.next();
			}
			out.println("</select>");
		}
		%>
		<tr><td colspan=2>
		<input type="submit" value="submit">
		</table>
		</form>
		<% 
		pmPersistence.RetrieveResult<Forum> forums = ShowAllForums.showForums(userSession);
		domainModel.Forum forumObj = forums.next();
		if(forumObj!=null){
			out.println("<table><tr><td>forum title<td>Created date<td>Project");
			while(forumObj != null){
				Project resultProject = forumObj.getProject();
				out.println("<tr>");
				out.println("<td><a href='showForum.jsp?forumID=" + forumObj.getForumId() + "'>" + forumObj.getTitle() + "</a>");
				out.println("<td>" + forumObj.getCreatedDate());
				out.println("<td>" + forumObj.getProject().getName());
				if(userSession.getRole().getAccessLevelId().equals(Role.INSTRUCTOR))
					out.println("<td><a href='deleteForum.jsp?forumID=" + forumObj.getForumId() + "'>Delete</a>");
				forumObj = forums.next();
			}
			out.println("</table>");
		}
		else
			out.println("No Forum");
	}
%>
</center>
<tr><td colspan=2>
<%@ include file="pageElements/FooterFile.html"%>
</table>
</body>
</html>