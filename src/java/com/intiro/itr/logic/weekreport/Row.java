/**
 * Title:         ITR
 * Description:
 * Copyright:     Copyright (c) 2001
 * Company:       Intiro Development AB
 * @author        Daniel Kjall
 * @version       1.0
 */
package com.intiro.itr.logic.weekreport;

import java.util.Vector;

import com.intiro.itr.db.DBConstants;
import com.intiro.itr.logic.project.Project;
import com.intiro.itr.util.ITRCalendar;
import com.intiro.itr.util.StringRecordset;
import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.itr.util.xml.DynamicXMLCarrier;
import com.intiro.itr.util.xml.XMLBuilderException;
import com.intiro.toolbox.log.IntiroLog;

public class Row extends DynamicXMLCarrier {
  // ~ Instance/static variables ........................................................................................

  protected static final String XML_FRI_END = "</fr>";

  protected static final String XML_FRI_START = "<fr>";

  protected static final String XML_MON_END = "</mo>";

  protected static final String XML_MON_START = "<mo>";

  protected static final String XML_ROWINDEX_END = "</i>";

  protected static final String XML_ROWINDEX_START = "<i>";

  protected static final String XML_ROW_COMMENT_END = "</comment>";

  protected static final String XML_ROW_COMMENT_START = "<comment>";

  protected static final String XML_ROW_END = "</row>";

  protected static final String XML_ROW_EXIST_END = "</exist>";

  protected static final String XML_ROW_EXIST_START = "<exist>";

  protected static final String XML_ROW_HOURS_END = "</hours>";

  protected static final String XML_ROW_HOURS_START = "<hours>";

  // ROW
  protected static final String XML_ROW_START = "<row>";

  protected static final String XML_ROW_TIMETYPEID_END = "</timetypeid>";

  protected static final String XML_ROW_TIMETYPEID_START = "<timetypeid>";

  protected static final String XML_ROW_TIMETYPE_END = "</timetype>";

  protected static final String XML_ROW_TIMETYPE_START = "<timetype>";

  protected static final String XML_ROW_TOTAL_END = "</total>";

  protected static final String XML_ROW_TOTAL_START = "<total>";

  protected static final String XML_ROW_WEEKEND_END = "</weekend>";

  protected static final String XML_ROW_WEEKEND_START = "<weekend>";

  protected static final String XML_SAT_END = "</sa>";

  protected static final String XML_SAT_START = "<sa>";

  protected static final String XML_SUN_END = "</su>";

  protected static final String XML_SUN_START = "<su>";

  protected static final String XML_THU_END = "</th>";

  protected static final String XML_THU_START = "<th>";

  protected static final String XML_TUE_END = "</tu>";

  protected static final String XML_TUE_START = "<tu>";

  protected static final String XML_WED_END = "</we>";

  protected static final String XML_WED_START = "<we>";

  /* Friday */
  protected String friComment = "";

  protected boolean friExist = false;

  protected double friHours = 0;

  protected boolean friWeekend = false;

  protected ITRCalendar fromDate = null;

  /* Monday */
  protected String monComment = "";

  protected boolean monExist = false;

  protected double monHours = 0;

  protected boolean monWeekend = false;

  protected Project project = null;

  protected boolean removed = false;

  protected int rowEntryId = -1;

  protected double rowSum = 0;

  /* Satday */
  protected String satComment = "";

  protected boolean satExist = false;

  protected double satHours = 0;

  protected boolean satWeekend = false;

  /* Sunday */
  protected String sunComment = "";

  protected boolean sunExist = false;

  protected double sunHours = 0;

  protected boolean sunWeekend = false;

  /* Thursday */
  protected String thuComment = "";

  protected boolean thuExist = false;

  protected double thuHours = 0;

  protected boolean thuWeekend = false;

  protected String timeType = "";

  protected String timeTypeId = "";

  protected ITRCalendar toDate = null;

  /* Tuesday */
  protected String tueComment = "";

  protected boolean tueExist = false;

  protected double tueHours = 0;

  protected boolean tueWeekend = false;

  /* Wednesday */
  protected String wedComment = "";

