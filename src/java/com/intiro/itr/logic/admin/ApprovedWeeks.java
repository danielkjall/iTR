package com.intiro.itr.logic.admin;

import java.util.ArrayList;
import com.intiro.itr.db.DBConstants;
import com.intiro.itr.db.DBQueries;
import com.intiro.itr.db.DBQueriesInterface;
import com.intiro.itr.logic.weekreport.WeekReport;
import com.intiro.itr.util.ITRCalendar;
import com.intiro.itr.util.StopWatch;
import com.intiro.itr.util.StringRecordset;
import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.itr.util.xml.DynamicXMLCarrier;
import com.intiro.itr.util.xml.XMLBuilder;
import com.intiro.itr.util.xml.XMLBuilderException;
import com.intiro.itr.util.log.IntiroLog;
import java.util.Map;

public class ApprovedWeeks extends DynamicXMLCarrier {

  static final String XML_MODE_END = "</mode>";
  static final String XML_MODE_START = "<mode>";
  String mode = "";
  ArrayList<WeekReport> weekReports = new ArrayList<>();

  /**
   * Constructor I for Weeks.
   *
   * @param profile the UserProfile for the current user.
   * @param mode
   * @exception XMLBuilderException if something goes wrong.
   */
  public ApprovedWeeks(UserProfile profile, String mode) throws XMLBuilderException {
    super(profile);
    weekReports = new ArrayList<>();
    this.mode = mode;
  }

  /**
   * Load the weekreports for users.
   *
   * @param year
   * @return
   * @throws com.intiro.itr.util.xml.XMLBuilderException
   */
  public ArrayList loadPerformance(String year) throws XMLBuilderException {
    try {
      Map<String, UserProfile> mapUserProfiles = UserProfile.loadAllUserProfiles();

      StringRecordset rs = DBQueries.getProxy().getAllApprovedWeeks(year);
      int lastCalendarWeekId = -1;

      StopWatch sw = new StopWatch();
      sw.startTiming();

      while (!rs.getEOF()) {
        int calendarWeekId = Integer.parseInt(rs.getField(DBConstants.CALENDARWEEK_ID_PK));

        // Only make a new WeekReport if we have a new week.
        if (calendarWeekId != lastCalendarWeekId) {
          lastCalendarWeekId = calendarWeekId;

          ITRCalendar fromDate = new ITRCalendar(rs.getField(DBConstants.CALENDARWEEK_FROM_DATE));
          ITRCalendar toDate = new ITRCalendar(rs.getField(DBConstants.CALENDARWEEK_TO_DATE));
          String userId = rs.getField("ITR_UserId");
          WeekReport oneWeekReport = new WeekReport(mapUserProfiles.get(userId), fromDate, toDate, "Approve");
          oneWeekReport.load();
          //WeekReport.loadAllApproved();

          // Add weekreport to week reports
          weekReports.add(oneWeekReport);
        }

        rs.moveNext();
      }
      IntiroLog.info(ApprovedWeeks.class, "load: " + sw.stopTiming());

      rs.close();
    } catch (Exception e) {
      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".load(): ERROR FROM DATABASE, exception = " + e.getMessage());
      }

      throw new XMLBuilderException(e.getMessage());
    }
    return weekReports;
  }

  public ArrayList load(String year, String userId) throws XMLBuilderException {

    ArrayList<String> allUsers = new ArrayList<>();
    DBQueriesInterface proxy = DBQueries.getProxy();
    if (userId.length() > 0) {
      allUsers.add(userId);
    } else {
      try {
        StringRecordset rs = proxy.getUsersReportedYear(year);
        while (!rs.getEOF()) {
          allUsers.add(rs.getField("Id"));
          rs.moveNext();
        }
      } catch (Exception e) {
        if (IntiroLog.d()) {
          IntiroLog.detail(getClass(), getClass().getName() + ".load(): ERROR FROM DATABASE, exception = " + e.getMessage());
        }
        throw new XMLBuilderException(e.getMessage());
      }
    }

    UserProfile profile;

    for (int i = 0; i < allUsers.size(); i++) {
      //for (int i = 0; i < 1; i++) {
      userId = allUsers.get(i);
      profile = new UserProfile();
      profile.load(userId);
      profile.setClientInfo(getUserProfile().getClientInfo());

      try {
        StringRecordset rs = proxy.getApprovedWeeks(userId, year);
        int lastCalendarWeekId = -1;

        while (!rs.getEOF()) {
          int calendarWeekId = Integer.parseInt(rs.getField(DBConstants.CALENDARWEEK_ID_PK));

          // Only make a new WeekReport if we have a new week.
          if (calendarWeekId != lastCalendarWeekId) {
            lastCalendarWeekId = calendarWeekId;

            ITRCalendar fromDate = new ITRCalendar(rs.getField(DBConstants.CALENDARWEEK_FROM_DATE));
            ITRCalendar toDate = new ITRCalendar(rs.getField(DBConstants.CALENDARWEEK_TO_DATE));
            WeekReport oneWeekReport = new WeekReport(profile, fromDate, toDate, "Approve");
            oneWeekReport.load();

            // Add weekreport to week reports
            weekReports.add(oneWeekReport);
          }

          rs.moveNext();
        }

        rs.close();
      } catch (Exception e) {
        if (IntiroLog.d()) {
          IntiroLog.detail(getClass(), getClass().getName() + ".load(): ERROR FROM DATABASE, exception = " + e.getMessage());
        }

        throw new XMLBuilderException(e.getMessage());
      }
    }

    return weekReports;
  }

  /**
   * Make xml of weeks.
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

    // mode
    xmlDoc.append(XML_MODE_START);
    xmlDoc.append(mode);
    xmlDoc.append(XML_MODE_END);

    // week reports
    WeekReport oneWeekReport;

    // Loop through all weekReports in combobox
    for (int i = 0; i < weekReports.size(); i++) {
      oneWeekReport = weekReports.get(i);

      if (oneWeekReport != null) {
        oneWeekReport.toXMLSummary(xmlDoc, i);
      }
    }

    // Get end of document
    builder.getEndOfDocument(xmlDoc);

    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".toXML(): xmlDoc = " + xmlDoc.toString());
    }
  }

  public String getMode() {
    return mode;
  }

  public ArrayList<WeekReport> getWeekReports() {
    return weekReports;
  }
}
