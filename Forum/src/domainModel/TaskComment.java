package domainModel;

import java.sql.Date;

import pmPersistence.Database;
import pmPersistence.PersistentObject;
import pmPersistence.RetrieveResult;

public final class TaskComment extends PersistentObject {
	private static final String TABLE = "commenttask";
	private static final String COMMENT_ID = "CommentsID";
	private static final String COMMENT_TITLE = "CommentTitle";
	private static final String COMMENT_DATE = "CommentDate";
	private static final String COMMENT_TEXT = "CommentText";
	private static final String USER_ID = "UserID";
	private static final String TASK_ID = "TaskID";

	public static TaskComment findById(Database db, Integer id)
	{
		return retrieveObjectByKey(db, TaskComment.class, TABLE, COMMENT_ID, id);
	}
	
	public static RetrieveResult<TaskComment> findByTask(Database db, Task task)
	{
		return retrievePersistentObjects(db, TaskComment.class, TABLE, TASK_ID + "="+task.getTaskId().toString());
	}
	
	public static RetrieveResult<TaskComment> findByUser(Database db, User user)
	{
		return retrievePersistentObjects(db, TaskComment.class, TABLE, USER_ID + "="+user.getUserId().toString());
	}
	
	public TaskComment(Database db) 
	{
		super(db, TABLE, COMMENT_ID);
	}
	
	public Integer getCommentId()
	{
		return (Integer)getPersistentValue(COMMENT_ID);
	}
	
	public void setTitle(String title)
	{
		setPersistentValue(COMMENT_TITLE, title);	
	}
	
	public String getTitle()
	{
		return (String)getPersistentValue(COMMENT_TITLE);
	}
	
	public void setDate(Date date)
	{
		setPersistentValue(COMMENT_DATE, date);	
	}
	
	public Date getDate()
	{
		return (Date)getPersistentValue(COMMENT_DATE);
	}
	
	public void setText(String text)
	{
		setPersistentValue(COMMENT_TEXT, text);	
	}
	
	public String getText()
	{
		return (String)getPersistentValue(COMMENT_TEXT);
	}
	
	//package private
	void setUser(User user)
	{
		setPersistentValue(USER_ID, user.getUserId());	
	}
	
	public User getUser()
	{
		return User.findById(getDatabase(), (Integer)getPersistentValue(USER_ID));
	}
	
	//package private
	void setTask(Task task)
	{
		setPersistentValue(TASK_ID, task.getTaskId());
	}
	
	public Task getTask()
	{
		return Task.findById(getDatabase(), (Integer)getPersistentValue(TASK_ID));
	}
}
