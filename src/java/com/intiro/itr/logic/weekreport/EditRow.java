/**
 * Title:         ITR
 * Description:
 * Copyright:     Copyright (c) 2001
 * Company:       Intiro Development AB
 * @author        Daniel Kjall
 * @version       1.0
 */
package com.intiro.itr.logic.weekreport;

import com.intiro.itr.util.ITRCalendar;
import com.intiro.itr.util.combos.TimeTypeCombo;
import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.itr.util.xml.XMLBuilderException;
import com.intiro.itr.util.log.IntiroLog;

public class EditRow extends Row {

  //~ Instance/static variables ........................................................................................

  protected static final String XML_EDITROW_END = "</editrow>";
  protected static final String XML_EDITROW_START = "<editrow>";
  protected SumRows sumRows = null;
  protected TimeTypeCombo timeTypeCombo = null;

  //~ Constructors .....................................................................................................

  /**
   * Constructor I for EditRow.
   * Creates an EditRow.
   *
   * @param   userProfile   the users UserProfile.
   * @param   fromDate     a ITRCalendar with the start date.
   * @param   toDate       a ITRCalendar with the end date.
   * @exception   throws XMLBuilderException if something goes wrong.
   */
  public EditRow(UserProfile userProfile, ITRCalendar fromDate, ITRCalendar toDate, SumRows sumRows) throws XMLBuilderException {
    super(userProfile, fromDate, toDate);
    timeTypeCombo = new TimeTypeCombo(userProfile);
    timeTypeCombo.load();
    timeTypeCombo.setStartEndTags(XML_ROW_TIMETYPE_START, XML_ROW_TIMETYPE_END);
    this.sumRows = sumRows;
    calculateDayStatus(sumRows);
    if (timeTypeId != null && timeTypeId.length() > 0) {
      timeTypeCombo.setSelectedValue(timeTypeId);
    }
  }

  //~ Methods ..........................................................................................................

  public Row cloneToRow() throws XMLBuilderException {
    Row retval = new Row(userProfile, getFromDate(), getToDate());
    retval.setMonday(getMonday());
    retval.setTuesday(getTuesday());
    retval.setWednesday(getWednesday());
    retval.setThursday(getThursday());
    retval.setFriday(getFriday());
    retval.setSaturday(getSaturday());
    retval.setSunday(getSunday());
    retval.setProject(getProject().cloneProject());
    retval.setTimeTypeId(getTimeTypeId());
    retval.setTimeType(getTimeType());
    retval.setFromDate(getFromDate().cloneCalendar());
    retval.setToDate(getToDate().cloneCalendar());
    retval.setRowSum();

    //exist
    retval.monExist = monExist;
    retval.tueExist = tueExist;
    retval.wedExist = wedExist;
    retval.thuExist = thuExist;
    retval.friExist = friExist;
    retval.satExist = satExist;
    retval.sunExist = sunExist;

    //weekend
    retval.monWeekend = monWeekend;
    retval.tueWeekend = tueWeekend;
    retval.wedWeekend = wedWeekend;
    retval.thuWeekend = thuWeekend;
    retval.friWeekend = friWeekend;
    retval.satWeekend = satWeekend;
    retval.sunWeekend = sunWeekend;

    return retval;
  }

  public void load(Row row) throws XMLBuilderException {
    this.setMonday(row.getMonday());
    this.setTuesday(row.getTuesday());
    this.setWednesday(row.getWednesday());
    this.setThursday(row.getThursday());
    this.setFriday(row.getFriday());
    this.setSaturday(row.getSaturday());
    this.setSunday(row.getSunday());
    this.setProject(row.getProject().cloneProject());
    this.setTimeTypeId(row.getTimeTypeId());
    this.setTimeType(row.getTimeType());
    this.setFromDate(row.getFromDate().cloneCalendar());
    this.setToDate(row.getToDate().cloneCalendar());
  }

