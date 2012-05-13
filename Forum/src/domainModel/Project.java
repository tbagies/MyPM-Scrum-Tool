package domainModel;

import java.sql.Date;


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
	private static final String LAST_UPDATED_DATE = "LastUpdatedDate";
	
	public static RetrieveResult<Project> getAll(Database db)
	{
		return retrievePersistentObjects(db, Project.class, TABLE, null);
	}
	
	public static Project findById(Database db, Integer id)
	{
		return retrieveObjectByKey(db, Project.class, TABLE, PROJECT_ID, id);
	}
	
	public Project(Database db)
	{
		super(db, TABLE, PROJECT_ID);
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
	
	public RetrieveResult<Forum> getForums()
	{
		return Forum.findByProject(getDatabase(), this);
	}
	
	public void setLastUpdatedDate(Date date)
	{
		setPersistentValue(LAST_UPDATED_DATE, date);		
	}
	
	public Date getLastUpdatedDate(Date date)
	{
		return (Date)getPersistentValue(LAST_UPDATED_DATE);		
	}

}
