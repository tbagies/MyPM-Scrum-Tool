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
 * Servlet implementation class ReplyToThreadServlet
 */
@WebServlet("/ReplyToThreadServlet")
public class ReplyToThreadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final pmPersistence.Database myDb = new pmPersistence.Database("jdbc:mysql://localhost:3306/", "com.mysql.jdbc.Driver", "mypmscrumdb", "root", "scrumPM2012");
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReplyToThreadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String title = request.getParameter("title");
		String threadText = request.getParameter("postText");
		String threadID = request.getParameter("threadID");
		String userID = request.getParameter("userID");
		RequestDispatcher dispatcher;
		String fileName="/replyToThread.jsp?threadID=" + threadID;
		String msg="";
		if(title == null || threadText==null || threadText.isEmpty() || threadID == null || userID == null){
			msg= "All field are required";
		}
		else{
			// insert the record in database
			domainModel.Thread threadObj = domainModel.Thread.findById(myDb, Integer.parseInt(threadID));
			User userObj = User.findById(myDb, Integer.parseInt(userID));
			Forum forumObj = threadObj.getForum();
			if(userObj.getProject().getProjectId().equals(forumObj.getProject().getProjectId().intValue()) || userObj.getRole().getAccessLevelId().equals(Role.INSTRUCTOR)){
				domainModel.Post postObj= new domainModel.Post(myDb);
				java.util.Date today = new java.util.Date();
				long t = today.getTime();
				Date date = new Date(t);
				postObj.setDate(date);
				postObj.setText(threadText);
				postObj.setTitle(title);
				postObj.setUser(userObj);
				postObj.setThread(threadObj);
				if(!postObj.persist())
					msg = "your post has not been added into database";
			}
			else
				msg= "you are not allowed to post in this forum";
		}
		request.setAttribute("msg", msg);
		dispatcher = getServletContext().getRequestDispatcher(fileName);
        dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
