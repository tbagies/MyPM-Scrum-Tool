<%@ page import="domainModel.Project"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="pageElements/scrumpm.css" type="text/css"></link>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create Forum</title>
</head>
<body>
 <%@ include file="pageElements/InstructorSession.jsp"%>
<table>
<tr><td>
<%@ include file="pageElements/HeaderFile.html"%>
<tr><td>
<table>
<tr><td><%@ include file="pageElements/LeftMenuFile.jsp"%>
<tr><td>
<form action="CreateForumServlet" method="get">
<table>
<tr><td>Forum Name:
<td><input type="text" name="forumName">
<tr><td valign="top">members:
<td>

<%
pmPersistence.RetrieveResult<Project> result = Project.getAll( new pmPersistence.Database("jdbc:mysql://localhost:3306/", "com.mysql.jdbc.Driver", "mypmscrumdb", "root", "scrumPM2012"));
domainModel.Project projectObj = result.next();
if(projectObj==null){
	out.println("There is no student registred in the system");
}
else{
	out.println("<select name='projectID'>");
	while(projectObj!=null){
		out.println("<option value='" + projectObj.getName() + "'>" + projectObj.getName());
		projectObj = result.next();
		out.println("</option>");
	}
	out.println("</select>");

}
%>
<tr><td colspan=2>
<input type="submit" value="submit">
</table>
</form>
</table>
<tr><td>
<%@ include file="pageElements/FooterFile.html"%>
</table>
</body>
</html>