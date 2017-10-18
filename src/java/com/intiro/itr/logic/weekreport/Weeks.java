package com.intiro.itr.logic.weekreport;

import com.intiro.itr.db.DBConstants;
import com.intiro.itr.db.DBQueriesUser;
import com.intiro.itr.db.InvocationHandlerSetting;
import com.intiro.itr.util.ITRCalendar;
import com.intiro.itr.util.StringRecordset;
import com.intiro.itr.util.personalization.*;
import com.intiro.itr.util.xml.*;
import com.intiro.itr.util.log.IntiroLog;
import java.util.*;

public class Weeks extends DynamicXMLCarrier {

  static final String XML_MODE_END = "</mode>";
  static final String XML_MODE_START = "<mode>";
  static final String XML_TITLE_END = "</title>";
  static final String XML_TITLE_START = "<title>";
  static final String XML_USERNAME_END = "</username>";
  // Week
  static final String XML_USERNAME_START = "<username>";
  String mode = "";
  String title = "Title not set";
  // week reports
  ArrayList<WeekReport> weekReports = new ArrayList<>();

  /**
   * Constructor I for Weeks.
   *
   * @param profile the UserProfile for the current user.
   * @param mode
   * @exception XMLBuilderException if something goes wrong.
   */
  public Weeks(UserProfile profile, String mode) throws XMLBuilderException {
    super(profile);
    weekReports = new ArrayList<>();
    this.mode = mode;
  }

