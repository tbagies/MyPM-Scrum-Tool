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
<form action="CreateUserProfileServlet" method="get">
<table>

<tr><td>
<p dir="ltr" align="left"><font color="#000080"><b>Insert User Profile:</b></font></p>
			<tr><td>
First Name:
<td><input type="text" name="firstName">
<tr><td>
Last Name:
<td><input type="text" name="lastName">
<tr><td>
Alternate Email:
<td>
<input type="text" name="altEmail">
<tr><td>Date of Birth:
<td> 
<select size="1" name="birthMonth" >
<option>Month</option>
		<%
		for(int i=1; i<=12 ; i++){
			%>
			<option>
			<% out.print(i); %>
			</option>
			<% } %>
			</select>
	<select size="1" name="birthDay" >
	<option>Day</option>
		<%
		for(int i=1; i<=31 ; i++){
			%>
			<option>
			<% out.print(i); %>
			</option>
			<% } %>
			</select>		
		<select size="1" name="birthYear" >
		<option>Year</option>
		<%
		for(int i=1950; i<=2012 ; i++){
			%>
			<option>
			<% out.print(i); %>
			</option>
			<% } %>
			</select>
<tr><td>
Gender:
<td>	
			<input type="radio" value="male" checked name="gender">Male
			<input type="radio" value="female" name="gender">Female


<tr><td>
Contact No.:
<td><input type="text" name="contNo" maxlength=10>
<tr><td>

<tr><td colspan=2 align=center>
<input type="submit" value="login">
<input type="reset" value="reset">
	</table>
</form>
<% } %>
<tr><td colspan=2> <%@ include file="pageElements/FooterFile.html"%>
			
</table>
</body>
</html>