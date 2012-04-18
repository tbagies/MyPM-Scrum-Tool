package domainModel;

import java.sql.Date;
import java.util.List;

import pmPersistence.Database;
import pmPersistence.PersistentObject;
import pmPersistence.RetrieveResult;

public class Project extends PersistentObject {
	static final String TABLE = "project";
	private static final String PROJECT_ID = "ProjectID";
	private static final String PROJECT_NAME = "projectName";
	private static final String PROJECT_DESCRIPTION = "projectDescription";
	private static final String PROJECT_CREATED_DATE = "ProjectCreatedDate";
	private static final String PROJECT_DUE_DATE = "ProjectdueDate";
	
	public static RetrieveResult<Project> getAll(Database db)
	{
		return retrievePersistentObjects(db, Project.class, TABLE, null);
	}
	
	public static Project findById(Database db, Integer id)
	{
		return retrieveObjectByKey(db, Project.class, TABLE, id);
	}
	
	public Project(Database db)
	{
		super(db, TABLE);
	}
	
	public Integer getProjectId()
	{
		return (Integer)getPersistentValue(PROJECT_ID);
	}
	
	public String getName()
	{
		return (String)getPersistentValue(PROJECT_NAME);
	}
	
	public void setName(String name)
	{
		setPersistentValue(PROJECT_NAME, name);
	}
	
	public String getDescription()
	{
		return (String)getPersistentValue(PROJECT_DESCRIPTION);	
	}
	
	public void setDescription(String description)
	{
		setPersistentValue(PROJECT_DESCRIPTION, description);
	}
	
	public String getDueDate()
	{
		return (String)getPersistentValue(PROJECT_DUE_DATE);	
	}
	
	public void setCreatedDate(Date date)
	{
		setPersistentValue(PROJECT_CREATED_DATE, date);	
	}
	public void setDueDate(String ProjectDueDate)
	{
		setPersistentValue(PROJECT_DUE_DATE, ProjectDueDate);	
	}
	public RetrieveResult<CustomerReport> getCustomerReports()
	{
		return CustomerReport.findByProject(getDatabase(), this);
	}
	
	public RetrieveResult<ScrumReport> getScrumReports()
	{
		return ScrumReport.findByProject(getDatabase(), this);
	}
	
	public RetrieveResult<Task> getTasks()
	{
		return Task.findByProject(getDatabase(), this);
	}
	
	public RetrieveResult<Task> getUnassignedTasks()
	{
		return null;
	}
//
//
//
	public boolean addUsersById(List<Integer> userIds)
	{
		boolean ret = true;
		for(Integer id : userIds)
		{
			User user = User.findById(getDatabase(), id);
			if(user != null)
			{
				if(!addUser(user))
				{
					ret = false;
				}
			}
			else
			{
				ret = false;
			}
		}
		return ret;
	}
	//return true if the user is assigned to the task or the user is already assigned to the task
	public boolean addUser(User user)
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
				UserProjectMapping mapping = new UserProjectMapping(getDatabase());
				mapping.setProject(this);
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
			RetrieveResult<UserProjectMapping> rs = UserProjectMapping.findByProjectAndUser(getDatabase(), this, user);
			UserProjectMapping mapping = rs.next();
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
			if(UserProjectMapping.findByProjectAndUser(getDatabase(), this, user).next() != null)
			{
				ret = true;
			}
		}
		return ret;
	}

}
