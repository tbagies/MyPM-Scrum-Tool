package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import domainModel.Forum;

/**
 * Servlet implementation class DeleteForumServlet
 */
@WebServlet("/DeleteForumServlet")
public class DeleteForumServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	  private static final pmPersistence.Database myDb = new pmPersistence.Database("jdbc:mysql://localhost:3306/", "com.mysql.jdbc.Driver", "mypmscrumdb", "root", "scrumPM2012");
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteForumServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher;
		String forumID = request.getParameter("forumID");
		String fileName = "/showAllForums.jsp";
		String msg="";
		if(forumID!=null){
			Forum forumObj = Forum.findById(myDb, Integer.parseInt(forumID));
			if(!forumObj.delete())
				msg ="The forum has not been deleted";
		}
		else
			msg = "you have to select a forum";
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
