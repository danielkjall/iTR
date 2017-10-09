package com.intiro.itr.logic.weekreport;

import com.intiro.itr.db.vo.CalendarWeekVO;
import com.intiro.itr.db.DBConstants;
import com.intiro.itr.db.DBExecute;
import com.intiro.itr.db.DBQueries;
import com.intiro.itr.db.DBQueriesInterface;
import com.intiro.itr.db.DbExecuteInterface;
import com.intiro.itr.logic.project.Project;
import com.intiro.itr.util.ITRCalendar;
import com.intiro.itr.util.NumberFormatter;
import com.intiro.itr.util.StringRecordset;
import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.itr.util.xml.DynamicXMLCarrier;
import com.intiro.itr.util.xml.XMLBuilder;
import com.intiro.itr.util.xml.XMLBuilderException;
import com.intiro.itr.util.log.IntiroLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeekReport extends DynamicXMLCarrier {

  static final String XML_ACTION_ON_FORM_END = "</action>";
  static final String XML_ACTION_ON_FORM_START = "<action>";
  static final String XML_DATE_END = "</date>";
  static final String XML_DATE_START = "<date>";
  static final String XML_EXPECTED_HOURS_END = "</expectedhours>";
  static final String XML_EXPECTED_HOURS_START = "<expectedhours>";
  static final String XML_MESSAGE_END = "</message>";
  // Variables for SAVE and SUBMIT.
  static final String XML_MESSAGE_START = "<message>";
  static final String XML_MODE_END = "</mode>";
  static final String XML_MODE_START = "<mode>";
  static final String XML_MONTH_END = "</month>";
  static final String XML_MONTH_START = "<month>";
  static final String XML_NORMAL_HOURS_END = "</normalhours>";
  static final String XML_NORMAL_HOURS_START = "<normalhours>";
  static final String XML_SAVE_ERROR_OCCURED_END = "</saveerror>";
  static final String XML_SAVE_ERROR_OCCURED_START = "<saveerror>";
  static final String XML_SUBMIT_ERROR_OCCURED_END = "</message>";
  static final String XML_SUBMIT_ERROR_OCCURED_START = "<submiterror>";
  static final String XML_TITLE_END = "</title>";
  static final String XML_TITLE_START = "<title>";
  static final String XML_USERNAME_END = "</username>";
  static final String XML_USERNAME_START = "<username>";
  static final String XML_WEEKCOMMENT_END = "</weekcomment>";
  static final String XML_WEEKCOMMENT_START = "<weekcomment>";
  static final String XML_WEEKINDEX_END = "</i>";
  // variables for toXMLSummary
  static final String XML_WEEKINDEX_START = "<i>";
  static final String XML_WEEKNO_END = "</weekno>";
  // Week
  static final String XML_WEEKNO_START = "<weekno>";
  static final String XML_WEEKPART_END = "</weekpart>";
  static final String XML_WEEKPART_START = "<weekpart>";
  static final String XML_WEEK_END = "</week>";
  static final String XML_WEEK_START = "<week>";
  static final String XML_YEAR_END = "</year>";
  static final String XML_YEAR_START = "<year>";
  EditRow editRow = null;
  ITRCalendar fromDate = null;
  boolean isLoaded = false;
  boolean isSaved = false;
  boolean isSubmittedFromDB = false;
  boolean isSubmittedFromUser = false;
  String mode = "";
  ArrayList<Row> rows = new ArrayList<>();
  boolean saveError = false;
  boolean submitError = false;
  boolean submitHasBeenChecked = false;
  SumRows sumRows = null;
  ITRCalendar toDate = null;
  String weekComment = "";
  int weekCommentId = 1;
  int weekReportId = -1;

  /**
   * Constructor I for WeekReport.
   *
   * @param profile the UserProfile for the current user.
   * @param fromDate a ITRCalendar with the start date.
   * @param toDate a ITRCalendar with the end date.
   * @exception XMLBuilderException if something goes wrong.
   */
  public WeekReport(UserProfile profile, ITRCalendar fromDate, ITRCalendar toDate, String mode) throws XMLBuilderException {
    super(profile);
    this.fromDate = fromDate;
    this.toDate = toDate;
    rows = new ArrayList<>();
    sumRows = new SumRows(profile, fromDate, toDate);
    this.mode = mode;
    editRow = new EditRow(profile, fromDate, toDate, sumRows);
  }

  public WeekReport(UserProfile profile) throws XMLBuilderException {
    super(profile);
  }

  public void setEditRow(EditRow editRow) {
    if (editRow != null) {
      this.editRow = editRow;
    }
  }

  public EditRow getEditRow() {
    return editRow;
  }

  public ITRCalendar getFromDate() {
    return fromDate;
  }

  public boolean isLoaded() {
    return isLoaded;
  }

  public Row getRow(int index) {
    return rows.get(index);
  }

  public ArrayList<Row> getRows() {
    if (IntiroLog.d()) {
      IntiroLog.detail(WeekReport.class, WeekReport.class.getName() + ".getRows(): Entering");
    }

    return rows;
  }

  public boolean isSaved() {
    return isSaved;
  }

  public void setSubmitErrorOccurred(boolean status) {
    submitError = status;
  }

  public boolean isSubmittedFromDB() {
    return isSubmittedFromDB;
  }

  public boolean isSubmittedFromUser() {
    return isSubmittedFromUser;
  }

  public SumRows getSumRows() {
    return sumRows;
  }

  public ITRCalendar getToDate() {
    return toDate;
  }

  public void setWeekComment(String comment) {
    if (comment != null) {
      weekComment = comment;
    }
  }

  public String getWeekComment() {
    return weekComment;
  }

  public String getWeekCommentShort() {

    if (weekComment.length() < 200) {
      return weekComment.substring(0, weekComment.length());
    } else {
      return weekComment.substring(0, 100) + "...";
    }
  }

  public void setWeekCommentId(int id) {
    weekCommentId = id;
  }

  public int getWeekCommentId() {
    return weekCommentId;
  }

  public void setWeekReportId(int id) {
    weekReportId = id;
  }

  public int getWeekReportId() {
    return weekReportId;
  }

  /**
   * Add row in the week report. If we already have a row that exist with the same projectcode, subcode and timetype. Then add to the
   * already existing row, else add the new row along side of the other rows.
   *
   * @param newRow
   */
  public void addRow(Row newRow) {
    if (IntiroLog.d()) {
      IntiroLog.detail(WeekReport.class, WeekReport.class.getName() + ".addRow(Row newRow): Entering");
    }

    boolean inserted = false;
    int noOfRows = rows.size();

    for (int i = 0; i < noOfRows; i++) {
      Row oneRow = getRow(i);
      Project oneProject = oneRow.getProject();
      Project newProject = newRow.getProject();

      // If we already have a row that exist with the same projectcode, subcode and timetype. Then add to the already existing row.
      if ( // oneProject.getProjectaSubCode().trim().equalsIgnoreCase(newProject.getProjectSubCode().trim()) &&
              oneRow != null && !oneRow.isRemoved() && oneProject.getProjectId().trim().equalsIgnoreCase(newProject.getProjectId().trim())
              && oneProject.getProjectActivityId().trim().equalsIgnoreCase(newProject.getProjectActivityId().trim())
              && oneRow.getTimeTypeId().trim().equalsIgnoreCase(newRow.getTimeTypeId())) {

        // Add to the row already existing.
        oneRow.addToMonday(newRow.getMonday());
        oneRow.addToTuesday(newRow.getTuesday());
        oneRow.addToWednesday(newRow.getWednesday());
        oneRow.addToThursday(newRow.getThursday());
        oneRow.addToFriday(newRow.getFriday());
        oneRow.addToSaturday(newRow.getSaturday());
        oneRow.addToSunday(newRow.getSunday());
        inserted = true;
        break;
      } else {
        rows.add(0, newRow);
        inserted = true;
        break;
      }
    }
    if (!inserted) {
      rows.add(0, newRow);
    }

    // Clear the editrows hours
    getEditRow().clearHours();
  }

  public void approve() throws XMLBuilderException {
    try {
      DBExecute.getProxy().updateApprovedInWeek(getWeekReportId(), true);
      handleComment();
      // getUserProfile().save();
    } catch (Exception e) {
      IntiroLog.error(WeekReport.class, WeekReport.class.getName() + ".load(String weekId): ERROR FROM DATABASE, exception = " + e.getMessage());
      throw new XMLBuilderException(e.getMessage());
    }
  }

  public boolean checkIfOkToSubmit() {
    boolean retval = false;
    submitHasBeenChecked = true;

    double expectedHours = getSumRows().getExpectedRow().getRowSum();
    double normalHours = getSumRows().getTotalRow().getNormalHours();

    if (IntiroLog.d()) {
      IntiroLog.detail(WeekReport.class, WeekReport.class.getName() + ".checkIfOkToSubmit(): expectedHours = " + expectedHours);
    }
    if (IntiroLog.d()) {
      IntiroLog.detail(WeekReport.class, WeekReport.class.getName() + ".checkIfOkToSubmit(): normalHours = " + normalHours);
    }
    if (expectedHours == normalHours) {
      retval = true;
    }
    // If we dont want them to submit set submit error.
    if (!retval) {
      setSubmitErrorOccurred(true);
    }
    if (IntiroLog.d()) {
      IntiroLog.detail(WeekReport.class, WeekReport.class.getName() + ".checkIfOkToSubmit(): retval = " + retval);
    }

    return retval;
  }

  public void clear() throws XMLBuilderException {
    rows = new ArrayList<>();
    sumRows = new SumRows(getUserProfile(), getFromDate(), getToDate());
    editRow = new EditRow(getUserProfile(), getFromDate(), getToDate(), sumRows);
  }

  /**
   * Equals. If fromdate is the same the two weekreports are identical.
   *
   * @param oneWeekReport a WeekReport that should be compared with this weekreport.
   * @return a boolean, true if equal, else false.
   */
  public boolean equals(WeekReport oneWeekReport) {
    boolean retval = false;

    if (getFromDate() != null && oneWeekReport.getFromDate() != null) {
      if (getFromDate().getCalendarInStoreFormat().equalsIgnoreCase(oneWeekReport.getFromDate().getCalendarInStoreFormat())) {
        retval = true;
      }
    }

    return retval;
  }

  public static List<WeekReport> loadAllApproved() throws XMLBuilderException {
    List<WeekReport> retval = new ArrayList<>();

    try {
      StringRecordset rs = DBQueries.getProxy().getAllRowsInUserWeek();

      int currentUserId = -1;

      Map<String, UserProfile> mapUserProfiles = UserProfile.loadAllUserProfiles();

      WeekReport wr = null;
      while (!rs.getEOF()) {
        int tmpUserId = rs.getInt(DBConstants.ENTRYROW_USERID_FK);
        if (tmpUserId != currentUserId) {
          WeekReport temp = new WeekReport(mapUserProfiles.get(tmpUserId));
          retval.add(temp);
          wr = temp;
          currentUserId = tmpUserId;
        }

        /* Week have been saved, retrive submitted and weekcomment. */
        wr.setLoaded();
        //extractFromRS(wr, rs);
        rs.moveNext();
      }

      //SumRows sumRow = new SumRows(getUserProfile(), getFromDate(), getToDate());
      //wr.sumRow.calcSum(getRows());
      //wr.setSumRows(sumRow);
    } catch (Exception e) {
      IntiroLog.error(WeekReport.class, WeekReport.class.getName() + ".loadAllApproved(): ERROR FROM DATABASE, exception = " + e.getMessage());
      throw new XMLBuilderException(e.getMessage());
    }

    //calculateDaysStatus();
    return retval;
  }

  public void load() throws XMLBuilderException {
    try {
      StringRecordset rs = DBQueries.getProxy().getRowsInUserWeek(getUserProfile().getUserId(), getFromDate().getCalendarInStoreFormat());

      while (!rs.getEOF()) {

        /* Week have been saved, retrive submitted and weekcomment. */
        setLoaded();
        extractFromRS(this, getUserProfile(), getFromDate(), getToDate(), rs);
        rs.moveNext();
      }

      SumRows sumRow = new SumRows(getUserProfile(), getFromDate(), getToDate());
      sumRow.calcSum(getRows());
      setSumRows(sumRow);
    } catch (Exception e) {
      IntiroLog.error(WeekReport.class, WeekReport.class.getName() + ".load(): ERROR FROM DATABASE, exception = " + e.getMessage());
      throw new XMLBuilderException(e.getMessage());
    }

    calculateDaysStatus();
  }

  public Map<String, String> alreadySubmittedWeeksAsHashmap(String inUserid) throws XMLBuilderException {
    Map<String, String> retval = new HashMap<>();

    try {
      StringRecordset rs = DBQueries.getProxy().getWeeksAlreadySubmittedAsStartDate(inUserid);

      while (!rs.getEOF()) {
        String dateStr = rs.getField("FromDate");
        if (retval.containsKey(dateStr) == false) {
          retval.put(dateStr, dateStr);
        }
        rs.moveNext();
      }

      SumRows sumRow = new SumRows(getUserProfile(), getFromDate(), getToDate());
      sumRow.calcSum(getRows());
      setSumRows(sumRow);
    } catch (Exception e) {
      IntiroLog.error(WeekReport.class, WeekReport.class.getName() + ".alreadySubmittedWeeksAsHashmap(): ERROR FROM DATABASE, exception = " + e.getMessage());
      throw new XMLBuilderException(e.getMessage());
    }

    return retval;
  }

  public void load(StringRecordset rs, int inWeekId) throws XMLBuilderException {
    try {

      setLoaded();
      while (!rs.getEOF()) {
        int calendarWeekId = Integer.parseInt(rs.getField("itr_userweekid"));
        if (calendarWeekId != inWeekId) {
          break;
        }
        extractFromRS(this, getUserProfile(), getFromDate(), getToDate(), rs);
        rs.moveNext();
      }

      SumRows sumRow = new SumRows(getUserProfile(), getFromDate(), getToDate());
      sumRow.calcSum(getRows());
      setSumRows(sumRow);
    } catch (Exception e) {
      IntiroLog.error(WeekReport.class, WeekReport.class.getName() + ".load(): ERROR FROM DATABASE, exception = " + e.getMessage());
      throw new XMLBuilderException(e.getMessage());
    }

    calculateDaysStatus();
  }

  private static void extractFromRS(WeekReport wr, UserProfile up, ITRCalendar fromDate, ITRCalendar toDate, StringRecordset rs) throws XMLBuilderException, Exception {
    wr.setSubmittedFromDB(rs.getField(DBConstants.USERWEEK_SUBMITTED));
    wr.setWeekComment(rs.getField(DBConstants.COMMENT_COMMENT));
    wr.setWeekReportId(Integer.parseInt(rs.getField(DBConstants.ENTRYROW_USERWEEKID_FK)));
    double mo = Double.parseDouble(rs.getField(DBConstants.ENTRYROW_MO_HOURS));
    double tu = Double.parseDouble(rs.getField(DBConstants.ENTRYROW_TU_HOURS));
    double we = Double.parseDouble(rs.getField(DBConstants.ENTRYROW_WE_HOURS));
    double th = Double.parseDouble(rs.getField(DBConstants.ENTRYROW_TH_HOURS));
    double fr = Double.parseDouble(rs.getField(DBConstants.ENTRYROW_FR_HOURS));
    double sa = Double.parseDouble(rs.getField(DBConstants.ENTRYROW_SA_HOURS));
    double su = Double.parseDouble(rs.getField(DBConstants.ENTRYROW_SU_HOURS));
    int rowId = Integer.parseInt(rs.getField("Eid"));
    Row oneRow = new Row(up, fromDate, toDate);
    oneRow.setRowEntryId(rowId);
    oneRow.setMonday(mo);
    oneRow.setTuesday(tu);
    oneRow.setWednesday(we);
    oneRow.setThursday(th);
    oneRow.setFriday(fr);
    oneRow.setSaturday(sa);
    oneRow.setSunday(su);
    oneRow.setTimeTypeId(rs.getField(DBConstants.ENTRYROW_TIMETYPEID_FK));
    oneRow.setTimeType(rs.getField(DBConstants.TIMETYPE_TYPE));
    Project oneProject = new Project(up);
    oneProject.load(rs.getField(DBConstants.ENTRYROW_PROJECTID_FK));
    oneRow.setProject(oneProject);
    oneRow.getProject().setProjectActivityId(rs.getField(DBConstants.ENTRYROW_PROJECTCODEID_FK));
    // oneRow.getProject().setProjectSubId(rs.getField(DBConstants.ENTRYROW_PROJECTCODEID_FK));
    // oneRow.getProject().setProjectSubCode(rs.getField(DBConstants.PROJECTCODE_CODE));
    // oneRow.getProject().setProjectSubDesc(rs.getField(DBConstants.PROJECTCODE_DESCRIPTION));
    oneRow.setRowSum();
    wr.rows.add(oneRow);
  }

  public void loadWeekWithLatestSubmittedWeek() throws XMLBuilderException {
    WeekReport oneWeekReport = null;

    try {
      StringRecordset rs = DBQueries.getProxy().getSubmittedWeeks(getUserProfile().getUserId(), true, false);

      int lastCalendarWeekId = -1;
      boolean latestWeekFound = false;

      while (!rs.getEOF() && !latestWeekFound) {
        int calendarWeekId = Integer.parseInt(rs.getField(DBConstants.CALENDARWEEK_ID_PK));

        /* Only make a new WeekReport if we have a new week. */
        if (calendarWeekId != lastCalendarWeekId) {
          lastCalendarWeekId = calendarWeekId;

          ITRCalendar fromDateInternal = new ITRCalendar(rs.getField(DBConstants.CALENDARWEEK_FROM_DATE));
          ITRCalendar toDateInternal = new ITRCalendar(rs.getField(DBConstants.CALENDARWEEK_TO_DATE));
          oneWeekReport = new WeekReport(getUserProfile(), fromDateInternal, toDateInternal, "Edit");
          oneWeekReport.load();
          latestWeekFound = true;
        }

        rs.moveNext();
      }

      rs.close();

    } catch (Exception e) {
      IntiroLog.error(WeekReport.class, WeekReport.class.getName() + ".loadWeekWithLatestSubmittedWeek(): ERROR FROM DATABASE, exception = " + e.getMessage());
      throw new XMLBuilderException(e.getMessage());
    }
    if (IntiroLog.d()) {
      IntiroLog.detail(WeekReport.class, WeekReport.class.getName() + ".loadWeekWithLatestSubmittedWeek(): oneWeekReport = " + oneWeekReport);
    }
    // If we have found a submitted report
    if (oneWeekReport != null && oneWeekReport.getRows() != null && oneWeekReport.getRows().size() > 0) {
      this.setRows(oneWeekReport.getRows());

      // Loop through the rows and set them to not saved in the database.
      for (int i = 0; i < getRows().size(); i++) {
        Row oneRow = getRow(i);
        oneRow.setRowEntryId(-1);
      }
    }

    // calculate totalrow from rows.
    getSumRows().calcSum(getRows());
    calculateDaysStatus();
  }

  public void reject() throws XMLBuilderException {
    try {
      DbExecuteInterface proxy = DBExecute.getProxy();
      proxy.updateSubmitInWeek(getWeekReportId(), false);
      proxy.updateApprovedInWeek(weekReportId, false);

      // Subtract the overtime hours from users profile.
      getUserProfile().subtractFromOvertimeVacationHours(getOvertimeVacationHoursForWeek());
      getUserProfile().subtractFromOvertimeMoneyHours(getOvertimeMoneyHoursForWeek());
      getUserProfile().save();
      handleComment();
    } catch (Exception e) {
      IntiroLog.error(WeekReport.class, WeekReport.class.getName() + ".load(String weekId): ERROR FROM DATABASE, exception = " + e.getMessage());
      throw new XMLBuilderException(e.getMessage());
    }
  }

  public void removeRow(int index) {
    getRow(index).setRemoved();
  }

  public void save() throws XMLBuilderException {

    /* If we dont have a userweek we have to make one. */
    if (getWeekReportId() == -1) {
      try {
        DBQueriesInterface proxy = DBQueries.getProxy();
        // CALENDAR WEEK
        CalendarWeekVO rsCalendarWeek = proxy.getCalendarWeek(getFromDate().getCalendarInStoreFormat());
        String calendarWeekId = "-1";

        if (rsCalendarWeek != null) {
          calendarWeekId = String.valueOf(rsCalendarWeek.getId());
        } else {
          throw new XMLBuilderException(WeekReport.class.getName() + ".save(): Could not find a CalendarWeekId");
        }

        // WEEKCOMMENT
        if (getWeekComment().length() != 0) {
          StringRecordset rsWeekComment = proxy.makeNewCommmentAndRetriveTheId(getWeekComment());

          if (!rsWeekComment.getEOF()) {
            setWeekCommentId(Integer.parseInt(rsWeekComment.getField("maxId")));
          } else {
            throw new XMLBuilderException(WeekReport.class.getName() + ".save(): Could not make and find a new week comment");
          }
        }
        // USER WEEK
        StringRecordset rsUserWeekId = proxy.makeNewUserWeekEntryAndRetriveTheId(calendarWeekId, getWeekCommentId());

        if (!rsUserWeekId.getEOF()) {
          setWeekReportId(Integer.parseInt(rsUserWeekId.getField("maxId")));
        } else {
          throw new XMLBuilderException(WeekReport.class.getName() + ".save(): Could not make and find a new UserWeekId");
        }
      } catch (Exception e) {
        IntiroLog.error(WeekReport.class, WeekReport.class.getName() + ".save(String weekId): ERROR FROM DATABASE, exception = " + e.getMessage());
        throw new XMLBuilderException(e.getMessage());
      }
    } else { // UPDATE WEEKREPORT.
      handleComment();
    }

    Row oneRow;

    for (int i = 0; i < rows.size(); i++) {
      oneRow = rows.get(i);

      if (oneRow != null) {
        oneRow.save(getWeekReportId());
      }
    }

    setSaved();
  }

  /**
   * If we have a default comment with commmentid = 1. Instead of updating the comment, we have to make a new one and connect it to this
   * weekreport.
   */
  private void handleComment() throws XMLBuilderException {
    try {

      DbExecuteInterface proxy = DBExecute.getProxy();
      if (getWeekCommentId() == 1 && getWeekComment().length() > 0) {
        StringRecordset rsWeekComment = DBQueries.getProxy().makeNewCommmentAndRetriveTheId(getWeekComment());
        if (!rsWeekComment.getEOF()) {
          setWeekCommentId(Integer.parseInt(rsWeekComment.getField("maxId")));
        } else {
          throw new XMLBuilderException(WeekReport.class.getName() + ".save(): Could not make and find a new week comment");
        }
        // Update weekreport with the new comment.
        proxy.updateUserWeekComment(getWeekReportId(), getWeekCommentId());
      } else {// else update the comment.
        proxy.updateComment(getWeekCommentId(), getWeekComment());
      }
    } catch (Exception e) {
      IntiroLog.error(WeekReport.class, WeekReport.class.getName() + ".save(String weekId): ERROR FROM DATABASE, exception = " + e.getMessage());
      throw new XMLBuilderException(e.getMessage());
    }
  }

  public boolean saveErrorOccurred() {
    return saveError;
  }

  public void submit() throws XMLBuilderException {
    if (!submitHasBeenChecked) {
      throw new XMLBuilderException(WeekReport.class.getName() + ".submit(): Submit has not been check. A developer failure.");
    }

    save();

    if (getWeekReportId() != -1) {
      DbExecuteInterface proxy = DBExecute.getProxy();
      try {
        proxy.updateSubmitInWeek(getWeekReportId(), true);
        proxy.updateApprovedInWeek(weekReportId, false);

        // Save overtime hours to users profile.
        getUserProfile().addToOvertimeVacationHours(getOvertimeVacationHoursForWeek());
        getUserProfile().addToOvertimeMoneyHours(getOvertimeMoneyHoursForWeek());
        getUserProfile().save();
      } catch (Exception e) {
        IntiroLog.error(WeekReport.class, WeekReport.class.getName() + ".load(String weekId): ERROR FROM DATABASE, exception = " + e.getMessage());
        throw new XMLBuilderException(e.getMessage());
      }

      setSubmittedFromUser();
    }
  }

  public boolean submitErrorOccurred() {
    return submitError;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("rows in weekreport = ").append(rows.size());
    sb.append(", fromDate = ").append(fromDate.getCalendarInStoreFormat());
    sb.append(", toDate = ").append(toDate.getCalendarInStoreFormat());
    sb.append(", fromDate.getWeekOfYear = ").append(fromDate.getWeekOfYear());
    sb.append(", fromDate.weekPart = ").append(fromDate.getWeekPart());

    return sb.toString();
  }

  @Override
  public void toXML(StringBuffer xmlDoc) throws Exception {
    if (IntiroLog.d()) {
      IntiroLog.detail(WeekReport.class, WeekReport.class.getName() + ".toXML(): Entering");
    }

    XMLBuilder builder = new XMLBuilder();

    // Get start of document
    builder.getStartOfDocument(xmlDoc);

    // user name
    xmlDoc.append(XML_USERNAME_START);
    xmlDoc.append(getUserProfile().getFirstName()).append(" ").append(getUserProfile().getLastName());
    xmlDoc.append(XML_USERNAME_END);

    // mode
    xmlDoc.append(XML_MODE_START);
    xmlDoc.append(mode);
    xmlDoc.append(XML_MODE_END);

    // Title
    xmlDoc.append(XML_TITLE_START);

    // Now the same xsl is used by two different modes, approve and view
    if (mode.equalsIgnoreCase("approve")) {
      xmlDoc.append("Approve");
    } else {
      xmlDoc.append("View");
    }

    xmlDoc.append(XML_TITLE_END);

    /* weekno */
    xmlDoc.append(XML_WEEKNO_START);
    xmlDoc.append(fromDate.getWeekOfYear());
    xmlDoc.append(XML_WEEKNO_END);

    /* week part */
    xmlDoc.append(XML_WEEKPART_START);
    xmlDoc.append(fromDate.getWeekPart());
    xmlDoc.append(XML_WEEKPART_END);

    /* year */
    xmlDoc.append(XML_YEAR_START);
    xmlDoc.append(getFromDate().getYear());
    xmlDoc.append(XML_YEAR_END);

    /* month */
    xmlDoc.append(XML_MONTH_START);
    xmlDoc.append(getFromDate().getMonthNameShort());
    xmlDoc.append(XML_MONTH_END);

    /* date */
    xmlDoc.append(XML_DATE_START);
    xmlDoc.append(fromDate.getCalendarInStoreFormat()).append("-").append(toDate.getCalendarInStoreFormat());
    xmlDoc.append(XML_DATE_END);

    /* weekcomment */
    xmlDoc.append(XML_WEEKCOMMENT_START);
    xmlDoc.append(getWeekComment());
    xmlDoc.append(XML_WEEKCOMMENT_END);

    /* This can happen on SaveSubmitWeekReport */
    if (submitErrorOccurred()) {

      // else if(submitErrorOccurred() ) {
      double expectedHours = getSumRows().getExpectedRow().getRowSum();
      double normalHours = getSumRows().getTotalRow().getNormalHours();

      /* expected hours */
      xmlDoc.append(XML_EXPECTED_HOURS_START);
      xmlDoc.append(NumberFormatter.format(expectedHours, 2, true));
      xmlDoc.append(XML_EXPECTED_HOURS_END);

      /* normal hours */
      xmlDoc.append(XML_NORMAL_HOURS_START);
      xmlDoc.append(NumberFormatter.format(normalHours, 2, true));
      xmlDoc.append(XML_NORMAL_HOURS_END);
    } /* This is happening on WeekReportEditorView */ else {

      // IntiroLog.detail(WeekReport.class.getName()+".toXML(): Before EditRow.toXML()");

      /* Edit row */
      editRow.toXML(xmlDoc);

      // IntiroLog.detail(WeekReport.class.getName()+".toXML(): After EditRow.toXML()");

      /* Rows in week */
      Row oneRow;

      /* Loop through all rows in combobox */
      for (int i = 0; i < rows.size(); i++) {
        oneRow = rows.get(i);

        if (oneRow != null) {
          oneRow.toXML(xmlDoc, i);
        }
      }

      // IntiroLog.detail(WeekReport.class.getName()+".toXML(): Before sumRows.toXML()");
      // IntiroLog.detail(WeekReport.class.getName()+".toXML(): sumRows =" + sumRows);

      /* summary rows */
      sumRows.toXML(xmlDoc);

      // IntiroLog.detail(WeekReport.class.getName()+".toXML(): After sumRow.toXML()");
    }

    /* Get end of document */
    builder.getEndOfDocument(xmlDoc);

    if (IntiroLog.d()) {
      IntiroLog.detail(WeekReport.class, WeekReport.class.getName() + ".toXML(): xmlDoc = " + xmlDoc.toString());
    }
  }

  public void toXMLSummary(StringBuffer xmlDoc, int weekIndex) throws Exception {
    if (IntiroLog.d()) {
      IntiroLog.detail(WeekReport.class, WeekReport.class.getName() + ".toXMLSummary(): Entering");
    }

    /* start of week */
    xmlDoc.append(XML_WEEK_START);

    // user name
    xmlDoc.append(XML_USERNAME_START);
    xmlDoc.append(getUserProfile().getFirstName()).append(" ").append(getUserProfile().getLastName());
    xmlDoc.append(XML_USERNAME_END);

    // mode
    xmlDoc.append(XML_MODE_START);
    xmlDoc.append(mode);
    xmlDoc.append(XML_MODE_END);

    // weekno
    xmlDoc.append(XML_WEEKNO_START);
    xmlDoc.append(fromDate.getWeekOfYear());
    xmlDoc.append(XML_WEEKNO_END);

    // week part
    xmlDoc.append(XML_WEEKPART_START);
    xmlDoc.append(fromDate.getWeekPart());
    xmlDoc.append(XML_WEEKPART_END);

    // index
    xmlDoc.append(XML_WEEKINDEX_START);
    xmlDoc.append(weekIndex);
    xmlDoc.append(XML_WEEKINDEX_END);

    // weekcomment
    xmlDoc.append(XML_WEEKCOMMENT_START);
    xmlDoc.append(getWeekComment());
    xmlDoc.append(XML_WEEKCOMMENT_END);

    // summary rows
    sumRows.toXMLSummary(xmlDoc);

    // end of week
    xmlDoc.append(XML_WEEK_END);

    if (IntiroLog.d()) {
      IntiroLog.detail(WeekReport.class, WeekReport.class.getName() + ".toXMLSummary(): xmlDoc = " + xmlDoc.toString());
    }
  }

  protected void setSumRows(SumRows sumRows) {
    this.sumRows = sumRows;
  }

  /**
   * Loops throw all rows in the weekreport and calls calculateDaysStatus on the rows. Makes it possible for the XSL to know what days to
   * display and witch to hide.
   */
  protected void calculateDaysStatus() {
    Row oneRow;

    for (int i = 0; i < rows.size(); i++) {
      oneRow = rows.get(i);

      if (oneRow != null) {
        oneRow.calculateDayStatus(getSumRows());
      }
    }
  }

  void setLoaded() {
    isLoaded = true;
  }

  /**
   * Get overtime hours for week, that is taken out in money.
   */
  double getOvertimeMoneyHoursForWeek() {
    return getSumRows().getTotalRow().getOvertimeMoneyHoursForWeek();
  }

  /**
   * Get overtime hours for week, that is taken out in vacation.
   */
  double getOvertimeVacationHoursForWeek() {
    return getSumRows().getTotalRow().getOvertimeVacationHoursForWeek();
  }

  /**
   * Set rows.
   */
  void setRows(ArrayList<Row> rows) {
    if (IntiroLog.d()) {
      IntiroLog.detail(WeekReport.class, WeekReport.class.getName() + ".setRows(ArrayList rows): Entering");
    }

    this.rows = rows;
  }

  /**
   * set save error occurred.
   */
  void setSaveErrorOccrred() {
    saveError = true;
  }

  /**
   * set saved.
   */
  void setSaved() {
    isSaved = true;
  }

  /**
   * set submitted from DB.
   */
  void setSubmittedFromDB(String status) {
    if (status.equalsIgnoreCase("true") || status.equalsIgnoreCase("1")) {
      isSubmittedFromDB = true;
    }
  }

  /**
   * set submitted.
   */
  void setSubmittedFromUser() {
    isSubmittedFromUser = true;
  }

  /**
   * @return Returns the mode.
   */
  public String getMode() {
    return mode;
  }

  /**
   * @param mode The mode to set.
   */
  public void setMode(String mode) {
    this.mode = mode;
  }
}
