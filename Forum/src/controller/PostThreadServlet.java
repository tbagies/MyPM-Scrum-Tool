package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PostThreadServlet
 */
@WebServlet("/PostThreadServlet")
public class PostThreadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
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
		// TODO Auto-generated method stub
		response.setContentType("text/htm;charset=UTF-8");
        PrintWriter out = response.getWriter();
		String title = request.getParameter("title").trim();
		String threadText = request.getParameter("threadText").trim();
		DesignPage page = new DesignPage();
		RequestDispatcher dispatcher;
		if(title == null || threadText==null || threadText.isEmpty()){
			dispatcher = getServletContext().getRequestDispatcher("/missingFields.jsp");
		}
		else{
			// insert the record in database
			dispatcher = getServletContext().getRequestDispatcher("/showAllThreads.jsp");
		}
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
