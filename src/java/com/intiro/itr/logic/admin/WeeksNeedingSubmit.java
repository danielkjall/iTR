package com.intiro.itr.logic.admin;

import java.util.ArrayList;

import com.intiro.itr.db.DBConstants;
import com.intiro.itr.db.DBQueries;
import com.intiro.itr.logic.weekreport.WeekReport;
import com.intiro.itr.util.ITRCalendar;
import com.intiro.itr.util.StringRecordset;
import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.itr.util.xml.DynamicXMLCarrier;
import com.intiro.itr.util.xml.XMLBuilder;
import com.intiro.itr.util.xml.XMLBuilderException;
import com.intiro.itr.util.log.IntiroLog;

public class WeeksNeedingSubmit extends DynamicXMLCarrier {

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
  public WeeksNeedingSubmit(UserProfile profile, String mode) throws XMLBuilderException {
    super(profile);
    weekReports = new ArrayList<>();
    this.mode = mode;
  }

  /**
   * Load the weekreports for users.
   *
   * @param year
   * @param periodType
   * @return
   * @throws com.intiro.itr.util.xml.XMLBuilderException
   */
  public ArrayList load(String year, int periodType) throws XMLBuilderException {
    ArrayList<String> allUsers = new ArrayList<>();
    String sDate;
    sDate = getEndPeriod(year, periodType);
    try {
      StringRecordset rs = DBQueries.getProxy().getUsers(true);
      String userId = null;

      while (!rs.getEOF()) {
        userId = rs.getField("Id");
        allUsers.add(userId);
        rs.moveNext();
      }

      rs.close();
    } catch (Exception e) {
      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".load(): ERROR FROM DATABASE, exception = " + e.getMessage());
      }

      throw new XMLBuilderException(e.getMessage());
    }

    UserProfile profile;

    for (int i = 0; i < allUsers.size(); i++) {
      String userId = allUsers.get(i);
      profile = new UserProfile();
      profile.load(userId);
      profile.setClientInfo(getUserProfile().getClientInfo());

      try {
        StringRecordset rs = DBQueries.getProxy().getWeeksNeedingSubmit(userId, profile.getActivatedDate(), year, sDate);
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

        //Snurra igenom veckor som inte paborjats, och lagg in har.
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

  private String getEndPeriod(String year, int periodType) {
    java.util.Calendar cDate = java.util.Calendar.getInstance();
    String sDate;
    String sMonth;
    String sYear;
    String sDay;

    if (Integer.parseInt(year) == cDate.get(java.util.Calendar.YEAR)) {
      if (periodType == 2) {
        int dd = cDate.get(java.util.Calendar.DAY_OF_WEEK) - 2;

        if (dd == -1) //Sondag
        {
          dd = 6;
        }

        if (dd > 0) {
          cDate.add(java.util.Calendar.DATE, -dd);
        }
        sDay = twoDigits(cDate.get(java.util.Calendar.DATE));
      } else {
        sDay = "01";
      }

      sMonth = twoDigits(cDate.get(java.util.Calendar.MONTH) + 1);
      sYear = Integer.toString(cDate.get(java.util.Calendar.YEAR));

      sDate = sYear + "-" + sMonth + "-" + sDay;

    } else {
      //Lagg till ett ar, och ta 1/1. Vill man se ar 2007, skall man ha det som ar fram till 2008-01-01
      sDate = Integer.parseInt(year) + 1 + "-01-01";
    }

    return sDate;
  }

  private String twoDigits(int num) {
    String sTemp = "0" + Integer.toString(num);
    sTemp = sTemp.substring(sTemp.length() - 2, sTemp.length());
    return sTemp;
  }

}
