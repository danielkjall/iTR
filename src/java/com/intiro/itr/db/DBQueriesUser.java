package com.intiro.itr.db;

import com.intiro.itr.util.ITRCalendar;
import com.intiro.itr.util.ItrUtil;
import com.intiro.itr.util.StringRecordset;
import com.intiro.itr.util.log.IntiroLog;
import java.lang.reflect.Proxy;

public class DBQueriesUser implements DBQueriesUserInterface, DBConstants {

  private static ThreadLocal<InvocationHandlerSetting> m_personalInvocationSetting = new ThreadLocal<>();

  @Override
  public void setCallSetup(InvocationHandlerSetting s) {
    m_personalInvocationSetting.set(s);
  }

  @Override
  public InvocationHandlerSetting getCallSetup() {
    InvocationHandlerSetting retval = new InvocationHandlerSetting();

    if (m_personalInvocationSetting.get() == null) {
      IntiroLog.warning(DBExecute.class, "Call has NOT been setup from caller. No caching will be performed in invocationhandler");
      return retval;
    }

    retval.setCacheKey(m_personalInvocationSetting.get().getCacheKey());
    retval.setCacheTimeInSeconds(m_personalInvocationSetting.get().getCacheTimeInSeconds());
    retval.setStatisticAction(m_personalInvocationSetting.get().getAction());
    m_personalInvocationSetting.set(null);
    m_personalInvocationSetting.remove();

    return retval;
  }

  protected DBQueriesUser() {
  }

  public static DBQueriesUserInterface getProxy(InvocationHandlerSetting s) {
    DBQueriesUser db = new DBQueriesUser();
    db.setCallSetup(s);
    return (DBQueriesUserInterface) Proxy.newProxyInstance(db.getClass().getClassLoader(), new Class<?>[]{DBQueriesUserInterface.class}, new ItrInvocationHandler(db));
  }

  @Override
  public StringRecordset getWeeksAlreadySubmittedAsStartDate(String userId) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT distinct ");
    sb.append("cw.FromDate ");
    sb.append("FROM ");
    sb.append("ITR_CalendarWeek as cw ");
    sb.append("INNER JOIN ITR_UserWeek as uw on (cw.id = uw.ITR_CalendarWeekId) ");
    sb.append("INNER JOIN ITR_EntryRow as er on (uw.id = er.ITR_UserWeekId) ");
    sb.append("WHERE   ");
    sb.append("er.ITR_UserId = ").append(userId).append(" ");
    sb.append("AND uw.Submitted = true ");
    sb.append("order by fromdate asc ");

