package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pmPersistence.RetrieveResult;

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
		// TODO Auto-generated method stub
		RequestDispatcher dispatcher;
		String forumID = request.getParameter("forumID");
		if(forumID!=null){
			Forum forumObj = Forum.findById(myDb, Integer.parseInt(forumID));
			RetrieveResult<domainModel.Thread> threadResult= forumObj.getThreads();
			domainModel.Thread threadObj = threadResult.next();
			boolean isDeleted = true;
			while(threadObj!=null){
				RetrieveResult<domainModel.Post> postResult = threadObj.getPosts();
				domainModel.Post postObj = postResult.next();
				while(postObj!=null){
					if(!postObj.delete()){
						isDeleted=false;
						break;
					}
					postObj = postResult.next();
				}
				if(isDeleted){
					if(!threadObj.delete()){
						isDeleted=false;
						break;
					}
				}
				threadObj = threadResult.next();
			}
			if(isDeleted){
				if(forumObj.delete()){
					request.setAttribute("isDeleted","true");
				}
				else
					request.setAttribute("isDeleted","false");
			}
			else
				request.setAttribute("isDeleted","false");
			dispatcher = getServletContext().getRequestDispatcher("/deleteForum.jsp?forumID=" + forumID);
		}
		else
			dispatcher = getServletContext().getRequestDispatcher("/error.jsp");

    dispatcher.forward(request, response);
    
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
