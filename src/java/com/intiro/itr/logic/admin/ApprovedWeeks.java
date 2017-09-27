/**
 * Title:         ITR
 * Description:
 * Copyright:     Copyright (c) 2001
 * Company:       Intiro Development AB
 * @author        Daniel Kjall
 * @version       1.0
 */
package com.intiro.itr.logic.admin;

import java.util.Vector;

import com.intiro.itr.db.DBConstants;
import com.intiro.itr.logic.weekreport.WeekReport;
import com.intiro.itr.util.ITRCalendar;
import com.intiro.itr.util.StringRecordset;
import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.itr.util.xml.DynamicXMLCarrier;
import com.intiro.itr.util.xml.XMLBuilder;
import com.intiro.itr.util.xml.XMLBuilderException;
import com.intiro.toolbox.log.IntiroLog;

public class ApprovedWeeks extends DynamicXMLCarrier {
  // ~ Instance/static variables ........................................................................................

  static final String XML_MODE_END = "</mode>";

  static final String XML_MODE_START = "<mode>";

  String mode = "";

  // week reports
  Vector<WeekReport> weekReports = new Vector<WeekReport>();

  // ~ Constructors .....................................................................................................

  /**
   * Constructor I for Weeks.
   * 
   * @param profile
   *          the UserProfile for the current user.
   * @exception XMLBuilderException
   *              if something goes wrong.
   */
  public ApprovedWeeks(UserProfile profile, String mode) throws XMLBuilderException {
    super(profile);
    weekReports = new Vector<WeekReport>();
    this.mode = mode;
  }

  // ~ Methods ..........................................................................................................

  /**
   * Load the weekreports for users.
   */
  //public Vector load() throws XMLBuilderException {
  //return load("", "", null);
  //}
  
  public Vector load(String year, String userId ) throws XMLBuilderException {

      Vector<String> allUsers = new Vector<String>();
    if(userId.length() > 0){
        allUsers.add(userId);
    }
    else
    {    
    try {
        StringRecordset rs = dbQuery.getUsersReportedYear(year);
        while (!rs.getEOF()) {
             allUsers.add(rs.getField("Id"));
            rs.moveNext();
        }   
    }
    catch (Exception e) {
        if (IntiroLog.d()) {
          IntiroLog.detail(getClass(), getClass().getName() + ".load(): ERROR FROM DATABASE, exception = " + e.getMessage());
        }
        throw new XMLBuilderException(e.getMessage());
      }
    }
    
    UserProfile profile = new UserProfile();

    for (int i = 0; i < allUsers.size(); i++) {
    //for (int i = 0; i < 1; i++) {
      userId = allUsers.get(i);
      profile = new UserProfile();
      profile.load(userId);
      profile.setClientInfo(getUserProfile().getClientInfo());

      try {
        StringRecordset rs = dbQuery.getApprovedWeeks(userId, year);
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
   */
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
    WeekReport oneWeekReport = null;

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
  /**
   * @return Returns the mode.
   */
  public String getMode() {
    return mode;
  }
  /**
   * @return Returns the weekReports.
   */
  public Vector<WeekReport> getWeekReports() {
    return weekReports;
  }
}