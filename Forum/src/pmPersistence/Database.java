package pmPersistence;

import java.sql.*;
import java.util.Map;
import java.util.Set;

public class Database {

	class FieldDefinition
	{
		public String name;
		public String datatype;
	};
	
	private Connection myConnection = null;
	public Database(String url, String driver, String name, String user, String pass)
	{
		myConnection = null;
		try {
			Class.forName(driver).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			myConnection = DriverManager.getConnection(url+name, user, pass);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void closeDatabase()
	{
		try {
			myConnection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected <T extends PersistentObject> boolean storePersistentObject(T obj) {
		boolean ret = true;
		try
		{
			Statement st = myConnection.createStatement();
			String query;
			if(obj.isNew())
			{
				query = "INSERT ";
			}
			else
			{
				query = "UPDATE ";
			}
			query += obj.getTableName() + " SET ";
			Map<String, Object> properties = obj.getProperties();
			Set<String> fields = properties.keySet();
			boolean firstValue = true;
			for(String fname : fields)
			{
				if(!firstValue)
				{
					query += ", ";
				}
				firstValue = false;
				query += fname + "=" + getValueString(properties.get(fname));
			}
			if(!obj.isNew())
			{
				query += " WHERE " + obj.getKeyField() + " = " + getValueString(properties.get(obj.getKeyField()));
			}
			if(obj.isNew())
			{
				st.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next())
				{
					int key = rs.getInt(1);
					String whereStmt = obj.getKeyField() + " = " + Integer.toString(key);
					
					
					@SuppressWarnings("unchecked")
					T obj2 = (T)retrievePersistentObjects(obj.getClass(), obj.getTableName(), whereStmt).next();
					
					//if the key was auto-generated, make sure it gets updated back to the object...
					obj.setPersistentValue(obj.getKeyField(), obj2.getPersistentValue(obj.getKeyField()));
					obj.clearNewFlag();
				}
			}
			else
			{
				st.executeUpdate(query);
			}
			
		}
		catch(SQLException x1)
		{
			System.out.println(x1.getMessage());
			ret = false;
		}
		return ret;
	}
	
	private String getValueString(Object obj)
	{
		String ret;
		if(obj == null)
		{
			ret = "DEFAULT";
		}
		else if(obj instanceof String)
		{
			ret = sanitize((String)obj);
		}
		else if(obj instanceof Date)
		{
			ret = "'"+obj.toString()+"'";
		}
		else
		{
			ret = obj.toString();
		}
		return ret;
	}
	
	protected Boolean deletePersistentObject(PersistentObject obj) {
		Boolean ret = false;
		try {
			  Statement st = myConnection.createStatement();
			  String query = "DELETE FROM " + obj.getTableName() + 
					  " WHERE " + obj.getKeyField() + " = " + 
					  getValueString(obj.getProperties().get(obj.getKeyField()));
			  int delete = st.executeUpdate(query);
			  if(delete == 1){
				  ret = true;
			  }
		  }
		  catch (SQLException s){
		  }
		return ret;
	}
	
	public static String sanitize(String rawData)
	{
		//Make string data safe for storage in the database
		//replace any \ with a \\
		rawData.replace("\\","\\\\");
		//replace any ' with a \'
		rawData.replace("\'","\\\'");
		//replace any " with a \"
		rawData.replace("\"", "\\\"");
		//encapsulate in ''
		return "'" + rawData + "'";
	}
	
	public static String unsanitize(String sanitizedData)
	{
		//Convert string back to original format...
		String ret = "";
		if(sanitizedData != null)
		{
			//ret = sanitizedData.substring(1, sanitizedData.length()-1);
			ret = sanitizedData;
			ret.replace("\\\"","\"");
			ret.replace("\\'","'");
			ret.replace("\\\\","\\");
		}
		return ret;
	}
	
	protected <T extends PersistentObject> T retrieveObjectByKey(Class<T> persistentClass, String tableName, String keyField, Object keyValue)
	{
		T ret = null;
		if(keyValue != null)
		{
			String whereClause = keyField + " = " + getValueString(keyValue);
			RetrieveResult<T> result = retrievePersistentObjects(persistentClass, tableName, whereClause);
			ret = result.next();
		}
		return ret;
	}
	
	protected <T extends PersistentObject> RetrieveResult<T> retrievePersistentObjects(Class<T> persistentClass, String tableName, String whereStmt) {
		RetrieveResult<T> result = null;
		try
		{
			Statement st = myConnection.createStatement();
			ResultSet rs;
			if(whereStmt == null)
			{
				rs = st.executeQuery("SELECT * FROM "+tableName);
			}
			else
			{
				rs = st.executeQuery("SELECT * FROM "+tableName+ " WHERE " + whereStmt);
			}
			result = new RetrieveResult<T>(persistentClass, rs, this);
		}
		catch (SQLException s){
			System.out.println(s.getMessage());
		}
		
		return result;
	}
	
	protected <T extends PersistentObject> RetrieveResult<T> retrievePersistentObjects(Class<T> persistentClass, String sqlStatement) {
		RetrieveResult<T> result = null;
		try
		{
			Statement st = myConnection.createStatement();
			ResultSet rs = st.executeQuery(sqlStatement);
			result = new RetrieveResult<T>(persistentClass, rs, this);
		}
		catch (SQLException s){
		}
		
		return result;
	}
}
