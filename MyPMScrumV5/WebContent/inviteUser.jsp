<%@ page import="domainModel.Role"%>
<html>

<head>
<meta http-equiv="Content-Language" content="en-us">
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1252">
<title>myPM-ScrumTool</title>
</head>

<body>
	<table>
		<tr>
			<td colspan=2><%@ include file="pageElements/HeaderFile.html"%>
		<tr>
			<td><%@ include file="pageElements/LeftMenueFile.html"%>
			<td>
				<table>
					<tr>
						<td colspan=2>Invite User: <!------------ JSP CODE TO READ FROM DATATBASE ---------->
							<% 
 	pmPersistence.Database myDb = new pmPersistence.Database("jdbc:mysql://localhost:3306/", "com.mysql.jdbc.Driver", "mypmscrumdb", "root", "scrumPM2012");       
	pmPersistence.RetrieveResult result = myDb.retrieveAllPersistentObjects(Role.class, Role.TABLE);
	domainModel.Role roleObj = (domainModel.Role)result.next();
	if(roleObj == null)
			{

			out.println("No Role is available");
}
else
{
%>
							<form action="InviteUserServlet" method="get">
								<tr>
									<td>User Email:
									<td><input type="text" name="userEmail">
								<tr>
									<td>User Type:
									<td><select name="roleDesc">
											<%
			do{
%>
											<option>
												<% out.println(roleObj.getDescription()); %>
											</option>
											<%
roleObj = (Role) result.next(); 
			}while(roleObj != null);
%>
									</select>
									</td>
									</tr>
								<tr>
									<td>Class No.:
									<td><select name="classNo">
											<option value="SE430">SE430</option>
											<option value="SE477">SE477</option>
									</select>
									</td></tr>
								<tr>
									<td colspan=2><input type="submit" value="Invite">
										<input type="reset" value="reset">
							</form> <% } %>
				</table>
			</td>
	</table>
	<tr>
		<td colspan=2><%@ include file="pageElements/FooterFile.html"%></td>
	</tr>

	</table>
</body>
</html>
