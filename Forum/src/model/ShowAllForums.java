package model;

import domainModel.Forum;
import domainModel.Role;
import domainModel.User;

public class ShowAllForums {
    private static final pmPersistence.Database myDb = new pmPersistence.Database("jdbc:mysql://localhost:3306/", "com.mysql.jdbc.Driver", "mypmscrumdb", "root", "scrumPM2012");
	
    public static pmPersistence.RetrieveResult<Forum> showForums(User userSession){
		pmPersistence.RetrieveResult<Forum> forumResult;
		if(userSession.getRole().getAccessLevelId().equals(Role.INSTRUCTOR))
			forumResult= Forum.getAll(myDb);		
		else
			forumResult = Forum.findByProject(myDb, userSession.getProject());
		return forumResult;
	}
}
