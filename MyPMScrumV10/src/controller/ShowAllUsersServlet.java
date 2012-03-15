package controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import domainModel.User;

/**
 * Servlet implementation class ShowAllUsersServlet
 */
@WebServlet("/ShowAllUsersServlet")
public class ShowAllUsersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final pmPersistence.Database myDb = new pmPersistence.Database("jdbc:mysql://localhost:3306/", "com.mysql.jdbc.Driver", "mypmscrumdb", "root", "scrumPM2012");
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowAllUsersServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");
		pmPersistence.RetrieveResult result = myDb.retrieveAllPersistentObjects(User.class, User.TABLE);
		domainModel.User userObj = (domainModel.User)result.next();
		
		if(userObj != null){
			request.setAttribute("isEmpty", "false");
			DesignPage page = new DesignPage("<table border=1 align='center'><tr><th>User Account"
					+ "<th>Email<th>Class Name<th>Role");
			while(userObj != null){
				if(userObj.getUserName()== null)
					page.pageCon("<tr><td>is invited but not create an account");
				else
					page.pageCon("<tr><td>" + userObj.getUserName());
				page.pageCon("<td>" + userObj.getEmail());
				page.pageCon("<td>" + userObj.getClassName());
				page.pageCon("<td>" + userObj.getRole().getDescription());	
				userObj = (domainModel.User) result.next();		
			}
			page.pageCon("</table>");
			request.setAttribute("records",page.getPage());
		}
		else{
			request.setAttribute("isEmpty", "true");
		}	
		RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/showAllUsers.jsp");
		requestDispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