  protected boolean wedExist = false;

  protected double wedHours = 0;

  protected boolean wedWeekend = false;

  // ~ Constructors .....................................................................................................

  /**
   * Constructor I for Row. Creates a Row.
   * 
   * @param userProfile
   *          the users UserProfile.
   * @param fromDate
   *          a ITRCalendar with the start date.
   * @param toDate
   *          a ITRCalendar with the end date.
   * @exception throws
   *              XMLBuilderException if something goes wrong.
   */
  public Row(UserProfile userProfile, ITRCalendar fromDate, ITRCalendar toDate) throws XMLBuilderException {
    super(userProfile);
    setFromDate(fromDate);
    setToDate(toDate);
    project = new Project(userProfile);
  }

  // ~ Methods ..........................................................................................................

  /**
   * Is this row compensation type1 hours.
   */
  public boolean isComp1Hours() {
    boolean retval = false;

    if (getTimeTypeId() != null && getTimeTypeId().equalsIgnoreCase("4")) {
      retval = true;
    }

    return retval;
  }

  /**
   * Is this row compensation type1 hours.
   */
  public boolean isComp2Hours() {
    boolean retval = false;

    if (getTimeTypeId() != null && getTimeTypeId().equalsIgnoreCase("5")) {
      retval = true;
    }

    return retval;
  }

  /**
   * Set value for Friday.
   */
  public void setFriday(double value) {
    friHours = value;
  }

  /**
   * Get value for Friday.
   */
  public double getFriday() {
    return friHours;
  }

  /**
   * Set from date for row.
   */
  public void setFromDate(ITRCalendar fromDate) {
    this.fromDate = fromDate;
  }

  /**
   * get from date for row.
   */
  public ITRCalendar getFromDate() {
    return fromDate;
  }

  /**
   * Set value for Monday.
   */
  public void setMonday(double value) {
    monHours = value;
  }

  /**
   * Get value for Monday.
   */
  public double getMonday() {
    return monHours;
  }

  /**
   * Is this row normal hours.
   */
  public boolean isNormalHours() {
    boolean retval = false;

    if (getTimeTypeId() != null && getTimeTypeId().equalsIgnoreCase("1")) {
      retval = true;
    }

    return retval;
  }

  /**
   * Is this row overtime type 1 hours.
   */
  public boolean isOvertime1Hours() {
    boolean retval = false;

    if (getTimeTypeId() != null && getTimeTypeId().equalsIgnoreCase("2")) {
      retval = true;
    }

    return retval;
  }

  /**
   * Is this row overtime type 2 hours.
   */
  public boolean isOvertime2Hours() {
    boolean retval = false;

    if (getTimeTypeId() != null && getTimeTypeId().equalsIgnoreCase("3")) {
      retval = true;
    }

    return retval;
  }

  /**
   * Sets the Project to be connected to this row.
   */
  public void setProject(Project project) {
    this.project = project;
  }

  /**
   * Gets the Project connected to this row.
   */
  public Project getProject() {
    return project;
  }

  /**
   * Set this row to status = removed.
   */
  public void setRemoved() {
    removed = true;
  }

  /**
   * Check to see if this row has status removed.
   */
  public boolean isRemoved() {
    return removed;
  }

  /**
   * Set row entry id.
   */
  public void setRowEntryId(int id) {
    this.rowEntryId = id;
  }

  /**
   * Get row entry id.
   */
  public int getRowEntryId() {
    return rowEntryId;
  }

  public void setRowSum() {
    rowSum = getMonday() + getTuesday() + getWednesday() + getThursday() + getFriday() + getSaturday() + getSunday();
  }

  /**
   * Get row sum.
   */
  public double getRowSum() {
    return rowSum;
  }

  /**
   * Set value for Saturday.
   */
  public void setSaturday(double value) {
    satHours = value;
  }

  /**
   * Get value for Saturday.
   */
  public double getSaturday() {
    return satHours;
  }

  /**
   * Set value for Sunday.
   */
  public void setSunday(double value) {
    sunHours = value;
  }

  /**
   * Get value for Sunday.
   */
  public double getSunday() {
    return sunHours;
  }

