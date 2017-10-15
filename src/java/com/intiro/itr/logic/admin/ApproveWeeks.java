package com.intiro.itr.logic.admin;


import com.intiro.itr.db.DBConstants;
import com.intiro.itr.db.DBQueriesAdmin;
import com.intiro.itr.db.DBQueriesConfig;
import com.intiro.itr.db.DBQueriesUser;
import com.intiro.itr.db.InvocationHandlerSetting;
import com.intiro.itr.logic.weekreport.WeekReport;
import com.intiro.itr.util.*;
import com.intiro.itr.util.personalization.*;
import com.intiro.itr.util.xml.*;
import com.intiro.itr.util.log.IntiroLog;
import java.util.*;

public class ApproveWeeks extends DynamicXMLCarrier {

  static final String XML_MODE_END = "</mode>";
  static final String XML_MODE_START = "<mode>";
  String mode = "";

  //week reports
  ArrayList<WeekReport> weekReports = new ArrayList<>();

  /**
   * Constructor I for Weeks.
   *
   * @param profile the UserProfile for the current user.
   * @param mode
   * @exception XMLBuilderException if something goes wrong.
   */
  public ApproveWeeks(UserProfile profile, String mode)
          throws XMLBuilderException {
    super(profile);
    weekReports = new ArrayList<>();
    this.mode = mode;
  }

  /**
   * Load the weekreports for users.
   *
   * @return
   * @throws com.intiro.itr.util.xml.XMLBuilderException
   */
  public ArrayList load() throws XMLBuilderException {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".load(): Submitted");
    }

    ArrayList<String> usersThatNeedApprovel = new ArrayList<>();
    String previousUserId = "-1";

    try {
      String key = getUserProfile().getUserId();
      //String cacheKey = getClass().getName() + ".getUsersThatNeedApprovel_" + key;
      String statisticKey = getClass().getName() + ".getUsersThatNeedApprovel";
      //int cacheTime = 3600 * 10;
      InvocationHandlerSetting s = InvocationHandlerSetting.create(statisticKey);
      StringRecordset rs = DBQueriesAdmin.getProxy(s).getUsersThatNeedApprovel(key);
      String userId = null;

      while (!rs.getEOF()) {
        userId = rs.getField(DBConstants.ENTRYROW_USERID_FK);

        //Only add new users
        if (!previousUserId.equalsIgnoreCase(userId)) {
          usersThatNeedApprovel.add(userId); //Add userid to usersThatNeedApprovel
        }

        previousUserId = userId;
        rs.moveNext();
      }

      rs.close();
    } catch (Exception e) {
      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".load(): ERROR FROM DATABASE, exception = " + e.getMessage());
      }

      throw new XMLBuilderException(e.getMessage());
    }
    for (int i = 0; i < usersThatNeedApprovel.size(); i++) {
      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".load(): usersThatNeedApprovel.get(i) = " + usersThatNeedApprovel.get(i));
      }
    }

    UserProfile profile;

    for (int i = 0; i < usersThatNeedApprovel.size(); i++) {
      String userId = usersThatNeedApprovel.get(i);
      profile = new UserProfile();
      profile.load(userId);
      profile.setClientInfo(getUserProfile().getClientInfo());

      try {
        //String cacheKey = getClass().getName() + ".getSubmittedWeeks_" + userId + "_" + false + "_" + true;
        String statisticKey = getClass().getName() + ".getSubmittedWeeks";
        //int cacheTime = 3600 * 10;
        InvocationHandlerSetting s = InvocationHandlerSetting.create(statisticKey);
        StringRecordset rs = DBQueriesUser.getProxy(s).getSubmittedWeeks(userId, false, true);
        int lastCalendarWeekId = -1;

        while (!rs.getEOF()) {
          int calendarWeekId = Integer.parseInt(rs.getField(DBConstants.CALENDARWEEK_ID_PK));

          //Only make a new WeekReport if we have a new week.
          if (calendarWeekId != lastCalendarWeekId) {
            lastCalendarWeekId = calendarWeekId;

            ITRCalendar fromDate = new ITRCalendar(rs.getField(DBConstants.CALENDARWEEK_FROM_DATE));
            ITRCalendar toDate = new ITRCalendar(rs.getField(DBConstants.CALENDARWEEK_TO_DATE));
            WeekReport oneWeekReport = new WeekReport(profile, fromDate, toDate, "Approve");
            oneWeekReport.load();

            //Add weekreport to week reports
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

    //Get start of document
    builder.getStartOfDocument(xmlDoc);

    //mode
    xmlDoc.append(XML_MODE_START);
    xmlDoc.append(mode);
    xmlDoc.append(XML_MODE_END);

    //week reports
    WeekReport oneWeekReport = null;

    //Loop through all weekReports in combobox
    for (int i = 0; i < weekReports.size(); i++) {
      oneWeekReport = weekReports.get(i);

      if (oneWeekReport != null) {
        oneWeekReport.toXMLSummary(xmlDoc, i);
      }
    }

    //Get end of document
    builder.getEndOfDocument(xmlDoc);

    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".toXML(): xmlDoc = " + xmlDoc.toString());
    }
  }
}
