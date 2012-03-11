package pmPersistence;

import java.util.Set;

public class DBTable {

	public final String Name;
	public final Set<String> Fields;
	public final String KeyField;
	public final Database Db;
	
	DBTable(String name, Set<String> fields, String key, Database db)
	{
		Name = name;
		Fields = fields;
		KeyField = key;
		Db = db;
	}
}
