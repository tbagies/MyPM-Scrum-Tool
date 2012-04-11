package domainModel;
import pmPersistence.Database;
import pmPersistence.PersistentObject;
import pmPersistence.RetrieveResult;


public class User extends PersistentObject {

	final static String TABLE = "users";
	private final static String USER_ID = "UserID";
	private final static String PROJECT_ID = "ProjectID";
	private final static String NAME = "UserName";
	private final static String PASSWORD = "UserPassword";
	private final static String ACCESS_LEVEL = "AccessLevelID";
	private final static String EMAIL = "Email";
	private final static String CLASS = "Class";
	
	
	public static RetrieveResult<User> getAll(Database db)
	{
		return retrievePersistentObjects(db, User.class, TABLE, null);
	}
	
	public static RetrieveResult<User> findByTask(Database db, Task task)
	{
		return retrievePersistentObjects(db, User.class, 
				"SELECT " + 
				TABLE + ".* FROM " + 
				TABLE + " INNER JOIN resources ON " +
				TABLE + "." + USER_ID + "=resources.UserID WHERE resources.TaskID=" + 
				task.getTaskId().toString());	
	}
	
	public static RetrieveResult<User> findByForum(Database db, Forum forum)
	{
		return retrievePersistentObjects(db, User.class, 
				"SELECT " + 
				TABLE + ".* FROM " + 
				TABLE + " INNER JOIN forummembers ON " +
				TABLE + "." + USER_ID + "=forummembers.UserID WHERE forummembers.ForumID=" + 
				forum.getForumId().toString());	
	}
	
	public static User findById(Database db, Integer id)
	{
		return retrieveObjectByKey(db, User.class, TABLE, id);
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
		super(db, TABLE);

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
	
	public RetrieveResult<CustomerReport> getCustomerReports()
	{
		return CustomerReport.findByUser(getDatabase(), this);
	}
	
	public RetrieveResult<Forum> getForums()
	{
		return Forum.findByUser(getDatabase(), this);
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
		if(!isNew)
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
		if(!isNew)
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
		if(!isNew)
		{
			if(UserTaskMapping.findByTaskAndUser(getDatabase(), task, this).next() != null)
			{
				ret = true;
			}
		}
		return ret;
	}
	
	
	//return true if the user is assigned to the task or the user is already assigned to the task
	public boolean assignForum(Forum forum)
	{
		boolean ret = false;
		//can only assign a user if the task already exists in the database
		if(!isNew)
		{
			//first check if the user is already assigned to the task
			if(isForumAssigned(forum))
			{
				ret = true;
			}
			else
			{
				UserForumMapping mapping = new UserForumMapping(getDatabase());
				mapping.setForum(forum);
				mapping.setUser(this);
				ret = mapping.persist();
			}
		}
		return ret;
	}
	
	public boolean removeForum(Forum forum)
	{
		boolean ret = false;
		if(!isNew)
		{
			ret = true;
			RetrieveResult<UserForumMapping> rs = UserForumMapping.findByForumAndUser(getDatabase(), forum, this);
			UserForumMapping mapping = rs.next();
			//there should only be one resource record per user/task pair, but just in case...
			while(mapping != null)
			{
				if(!mapping.delete())
				{
					ret = false;
				}
				mapping = rs.next();
			}
		}
		return ret;
	}
	
	public boolean isForumAssigned(Forum forum)
	{
		boolean ret = false;
		if(!isNew)
		{
			if(UserForumMapping.findByForumAndUser(getDatabase(), forum, this).next() != null)
			{
				ret = true;
			}
		}
		return ret;
	}
		
		
}