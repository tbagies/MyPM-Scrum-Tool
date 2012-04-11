package domainModel;

import java.sql.Date;
import java.util.List;

import pmPersistence.Database;
import pmPersistence.PersistentObject;
import pmPersistence.RetrieveResult;

public class Forum extends PersistentObject {
	private static final String TABLE = "forum";
	private static final String FORUM_ID = "ForumID";
	private static final String FORUM_TITLE = "ForumTitle";
	private static final String CREATED_DATE = "CreatedDate";
	
	public static RetrieveResult<Forum> getAll(Database db)
	{
		return retrievePersistentObjects(db, Forum.class, TABLE, null);
	}
	
	public static Forum findById(Database db, Integer id)
	{
		return retrieveObjectByKey(db, Forum.class, TABLE, id);
	}
	
	public static RetrieveResult<Forum> findByUser(Database db, User user)
	{
		return retrievePersistentObjects(db, Forum.class, 
				"SELECT " + 
				TABLE + ".* FROM " + 
				TABLE + " INNER JOIN "+ UserForumMapping.TABLE + " ON " +
				TABLE + "." + FORUM_ID + "=" + UserForumMapping.TABLE + "." + UserForumMapping.FORUM_ID + " WHERE " + 
				UserForumMapping.TABLE + "." + UserForumMapping.USER_ID + "=" + user.getUserId().toString());
	}
	
	public Forum(Database db)
	{
		super(db, TABLE);
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
	
	public RetrieveResult<User> getUsers()
	{
		return User.findByForum(getDatabase(), this);
	}
	
	//return true if all users are assigned to the the forum, or false if any user cannot be assigned
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
				UserForumMapping mapping = new UserForumMapping(getDatabase());
				mapping.setForum(this);
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
			RetrieveResult<UserForumMapping> rs = UserForumMapping.findByForumAndUser(getDatabase(), this, user);
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

	public boolean isUserAssigned(User user)
	{
		boolean ret = false;
		if(!isNew)
		{
			if(UserForumMapping.findByForumAndUser(getDatabase(), this, user).next() != null)
			{
				ret = true;
			}
		}
		return ret;
	}

}
