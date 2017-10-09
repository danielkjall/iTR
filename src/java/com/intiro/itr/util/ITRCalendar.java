package com.intiro.itr.util;

import com.intiro.itr.util.log.IntiroLog;
import java.text.SimpleDateFormat;

import java.util.*;

public class ITRCalendar extends GregorianCalendar {

  public final String DATE_SEPARATOR_LINE = "-";
  public final String DATE_SEPARATOR_SLASH = "/";
  protected int weekPart = 1;
  private ArrayList<ITRCalendar> datesInWeek = new ArrayList<ITRCalendar>();
  private ITRCalendar fromDate = null;
  private boolean isWeekSplit = false;
  private ITRCalendar toDate = null;

  /**
   * Constructor I for ITRCalendar.
   */
  public ITRCalendar() {
    super();

    String[] ids = TimeZone.getAvailableIDs(+1 * 60 * 60 * 1000);
    SimpleTimeZone ect = new SimpleTimeZone(+1 * 60 * 60 * 1000, ids[0]);
    setTimeZone(ect);
  }

  /**
   * Constructor II for ITRCalendar. This constructor calls loadCalendarWithDate(String loadString); The format of loadString must be:
   * [YEAR-MONTH-DATE] Ex. 2000-12-31
   *
   * @param loadString a String containing the recuired information.
   */
  public ITRCalendar(String loadString) {
    this();

    try {
      loadCalendarWithDate(loadString);
    } catch (Exception e) {
      IntiroLog.error(getClass(), getClass().getName() + ".constructor(String): Error when loading from string, ", e);
    }
  }
  //~ Methods ..........................................................................................................

  public static void test() {
    IntiroLog.getInstance().setLoggingLevel(6);
  }

