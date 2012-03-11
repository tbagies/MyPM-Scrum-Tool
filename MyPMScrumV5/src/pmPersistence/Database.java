package pmPersistence;

import java.sql.*;
import java.util.HashSet;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			myConnection = DriverManager.getConnection(url+name, user, pass);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void closeDatabase()
	{
		try {
			myConnection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public DBTable getTable(String name)
	{
		Set<String> fields = new HashSet<String>();
		String keyField="";
		try
		{
			DatabaseMetaData dbm = myConnection.getMetaData();
			ResultSet rs = dbm.getColumns(null, "%", name, "%");
			String colName;
			while(rs.next()){
				colName = rs.getString("COLUMN_NAME");
				fields.add(colName);
			}
		
			ResultSet rs2 = dbm.getPrimaryKeys(null, "%", name);
			if(rs2.next())
			{
				keyField = rs2.getString("COLUMN_NAME");
			}
			if(rs2.next())
			{
				System.out.println("Error: Multiple key fields");
			}
		}
		catch (SQLException s){
		}
		return new DBTable(name, fields, keyField, this);
	}

	public void storePersistentObject(PersistentObject obj) {
		try
		{
			Statement st = myConnection.createStatement();
			String query;
			if(obj.isNew)
			{
				query = "INSERT ";
			}
			else
			{
				query = "UPDATE ";
			}
			query += obj.getTable().Name + " SET ";
			Map<String, Object> properties = obj.getProperties();
			Set<String> fields = obj.getTable().Fields;
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
			if(!obj.isNew)
			{
				query += " WHERE " + obj.getTable().KeyField + " = " + getValueString(properties.get(obj.getTable().KeyField));
			}
			if(obj.isNew)
			{
				st.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next())
				{
					int key = rs.getInt(1);
					String whereStmt = obj.getTable().KeyField + " = " + Integer.toString(key);
					
					PersistentObject obj2 = retrievePersistentObjects(obj.getClass(), obj.getTable().Name, whereStmt).next();
					
					//if the key was auto-generated, make sure it gets updated back to the object...
					obj.setPersistentValue(obj.getTable().KeyField, obj2.getPersistentValue(obj.getTable().KeyField));
					obj.isNew = false;
				}
			}
			else
			{
				st.executeUpdate(query);
			}
			
		}
		catch(SQLException x1)
		{
			System.out.println("Error...!");
		}
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
		else
		{
			ret = obj.toString();
		}
		return ret;
	}
	
	public Boolean deletePersistentObject(PersistentObject obj) {
		Boolean ret = false;
		try {
			  Statement st = myConnection.createStatement();
			  String query = "DELETE FROM " + obj.getTable().Name + " WHERE " + obj.getTable().KeyField + " = " + getValueString(obj.getProperties().get(obj.getTable().KeyField));
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
		rawData.replace("'","\\'");
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
	
	public PersistentObject retrieveObjectByKey(@SuppressWarnings("rawtypes") Class persistentClass, String tableName, Object keyValue)
	{
		PersistentObject ret = null;
		if(keyValue != null)
		{
			DBTable table = getTable(tableName);
			String whereClause = table.KeyField + " = " + getValueString(keyValue);
			RetrieveResult result = retrievePersistentObjects(persistentClass, tableName, whereClause);
			ret = result.next();
		}
		return ret;
	}
	public RetrieveResult retrievePersistentObjects(@SuppressWarnings("rawtypes") Class persistentClass, String tableName, String whereStmt) {
		RetrieveResult result = null;
		try
		{
			Statement st = myConnection.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM "+tableName+ " WHERE " + whereStmt);
			result = new RetrieveResult(persistentClass, rs, this);
		}
		catch (SQLException s){
		}
		
		return result;
	}
	
	//--------------- adding by Taghreed ------------
	
	public RetrieveResult retrieveAllPersistentObjects(@SuppressWarnings("rawtypes") Class persistentClass, String tableName) {
		RetrieveResult result = null;
		try
		{
			Statement st = myConnection.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM "+tableName);
			result = new RetrieveResult(persistentClass, rs, this);
		}
		catch (SQLException s){
		}
		
		return result;
	}
}
