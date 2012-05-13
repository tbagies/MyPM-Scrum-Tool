<%@ page import="model.PMSession"%>
<%@ page import="domainModel.*"%>
<% 
if(session.getAttribute("userID") != null){
%>
<table>
<tr><td>
<a href="index.jsp">Dashboard</a>
</td></tr>
<% 
pmPersistence.Database db = new pmPersistence.Database("jdbc:mysql://localhost:3306/", "com.mysql.jdbc.Driver", "mypmscrumdb", "root", "scrumPM2012");
User user = User.findById(db, (Integer)session.getAttribute("userID"));
if(user.getRole().getAccessLevelId() >= Role.INSTRUCTOR) {
%>
<tr><td>
<a href="index.jsp">Projects</a>
</td></tr>
<%
}
else if(user.getProject() != null) {
%>

<tr><td>
<a href="showForum.jsp">Forums</a>
</td></tr>
<%
if(user.getRole().getAccessLevelId() == Role.STUDENT) {
%>
<tr>
<td>
<a href="todo">Tasks</a>
</td></tr>
<tr><td>
<a href="TODO">Reports</a>
</td></tr>
<%}
else if(user.getRole().getAccessLevelId() == Role.CUSTOMER) {
%>
<tr><td>
<a href="TODO">Customer Reports</a>
</td></tr>
<%}
} %>
<tr><td>
<a href="logout.jsp">Logout</a>
</td>
</tr>
</table>
<% } %>
