package pmPersistence;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;


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
					ret.clearNewFlag();
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
		ResultSetMetaData meta;
		try 
		{
			meta = myResultSet.getMetaData();
			int columns = meta.getColumnCount();
			for(int column = 1; column <= columns; ++column)
			{
				String fname = meta.getColumnName(column);
				obj.getProperties().put(fname, getPropertyValue(fname));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
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
