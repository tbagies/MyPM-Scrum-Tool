<%@ page import="domainModel.Role,model.PMSession"%>
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
			<td><%@ include file="pageElements/LeftMenuFile.html"%>
			<td>
				<table>
					<tr>
						<td colspan=2>Invite User: <!------------ JSP CODE TO READ FROM DATATBASE ---------->
							<% 
							
							String userName = (String) session.getAttribute("userName");
							String userPassword = (String)session.getAttribute("userPassword");
							model.PMSession pmSession = new PMSession(userName, userPassword);
							if(!pmSession.isSession()){
								out.println("You should login first");
							%>
							<a href="login.jsp">login</a>
							<% 
							}
							else{
								if(!pmSession.isInstructor()){
									out.println("You are not allowed to invite a user becuase you should be the instructor");
								}
								else{
									out.println("Welcome back" + session.getAttribute("userName"));
									pmPersistence.Database myDb = new pmPersistence.Database("jdbc:mysql://localhost:3306/", "com.mysql.jdbc.Driver", "mypmscrumdb", "root", "scrumPM2012");       
									pmPersistence.RetrieveResult result = myDb.retrieveAllPersistentObjects(Role.class, Role.TABLE);
									domainModel.Role roleObj = (domainModel.Role)result.next();
									if(roleObj == null){
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
										<option>Select type</option>
												<%
												do{
												%>
												<option>
												<% out.println(roleObj.getDescription()); %>
												</option>
												<% roleObj = (Role) result.next(); 
												}while(roleObj != null);
												%>
										</select>
										</td>
										</tr>
									<tr>
										<td>Class Name:
										<td><select name="classNo">
												<option>Select class</option>
												<option value="SE430">SE430</option>
												<option value="SE477">SE477</option>
										</select>
										</td></tr>
									<tr>
										<td colspan=2 align=center>
										<input type="submit" value="Invite">
											<input type="reset" value="reset">
								</form> <%
									}
								
								}
								}%>
				</table>
	<tr>
		<td colspan=2>
		<%@ include file="pageElements/FooterFile.html"%>
		</td>
	</tr>

	</table>
</body>
</html>
