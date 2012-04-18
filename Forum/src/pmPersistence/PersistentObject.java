package pmPersistence;

import java.util.HashMap;
import java.util.Map;

public class PersistentObject {
	private Map<String, Object> myProperties;
	private final String myTableName;
	private final String myKeyField;
	private final Database myDb;
	private boolean myNewFlag;
	protected PersistentObject(Database db, String tableName, String keyField)
	{
		myDb = db;
		myTableName = tableName;
		myKeyField = keyField;
		myNewFlag = true;
		myProperties = new HashMap<String, Object>();
	}
	Map<String, Object> getProperties()
	{
		return myProperties;
	}
	
	protected static <T extends PersistentObject> T retrieveObjectByKey(Database db, Class<T> persistentClass, String tableName, String keyField, Object key)
	{
		return db.retrieveObjectByKey(persistentClass, tableName, keyField, key);
	}
	
	protected static <T extends PersistentObject> RetrieveResult<T> retrievePersistentObjects(Database db, Class<T> persistentClass, String tableName, String whereStmt) 
	{
		return db.retrievePersistentObjects(persistentClass, tableName, whereStmt);
	}
	
	protected static <T extends PersistentObject> RetrieveResult<T> retrievePersistentObjects(Database db, Class<T> persistentClass, String sqlStmt) 
	{
		return db.retrievePersistentObjects(persistentClass, sqlStmt);
	}
	
	protected String getTableName()
	{
		return myTableName;
	}
	
	protected String getKeyField()
	{
		return myKeyField;
	}
	
		
	protected Database getDatabase()
	{
		return myDb;
	}
	
	public boolean persist()
	{
		return myDb.storePersistentObject(this);
	}
	
	public boolean isNew()
	{
		return myNewFlag;
	}
	
	void clearNewFlag()
	{
		myNewFlag = false;
	}
	
	public boolean delete()
	{
		return myDb.deletePersistentObject(this);
	}
	
	protected Object getPersistentValue(String key)
	{
		return myProperties.get(key);
	}
	
	protected void setPersistentValue(String key, Object obj)
	{
		myProperties.put(key, obj);
	}
}
