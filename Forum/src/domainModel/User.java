package domainModel;
import pmPersistence.Database;
import pmPersistence.PersistentObject;
import pmPersistence.RetrieveResult;


public class User extends PersistentObject {

	public final static String TABLE = "users";
	private final static String USER_ID = "UserID";
	private final static String PROJECT_ID = "ProjectID";
	private final static String NAME = "UserName";
	private final static String PASSWORD = "UserPassword";
	private final static String ACCESS_LEVEL = "AccessLevelID";
	private final static String EMAIL = "Email";
	private final static String CLASS = "Class";
	
	private UserProfile myProfile;
	private Role myRole;
	
	public static User findByName(Database db, String name)
	{
		RetrieveResult result = db.retrievePersistentObjects(User.class, TABLE, NAME + " = " + name);
		return (User)result.next();
	}
	
	public static User findByEmail(Database db, String email)
	{
		RetrieveResult result = db.retrievePersistentObjects(User.class, TABLE, EMAIL + " = " + email);
		return (User)result.next();
	}
	public User(Database db) {
		super(db.getTable(TABLE));

	}
	
	public UserProfile getProfile()
	{
		if(myProfile == null)
		{
			myProfile = (UserProfile)myTable.Db.retrieveObjectByKey(UserProfile.class, UserProfile.TABLE, new Integer(getUserId()));
		}
		return myProfile;
	}
	
	public int getUserId()
	{
		Integer i =(Integer)getPersistentValue(USER_ID);
		if(i == null)
		{
			return 0;
		}
		return i.intValue();
	}
	
	public int getProjectId()
	{
		Integer i =(Integer)getPersistentValue(PROJECT_ID);
		if(i == null)
		{
			return 0;
		}
		return i.intValue();
	}
	
	public Role getRole()
	{
		if(myRole == null)
		{
			Integer accessLevel =(Integer)getPersistentValue(ACCESS_LEVEL);
			if(accessLevel!=null)
			{
				myRole = (Role)myTable.Db.retrieveObjectByKey(Role.class, Role.TABLE, accessLevel);
			}
		}
		return myRole;
	}
	
	public String getUserName()
	{
		return (String)getPersistentValue(NAME);
	}
	
	public String getPassword()
	{
		return (String)getPersistentValue(PASSWORD);
	}
	
	public String getClassName()
	{
		return (String)getPersistentValue(CLASS);
	}
	
	public String getEmail()
	{
		return (String)getPersistentValue(EMAIL);
	}
	
	public void setProjectId(int id)
	{
		setPersistentValue(PROJECT_ID, new Integer(id));
	}
	/*
	public void setRole(int accessId)
	{
		setRole((Role)myTable.Db.retrieveObjectByKey(Role.class, Role.TABLE, new Integer(accessId)));
	}
	*/
	
	public void setRole(Role role)
	{
		if(role != null)
		{
			myRole = role;
			setPersistentValue(ACCESS_LEVEL, new Integer(role.getAccessLevelId()));
		}
	}
	
	public void setUserName(String username)
	{
		setPersistentValue(NAME, username);
	}
	
	public void setPassword(String password)
	{
		setPersistentValue(PASSWORD, password);
	}
	
	public void setEmail(String email)
	{
		setPersistentValue(EMAIL, email);
	}

	public void setClassName(String className)
	{
		setPersistentValue(CLASS, className);
	}
	
	// add By Taghreed for InviteUserServelt
		public void setAccessLevel(int accessLevel)
		{
			setPersistentValue(ACCESS_LEVEL, accessLevel);
		}

}
