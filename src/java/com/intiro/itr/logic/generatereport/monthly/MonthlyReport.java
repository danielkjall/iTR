package com.intiro.itr.logic.generatereport.monthly;

import com.intiro.itr.db.DBQueriesAdmin;
import com.intiro.itr.db.InvocationHandlerSetting;
import java.util.ArrayList;
import com.intiro.itr.logic.activity.Activity;
import com.intiro.itr.logic.project.Project;
import com.intiro.itr.util.NumberFormatter;
import com.intiro.itr.util.StringRecordset;
import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.itr.util.xml.DynamicXMLCarrier;
import com.intiro.itr.util.xml.XMLBuilder;
import com.intiro.itr.util.xml.XMLBuilderException;
import com.intiro.itr.util.log.IntiroLog;

public class MonthlyReport extends DynamicXMLCarrier {

  static final String XML_AVERAGE_RATE_END = "</averagerate>";
  // Summarize row
  static final String XML_AVERAGE_RATE_START = "<averagerate>";
  static final String XML_MONTH_END = "</month>";
  static final String XML_MONTH_START = "<month>";
  static final String XML_PRES_CRIT_END = "</prescrit>";
  // Presentation And Search Criterias
  static final String XML_PRES_CRIT_START = "<prescrit>";
  static final String XML_PROJECTCODE_END = "</projectcode>";
  static final String XML_PROJECTCODE_START = "<projectcode>";
  static final String XML_PROJECT_END = "</project>";
  static final String XML_PROJECT_START = "<project>";
  static final String XML_ROW_END = "</row>";
  static final String XML_ROW_FIRSTNAME_END = "</firstname>";
  static final String XML_ROW_FIRSTNAME_START = "<firstname>";
  static final String XML_ROW_INDEX_END = "</i>";
  static final String XML_ROW_INDEX_START = "<i>";
  static final String XML_ROW_LASTNAME_END = "</lastname>";
  static final String XML_ROW_LASTNAME_START = "<lastname>";
  static final String XML_ROW_LOGINID_END = "</loginid>";
  static final String XML_ROW_LOGINID_START = "<loginid>";
  static final String XML_ROW_PROJECTCODEDESC_END = "</projectcodedesc>";
  static final String XML_ROW_PROJECTCODEDESC_START = "<projectcodedesc>";
  static final String XML_ROW_PROJECTCODEID_END = "</projectcodeid>";
  static final String XML_ROW_PROJECTCODEID_START = "<projectcodeid>";
  static final String XML_ROW_PROJECTID_END = "</projectid>";
  static final String XML_ROW_PROJECTID_START = "<projectid>";
  static final String XML_ROW_PROJECTNAME_END = "</projectname>";
  static final String XML_ROW_PROJECTNAME_START = "<projectname>";
  static final String XML_ROW_RATE_END = "</rate>";
  static final String XML_ROW_RATE_START = "<rate>";
  static final String XML_ROW_REVENUE_END = "</revenue>";
  static final String XML_ROW_REVENUE_START = "<revenue>";
  // Row Data
  static final String XML_ROW_START = "<row>";
  static final String XML_ROW_TIMETYPEDESC_END = "</timetypedesc>";
  static final String XML_ROW_TIMETYPEDESC_START = "<timetypedesc>";
  static final String XML_ROW_USERID_END = "</userid>";
  static final String XML_ROW_USERID_START = "<userid>";
  static final String XML_ROW_WORKEDH_END = "</workedh>";
  static final String XML_ROW_WORKEDH_START = "<workedh>";
  static final String XML_SEARCH_CRIT_END = "</searchcrit>";
  static final String XML_SEARCH_CRIT_START = "<searchcrit>";
  static final String XML_SUM_REVENUE_END = "</sumrevenue>";
  static final String XML_SUM_REVENUE_START = "<sumrevenue>";
  static final String XML_SUM_WORKEDHOURS_END = "</sumworkedh>";
  static final String XML_SUM_WORKEDHOURS_START = "<sumworkedh>";
  static final String XML_USER_END = "</user>";
  static final String XML_USER_START = "<user>";
  static final String XML_YEAR_END = "</year>";
  static final String XML_YEAR_START = "<year>";
  private double averageRate = 0;
  private String fromDate = null;
  private String month = "All months";
  private String project = "All projects";
  private String projectCode = "All project codes";
  private String projectCodeId = null;
  private String projectId = null;
  private ArrayList<ReportRow> rows = new ArrayList<ReportRow>();
  private double sumRevenue = 0;
  private double sumWorkedHours = 0;
  private String toDate = null;
  private String user = "All users";
  private String userId = null;
  private String year = "";

