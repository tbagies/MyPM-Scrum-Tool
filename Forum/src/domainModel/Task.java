package domainModel;

import java.sql.Date;

import pmPersistence.Database;
import pmPersistence.PersistentObject;
import pmPersistence.RetrieveResult;

public class Task  extends PersistentObject {
	static final String TABLE = "task";
	private static final String TASK_ID = "TaskID";
	private static final String NAME = "name";
	private static final String DESCRIPTION = "Description";
	private static final String START_DATE = "StartDate";
	private static final String END_DATE = "EndDate";
	private static final String PROJECT_ID = "ProjectID";
	
	public static RetrieveResult<Task> getAll(Database db)
	{
		return retrievePersistentObjects(db, Task.class, TABLE, null);
	}
	
	public static RetrieveResult<Task> findByUser(Database db, User user)
	{
		return retrievePersistentObjects(db, Task.class, 
				"SELECT " + 
				TABLE + ".* FROM " + 
				TABLE + " INNER JOIN " + UserTaskMapping.TABLE + " ON " +
				TABLE + "." + TASK_ID + "=" + UserTaskMapping.TABLE + "." + UserTaskMapping.TASK_ID + " WHERE " +
				UserTaskMapping.TABLE + "." + UserTaskMapping.USER_ID + "=" + user.getUserId().toString());
	}
	
	public static RetrieveResult<Task> findByProject(Database db, Project project)
	{
		return retrievePersistentObjects(db, Task.class, TABLE, PROJECT_ID +"="+project.getProjectId());
	}
	
	public static RetrieveResult<Task> findUnassignedByProject(Database db, Project project)
	{
		return retrievePersistentObjects(db, Task.class, 
				"SELECT " + 
				TABLE + ".* FROM " + 
				TABLE + " LEFT JOIN " + UserTaskMapping.TABLE + " ON " +
				TABLE + "." + TASK_ID + "=" + UserTaskMapping.TABLE + "." + UserTaskMapping.TASK_ID + " WHERE " +
				UserTaskMapping.TABLE + "." + UserTaskMapping.USER_ID + " IS NULL AND " +
				TABLE + "." + PROJECT_ID + "=" + project.getProjectId().toString());
	}
	
	public static Task findById(Database db, Integer id)
	{
		return retrieveObjectByKey(db, Task.class, TABLE, id);
	}
	
	public Task(Database db)
	{
		super(db, TABLE);
	}
	
	public Integer getTaskId()
	{
		return (Integer)getPersistentValue(TASK_ID);
	}
	
	public String getName()
	{
		return (String)getPersistentValue(NAME);
	}
	
	public void setName(String name)
	{
		setPersistentValue(NAME, name);
	}
	
	public String getDescription()
	{
		return (String)getPersistentValue(DESCRIPTION);
	}
	
	public void setDescription(String description)
	{
		setPersistentValue(DESCRIPTION, description);
	}
	
	public Date getStartDate()
	{
		return (Date)getPersistentValue(START_DATE);
	}
	
	public void setStartDate(Date date)
	{
		setPersistentValue(START_DATE, date);	
	}
	
	public Date getEndDate()
	{
		return (Date)getPersistentValue(END_DATE);
	}
	
	public void setEndDate(Date date)
	{
		setPersistentValue(END_DATE, date);	
	}
	
	public Project getProject()
	{
		return Project.findById(getDatabase(), (Integer)getPersistentValue(PROJECT_ID));
	}
	
	public void setProject(Project project)
	{
		setPersistentValue(PROJECT_ID, project.getProjectId());
	}
	
	//return true if the user is assigned to the task or the user is already assigned to the task
	public boolean assignUser(User user)
	{
		boolean ret = false;
		//can only assign a user if the task already exists in the database
		if(!isNew)
		{
			//first check if the user is already assigned to the task
			if(isUserAssigned(user))
			{
				ret = true;
			}
			else
			{
				UserTaskMapping mapping = new UserTaskMapping(getDatabase());
				mapping.setTask(this);
				mapping.setUser(user);
				ret = mapping.persist();
			}
		}
		return ret;
	}
	
	public boolean removeUser(User user)
	{
		boolean ret = false;
		if(!isNew)
		{
			ret = true;
			RetrieveResult<UserTaskMapping> rs = UserTaskMapping.findByTaskAndUser(getDatabase(), this, user);
			UserTaskMapping mapping = rs.next();
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

	public boolean isUserAssigned(User user)
	{
		boolean ret = false;
		if(!isNew)
		{
			if(UserTaskMapping.findByTaskAndUser(getDatabase(), this, user).next() != null)
			{
				ret = true;
			}
		}
		return ret;
	}
}
