package domainModel;

import java.sql.Date;
import pmPersistence.Database;
import pmPersistence.PersistentObject;
import pmPersistence.RetrieveResult;


public final class ScrumReport extends PersistentObject {
	private static final String TABLE = "scrumreport";
	private static final String REPORT_ID = "ReportID";
	private static final String REPORT_DATE = "ReportDate";
	private static final String REPORT_TITLE = "ReportTitle";
	private static final String ACCOMPLISHMENTS = "accomplishment";
	private static final String PLANNED = "planned";
	private static final String IMPEDIMENTS = "impedment";
	private static final String USER_ID = "UserID";
	private static final String NEXT_REPORT = "NextReport";
	private static final String PROJECT_ID = "ProjectID";
	
	public static RetrieveResult<ScrumReport> getAll(Database db)
	{
		return retrievePersistentObjects(db, ScrumReport.class, TABLE, null);
	}
	
	public static ScrumReport findById(Database db, Integer id)
	{
		return retrieveObjectByKey(db, ScrumReport.class, TABLE, REPORT_ID, id);
	}
	
	public static RetrieveResult<ScrumReport> findByUser(Database db, User user)
	{
		return retrievePersistentObjects(db, ScrumReport.class, TABLE, USER_ID + " = " + user.getUserId().toString());
	}
	
	public static RetrieveResult<ScrumReport> findByProject(Database db, Project project)
	{
		return retrievePersistentObjects(db, ScrumReport.class, TABLE, PROJECT_ID + " = " + project.getProjectId().toString());
	}
	
	public ScrumReport(Database db)
	{
		super(db, TABLE, REPORT_ID);
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
		Integer uid = (Integer)getPersistentValue(USER_ID);
		return User.findById(getDatabase(), uid);
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
		Integer pid = (Integer)getPersistentValue(PROJECT_ID);
		return Project.findById(getDatabase(), pid);
	}
	
	public void setProject(Project p)
	{
		setPersistentValue(PROJECT_ID, p.getProjectId());
	}

}
