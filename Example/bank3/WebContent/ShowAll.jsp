<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Show All records</title>
</head>
<body>
<%@ page import="servlet.SQLCommand,java.sql.*"  %>
<%
	try
{
	Connection con = SQLCommand.connect();
	Statement stmt = con.createStatement();
	ResultSet rs = stmt.executeQuery("select * from userAccount");
%>
	<TABLE border=1>
    <TR><TD> ID number
    <TD>User Name
   <TD>Balance
   <%
    while (rs.next())
    {
    	%>
        <TR><TD><%= rs.getInt(1) %>
       <TD> <%=  rs.getString(2)%>
       <TD><%= rs.getDouble(3)%>
   <%} %>
  </TABLE>
 <% 
    rs.close();
    stmt.close();
    con.close();
}catch(SQLException e){
	out.println("SQL Exception");
}
%>
</body>
</html>