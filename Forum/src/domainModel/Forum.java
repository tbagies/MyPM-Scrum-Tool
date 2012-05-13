package domainModel;

import java.sql.Date;

import pmPersistence.Database;
import pmPersistence.PersistentObject;
import pmPersistence.RetrieveResult;

public final class Forum extends PersistentObject {
	private static final String TABLE = "forum";
	private static final String FORUM_ID = "ForumID";
	private static final String FORUM_TITLE = "ForumTitle";
	private static final String CREATED_DATE = "CreatedDate";
	private static final String PROJECT_ID = "ProjectID";
	
	public static RetrieveResult<Forum> getAll(Database db)
	{
		return retrievePersistentObjects(db, Forum.class, TABLE, null);
	}
	
	public static Forum findById(Database db, Integer id)
	{
		return retrieveObjectByKey(db, Forum.class, TABLE, FORUM_ID, id);
	}
	
	public static RetrieveResult<Forum> findByProject(Database db, Project project)
	{
		return retrievePersistentObjects(db, Forum.class, TABLE, PROJECT_ID +"="+project.getProjectId());
	}
	
	public Forum(Database db)
	{
		super(db, TABLE, FORUM_ID);
	}
	
	public Integer getForumId()
	{
		return (Integer)getPersistentValue(FORUM_ID);
	}
	
	public String getTitle()
	{
		return (String)getPersistentValue(FORUM_TITLE);
	}
	
	public void setTitle(String title)
	{
		setPersistentValue(FORUM_TITLE, title);
	}
	
	public Date getCreatedDate()
	{
		return (Date)getPersistentValue(CREATED_DATE);
	}
	
	public void setCreatedDate(Date date)
	{
		setPersistentValue(CREATED_DATE, date);	
	}

	public RetrieveResult<Thread> getThreads()
	{
		return Thread.findByForum(getDatabase(), this);
	}
	
	public void setProject(Project project)
	{
		setPersistentValue(PROJECT_ID, project.getProjectId());
	}
	
	public Project getProject()
	{
		return Project.findById(getDatabase(), (Integer)getPersistentValue(PROJECT_ID));
	}

}
