package controller;

public class DesignPageManuel {
	private StringBuilder page= new StringBuilder("");
	DesignPageManuel(String str){
		page.append(str);
	}
	DesignPageManuel(){
		page.append("<html>");
		page.append("<body>");
		String str="";
		page.append(str);
	}
	public void pageCon(String str){
		page.append(str);
	}
	public String getPage(){
		page.append("</table>");
		page.append("<br><br>");
		page.append("<a href=/MyPMScrumV31/CreateUserAccount.htm > If you do not have an account please Create Account Page </a>");
		page.append("<br><br><a href=/MyPMScrumV31/loginT.htm > Please Reenter your Login </a>");
		page.append("</body>");
		page.append("</html>");
		page.append("</body>");
		page.append("</html>");
		return page.toString();
	}
	public String getPageHome(){
		page.append("</table>");
		page.append("<br><br>");
		page.append("<a href=/MyPMScrumV31/Home.htm > Return to Create Account Page </a>");
		page.append("</body>");
		page.append("</html>");
		page.append("</body>");
		page.append("</html>");
		
		return page.toString();
	}
	public String getPageDashboard(){
		page.append("</table>");
		page.append("<br><br>");
		page.append("<a href=/MyPMScrumV31/dashboard.htm > Return to Dashboard Page </a>");
		page.append("</body>");
		page.append("</html>");
		page.append("</body>");
		page.append("</html>");
	
		return page.toString();
	}
	public String getPageLogin(){
		page.append("</table>");
		page.append("<br><br>");
		page.append("<a href=/MyPMScrumV31/loginT.htm > Return to Login Page </a>");
		page.append("</body>");
		page.append("</html>");
		page.append("</body>");
		page.append("</html>");
	
		return page.toString();
	}
	
	public String getPageProfile(){
		page.append("</table>");
		page.append("<br><br>");
		page.append("<br><a href=/MyPMScrumV31/loginT.htm > Return to Login Page </a></br>");
		page.append("<br><a href=/MyPMScrumV31/createUserProfile.htm > Go to Profile Page  </a></br>");
		page.append("</body>");
		page.append("</html>");
		page.append("</body>");
		page.append("</html>");
	
		return page.toString();
	}
}
