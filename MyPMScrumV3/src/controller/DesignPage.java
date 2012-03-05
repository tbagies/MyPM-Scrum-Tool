package controller;

public class DesignPage {
	private StringBuilder page= new StringBuilder("");
	DesignPage(String str){
		page.append(str);
	}
	DesignPage(){
		page.append("<html>");
		page.append("<body>");
		String str="<table><tr><td><img border='0' src='images/h8y09170.jpg'></td><tr><td></table>";
		page.append(str);
	}
	public void pageCon(String str){
		page.append(str);
	}
public String getPage(){
		page.append("</body>");
		page.append("</html>");
		return page.toString();
	}
}