    DBConnect access = new DBConnect();
    StringRecordset rs = access.executeQuery(sb);
    return rs;
  }

  @Override
  public StringRecordset getRowEntryForUser(String userId, String rowEntryId) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT ");
    sb.append("*");
    sb.append(" FROM ");
    sb.append(TABLE_ENTRYROW);
    sb.append(" WHERE ");
    sb.append(TABLE_ENTRYROW_DOT + ENTRYROW_ID_PK + " = ").append(rowEntryId);
    sb.append(" AND ");
    sb.append(TABLE_ENTRYROW_DOT + ENTRYROW_USERID_FK + " = ").append(userId);

    DBConnect access = new DBConnect();
    StringRecordset rs = access.executeQuery(sb);
    return rs;
  }

  @Override
  public StringRecordset getRowsInUserWeek(String userId, String fromDate) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT ");
    sb.append(TABLE_ENTRYROW_DOT + ENTRYROW_MO_HOURS + COMMA);
    sb.append(TABLE_ENTRYROW_DOT + ENTRYROW_TU_HOURS + COMMA);
    sb.append(TABLE_ENTRYROW_DOT + ENTRYROW_WE_HOURS + COMMA);
    sb.append(TABLE_ENTRYROW_DOT + ENTRYROW_TH_HOURS + COMMA);
    sb.append(TABLE_ENTRYROW_DOT + ENTRYROW_FR_HOURS + COMMA);
    sb.append(TABLE_ENTRYROW_DOT + ENTRYROW_SA_HOURS + COMMA);
    sb.append(TABLE_ENTRYROW_DOT + ENTRYROW_SU_HOURS + COMMA);
    sb.append(TABLE_ENTRYROW_DOT + ENTRYROW_HOURS_SUM + COMMA);
    sb.append(TABLE_ENTRYROW_DOT + ENTRYROW_ID_PK + " as Eid " + COMMA);
    sb.append(TABLE_ENTRYROW_DOT + ENTRYROW_PROJECTCODEID_FK + COMMA);
    sb.append(TABLE_ENTRYROW_DOT + ENTRYROW_PROJECTID_FK + COMMA);
    sb.append(TABLE_ENTRYROW_DOT + ENTRYROW_TIMETYPEID_FK + COMMA);
    sb.append(TABLE_ENTRYROW_DOT + ENTRYROW_USERID_FK + COMMA);
    sb.append(TABLE_ENTRYROW_DOT + ENTRYROW_USERWEEKID_FK + COMMA);
    sb.append(TABLE_TIMETYPE_DOT + TIMETYPE_TYPE + COMMA);
    sb.append(TABLE_USERWEEK_DOT + "*" + COMMA);
    sb.append(TABLE_PROJECTCODE_DOT + PROJECTCODE_CODE);
    sb.append(" FROM ");
    sb.append(TABLE_USERWEEK + COMMA);
    sb.append(TABLE_CALENDARWEEK + COMMA);
    sb.append(TABLE_TIMETYPE + COMMA);
    sb.append(TABLE_PROJECTCODE + COMMA);
    sb.append(TABLE_ENTRYROW);
    sb.append(" WHERE ");
    sb.append("(");

    /* Connect userweek and calendarweek */
    sb.append(TABLE_USERWEEK_DOT + USERWEEK_CALENDARWEEK_ID_FK + " = " + TABLE_CALENDARWEEK_DOT + CALENDARWEEK_ID_PK);
    sb.append(" AND ");

    /* Connect userweek and entryrow */
    sb.append(TABLE_USERWEEK_DOT + USERWEEK_ID_PK + " = " + TABLE_ENTRYROW_DOT + ENTRYROW_USERWEEKID_FK);
    sb.append(" AND ");

    /* It must be this user that made them */
    sb.append(TABLE_ENTRYROW_DOT + ENTRYROW_USERID_FK + " = ").append(userId);
    sb.append(" AND ");

    if (fromDate != null) {
      /* the week must have a from date that is = supplied fromDate */
      sb.append(TABLE_CALENDARWEEK_DOT + CALENDARWEEK_FROM_DATE + " = '").append(fromDate).append("'");
      sb.append(" AND ");
    }

    /* the timetypeid is the same */
    sb.append(TABLE_TIMETYPE_DOT + TIMETYPE_ID_PK + " = " + TABLE_ENTRYROW_DOT + ENTRYROW_TIMETYPEID_FK);
    sb.append(" AND ");

    /* the timetypeid is the same */
    sb.append(TABLE_ENTRYROW_DOT + ENTRYROW_PROJECTCODEID_FK + " = " + TABLE_PROJECTCODE_DOT + PROJECTCODE_ID_PK);
    sb.append(")");

    DBConnect access = new DBConnect();
    StringRecordset rs = access.executeQuery(sb);
    return rs;
  }

  @Override
  public StringRecordset getAllRowsInUserWeek() throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT ");
    sb.append(TABLE_ENTRYROW_DOT + ENTRYROW_MO_HOURS + COMMA);
    sb.append(TABLE_ENTRYROW_DOT + ENTRYROW_TU_HOURS + COMMA);
    sb.append(TABLE_ENTRYROW_DOT + ENTRYROW_WE_HOURS + COMMA);
    sb.append(TABLE_ENTRYROW_DOT + ENTRYROW_TH_HOURS + COMMA);
    sb.append(TABLE_ENTRYROW_DOT + ENTRYROW_FR_HOURS + COMMA);
    sb.append(TABLE_ENTRYROW_DOT + ENTRYROW_SA_HOURS + COMMA);
    sb.append(TABLE_ENTRYROW_DOT + ENTRYROW_SU_HOURS + COMMA);
    sb.append(TABLE_ENTRYROW_DOT + ENTRYROW_HOURS_SUM + COMMA);
    sb.append(TABLE_ENTRYROW_DOT + ENTRYROW_ID_PK + " as Eid " + COMMA);
    sb.append(TABLE_ENTRYROW_DOT + ENTRYROW_PROJECTCODEID_FK + COMMA);
    sb.append(TABLE_ENTRYROW_DOT + ENTRYROW_PROJECTID_FK + COMMA);
    sb.append(TABLE_ENTRYROW_DOT + ENTRYROW_TIMETYPEID_FK + COMMA);
    sb.append(TABLE_ENTRYROW_DOT + ENTRYROW_USERID_FK + COMMA);
    sb.append(TABLE_ENTRYROW_DOT + ENTRYROW_USERWEEKID_FK + COMMA);
    sb.append(TABLE_TIMETYPE_DOT + TIMETYPE_TYPE + COMMA);
    sb.append(TABLE_USERWEEK_DOT + "*" + COMMA);
    sb.append(TABLE_PROJECTCODE_DOT + PROJECTCODE_CODE + COMMA);
    sb.append(TABLE_CALENDARWEEK_DOT + CALENDARWEEK_FROM_DATE);
    sb.append(" FROM ");
    sb.append(TABLE_USERWEEK + COMMA);
    sb.append(TABLE_CALENDARWEEK + COMMA);
    sb.append(TABLE_TIMETYPE + COMMA);
    sb.append(TABLE_PROJECTCODE + COMMA);
    sb.append(TABLE_ENTRYROW);
    sb.append(" WHERE ");
    sb.append("(");

    /* Connect userweek and calendarweek */
    sb.append(TABLE_USERWEEK_DOT + USERWEEK_CALENDARWEEK_ID_FK + " = " + TABLE_CALENDARWEEK_DOT + CALENDARWEEK_ID_PK);
    sb.append(" AND ");

    /* Connect userweek and entryrow */
    sb.append(TABLE_USERWEEK_DOT + USERWEEK_ID_PK + " = " + TABLE_ENTRYROW_DOT + ENTRYROW_USERWEEKID_FK);
    sb.append(" AND ");

    /* the timetypeid is the same */
    sb.append(TABLE_TIMETYPE_DOT + TIMETYPE_ID_PK + " = " + TABLE_ENTRYROW_DOT + ENTRYROW_TIMETYPEID_FK);
    sb.append(" AND ");

    /* the timetypeid is the same */
    sb.append(TABLE_ENTRYROW_DOT + ENTRYROW_PROJECTCODEID_FK + " = " + TABLE_PROJECTCODE_DOT + PROJECTCODE_ID_PK);
    sb.append(")");
    sb.append(" ORDER BY ");
    sb.append(TABLE_ENTRYROW_DOT + ENTRYROW_USERID_FK + COMMA);
    sb.append(TABLE_ENTRYROW_DOT + ENTRYROW_USERID_FK);

    DBConnect access = new DBConnect();
    StringRecordset rs = access.executeQuery(sb);
    return rs;
  }

  @Override
  public StringRecordset getSubmittedYears(String userId) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT distinct ");
    sb.append("YEAR(" + TABLE_CALENDARWEEK_DOT + CALENDARWEEK_FROM_DATE + ") as theYear, count(" + TABLE_CALENDARWEEK_DOT + CALENDARWEEK_FROM_DATE + ") as quantity ");
    sb.append(" FROM ");
    sb.append(TABLE_USERWEEK + COMMA);
    sb.append(TABLE_CALENDARWEEK + COMMA);
    sb.append(TABLE_ENTRYROW);
    sb.append(" WHERE (");

    /* Connect userweek and calendarweek */
    sb.append(TABLE_USERWEEK_DOT + USERWEEK_CALENDARWEEK_ID_FK + " = " + TABLE_CALENDARWEEK_DOT + CALENDARWEEK_ID_PK);
    sb.append(" AND ");

    /* Connect userweek and entryrow */
    sb.append(TABLE_USERWEEK_DOT + USERWEEK_ID_PK + " = " + TABLE_ENTRYROW_DOT + ENTRYROW_USERWEEKID_FK);

    if (userId.length() > 0) {
      sb.append(" AND ");
      /* It must be this user that made them */
      sb.append(TABLE_ENTRYROW_DOT + ENTRYROW_USERID_FK + " = ").append(userId);
    }
    sb.append(")");
    /* Order by weekno and weekpart */
    sb.append(" GROUP BY ");
    sb.append("YEAR(" + TABLE_CALENDARWEEK_DOT + CALENDARWEEK_FROM_DATE + ")");

    sb.append(" ORDER BY ");
    sb.append("YEAR(" + TABLE_CALENDARWEEK_DOT + CALENDARWEEK_FROM_DATE + ")");
    sb.append(" DESC");

    DBConnect access = new DBConnect();
    StringRecordset rs = access.executeQuery(sb);
    return rs;
  }

  @Override
  public StringRecordset getSubmittedWeeks(String userId, boolean descendingSortOrder, boolean checkIfTheyAreNotApproved) throws Exception {
    return getSubmittedWeeks(userId, "", descendingSortOrder, checkIfTheyAreNotApproved);
  }

  @Override
  public StringRecordset getSubmittedWeeks(String userId, String year, boolean descendingSortOrder, boolean checkIfTheyAreNotApproved) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT ");
    sb.append(TABLE_CALENDARWEEK_DOT + "* ");
    sb.append(" FROM ");
    sb.append(TABLE_USERWEEK + COMMA);
    sb.append(TABLE_CALENDARWEEK + COMMA);
    sb.append(TABLE_ENTRYROW);
    sb.append(" WHERE (");

    /* Connect userweek and calendarweek */
    sb.append(TABLE_USERWEEK_DOT + USERWEEK_CALENDARWEEK_ID_FK + " = " + TABLE_CALENDARWEEK_DOT + CALENDARWEEK_ID_PK);
    sb.append(" AND ");

    /* Connect userweek and entryrow */
    sb.append(TABLE_USERWEEK_DOT + USERWEEK_ID_PK + " = " + TABLE_ENTRYROW_DOT + ENTRYROW_USERWEEKID_FK);
    sb.append(" AND ");

    /* they must be submitted */
    sb.append(TABLE_USERWEEK_DOT + USERWEEK_SUBMITTED + " = " + TRUE_ACCESS);

    if (checkIfTheyAreNotApproved) {
      sb.append(" AND ");

      /* they must not have been approved */
      sb.append(TABLE_USERWEEK_DOT + USERWEEK_APPROVED + " = " + FALSE_ACCESS);
    }

    if (year.length() > 0) {
      /* select specified year */
      sb.append(" AND ");
      sb.append("YEAR(" + TABLE_CALENDARWEEK_DOT + CALENDARWEEK_FROM_DATE + ") = '").append(year).append("'");
    }

    sb.append(" AND ");

    /* It must be this user that made them */
    sb.append(TABLE_ENTRYROW_DOT + ENTRYROW_USERID_FK + " = ").append(userId);

    /* Order by weekno and weekpart */
    sb.append(") ORDER BY ");
    sb.append(TABLE_CALENDARWEEK_DOT + CALENDARWEEK_FROM_DATE);

    if (descendingSortOrder) {
      sb.append(" DESC ");
    }

    DBConnect access = new DBConnect();
    StringRecordset rs = access.executeQuery(sb);
    return rs;
  }

  @Override
  public StringRecordset getSubmittedWeeksThick(String userId, String year) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT ");
    sb.append("  ITR_EntryRow.Id as Eid  ");
    sb.append(", ITR_EntryRow.ITR_UserWeekId ");
    sb.append(", ITR_EntryRow.HoursMo ");
    sb.append(", ITR_EntryRow.HoursTu ");
    sb.append(", ITR_EntryRow.HoursWe ");
    sb.append(", ITR_EntryRow.HoursTh ");
    sb.append(", ITR_EntryRow.HoursFr ");
    sb.append(", ITR_EntryRow.HoursSa ");
    sb.append(", ITR_EntryRow.HoursSu ");
    sb.append(", ITR_EntryRow.HoursSum ");
    sb.append(", ITR_EntryRow.ITR_ProjectCodeId ");
    sb.append(", ITR_EntryRow.ITR_ProjectId ");
    sb.append(", ITR_EntryRow.ITR_TimeTypeId ");
    sb.append(", ITR_EntryRow.ITR_UserId ");
    sb.append(", ITR_TimeType.Type ");
    sb.append(", ITR_UserWeek.* ");
    sb.append(", ITR_ProjectCode.Code ");
    sb.append(", ITR_CalendarWeek.FromDate ");
    sb.append(", ITR_CalendarWeek.ToDate ");
    sb.append("FROM  ");
    sb.append("  ITR_UserWeek ");
    sb.append(", ITR_CalendarWeek ");
    sb.append(", ITR_EntryRow  ");
    sb.append(", ITR_TimeType ");
    sb.append(", ITR_ProjectCode ");
    sb.append("WHERE  ");
    sb.append("    ITR_UserWeek.ITR_CalendarWeekId = ITR_CalendarWeek.Id  ");
    sb.append("AND ITR_UserWeek.Id = ITR_EntryRow.ITR_UserWeekId  ");
    
    if (ItrUtil.isStrContainingValue(year)) {
      /* select specified year */
      sb.append("AND YEAR(" + TABLE_CALENDARWEEK_DOT + CALENDARWEEK_FROM_DATE + ") = '").append(year).append("' ");
    }

    sb.append("AND ITR_EntryRow.ITR_UserId = ").append(userId).append(" ");
    sb.append("AND ITR_TimeType.Id = ITR_EntryRow.ITR_TimeTypeId  ");
    sb.append("AND ITR_EntryRow.ITR_ProjectCodeId = ITR_ProjectCode.Id ");
    sb.append("AND ITR_UserWeek.Submitted =  True ");

    /* Order by weekno and weekpart */
    sb.append("ORDER BY ");
    sb.append("itr_userweekid ");

    DBConnect access = new DBConnect();
    StringRecordset rs = access.executeQuery(sb);
    return rs;
  }

  public StringRecordset getApprovedWeeks(String userId) throws Exception {
    return getApprovedWeeks(userId, "");
  }

  @Override
  public StringRecordset getApprovedWeeks(String userId, String year) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT ");
    sb.append(TABLE_CALENDARWEEK_DOT + "* ");
    sb.append(" FROM ");
    sb.append(TABLE_USERWEEK + COMMA);
    sb.append(TABLE_CALENDARWEEK + COMMA);
    sb.append(TABLE_ENTRYROW);
    sb.append(" WHERE (");

    /* Connect userweek and calendarweek */
    sb.append(TABLE_USERWEEK_DOT + USERWEEK_CALENDARWEEK_ID_FK + " = " + TABLE_CALENDARWEEK_DOT + CALENDARWEEK_ID_PK);
    sb.append(" AND ");

    /* Connect userweek and entryrow */
    sb.append(TABLE_USERWEEK_DOT + USERWEEK_ID_PK + " = " + TABLE_ENTRYROW_DOT + ENTRYROW_USERWEEKID_FK);
    sb.append(" AND ");

    /* they must have been approved */
    sb.append(TABLE_USERWEEK_DOT + USERWEEK_APPROVED + " = " + TRUE_ACCESS);

    if (ItrUtil.isStrContainingValue(year)) {
      sb.append(" AND ");
      if (year.length() > 4) {
        sb.append(TABLE_CALENDARWEEK_DOT + CALENDARWEEK_FROM_DATE + " > '").append(year).append("'");
      } else {
        sb.append("YEAR(" + TABLE_CALENDARWEEK_DOT + CALENDARWEEK_FROM_DATE + ") = '").append(year).append("'");
      }
    }
    sb.append(" AND ");

    /* It must be this user that made them */
    sb.append(TABLE_ENTRYROW_DOT + ENTRYROW_USERID_FK + " = ").append(userId);

    /* Order by weekno and weekpart */
    sb.append(") ORDER BY ");
    sb.append(TABLE_ENTRYROW_DOT + ENTRYROW_USERID_FK + ", " + TABLE_CALENDARWEEK_DOT + CALENDARWEEK_FROM_DATE);

    DBConnect access = new DBConnect();
    StringRecordset rs = access.executeQuery(sb);
    return rs;
  }

  @Override
  public StringRecordset getAllApprovedWeeks(String year) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT ");
    sb.append(TABLE_CALENDARWEEK_DOT + "*, " + TABLE_ENTRYROW_DOT + ENTRYROW_USERID_FK);
    sb.append(" FROM ");
    sb.append(TABLE_USERWEEK + COMMA);
    sb.append(TABLE_CALENDARWEEK + COMMA);
    sb.append(TABLE_ENTRYROW);
    sb.append(" WHERE (");

    /* Connect userweek and calendarweek */
    sb.append(TABLE_USERWEEK_DOT + USERWEEK_CALENDARWEEK_ID_FK + " = " + TABLE_CALENDARWEEK_DOT + CALENDARWEEK_ID_PK);
    sb.append(" AND ");

    /* Connect userweek and entryrow */
    sb.append(TABLE_USERWEEK_DOT + USERWEEK_ID_PK + " = " + TABLE_ENTRYROW_DOT + ENTRYROW_USERWEEKID_FK);
    sb.append(" AND ");

    /* they must have been approved */
    sb.append(TABLE_USERWEEK_DOT + USERWEEK_APPROVED + " = " + TRUE_ACCESS);

    if (ItrUtil.isStrContainingValue(year)) {
      /* select specified year */
      sb.append(" AND ");
      sb.append("YEAR(" + TABLE_CALENDARWEEK_DOT + CALENDARWEEK_FROM_DATE + ") = '").append(year).append("'");
    }

    /* Order by weekno and weekpart */
    sb.append(") ORDER BY ");
    sb.append(TABLE_ENTRYROW_DOT + ENTRYROW_USERID_FK + ", " + TABLE_CALENDARWEEK_DOT + CALENDARWEEK_FROM_DATE);

    DBConnect access = new DBConnect();
    StringRecordset rs = access.executeQuery(sb);
    return rs;
  }

  @Override
  public StringRecordset getWeeksNeedingSubmit(String userId, ITRCalendar cal, String year, String stopDate) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT DISTINCT ");
    sb.append(TABLE_CALENDARWEEK_DOT + "* ");
    sb.append(" FROM ");
    sb.append(TABLE_USERWEEK + COMMA);
    sb.append(TABLE_CALENDARWEEK + COMMA);
    sb.append(TABLE_ENTRYROW);
    sb.append(" WHERE (");

    /* Connect userweek and calendarweek */
    sb.append(TABLE_USERWEEK_DOT + USERWEEK_CALENDARWEEK_ID_FK + " = " + TABLE_CALENDARWEEK_DOT + CALENDARWEEK_ID_PK);
    sb.append(" AND ");

    /* Connect userweek and entryrow */
    sb.append(TABLE_USERWEEK_DOT + USERWEEK_ID_PK + " = " + TABLE_ENTRYROW_DOT + ENTRYROW_USERWEEKID_FK);
    sb.append(" AND ");

    /* they are not approved */
    sb.append(TABLE_USERWEEK_DOT + USERWEEK_APPROVED + " = " + FALSE_ACCESS);
    sb.append(" AND ");
    /* they are not submitted */
    sb.append(TABLE_USERWEEK_DOT + USERWEEK_SUBMITTED + " = " + FALSE_ACCESS);

    sb.append(" AND ");
    sb.append(TABLE_CALENDARWEEK_DOT + CALENDARWEEK_FROM_DATE + " <= date('").append(stopDate).append("') ");

    sb.append(" AND ");
    sb.append("YEAR(" + TABLE_CALENDARWEEK_DOT + CALENDARWEEK_FROM_DATE + ") = '").append(year).append("'");
    sb.append(" AND ");
    sb.append(TABLE_ENTRYROW_DOT + ENTRYROW_USERID_FK + " = ").append(userId);

    sb.append(" ) ");

    sb.append(" UNION ");

    sb.append("SELECT DISTINCT ");
    sb.append(TABLE_CALENDARWEEK_DOT + "* ");
    sb.append(" FROM ");
    sb.append(TABLE_CALENDARWEEK);

    sb.append(" WHERE (");
    sb.append(TABLE_CALENDARWEEK_DOT + CALENDARWEEK_ID_PK);
    sb.append(" NOT IN ( ");
    sb.append("SELECT " + TABLE_CALENDARWEEK_DOT + CALENDARWEEK_ID_PK);
    sb.append(" FROM ");
    sb.append(TABLE_USERWEEK + COMMA);
    sb.append(TABLE_CALENDARWEEK + COMMA);
    sb.append(TABLE_ENTRYROW);
    sb.append(" WHERE (");
    sb.append(TABLE_USERWEEK_DOT + USERWEEK_CALENDARWEEK_ID_FK + " = " + TABLE_CALENDARWEEK_DOT + CALENDARWEEK_ID_PK);
    sb.append(" AND ");
    sb.append(TABLE_USERWEEK_DOT + USERWEEK_ID_PK + " = " + TABLE_ENTRYROW_DOT + ENTRYROW_USERWEEKID_FK);
    sb.append(" AND ");
    sb.append(TABLE_ENTRYROW_DOT + ENTRYROW_USERID_FK + " = ").append(userId);
    sb.append(" ) ");
    sb.append(" ) ");
    sb.append(" AND ");
    sb.append(TABLE_CALENDARWEEK_DOT + CALENDARWEEK_FROM_DATE + " > DATE('").append(cal.getCalendarInStoreFormat()).append("')  ");
    sb.append(" AND ");
    sb.append(TABLE_CALENDARWEEK_DOT + CALENDARWEEK_FROM_DATE + " < DATE('").append(stopDate).append("')  ");
    sb.append(" AND ");
    sb.append(" YEAR(" + TABLE_CALENDARWEEK_DOT + CALENDARWEEK_FROM_DATE + ") = ").append(year);

    sb.append(" ) ");
    sb.append(" ORDER BY ID ");

    DBConnect access = new DBConnect();
    StringRecordset rs = access.executeQuery(sb);
    return rs;
  }

  @Override
  public StringRecordset getUsersReportedYear(String year) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT DISTINCT ");
    sb.append(TABLE_USER_DOT + USER_ID_PK);
    sb.append(", ");
    sb.append("CONCAT(" + TABLE_USER_DOT + USER_FIRSTNAME + ",' '," + TABLE_USER_DOT + USER_LASTNAME + ") AS FullName");
    sb.append(" FROM ");
    sb.append(TABLE_USERWEEK + COMMA);
    sb.append(TABLE_CALENDARWEEK + COMMA);
    sb.append(TABLE_USER + COMMA);
    sb.append(TABLE_ENTRYROW);
    sb.append(" WHERE (");

    /* Connect userweek and calendarweek */
    sb.append(TABLE_USERWEEK_DOT + USERWEEK_CALENDARWEEK_ID_FK + " = " + TABLE_CALENDARWEEK_DOT + CALENDARWEEK_ID_PK);
    sb.append(" AND ");

    /* Connect userweek and entryrow */
    sb.append(TABLE_USERWEEK_DOT + USERWEEK_ID_PK + " = " + TABLE_ENTRYROW_DOT + ENTRYROW_USERWEEKID_FK);
    sb.append(" AND ");

    /* Connect user and entryrow */
    sb.append(TABLE_USER_DOT + USER_ID_PK + " = " + TABLE_ENTRYROW_DOT + ENTRYROW_USERID_FK);
    sb.append(" AND ");

    /* they must be submitted */
    sb.append(TABLE_USERWEEK_DOT + USERWEEK_SUBMITTED + " = " + TRUE_ACCESS);
    sb.append(" AND ");

    if (year.length() > 4) {
      sb.append(TABLE_CALENDARWEEK_DOT + CALENDARWEEK_FROM_DATE + " > '").append(year).append("'");
    } else {
      sb.append("YEAR(" + TABLE_CALENDARWEEK_DOT + CALENDARWEEK_FROM_DATE + ") = '").append(year).append("'");
    }
    // Order by weekno and weekpart
    sb.append(") ORDER BY FullName");

    DBConnect access = new DBConnect();
    StringRecordset rs = access.executeQuery(sb);
    return rs;
  }

  @Override
  public StringRecordset loadUserProfile(String userId) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT ");
    sb.append(TABLE_USER_DOT + "*" + COMMA);
    sb.append(TABLE_SKIN_DOT + SKIN_NAME + " as Sname " + COMMA);
    sb.append(TABLE_SKIN_DOT + SKIN_ID_PK + " as Sid " + COMMA);
    sb.append(TABLE_SKIN_DOT + SKIN_PATH + COMMA);
    sb.append(TABLE_SKIN_DOT + SKIN_DESCRIPTION + COMMA);
    sb.append(TABLE_LANGUAGE_DOT + LANGUAGE_CODE + COMMA);
    sb.append(TABLE_LANGUAGE_DOT + LANGUAGE_ID_PK + " as Lid " + COMMA);
    sb.append(TABLE_LANGUAGE_DOT + LANGUAGE_NAME + " as Lname " + COMMA);
    sb.append(TABLE_COMPANY_DOT + COMPANY_NAME + " as cName ");
    sb.append("FROM ");
    sb.append(TABLE_USER + COMMA + TABLE_LANGUAGE + COMMA + TABLE_SKIN + COMMA + TABLE_COMPANY);
    sb.append(" WHERE ");
    if (userId != null && userId.length() > 0) {
      sb.append(TABLE_USER_DOT + USER_ID_PK + " = ").append(userId);
      sb.append(" AND ");
    }
    sb.append(TABLE_USER_DOT + USER_LANGUAGEID_FK + " = " + TABLE_LANGUAGE_DOT + LANGUAGE_ID_PK);
    sb.append(" AND ");
    sb.append(TABLE_SKIN_DOT + SKIN_ID_PK + " = " + TABLE_USER_DOT + USER_SKINID_FK);
    sb.append(" AND ");
    sb.append(TABLE_USER_DOT + USER_COMPANYID_FK + " = " + TABLE_COMPANY_DOT + COMPANY_ID_PK);
    sb.append(" ORDER BY ");
    sb.append(TABLE_USER_DOT + USER_ID_PK);

    DBConnect access = new DBConnect();
    StringRecordset rs = access.executeQuery(sb);
    return rs;
  }

  @Override
  public StringRecordset login(String loginId, String password) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT ");
    sb.append(TABLE_USER_DOT + "*" + COMMA);
    sb.append(TABLE_SKIN_DOT + SKIN_NAME + " as Sname " + COMMA);
    sb.append(TABLE_SKIN_DOT + SKIN_ID_PK + " as Sid " + COMMA);
    sb.append(TABLE_SKIN_DOT + SKIN_PATH + COMMA);
    sb.append(TABLE_SKIN_DOT + SKIN_DESCRIPTION + COMMA);
    sb.append(TABLE_LANGUAGE_DOT + LANGUAGE_CODE + COMMA);
    sb.append(TABLE_LANGUAGE_DOT + LANGUAGE_ID_PK + " as Lid " + COMMA);
    sb.append(TABLE_LANGUAGE_DOT + LANGUAGE_NAME + " as Lname " + COMMA);
    sb.append(TABLE_COMPANY_DOT + COMPANY_NAME + " as cName ");
    sb.append("FROM ");
    sb.append(TABLE_USER + COMMA + TABLE_LANGUAGE + COMMA + TABLE_SKIN + COMMA + TABLE_COMPANY);
    sb.append(" WHERE ");
    sb.append(TABLE_USER_DOT + USER_LOGINID + " = " + SINGLE_QUOTE).append(loginId).append(SINGLE_QUOTE);
    sb.append(" AND ");
    sb.append(TABLE_USER_DOT + USER_PASSWORD + " = " + SINGLE_QUOTE).append(password).append(SINGLE_QUOTE);
    sb.append(" AND ");
    sb.append(TABLE_USER_DOT + USER_LANGUAGEID_FK + " = " + TABLE_LANGUAGE_DOT + LANGUAGE_ID_PK);
    sb.append(" AND ");
    sb.append(TABLE_SKIN_DOT + SKIN_ID_PK + " = " + TABLE_USER_DOT + USER_SKINID_FK);
    sb.append(" AND ");
    sb.append(TABLE_USER_DOT + USER_COMPANYID_FK + " = " + TABLE_COMPANY_DOT + COMPANY_ID_PK);

    DBConnect access = new DBConnect();
    StringRecordset rs = access.executeQuery(sb);
    return rs;
  }

  @Override
  public synchronized StringRecordset makeNewUserWeekEntryAndRetriveTheId(String calendarWeekId, String comment) throws Exception {
    try {
      String statisticKey = getClass().getName() + ".makeNewUserWeekEntryAndRetriveTheId";
      InvocationHandlerSetting s = InvocationHandlerSetting.create(statisticKey);
      DBExecute.getProxy(s).makeNewUserWeekId(calendarWeekId, comment);
    } catch (Exception e) {
      throw new Exception(getClass().getName() + ".makeNewUserWeekEntryAndRetriveTheId(String calendarWeekId, String comment): Could not make a new UserWeekId");
    }

    StringBuffer sb = new StringBuffer();
    sb.append("SELECT ");
    sb.append(" max(" + USERWEEK_ID_PK + ") maxId");
    sb.append(" FROM ");
    sb.append(TABLE_USERWEEK);

    DBConnect access = new DBConnect();
    StringRecordset rs = access.executeQuery(sb);
    return rs;
  }
}
