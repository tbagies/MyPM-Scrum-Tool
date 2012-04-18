package model;

import domainModel.Forum;
import domainModel.Role;
import domainModel.User;

public class ShowAllForums {
    private static final pmPersistence.Database myDb = new pmPersistence.Database("jdbc:mysql://localhost:3306/", "com.mysql.jdbc.Driver", "mypmscrumdb", "root", "scrumPM2012");
	public static String showForums(User userSession){
		pmPersistence.RetrieveResult<Forum> forumResult;
		StringBuilder forums = new StringBuilder("");
		if(userSession.getRole().getAccessLevelId().equals(Role.INSTRUCTOR)){
			forumResult= Forum.getAll(myDb);		}
		else{
			forumResult= Forum.findByUser(myDb, userSession);
		}
		domainModel.Forum forumObj = forumResult.next();
		if(forumObj!=null){
			forums.append("<table><tr><td>forum title<td>Created date<td>Members");
			while(forumObj != null){
				pmPersistence.RetrieveResult<User> resultUser = forumObj.getUsers();				
				forums.append("<tr>");
				forums.append("<td><a href='showForum.jsp?forumID=" + forumObj.getForumId() + "'>" + forumObj.getTitle() + "</a>");
				forums.append("<td>" + forumObj.getCreatedDate());
				User userObj = resultUser.next();
				forums.append("<td>");
				while(userObj!=null){		
					forums.append(userObj.getUserName() + "<br>");
					userObj = resultUser.next();
				}
				if(userSession.getRole().getAccessLevelId().equals(Role.INSTRUCTOR))
					forums.append("<td><a href='deleteForum.jsp?forumID=" + forumObj.getForumId() + "'>Delete</a>");
				forumObj = forumResult.next();
			}
			forums.append("</table>");
		}
		else
			forums.append("No Forum");
		return forums.toString();
	}
}
