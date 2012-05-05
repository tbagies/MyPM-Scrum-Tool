package controller;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domainModel.Forum;
import domainModel.Task;

/**
 * Servlet implementation class TestingTaskServlet
 */
@WebServlet("/TestingTaskServlet")
public class TestingTaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final pmPersistence.Database myDb = new pmPersistence.Database("jdbc:mysql://localhost:3306/", "com.mysql.jdbc.Driver", "mypmscrumdb", "root", "scrumPM2012"); 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestingTaskServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		Task taskObj = new Task(myDb);
		taskObj.setName("Testing Task");
		taskObj.setDescription("Java");
        taskObj.persist(); // working
        
		Forum forumObj = new Forum(myDb);
		java.util.Date today = new java.util.Date();
		long t = today.getTime();
		Date date = new Date(t);
		forumObj.setCreatedDate(date);
		forumObj.setTitle("Testing Forum");
		forumObj.persist(); //working
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
