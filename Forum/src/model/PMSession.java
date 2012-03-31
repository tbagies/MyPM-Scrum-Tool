package model;

import domainModel.User;

public class PMSession {
	private Integer userID;
	
	public PMSession (Integer userID){
		this.userID = userID;
	}

	public boolean isSession(){
		if(userID == null)
			return false;
			//pmPersistence.Database myDb = new pmPersistence.Database("jdbc:mysql://localhost:3306/", "com.mysql.jdbc.Driver", "mypmscrumdb", "root", "scrumPM2012");       
		//	pmPersistence.RetrieveResult result = myDb.retrievePersistentObjects(User.class, User.TABLE, "UserID = " + userID);
	//		if (result.next() == null)
	//			return false;
		    return true;
	}
}
