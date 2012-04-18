package domainModel;

import pmPersistence.Database;
import pmPersistence.PersistentObject;
import pmPersistence.RetrieveResult;

public final class Role extends PersistentObject {
	private static final String TABLE = "role";
	private static final String ACCESS_LEVEL_ID = "AccessLevelID";
	private static final String DESCRIPTION = "RoleDesc";
	public static final Integer CUSTOMER = new Integer(1);
	public static final Integer STUDENT = new Integer(2);
	public static final Integer INSTRUCTOR = new Integer(3);
	public static final Integer ADMINISTRATOR = new Integer(4);
	
	public static RetrieveResult<Role> getAll(Database db)
	{
		return retrievePersistentObjects(db, Role.class, TABLE, null);
	}
	
	public static Role findById(Database db, Integer role)
	{
		return retrieveObjectByKey(db, Role.class, Role.TABLE, ACCESS_LEVEL_ID, role);
	}
	public static Role findByDescription(Database db, String description)
	{
		return retrievePersistentObjects(db, Role.class, TABLE, DESCRIPTION + "="+Database.sanitize(description)).next();
	}
	
	public Role(Database db) {
		super(db, TABLE, ACCESS_LEVEL_ID);
		
	}
		
	public Integer getAccessLevelId()
	{
		return (Integer)getPersistentValue(ACCESS_LEVEL_ID);
	}
	
	public String getDescription()
	{
		return (String)getPersistentValue(DESCRIPTION);
	}
	
	public void setDescription(String description)
	{
		setPersistentValue(DESCRIPTION, description);
	}
}
