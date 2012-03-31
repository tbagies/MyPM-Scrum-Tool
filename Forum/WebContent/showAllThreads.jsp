<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<table>
<tr><td> <%@ include file="pageElements/HeaderFile.html"%>
			
<tr><td>
<table>
<tr><td><%@ include file="pageElements/LeftMenuFile.jsp"%>
			
<tr><td>
<% 
		if(request.getAttribute("isEmpty") != null){
			if(request.getAttribute("isEmpty") == "true")
				out.println("No thread");
			else{
				out.println(request.getAttribute("records"));
			}
		}
		else {
			RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/ShowAllThreadssServlet");
			requestDispatcher.include(request, response);
		}
%>
</table>
<tr><td><%@ include file="pageElements/FooterFile.html"%>
			
</table>
</body>
</html>