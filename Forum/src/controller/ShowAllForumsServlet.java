package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domainModel.Forum;
import domainModel.User;

/**
 * Servlet implementation class ShowAllForumsServlet
 */
@WebServlet("/ShowAllForumsServlet")
public class ShowAllForumsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final pmPersistence.Database myDb = new pmPersistence.Database("jdbc:mysql://localhost:3306/", "com.mysql.jdbc.Driver", "mypmscrumdb", "root", "scrumPM2012");

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowAllForumsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");
		pmPersistence.RetrieveResult<Forum> forumResult = Forum.getAll(myDb);
		domainModel.Forum forumObj = forumResult.next();
		StringBuilder forums = new StringBuilder("");
		if(forumObj!=null){
			forums.append("<table><tr><td>forum title<td>Created date<td>Members");
			while(forumObj != null){
				pmPersistence.RetrieveResult<User> resultUser = forumObj.getUsers();				
				forums.append("<tr>");
				forums.append("<td><a href='showForum.jsp?forumID=" + forumObj.getForumId() + "'>" + forumObj.getTitle() + "</a>");
				forums.append("<td>" + forumObj.getCreatedDate());
				User userObj = resultUser.next();
				forums.append("<td>");
				while(userObj!=null){		
					forums.append(userObj.getUserName() + "<br>");
					userObj = resultUser.next();
				}
				forums.append("<td><a href='deleteForum.jsp?forumID=" + forumObj.getForumId() + "'>Delete</a>");
				forumObj = forumResult.next();
			}
			request.setAttribute("isEmpty", "false");
			forums.append("</table>");
		}
		else{
			request.setAttribute("isEmpty", "true");
		}
		request.setAttribute("records",forums.toString());
		RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/showAllForums.jsp");
		requestDispatcher.forward(request, response);
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