  /**
   * Loads the weeks with depending on the mode that is set.
   *
   * @param year
   * @return
   * @throws com.intiro.itr.util.xml.XMLBuilderException
   */
  public ArrayList load(String year) throws XMLBuilderException {
    ArrayList retval = null;

    if (mode.equalsIgnoreCase("submitted")) {
      retval = loadSubmitted(year);
    } else if (mode.equalsIgnoreCase("todo")) {
      retval = loadTodoPerformance();
    } else {
      retval = loadFuture();
    }

    return retval;
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

    /* Get start of document */
    builder.getStartOfDocument(xmlDoc);

    /* Title */
    xmlDoc.append(XML_TITLE_START);
    xmlDoc.append(title);
    xmlDoc.append(XML_TITLE_END);

    /* Title */
    xmlDoc.append(XML_MODE_START);
    xmlDoc.append(mode);
    xmlDoc.append(XML_MODE_END);

    /* User name */
    xmlDoc.append(XML_USERNAME_START);
    xmlDoc.append(getUserProfile().getFirstName()).append(" ").append(getUserProfile().getLastName());
    xmlDoc.append(XML_USERNAME_END);

    /* week reports */
    WeekReport oneWeekReport = null;

    /* Loop through all weekReports in combobox */
    for (int i = 0; i < weekReports.size(); i++) {
      oneWeekReport = weekReports.get(i);

      if (oneWeekReport != null) {
        oneWeekReport.toXMLSummary(xmlDoc, i);
      }
    }

    /* Get end of document */
    builder.getEndOfDocument(xmlDoc);

    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".toXML(): xmlDoc = " + xmlDoc.toString());
    }
  }

  /**
   * Loads week six month in to the future.
   */
  private ArrayList loadFuture() throws XMLBuilderException {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".loadFuture(): Entering");
    }

    title = "Future";

    ITRCalendar now = new ITRCalendar();

    /* move forward to next weekpart */
    now.nextWeekPart();

    ITRCalendar nowPlusSixMonth = new ITRCalendar();
    nowPlusSixMonth.nextMonth();
    nowPlusSixMonth.nextMonth();
    nowPlusSixMonth.nextMonth();
    nowPlusSixMonth.nextMonth();
    nowPlusSixMonth.nextMonth();
    nowPlusSixMonth.nextMonth();

    ITRCalendar currentDate = now.cloneCalendar();

    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".loadFuture(): now = " + now);
    }
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".loadFuture(): currentDate = " + currentDate);
    }
    /* First create all weeks between now and nowPlusSixMonth. */
    while (true) {
      WeekReport oneWeekReport = new WeekReport(getUserProfile(), currentDate.getFromDateForWeekPart(), currentDate.getToDateForWeekPart(), "Edit");
      oneWeekReport.load();

      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".loadFuture(): weekreport to be added = " + oneWeekReport);
      }
      if (!oneWeekReport.isSubmittedFromDB()) {
        weekReports.add(oneWeekReport);
      }

      /* next weekpart, it rolls to next week if needed. */
      currentDate.nextWeekPart();

      /* break loop if when have passed nowPlusSixMonth. */
      if (nowPlusSixMonth.before(currentDate)) {
        break;
      }
    }

    return weekReports;
  }

  /**
   * Loads submitted weeks.
   */
  private ArrayList loadSubmitted() throws XMLBuilderException {
    return loadSubmitted("");
  }

  /**
   * Loads submitted weeks.
   */
  private ArrayList loadSubmitted(String year) throws XMLBuilderException {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".loadSubmitted()(): Submitted");
    }

    title = "Submitted";

    try {
      String cacheKey = getClass().getName() + ".getSubmittedWeeksThick_" + getUserProfile().getUserId() + "_" + year;
      String statisticKey = getClass().getName() + ".loadSubmitted";
      int cacheTime = 3600*10;
      InvocationHandlerSetting s = InvocationHandlerSetting.create(cacheKey, cacheTime, statisticKey);
      StringRecordset rs = DBQueriesUser.getProxy(s).getSubmittedWeeksThick(getUserProfile().getUserId(), year);
      int lastCalendarWeekId = -1;

      while (!rs.getEOF()) {
        int calendarWeekId = Integer.parseInt(rs.getField(DBConstants.CALENDARWEEK_ID_PK));
        //int calendarWeekId = Integer.parseInt(rs.getField("itr_userweekid"));

        /* Only make a new WeekReport if we have a new week. */
        if (calendarWeekId != lastCalendarWeekId) {
          lastCalendarWeekId = calendarWeekId;

          ITRCalendar fromDate = new ITRCalendar(rs.getField(DBConstants.CALENDARWEEK_FROM_DATE));
          ITRCalendar toDate = new ITRCalendar(rs.getField(DBConstants.CALENDARWEEK_TO_DATE));
          WeekReport oneWeekReport = new WeekReport(getUserProfile(), fromDate, toDate, "View");
          oneWeekReport.load(rs, lastCalendarWeekId);
          //oneWeekReport.load(rs, calendarWeekId);

          /* Add weekreport to week reports */
          weekReports.add(oneWeekReport);
        }

        //rs.moveNext();
      }

      rs.close();

      return weekReports;
    } catch (Exception e) {
      IntiroLog.error(getClass(), getClass().getName() + ".loadSubmitted(): ERROR FROM DATABASE, exception = " + e.getMessage());
      throw new XMLBuilderException(e.getMessage());
    }
  }

  /**
   * Loads week that needs to be done.
   */
  private ArrayList loadTodo() throws XMLBuilderException {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".loadTodo(): Entering");
    }
    ITRCalendar now = new ITRCalendar();
    ITRCalendar usersActivationDate = getUserProfile().getActivatedDate();
    ITRCalendar currentDate = usersActivationDate.cloneCalendar();

    /* First create all weeks between activation and now. */
    while (true) {
      WeekReport oneWeekReport = new WeekReport(getUserProfile(), currentDate.getFromDateForWeekPart(), currentDate.getToDateForWeekPart(), "Edit");
      oneWeekReport.load();

      /* Only add weekreports that have not been submitted */
      if (!oneWeekReport.isSubmittedFromDB()) {
        weekReports.add(oneWeekReport);
      }

      /* next weekpart, it rolls to next week if needed. */
      currentDate.nextWeekPart();

      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".loadTodo(): currentDate = " + currentDate);
      }
      /* break loop if when have passed now. */
      if (now.before(currentDate)) {
        break;
      }
    }

    return weekReports;
  }

  private ArrayList loadTodoPerformance() throws XMLBuilderException {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".loadTodoPerformance(): Entering");
    }
    ITRCalendar now = new ITRCalendar();
    ITRCalendar usersActivationDate = getUserProfile().getActivatedDate();
    ITRCalendar currentDate = usersActivationDate.cloneCalendar();
    
    // Gå aldrig tillbaka mer än 2 år:
    ITRCalendar twoYearsAgo = new ITRCalendar();
    twoYearsAgo.roll(Calendar.YEAR, -2);
    if(twoYearsAgo.after(currentDate)) {
      currentDate = twoYearsAgo;
    }

    WeekReport temp = new WeekReport(getUserProfile(), currentDate.getFromDateForWeekPart(), currentDate.getToDateForWeekPart(), "Edit");
    Map<String, String> alreadySubmittedWeeksHash = temp.alreadySubmittedWeeksAsHashmap(getUserProfile().getUserId());

    /* First create all weeks between activation and now. */
    while (true) {
      WeekReport oneWeekReport = new WeekReport(getUserProfile(), currentDate.getFromDateForWeekPart(), currentDate.getToDateForWeekPart(), "Edit");

      String dateStr = currentDate.getCalendarInStoreFormat() + " 00:00:00.0";
      boolean alreadySubmitted = alreadySubmittedWeeksHash.containsKey(dateStr);
      if (alreadySubmitted == false) {
        oneWeekReport.load();
      }

      /* Only add weekreports that have not been submitted */
      if (!oneWeekReport.isSubmittedFromDB() && alreadySubmitted == false) {
        weekReports.add(oneWeekReport);
      }

      /* next weekpart, it rolls to next week if needed. */
      currentDate.nextWeekPart();

      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".loadTodoPerformance(): currentDate = " + currentDate);
      }
      /* break loop if we have passed today. */
      if (now.before(currentDate)) {
        break;
      }
    }

    return weekReports;
  }

  /**
   * @return Returns the weekReports.
   */
  public ArrayList<WeekReport> getWeekReports() {
    return weekReports;
  }
}
