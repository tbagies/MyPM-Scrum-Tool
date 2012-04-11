<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
if(request.getAttribute("isDeleted") != null){
	if(request.getAttribute("isDeleted") == "true")
		out.println("Forum has been deleted succeffully");
	else{
		out.println("Error");
	}
}
else
{
	RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/DeleteForumServlet");
	requestDispatcher.include(request, response);
}
%>
</body>
</html>