  /**
   * Constructor I for MonthlyReport.
   *
   * @param profile the UserProfile for the current user.
   * @param userId The User to make a report for. If null all users.
   * @param projectId The Project to make a report for. If null all Projects.
   * @param projectCodeId The ProjectCode to make a report for. If null all ProjectsCodes.
   * @param fromDate The first day of the month, of which to create the report.
   * @param toDate The first day of the following month, of which to create the report.
   * @exception XMLBuilderException if something goes wrong.
   */
  public MonthlyReport(UserProfile profile, String userId, String projectId, String projectCodeId, String fromDate, String toDate) throws XMLBuilderException {
    super(profile);
    this.userId = userId;
    this.projectId = projectId;
    this.projectCodeId = projectCodeId;
    this.fromDate = fromDate;
    this.toDate = toDate;

    if (!userId.equals("")) {
      user = loadPresentationUser(userId);
    }
    if (!projectId.equals("")) {
      project = loadPresentationProject(projectId);
    }
    if (!projectCodeId.equals("")) {
      projectCode = loadPresentationProjectCode(projectCodeId);
    }
    if (fromDate.length() > 7) {
      this.year = fromDate.substring(0, 4);
    }
  }

  /**
   * Loads the general report.
   *
   * @throws com.intiro.itr.util.xml.XMLBuilderException
   */
  public void load() throws XMLBuilderException {
    try {
      //String cacheKey = getClass().getName() + ".getMonthlyReport_" + userId + "_" + projectId + "_" + projectCodeId + "_" + fromDate + "_" + toDate;
      String statisticKey = getClass().getName() + ".load";
      //int cacheTime = 3600 * 10;
      InvocationHandlerSetting s = InvocationHandlerSetting.create(statisticKey);
      StringRecordset rs = DBQueriesAdmin.getProxy(s).getMonthlyReport(userId, projectId, projectCodeId, fromDate, toDate);
      int index = 0;

      while (!rs.getEOF()) {
        String projectId = rs.getField("ProjectId");
        String projectName = rs.getField("ProjectName");
        String userId = rs.getField("UserId");
        String firstName = rs.getField("FirstName");
        String lastName = rs.getField("LastName");
        String loginId = rs.getField("LoginId");
        String projectCodeId = rs.getField("ProjectCodeId");
        String projectCodeDesc = rs.getField("ProjectCodeDescription");
        String timeTypeDesc = rs.getField("TimeTypeDescription");
        double rate = rs.getDouble("Rate");
        double sumWorkedHours = rs.getDouble("SumWorkedHours");
        double revenue = rs.getDouble("Revenue");
        ReportRow oneRow = new ReportRow(projectId, projectName, userId, firstName, lastName, loginId, projectCodeId, projectCodeDesc, timeTypeDesc, rate, sumWorkedHours, revenue,
                index++);

        // Add ReportRow to rows
        rows.add(oneRow);
        rs.moveNext();
      }

      rs.close();

      // Calculate summarys
      calculateSummary();
    } catch (Exception e) {
      IntiroLog.criticalError(getClass(), getClass().getName() + ".load(): ERROR FROM DATABASE, exception = " + e);
      throw new XMLBuilderException(e.getMessage());
    }
  }

  /**
   * Return the presentation data for the given project.
   *
   * @param projectId
   * @return
   * @throws com.intiro.itr.util.xml.XMLBuilderException
   */
  public String loadPresentationProject(String projectId) throws XMLBuilderException {
    Project proj = new Project(userProfile);
    proj.load(projectId);
    return proj.getProjectName();
  }

  /**
   * Return the presentation data for the given project.
   *
   * @param projectCodeId
   * @return
   * @throws com.intiro.itr.util.xml.XMLBuilderException
   */
  public String loadPresentationProjectCode(String projectCodeId) throws XMLBuilderException {
    Activity act = new Activity();
    act.load(projectCodeId);
    return act.getCode() + ", " + act.getDescription();
  }

  /**
   * Return the presentation data for the given user.
   *
   * @param userId
   * @return
   * @throws com.intiro.itr.util.xml.XMLBuilderException
   */
  public String loadPresentationUser(String userId) throws XMLBuilderException {
    UserProfile up = new UserProfile();
    up.load(userId);
    return up.getFirstName() + " " + up.getLastName() + " (" + up.getLoginId() + ")";
  }

