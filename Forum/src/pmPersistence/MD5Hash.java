package pmPersistence;

public class MD5Hash {
	private final String myValue;

	public MD5Hash(String value)
	{
		myValue = value;
	}
	
	public String toString()
	{
		return "MD5('" + sanitize(myValue) + "')";
	}
	
	private static String sanitize(String rawData)
	{
		//Make string data safe for storage in the database
		//replace any \ with a \\
		rawData.replace("\\","\\\\");
		//replace any ' with a \'
		rawData.replace("\'","\\\'");
		//replace any " with a \"
		rawData.replace("\"", "\\\"");
		//encapsulate in ''
		return rawData;
	}
}
