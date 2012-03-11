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
				String msg = roleDesc + " Welcome to MyPmScrumTool for " + classNo + "click on the follwoing link to finish setting up your account the website"
						+ " <a href='http://sestudio2.csctcis.cti.depaul.edu/createUserAccount.html?userEmail=" + userEmail + "'> create user account </a>";
				SendMail invitation = new SendMail(userEmail,msg);
				page.pageCon(invitation.send());
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
