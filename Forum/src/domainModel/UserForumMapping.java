package domainModel;

import pmPersistence.Database;
import pmPersistence.PersistentObject;
import pmPersistence.RetrieveResult;

public final class UserForumMapping extends PersistentObject {
	//package private for table name
	static final String TABLE="forummembers";
	private static final String ID = "ID";
	//package private for these fields
	static final String USER_ID = "UserID";
	static final String FORUM_ID = "ForumID";
	public UserForumMapping(Database db) {
		super(db, TABLE, ID);
	}
	
	static RetrieveResult<UserForumMapping> findByForumAndUser(Database db, Forum forum, User user)
	{
		return retrievePersistentObjects(db, UserForumMapping.class, TABLE, USER_ID + "="+user.getUserId().toString() + " AND " + FORUM_ID + "=" + forum.getForumId().toString());
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
	
	public Forum getForum()
	{
		return Forum.findById(getDatabase(), (Integer)getPersistentValue(FORUM_ID));
	}
	
	public void setForum(Forum forum)
	{
		setPersistentValue(FORUM_ID, forum.getForumId());
	}

}
