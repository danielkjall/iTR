package com.intiro.itr.logic.generatereport.general;

import com.intiro.itr.db.*;
import com.intiro.itr.util.*;
import com.intiro.itr.util.personalization.*;
import com.intiro.itr.util.xml.*;
import com.intiro.itr.util.log.IntiroLog;
import java.util.*;

public class GeneralReport extends DynamicXMLCarrier {

  static final String XML_AVG_RATE_END = "</avgrate>";
  static final String XML_AVG_RATE_START = "<avgrate>";
  static final String XML_ROLE_END = "</role>";
  //GeneralReport
  static final String XML_ROLE_START = "<role>";
  static final String XML_ROW_END = "</row>";
  static final String XML_ROW_EXPECTEDHOURS_END = "</expectedh>";
  static final String XML_ROW_EXPECTEDHOURS_START = "<expectedh>";
  static final String XML_ROW_INDEX_END = "</i>";
  static final String XML_ROW_INDEX_START = "<i>";
  static final String XML_ROW_KVARTAL_END = "</kvartal>";
  static final String XML_ROW_KVARTAL_START = "<kvartal>";
  static final String XML_ROW_MONTH_END = "</month>";
  static final String XML_ROW_MONTH_START = "<month>";
  static final String XML_ROW_PROJECTADMIN_END = "</projadmin>";
  static final String XML_ROW_PROJECTADMIN_START = "<projadmin>";
  static final String XML_ROW_PROJECTCODENAME_END = "</projectcodename>";
  static final String XML_ROW_PROJECTCODENAME_START = "<projectcodename>";
  static final String XML_ROW_PROJECTNAME_END = "</projectname>";
  static final String XML_ROW_PROJECTNAME_START = "<projectname>";
  static final String XML_ROW_RATE_END = "</rate>";
  static final String XML_ROW_RATE_START = "<rate>";
  static final String XML_ROW_REVENUE_END = "</revenue>";
  static final String XML_ROW_REVENUE_START = "<revenue>";
  static final String XML_ROW_START = "<row>";
  static final String XML_ROW_SUM_END = "</rowsum>";
  static final String XML_ROW_SUM_START = "<rowsum>";
  static final String XML_ROW_USERNAME_END = "</username>";
  static final String XML_ROW_USERNAME_START = "<username>";
  static final String XML_ROW_WEEK_END = "</week>";
  static final String XML_ROW_WEEK_START = "<week>";
  static final String XML_ROW_WORKEDHOURS_END = "</workedh>";
  static final String XML_ROW_WORKEDHOURS_START = "<workedh>";
  static final String XML_ROW_YEAR_END = "</year>";
  //ROW
  static final String XML_ROW_YEAR_START = "<year>";
  static final String XML_SUM_EXPECTEDHOURS_END = "</sumexpectedh>";
  static final String XML_SUM_EXPECTEDHOURS_START = "<sumexpectedh>";
  static final String XML_SUM_REVENUES_END = "</sumrevenues>";
  static final String XML_SUM_REVENUES_START = "<sumrevenues>";
  static final String XML_SUM_WORKEDHOURS_END = "</sumworkedh>";
  static final String XML_SUM_WORKEDHOURS_START = "<sumworkedh>";
  String fromDate = null;
  String mode = "";
  String projectCodeId = null;
  String projectId = null;
  double rateAverage = 0;
  //week reports
  ArrayList<ReportRow> rows = new ArrayList<ReportRow>();
  double sumExpectedHours = 0;
  double sumRevenue = 0;
  double sumWorkedHours = 0;
  String toDate = null;
  String userId = null;

  /**
   * Constructor I for GeneralReport.
   *
   * @param profile the UserProfile for the current user.
   * @param userId
   * @param toDate
   * @param projectId
   * @param projectCodeId
   * @param fromDate
   * @exception XMLBuilderException if something goes wrong.
   */
  public GeneralReport(UserProfile profile, String userId, String projectId, String projectCodeId, String fromDate, String toDate) throws XMLBuilderException {
    super(profile);
    this.userId = userId;
    this.projectId = projectId;
    this.projectCodeId = projectCodeId;
    this.fromDate = fromDate;
    this.toDate = toDate;
  }

