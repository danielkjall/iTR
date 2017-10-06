package com.intiro.itr.logic.generatereport.vacation;

import com.intiro.itr.db.*;
import com.intiro.itr.util.*;
import com.intiro.itr.util.personalization.*;
import com.intiro.itr.util.xml.*;
import com.intiro.itr.util.log.IntiroLog;
import java.util.*;

public class VacationReport extends DynamicXMLCarrier {

  static final String XML_ROLE_END = "</role>";
  //GeneralReport
  static final String XML_ROLE_START = "<role>";
  static final String XML_ROW_END = "</row>";
  static final String XML_ROW_INDEX_END = "</i>";
  static final String XML_ROW_INDEX_START = "<i>";
  static final String XML_ROW_PROJECTNAME_END = "</projectname>";
  static final String XML_ROW_PROJECTNAME_START = "<projectname>";
  //ROW
  static final String XML_ROW_START = "<row>";
  static final String XML_ROW_TIMETYPE_END = "</timetype>";
  static final String XML_ROW_TIMETYPE_START = "<timetype>";
  static final String XML_ROW_WORKEDHOURS_END = "</workedh>";
  static final String XML_ROW_WORKEDHOURS_START = "<workedh>";
  static final String XML_SUM_MONEYOVERTIMEHOURS_END = "</summoneyh>";
  static final String XML_SUM_MONEYOVERTIMEHOURS_START = "<summoneyh>";
  static final String XML_SUM_TOTALOVERTIMEHOURS_END = "</sumovertimeh>";
  static final String XML_SUM_TOTALOVERTIMEHOURS_START = "<sumovertimeh>";
  static final String XML_SUM_VACATIONOVERTIMEHOURS_END = "</sumvacationh>";
  static final String XML_SUM_VACATIONOVERTIMEHOURS_START = "<sumvacationh>";
  static final String XML_SUM_WORKEDHOURS_END = "</sumworkedh>";
  static final String XML_SUM_WORKEDHOURS_START = "<sumworkedh>";
  static final String XML_TABLE_COMPANY_END = "</company>";
  static final String XML_TABLE_COMPANY_START = "<company>";
  static final String XML_TABLE_DEFAULTDAYS_END = "</defaultdays>";
  static final String XML_TABLE_DEFAULTDAYS_START = "<defaultdays>";
  static final String XML_TABLE_END = "</vacationtable>";
  static final String XML_TABLE_MONEYHOURS_END = "</moneyh>";
  static final String XML_TABLE_MONEYHOURS_START = "<moneyh>";
  static final String XML_TABLE_REMAININGDAYS_END = "</remainingdays>";
  static final String XML_TABLE_REMAININGDAYS_START = "<remainingdays>";
  static final String XML_TABLE_SAVEDDAYS_END = "</saveddays>";
  static final String XML_TABLE_SAVEDDAYS_START = "<saveddays>";
  //TABLE
  static final String XML_TABLE_START = "<vacationtable>";
  static final String XML_TABLE_SUMWORKEDHOURS_END = "</sumworkedh>";
  static final String XML_TABLE_SUMWORKEDHOURS_START = "<sumworkedh>";
  static final String XML_TABLE_TOTALHOURS_END = "</totalh>";
  static final String XML_TABLE_TOTALHOURS_START = "<totalh>";
  static final String XML_TABLE_USEDDAYS_END = "</useddays>";
  static final String XML_TABLE_USEDDAYS_START = "<useddays>";
  static final String XML_TABLE_USERNAME_END = "</username>";
  static final String XML_TABLE_USERNAME_START = "<username>";
  static final String XML_TABLE_VACATIONHOURS_END = "</vacationh>";
  static final String XML_TABLE_VACATIONHOURS_START = "<vacationh>";
  static final String XML_YEAR_END = "</year>";
  static final String XML_YEAR_START = "<year>";
  String companyId = null;
  String mode = "";
  String projectId = null;
  double sumExpectedHours = 0;
  double sumMoneyOvertimeHours = 0;
  double sumTotalOvertimeHours = 0;
  double sumVacationOvertimeHours = 0;
  double sumWorkedHours = 0;

  //week reports
  ArrayList<ReportTable> tables = new ArrayList<>();
  String userId = null;
  String year = null;