  /**
   * Set value for Thursday.
   */
  public void setThursday(double value) {
    thuHours = value;
  }

  /**
   * Get value for Thursday.
   */
  public double getThursday() {
    return thuHours;
  }

  /**
   * Set value for time type.
   */
  public void setTimeType(String type) {
    timeType = type;
  }

  /**
   * Get value for time type.
   */
  public String getTimeType() {
    return timeType;
  }

  /**
   * Set value for time type id.
   */
  public void setTimeTypeId(String id) {
    timeTypeId = id;
  }

  /**
   * Get value for time type id.
   */
  public String getTimeTypeId() {
    return timeTypeId;
  }

  /**
   * Set to date for row.
   */
  public void setToDate(ITRCalendar toDate) {
    this.toDate = toDate;
  }

  /**
   * Get to date for row.
   */
  public ITRCalendar getToDate() {
    return toDate;
  }

  /**
   * Set value for Tuesday.
   */
  public void setTuesday(double value) {
    tueHours = value;
  }

  /**
   * Get value for Thuesday.
   */
  public double getTuesday() {
    return tueHours;
  }

  /**
   * Set value for Wednesday.
   */
  public void setWednesday(double value) {
    wedHours = value;
  }

  /**
   * Get value for Wednesday.
   */
  public double getWednesday() {
    return wedHours;
  }

  public void addToFriday(double add) {
    friHours += add;
  }

  public void addToMonday(double add) {
    monHours += add;
  }

  public void addToSaturday(double add) {
    satHours += add;
  }

  public void addToSunday(double add) {
    sunHours += add;
  }

  public void addToThursday(double add) {
    thuHours += add;
  }

  public void addToTuesday(double add) {
    tueHours += add;
  }

  public void addToWednesday(double add) {
    wedHours += add;
  }

