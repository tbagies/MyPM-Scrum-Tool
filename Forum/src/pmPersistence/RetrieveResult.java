package pmPersistence;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

public class RetrieveResult {

	@SuppressWarnings("rawtypes")
	private Class myClass;
	private ResultSet myResultSet;
	private Database myDb;
	@SuppressWarnings("rawtypes")
	private Class[] myCtorArgs;
	RetrieveResult(@SuppressWarnings("rawtypes") Class persistentClass, ResultSet rs, Database db)
	{
		myDb = db;
		myClass = persistentClass;
		myCtorArgs = new Class[1];
		myCtorArgs[0] = Database.class;
		myResultSet = rs;
	}
		
	@SuppressWarnings("unchecked")
	public PersistentObject next()
	{
		PersistentObject ret = null;
		try
		{
			if(myResultSet.next())
			{
				try {
					ret = (PersistentObject)myClass.getConstructor(myCtorArgs).newInstance(myDb);
					//initialize the object...
					retrieveRow(ret);
					ret.isNew = false;
				}
				catch(InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException s)
				{
					
				}
			}
		}
		catch(SQLException s)
		{
		
		}
		return ret;
	}
	
	private void retrieveRow(PersistentObject obj)
	{
		Set<String> fields = obj.myTable.Fields;
		for(String fname : fields)
		{
			obj.getProperties().put(fname, getPropertyValue(fname));
		}
	}
	
	private Object getPropertyValue(String field)
	{
		Object ret = null;
		try
		{
			ret = myResultSet.getObject(field);
			if((ret != null) && (ret instanceof String))
			{
				ret = Database.unsanitize((String)ret);
			}
		}
		catch(SQLException s)
		{
		}
		return ret;
	}
	
}
