package domainModel;

import java.sql.Date;

import pmPersistence.Database;
import pmPersistence.PersistentObject;
import pmPersistence.RetrieveResult;

public final class UserProfile extends PersistentObject {
	private static final String TABLE = "userprofile";
	static private final String PROFILE_ID ="ProfileID";
	static private final String FIRST_NAME="FirstName";
	static private final String LAST_NAME="LastName";
	static private final String GENDER="Gender";
	static private final String DATE_OF_BIRTH="DateOfBirth";
	static private final String USER_ID="UserID";
	static private final String ALT_EMAIL="AlternativeEmail";
	static private final String TELEPHONE="Telphone";
	static private final String LAST_UPDATED_DATE = "LastUpdatedDate";
	
	public static RetrieveResult<UserProfile> getAll(Database db)
	{
		return retrievePersistentObjects(db, UserProfile.class, TABLE, null);
	}
	
	public static UserProfile findById(Database db, Integer id)
	{
		return (UserProfile)retrieveObjectByKey(db, UserProfile.class, TABLE, PROFILE_ID, id);
	}
	
	public UserProfile(Database db) {
		super(db, TABLE, PROFILE_ID);
	}
	
	public Integer getProfileId()
	{
		return (Integer)getPersistentValue(PROFILE_ID);
	}
	
	public Integer getUserId()
	{
		return (Integer)getPersistentValue(USER_ID);
	}
	
	public void setUserId(Integer id)
	{
		setPersistentValue(USER_ID, id);
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
	
	public void setDOB(String DOB)
	{
		setPersistentValue(DATE_OF_BIRTH, DOB);
	}
	
	
	public String getDob()
	{
		return (String)getPersistentValue(DATE_OF_BIRTH);
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
	
	public void setLastUpdatedDate(Date date)
	{
		setPersistentValue(LAST_UPDATED_DATE, date);
	}
	
	public Date getLastUpdatedDate()
	{
		return (Date)getPersistentValue(LAST_UPDATED_DATE);
	}
}
