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
 * Servlet implementation class ShowForumServlet
 */
@WebServlet("/ShowForumServlet")
public class ShowForumServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final pmPersistence.Database myDb = new pmPersistence.Database("jdbc:mysql://localhost:3306/", "com.mysql.jdbc.Driver", "mypmscrumdb", "root", "scrumPM2012");

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowForumServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");
		String fileName; 
		String forumID = request.getParameter("forumID");
		if(forumID != null){
			fileName = "/showForum.jsp?forumID=" + forumID;
			Forum forumObj = Forum.findById(myDb, Integer.parseInt(forumID));
			pmPersistence.RetrieveResult<domainModel.Thread> threadResult = domainModel.Thread.findByForum(myDb, forumObj);
			domainModel.Thread threadObj = threadResult.next();
			StringBuilder threads = new StringBuilder("");
			if(threadObj!=null){
				threads.append("<table><tr><td>title<td>Created date<td>author");
				while(threadObj != null){		
					threads.append("<tr>");
					threads.append("<td><a href='showThread.jsp?threadID=" + threadObj.getThreadId() + "'>" + threadObj.getTitle() + "</a>");
					threads.append("<td>" + threadObj.getDate());
					threads.append("<td>" + threadObj.getUser().getUserName() + "<br>");
					threadObj = threadResult.next();
				}
				request.setAttribute("isEmpty", "false");
				threads.append("</table>");
			}
			else{
				request.setAttribute("isEmpty", "true");
			}
			request.setAttribute("records",threads.toString());
		}
		else
			fileName = "/error.jsp";
		RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(fileName);
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
