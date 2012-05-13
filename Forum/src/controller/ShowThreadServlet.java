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
 * Servlet implementation class ShowThreadServlet
 */
@WebServlet("/ShowThreadServlet")
public class ShowThreadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final pmPersistence.Database myDb = new pmPersistence.Database("jdbc:mysql://localhost:3306/", "com.mysql.jdbc.Driver", "mypmscrumdb", "root", "scrumPM2012");
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowThreadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String threadID = request.getParameter("threadID");
		String fileName = "/replyToThread.jsp?threadID=" + threadID; 
		String msg="";
		if(threadID != null){
			domainModel.Thread threadObj = domainModel.Thread.findById(myDb, Integer.parseInt(threadID));
			pmPersistence.RetrieveResult<domainModel.Post> postResult = domainModel.Post.findByThread(myDb, threadObj);
			domainModel.Post postObj = postResult.next();
			StringBuilder posts = new StringBuilder("");
			posts.append("<table>");
			posts.append("<tr>");
			posts.append("<td colspan=2>" + threadObj.getTitle());
			posts.append("<tr><td colspan=2>" + threadObj.getText());
			posts.append("<tr><td align=left>" + threadObj.getUser().getUserName());
			posts.append("<td align=right>" + threadObj.getDate());
			if(postObj!=null){
				while(postObj != null){		
					posts.append("<tr><td colspan=2>");
					posts.append(postObj.getTitle());
					posts.append("<tr ><td colspan=2>" + postObj.getText());
					posts.append("<tr><td>" + postObj.getUser().getUserName() + "<td>" + postObj.getDate());
					postObj = postResult.next();
				}
			}
			request.setAttribute("isEmpty", "false");
			posts.append("</table>");
			request.setAttribute("records",posts.toString());
		}
		else
			msg ="You have to select a thread first";
		RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(fileName);
		requestDispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