  public void toXML(StringBuffer xmlDoc) throws Exception {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".toXML(): Entering");
    }

    xmlDoc.append(XML_EDITROW_START);
    super.project.toXML(xmlDoc);

    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".toXML(): timeTypeId = " + timeTypeId);
    }
    if (timeTypeId != null && timeTypeId.length() > 0) {
      timeTypeCombo.setSelectedValue(timeTypeId);
    }

    timeTypeCombo.toXML(xmlDoc);

    //IntiroLog.detail(getClass().getName()+".toXML(): before monday");

    /*Monday*/
    xmlDoc.append(XML_MON_START);
    xmlDoc.append(XML_ROW_HOURS_START);
    xmlDoc.append(monHours);
    xmlDoc.append(XML_ROW_HOURS_END);
    xmlDoc.append(XML_ROW_EXIST_START);
    xmlDoc.append(monExist);
    xmlDoc.append(XML_ROW_EXIST_END);
    xmlDoc.append(XML_ROW_WEEKEND_START);
    xmlDoc.append(monWeekend);
    xmlDoc.append(XML_ROW_WEEKEND_END);
    xmlDoc.append(XML_MON_END);

    //IntiroLog.detail(getClass().getName()+".toXML(): Before tuesday");

    /*Tuesday*/
    xmlDoc.append(XML_TUE_START);
    xmlDoc.append(XML_ROW_HOURS_START);
    xmlDoc.append(tueHours);
    xmlDoc.append(XML_ROW_HOURS_END);
    xmlDoc.append(XML_ROW_EXIST_START);
    xmlDoc.append(tueExist);
    xmlDoc.append(XML_ROW_EXIST_END);
    xmlDoc.append(XML_ROW_WEEKEND_START);
    xmlDoc.append(tueWeekend);
    xmlDoc.append(XML_ROW_WEEKEND_END);
    xmlDoc.append(XML_TUE_END);

    //IntiroLog.detail(getClass().getName()+".toXML(): before wednesday");

    /*Wednesday*/
    xmlDoc.append(XML_WED_START);
    xmlDoc.append(XML_ROW_HOURS_START);
    xmlDoc.append(wedHours);
    xmlDoc.append(XML_ROW_HOURS_END);
    xmlDoc.append(XML_ROW_EXIST_START);
    xmlDoc.append(wedExist);
    xmlDoc.append(XML_ROW_EXIST_END);
    xmlDoc.append(XML_ROW_WEEKEND_START);
    xmlDoc.append(wedWeekend);
    xmlDoc.append(XML_ROW_WEEKEND_END);
    xmlDoc.append(XML_WED_END);

    //IntiroLog.detail(getClass().getName()+".toXML(): before thursday");

    /*Thursday*/
    xmlDoc.append(XML_THU_START);
    xmlDoc.append(XML_ROW_HOURS_START);
    xmlDoc.append(thuHours);
    xmlDoc.append(XML_ROW_HOURS_END);
    xmlDoc.append(XML_ROW_EXIST_START);
    xmlDoc.append(thuExist);
    xmlDoc.append(XML_ROW_EXIST_END);
    xmlDoc.append(XML_ROW_WEEKEND_START);
    xmlDoc.append(thuWeekend);
    xmlDoc.append(XML_ROW_WEEKEND_END);
    xmlDoc.append(XML_THU_END);

    //IntiroLog.detail(getClass().getName()+".toXML(): before friday");

    /*Friday*/
    xmlDoc.append(XML_FRI_START);
    xmlDoc.append(XML_ROW_HOURS_START);
    xmlDoc.append(friHours);
    xmlDoc.append(XML_ROW_HOURS_END);
    xmlDoc.append(XML_ROW_EXIST_START);
    xmlDoc.append(friExist);
    xmlDoc.append(XML_ROW_EXIST_END);
    xmlDoc.append(XML_ROW_WEEKEND_START);
    xmlDoc.append(friWeekend);
    xmlDoc.append(XML_ROW_WEEKEND_END);
    xmlDoc.append(XML_FRI_END);

    //IntiroLog.detail(getClass().getName()+".toXML(): before saturday");

    /*Saturday*/
    xmlDoc.append(XML_SAT_START);
    xmlDoc.append(XML_ROW_HOURS_START);
    xmlDoc.append(satHours);
    xmlDoc.append(XML_ROW_HOURS_END);
    xmlDoc.append(XML_ROW_EXIST_START);
    xmlDoc.append(satExist);
    xmlDoc.append(XML_ROW_EXIST_END);
    xmlDoc.append(XML_ROW_WEEKEND_START);
    xmlDoc.append(satWeekend);
    xmlDoc.append(XML_ROW_WEEKEND_END);
    xmlDoc.append(XML_SAT_END);

    //IntiroLog.detail(getClass().getName()+".toXML(): before sunday");

    /*Sunday*/
    xmlDoc.append(XML_SUN_START);
    xmlDoc.append(XML_ROW_HOURS_START);
    xmlDoc.append(sunHours);
    xmlDoc.append(XML_ROW_HOURS_END);
    xmlDoc.append(XML_ROW_EXIST_START);
    xmlDoc.append(sunExist);
    xmlDoc.append(XML_ROW_EXIST_END);
    xmlDoc.append(XML_ROW_WEEKEND_START);
    xmlDoc.append(sunWeekend);
    xmlDoc.append(XML_ROW_WEEKEND_END);
    xmlDoc.append(XML_SUN_END);
    xmlDoc.append(XML_EDITROW_END);
  }
  /**
   * @return Returns the timeTypeCombo.
   */
  public TimeTypeCombo getTimeTypeCombo() {
    return timeTypeCombo;
  }
}