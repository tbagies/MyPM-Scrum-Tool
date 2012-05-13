package controller;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domainModel.Forum;
import domainModel.Role;
import domainModel.User;

/**
 * Servlet implementation class PostThreadServlet
 */
@WebServlet("/PostThreadServlet")
public class PostThreadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	  private static final pmPersistence.Database myDb = new pmPersistence.Database("jdbc:mysql://localhost:3306/", "com.mysql.jdbc.Driver", "mypmscrumdb", "root", "scrumPM2012");
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PostThreadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String title = request.getParameter("title").trim();
		String threadText = request.getParameter("threadText").trim();
		String forumID = request.getParameter("forumID");
		String userID = request.getParameter("userID");
		RequestDispatcher dispatcher;
		String fileName="/showForum.jsp?forumID=" + forumID;
		String msg="";
		if(title == null || threadText==null || threadText.isEmpty() || forumID == null || userID == null){
			msg="All Fields are required";
		}
		else{
			// insert the record in database
			Forum forumObj = Forum.findById(myDb, Integer.parseInt(forumID));
			User userObj = User.findById(myDb, Integer.parseInt(userID));
			if((userObj.getProject().getProjectId().equals(forumObj.getProject().getProjectId())) || (userObj.getRole().getAccessLevelId().equals(Role.INSTRUCTOR))){
				domainModel.Thread threadObj= new domainModel.Thread(myDb);
				java.util.Date today = new java.util.Date();
				long t = today.getTime();
				Date date = new Date(t);
				threadObj.setDate(date);
				threadObj.setText(threadText);
				threadObj.setTitle(title);
				threadObj.setUser(userObj);
				threadObj.setForum(forumObj);
				if(!threadObj.persist())
					msg= "The post has not been added to database";
			}
			else
				msg="You are not allowed to post a thread in this forum" + userObj.getUserName() + " " + userObj.getRole().getDescription();
		}
		request.setAttribute("msg", msg);
		dispatcher = getServletContext().getRequestDispatcher(fileName);
        dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