  /**
   * Get Calendar date in store format. Format: [YEAR-MONTH-DATE] 20 december 2000 -> 2000-12-20
   */
  public String getCalendarInStoreFormat() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    return sdf.format(getTime());
  }

  //------------------------------------------ START CONVENIENT SET AND GET METHODS ----------------------------------------
  //DATE
  /**
   * Sets the date using Calendars method set(int field, int value).
   */
  public void setDate(int date) {
    set(Calendar.DATE, date);
  }

  /**
   * Gets the date using Calendars method get(int field).
   */
  public int getDate() {
    return get(Calendar.DATE);
  }

  /**
   * Get dates in week part.
   */
  public ArrayList getDatesInWeekPart() {

    //IntiroLog.detail(getClass().getName()+".getDatesInWeekPart(): Entering");
    ArrayList retval = null;
    retval = getDatesInWeek();

    return retval;
  }

  /**
   * Get the day name in the long version. Ex. Monday, Tuesday, .... Sunday.
   */
  public String getDayNameLong() {
    String retval = "Missing";
    int day = get(DAY_OF_WEEK);

    if (day == Calendar.MONDAY) {
      retval = "Monday";
    } else if (day == Calendar.TUESDAY) {
      retval = "Tuesday";
    } else if (day == Calendar.WEDNESDAY) {
      retval = "Wednesday";
    } else if (day == Calendar.THURSDAY) {
      retval = "Thursday";
    } else if (day == Calendar.FRIDAY) {
      retval = "Friday";
    } else if (day == Calendar.SATURDAY) {
      retval = "Saturday";
    } else if (day == Calendar.SUNDAY) {
      retval = "Sunday";
    }

    return retval;
  }

  /**
   * Get the day name in the short version. Ex. mon, tue, .... sun.
   */
  public String getDayNameShort() {
    String retval = "Missing";
    int day = get(DAY_OF_WEEK);

    if (day == Calendar.MONDAY) {
      retval = "mon";
    } else if (day == Calendar.TUESDAY) {
      retval = "tue";
    } else if (day == Calendar.WEDNESDAY) {
      retval = "wed";
    } else if (day == Calendar.THURSDAY) {
      retval = "thu";
    } else if (day == Calendar.FRIDAY) {
      retval = "fri";
    } else if (day == Calendar.SATURDAY) {
      retval = "sat";
    } else if (day == Calendar.SUNDAY) {
      retval = "sun";
    }

    return retval;
  }

  //DAY OF WEEK
  /**
   * Sets the day of week, using Calendars method set(int field, int value).
   */
  public void setDayOfWeek(int day) {
    set(Calendar.DAY_OF_WEEK, day);
  }

  /**
   * Gets the day of week, using Calendars method get(int field).
   */
  public int getDayOfWeek() {
    return get(Calendar.DAY_OF_WEEK);
  }

  /**
   * Is this date a friday.
   */
  public boolean isFriday() {
    boolean retval = false;
    int day = get(DAY_OF_WEEK);

    if (day == Calendar.FRIDAY) {
      retval = true;
    }

    return retval;
  }

  /**
   * Get fromdate in weekpart.
   */
  public ITRCalendar getFromDateForWeekPart() {

    //IntiroLog.detail(getClass().getName()+".getFromDateForWeekPart(): Entering");
    ITRCalendar retval = null;
    retval = getFromDate();

    return retval.cloneCalendar();
  }

  /**
   * Gets the kvartal.
   */
  public int getKvartal() {
    int retval = 0;
    int month = getMonth();

    if (month > 0 && month < 4) {
      retval = 1;
    } else if (month > 3 && month < 7) {
      retval = 2;
    } else if (month > 6 && month < 10) {
      retval = 3;
    } else {
      retval = 4;
    }

    return retval;
  }

  /**
   * Is this date a monday.
   */
  public boolean isMonday() {
    boolean retval = false;
    int day = get(DAY_OF_WEEK);

    if (day == Calendar.MONDAY) {
      retval = true;
    }

    return retval;
  }

  //MONTH
  /**
   * Sets the month using Calendars method set(int field, int value). The value retrived is subtracted with 1, since month are 0 indexed.
   * Ex. January = 1 February = 2 .... December = 12
   */
  public void setMonth(int month) {
    set(Calendar.MONTH, month - 1);
  }

  /**
   * Gets the month using Calendars method get(int field). The value retruned is added with 1, since month are 0 indexed.
   */
  public int getMonth() {
    return get(Calendar.MONTH) + 1;
  }

  /**
   * Get the name of the month. Ex. Januari = Jan. etc.
   */
  public String getMonthNameShort() {
    StringBuffer retval = new StringBuffer();

    if (getMonth() == 1) {
      retval.append("Jan");
    } else if (getMonth() == 2) {
      retval.append("Feb");
    } else if (getMonth() == 3) {
      retval.append("Mar");
    } else if (getMonth() == 4) {
      retval.append("Apr");
    } else if (getMonth() == 5) {
      retval.append("Maj");
    } else if (getMonth() == 6) {
      retval.append("Jun");
    } else if (getMonth() == 7) {
      retval.append("Jul");
    } else if (getMonth() == 8) {
      retval.append("Aug");
    } else if (getMonth() == 9) {
      retval.append("Sep");
    } else if (getMonth() == 10) {
      retval.append("Okt");
    } else if (getMonth() == 11) {
      retval.append("Nov");
    } else if (getMonth() == 12) {
      retval.append("Dec");
    }

    return retval.toString();
  }

  /**
   * Is this date a saturday.
   */
  public boolean isSaturday() {
    boolean retval = false;
    int day = get(DAY_OF_WEEK);

    if (day == Calendar.SATURDAY) {
      retval = true;
    }

    return retval;
  }

  /**
   * Is this date a thursday.
   */
  public boolean isSunday() {
    boolean retval = false;
    int day = get(DAY_OF_WEEK);

    if (day == Calendar.SUNDAY) {
      retval = true;
    }

    return retval;
  }

  /**
   * Is this date a thursday.
   */
  public boolean isThursday() {
    boolean retval = false;
    int day = get(DAY_OF_WEEK);

    if (day == Calendar.THURSDAY) {
      retval = true;
    }

    return retval;
  }

  /**
   * Get todate in weekpart.
   */
  public ITRCalendar getToDateForWeekPart() {

    //IntiroLog.detail(getClass().getName()+".getToDateForWeekPart(): Entering");
    ITRCalendar retval = null;
    retval = getToDate();

    return retval.cloneCalendar();
  }

  /**
   * Is this date a tuesday.
   */
  public boolean isTuesday() {
    boolean retval = false;
    int day = get(DAY_OF_WEEK);

    if (day == Calendar.TUESDAY) {
      retval = true;
    }

    return retval;
  }

  /**
   * Is this date a wednesday.
   */
  public boolean isWednesday() {
    boolean retval = false;
    int day = get(DAY_OF_WEEK);

    if (day == Calendar.WEDNESDAY) {
      retval = true;
    }

    return retval;
  }

  //WEEK OF YEAR
  /**
   * Sets the week of year using Calendars method set(int field, int value).
   */
  public void setWeekOfYear(int week) {
    set(Calendar.WEEK_OF_YEAR, week);
  }

  /**
   * Gets the week of year using Calendars method get(int field).
   */
  public int getWeekOfYear() {
    return get(Calendar.WEEK_OF_YEAR);
  }

  /**
   * Gets the week part.
   */
  public int getWeekPart() {
    getFromDate();

    return weekPart;
  }

  /**
   * Gets the week parts of the year.
   *
   * @retrun a ArrayList with the first date of each weekpart in the year.
   */
  public ArrayList<ITRCalendar> getWeekPartsOfYear() {

    //IntiroLog.detail(getClass().getName()+".getWeekPartsOfYear(): Entering");
    ArrayList<ITRCalendar> retval = new ArrayList<ITRCalendar>();
    ITRCalendar dateToAdd = new ITRCalendar(getYear() + "-01-01");
    int originalYear = dateToAdd.getYear();

    while (true) {

      /*dateToAdd.getYear() is  == originalYear */
      if (dateToAdd.getYear() == originalYear) {

        //IntiroLog.detail(getClass().getName()+".getWeekPartsOfYear(): dateToAdd = "+dateToAdd);
        retval.add(dateToAdd.cloneCalendar());
        dateToAdd.nextWeekPart();
      } else {

        /*A new year has come.*/
        break;
      }
    }

    return retval;
  }

  /**
   * isWeekSplit.
   */
  public boolean isWeekSplit() {
    getFromDate();

    return isWeekSplit;
  }

  //YEAR
  /**
   * Sets the year using Calendars method set(int field, int value).
   */
  public void setYear(int year) {
    set(Calendar.YEAR, year);
  }

  /**
   * Gets the year using Calendars method get(int field).
   */
  public int getYear() {
    return get(Calendar.YEAR);
  }

  /**
   * Call super.clear() and performs some cleaning in this class to.
   */
  public void clearCalendar() {
    weekPart = 1;
    isWeekSplit = false;
    datesInWeek.clear();
    fromDate = null;
    toDate = null;
    clear();
  }

  /**
   * Clones this Calendar.
   */
  public ITRCalendar cloneCalendar() {
    ITRCalendar retval = null;

    try {
      retval = new ITRCalendar();
      retval.clear();
      retval.loadCalendarWithDate(getCalendarInStoreFormat());
    } catch (Exception e) {
      IntiroLog.error(getClass(), getClass().getName() + ".cloneCalendar(): Error when trying to clone Calendar", e);
    }

    return retval;
  }

  /**
   * Load the calendar with a specific date. Usely this is retrived directly from the database. The format of loadString must be:
   * [YEAR-MONTH-DATE] Ex. 2000-12-31 [MONTH/DATE/YEAR] Ex. 12/31/2000 or [YEAR-MONTH-DATE HOURS:MINUTES:SECONDS] Ex. 2000-12-31 12:52:13
   * [MONTH/DATE/YEAR HOURS:MINUTES:SECONDS] Ex. 12/31/2000 12:52:13
   *
   * @param loadString a String containing the required information.
   * @exception throws Exception if something is wrong with loadString.
   */
  public void loadCalendarWithDate(String loadString)
          throws Exception {

    //IntiroLog.detail(getClass().getName()+".loadCalendarWithDate(String): Entering");
    //IntiroLog.detail(getClass().getName()+".loadCalendarWithDate(String): loadString = " + loadString);
    if (loadString == null) {
      throw new Exception(getClass().getName() + ".loadCalendarWithDate(String loadString): loadString = "
              + loadString);
    }

    int year = 0;
    int month = 0;
    int date = 0;
    int monthPos = 0;
    int datePos = 0;
    int hourPos = 0;
    int yearPos = 0;

    // Check which format
    if (loadString.lastIndexOf(DATE_SEPARATOR_LINE) != -1) {

      //Remove the hours, minutes and seconds from loadString. loadString looks like this [YEAR-MONTH-DATE HOURS:MINUTES:SECONDS]
      if ((hourPos = loadString.indexOf(":")) != -1) {
        loadString = loadString.substring(0, hourPos - 3);
      }
      if ((monthPos = loadString.indexOf(DATE_SEPARATOR_LINE)) != -1) {
        year = Integer.parseInt(loadString.substring(0, monthPos));

        if ((datePos = loadString.lastIndexOf(DATE_SEPARATOR_LINE)) != -1) {
          month = Integer.parseInt(loadString.substring(monthPos + 1, datePos));
          date = Integer.parseInt(loadString.substring(datePos + 1));
        } else {
          throw new Exception(getClass().getName()
                  + ".loadCalendarWithDate(String loadString): Date position could not be found, loadString = "
                  + loadString);
        }

        setYear(year);
        setMonth(month);
        setDate(date);
      }
    } else if (loadString.lastIndexOf(DATE_SEPARATOR_SLASH) != -1) {

      //Remove the hours, minutes and seconds from loadString. loadString looks like this [MONTH/DATE/YEAR HOURS:MINUTES:SECONDS]
      if ((hourPos = loadString.indexOf(":")) != -1) {
        loadString = loadString.substring(0, hourPos - 3);
      }
      if ((datePos = loadString.indexOf(DATE_SEPARATOR_SLASH)) != -1) {
        month = Integer.parseInt(loadString.substring(0, datePos));

        if ((yearPos = loadString.lastIndexOf(DATE_SEPARATOR_SLASH)) != -1) {
          date = Integer.parseInt(loadString.substring(datePos + 1, yearPos));
          year = Integer.parseInt(loadString.substring(yearPos + 1));
        } else {
          throw new Exception(getClass().getName()
                  + ".loadCalendarWithDate(String loadString): Date position could not be found, loadString = "
                  + loadString);
        }

        setYear(year);
        setMonth(month);
        setDate(date);
      }
    } else {
      throw new Exception(getClass().getName()
              + ".loadCalendarWithDate(String loadString): Month position could not be found, loadString = "
              + loadString);
    }

    /*
      // print out a bunch of interesting things
    System.out.println("ERA: " + get(Calendar.ERA));
    System.out.println("YEAR: " + get(Calendar.YEAR));
    System.out.println("MONTH: " + get(Calendar.MONTH));
    System.out.println("WEEK_OF_YEAR: " + get(Calendar.WEEK_OF_YEAR));
    System.out.println("WEEK_OF_MONTH: " + get(Calendar.WEEK_OF_MONTH));
    System.out.println("DATE: " + get(Calendar.DATE));
    System.out.println("DAY_OF_MONTH: " + get(Calendar.DAY_OF_MONTH));
    System.out.println("DAY_OF_YEAR: " + get(Calendar.DAY_OF_YEAR));
    System.out.println("DAY_OF_WEEK: " + get(Calendar.DAY_OF_WEEK));
    System.out.println("DAY_OF_WEEK_IN_MONTH: "
                        + get(Calendar.DAY_OF_WEEK_IN_MONTH));
    System.out.println("AM_PM: " + get(Calendar.AM_PM));
    System.out.println("HOUR: " + get(Calendar.HOUR));
    System.out.println("HOUR_OF_DAY: " + get(Calendar.HOUR_OF_DAY));
    System.out.println("MINUTE: " + get(Calendar.MINUTE));
    System.out.println("SECOND: " + get(Calendar.SECOND));
    System.out.println("MILLISECOND: " + get(Calendar.MILLISECOND));
    System.out.println("ZONE_OFFSET: "
                        + (get(Calendar.ZONE_OFFSET)/(60*60*1000)));
    System.out.println("DST_OFFSET: "
                        + (get(Calendar.DST_OFFSET)/(60*60*1000)));
    System.out.println("Current Time, with hour reset to 3");
    clear(Calendar.HOUR_OF_DAY); // so doesn't override
    set(Calendar.HOUR, 3);
    System.out.println("ERA: " + get(Calendar.ERA));
    System.out.println("YEAR: " + get(Calendar.YEAR));
    System.out.println("MONTH: " + get(Calendar.MONTH));
    System.out.println("WEEK_OF_YEAR: " + get(Calendar.WEEK_OF_YEAR));
    System.out.println("WEEK_OF_MONTH: " + get(Calendar.WEEK_OF_MONTH));
    System.out.println("DATE: " + get(Calendar.DATE));
    System.out.println("DAY_OF_MONTH: " + get(Calendar.DAY_OF_MONTH));
    System.out.println("DAY_OF_YEAR: " + get(Calendar.DAY_OF_YEAR));
    System.out.println("DAY_OF_WEEK: " + get(Calendar.DAY_OF_WEEK));
    System.out.println("DAY_OF_WEEK_IN_MONTH: "
                        + get(Calendar.DAY_OF_WEEK_IN_MONTH));
    System.out.println("AM_PM: " + get(Calendar.AM_PM));
    System.out.println("HOUR: " + get(Calendar.HOUR));
    System.out.println("HOUR_OF_DAY: " + get(Calendar.HOUR_OF_DAY));
    System.out.println("MINUTE: " + get(Calendar.MINUTE));
    System.out.println("SECOND: " + get(Calendar.SECOND));
    System.out.println("MILLISECOND: " + get(Calendar.MILLISECOND));
    System.out.println("ZONE_OFFSET: "
            + (get(Calendar.ZONE_OFFSET)/(60*60*1000))); // in hours
    System.out.println("DST_OFFSET: "
            + (get(Calendar.DST_OFFSET)/(60*60*1000))); // in hours
     */
  }

  /**
   * Set calendar to the next date, using Calendars method roll(int field, boolean up). Moves into next month if nessessary. Ex. if
   * nextDate() is called when date is 2000-11-30, it will be set to 2000-12-01.
   */
  public void nextDate() {

    //IntiroLog.detail(getClass().getName()+".nextDate(): getDate() = " + getDate());
    //IntiroLog.detail(getClass().getName()+".nextDate(): getActualMaximum(Calendar.DAY_OF_MONTH) = " + getActualMaximum(Calendar.DAY_OF_MONTH));
    if (getDate() == getActualMaximum(Calendar.DAY_OF_MONTH)) {
      nextMonth();
      setDate(1);
    } else {
      roll(Calendar.DATE, true);
    }
  }

  /**
   * Roll month up. Moves into next year if nessessary. Ex. if nextMont() is called when date is 2000-12-30, it will be set to 2001-01-30
   */
  public void nextMonth() {
    if (getMonth() == 12) {
      nextYear();
      setMonth(1);
    } else {
      roll(Calendar.MONTH, true);
    }
  }

  /**
   * Roll week up. Stays on MONDAY the next week.
   */
  public void nextWeek() {

    //If we have to roll down to a previous year.
    if (getActualMaximum(WEEK_OF_YEAR) == getWeekOfYear()) {
      nextYear();
      set(WEEK_OF_YEAR, 1);
      set(DAY_OF_WEEK, getActualMinimum(DAY_OF_WEEK));
    } else {
      roll(WEEK_OF_YEAR, true);
      set(DAY_OF_WEEK, Calendar.MONDAY);
    }
  }

  /**
   * Roll week or weekpart up. If week 4 is split and calendar is in week 4_1 and nextWeekPart() is called it rolls up to week 4_2. If
   * calendar is week 4_2 it is rolled up to week 5_1. It stays on first date of the new week or weekpart. If a new year is hit the calendar
   * is rolled up to this new year.
   */
  public void nextWeekPart() {
    ITRCalendar nextWeek = getToDate();
    nextWeek.nextDate();

    try {
      this.loadCalendarWithDate(nextWeek.getCalendarInStoreFormat());
    } catch (Exception e) {
      IntiroLog.criticalError(getClass(), getClass().getName() + ".nextWeekPart(): Unable to load. ", e);
    }
  }

  /**
   * Roll year up.
   */
  public void nextYear() {
    roll(YEAR, true);
  }

  /**
   * Set calendar to the previous date, using Calendars method roll(int field, boolean up). Moves into previous month if nessessary. Ex. if
   * previousDate() is called when date is 2000-11-01, it will be set to 2000-10-31.
   */
  public void previousDate() {
    if (getDate() == 1) {
      previousMonth();
      setDate(getActualMaximum(Calendar.DAY_OF_MONTH));
    } else {
      roll(Calendar.DATE, false);
    }
  }

  /**
   * Roll month down.
   */
  public void previousMonth() {
    if (getMonth() == 1) {
      previousYear();
      setMonth(12);
    } else {
      roll(Calendar.MONTH, false);
    }
  }

  /**
   * Roll week down. Stays on SUNDAY the previous week.
   */
  public void previousWeek() {

    //If we have to roll down to a previous year.
    if (getMinimum(WEEK_OF_YEAR) == getWeekOfYear()) {
      previousYear();
      set(WEEK_OF_YEAR, getActualMaximum(WEEK_OF_YEAR));
      set(DAY_OF_WEEK, getActualMaximum(DAY_OF_WEEK));
    } else {
      roll(WEEK_OF_YEAR, false);
      set(DAY_OF_WEEK, Calendar.SUNDAY);
    }
  }

  /**
   * Roll year down.
   */
  public void previousYear() {
    roll(YEAR, false);
  }

  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append("[" + getCalendarInStoreFormat() + "], ");
    sb.append("YEAR: " + get(Calendar.YEAR));
    sb.append(", MONTH: " + get(Calendar.MONTH));
    sb.append(", WEEK_OF_YEAR: " + get(Calendar.WEEK_OF_YEAR));
    sb.append(", WEEK_OF_MONTH: " + get(Calendar.WEEK_OF_MONTH));
    sb.append(", DATE: " + get(Calendar.DATE));
    sb.append(", DAY_OF_MONTH: " + get(Calendar.DAY_OF_MONTH));
    sb.append(", DAY_OF_YEAR: " + get(Calendar.DAY_OF_YEAR));
    sb.append(", DAY_OF_WEEK: " + get(Calendar.DAY_OF_WEEK));
    sb.append(", DAY_OF_WEEK_IN_MONTH: " + get(Calendar.DAY_OF_WEEK_IN_MONTH));

    return sb.toString();
  }

  /**
   * Get date in week.
   *
   * @param weekNo an int specifying the week number.
   * @param weekPart an int specifying the week part.
   */
  ArrayList getDatesInWeek() {

    //IntiroLog.detail(getClass().getName()+".getDatesInWeek(): Entering");
    ITRCalendar dateToAdd = getFromDate();

    //IntiroLog.detail(getClass().getName()+".getDatesInWeek(): getFromDate() = " + getFromDate());
    //IntiroLog.detail(getClass().getName()+".getDatesInWeek(): getToDate() = " + getToDate());
    while (true) {

      /*As long as dateToAdd is not after last date in week,
      add it to dates to return. */
      if (!dateToAdd.after(getToDate())) {

        //IntiroLog.detail(getClass().getName()+".getDatesInWeek(): dateToAdd = "+dateToAdd);
        addDateInWeek(dateToAdd.cloneCalendar());
        dateToAdd.nextDate();
      } else {
        break;
      }
    }

    return datesInWeek;
  }

  /**
   * get from date for week.
   */
  ITRCalendar getFromDate() {
    loadWeekHandeling();

    return fromDate;
  }

  /**
   * Get to date for week.
   */
  ITRCalendar getToDate() {
    loadWeekHandeling();

    return toDate;
  }

  //WEEK PART
  /**
   * Set the weekpart.
   */
  void setWeekPart(int part) {
    weekPart = part;
  }

  /**
   * setWeekSplit.
   */
  void setWeekSplit(boolean split) {
    isWeekSplit = split;
  }

  /**
   * Add date to week.
   */
  void addDateInWeek(ITRCalendar date) {
    if (date != null) {
      datesInWeek.add(date);
    }
  }

  /**
   * Load week handeling. Load fromDate and toDate.
   */
  void loadWeekHandeling() {

    //IntiroLog.detail(getClass().getName()+".getFromDate(): Entering");
    //IntiroLog.detail(getClass().getName()+".getFromDate(): Date we are loading week for = " + this.toString());
    int thisYear = getYear();
    int thisMonthNo = getMonth();
    //int thisMonthDate = getDate();
    int thisWeekNo = getWeekOfYear();
    boolean splitWeekHasBeenDetected = false;

    //--------------------START FROM DATE -----------
    ITRCalendar previousDate = cloneCalendar();
    previousDate.previousDate();

    //find start of week.
    for (int i = 0; i < 8; i++) {

      /*IntiroLog.detail(getClass().getName()+".getFromDate(): Loop["+i+"],  previousDate = " + previousDate);
      IntiroLog.detail(getClass().getName()+".getFromDate():   Loop["+i+"], Finding out the fromDate , thisYear = " + thisYear +
                                                                                            ", thisMonthNo = "+thisMonthNo +
                                                                                            ", thisMonthDate = " +thisMonthDate +
                                                                                            ", thisWeekNo = "+thisWeekNo);
       */
      //Check if we have gone backward into previous year.
      if (previousDate.getYear() < thisYear) {
        setWeekSplit(false);
        setWeekPart(1);

        if (IntiroLog.d()) {
          IntiroLog.detail(getClass(),
                  getClass().getName() + ".getFromDate(): BREAKING -> (previousDate.getYear() < thisYear)");
        }
        if (IntiroLog.d()) {
          IntiroLog.detail(getClass(),
                  getClass().getName() + ".getFromDate(): [previousDate.getMonth() = "
                  + previousDate.getMonth() + ", thisMonthNo = " + thisMonthNo
                  + "] [previousDate.getWeekOfYear() = " + previousDate.getWeekOfYear() + ", thisWeekNo = "
                  + thisWeekNo + "]");
        }

        break;
      } //Check if we have backed down to a new month and still in same week.
      else if (previousDate.getMonth() != thisMonthNo && previousDate.getWeekOfYear() == thisWeekNo) {
        setWeekSplit(true);
        setWeekPart(2);
        splitWeekHasBeenDetected = true;
        break;
      } //Check if we have backed down to a new month and a new week.
      else if (previousDate.getMonth() != thisMonthNo && previousDate.getWeekOfYear() != thisWeekNo) {
        setWeekSplit(false);
        setWeekPart(1);

        if (IntiroLog.d()) {
          IntiroLog.detail(getClass(),
                  getClass().getName()
                  + ".getFromDate(): BREAKING -> (previousDate.getMonth() != thisMonthNo && previousDate.getWeekOfYear() != thisWeekNo)");
        }
        if (IntiroLog.d()) {
          IntiroLog.detail(getClass(),
                  getClass().getName() + ".getFromDate(): [previousDate.getMonth() = "
                  + previousDate.getMonth() + ", thisMonthNo = " + thisMonthNo
                  + "] [previousDate.getWeekOfYear() = " + previousDate.getWeekOfYear() + ", thisWeekNo = "
                  + thisWeekNo + "]");
        }

        break;
      } else if (previousDate.getWeekOfYear() != thisWeekNo) {
        if (IntiroLog.d()) {
          IntiroLog.detail(getClass(),
                  getClass().getName()
                  + ".getFromDate(): BREAKING -> (previousDate.getWeekOfYear() != thisWeekNo)");
        }
        if (IntiroLog.d()) {
          IntiroLog.detail(getClass(),
                  getClass().getName() + ".getFromDate(): [previousDate.getMonth() = "
                  + previousDate.getMonth() + ", thisMonthNo = " + thisMonthNo
                  + "] [previousDate.getWeekOfYear() = " + previousDate.getWeekOfYear() + ", thisWeekNo = "
                  + thisWeekNo + "]");
        }

        setWeekSplit(false);
        setWeekPart(1);
        break;
      }

      //move 1 day back.
      previousDate.previousDate();
    } //for(int i=0; i < 7; i++)

    //set start of week.
    previousDate.nextDate();
    fromDate = previousDate;

    //IntiroLog.detail(getClass().getName()+".loadWeekHandeling(): FromDate has been found and stored, Date we are loading week for = " + this.toString());
    //--------------------END FROM DATE -----------
    //--------------------START TO DATE -----------
    //IntiroLog.detail(getClass().getName()+".getToDate(): Entering");
    //IntiroLog.detail(getClass().getName()+".getToDate(): Date we are loading week for = " + this.toString());
    thisYear = getYear();
    thisMonthNo = getMonth();
    //thisMonthDate = getDate();
    thisWeekNo = getWeekOfYear();

    ITRCalendar nextDate = cloneCalendar();
    nextDate.nextDate();

    //FIND END OF WEEK.
    //find start of week.
    for (int i = 0; i < 8; i++) {

      /*IntiroLog.detail(getClass().getName()+".getToDate(): Loop["+i+"],  nextDate = " + nextDate);
      IntiroLog.detail(getClass().getName()+".getToDate(): Loop = ["+i+"], Finding out the toDate , thisYear = " + thisYear +
                                                                                            ", thisMonthNo = "+thisMonthNo +
                                                                                            ", thisMonthDate = " +thisMonthDate +
                                                                                            ", thisWeekNo = "+thisWeekNo);
       */
      //Check if we have gone forward into next year.
      if (nextDate.getYear() > thisYear) {
        setWeekSplit(false);
        setWeekPart(1);

        if (IntiroLog.d()) {
          IntiroLog.detail(getClass(),
                  getClass().getName() + ".getToDate(): Loop[" + i
                  + "], Finding out the toDate, BREAKING -> (nextDate.getYear() > thisYear)");
        }

        break;
      } //Check if we have gone forward to a new month and still in same week.
      else if (nextDate.getMonth() != thisMonthNo && nextDate.getWeekOfYear() == thisWeekNo) {
        setWeekSplit(true);
        setWeekPart(1);
        break;
      } //Check if we have gone forward to a new month and a new week.
      else if (nextDate.getMonth() != thisMonthNo && nextDate.getWeekOfYear() != thisWeekNo) {
        if (!splitWeekHasBeenDetected) {
          setWeekSplit(false);
          setWeekPart(1);
        }
        if (IntiroLog.d()) {
          IntiroLog.detail(getClass(),
                  getClass().getName() + ".getToDate(): Loop[" + i
                  + "], Finding out the toDate, BREAKING -> (nextDate.getMonth() != thisMonthNo && nextDate.getWeekOfYear() != thisWeekNo)");
        }

        break;
      } else if (nextDate.getWeekOfYear() != thisWeekNo) {
        if (IntiroLog.d()) {
          IntiroLog.detail(getClass(),
                  getClass().getName() + ".getToDate(): Loop[" + i
                  + "], Finding out the toDate, BREAKING -> (nextDate.getWeekOfYear() != thisWeekNo): nextDate.getMonth() = "
                  + nextDate.getMonth() + ", thisMonthNo = " + thisMonthNo);
        }
        if (!splitWeekHasBeenDetected) {
          setWeekSplit(false);
          setWeekPart(1);
        }

        break;
      }

      //move 1 day forward.
      nextDate.nextDate();
    } //for(int i=0; i < 7; i++)

    //set end of week.
    nextDate.previousDate();
    toDate = nextDate;

    //--------------------END TO DATE -----------
  }

  //------------------------------------------ END CONVENIENT SET AND GET METHODS ----------------------------------------
}
