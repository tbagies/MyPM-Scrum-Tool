package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domainModel.Forum;
import domainModel.Project;
import domainModel.Task;

/**
 * Servlet implementation class CreateForumServlet
 */
@WebServlet("/CreateForumServlet")
public class CreateForumServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final pmPersistence.Database myDb = new pmPersistence.Database("jdbc:mysql://localhost:3306/", "com.mysql.jdbc.Driver", "mypmscrumdb", "root", "scrumPM2012");

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateForumServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String forumName = request.getParameter("forumName");
		String projectID = request.getParameter("projectID");
	//	String memberNo = request.getParameter("i");
	//	ArrayList<Integer> membersID = new ArrayList<Integer>();
		String fileName="/error.jsp";
		if(projectID==null || projectID.isEmpty() || forumName == null || forumName.isEmpty())
			fileName="error.jsp?msg=all fields are required";		
		else 
			if(forumName.length()>45){
			fileName= fileName + "?msg=forum name are wrong";
		}
			else{
				Forum forumObj = new Forum(myDb);
				java.util.Date today = new java.util.Date();
				long t = today.getTime();
				Date date = new Date(t);
				forumObj.setCreatedDate(date);
				forumObj.setTitle(forumName);	
				Project projectObj = Project.findById(myDb, Integer.parseInt(projectID));
				forumObj.setProject(projectObj);
				if(forumObj.persist()){
					fileName="/showAllForums.jsp";
				}
				else
					fileName= fileName + "?msg=Error,,, forum is not  created";
		}
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(fileName);
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
