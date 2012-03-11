package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.SendMail;

import pmPersistence.Database;
import domainModel.Role;
import domainModel.User;


/**
 * Servlet implementation class InviteUserServlet
 */
@WebServlet("/InviteUserServlet")
public class InviteUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final pmPersistence.Database myDb = new pmPersistence.Database("jdbc:mysql://localhost:3306/", "com.mysql.jdbc.Driver", "mypmscrumdb", "root", "scrumPM2012");
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InviteUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
		String userEmail = request.getParameter("userEmail");
		String roleDesc = request.getParameter("roleDesc");
		String classNo = request.getParameter("classNo");
		DesignPage page= new DesignPage();
		if((userEmail == null) || userEmail.isEmpty() || (roleDesc == null) ||roleDesc.isEmpty() || (classNo == null) || classNo.isEmpty()){
			page.pageCon("all fields are requiered");
		}
		else if(!(userEmail.contains("@")) || !(userEmail.contains("."))){
			page.pageCon("user email is wrong");
			
		}
		else if( !(classNo.equals("SE430")) && !(classNo.equals("SE477"))){
			page.pageCon("class number is wrong");
		}
		else{
			pmPersistence.RetrieveResult result = myDb.retrievePersistentObjects(Role.class, Role.TABLE, "RoleDesc = " + Database.sanitize(roleDesc));
			domainModel.Role roleObj = (domainModel.Role)result.next();
			if(roleObj == null)
			{
				page.pageCon("Role is wrong " + roleDesc);
			}
			else{
				result = myDb.retrievePersistentObjects(User.class, User.TABLE, "Email = " + Database.sanitize(userEmail));
				if(result !=null){
						page.pageCon("<br>This user email address already exists<br>Please use a different user email<br>");
				}
				else {
				String msg = roleDesc + " Welcome to MyPmScrumTool for " + classNo + "click on the follwoing link to finish setting up your account the website"
						+ " <a href='http://sestudio2.cstcis.cti.depaul.edu/createUserAccount.jsp?userEmail=" + userEmail + "'> create user account </a>";
				SendMail invitation = new SendMail(userEmail,msg);
				page.pageCon(invitation.send());
				User user = new User(myDb);
				user.setEmail(userEmail);
				user.setClassName(classNo);
				//usr.setRole(Role.getRole(myDb, roleDesc));
				user.setAccessLevel(roleObj.getAccessLevelId());
				myDb.storePersistentObject(user);
				page.pageCon("<br>User succesfully registered!<br>");
				}
			}
			
		}
		out.println(page.getPage());
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
