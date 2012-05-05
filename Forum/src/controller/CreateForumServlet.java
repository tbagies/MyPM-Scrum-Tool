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
		response.setContentType("text/htm;charset=UTF-8");
        PrintWriter out = response.getWriter();
		String forumName = request.getParameter("forumName");
		String memberNo = request.getParameter("i");
		ArrayList<Integer> membersID = new ArrayList<Integer>();
	//	DesignPage page= new DesignPage();
		String fileName="/error.jsp";
		if(memberNo==null || memberNo.isEmpty())
			fileName="error.jsp?msg=choose member name";
		else{
			int j = Integer.parseInt(memberNo);
			for(int i=1; i<j;  i++){
				String memberName = request.getParameter("membersName" + i);
				if(memberName!=null)
					membersID.add(Integer.parseInt(memberName));
			}
		}
		if((forumName == null) || forumName.isEmpty()){
			fileName= fileName + "?msg=Forum name must be entered";
		}
		else if(forumName.length()>45){
			fileName= fileName + "?msg=forum name are wrong";
		}
		else{
			Forum forumObj = new Forum(myDb);
			java.util.Date today = new java.util.Date();
			long t = today.getTime();
			Date date = new Date(t);
			forumObj.setCreatedDate(date);
			forumObj.setTitle(forumName);				
			if(forumObj.persist()){
				if(forumObj.addUsersById(membersID))
					fileName="/showAllForums.jsp";
				else{
					fileName = fileName + "?msg=Error,,, Forum is not created because of Forum Members table";
					if(!forumObj.delete())
						fileName= fileName + "?msg=Error,,, Forum is not deleted";
				}
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
	}

}
