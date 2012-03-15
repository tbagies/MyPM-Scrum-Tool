package model;

import pmPersistence.Database;

import domainModel.Role;
import domainModel.User;

public class PMSession {
	private String userName;
	private String password;
	public PMSession(String userName, String password){
		this.userName = userName;
		this.password = password;
	}
	
	public boolean isSession(){
		if(userName== null || password==null)
			return false;
		if(!userName.isEmpty()){
			pmPersistence.Database myDb = new pmPersistence.Database("jdbc:mysql://localhost:3306/", "com.mysql.jdbc.Driver", "mypmscrumdb", "root", "scrumPM2012");       
			pmPersistence.RetrieveResult result = myDb.retrievePersistentObjects(User.class, User.TABLE, "UserName = " + Database.sanitize(userName));
			if (result.next() == null)
				return false;
			result = myDb.retrievePersistentObjects(User.class, User.TABLE, "UserPassword = " + Database.sanitize(password));
			if (result.next() == null)
				return false;
		return true;
		}
		return false;
	}
	
	public boolean isInstructor(){
		if(userName== null || password==null)
			return false;
		if(!userName.isEmpty()){
			pmPersistence.Database myDb = new pmPersistence.Database("jdbc:mysql://localhost:3306/", "com.mysql.jdbc.Driver", "mypmscrumdb", "root", "scrumPM2012");       
			pmPersistence.RetrieveResult result = myDb.retrievePersistentObjects(Role.class, Role.TABLE, "RoleDesc = " + Database.sanitize("Instructor"));
			domainModel.Role roleObj = (domainModel.Role)result.next();
			if(roleObj == null)
			{
				return false;
			}
			result = myDb.retrievePersistentObjects(User.class, User.TABLE, "AccessLevelID = " + roleObj.getAccessLevelId());
			if(result.next() == null)
				return false;
		return true;
		}
		return false;
	}
}