  /**
   * Make xml of GeneralReport.
   *
   * @throws java.lang.Exception
   */
  @Override
  public void toXML(StringBuffer xmlDoc) throws Exception {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".toXML(): Entering");
    }

    XMLBuilder builder = new XMLBuilder();

    // Get start of document
    builder.getStartOfDocument(xmlDoc);

    // Presentation Criterias
    xmlDoc.append(XML_PRES_CRIT_START);
    xmlDoc.append(XML_USER_START).append(user).append(XML_USER_END);
    xmlDoc.append(XML_PROJECT_START).append(project).append(XML_PROJECT_END);
    xmlDoc.append(XML_PROJECTCODE_START).append(projectCode).append(XML_PROJECTCODE_END);
    xmlDoc.append(XML_YEAR_START).append(year).append(XML_YEAR_END);
    xmlDoc.append(XML_MONTH_START).append(month).append(XML_MONTH_END);
    xmlDoc.append(XML_PRES_CRIT_END);

    // Search Criterias
    xmlDoc.append(XML_SEARCH_CRIT_START);
    xmlDoc.append(XML_USER_START).append(userId).append(XML_USER_END);
    xmlDoc.append(XML_PROJECT_START).append(projectId).append(XML_PROJECT_END);
    xmlDoc.append(XML_PROJECTCODE_START).append(projectCodeId).append(XML_PROJECTCODE_END);
    xmlDoc.append(XML_YEAR_START).append(year).append(XML_YEAR_END);
    xmlDoc.append(XML_MONTH_START).append(month).append(XML_MONTH_END);
    xmlDoc.append(XML_SEARCH_CRIT_END);

    // Result Rows
    ReportRow oneRow;

    for (int i = 0; i < rows.size(); i++) {
      oneRow = rows.get(i);

      if (oneRow != null) {
        oneRow.toXML(xmlDoc);
      }
    }

    // Summary data
    xmlDoc.append(XML_AVERAGE_RATE_START).append(NumberFormatter.format(averageRate, 2, true)).append(XML_AVERAGE_RATE_END);
    xmlDoc.append(XML_SUM_WORKEDHOURS_START).append(NumberFormatter.format(sumWorkedHours, 2, true)).append(XML_SUM_WORKEDHOURS_END);
    xmlDoc.append(XML_SUM_REVENUE_START).append(NumberFormatter.format(sumRevenue, 2, true)).append(XML_SUM_REVENUE_END);

    /* Get end of document */
    builder.getEndOfDocument(xmlDoc);

    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".toXML(): xmlDoc = " + xmlDoc.toString());
    }
  }

  private void calculateSummary() {
    ReportRow oneRow;

    for (int i = 0; i < rows.size(); i++) {
      oneRow = rows.get(i);

      if (oneRow != null) {
        this.sumWorkedHours += oneRow.getRowSumWorkedHours();
        this.sumRevenue += oneRow.getRowRevenue();
        this.averageRate += oneRow.getRowRate();
      }
    }
    if (rows.size() > 0) {
      this.averageRate = this.averageRate / rows.size();
    }
  }

  public double getAverageRate() {
    return averageRate;
  }

  public String getFromDate() {
    return fromDate;
  }

  public String getMonth() {
    return month;
  }

  public String getProject() {
    return project;
  }

  public String getProjectCode() {
    return projectCode;
  }

  public String getProjectCodeId() {
    return projectCodeId;
  }

  public String getProjectId() {
    return projectId;
  }

  public ArrayList<ReportRow> getRows() {
    return rows;
  }

  public double getSumRevenue() {
    return sumRevenue;
  }

  public double getSumWorkedHours() {
    return sumWorkedHours;
  }

  public String getToDate() {
    return toDate;
  }

  public String getUser() {
    return user;
  }

  public String getUserId() {
    return userId;
  }

  public String getYear() {
    return year;
  }

  /**
   * Inner class used to represent a row in a Monthly Report
   */
  public class ReportRow {

    private String firstName = "";
    private int index = 0;
    private String rowLastName = "";
    private String rowLoginId = "";
    private String rowProjectCodeDesc = "";
    private String rowProjectCodeId = "";
    private String rowProjectId = "";
    private String rowProjectName = "";
    private double rowRate = 0;
    private double rowRevenue = 0;
    private double rowSumWorkedHours = 0;
    private String rowTimeTypeDesc = "";
    private String rowUserId = "";

    /**
     * Constructor for ReportRow. Creates a Row.
     *
     * @param projectId
     * @param projectName
     * @param userId
     * @param loginId
     * @param firstName
     * @param projectCodeId
     * @param lastName
     * @param projectCodeDesc
     * @exception throws XMLBuilderException if something goes wrong.
     */
    public ReportRow(String projectId, String projectName, String userId, String firstName, String lastName, String loginId, String projectCodeId, String projectCodeDesc,
            String timeTypeDesc, double rate, double sumWorkedHours, double revenue, int index) throws XMLBuilderException {
      this.rowProjectId = projectId;
      this.rowProjectName = projectName;
      this.rowUserId = userId;
      this.firstName = firstName;
      this.rowLastName = lastName;
      this.rowLoginId = loginId;
      this.rowProjectCodeId = projectCodeId;
      this.rowProjectCodeDesc = projectCodeDesc;
      this.rowTimeTypeDesc = timeTypeDesc;
      this.rowRate = rate;
      this.rowSumWorkedHours = sumWorkedHours;
      this.rowRevenue = revenue;
      this.index = index;
    }

    /**
     * Make ReportRow to xml.
     *
     * @param xmlDoc
     * @throws java.lang.Exception
     */
    public void toXML(StringBuffer xmlDoc) throws Exception {
      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".toXML(): Entering");
      }

      xmlDoc.append(XML_ROW_START);
      xmlDoc.append(XML_ROW_INDEX_START).append(index).append(XML_ROW_INDEX_END);
      xmlDoc.append(XML_ROW_PROJECTID_START).append(rowProjectId).append(XML_ROW_PROJECTID_END);
      xmlDoc.append(XML_ROW_PROJECTNAME_START).append(rowProjectName).append(XML_ROW_PROJECTNAME_END);
      xmlDoc.append(XML_ROW_USERID_START).append(rowUserId).append(XML_ROW_USERID_END);
      xmlDoc.append(XML_ROW_FIRSTNAME_START).append(firstName).append(XML_ROW_FIRSTNAME_END);
      xmlDoc.append(XML_ROW_LASTNAME_START).append(rowLastName).append(XML_ROW_LASTNAME_END);
      xmlDoc.append(XML_ROW_LOGINID_START).append(rowLoginId).append(XML_ROW_LOGINID_END);
      xmlDoc.append(XML_ROW_PROJECTCODEID_START).append(rowProjectCodeId).append(XML_ROW_PROJECTCODEID_END);
      xmlDoc.append(XML_ROW_PROJECTCODEDESC_START).append(rowProjectCodeDesc).append(XML_ROW_PROJECTCODEDESC_END);
      xmlDoc.append(XML_ROW_TIMETYPEDESC_START).append(rowTimeTypeDesc).append(XML_ROW_TIMETYPEDESC_END);
      xmlDoc.append(XML_ROW_RATE_START).append(NumberFormatter.format(rowRate, 0, true)).append(XML_ROW_RATE_END);
      xmlDoc.append(XML_ROW_WORKEDH_START).append(NumberFormatter.format(rowSumWorkedHours, 2, true)).append(XML_ROW_WORKEDH_END);
      xmlDoc.append(XML_ROW_REVENUE_START).append(NumberFormatter.format(rowRevenue, 2, true)).append(XML_ROW_REVENUE_END);
      xmlDoc.append(XML_ROW_END);
    }

    public double getRowRate() {
      return this.rowRate;
    }

    public double getRowRevenue() {
      return this.rowRevenue;
    }

    public double getRowSumWorkedHours() {
      return this.rowSumWorkedHours;
    }

    public String getFirstName() {
      return firstName;
    }

    public int getIndex() {
      return index;
    }

    public String getRowLastName() {
      return rowLastName;
    }

    public String getRowLoginId() {
      return rowLoginId;
    }

    public String getRowProjectCodeDesc() {
      return rowProjectCodeDesc;
    }

    public String getRowProjectCodeId() {
      return rowProjectCodeId;
    }

    public String getRowProjectId() {
      return rowProjectId;
    }

    public String getRowProjectName() {
      return rowProjectName;
    }

    public String getRowTimeTypeDesc() {
      return rowTimeTypeDesc;
    }

    public String getRowUserId() {
      return rowUserId;
    }
  }
}
