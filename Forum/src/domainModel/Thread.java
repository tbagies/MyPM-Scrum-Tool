package domainModel;

import java.sql.Date;

import pmPersistence.Database;
import pmPersistence.PersistentObject;
import pmPersistence.RetrieveResult;

public final class Thread  extends PersistentObject {
	private static final String TABLE = "thread";
	private static final String THREAD_ID = "ThreadID";
	private static final String THREAD_TITLE = "ThreadTitle";
	private static final String THREAD_TEXT = "ThreadText";
	private static final String THREAD_DATE = "ThreadDate";
	private static final String USER_ID = "UserID";
	private static final String FORUM_ID = "ForumID";
	
	public static RetrieveResult<Thread> getAll(Database db)
	{
		return retrievePersistentObjects(db, Thread.class, TABLE, null);
	}
	
	public static RetrieveResult<Thread> findByForum(Database db, Forum forum)
	{
		return retrievePersistentObjects(db, Thread.class, TABLE, FORUM_ID + " = " + forum.getForumId().toString());
	}
	
	public static RetrieveResult<Thread> findByUser(Database db, User user)
	{
		return retrievePersistentObjects(db, Thread.class, TABLE, USER_ID + " = " + user.getUserId().toString());
	}
	
	public static Thread findById(Database db, Integer id)
	{
		return retrieveObjectByKey(db, Thread.class, TABLE, THREAD_ID, id);
	}
	
	public Thread(Database db)
	{
		super(db, TABLE, THREAD_ID);
	}
	
	public Integer getThreadId()
	{
		return (Integer)getPersistentValue(THREAD_ID);
	}
	
	public String getTitle()
	{
		return (String)getPersistentValue(THREAD_TITLE);
	}
	
	public void setTitle(String title)
	{
		setPersistentValue(THREAD_TITLE, title);
	}
	
	public String getText()
	{
		return (String)getPersistentValue(THREAD_TEXT);
	}
	
	public void setText(String text)
	{
		setPersistentValue(THREAD_TEXT, text);
	}
	
	public Date getDate()
	{
		return (Date)getPersistentValue(THREAD_DATE);
	}
	
	public void setDate(Date date)
	{
		setPersistentValue(THREAD_DATE, date);
	}
	
	public User getUser()
	{
		return User.findById(getDatabase(), (Integer)getPersistentValue(USER_ID));
	}
	
	public void setUser(User user)
	{
		setPersistentValue(USER_ID, user.getUserId());
	}
	
	public Forum getForum()
	{
		return Forum.findById(getDatabase(), (Integer)getPersistentValue(FORUM_ID));
	}
	
	public void setForum(Forum forum)
	{
		setPersistentValue(FORUM_ID, forum.getForumId());
	}
	
	public RetrieveResult<Post> getPosts()
	{
		return Post.findByThread(getDatabase(), this);
	}
}
