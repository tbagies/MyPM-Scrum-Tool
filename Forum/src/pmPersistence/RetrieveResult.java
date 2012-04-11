package pmPersistence;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

//public class RetrieveResult {
public class RetrieveResult<T extends PersistentObject> {

	private Class<T> myClass;
	private ResultSet myResultSet;
	private Database myDb;
	private Class<?>[] myCtorArgs;
	RetrieveResult(Class<T> persistentClass, ResultSet rs, Database db)
	{
		myDb = db;
		myClass = persistentClass;
		myCtorArgs = new Class[1];
		myCtorArgs[0] = Database.class;
		myResultSet = rs;
	}
		
	public T next()
	{
		T ret = null;
		try
		{
			if(myResultSet.next())
			{
				try {
					ret = myClass.getConstructor(myCtorArgs).newInstance(myDb);
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
	
	private void retrieveRow(T obj)
	{
		Set<String> fields = obj.getTable().Fields;
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
