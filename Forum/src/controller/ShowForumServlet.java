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
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		String msg="";
		String forumID = request.getParameter("forumID");
		String fileName = "/showForum.jsp?forumID=" + forumID;
		if(forumID != null){
			Forum forumObj = Forum.findById(myDb, Integer.parseInt(forumID));
			pmPersistence.RetrieveResult<domainModel.Thread> threadResult = domainModel.Thread.findByForum(myDb, forumObj);
			domainModel.Thread threadObj = threadResult.next();
			StringBuilder threads = new StringBuilder("");
			if(threadObj!=null){	
				pmPersistence.RetrieveResult<domainModel.Post> postResult;
				domainModel.Post postObj ;
				while(threadObj != null){	
					threads.append("<table>");
					threads.append("<tr><td colspan=2>" + threadObj.getTitle());
					threads.append("<tr><td colspan=2>" + threadObj.getText());
					threads.append("<tr><td align=left>" + threadObj.getUser().getUserName());
					threads.append("<td align=right>" + threadObj.getDate());	
					threads.append("<tr><td colspan=2 align=right><a href=replyToThread.jsp?threadID=" + threadObj.getThreadId() + ">Reply" + "</a>");
					threads.append("</table>");
					postResult = domainModel.Post.findByThread(myDb, threadObj);
					postObj = postResult.next();
					if(postObj!=null){
						threads.append("<table>");
						while(postObj != null){		
							threads.append("<tr><td colspan=2>");
							threads.append(postObj.getTitle());
							threads.append("<tr ><td colspan=2>" + postObj.getText());
							threads.append("<tr><td align=left>" + postObj.getUser().getUserName());
							threads.append("<td align=right>" + postObj.getDate());
							postObj = postResult.next();
						}
						threads.append("</table>");
					}
					threadObj = threadResult.next();
				}
				request.setAttribute("isEmpty", "false");
			}
			else{
				request.setAttribute("isEmpty", "true");
			}
			request.setAttribute("records",threads.toString());
		}	
		else{
			msg="Forum ID has not been selected";
		}
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
