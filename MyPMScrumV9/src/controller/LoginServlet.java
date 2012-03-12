package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import domainModel.User;

import pmPersistence.Database;

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
        PrintWriter out = response.getWriter();
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		DesignPage page= new DesignPage();
		if((userName == null) || userName.isEmpty() || (password == null) || password.isEmpty()){
			page.pageCon("user name and password must be entered");
		}
		else if(userName.length()>45 || password.length()>10){
			page.pageCon("user name and password are wrong");
		}
		else{
			page.pageCon("userName: " + userName + "\npassword: " + password);
			pmPersistence.RetrieveResult result = myDb.retrievePersistentObjects(User.class, User.TABLE, "UserName = " + Database.sanitize(userName));
			domainModel.User userObj = (domainModel.User)result.next();
			if(userObj != null)
			{
				page.pageCon(" User Found! ");	
				if(userObj.getPassword().equals(password))
				{
					page.pageCon(" And the password matches! ");
					domainModel.Role role = userObj.getRole();
					if(role != null)
					{
						page.pageCon(" Role = " + role.getDescription());
						HttpSession session = request.getSession(true);
						session.setAttribute("userName", userName);
						session.setAttribute("userPassword", password);
						session.setAttribute("AccessLevelID", role.getAccessLevelId());
						page.pageCon("<p>You will be redirect within 2 seconds, if not happened");
						page.pageCon("<a href='dashboardAdmin.jsp'>click here</a>");
						page.pageCon("<META HTTP-EQUIV='refresh' CONTENT='2;URL=dashboardAdmin.jsp'>");
					}
					else
					{
						page.pageCon(" Unknown Role. ");
					}
				}
				else
				{
					page.pageCon("\nBut the password does not match...");
				}
			}
			else
			{
				page.pageCon("\nUser Not Found...");
			}
		}
		out.println(page.getPage());
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
