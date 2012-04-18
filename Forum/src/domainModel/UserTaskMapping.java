package domainModel;

import pmPersistence.Database;
import pmPersistence.PersistentObject;
import pmPersistence.RetrieveResult;

//class must be public so the persistence subsystem can instantiate it
public final class UserTaskMapping extends PersistentObject {
	static final String TABLE="resources";
	private static final String RESOURCE_ID = "ResourceID";
	//package private for these fields
	static final String USER_ID = "UserID";
	static final String TASK_ID = "TaskID";
	
	//ctor must be public so the persistence subsystem can instantiate the class
	public UserTaskMapping(Database db) {
		super(db, TABLE, RESOURCE_ID);
	}
	
	//all other methods should be package private
	static RetrieveResult<UserTaskMapping> findByTaskAndUser(Database db, Task task, User user)
	{
		return retrievePersistentObjects(db, UserTaskMapping.class, TABLE, USER_ID + "="+user.getUserId().toString() + " AND " + TASK_ID + "=" + task.getTaskId().toString());
	}
	
	Integer getResourceId()
	{
		return (Integer)getPersistentValue(RESOURCE_ID);
	}
	
	User getUser()
	{
		return User.findById(getDatabase(), (Integer)getPersistentValue(USER_ID));
	}
	
	void setUser(User user)
	{
		setPersistentValue(USER_ID, user.getUserId());
	}
	
	Task getTask()
	{
		return Task.findById(getDatabase(), (Integer)getPersistentValue(TASK_ID));
	}
	
	void setTask(Task task)
	{
		setPersistentValue(TASK_ID, task.getTaskId());
	}

}