  /**
   * Constructor I for VacationReport.
   *
   * @param profile the UserProfile for the current user.
   * @param userId
   * @param projectId
   * @param companyId
   * @param year
   * @exception XMLBuilderException if something goes wrong.
   */
  public VacationReport(UserProfile profile, String userId, String projectId, String companyId, String year) throws XMLBuilderException {
    super(profile);
    this.userId = userId;
    this.projectId = projectId;
    this.companyId = companyId;
    this.year = year;

    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".constructor(): userId = " + userId + ", projectId = " + projectId + ", companyId = " + companyId + ", year = " + year);
    }
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
   * Loads the vacation report.
   *
   * @throws com.intiro.itr.util.xml.XMLBuilderException
   */
  public void load() throws XMLBuilderException {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".load() Entering");
    }
    try {
      StringRecordset rs = DBQueries.getProxy().getVacationReport(userId, projectId, companyId, year);
      int rowIndex = 0;
      int tableIndex = 0;
      int userId = -1;
      String userName = null;
      int defaultVacationDays = -1;
      int usedVacationDays = -1;
      int savedVacationDays = -1;
      double overtimeVacationHours = -1;
      double overtimeMoneyHours = -1;
      String company = null;
      String projectName = null;
      String timetype = null;
      double workedHours = -1;
      int userIdOld = 0;
      ReportTable oneTable = null;

      while (!rs.getEOF()) {
        projectName = rs.getField("pr_Name");
        timetype = rs.getField(DBConstants.TIMETYPE_TYPE);
        workedHours = Double.parseDouble(rs.getField("WorkedHours"));

        ReportRow oneRow;
        userId = Integer.parseInt(rs.getField(DBConstants.USER_ID_PK));

        if (userId != userIdOld) {
          userName = rs.getField(DBConstants.USER_FIRSTNAME) + " " + rs.getField(DBConstants.USER_LASTNAME);
          defaultVacationDays = Integer.parseInt(rs.getField(DBConstants.USER_VACATION_DEFAULT_DAYS));
          usedVacationDays = Integer.parseInt(rs.getField(DBConstants.USER_VACATION_USED_DAYS));
          savedVacationDays = Integer.parseInt(rs.getField(DBConstants.USER_VACATION_SAVED_DAYS));
          overtimeVacationHours = Double.parseDouble(rs.getField(DBConstants.USER_OVERTIME_VACATION_HOURS));
          overtimeMoneyHours = Double.parseDouble(rs.getField(DBConstants.USER_OVERTIME_MONEY_HOURS));
          company = rs.getField("co_Name");
          userIdOld = userId;
          oneTable = new ReportTable(userName, defaultVacationDays, usedVacationDays, savedVacationDays, overtimeVacationHours, overtimeMoneyHours, company, tableIndex);
          tableIndex++;
          rowIndex = 0;

          //Add ReportTable to tables
          tables.add(oneTable);
        }

        oneRow = new ReportRow(projectName, timetype, workedHours, rowIndex);
        oneTable.addRow(oneRow);
        rs.moveNext();
        rowIndex++;
      }

      rs.close();

      //Calculate summarys
      calculateTimeReportSummary();
    } catch (Exception e) {
      IntiroLog.criticalError(getClass(), getClass().getName() + ".ERROR FROM DATABASE, exception = " + e.getMessage());
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

    /*Year*/
    xmlDoc.append(XML_YEAR_START);
    xmlDoc.append(year);
    xmlDoc.append(XML_YEAR_END);

    /*table*/
    ReportTable oneTable;

    /*Loop through all table in VacationReport*/
    for (int i = 0; i < tables.size(); i++) {
      oneTable = tables.get(i);

      if (oneTable != null) {
        oneTable.toXML(xmlDoc);
      }
    }

    /*Sum WorkedHours*/
    xmlDoc.append(XML_SUM_WORKEDHOURS_START);
    xmlDoc.append(sumWorkedHours);
    xmlDoc.append(XML_SUM_WORKEDHOURS_END);

    //Sum Money Overtime Hours
    xmlDoc.append(XML_SUM_MONEYOVERTIMEHOURS_START);
    xmlDoc.append(sumMoneyOvertimeHours);
    xmlDoc.append(XML_SUM_MONEYOVERTIMEHOURS_END);

    //Sum Vacation Overtime Hours
    xmlDoc.append(XML_SUM_VACATIONOVERTIMEHOURS_START);
    xmlDoc.append(sumVacationOvertimeHours);
    xmlDoc.append(XML_SUM_VACATIONOVERTIMEHOURS_END);

    //Sum Total Overtime Hours
    xmlDoc.append(XML_SUM_TOTALOVERTIMEHOURS_START);
    xmlDoc.append(sumTotalOvertimeHours);
    xmlDoc.append(XML_SUM_TOTALOVERTIMEHOURS_END);

    /*Get end of document*/
    builder.getEndOfDocument(xmlDoc);

    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".toXML(): xmlDoc = " + xmlDoc.toString());
    }
  }

  void calculateTimeReportSummary() {
    ReportTable oneTable;

    for (int i = 0; i < tables.size(); i++) {
      oneTable = tables.get(i);

      if (oneTable != null) {
        oneTable.calculateSumWorkedHours();
        sumWorkedHours += oneTable.getReportSumWorkedHours();
        sumVacationOvertimeHours += oneTable.getVacationHours();
        sumMoneyOvertimeHours += oneTable.getMoneyHours();
      }
    }

    sumTotalOvertimeHours = sumMoneyOvertimeHours + sumVacationOvertimeHours;
  }

  /**
   * Inner class used to represent a table in GeneralReport.
   */
  class ReportTable {

    private String reportCompany = null;
    private int reportDefaultVacationDays = 0;
    private double reportOvertimeMoneyHours = 0;
    //private double reportOvertimeTotalHours = 0;
    private double reportOvertimeVacationHours = 0;
    private int reportRemainingVacationDays = 0;
    private int reportRowIndex = 0;
    private ArrayList<ReportRow> reportRows = new ArrayList<>();
    private int reportSavedVacationDays = 0;
    private double reportSumWorkedHours = 0;
    private int reportUsedVacationDays = 0;
    private String reportUserName = null;

    /**
     * Constructor I for ReportTable. Creates a Table.
     *
     * @exception throws XMLBuilderException if something goes wrong.
     */
    public ReportTable(String userName, int defaultVacationDays, int usedVacationDays, int savedVacationDays, double overtimeVacationHours, double overtimeMoneyHours, String company, int rowIndex) throws XMLBuilderException {
      this.reportUserName = userName;
      this.reportDefaultVacationDays = defaultVacationDays;
      this.reportUsedVacationDays = usedVacationDays;
      this.reportSavedVacationDays = savedVacationDays;
      this.reportOvertimeVacationHours = overtimeVacationHours;
      this.reportOvertimeMoneyHours = overtimeMoneyHours;
      this.reportCompany = company;
      this.reportRowIndex = rowIndex;
      this.reportRemainingVacationDays = (defaultVacationDays + savedVacationDays) - usedVacationDays;
    }

    /**
     * Make ReportTable to xml.
     */
    public void toXML(StringBuffer xmlDoc) throws Exception {
      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".toXML(): Entering");
      }

      xmlDoc.append(XML_TABLE_START);

      // Static part of the row
      //User name
      xmlDoc.append(XML_TABLE_USERNAME_START);
      xmlDoc.append(reportUserName);
      xmlDoc.append(XML_TABLE_USERNAME_END);

      //Default Vacation days
      xmlDoc.append(XML_TABLE_DEFAULTDAYS_START);
      xmlDoc.append(reportDefaultVacationDays);
      xmlDoc.append(XML_TABLE_DEFAULTDAYS_END);

      //Remaining Vacation days
      xmlDoc.append(XML_TABLE_USEDDAYS_START);
      xmlDoc.append(getUsedDays());
      xmlDoc.append(XML_TABLE_USEDDAYS_END);

      //Remaining Vacation days
      xmlDoc.append(XML_TABLE_REMAININGDAYS_START);
      xmlDoc.append(getRemainingDays());
      xmlDoc.append(XML_TABLE_REMAININGDAYS_END);

      //Saved Vacation days
      xmlDoc.append(XML_TABLE_SAVEDDAYS_START);
      xmlDoc.append(reportSavedVacationDays);
      xmlDoc.append(XML_TABLE_SAVEDDAYS_END);

      /*Vacation hours*/
      xmlDoc.append(XML_TABLE_VACATIONHOURS_START);
      xmlDoc.append(reportOvertimeVacationHours);
      xmlDoc.append(XML_TABLE_VACATIONHOURS_END);

      /*Money hours*/
      xmlDoc.append(XML_TABLE_MONEYHOURS_START);
      xmlDoc.append(reportOvertimeMoneyHours);
      xmlDoc.append(XML_TABLE_MONEYHOURS_END);

      /*Total hours*/
      xmlDoc.append(XML_TABLE_TOTALHOURS_START);
      xmlDoc.append(getOvertimeTotal());
      xmlDoc.append(XML_TABLE_TOTALHOURS_END);

      /*Company*/
      xmlDoc.append(XML_TABLE_COMPANY_START);
      xmlDoc.append(reportCompany);
      xmlDoc.append(XML_TABLE_COMPANY_END);

      /*Worked Hours Sum*/
      xmlDoc.append(XML_TABLE_SUMWORKEDHOURS_START);
      xmlDoc.append(getReportSumWorkedHours());
      xmlDoc.append(XML_TABLE_SUMWORKEDHOURS_END);

      //Row index
      xmlDoc.append(XML_ROW_INDEX_START);
      xmlDoc.append(reportRowIndex);
      xmlDoc.append(XML_ROW_INDEX_END);

      /*rows*/
      ReportRow oneRow;

      /*Loop through all rows in VacationReport*/
      for (int i = 0; i < reportRows.size(); i++) {
        oneRow = reportRows.get(i);

        if (oneRow != null) {
          oneRow.toXML(xmlDoc);
        }
      }

      xmlDoc.append(XML_TABLE_END);
    }

    int getDefaultDays() {
      return this.reportDefaultVacationDays;
    }

    double getMoneyHours() {
      return this.reportOvertimeMoneyHours;
    }

    double getOvertimeTotal() {
      return this.reportOvertimeMoneyHours + this.reportOvertimeVacationHours;
    }

    int getRemainingDays() {
      return this.reportRemainingVacationDays;
    }

    int getSavedDays() {
      return this.reportSavedVacationDays;
    }

    double getReportSumWorkedHours() {
      return this.reportSumWorkedHours;
    }

    int getUsedDays() {
      return this.reportUsedVacationDays;
    }

    double getVacationHours() {
      return this.reportOvertimeVacationHours;
    }

    void addRow(ReportRow row) {
      reportRows.add(row);
    }

    void calculateSumWorkedHours() {
      ReportRow oneRow;

      for (int i = 0; i < reportRows.size(); i++) {
        oneRow = reportRows.get(i);

        if (oneRow != null) {
          reportSumWorkedHours += oneRow.getWorkedHours();
        }
      }
    }
  }

  /**
   * Inner class used to represent a row in GeneralReport.
   */
  class ReportRow {

    String projectName = null;
    int rowIndex = 0;
    String timetype = null;
    double workedHours = 0;

    /**
     * Constructor I for ReportRow. Creates a Row.
     *
     * @exception throws XMLBuilderException if something goes wrong.
     */
    public ReportRow(String projectName, String timetype, double workedHours, int rowIndex) throws XMLBuilderException {
      this.projectName = projectName;
      this.timetype = timetype;
      this.workedHours = workedHours;
      this.rowIndex = rowIndex;
    }

    /**
     * Make ReportRow to xml.
     */
    public void toXML(StringBuffer xmlDoc) throws Exception {
      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".toXML(): Entering");
      }

      xmlDoc.append(XML_ROW_START);

      //Project
      xmlDoc.append(XML_ROW_PROJECTNAME_START);
      xmlDoc.append(projectName);
      xmlDoc.append(XML_ROW_PROJECTNAME_END);

      /*Type of time*/
      xmlDoc.append(XML_ROW_TIMETYPE_START);
      xmlDoc.append(timetype);
      xmlDoc.append(XML_ROW_TIMETYPE_END);

      /*WorkedHours*/
      xmlDoc.append(XML_ROW_WORKEDHOURS_START);
      xmlDoc.append(workedHours);
      xmlDoc.append(XML_ROW_WORKEDHOURS_END);

      //Row index
      xmlDoc.append(XML_ROW_INDEX_START);
      xmlDoc.append(rowIndex);
      xmlDoc.append(XML_ROW_INDEX_END);
      xmlDoc.append(XML_ROW_END);
    }

    double getWorkedHours() {
      return this.workedHours;
    }
  }

}
