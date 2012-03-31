package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domainModel.User;

/**
 * Servlet implementation class ShowAllThreadssServlet
 */
@WebServlet("/ShowAllThreadssServlet")
public class ShowAllThreadssServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowAllThreadssServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		pmPersistence.RetrieveResult result = myDb.retrieveAllPersistentObjects(Thread.class, Thread.TABLE);
//		domainModel.Thread threadObj = (domainModel.Thread)result.next();
		
//		if(threadObj != null){
			request.setAttribute("isEmpty", "false");
			DesignPage page = new DesignPage("<table border=1 align='center'><tr><th>User Account"
					+ "<th>Email<th>Class Name<th>Role");
		//	while(threadObj != null){
		//		if(threadObj.getUserName()== null)
					page.pageCon("<tr><td>is invited but not create an account");
		//		else
		/*			page.pageCon("<tr><td>" + threadObj.getUserName());
				page.pageCon("<td>" + threadObj.getEmail());
				page.pageCon("<td>" + threadObj.getClassName());
				page.pageCon("<td>" + threadObj.getRole().getDescription());	
				threadObj = (domainModel.Thread) result.next();		
		//	}
		 * 
		 */
			page.pageCon("</table>");
			request.setAttribute("records",page.getPage());
	//	}
		//else{
			request.setAttribute("isEmpty", "true");
	//	}	
		RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/showAllThreads.jsp");
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