  /**
   * Calculate what day is weekend. This method needs to be called on the Row, to get the correct status on the days in week.
   */
  public void calculateDayStatus(SumRows sumRows) {

    // if we have a split week
    if (getFromDate().isWeekSplit()) {
      Vector daysInWeekPart = getFromDate().getDatesInWeekPart();
      ITRCalendar oneDay = null;

      for (int i = 0; i < daysInWeekPart.size(); i++) {
        oneDay = (ITRCalendar) daysInWeekPart.get(i);

        if (oneDay != null) {
          if (oneDay.isMonday()) {
            monExist = true;
          }
          if (oneDay.isTuesday()) {
            tueExist = true;
          }
          if (oneDay.isWednesday()) {
            wedExist = true;
          }
          if (oneDay.isThursday()) {
            thuExist = true;
          }
          if (oneDay.isFriday()) {
            friExist = true;
          }
          if (oneDay.isSaturday()) {
            satExist = true;
          }
          if (oneDay.isSunday()) {
            sunExist = true;
          }
        }
      }
    } else {
      monExist = true;
      tueExist = true;
      wedExist = true;
      thuExist = true;
      friExist = true;
      satExist = true;
      sunExist = true;
    }
    // Weekend
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".calculateWitchDaysExist(): sumRows.getExpectedRow().getMonday() = " + sumRows.getExpectedRow().getMonday());
    }
    if (sumRows.getExpectedRow().getMonday() > 0) {
      monWeekend = false;
    }
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".calculateWitchDaysExist(): sumRows.getExpectedRow().getTuesday() = " + sumRows.getExpectedRow().getTuesday());
    }
    if (sumRows.getExpectedRow().getTuesday() > 0) {
      tueWeekend = false;
    }
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".calculateWitchDaysExist(): sumRows.getExpectedRow().getWednesday() = " + sumRows.getExpectedRow().getWednesday());
    }
    if (sumRows.getExpectedRow().getWednesday() > 0) {
      wedWeekend = false;
    }
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".calculateWitchDaysExist(): sumRows.getExpectedRow().getThursday() = " + sumRows.getExpectedRow().getThursday());
    }
    if (sumRows.getExpectedRow().getThursday() > 0) {
      thuWeekend = false;
    }
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".calculateWitchDaysExist(): sumRows.getExpectedRow().getFriday() = " + sumRows.getExpectedRow().getFriday());
    }
    if (sumRows.getExpectedRow().getFriday() > 0) {
      friWeekend = false;
    }
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".calculateWitchDaysExist(): sumRows.getExpectedRow().getSaturday() = " + sumRows.getExpectedRow().getSaturday());
    }
    if (sumRows.getExpectedRow().getSaturday() > 0) {
      satWeekend = false;
    }
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".calculateWitchDaysExist(): sumRows.getExpectedRow().getSunday() = " + sumRows.getExpectedRow().getSunday());
    }
    if (sumRows.getExpectedRow().getSunday() > 0) {
      sunWeekend = false;
    }
  }

  /**
   * Clears the hours in the row.
   */
  public void clearHours() {
    monHours = 0;
    tueHours = 0;
    wedHours = 0;
    thuHours = 0;
    friHours = 0;
    satHours = 0;
    sunHours = 0;
    setRowSum();
  }

  public void loadTimeType(String timeTypeId) {
    try {
      StringRecordset rs = dbQuery.getTimeType(timeTypeId);

      if (!rs.getEOF()) {
        if (IntiroLog.d()) {
          IntiroLog.detail(getClass(), getClass().getName() + ".loadTimeType(): timeTypeId = " + timeTypeId);
        }

        setTimeTypeId(timeTypeId);
        setTimeType(rs.getField(DBConstants.TIMETYPE_TYPE));

        if (IntiroLog.d()) {
          IntiroLog.detail(getClass(), getClass().getName() + ".loadTimeType(): timeType = " + getTimeType());
        }
      }

      rs.close();
    } catch (Exception e) {
      if (IntiroLog.e()) {
        IntiroLog.error(getClass(), getClass().getName() + ".loadTimeType(): ERROR FROM DATABASE, exception = " + e.getMessage());
      }
    }
  }

  /**
   * Saves the row.
   */
  public boolean save(int weekReportId) throws XMLBuilderException {
    boolean retval = false;

    try {

      //If this row is supposed to be added:
      if (!removed) {

        /* If it has not existed before make a new row. */
        if (getRowEntryId() == -1) {
          retval = dbExecute.insertRow(this, weekReportId);
        } else {
          retval = dbExecute.updateRow(this, weekReportId);
        }
      } else {
        if (getRowEntryId() != -1) {
          retval = dbExecute.deleteRow(this);
        }
      }
    } catch (Exception e) {
      if (IntiroLog.e()) {
        IntiroLog.error(getClass(), getClass().getName() + ".save(int weekReportId): ERROR FROM DATABASE, exception = " + e.getMessage());
      }

      throw new XMLBuilderException(e.getMessage());
    }

    return retval;
  }

  public String toString() {
    StringBuffer sb = new StringBuffer();
    toString(sb);

    return sb.toString();
  }

  public void toString(StringBuffer sb) {

    /* project */
    project.toString(sb);

    /* time type */
    sb.append(", timeType = ");
    sb.append(timeType);

    /* time type id */
    sb.append(", timeTypeId = ");
    sb.append(timeTypeId);

    /* Monday */
    sb.append(", monHours = ");
    sb.append(monHours);

    /* Tuesday */
    sb.append(", tueHours = ");
    sb.append(tueHours);

    /* Wednesday */
    sb.append(", wedHours = ");
    sb.append(wedHours);

    /* Thursday */
    sb.append(", thuHours = ");
    sb.append(thuHours);

    /* Friday */
    sb.append(", fridayHours = ");
    sb.append(friHours);

    /* Saturday */
    sb.append(", satHours = ");
    sb.append(satHours);

    /* Sunday */
    sb.append(", sunHours = ");
    sb.append(sunHours);

    /* Row total */
    sb.append(", rowSum = ");
    sb.append(rowSum);
  }

  /**
   * Create XML from the Row.
   */
  public void toXML(StringBuffer xmlDoc, int index) throws Exception {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".toXML(): Entering");
    }
    if (!isRemoved()) {
      xmlDoc.append(XML_ROW_START);

      /* index */
      xmlDoc.append(XML_ROWINDEX_START);
      xmlDoc.append(index);
      xmlDoc.append(XML_ROWINDEX_END);

      /* Project */
      project.toXML(xmlDoc);

      /* time type */
      xmlDoc.append(XML_ROW_TIMETYPE_START);
      xmlDoc.append(timeType);
      xmlDoc.append(XML_ROW_TIMETYPE_END);

      /* time type id */
      xmlDoc.append(XML_ROW_TIMETYPEID_START);
      xmlDoc.append(timeTypeId);
      xmlDoc.append(XML_ROW_TIMETYPEID_END);

      /* Monday */
      xmlDoc.append(XML_MON_START);
      xmlDoc.append(XML_ROW_HOURS_START);
      xmlDoc.append(monHours);
      xmlDoc.append(XML_ROW_HOURS_END);
      xmlDoc.append(XML_ROW_COMMENT_START);
      xmlDoc.append(monComment);
      xmlDoc.append(XML_ROW_COMMENT_END);
      xmlDoc.append(XML_ROW_EXIST_START);
      xmlDoc.append(monExist);
      xmlDoc.append(XML_ROW_EXIST_END);
      xmlDoc.append(XML_ROW_WEEKEND_START);
      xmlDoc.append(monWeekend);
      xmlDoc.append(XML_ROW_WEEKEND_END);
      xmlDoc.append(XML_MON_END);

      /* Tuesday */
      xmlDoc.append(XML_TUE_START);
      xmlDoc.append(XML_ROW_HOURS_START);
      xmlDoc.append(tueHours);
      xmlDoc.append(XML_ROW_HOURS_END);
      xmlDoc.append(XML_ROW_COMMENT_START);
      xmlDoc.append(tueComment);
      xmlDoc.append(XML_ROW_COMMENT_END);
      xmlDoc.append(XML_ROW_EXIST_START);
      xmlDoc.append(tueExist);
      xmlDoc.append(XML_ROW_EXIST_END);
      xmlDoc.append(XML_ROW_WEEKEND_START);
      xmlDoc.append(tueWeekend);
      xmlDoc.append(XML_ROW_WEEKEND_END);
      xmlDoc.append(XML_TUE_END);

      /* Wednesday */
      xmlDoc.append(XML_WED_START);
      xmlDoc.append(XML_ROW_HOURS_START);
      xmlDoc.append(wedHours);
      xmlDoc.append(XML_ROW_HOURS_END);
      xmlDoc.append(XML_ROW_COMMENT_START);
      xmlDoc.append(wedComment);
      xmlDoc.append(XML_ROW_COMMENT_END);
      xmlDoc.append(XML_ROW_EXIST_START);
      xmlDoc.append(wedExist);
      xmlDoc.append(XML_ROW_EXIST_END);
      xmlDoc.append(XML_ROW_WEEKEND_START);
      xmlDoc.append(wedWeekend);
      xmlDoc.append(XML_ROW_WEEKEND_END);
      xmlDoc.append(XML_WED_END);

      /* Thursday */
      xmlDoc.append(XML_THU_START);
      xmlDoc.append(XML_ROW_HOURS_START);
      xmlDoc.append(thuHours);
      xmlDoc.append(XML_ROW_HOURS_END);
      xmlDoc.append(XML_ROW_COMMENT_START);
      xmlDoc.append(thuComment);
      xmlDoc.append(XML_ROW_COMMENT_END);
      xmlDoc.append(XML_ROW_EXIST_START);
      xmlDoc.append(thuExist);
      xmlDoc.append(XML_ROW_EXIST_END);
      xmlDoc.append(XML_ROW_WEEKEND_START);
      xmlDoc.append(thuWeekend);
      xmlDoc.append(XML_ROW_WEEKEND_END);
      xmlDoc.append(XML_THU_END);

      /* Friday */
      xmlDoc.append(XML_FRI_START);
      xmlDoc.append(XML_ROW_HOURS_START);
      xmlDoc.append(friHours);
      xmlDoc.append(XML_ROW_HOURS_END);
      xmlDoc.append(XML_ROW_COMMENT_START);
      xmlDoc.append(friComment);
      xmlDoc.append(XML_ROW_COMMENT_END);
      xmlDoc.append(XML_ROW_EXIST_START);
      xmlDoc.append(friExist);
      xmlDoc.append(XML_ROW_EXIST_END);
      xmlDoc.append(XML_ROW_WEEKEND_START);
      xmlDoc.append(friWeekend);
      xmlDoc.append(XML_ROW_WEEKEND_END);
      xmlDoc.append(XML_FRI_END);

      /* Saturday */
      xmlDoc.append(XML_SAT_START);
      xmlDoc.append(XML_ROW_HOURS_START);
      xmlDoc.append(satHours);
      xmlDoc.append(XML_ROW_HOURS_END);
      xmlDoc.append(XML_ROW_COMMENT_START);
      xmlDoc.append(satComment);
      xmlDoc.append(XML_ROW_COMMENT_END);
      xmlDoc.append(XML_ROW_EXIST_START);
      xmlDoc.append(satExist);
      xmlDoc.append(XML_ROW_EXIST_END);
      xmlDoc.append(XML_ROW_WEEKEND_START);
      xmlDoc.append(satWeekend);
      xmlDoc.append(XML_ROW_WEEKEND_END);
      xmlDoc.append(XML_SAT_END);

      /* Sunday */
      xmlDoc.append(XML_SUN_START);
      xmlDoc.append(XML_ROW_HOURS_START);
      xmlDoc.append(sunHours);
      xmlDoc.append(XML_ROW_HOURS_END);
      xmlDoc.append(XML_ROW_COMMENT_START);
      xmlDoc.append(sunComment);
      xmlDoc.append(XML_ROW_COMMENT_END);
      xmlDoc.append(XML_ROW_EXIST_START);
      xmlDoc.append(sunExist);
      xmlDoc.append(XML_ROW_EXIST_END);
      xmlDoc.append(XML_ROW_WEEKEND_START);
      xmlDoc.append(sunWeekend);
      xmlDoc.append(XML_ROW_WEEKEND_END);
      xmlDoc.append(XML_SUN_END);

      /* Row total */
      xmlDoc.append(XML_ROW_TOTAL_START);
      xmlDoc.append(rowSum);
      xmlDoc.append(XML_ROW_TOTAL_END);
      xmlDoc.append(XML_ROW_END);
    }
  }

  /**
   * @return Returns the friWeekend.
   */
  public boolean isFriWeekend() {
    return friWeekend;
  }

  /**
   * @return Returns the monWeekend.
   */
  public boolean isMonWeekend() {
    return monWeekend;
  }

  /**
   * @return Returns the satWeekend.
   */
  public boolean isSatWeekend() {
    return satWeekend;
  }

  /**
   * @return Returns the sunWeekend.
   */
  public boolean isSunWeekend() {
    return sunWeekend;
  }

  /**
   * @return Returns the thuWeekend.
   */
  public boolean isThuWeekend() {
    return thuWeekend;
  }

  /**
   * @return Returns the tueWeekend.
   */
  public boolean isTueWeekend() {
    return tueWeekend;
  }

  /**
   * @return Returns the wedWeekend.
   */
  public boolean isWedWeekend() {
    return wedWeekend;
  }

  /**
   * @return Returns the sunExist.
   */
  public boolean isSunExist() {
    return sunExist;
  }

  /**
   * @param sunExist
   *          The sunExist to set.
   */
  public void setSunExist(boolean sunExist) {
    this.sunExist = sunExist;
  }

  /**
   * @return Returns the friExist.
   */
  public boolean isFriExist() {
    return friExist;
  }

  /**
   * @return Returns the monExist.
   */
  public boolean isMonExist() {
    return monExist;
  }

  /**
   * @return Returns the satExist.
   */
  public boolean isSatExist() {
    return satExist;
  }

  /**
   * @return Returns the thuExist.
   */
  public boolean isThuExist() {
    return thuExist;
  }

  /**
   * @return Returns the tueExist.
   */
  public boolean isTueExist() {
    return tueExist;
  }

  /**
   * @return Returns the wedExist.
   */
  public boolean isWedExist() {
    return wedExist;
  }
}