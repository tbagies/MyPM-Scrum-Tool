package domainModel;

import java.sql.Date;

import pmPersistence.Database;
import pmPersistence.PersistentObject;
import pmPersistence.RetrieveResult;

public class Post extends PersistentObject {
	final static String TABLE = "posts";
	
	private final static String POST_ID = "PostID";
	private final static String THREAD_ID = "ThreadID";
	private final static String USER_ID = "UserID";
	private final static String POST_TITLE = "PostTitle";
	private final static String POST_TEXT = "PostText";
	private final static String POST_DATE = "PostDate";
	
	public static RetrieveResult<Post> getAll(Database db)
	{
		return retrievePersistentObjects(db, Post.class, TABLE, null);
	}
	
	public static RetrieveResult<Post> findByUser(Database db, User user)
	{
		return retrievePersistentObjects(db, Post.class, TABLE, USER_ID + " = " + user.getUserId().toString());
	}
	
	public static RetrieveResult<Post> findByThread(Database db, Thread thread)
	{
		return retrievePersistentObjects(db, Post.class, TABLE, THREAD_ID + " = " + thread.getThreadId().toString());
	}
	
	public static Post findById(Database db, Integer id)
	{
		return retrieveObjectByKey(db, Post.class, TABLE, id);
	}
	
	public Post(Database db)
	{
		super(db, TABLE);
	}
	
	public Integer getPostId()
	{
		return (Integer)getPersistentValue(POST_ID);
	}
	
	public Thread getThread()
	{
		return Thread.findById(getDatabase(), (Integer)getPersistentValue(THREAD_ID));
	}
	
	public void setThread(Thread thread)
	{
		setPersistentValue(THREAD_ID, thread.getThreadId());
	}
	
	public User getUser()
	{
		return User.findById(getDatabase(), (Integer)getPersistentValue(USER_ID));
	}
	
	public void setUser(User user)
	{
		setPersistentValue(USER_ID, user.getUserId());
	}
	
	public String getTitle()
	{
		return (String)getPersistentValue(POST_TITLE);
	}
	
	public void setTitle(String title)
	{
		setPersistentValue(POST_TITLE, title);
	}
	
	public String getText()
	{
		return (String)getPersistentValue(POST_TEXT);
	}
	
	public void setText(String text)
	{
		setPersistentValue(POST_TEXT, text);
	}
	
	public Date getDate()
	{
		return (Date)getPersistentValue(POST_DATE);
	}
	
	public void setDate(Date date)
	{
		setPersistentValue(POST_DATE, date);
	}
}
