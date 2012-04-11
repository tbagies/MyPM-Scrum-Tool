<%@ page import="domainModel.User"%>
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
pmPersistence.RetrieveResult<User> result = User.getAll( new pmPersistence.Database("jdbc:mysql://localhost:3306/", "com.mysql.jdbc.Driver", "mypmscrumdb", "root", "scrumPM2012"));
domainModel.User userObj = result.next();
if(userObj==null){
	out.println("There is no student registred in the system");
}
else{
	int i=1;
	while(userObj!=null){
		out.println("<input type='checkbox' name='membersName" + i + "' value='" + userObj.getUserId() + "'>" +  userObj.getUserName() + "<br>");
		userObj = result.next();
		i++;
	}
	%>
	<input type="hidden" name="i" value=<%=i %>>
	<%
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