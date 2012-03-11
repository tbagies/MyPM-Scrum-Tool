package controller;

import java.io.IOException;
import java.io.PrintWriter;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domainModel.UserProfile;

/**
 * Servlet implementation class CreateUserProfileServlet
 */
@WebServlet("/CreateUserProfileServlet")
public class CreateUserProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final pmPersistence.Database myDb = new pmPersistence.Database("jdbc:mysql://localhost:3306/", "com.mysql.jdbc.Driver", "mypmscrumdb", "root", "scrumPM2012");   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateUserProfileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		response.setContentType("text/htm;charset=UTF-8");
        PrintWriter out = response.getWriter();
		String firstName = request.getParameter("firstName").trim();
		String lastName = request.getParameter("lastName").trim();
		String alternativeEmail = request.getParameter("alternativeEmail").trim();
		String birthYear = request.getParameter("birthYear");
		String gender = request.getParameter("gender").trim();
		String contact = request.getParameter("contact").trim();
		DesignPageManuel page= new DesignPageManuel();
		
		//if(SessionUser.userId==0){
		if(SessionUser.user == null) {
	    	page.pageCon("<br>Sorry you are not an active user , please login first !</br>");
	    	out.println(page.getPageLogin()); 
	    	return;	    	
	    }
		
		if(firstName.isEmpty() || lastName.isEmpty() ||firstName.length()>45 || lastName.length()>45){
		    	page.pageCon("<br>Please enter a valid name  !</br>");
		    	out.println(page.getPageProfile());
		    	return;
		   }

		//pmPersistence.RetrieveResult result = myDb.retrievePersistentObjects(UserProfile.class, UserProfile.TABLE,  "FirstName = " + Database.sanitize(firstName));
		//domainModel.UserProfile userProfileObj = (domainModel.UserProfile)result.next();
		domainModel.UserProfile  userProfileObj = SessionUser.user.getProfile();
	    	  
	    if(!alternativeEmail.contains("@") ||!alternativeEmail.contains(".")){
	    	page.pageCon("<br>Please enter a valid email address  !</br>");
	    	out.println(page.getPageProfile());
	    	return;
	    }
	    
	    if(contact.isEmpty()  || contact.length()>10){
	    	page.pageCon("<br>Please enter a valid contact phone number  !</br>");
	    	out.println(page.getPageProfile());
	    	return;
	    }
	   	    
	    UserProfile userProfile = userProfileObj;
		if(userProfileObj == null)
		{
			userProfile = new UserProfile(myDb);
		}
		
		
		userProfile.setFirstName(firstName);
		userProfile.setLastName(lastName);
		//userProfile.setUserId(SessionUser.userId);
		userProfile.setUserId(SessionUser.user.getUserId());
		userProfile.setAltEmail(alternativeEmail);
		
		userProfile.setDOB(Integer.valueOf(birthYear).intValue());
		userProfile.setGender(gender);
		userProfile.setTelephone(contact);
		myDb.storePersistentObject(userProfile);
		page.pageCon("<br>User succesfully registered!<br>");
		//}
		//else{
		//	page.pageCon("<br>User Profile has been created before !<br>");	
		//}
		out.println(page.getPageLogin());
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
