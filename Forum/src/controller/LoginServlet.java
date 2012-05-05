package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import domainModel.User;

import pmPersistence.Database;
import pmPersistence.PersistentObject;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final pmPersistence.Database myDb = new pmPersistence.Database("jdbc:mysql://localhost:3306/", "com.mysql.jdbc.Driver", "mypmscrumdb", "root", "scrumPM2012");
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/htm;charset=UTF-8");
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String fileName="/error.jsp";
		String msg="";
		if((userName == null) || userName.isEmpty() || (password == null) || password.isEmpty()){
			fileName="/login.jsp";
			msg="user name and password must be entered";
		}
		else if(userName.length()>45 || password.length()>10){
			fileName="/login.jsp";
			msg="user name and password are wrong";
		}
		else{
			domainModel.User userObj = User.findByName(myDb,userName);
			if(userObj != null)
			{
				if(userObj.getPasswordMatches(password))
				{
					domainModel.Role role = userObj.getRole();
					if(role != null)
					{
						HttpSession session = request.getSession(true);
						session.setAttribute("userID", userObj.getUserId());
						session.setAttribute("userRole", userObj.getRole().getDescription());
						fileName= "/dashboardAdmin.jsp";
						}
					else
					{
						fileName="/login.jsp";
						msg="Unknown Role";	
					}
				}
				else
				{
					fileName="/login.jsp";
					msg="The password does not match";
				}
			}
			else
			{
				fileName="/login.jsp";
				msg="User Not Found";	
			}
		}
		request.setAttribute("msg", msg);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(fileName);
	    dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
