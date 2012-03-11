package pmPersistence;

import java.util.HashMap;
import java.util.Map;

public class PersistentObject {
	private Map<String, Object> myProperties;
	protected final DBTable myTable;
	protected boolean isNew;
	protected PersistentObject(DBTable table)
	{
		isNew = true;
		myTable = table;
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
	DBTable getTable()
	{
		return myTable;
	};
	
	protected Object getPersistentValue(String key)
	{
		Object ret = null;
		if(myProperties.containsKey(key))
		{
			ret = myProperties.get(key);;
		}
		return ret;
	}
	
	protected Boolean setPersistentValue(String key, Object obj)
	{
		Boolean ret = false;
		if(myProperties.containsKey(key))
		{
			ret = true;
			myProperties.put(key, obj);
		}
		return ret;
	}
}
