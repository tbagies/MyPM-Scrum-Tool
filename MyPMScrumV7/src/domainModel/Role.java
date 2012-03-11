package domainModel;

import pmPersistence.Database;
import pmPersistence.PersistentObject;

public class Role extends PersistentObject {
	public static final String TABLE = "role";
	private static final String ACCESS_LEVEL_ID = "AccessLevelID";
	private static final String DESCRIPTION = "RoleDesc";
	public static final Integer CUSTOMER = new Integer(1);
	public static final Integer STUDENT = new Integer(2);
	public static final Integer INSTRUCTOR = new Integer(3);
	public static final Integer ADMINISTRATOR = new Integer(4);
	public static Role getRole(Database db, Integer role)
	{
		return (Role)db.retrieveObjectByKey(Role.class, Role.TABLE, role);
	}
	public Role(Database db) {
		super(db.getTable(TABLE));
		
	}
	
	public int getAccessLevelId()
	{
		Integer i =(Integer)getPersistentValue(ACCESS_LEVEL_ID);
		if(i == null)
		{
			return 0;
		}
		return i.intValue();
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
