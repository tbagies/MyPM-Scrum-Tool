package pmPersistence;

import java.util.HashMap;
import java.util.Map;

public class PersistentObject {
	private Map<String, Object> myProperties;
	private final DBTable myTable;
	private final Database myDb;
	protected boolean isNew;
	protected PersistentObject(Database db, String tableName)
	{
		myDb = db;
		myTable = db.getTable(tableName);
		isNew = true;
		myProperties = new HashMap<String, Object>();
		for(String s : myTable.Fields)
		{
			myProperties.put(s, null);
		}
	}
	Map<String, Object> getProperties()
	{
		return myProperties;
	}
	
	protected static <T extends PersistentObject> T retrieveObjectByKey(Database db, Class<T> persistentClass, String tableName, Object key)
	{
		return db.retrieveObjectByKey(persistentClass, tableName, key);
	}
	
	protected static <T extends PersistentObject> RetrieveResult<T> retrievePersistentObjects(Database db, Class<T> persistentClass, String tableName, String whereStmt) 
	{
		return db.retrievePersistentObjects(persistentClass, tableName, whereStmt);
	}
	
	protected static <T extends PersistentObject> RetrieveResult<T> retrievePersistentObjects(Database db, Class<T> persistentClass, String sqlStmt) 
	{
		return db.retrievePersistentObjects(persistentClass, sqlStmt);
	}
	
	protected DBTable getTable()
	{
		return myTable;
	};
	
	protected Database getDatabase()
	{
		return myDb;
	}
	
	public boolean persist()
	{
		return myDb.storePersistentObject(this);
	}
	
	public boolean delete()
	{
		return myDb.deletePersistentObject(this);
	}
	
	protected Object getPersistentValue(String key)
	{
		Object ret = null;
		if(myProperties.containsKey(key))
		{
			ret = myProperties.get(key);
		}
		return ret;
	}
	
	protected boolean setPersistentValue(String key, Object obj)
	{
		boolean ret = false;
		if(myProperties.containsKey(key))
		{
			ret = true;
			myProperties.put(key, obj);
		}
		return ret;
	}
}
