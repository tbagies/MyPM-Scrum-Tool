package domainModel;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import pmPersistence.Database;
import pmPersistence.MD5Hash;
import pmPersistence.PersistentObject;
import pmPersistence.RetrieveResult;


public final class User extends PersistentObject {

	private final static String TABLE = "users";
	private final static String USER_ID = "UserID";
	private final static String PROJECT_ID = "ProjectID";
	private final static String NAME = "UserName";
	private final static String ACCESS_LEVEL = "AccessLevelID";
	private final static String EMAIL = "Email";
	private final static String CLASS = "Class";
	private final static String PASSWORD_HASH = "PasswordHash";
	private final static String PASSWORD_SALT = "PasswordSalt";
	
	
	public static RetrieveResult<User> getAll(Database db)
	{
		return retrievePersistentObjects(db, User.class, TABLE, null);
	}
	
	public static RetrieveResult<User> findByTask(Database db, Task task)
	{
		return retrievePersistentObjects(db, User.class, 
				"SELECT " + TABLE + ".* FROM " + 
				TABLE + " INNER JOIN "+ UserTaskMapping.TABLE + " ON " +
				TABLE + "." + USER_ID + "=" + UserTaskMapping.TABLE + "." + UserTaskMapping.USER_ID + " WHERE " +
				UserTaskMapping.TABLE + "." + UserTaskMapping.TASK_ID + "=" + task.getTaskId().toString());	
	}
	
	public static RetrieveResult<User> findByProject(Database db, Project project)
	{
		return retrievePersistentObjects(db, User.class, TABLE, PROJECT_ID + "=" + project.getProjectId().toString());
	}
	public static User findById(Database db, Integer id)
	{
		return retrieveObjectByKey(db, User.class, TABLE, USER_ID, id);
	}
	
	public static User findByName(Database db, String name)
	{
		RetrieveResult<User> result = retrievePersistentObjects(db, User.class, TABLE, NAME + "=" + Database.sanitize(name));
		return result.next();
	}
	
	public static User findByEmail(Database db, String email)
	{
		RetrieveResult<User> result = retrievePersistentObjects(db, User.class, TABLE, EMAIL + " = " + Database.sanitize(email));
		return result.next();
	}
	
	
	public User(Database db) {
		super(db, TABLE, USER_ID);

	}

	public UserProfile getProfile()
	{
		return UserProfile.findById(getDatabase(), getUserId());
	}
	
	public Integer getUserId()
	{
		return (Integer)getPersistentValue(USER_ID);
	}
	
	public Project getProject()
	{
		return Project.findById(getDatabase(), (Integer)getPersistentValue(PROJECT_ID));
	}
	
	public Role getRole()
	{
		return Role.findById(getDatabase(), (Integer)getPersistentValue(ACCESS_LEVEL));
	}
	
	public String getUserName()
	{
		return (String)getPersistentValue(NAME);
	}
	
	
	public boolean getPasswordMatches(String password)
	{
		String value = password + getSalt();
		boolean ret = false;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] thedigest = md.digest(value.getBytes("UTF-8"));
			String testHash= new String();
			for(int i = 0; i < thedigest.length; ++i)
			{
				testHash = testHash + String.format("%x", thedigest[i]);
			}
			String realhash = (String)getPersistentValue(PASSWORD_HASH);
			
			if(realhash.equals(testHash))
			{
				ret = true;
			}
		} 
		catch (UnsupportedEncodingException e) {
		}
		catch (NoSuchAlgorithmException e) {
		}
		return ret;
	}
	
	public String getClassName()
	{
		return (String)getPersistentValue(CLASS);
	}
	
	public String getEmail()
	{
		return (String)getPersistentValue(EMAIL);
	}
	
	public void setProject(Project project)
	{
		setPersistentValue(PROJECT_ID, project.getProjectId());
	}
	
	public void setRole(Role role)
	{
		setPersistentValue(ACCESS_LEVEL, role.getAccessLevelId());
	}
	
	public void setUserName(String username)
	{
		setPersistentValue(NAME, username);
	}
	
	private String getSalt()
	{
		String ret = (String)getPersistentValue(PASSWORD_SALT);
		if(ret == null)
		{
			//Generate a 16 byte salt
			final String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789`~!@#$%^&*()-=_+{}[]\\|:;'\"<,>.?/";
			char[] salt = new char[16];
			Random rng = new Random();
		    for (int i = 0; i < 16; i++)
		    {
		    	salt[i] = characters.charAt(rng.nextInt(characters.length()));
		    }
			ret = new String(salt);
			
			setPersistentValue(PASSWORD_SALT, ret);
		}
		return ret;
	}
	public void setPassword(String password)
	{
		MD5Hash md5 = new MD5Hash(password + getSalt());
		setPersistentValue(PASSWORD_HASH, md5);
	}
	
	public void setEmail(String email)
	{
		setPersistentValue(EMAIL, email);
	}

	public void setClassName(String className)
	{
		setPersistentValue(CLASS, className);
	}
	
	public RetrieveResult<CustomerReport> getCustomerReports()
	{
		return CustomerReport.findByUser(getDatabase(), this);
	}
	
	public RetrieveResult<Post> getPosts()
	{
		return Post.findByUser(getDatabase(), this);
	}
	
	public RetrieveResult<ScrumReport> getScrumReports()
	{
		return ScrumReport.findByUser(getDatabase(), this);
	}
	
	public RetrieveResult<Task> getTasks()
	{
		return Task.findByUser(getDatabase(), this);
	}
	
	public RetrieveResult<Thread> getThreads()
	{
		return Thread.findByUser(getDatabase(), this);
	}

	//return true if the user is assigned to the task or the user is already assigned to the task
	public boolean assignTask(Task task)
	{
		boolean ret = false;
		//can only assign a user if the task already exists in the database
		if(!isNew())
		{
			//first check if the user is already assigned to the task
			if(isTaskAssigned(task))
			{
				ret = true;
			}
			else
			{
				UserTaskMapping mapping = new UserTaskMapping(getDatabase());
				mapping.setTask(task);
				mapping.setUser(this);
				ret = mapping.persist();
			}
		}
		return ret;
	}
	
	public boolean removeTask(Task task)
	{
		boolean ret = false;
		if(!isNew())
		{
			ret = true;
			RetrieveResult<UserTaskMapping> rs = UserTaskMapping.findByTaskAndUser(getDatabase(), task, this);
			UserTaskMapping mapping = rs.next();
			//there should only be one resource record per user/task pair, but just in case...
			while(mapping != null)
			{
				if(!mapping.delete())
				{
					ret = false;
				}
				mapping = (UserTaskMapping)rs.next();
			}
		}
		return ret;
	}
	
	public boolean isTaskAssigned(Task task)
	{
		boolean ret = false;
		if(!isNew())
		{
			if(UserTaskMapping.findByTaskAndUser(getDatabase(), task, this).next() != null)
			{
				ret = true;
			}
		}
		return ret;
	}
}
