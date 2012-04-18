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
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");
		String fileName; 
		String threadID = request.getParameter("threadID");
		if(threadID != null){
			fileName = "/showThread.jsp?threadID=" + threadID;
			domainModel.Thread threadObj = domainModel.Thread.findById(myDb, Integer.parseInt(threadID));
			pmPersistence.RetrieveResult<domainModel.Post> postResult = domainModel.Post.findByThread(myDb, threadObj);
			domainModel.Post postObj = postResult.next();
			StringBuilder posts = new StringBuilder("");
			posts.append("<table><tr><td>title<td>post<td>Created date<td>author");
			posts.append("<tr>");
			posts.append("<td>" + threadObj.getTitle());
			posts.append("<td>" + threadObj.getText());
			posts.append("<td>" + threadObj.getDate());
			posts.append("<td>" + threadObj.getUser().getUserName());
			if(postObj!=null){
				while(postObj != null){		
					posts.append("<tr>");
					posts.append("<td>" + postObj.getTitle());
					posts.append("<td>" + postObj.getText());
					posts.append("<td>" + postObj.getDate());
					posts.append("<td>" + postObj.getUser().getUserName());
					postObj = postResult.next();
				}
				
			}
			request.setAttribute("isEmpty", "false");
			posts.append("</table>");
			request.setAttribute("records",posts.toString());
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
	}

}
