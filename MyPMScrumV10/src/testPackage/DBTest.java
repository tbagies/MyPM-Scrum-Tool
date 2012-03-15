package testPackage;

import domainModel.User;
import pmPersistence.Database;


public class DBTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String dbName="mypmscrumdb";
		String user="root";
		String pass="scrumPM2012";
		Database db = new Database("jdbc:mysql://localhost:3306/", 
									"com.mysql.jdbc.Driver", 
									dbName, 
									user, 
									pass);
		
		//User usr = (User)db.createPersistentObject("User", "users");
		User usr = new User(db);
		
		
		
		usr.setUserName("Brian");
		
		db.storePersistentObject(usr);
		int uid = usr.getUserId();
		User usr2 = (User)db.retrieveObjectByKey(User.class, User.TABLE, new Integer(uid));
		//RetrieveResult result = db.retrievePersistentObjects(User.class, User.TABLE, "UserID = " + Integer.toString(uid)); 
		//User usr2 = (User) result.next();
		
		if(usr2 != null)
		{
			System.out.println(usr2.getUserName());
		}
		
	}

}