  public boolean getBooleanValue(String booleanFromDB) {
    //if booleanFromDB = "1" || booleanFromDB = "True",  then it is true.
    if ((booleanFromDB != null && booleanFromDB.trim().equals("1")) || (booleanFromDB != null && booleanFromDB.trim().equalsIgnoreCase("True"))) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Loads the general report.
   *
   * @throws com.intiro.itr.util.xml.XMLBuilderException
   */
  public void load() throws XMLBuilderException {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".load() Entering");
    }
    try {
      StringRecordset rs = DBQueries.getProxy().getGeneralReport(userId, projectId, projectCodeId, fromDate, toDate);
      int index = 0;

      while (!rs.getEOF()) {
        String projectName = rs.getField("pr_Name");
        String projectCode = rs.getField("pc_Description");
        String userName = rs.getField(DBConstants.USER_FIRSTNAME) + " " + rs.getField(DBConstants.USER_LASTNAME);
        double expectedHours = Double.parseDouble(rs.getField("ExpectedHours"));
        double workedHours = Double.parseDouble(rs.getField("WorkedHours"));
        ITRCalendar date = new ITRCalendar(rs.getField("FromDate"));
        boolean projectAdmin = getBooleanValue(rs.getField(DBConstants.PROJECTMEMBERS_PROJECTADMIN));
        double rate = Double.parseDouble(rs.getField(DBConstants.PROJECTMEMBERS_RATE));
        ReportRow oneRow = new ReportRow(projectName, projectCode, userName, expectedHours, workedHours, date, projectAdmin, rate, index);

        /*Add ReportRow to rows*/
        rows.add(oneRow);
        rs.moveNext();
        index++;
      }

      rs.close();

      //Calculate summarys
      calculateTimeReportSummary();
    } catch (Exception e) {
      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".ERROR FROM DATABASE, exception = " + e.getMessage());
      }

      throw new XMLBuilderException(e.getMessage());
    }
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

    /*Get start of document*/
    builder.getStartOfDocument(xmlDoc);

    /*Users role*/
    xmlDoc.append(XML_ROLE_START);
    xmlDoc.append(getUserProfile().getRole().getName());
    xmlDoc.append(XML_ROLE_END);

    /*rows*/
    ReportRow oneRow;

    /*Loop through all rows in GeneralReport*/
    for (int i = 0; i < rows.size(); i++) {
      oneRow = rows.get(i);

      if (oneRow != null) {
        oneRow.toXML(xmlDoc);
      }
    }

    /*Sum ExpectedHours*/
    xmlDoc.append(XML_SUM_EXPECTEDHOURS_START);
    xmlDoc.append(sumExpectedHours);
    xmlDoc.append(XML_SUM_EXPECTEDHOURS_END);

    /*Sum WorkedHours*/
    xmlDoc.append(XML_SUM_WORKEDHOURS_START);
    xmlDoc.append(sumWorkedHours);
    xmlDoc.append(XML_SUM_WORKEDHOURS_END);

    /*Average rate*/
    xmlDoc.append(XML_AVG_RATE_START);
    xmlDoc.append(rateAverage);
    xmlDoc.append(XML_AVG_RATE_END);

    /*Sum Revenues*/
    xmlDoc.append(XML_SUM_REVENUES_START);
    xmlDoc.append(sumRevenue);
    xmlDoc.append(XML_SUM_REVENUES_END);

    /*Get end of document*/
    builder.getEndOfDocument(xmlDoc);

    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".toXML(): xmlDoc = " + xmlDoc.toString());
    }
  }

  void calculateTimeReportSummary() {
    double sumRate = 0;

    /*rows*/
    ReportRow oneRow;

    /*Loop through all weekReports in combobox*/
    for (int i = 0; i < rows.size(); i++) {
      oneRow = rows.get(i);

      if (oneRow != null) {
        sumExpectedHours += oneRow.getExpectedHours();
        sumWorkedHours += oneRow.getWorkedHours();
        sumRate += oneRow.getRate();
      }
    }
    if (sumRate > 0) {
      rateAverage = sumRate / rows.size();
    }

    sumRevenue = rateAverage * sumWorkedHours;
  }

  /**
   * Inner class used to represent a row in GeneralReport.
   */
  class ReportRow {

    ITRCalendar date = null;
    double expectedHours = 0;
    boolean projectAdmin = false;
    String projectCode = "";
    String projectName = "";
    double rate = 0;
    double revenue = 0;
    int rowIndex = 1;
    String userName = "";
    double workedHours = 0;

    /**
     * Constructor I for ReportRow. Creates a Row.
     *
     * @exception throws XMLBuilderException if something goes wrong.
     */
    public ReportRow(String projectName, String projectCode, String userName, double expectedHours, double workedHours, ITRCalendar date, boolean projectAdmin, double rate, int rowIndex) throws XMLBuilderException {
      this.projectName = projectName;
      this.projectCode = projectCode;
      this.userName = userName;
      this.expectedHours = expectedHours;
      this.workedHours = workedHours;
      this.projectAdmin = projectAdmin;
      this.date = date;
      this.rate = rate;
      this.rowIndex = rowIndex;
      this.revenue = workedHours * this.rate;
    }

    /**
     * Make ReportRow to xml.
     */
    public void toXML(StringBuffer xmlDoc) throws Exception {
      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".toXML(): Entering");
      }

      xmlDoc.append(XML_ROW_START);

      //Revenues
      xmlDoc.append(XML_ROW_INDEX_START);
      xmlDoc.append(rowIndex);
      xmlDoc.append(XML_ROW_INDEX_END);

      //Year
      xmlDoc.append(XML_ROW_YEAR_START);

      if (date != null) {
        xmlDoc.append(date.getYear());
      }

      xmlDoc.append(XML_ROW_YEAR_END);

      //Kvartal
      xmlDoc.append(XML_ROW_KVARTAL_START);

      if (date != null) {
        xmlDoc.append(date.getKvartal());
      }

      xmlDoc.append(XML_ROW_KVARTAL_END);

      //Month
      xmlDoc.append(XML_ROW_MONTH_START);

      if (date != null) {
        xmlDoc.append(date.getMonthNameShort());
      }

      xmlDoc.append(XML_ROW_MONTH_END);

      //Week
      xmlDoc.append(XML_ROW_WEEK_START);

      if (date != null) {
        xmlDoc.append(date.getWeekOfYear()).append("_").append(date.getWeekPart());
      }

      xmlDoc.append(XML_ROW_WEEK_END);

      //Project
      xmlDoc.append(XML_ROW_PROJECTNAME_START);
      xmlDoc.append(projectName);
      xmlDoc.append(XML_ROW_PROJECTNAME_END);

      //ProjectCode
      xmlDoc.append(XML_ROW_PROJECTCODENAME_START);
      xmlDoc.append(projectCode);
      xmlDoc.append(XML_ROW_PROJECTCODENAME_END);

      //User name
      xmlDoc.append(XML_ROW_USERNAME_START);
      xmlDoc.append(userName);
      xmlDoc.append(XML_ROW_USERNAME_END);

      /*Project admin*/
      xmlDoc.append(XML_ROW_PROJECTADMIN_START);
      xmlDoc.append(projectAdmin);
      xmlDoc.append(XML_ROW_PROJECTADMIN_END);

      /*Rate*/
      xmlDoc.append(XML_ROW_RATE_START);
      xmlDoc.append(rate);
      xmlDoc.append(XML_ROW_RATE_END);

      /*ExpectedHours*/
      xmlDoc.append(XML_ROW_EXPECTEDHOURS_START);
      xmlDoc.append(expectedHours);
      xmlDoc.append(XML_ROW_EXPECTEDHOURS_END);

      /*WorkedHours*/
      xmlDoc.append(XML_ROW_WORKEDHOURS_START);
      xmlDoc.append(workedHours);
      xmlDoc.append(XML_ROW_WORKEDHOURS_END);

      /*Revenues*/
      xmlDoc.append(XML_ROW_REVENUE_START);
      xmlDoc.append(revenue);
      xmlDoc.append(XML_ROW_REVENUE_END);
      xmlDoc.append(XML_ROW_END);
    }

    double getExpectedHours() {
      return this.expectedHours;
    }

    double getRate() {
      return this.rate;
    }

    double getWorkedHours() {
      return this.workedHours;
    }
  }
}