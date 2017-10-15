package com.intiro.itr.logic.weekreport;

import com.intiro.itr.db.vo.CalendarWeekVO;
import java.util.ArrayList;

import com.intiro.itr.db.DBQueriesConfig;
import com.intiro.itr.db.InvocationHandlerSetting;
import com.intiro.itr.util.ITRCalendar;
import com.intiro.itr.util.NumberFormatter;
import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.itr.util.xml.XMLBuilderException;
import com.intiro.itr.util.log.IntiroLog;

public class SumRows {

  protected static final String XML_DIFFROW_END = "</diffrow>";
  protected static final String XML_DIFFROW_START = "<diffrow>";
  protected static final String XML_DIFFSUM_END = "</diffsum>";
  protected static final String XML_DIFFSUM_START = "<diffsum>";
  protected static final String XML_EXPECTEDROW_END = "</expectedrow>";
  protected static final String XML_EXPECTEDROW_START = "<expectedrow>";
  protected static final String XML_EXPECTEDSUM_END = "</expectedsum>";
  protected static final String XML_EXPECTEDSUM_START = "<expectedsum>";
  protected static final String XML_SUMMARYROWS_END = "</sumrows>";
  protected static final String XML_SUMMARYROWS_START = "<sumrows>";
  protected static final String XML_TOTALROW_END = "</totalrow>";
  protected static final String XML_TOTALROW_START = "<totalrow>";
  protected static final String XML_TOTALSUM_END = "</totalsum>";
  protected static final String XML_TOTALSUM_START = "<totalsum>";
  protected DifferenceRow differenceRow = null;
  protected ExpectedRow expectedRow = null;
  protected TotalRow totalRow = null;

  /**
   * Constructor for SumRows.
   *
   * @param userProfile
   * @param fromDate
   * @param toDate
   * @throws com.intiro.itr.util.xml.XMLBuilderException
   */
  public SumRows(UserProfile userProfile, ITRCalendar fromDate, ITRCalendar toDate) throws XMLBuilderException {
    super();
    totalRow = new TotalRow(userProfile, fromDate, toDate);
    expectedRow = new ExpectedRow(userProfile, fromDate, toDate);
    differenceRow = new DifferenceRow(userProfile, fromDate, toDate);
    expectedRow.load();
  }

  public DifferenceRow getDifferenceRow() {
    return differenceRow;
  }

  public ExpectedRow getExpectedRow() {
    return expectedRow;
  }

  public TotalRow getTotalRow() {
    return totalRow;
  }

  public void calcSum(ArrayList rows) {
    totalRow.calcSum(rows);
    differenceRow.calcSum(totalRow, expectedRow);
  }

  @Override
  public String toString() {
    String retval = null;

    if (this != null) {
      retval = getClass().getName();
    }

    return retval;
  }

