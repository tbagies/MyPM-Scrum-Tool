package domainModel;

import pmPersistence.Database;
import pmPersistence.PersistentObject;

public class UserProfile extends PersistentObject {
	static public final String TABLE = "userprofile";
	static private final String PROFILE_ID ="ProfileID";
	static private final String FIRST_NAME="FirstName";
	static private final String LAST_NAME="LastName";
	static private final String GENDER="Gender";
	static private final String DATE_OF_BIRTH="DateOfBirth";
	static private final String USER_ID="UserID";
	static private final String ALT_EMAIL="AlternativeEmail";
	static private final String TELEPHONE="Telphone";
	
	public UserProfile(Database db) {
		super(db.getTable(TABLE));
	}
	
	public int getProfileId(String userID)
	{
		Integer i =(Integer)getPersistentValue(PROFILE_ID);
		if(i == null)
		{
			i=0;
		}
		return i.intValue();
	}
	
	public int getUserId()
	{
		Integer i =(Integer)getPersistentValue(USER_ID);
		if(i == null)
		{
			return 0;
		}
		return i.intValue();
	}
	
	public void setUserId(int id)
	{
		setPersistentValue(USER_ID, new Integer(id));
	}

	public void setFirstName(String firstName)
	{
		setPersistentValue(FIRST_NAME, firstName);
	}
	
	public String getFirstName()
	{
		return (String)getPersistentValue(FIRST_NAME);
	}
	
	public void setLastName(String lastName)
	{
		setPersistentValue(LAST_NAME, lastName);
	}
	
	public String getLastName()
	{
		return (String)getPersistentValue(LAST_NAME);
	}
	
	public void setGender(String gender)
	{
		setPersistentValue(GENDER, gender);
	}
	
	public String getGender()
	{
		return (String)getPersistentValue(GENDER);
	}
	
	public void setDOB(int year)
	{
		setPersistentValue(DATE_OF_BIRTH, Integer.toString(year));
	}
	
	
	public int getDob()
	{
		return Integer.valueOf((String)getPersistentValue(DATE_OF_BIRTH)).intValue();
	}
	
	public void setAltEmail(String altEmail)
	{
		setPersistentValue(ALT_EMAIL, altEmail);
	}
	
	public String getAltEmail()
	{
		return (String)getPersistentValue(ALT_EMAIL);
	}
	
	public void setTelephone(String telephoneNumber)
	{
		setPersistentValue(TELEPHONE, telephoneNumber);
	}
	
	public String getTelephone()
	{
		return (String)getPersistentValue(TELEPHONE);
	}
}
