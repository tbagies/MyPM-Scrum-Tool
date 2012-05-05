package domainModel;

import pmPersistence.Database;
import pmPersistence.PersistentObject;
import pmPersistence.RetrieveResult;

public class UserProjectMapping extends PersistentObject {
	static final String TABLE="projectmembers";
	private static final String ID = "ID";
	//package private for these fields
	static final String USER_ID = "UserID";
	static final String PROJECT_ID = "ProjectID";
	public UserProjectMapping(Database db) {
		super(db, TABLE, ID);
	}
	
	static RetrieveResult<UserProjectMapping> findByProjectAndUser(Database db, Project project, User user)
	{
		return retrievePersistentObjects(db, UserProjectMapping.class, TABLE, USER_ID + "="+user.getUserId().toString() + " AND " + PROJECT_ID + "=" + project.getProjectId().toString());
	}
	
	public Integer getId()
	{
		return (Integer)getPersistentValue(ID);
	}
	
	public User getUser()
	{
		return User.findById(getDatabase(), (Integer)getPersistentValue(USER_ID));
	}
	
	public void setUser(User user)
	{
		setPersistentValue(USER_ID, user.getUserId());
	}
	
	public Forum getProject()
	{
		return Forum.findById(getDatabase(), (Integer)getPersistentValue(PROJECT_ID));
	}
	
	public void setProject(Project project)
	{
		setPersistentValue(PROJECT_ID, project.getProjectId());
	}

}