  public void toXML(StringBuffer xmlDoc) {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".toXML(): Entering");
    }

    xmlDoc.append(XML_SUMMARYROWS_START);
    totalRow.toXML(xmlDoc);
    expectedRow.toXML(xmlDoc);
    differenceRow.toXML(xmlDoc);
    xmlDoc.append(XML_SUMMARYROWS_END);
  }

  public void toXMLSummary(StringBuffer xmlDoc) {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".toXMLSummary(): Entering");
    }

    xmlDoc.append(XML_SUMMARYROWS_START);
    totalRow.toXMLSummary(xmlDoc);
    expectedRow.toXMLSummary(xmlDoc);
    differenceRow.toXMLSummary(xmlDoc);
    xmlDoc.append(XML_SUMMARYROWS_END);
  }

  /**
   * Inner class that represents the expected hours row.
   */
  public class ExpectedRow extends Row {

    ExpectedRow(UserProfile profile, ITRCalendar fromDate, ITRCalendar toDate) throws XMLBuilderException {
      super(profile, fromDate, toDate);
    }

    @Override
    public void toXML(StringBuffer xmlDoc) {
      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".toXML(): Entering");
      }

      /* Expected row starts */
      xmlDoc.append(XML_EXPECTEDROW_START);

      /* Monday */
      xmlDoc.append(XML_MON_START);
      xmlDoc.append(NumberFormatter.format(monHours, 1, false));
      xmlDoc.append(XML_MON_END);

      /* Tuesday */
      xmlDoc.append(XML_TUE_START);
      xmlDoc.append(NumberFormatter.format(tueHours, 1, false));
      xmlDoc.append(XML_TUE_END);

      /* Wednesday */
      xmlDoc.append(XML_WED_START);
      xmlDoc.append(NumberFormatter.format(wedHours, 1, false));
      xmlDoc.append(XML_WED_END);

      /* Thursday */
      xmlDoc.append(XML_THU_START);
      xmlDoc.append(NumberFormatter.format(thuHours, 1, false));
      xmlDoc.append(XML_THU_END);

      /* Friday */
      xmlDoc.append(XML_FRI_START);
      xmlDoc.append(NumberFormatter.format(friHours, 1, false));
      xmlDoc.append(XML_FRI_END);

      /* Saturday */
      xmlDoc.append(XML_SAT_START);
      xmlDoc.append(NumberFormatter.format(satHours, 1, false));
      xmlDoc.append(XML_SAT_END);

      /* Sunday */
      xmlDoc.append(XML_SUN_START);
      xmlDoc.append(NumberFormatter.format(sunHours, 1, false));
      xmlDoc.append(XML_SUN_END);

      /* Week sum */
      xmlDoc.append(XML_EXPECTEDSUM_START);
      xmlDoc.append(NumberFormatter.format(rowSum, 1, false));
      xmlDoc.append(XML_EXPECTEDSUM_END);

      /* Expected row ends */
      xmlDoc.append(XML_EXPECTEDROW_END);
    }

    public void toXMLSummary(StringBuffer xmlDoc) {
      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".toXMLSummary(): Entering");
      }

      /* Expected row starts */
      xmlDoc.append(XML_EXPECTEDROW_START);

      /* Week sum */
      xmlDoc.append(XML_EXPECTEDSUM_START);
      xmlDoc.append(NumberFormatter.format(rowSum, 1, false));
      xmlDoc.append(XML_EXPECTEDSUM_END);

      /* Expected row ends */
      xmlDoc.append(XML_EXPECTEDROW_END);
    }

    void load() throws XMLBuilderException {
      try {
        String key = getFromDate().getCalendarInStoreFormat();
        String cacheKey = "getCalendarWeek_" + key;
        String statisticKey = getClass().getName() + ".load";
        int cacheTime = 3600 * 10;
        InvocationHandlerSetting s = InvocationHandlerSetting.create(cacheKey, cacheTime, statisticKey);
        CalendarWeekVO rs = DBQueriesConfig.getProxy(s).getCalendarWeek(key);

        if (rs != null) {
          setMonday(rs.getMo());
          setTuesday(rs.getTu());
          setWednesday(rs.getWe());
          setThursday(rs.getTh());
          setFriday(rs.getFr());
          setSaturday(rs.getSa());
          setSunday(rs.getSu());
          setRowSum();
        }
      } catch (Exception e) {
        IntiroLog.error(getClass(), getClass().getName() + ".load(String weekId): ERROR FROM DATABASE, exception = " + e.getMessage());
        throw new XMLBuilderException(e.getMessage());
      }
    }
  }

  /**
   * Inner class that represents the diff hours row.
   */
  public class DifferenceRow extends Row {

    DifferenceRow(UserProfile profile, ITRCalendar fromDate, ITRCalendar toDate) throws XMLBuilderException {
      super(profile, fromDate, toDate);
    }

    public void calcSum(TotalRow totalRow, ExpectedRow expectedRow) {
      setMonday(totalRow.getMonday() - expectedRow.getMonday());
      setTuesday(totalRow.getTuesday() - expectedRow.getTuesday());
      setWednesday(totalRow.getWednesday() - expectedRow.getWednesday());
      setThursday(totalRow.getThursday() - expectedRow.getThursday());
      setFriday(totalRow.getFriday() - expectedRow.getFriday());
      setSaturday(totalRow.getSaturday() - expectedRow.getSaturday());
      setSunday(totalRow.getSunday() - expectedRow.getSunday());
      setRowSum();
    }

    @Override
    public void toXML(StringBuffer xmlDoc) {
      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".toXML(): Entering");
      }

      /* Difference row starts */
      xmlDoc.append(XML_DIFFROW_START);

      /* Monday */
      xmlDoc.append(XML_MON_START);
      xmlDoc.append(NumberFormatter.format(monHours, 1, false));
      xmlDoc.append(XML_MON_END);

      /* Tuesday */
      xmlDoc.append(XML_TUE_START);
      xmlDoc.append(NumberFormatter.format(tueHours, 1, false));
      xmlDoc.append(XML_TUE_END);

      /* Wednesday */
      xmlDoc.append(XML_WED_START);
      xmlDoc.append(NumberFormatter.format(wedHours, 1, false));
      xmlDoc.append(XML_WED_END);

      /* Thursday */
      xmlDoc.append(XML_THU_START);
      xmlDoc.append(NumberFormatter.format(thuHours, 1, false));
      xmlDoc.append(XML_THU_END);

      /* Friday */
      xmlDoc.append(XML_FRI_START);
      xmlDoc.append(NumberFormatter.format(friHours, 1, false));
      xmlDoc.append(XML_FRI_END);

      /* Saturday */
      xmlDoc.append(XML_SAT_START);
      xmlDoc.append(NumberFormatter.format(satHours, 1, false));
      xmlDoc.append(XML_SAT_END);

      /* Sunday */
      xmlDoc.append(XML_SUN_START);
      xmlDoc.append(NumberFormatter.format(sunHours, 1, false));
      xmlDoc.append(XML_SUN_END);

      /* Week sum */
      xmlDoc.append(XML_DIFFSUM_START);
      xmlDoc.append(NumberFormatter.format(rowSum, 1, false));
      xmlDoc.append(XML_DIFFSUM_END);

      /* Difference row ends */
      xmlDoc.append(XML_DIFFROW_END);
    }

    public void toXMLSummary(StringBuffer xmlDoc) {
      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".toXMLSummary(): Entering");
      }

      /* Difference row starts */
      xmlDoc.append(XML_DIFFROW_START);

      /* Week sum */
      xmlDoc.append(XML_DIFFSUM_START);
      xmlDoc.append(NumberFormatter.format(rowSum, 1, false));
      xmlDoc.append(XML_DIFFSUM_END);

      /* Difference row ends */
      xmlDoc.append(XML_DIFFROW_END);
    }
  }

  public class TotalRow extends Row {

    protected double komp1Hours = 0;

    protected double komp2Hours = 0;

    protected double normalHours = 0;

    protected double overtime1Hours = 0;

    protected double overtime2Hours = 0;

    TotalRow(UserProfile profile, ITRCalendar fromDate, ITRCalendar toDate) throws XMLBuilderException {
      super(profile, fromDate, toDate);
    }

    public double getComp1Hours() {
      return komp1Hours;
    }

    public double getComp2Hours() {
      return komp2Hours;
    }

    public double getNormalHours() {
      return normalHours;
    }

    public double getOvertime1Hours() {
      return overtime1Hours;
    }

    public double getOvertime2Hours() {
      return overtime2Hours;
    }

    /**
     * Get overtime hours for week, that is taken out in money.
     *
     * @return
     */
    public double getOvertimeMoneyHoursForWeek() {
      double retval = 0;
      retval += (getOvertime1Hours());
      retval += (getOvertime2Hours());

      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".getMoneyOvertimeHoursForWeek(): retval = " + retval);
      }

      return retval;
    }

    /**
     * Get overtime hours for week, that is taken out in vacation.
     *
     * @return
     */
    public double getOvertimeVacationHoursForWeek() {
      double retval = 0;
      retval += (getComp1Hours());
      retval += (getComp2Hours());

      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".getVacationOvertimeHoursForWeek(): retval = " + retval);
      }

      return retval;
    }

    public void addToKomp1Hours(double hours) {

      // IntiroLog.detail(getClass().getName()+".addToKomp1Hours(): before komp1Hours = "+komp1Hours);
      komp1Hours += hours;

      // IntiroLog.detail(getClass().getName()+".addToKomp1Hours(): after komp1Hours = "+komp1Hours);
    }

    public void addToKomp2Hours(double hours) {

      // IntiroLog.detail(getClass().getName()+".addToKomp2Hours(): before komp2Hours = "+komp2Hours);
      komp2Hours += hours;

      // IntiroLog.detail(getClass().getName()+".addToKomp2Hours(): after komp2Hours = "+komp2Hours);
    }

    public void addToNormalHours(double hours) {

      // IntiroLog.detail(getClass().getName()+".addToNormalHours(): before normalHours = "+normalHours);
      normalHours += hours;

      // IntiroLog.detail(getClass().getName()+".addToNormalHours(): after normalHours = "+normalHours);
    }

    public void addToOvertime1Hours(double hours) {

      // IntiroLog.detail(getClass().getName()+".addToOvertime1Hours(): before overtime1Hours = "+overtime1Hours);
      overtime1Hours += hours;

      // IntiroLog.detail(getClass().getName()+".addToOvertime1Hours(): after overtime1Hours = "+overtime1Hours);
    }

    public void addToOvertime2Hours(double hours) {

      // IntiroLog.detail(getClass().getName()+".addToOvertime2Hours(): before overtime2Hours = "+overtime2Hours);
      overtime2Hours += hours;

      // IntiroLog.detail(getClass().getName()+".addToOvertime2Hours(): after overtime2Hours = "+overtime2Hours);
    }

    public void calcSum(ArrayList rows) {

      /* Clear the hours before adding */
      this.clearHours();

      /* Rows in week */
      Row oneRow;

      /* Loop through all rows */
      for (int i = 0; i < rows.size(); i++) {
        oneRow = (Row) rows.get(i);

        /* Check to see that row still there */
        if (oneRow != null && !oneRow.isRemoved()) {
          if (oneRow.isNormalHours()) {
            addToNormalHours(oneRow.getRowSum());
          } else if (oneRow.isOvertime1Hours()) {
            addToOvertime1Hours(oneRow.getRowSum());
          } else if (oneRow.isOvertime2Hours()) {
            addToOvertime2Hours(oneRow.getRowSum());
          } else if (oneRow.isComp1Hours()) {
            addToKomp1Hours(oneRow.getRowSum());
          } else if (oneRow.isComp2Hours()) {
            addToKomp2Hours(oneRow.getRowSum());
          }

          addToMonday(oneRow.getMonday());
          addToTuesday(oneRow.getTuesday());
          addToWednesday(oneRow.getWednesday());
          addToThursday(oneRow.getThursday());
          addToFriday(oneRow.getFriday());
          addToSaturday(oneRow.getSaturday());
          addToSunday(oneRow.getSunday());
        }
      }

      setRowSum();
    }

    /**
     * Clears the hours in the row. Overrides the Row.clearHours().
     */
    @Override
    public void clearHours() {
      super.clearHours();
      normalHours = 0;
      overtime1Hours = 0;
      overtime2Hours = 0;
      komp1Hours = 0;
      komp2Hours = 0;
    }

    @Override
    public void toXML(StringBuffer xmlDoc) {
      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".toXML(): Entering");
      }

      xmlDoc.append(XML_TOTALROW_START);

      /* Monday */
      xmlDoc.append(XML_MON_START);
      xmlDoc.append(NumberFormatter.format(monHours, 1, false));
      xmlDoc.append(XML_MON_END);

      /* Tuesday */
      xmlDoc.append(XML_TUE_START);
      xmlDoc.append(NumberFormatter.format(tueHours, 1, false));
      xmlDoc.append(XML_TUE_END);

      /* Wednesday */
      xmlDoc.append(XML_WED_START);
      xmlDoc.append(NumberFormatter.format(wedHours, 1, false));
      xmlDoc.append(XML_WED_END);

      /* Thursday */
      xmlDoc.append(XML_THU_START);
      xmlDoc.append(NumberFormatter.format(thuHours, 1, false));
      xmlDoc.append(XML_THU_END);

      /* Friday */
      xmlDoc.append(XML_FRI_START);
      xmlDoc.append(NumberFormatter.format(friHours, 1, false));
      xmlDoc.append(XML_FRI_END);

      /* Saturday */
      xmlDoc.append(XML_SAT_START);
      xmlDoc.append(NumberFormatter.format(satHours, 1, false));
      xmlDoc.append(XML_SAT_END);

      /* Sunday */
      xmlDoc.append(XML_SUN_START);
      xmlDoc.append(NumberFormatter.format(sunHours, 1, false));
      xmlDoc.append(XML_SUN_END);

      /* Total sum */
      xmlDoc.append(XML_TOTALSUM_START);
      xmlDoc.append(NumberFormatter.format(rowSum, 1, false));
      xmlDoc.append(XML_TOTALSUM_END);

      /* total row end */
      xmlDoc.append(XML_TOTALROW_END);
    }

    public void toXMLSummary(StringBuffer xmlDoc) {
      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".toXMLSummary(): Entering");
      }

      xmlDoc.append(XML_TOTALROW_START);

      /* Monday */
      xmlDoc.append(XML_MON_START);
      xmlDoc.append(NumberFormatter.format(monHours, 1, false));
      xmlDoc.append(XML_MON_END);

      /* Tuesday */
      xmlDoc.append(XML_TUE_START);
      xmlDoc.append(NumberFormatter.format(tueHours, 1, false));
      xmlDoc.append(XML_TUE_END);

      /* Wednesday */
      xmlDoc.append(XML_WED_START);
      xmlDoc.append(NumberFormatter.format(wedHours, 1, false));
      xmlDoc.append(XML_WED_END);

      /* Thursday */
      xmlDoc.append(XML_THU_START);
      xmlDoc.append(NumberFormatter.format(thuHours, 1, false));
      xmlDoc.append(XML_THU_END);

      /* Friday */
      xmlDoc.append(XML_FRI_START);
      xmlDoc.append(NumberFormatter.format(friHours, 1, false));
      xmlDoc.append(XML_FRI_END);

      /* Saturday */
      xmlDoc.append(XML_SAT_START);
      xmlDoc.append(NumberFormatter.format(satHours, 1, false));
      xmlDoc.append(XML_SAT_END);

      /* Sunday */
      xmlDoc.append(XML_SUN_START);
      xmlDoc.append(NumberFormatter.format(sunHours, 1, false));
      xmlDoc.append(XML_SUN_END);

      /* Total sum */
      xmlDoc.append(XML_TOTALSUM_START);
      xmlDoc.append(NumberFormatter.format(rowSum, 1, false));
      xmlDoc.append(XML_TOTALSUM_END);

      /* total row end */
      xmlDoc.append(XML_TOTALROW_END);
    }
  }
}
