package domainModel;

import java.sql.Date;
import pmPersistence.Database;
import pmPersistence.PersistentObject;
import pmPersistence.RetrieveResult;

public class CustomerReport extends PersistentObject {
	private static final String TABLE = "customerreport";
	private static final String REPORT_ID = "ReportID";
	private static final String REPORT_DATE = "ReportDate";
	private static final String REPORT_TITLE = "ReportTitle";
	private static final String ACCOMPLISHMENTS = "accomplishment";
	private static final String PLANNED = "planned";
	private static final String IMPEDIMENTS = "impedment";
	private static final String USER_ID = "UserID";
	private static final String NEXT_REPORT = "NextReport";
	private static final String PROJECT_ID = "ProjectID";
	
	public static RetrieveResult<CustomerReport> getAll(Database db)
	{
		return retrievePersistentObjects(db, CustomerReport.class, TABLE, null);
	}
	
	public static CustomerReport findById(Database db, Integer id)
	{
		return retrieveObjectByKey(db, CustomerReport.class, TABLE, id);
	}
	
	public static RetrieveResult<CustomerReport> findByUser(Database db, User user)
	{
		return retrievePersistentObjects(db, CustomerReport.class, TABLE, USER_ID + " = " + user.getUserId().toString());
	}
	
	public static RetrieveResult<CustomerReport> findByProject(Database db, Project project)
	{
		return retrievePersistentObjects(db, CustomerReport.class, TABLE, PROJECT_ID + " = " + project.getProjectId().toString());
	}
	
	public CustomerReport(Database db)
	{
		super(db, TABLE);
	}
	
	public Integer getReportId()
	{
		return (Integer)getPersistentValue(REPORT_ID);
	}
	
	public Date getReportDate()
	{
		return (Date)getPersistentValue(REPORT_DATE);
	}
	
	public void setReportDate(Date d)
	{
		setPersistentValue(REPORT_DATE, d);
	}
	
	public String getReportTitle()
	{
		return (String)getPersistentValue(REPORT_TITLE);
	}
	
	public void setReportTitle(String title)
	{
		setPersistentValue(REPORT_TITLE, title);
	}
	
	public String getAccomplishments()
	{
		return (String)getPersistentValue(ACCOMPLISHMENTS);
	}
	
	public void setAccomplishments(String a)
	{
		setPersistentValue(ACCOMPLISHMENTS, a);
	}
	
	public String getPlanned()
	{
		return (String)getPersistentValue(PLANNED);
	}
	
	public void setPlanned(String p)
	{
		setPersistentValue(PLANNED, p);
	}
	
	public String getImpediments()
	{
		return (String)getPersistentValue(IMPEDIMENTS);
	}
	
	public void setImpediments(String i)
	{
		setPersistentValue(IMPEDIMENTS, i);
	}
	
	public User getUser()
	{
		return User.findById(getDatabase(), (Integer)getPersistentValue(USER_ID));
	}
	
	public void setUser(User user)
	{
		setPersistentValue(USER_ID, user.getUserId());
	}
	
	public Date getNextReportDate()
	{
		return (Date)getPersistentValue(NEXT_REPORT);
	}
	
	public void setNextReportDate(Date d)
	{
		setPersistentValue(NEXT_REPORT, d);
	}
	
	public Project getProject()
	{
		return Project.findById(getDatabase(), (Integer)getPersistentValue(PROJECT_ID));
	}
	
	public void setProject(Project p)
	{
		setPersistentValue(PROJECT_ID, p.getProjectId());
	}
}
