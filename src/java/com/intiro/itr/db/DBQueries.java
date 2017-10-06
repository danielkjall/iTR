package com.intiro.itr.db;

import com.intiro.itr.logic.activity.Activity;
import com.intiro.itr.logic.company.Company;
import com.intiro.itr.logic.contacts.Contacts;
import com.intiro.itr.logic.email.Email;
import com.intiro.itr.logic.phone.PhoneNumber;
import com.intiro.itr.logic.project.Project;
import com.intiro.itr.logic.project.ProjectActivity;
import com.intiro.itr.logic.project.ProjectMember;
import com.intiro.itr.util.ITRCalendar;
import com.intiro.itr.util.StringRecordset;
import com.intiro.itr.util.personalization.Role;
import com.intiro.itr.util.personalization.UserProfile;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class DBQueries implements DBQueriesInterface, DBConstants {

  private DBQueries() {
  }

  public static DBQueriesInterface getProxy() {
    DBQueries db = new DBQueries();
    return (DBQueriesInterface) Proxy.newProxyInstance(db.getClass().getClassLoader(), new Class<?>[]{DBQueriesInterface.class}, new ItrInvocationHandler(db));
  }

  @Override
  public StringRecordset getActivitiesForProject(int projId, int activityId) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT ");
    sb.append(TABLE_PROJECTCODES_DOT + "*" + COMMA + TABLE_PROJECTCODE_DOT + PROJECTCODE_DESCRIPTION + COMMA + TABLE_PROJECTCODE_DOT + PROJECTCODE_CODE);
    sb.append(" FROM ");
    sb.append(TABLE_PROJECTCODE + COMMA + TABLE_PROJECTCODES);
    sb.append(" WHERE ");
    sb.append(TABLE_PROJECTCODES_DOT + PROJECTCODES_PROJECTCODEID_FK + " = " + TABLE_PROJECTCODE_DOT + PROJECTCODE_ID_PK);

    if (projId != -1) {
      sb.append(" AND ");
      sb.append(TABLE_PROJECTCODES_DOT + PROJECTCODES_PROJECTID_FK + " = ").append(projId);
    }
    if (activityId != -1) {
      sb.append(" AND ");
      sb.append(TABLE_PROJECTCODE_DOT + PROJECTCODE_ID_PK + " = ").append(activityId);
    }

    sb.append(" ORDER BY ITR_ProjectCode.Code, ITR_ProjectCode.Description ");

    DBConnect access = new DBConnect();
    StringRecordset rs = access.executeQuery(sb);
    return rs;
  }

  @Override
  public StringRecordset getAllCompanies() throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT ");
    sb.append(" * ");
    sb.append(" FROM ");
    sb.append(TABLE_COMPANY);
    sb.append(" ORDER BY Name ");

    DBConnect access = new DBConnect();
    StringRecordset rs = access.executeQuery(sb);
    return rs;
  }

  @Override
  public StringRecordset getAllPhoneCountries() throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append(" SELECT ");
    sb.append("*");
    sb.append(" FROM ");
    sb.append(TABLE_PHONECOUNTRYCODE);
    sb.append(" ORDER BY CountryName ");

    DBConnect access = new DBConnect();
    StringRecordset rs = access.executeQuery(sb);
    return rs;
  }

  @Override
  public StringRecordset getAllPhoneRegions() throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append(" SELECT ");
    sb.append("*");
    sb.append(" FROM ");
    sb.append(TABLE_PHONEREGIONCODE);
    sb.append(" ORDER BY RegionName ");

    DBConnect access = new DBConnect();
    StringRecordset rs = access.executeQuery(sb);
    return rs;
  }

  @Override
  public StringRecordset getAllProjectCodes() throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT ");
    sb.append(" * ");
    sb.append(" FROM ");
    sb.append(TABLE_PROJECTCODE);
    sb.append(" ORDER BY Code, Description ");

    DBConnect access = new DBConnect();
    StringRecordset rs = access.executeQuery(sb);
    return rs;
  }

  @Override
  public StringRecordset getAllProjects() throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT ");
    sb.append(" * ");
    sb.append(" FROM ");
    sb.append(TABLE_PROJECT);
    sb.append(" ORDER BY Name ");

    DBConnect access = new DBConnect();
    StringRecordset rs = access.executeQuery(sb);
    return rs;
  }

  @Override
  public StringRecordset getAllUsers() throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT ");
    sb.append(" * ");
    sb.append(" FROM ");
    sb.append(TABLE_USER);
    sb.append(" ORDER BY Firstname, LastName, LoginId ");

    DBConnect access = new DBConnect();
    StringRecordset rs = access.executeQuery(sb);
    return rs;
  }

  @Override
  public StringRecordset getCalendarWeek(String fromDate) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT ");
    sb.append(" * ");
    sb.append(" FROM ");
    sb.append(TABLE_CALENDARWEEK);
    sb.append(" WHERE ");

    /* the week must have a from date that is >= supplied startFromDate */
    sb.append(TABLE_CALENDARWEEK_DOT + CALENDARWEEK_FROM_DATE + " = '").append(fromDate).append("'");

    DBConnect access = new DBConnect();
    StringRecordset rs = access.executeQuery(sb);
    return rs;
  }

  @Override
  public StringRecordset getContacts() throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT ");
    sb.append("   * ");
    sb.append("FROM ");
    sb.append("   contact ");
    sb.append("ORDER BY FirstName, LastName ");

    DBConnect access = new DBConnect();
    StringRecordset rs = access.executeQuery(sb);
    return rs;
  }

  @Override
  public StringRecordset getEmails(int userId, int contactId, int emailId) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append(" SELECT ");
    sb.append("*");
    sb.append(" FROM ");
    sb.append(TABLE_EMAIL);
    sb.append(" WHERE ");

    if (!(emailId == -1)) {
      sb.append(EMAIL_ID_PK + " = ").append(emailId);
    }
    if (!(userId == -1)) {
      sb.append(EMAIL_USERID_FK + " = ").append(userId);
    }
    if (!(contactId == -1)) {
      sb.append(EMAIL_CONTACTID + " = ").append(contactId);
    }

    DBConnect access = new DBConnect();
    StringRecordset rs = access.executeQuery(sb);
    return rs;
  }

  @Override
  public StringRecordset getAllEmails() throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append(" SELECT ");
    sb.append("*");
    sb.append(" FROM ");
    sb.append(TABLE_EMAIL);
    DBConnect access = new DBConnect();
    StringRecordset rs = access.executeQuery(sb);
    return rs;
  }

  @Override
  public synchronized StringRecordset getGeneralReport(String userId, String projectId, String projectCodeId, String fromDate, String toDate) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT ");
    sb.append("cw.FromDate, ");
    sb.append("cw.WeekNo, ");
    sb.append("cw.WeekPart, ");
    sb.append("pr.Name as pr_Name, ");
    sb.append("pc.Description as pc_Description, ");
    sb.append("us.FirstName, ");
    sb.append("us.LastName, ");
    sb.append("pm.ProjectAdmin, ");
    sb.append("pm.Rate, ");
    sb.append("cw.ExpectedHoursSum as ExpectedHours, ");
    sb.append("SUM(er.HoursSum) as WorkedHours ");
    sb.append(" FROM ");
    sb.append("ITR_EntryRow        er, ");
    sb.append("ITR_UserWeek        uw, ");
    sb.append("ITR_CalendarWeek    cw, ");
    sb.append("ITR_Project         pr, ");
    sb.append("ITR_ProjectCode     pc, ");
    sb.append("ITR_User            us, ");
    sb.append("ITR_ProjectMembers  pm ");
    sb.append(" WHERE ");
    sb.append("er.ITR_UserWeekId          = uw.Id ");
    sb.append("AND uw.ITR_CalendarWeekId  = cw.Id ");
    sb.append("AND er.ITR_ProjectId       = pr.Id ");
    sb.append("AND er.ITR_ProjectCodeId   = pc.Id ");
    sb.append("AND er.ITR_UserId          = us.Id ");
    sb.append("AND pm.ITR_ProjectId       = pr.Id ");
    sb.append("AND pm.ITR_UserId          = us.Id ");

    if (userId != null) {
      sb.append(" AND er.ITR_UserId          = ").append(userId);
    }
    if (projectId != null) {
      sb.append(" AND er.ITR_ProjectId       = ").append(projectId);
    }
    if (projectCodeId != null) {
      sb.append(" AND er.ITR_ProjectCodeId   = ").append(projectCodeId);
    }
    if (fromDate != null && fromDate.length() > 0) {
      sb.append(" AND cw.FromDate           >= '").append(fromDate).append("'");
    }
    if (toDate != null && toDate.length() > 0) {
      sb.append(" AND cw.ToDate             <= '").append(toDate).append("'");
    }

    sb.append(" AND uw.Submitted = True ");
    sb.append(" GROUP BY ");
    sb.append("cw.FromDate, ");
    sb.append("cw.WeekNo, ");
    sb.append("cw.WeekPart, ");
    sb.append("pr.Name, ");
    sb.append("pc.Description, ");
    sb.append("pr.Name, ");
    sb.append("us.FirstName, ");
    sb.append("us.LastName, ");
    sb.append("pm.ProjectAdmin, ");
    sb.append("pm.Rate, ");
    sb.append("cw.ExpectedHoursSum ");

    DBConnect access = new DBConnect();
    StringRecordset rs = access.executeQuery(sb);
    return rs;
  }

  @Override
  public StringRecordset getLanguages() throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT ");
    sb.append("*");
    sb.append(" FROM ");
    sb.append(TABLE_LANGUAGE);
    sb.append(" ORDER BY Name ");

    DBConnect access = new DBConnect();
    StringRecordset rs = access.executeQuery(sb);
    return rs;
  }

  @Override
  public StringRecordset getModulesForRole(int roleId) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT ");
    sb.append(" * ");
    sb.append(" FROM ");
    sb.append(TABLE_MODULE);
    if (roleId > -1) {
      sb.append(" WHERE ");
      sb.append(TABLE_MODULE_DOT + MODULE_ROLESID_FK + " = ").append(roleId);
    }
    sb.append(" ORDER BY Module ");

    DBConnect access = new DBConnect();
    StringRecordset rs = access.executeQuery(sb);
    return rs;
  }

  @Override
  public StringRecordset getMonthlyReport(String userId, String projectId, String projectCodeId, String fromDate, String toDate) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT \n");
    sb.append("    ITR_Project.Id AS ProjectId, \n");
    sb.append("    ITR_Project.Name AS ProjectName, \n");
    sb.append("    ITR_User.Id AS UserId, \n");
    sb.append("    ITR_User.FirstName, \n");
    sb.append("    ITR_User.LastName, \n");
    sb.append("    ITR_User.LoginId, \n");
    sb.append("    ITR_ProjectCode.Id AS ProjectCodeId, \n");
    sb.append("    ITR_ProjectCode.Description AS ProjectCodeDescription, \n");
    sb.append("    ITR_TimeType.Description AS TimeTypeDescription, \n");
    sb.append("    ITR_ProjectMembers.Rate*ITR_TimeType.RateModifier AS Rate, \n");
    sb.append("    Sum(ITR_EntryRow.HoursSum) AS SumWorkedHours, \n");
    sb.append("    Sum(ITR_EntryRow.HoursSum)*ITR_ProjectMembers.Rate*ITR_TimeType.RateModifier AS Revenue \n");
    sb.append("FROM \n");
    sb.append("    (ITR_CalendarWeek INNER JOIN ITR_UserWeek ON ITR_CalendarWeek.Id = ITR_UserWeek.ITR_CalendarWeekId) \n");
    sb.append("        INNER JOIN (ITR_User \n");
    sb.append("            INNER JOIN (ITR_TimeType \n");
    sb.append("                INNER JOIN (ITR_Project \n");
    sb.append("                    INNER JOIN (ITR_ProjectCode \n");
    sb.append("                        INNER JOIN (ITR_EntryRow LEFT JOIN ITR_ProjectMembers \n");
    sb.append("                            ON (ITR_EntryRow.ITR_ProjectId = ITR_ProjectMembers.ITR_ProjectId) \n");
    sb.append("                        AND (ITR_EntryRow.ITR_UserId = ITR_ProjectMembers.ITR_UserId) \n");
    sb.append("                    ) ON ITR_ProjectCode.Id = ITR_EntryRow.ITR_ProjectCodeId \n");
    sb.append("                ) ON ITR_Project.Id = ITR_EntryRow.ITR_ProjectId \n");
    sb.append("            ) ON ITR_TimeType.Id = ITR_EntryRow.ITR_TimeTypeId \n");
    sb.append("        ) ON ITR_User.Id = ITR_EntryRow.ITR_UserId \n");
    sb.append("    ) ON ITR_UserWeek.Id = ITR_EntryRow.ITR_UserWeekId \n");
    sb.append("WHERE \n");
    sb.append("    ITR_CalendarWeek.FromDate>='").append(fromDate).append("' \n");
    sb.append("    AND ITR_CalendarWeek.ToDate<='").append(toDate).append("' \n");
    sb.append("    AND ITR_UserWeek.Approved=True \n");

    if (!projectId.equals("")) {
      sb.append("    AND ITR_EntryRow.ITR_ProjectId=").append(projectId).append(" \n");
    }
    if (!projectCodeId.equals("")) {
      sb.append("    AND ITR_EntryRow.ITR_ProjectCodeId=").append(projectCodeId).append(" \n");
    }
    if (!userId.equals("")) {
      sb.append("    AND ITR_EntryRow.ITR_userId=").append(userId).append(" \n");
    }

    sb.append("GROUP BY \n");
    sb.append("    ITR_Project.Id, \n");
    sb.append("    ITR_Project.Name, \n");
    sb.append("    ITR_User.Id, \n");
    sb.append("    ITR_User.FirstName, \n");
    sb.append("    ITR_User.LastName, \n");
    sb.append("    ITR_User.LoginId, \n");
    sb.append("    ITR_ProjectCode.Id, \n");
    sb.append("    ITR_ProjectCode.Description, \n");
    sb.append("    ITR_TimeType.Description, \n");
    sb.append("    ITR_ProjectMembers.Rate, \n");
    sb.append("    ITR_TimeType.RateModifier, \n");
    sb.append("    ITR_UserWeek.Approved \n");

    DBConnect access = new DBConnect();
    StringRecordset rs = access.executeQuery(sb);

    return rs;
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
  public StringRecordset getPhoneCountry(int countryId) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT ");
    sb.append(" * ");
    sb.append(" FROM ");
    sb.append(TABLE_PHONECOUNTRYCODE);
    if (countryId > -1) {
      sb.append(" WHERE ");
      sb.append(PHONECOUNTRYCODE_ID_PK + " = ").append(countryId);
    }

    DBConnect access = new DBConnect();
    StringRecordset rs = access.executeQuery(sb);
    return rs;
  }

  @Override
  public StringRecordset getPhoneNumbers(int userId, int contactId, int phoneId) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT ");
    sb.append(TABLE_PHONE_DOT + "*" + COMMA);
    sb.append(TABLE_PHONEREGIONCODE_DOT + PHONEREGIONCODE_REGIONCODE + COMMA);
    sb.append(TABLE_PHONEREGIONCODE_DOT + PHONEREGIONCODE_REGIONNAME + COMMA);
    sb.append(TABLE_PHONECOUNTRYCODE_DOT + PHONECOUNTRYCODE_COUNTRYCODE + COMMA);
    sb.append(TABLE_PHONECOUNTRYCODE_DOT + PHONECOUNTRYCODE_COUNTRYNAME);
    sb.append(" FROM ");
    sb.append(TABLE_PHONE + COMMA + TABLE_PHONEREGIONCODE + COMMA + TABLE_PHONECOUNTRYCODE);
    sb.append(" WHERE ");
    sb.append(TABLE_PHONE_DOT + PHONE_REGIONID_FK + " = " + TABLE_PHONEREGIONCODE_DOT + PHONEREGIONCODE_ID_PK);
    sb.append(" AND ");
    sb.append(TABLE_PHONEREGIONCODE_DOT + PHONEREGIONCODE_PHONECOUNTRYCODEID_FK + " = " + TABLE_PHONECOUNTRYCODE_DOT + PHONECOUNTRYCODE_ID_PK);
    sb.append(" AND ");

    if (!(phoneId == -1)) {
      sb.append(TABLE_PHONE_DOT + PHONE_ID_PK + " = ").append(phoneId);
    }
    if (!(userId == -1)) {
      sb.append(TABLE_PHONE_DOT + PHONE_USERID_FK + " = ").append(userId);
    }
    if (!(contactId == -1)) {
      sb.append(TABLE_PHONE_DOT + PHONE_CONTACTID_FK + " = ").append(contactId);
    }

    DBConnect access = new DBConnect();
    StringRecordset rs = access.executeQuery(sb);
    return rs;
  }

  @Override
  public StringRecordset getAllPhoneNumbers() throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT ");
    sb.append(TABLE_PHONE_DOT + "*" + COMMA);
    sb.append(TABLE_PHONEREGIONCODE_DOT + PHONEREGIONCODE_REGIONCODE + COMMA);
    sb.append(TABLE_PHONEREGIONCODE_DOT + PHONEREGIONCODE_REGIONNAME + COMMA);
    sb.append(TABLE_PHONECOUNTRYCODE_DOT + PHONECOUNTRYCODE_COUNTRYCODE + COMMA);
    sb.append(TABLE_PHONECOUNTRYCODE_DOT + PHONECOUNTRYCODE_COUNTRYNAME);
    sb.append(" FROM ");
    sb.append(TABLE_PHONE + COMMA + TABLE_PHONEREGIONCODE + COMMA + TABLE_PHONECOUNTRYCODE);
    sb.append(" WHERE ");
    sb.append(TABLE_PHONE_DOT + PHONE_REGIONID_FK + " = " + TABLE_PHONEREGIONCODE_DOT + PHONEREGIONCODE_ID_PK);
    sb.append(" AND ");
    sb.append(TABLE_PHONEREGIONCODE_DOT + PHONEREGIONCODE_PHONECOUNTRYCODEID_FK + " = " + TABLE_PHONECOUNTRYCODE_DOT + PHONECOUNTRYCODE_ID_PK);

    DBConnect access = new DBConnect();

    StringRecordset rs = access.executeQuery(sb);

    return rs;
  }

  @Override
  public StringRecordset getPhoneRegion(int regionId) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT ");
    sb.append(" * ");
    sb.append(" FROM ");
    sb.append(TABLE_PHONEREGIONCODE);
    if (regionId > -1) {
      sb.append(" WHERE ");
      sb.append(PHONEREGIONCODE_ID_PK + " = ").append(regionId);
    }
    DBConnect access = new DBConnect();
    StringRecordset rs = access.executeQuery(sb);
    return rs;
  }

  @Override
  public StringRecordset getProjectCodesForProject(int projId) throws Exception {

    StringBuffer sb = new StringBuffer();
    sb.append("SELECT ");
    sb.append(TABLE_PROJECTCODE_DOT + PROJECTCODE_CODE + COMMA + TABLE_PROJECTCODE_DOT + PROJECTCODE_DESCRIPTION + COMMA + TABLE_PROJECTCODE_DOT + PROJECTCODE_ID_PK);
    sb.append(" FROM ");
    sb.append(TABLE_PROJECTCODE + COMMA + TABLE_PROJECTCODES);
    sb.append(" WHERE ");
    sb.append(TABLE_PROJECTCODES_DOT + PROJECTCODES_PROJECTID_FK + " = ").append(projId);
    sb.append(" AND ");
    sb.append(TABLE_PROJECTCODES_DOT + PROJECTCODES_PROJECTCODEID_FK + " = " + TABLE_PROJECTCODE_DOT + PROJECTCODE_ID_PK);
    sb.append(" ORDER BY ITR_ProjectCode.Code, ITR_ProjectCode.Description ");

    DBConnect access = new DBConnect();

    StringRecordset rs = access.executeQuery(sb);

    return rs;
  }

  @Override
  public StringRecordset getProjectProperties(String projId) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT ");
    sb.append(" * ");
    sb.append(" FROM ");
    sb.append(TABLE_PROJECT);
    sb.append(" WHERE ");
    sb.append(TABLE_PROJECT_DOT + PROJECT_ID_PK + " = ").append(projId);

    DBConnect access = new DBConnect();
    StringRecordset rs = access.executeQuery(sb);
    return rs;
  }

  @Override
  public StringRecordset getProjectsForUser(int userId) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT ");
    sb.append(TABLE_PROJECT_DOT + PROJECT_ID_PK + COMMA);
    sb.append(TABLE_PROJECT_DOT + PROJECT_DESCRIPTION + COMMA);
    sb.append(TABLE_PROJECT_DOT + PROJECT_ID_PK + COMMA);
    sb.append(TABLE_PROJECT_DOT + PROJECT_NAME + COMMA);
    sb.append(TABLE_PROJECT_DOT + PROJECT_MAINCODE);
    sb.append(" FROM ");
    sb.append(TABLE_PROJECT + COMMA + TABLE_PROJECTMEMBERS);
    sb.append(" WHERE ");
    sb.append(TABLE_PROJECTMEMBERS_DOT + PROJECTMEMBERS_USERID_FK + " = ").append(userId);
    sb.append(" AND ");
    sb.append(TABLE_PROJECT_DOT + PROJECT_ID_PK + " = " + TABLE_PROJECTMEMBERS_DOT + PROJECTMEMBERS_PROJECTID_FK);
    sb.append(" ORDER BY ITR_Project.Name ");

    DBConnect access = new DBConnect();
    StringRecordset rs = access.executeQuery(sb);
    return rs;
  }

  @Override
  public StringRecordset getReportYears() throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT \n");
    sb.append("    DISTINCT Year(ITR_CalendarWeek.FromDate) AS year \n");
    sb.append("FROM \n");
    sb.append("    ITR_CalendarWeek \n");
    sb.append("ORDER BY Year(ITR_CalendarWeek.FromDate) \n");

    DBConnect access = new DBConnect();
    StringRecordset rs = access.executeQuery(sb);
    return rs;
  }

  @Override
  public StringRecordset getRoleForUser(String userId) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT ");
    sb.append(TABLE_USERROLES_DOT + "*" + COMMA);
    sb.append(TABLE_ROLES_DOT + ROLES_NAME);
    sb.append(" FROM ");
    sb.append(TABLE_ROLES + COMMA);
    sb.append(TABLE_USERROLES);
    sb.append(" WHERE ");
    if (userId != null && userId.length() > 0) {
      sb.append(TABLE_USERROLES_DOT + USERROLES_USERID_FK + " = ").append(userId);
      sb.append(" AND ");
    }
    sb.append(TABLE_USERROLES_DOT + USERROLES_ROLESID_FK + " = " + TABLE_ROLES_DOT + ROLES_ID_PK);
    sb.append(" ORDER BY ");
    sb.append(TABLE_USERROLES_DOT + USERROLES_USERID_FK);

    DBConnect access = new DBConnect();
    StringRecordset rs = access.executeQuery(sb);
    return rs;
  }

  @Override
  public StringRecordset getRoles() throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT ");
    sb.append(" * ");
    sb.append(" FROM ");
    sb.append(TABLE_ROLES);
    sb.append(" ORDER BY Name ");

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
    sb.append(TABLE_PROJECTCODE_DOT + PROJECTCODE_CODE + COMMA);
    sb.append(TABLE_COMMENT_DOT + COMMENT_COMMENT);
    sb.append(" FROM ");
    sb.append(TABLE_USERWEEK + COMMA);
    sb.append(TABLE_CALENDARWEEK + COMMA);
    sb.append(TABLE_COMMENT + COMMA);
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

    /* the week must have a from date that is = supplied fromDate */
    sb.append(TABLE_COMMENT_DOT + COMMENT_ID_PK + " = " + TABLE_USERWEEK_DOT + USERWEEK_COMMENTID_FK);
    sb.append(" AND ");

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
    sb.append(TABLE_COMMENT_DOT + COMMENT_COMMENT + COMMA);
    sb.append(TABLE_CALENDARWEEK_DOT + CALENDARWEEK_FROM_DATE);
    sb.append(" FROM ");
    sb.append(TABLE_USERWEEK + COMMA);
    sb.append(TABLE_CALENDARWEEK + COMMA);
    sb.append(TABLE_COMMENT + COMMA);
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

    sb.append(TABLE_COMMENT_DOT + COMMENT_ID_PK + " = " + TABLE_USERWEEK_DOT + USERWEEK_COMMENTID_FK);
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
  public StringRecordset getSettings() throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT ");
    sb.append(TABLE_LANGUAGE_DOT + "*" + COMMA + TABLE_SETTINGS_DOT + "*" + COMMA + TABLE_SKIN_DOT + "*");
    sb.append(" FROM ");
    sb.append(TABLE_SETTINGS + COMMA + TABLE_LANGUAGE + COMMA + TABLE_SKIN);
    sb.append(" WHERE ");
    sb.append(TABLE_SETTINGS_DOT + SETTINGS_LANGUAGEID_FK + " = " + TABLE_LANGUAGE_DOT + LANGUAGE_ID_PK);
    sb.append(" AND ");
    sb.append(TABLE_SETTINGS_DOT + SETTINGS_SKINID_FK + " = " + TABLE_SKIN_DOT + SKIN_ID_PK);

    DBConnect access = new DBConnect();
    StringRecordset rs = access.executeQuery(sb);
    return rs;
  }

  @Override
  public StringRecordset getSkins() throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT ");
    sb.append("*");
    sb.append(" FROM ");
    sb.append(TABLE_SKIN);
    sb.append(" ORDER BY Name ");

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
  public StringRecordset getSubmittedWeeksThick(String userId, String year, boolean descendingSortOrder, boolean checkIfTheyAreNotApproved) throws Exception {
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
    //sb.append(", ITR_EntryRow.* ");
    sb.append(", ITR_TimeType.Type ");
    sb.append(", ITR_UserWeek.* ");
    sb.append(", ITR_ProjectCode.Code ");
    sb.append(", ITR_Comment.Comment  ");
    sb.append(", ITR_CalendarWeek.FromDate ");
    sb.append(", ITR_CalendarWeek.ToDate ");
    sb.append("FROM  ");
    sb.append("  ITR_UserWeek ");
    sb.append(", ITR_CalendarWeek ");
    sb.append(", ITR_EntryRow  ");
    sb.append(", ITR_TimeType ");
    sb.append(", ITR_ProjectCode ");
    sb.append(", ITR_Comment ");
    sb.append("WHERE  ");
    sb.append("    ITR_UserWeek.ITR_CalendarWeekId = ITR_CalendarWeek.Id  ");
    sb.append("AND ITR_UserWeek.Id = ITR_EntryRow.ITR_UserWeekId  ");
    if (checkIfTheyAreNotApproved) {
      sb.append(" AND ");
      sb.append(TABLE_USERWEEK_DOT + USERWEEK_APPROVED + " = " + FALSE_ACCESS);
    }
    if (year.length() > 0) {
      /* select specified year */
      sb.append(" AND ");
      sb.append("YEAR(" + TABLE_CALENDARWEEK_DOT + CALENDARWEEK_FROM_DATE + ") = '").append(year).append("'");
    }
    sb.append(" AND ITR_EntryRow.ITR_UserId = ").append(userId);
    sb.append(" AND ITR_Comment.Id = ITR_UserWeek.ITR_CommentId  ");
    sb.append("AND ITR_TimeType.Id = ITR_EntryRow.ITR_TimeTypeId  ");
    sb.append("AND ITR_EntryRow.ITR_ProjectCodeId = ITR_ProjectCode.Id ");
    sb.append("AND ITR_UserWeek.Submitted =  True ");

    /* Order by weekno and weekpart */
    sb.append(" ORDER BY ");
    sb.append("itr_userweekid ");

    if (descendingSortOrder) {
      sb.append(" DESC ");
    }

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

    if (year.length() > 0) {
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
  public StringRecordset getTimeType(String id) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT ");
    sb.append("*");
    sb.append(" FROM ");
    sb.append(TABLE_TIMETYPE);
    sb.append(" WHERE ");
    sb.append(TABLE_TIMETYPE_DOT + TIMETYPE_ID_PK + " = ").append(id);

    DBConnect access = new DBConnect();
    StringRecordset rs = access.executeQuery(sb);
    return rs;
  }

  @Override
  public StringRecordset getTimeTypes() throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT ");
    sb.append("*");
    sb.append(" FROM ");
    sb.append(TABLE_TIMETYPE);
    sb.append(" ORDER BY Type ");

    DBConnect access = new DBConnect();
    StringRecordset rs = access.executeQuery(sb);
    return rs;
  }

  @Override
  public StringRecordset getUsers(boolean activated) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT ");
    sb.append(" Id, ITR_SkinId, ITR_LanguageId, ITR_CompanyId, FirstName, LastName, LoginId, Password, Activated, DeactivatedDate, ActivatedDate, CreatedDate, DefaultVacationDays, UsedVacationDays, VacationOvertimeHours, MoneyOvertimeHours, ReportApproverId, SavedVacationDays");
    sb.append(" FROM ");
    sb.append(TABLE_USER);
    sb.append(" WHERE ");

    if (activated) {
      sb.append(USER_ACTIVATED + " = " + TRUE_ACCESS);
    } else {
      sb.append(USER_ACTIVATED + " = " + FALSE_ACCESS);
    }

    sb.append(" ORDER BY Firstname, LastName, LoginId ");

    DBConnect access = new DBConnect();
    StringRecordset rs = access.executeQuery(sb);
    return rs;
  }

  public StringRecordset getUsersThatNeedApprovel(String userId) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT ");
    sb.append(TABLE_USER + USER_ID_PK);
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

    /* they must not have been approved */
    sb.append(TABLE_USERWEEK_DOT + USERWEEK_APPROVED + " = " + FALSE_ACCESS);
    sb.append(" AND ");

    /* It must be this user that is the approver for the user */
    sb.append(TABLE_USER_DOT + USER_REPORT_APPROVERID_FK + " = ").append(userId);

    // Order by weekno and weekpart
    sb.append(") ORDER BY ");
    sb.append(TABLE_USER_DOT + USER_ID_PK);

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

    sb.append("YEAR(" + TABLE_CALENDARWEEK_DOT + CALENDARWEEK_FROM_DATE + ") = '").append(year).append("'");

    // Order by weekno and weekpart
    sb.append(") ORDER BY FullName");

    DBConnect access = new DBConnect();
    StringRecordset rs = access.executeQuery(sb);
    return rs;
  }

  @Override
  public synchronized StringRecordset getVacationReport(String userId, String projectId, String companyId, String year) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT ");
    sb.append("    us.Id, ");
    sb.append("    us.LastName, ");
    sb.append("    us.FirstName,  ");
    sb.append("    us.DefaultVacationDays, ");
    sb.append("    us.UsedVacationDays, ");
    sb.append("    us.SavedVacationDays, ");
    sb.append("    us.VacationOvertimeHours, ");
    sb.append("    us.MoneyOvertimeHours, ");
    sb.append("    co.Name as co_Name, ");
    sb.append("    pr.Name as pr_Name,  ");
    sb.append("    er.ITR_TimetypeId, ");
    sb.append("    tt.type, ");
    sb.append("    SUM(er.HoursSum) as WorkedHours ");
    sb.append("FROM  ");
    sb.append("    ITR_EntryRow        er, ");
    sb.append("    ITR_UserWeek        uw, ");
    sb.append("    ITR_CalendarWeek    cw, ");
    sb.append("    ITR_Project         pr, ");
    sb.append("    ITR_User            us, ");
    sb.append("    ITR_ProjectMembers  pm, ");
    sb.append("    ITR_TimeType        tt, ");
    sb.append("    ITR_Company         co ");
    sb.append("WHERE  ");
    sb.append("        er.ITR_UserWeekId       = uw.Id ");
    sb.append("    AND er.ITR_ProjectId        = pr.Id  ");
    sb.append("    AND er.ITR_UserId           = us.Id  ");
    sb.append("    AND er.ITR_TimetypeId       = tt.Id  ");
    sb.append("    AND uw.ITR_CalendarWeekId   = cw.Id  ");
    sb.append("    AND uw.ITR_CalendarWeekId   = cw.Id  ");
    sb.append("    AND pm.ITR_ProjectId        = pr.Id  ");
    sb.append("    AND pm.ITR_UserId           = us.Id  ");
    sb.append("    AND us.ITR_CompanyId        = co.Id  ");
    sb.append("    AND uw.Submitted            = True ");

    if (userId != null) {
      sb.append("    AND us.Id = ").append(userId);
    }
    if (projectId != null) {
      sb.append("    AND pr.Id = ").append(projectId);
    }
    if (companyId != null) {
      sb.append("    AND co.Id = ").append(companyId);
    }
    if (year != null && year.length() > 0) {
      sb.append("    AND cw.FromDate >= '").append(year).append("-01-01'");
      sb.append("    AND cw.FromDate <= '").append(year).append("-12-31'");
    }

    sb.append("GROUP BY  ");
    sb.append("    us.Id, ");
    sb.append("    us.LastName, ");
    sb.append("    us.FirstName,  ");
    sb.append("    us.DefaultVacationDays, ");
    sb.append("    us.UsedVacationDays, ");
    sb.append("    us.SavedVacationDays, ");
    sb.append("    us.VacationOvertimeHours, ");
    sb.append("    us.MoneyOvertimeHours, ");
    sb.append("    co.Name, ");
    sb.append("    pr.Name,  ");
    sb.append("    er.ITR_TimetypeId, ");
    sb.append("    tt.type ");

    DBConnect access = new DBConnect();
    StringRecordset rs = access.executeQuery(sb);
    return rs;
  }

  @Override
  public synchronized StringRecordset addCompanyAndGetId(Company company) throws Exception {
    try {
      DBExecute.getProxy().addCompany(company);
    } catch (Exception e) {
      throw new Exception(getClass().getName() + ".addCompanyAndGetId(Company company): Could not make a new company");
    }

    StringBuffer sb = new StringBuffer();
    sb.append("SELECT ");
    sb.append(" MAX(" + COMPANY_ID_PK + ") maxId");
    sb.append(" FROM ");
    sb.append(TABLE_COMPANY);

    DBConnect access = new DBConnect();
    StringRecordset rs = access.executeQuery(sb);
    return rs;
  }

  @Override
  public synchronized StringRecordset addEmailAndGetId(Email email) throws Exception {
    try {
      DBExecute.getProxy().addEmail(email);
    } catch (Exception e) {
      throw new Exception(getClass().getName() + ".addEmailAndGetId(Email email): Could not make a new email address");
    }

    StringBuffer sb = new StringBuffer();
    sb.append("SELECT ");
    sb.append(" MAX(" + EMAIL_ID_PK + ") maxId");
    sb.append(" FROM ");
    sb.append(TABLE_EMAIL);

    DBConnect access = new DBConnect();
    StringRecordset rs = access.executeQuery(sb);
    return rs;
  }

  @Override
  public synchronized StringRecordset addPhoneNumberAndGetId(PhoneNumber phone) throws Exception {
    try {
      DBExecute.getProxy().addPhoneNumber(phone);
    } catch (Exception e) {
      throw new Exception(getClass().getName() + ".addPhoneNumberAndGetId(PhoneNumber phone): Could not make a new phone number");
    }

    StringBuffer sb = new StringBuffer();
    sb.append("SELECT ");
    sb.append(" MAX(" + PHONE_ID_PK + ") maxId");
    sb.append(" FROM ");
    sb.append(TABLE_PHONE);

    DBConnect access = new DBConnect();
    StringRecordset rs = access.executeQuery(sb);
    return rs;
  }

  @Override
  public StringRecordset loadActivitiesNotInProject(int projectId) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT DISTINCT ");
    sb.append("    ITR_ProjectCode.Id, ");
    sb.append("    ITR_ProjectCode.Description, ");
    sb.append("    ITR_ProjectCode.Code ");
    sb.append("FROM ");
    sb.append("    ITR_ProjectCode ");
    sb.append("WHERE ");
    sb.append("    NOT EXISTS ( ");
    sb.append("        SELECT * FROM  ITR_ProjectCodes ");
    sb.append("        WHERE ITR_ProjectCode.Id = ITR_ProjectCodes.ITR_ProjectCodeId ");
    sb.append("            AND ITR_ProjectCodes.ITR_ProjectId = ").append(projectId).append(" ");
    sb.append("    ) ");
    sb.append("ORDER BY ITR_ProjectCode.Code ");

    DBConnect access = new DBConnect();
    StringRecordset rs = access.executeQuery(sb);
    return rs;
  }

  @Override
  public StringRecordset loadActivity(int id) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT ");
    sb.append("* ");
    sb.append("FROM ");
    sb.append("ITR_ProjectCode ");
    sb.append("WHERE ");
    sb.append("Id = ").append(id);

    DBConnect access = new DBConnect();
    StringRecordset rs = access.executeQuery(sb);
    return rs;
  }

  @Override
  public StringRecordset loadAssignedProjectMembers(int projectId) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT ");
    sb.append("    ITR_ProjectMembers.*, ");
    sb.append("    ITR_User.FirstName, ");
    sb.append("    ITR_User.LastName, ");
    sb.append("    ITR_User.LoginId ");
    sb.append("FROM ");
    sb.append("    ITR_User INNER JOIN ITR_ProjectMembers ON ");
    sb.append("    ITR_User.Id = ITR_ProjectMembers.ITR_UserId ");
    sb.append("WHERE ");
    sb.append("    ITR_ProjectMembers.ITR_ProjectId = ").append(projectId).append(" ");
    sb.append("ORDER BY ");
    sb.append("    ITR_User.LastName, ");
    sb.append("    ITR_User.FirstName, ");
    sb.append("    ITR_User.LoginId ");

    DBConnect access = new DBConnect();
    StringRecordset rs = access.executeQuery(sb);
    return rs;
  }

  @Override
  public StringRecordset loadAvailableProjectMembers(int projectId) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT ");
    sb.append("    ITR_User.* ");
    sb.append("FROM ");
    sb.append("    ITR_User ");
    sb.append("WHERE ");
    sb.append("    NOT EXISTS ( ");
    sb.append("        SELECT * FROM ITR_ProjectMembers ");
    sb.append("        WHERE ITR_User.Id = ITR_ProjectMembers.ITR_UserId ");
    sb.append("            AND ITR_ProjectMembers.ITR_ProjectId = ").append(projectId).append(" ");
    sb.append("    ) ");
    sb.append("    AND ITR_User.Activated=True ");
    sb.append("ORDER BY ");
    sb.append("    ITR_User.LastName, ");
    sb.append("    ITR_User.FirstName, ");
    sb.append("    ITR_User.LoginId ");

    DBConnect access = new DBConnect();
    StringRecordset rs = access.executeQuery(sb);
    return rs;
  }

  @Override
  public StringRecordset loadCompany(String companyId) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT ");
    sb.append(" * ");
    sb.append(" FROM ");
    sb.append(TABLE_COMPANY);
    sb.append(" WHERE ");
    sb.append(COMPANY_ID_PK + " = ").append(companyId);

    DBConnect access = new DBConnect();
    StringRecordset rs = access.executeQuery(sb);
    return rs;
  }

  @Override
  public StringRecordset loadContact(int contactId) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT ");
    sb.append("   * ");
    sb.append("FROM ");
    sb.append("    contact ");
    sb.append("WHERE ");
    sb.append("   Id = ").append(contactId);

    DBConnect access = new DBConnect();
    StringRecordset rs = access.executeQuery(sb);
    return rs;
  }

  @Override
  public StringRecordset loadProjectActivities(int projectId) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT ");
    sb.append("    ITR_ProjectCodes.*, ");
    sb.append("    ITR_ProjectCode.Description, ");
    sb.append("    ITR_ProjectCode.Code ");
    sb.append("FROM ");
    sb.append("    ITR_ProjectCode INNER JOIN ITR_ProjectCodes ON ");
    sb.append("    ITR_ProjectCode.Id = ITR_ProjectCodes.ITR_ProjectCodeId ");
    sb.append("WHERE ");
    sb.append("    ITR_ProjectCodes.ITR_ProjectId = ").append(projectId).append(" ");
    sb.append("ORDER BY ");
    sb.append("    ITR_ProjectCode.Code ");

    DBConnect access = new DBConnect();
    StringRecordset rs = access.executeQuery(sb);
    return rs;
  }

  @Override
  public StringRecordset loadProjectActivity(int id) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT ");
    sb.append("    ITR_ProjectCode.*, ");
    sb.append("    ITR_ProjectCodes.ITR_ProjectId, ");
    sb.append("    ITR_ProjectCodes.ITR_ProjectCodeId ");
    sb.append("FROM ");
    sb.append("    ITR_ProjectCode INNER JOIN ITR_ProjectCodes ON ");
    sb.append("    ITR_ProjectCode.Id = ITR_ProjectCodes.ITR_ProjectCodeId ");
    sb.append("WHERE ");
    sb.append("    ITR_ProjectCode.Id = ").append(id).append(" ");

    DBConnect access = new DBConnect();
    StringRecordset rs = access.executeQuery(sb);
    return rs;
  }

  @Override
  public StringRecordset loadProjectMember(int id) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT ");
    sb.append("    ITR_ProjectMembers.*, ");
    sb.append("    ITR_User.FirstName, ");
    sb.append("    ITR_User.LastName, ");
    sb.append("    ITR_User.LoginId ");
    sb.append("FROM ");
    sb.append("    ITR_User INNER JOIN ITR_ProjectMembers ON ");
    sb.append("    ITR_User.Id = ITR_ProjectMembers.ITR_UserId ");
    sb.append("WHERE ");
    sb.append("    ITR_ProjectMembers.Id = ").append(id).append(" ");

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
  public synchronized StringRecordset makeActivityAndFetchId(Activity activity) throws Exception {
    try {
      DBExecute.getProxy().addActivity(activity);
    } catch (Exception e) {
      throw new Exception(getClass().getName() + ".makeActivityAndFetchId(Activity activity): Could not make a new activity");
    }

    StringBuffer sb = new StringBuffer();
    sb.append("SELECT ");
    sb.append(" MAX(" + PROJECTCODE_ID_PK + ") maxId");
    sb.append(" FROM ");
    sb.append(TABLE_PROJECTCODE);

    DBConnect access = new DBConnect();
    StringRecordset rs = access.executeQuery(sb);
    return rs;
  }

  @Override
  public synchronized StringRecordset makeNewCommmentAndRetriveTheId(String comment) throws Exception {
    try {
      DBExecute.getProxy().makeNewComment(comment);
    } catch (Exception e) {
      throw new Exception(getClass().getName() + ".makeNewCommmentAndRetriveTheId(String comment): Could not make a new Comment: " + e.getMessage());
    }

    StringBuffer sb = new StringBuffer();
    sb.append("SELECT ");
    sb.append(" max(" + COMMENT_ID_PK + ") maxId");
    sb.append(" FROM ");
    sb.append(TABLE_COMMENT);

    DBConnect access = new DBConnect();
    StringRecordset rs = access.executeQuery(sb);
    return rs;
  }

  @Override
  public synchronized StringRecordset makeNewContactAndFetchId(Contacts contact) throws Exception {
    try {
      DBExecute.getProxy().addContacts(contact);
    } catch (Exception e) {
      throw new Exception(getClass().getName() + ".makeNewContactAndFetchId(Contacts contact): Could not make a new Contact.");
    }

    StringBuffer sb = new StringBuffer();
    sb.append("SELECT MAX(Id) maxId FROM Contact");

    DBConnect access = new DBConnect();
    StringRecordset rs = access.executeQuery(sb);
    return rs;
  }

  @Override
  public synchronized StringRecordset makeNewProjectActivityAndFetchId(ProjectActivity projectActivity) throws Exception {
    try {
      DBExecute.getProxy().addProjectActivity(projectActivity);
    } catch (Exception e) {
      throw new Exception(getClass().getName() + ".makeNewProjectActivityAndFetchId(ProjectActivity projectActivity): Could not make a new project activity");
    }

    StringBuffer sb = new StringBuffer();
    sb.append("SELECT ");
    sb.append(" MAX(" + PROJECTCODES_ID_PK + ") maxId");
    sb.append(" FROM ");
    sb.append(TABLE_PROJECTCODES);

    DBConnect access = new DBConnect();
    StringRecordset rs = access.executeQuery(sb);
    return rs;
  }

  @Override
  public synchronized StringRecordset makeNewProjectMemberAndFetchId(ProjectMember projectMember) throws Exception {
    try {
      DBExecute.getProxy().addProjectMember(projectMember);
    } catch (Exception e) {
      throw new Exception(getClass().getName() + ".makeNewProjectActivityAndFetchId(ProjectActivity projectActivity): Could not make a new project activity");
    }

    StringBuffer sb = new StringBuffer();
    sb.append("SELECT MAX(Id) maxId FROM ITR_ProjectMembers ");

    DBConnect access = new DBConnect();
    StringRecordset rs = access.executeQuery(sb);
    return rs;
  }

  @Override
  public synchronized StringRecordset makeNewUserAndFetchId(UserProfile user) throws Exception {
    try {
      DBExecute.getProxy().makeNewUserProfile(user);
    } catch (Exception e) {
      throw new Exception(getClass().getName() + ".makeNewUserProfileAndFetchId(UserProfile user): Could not make a new UserProfile: " + e.getMessage());
    }

    StringBuffer sb = new StringBuffer();
    sb.append("SELECT ");
    sb.append(" max(" + USER_ID_PK + ") maxId");
    sb.append(" FROM ");
    sb.append(TABLE_USER);

    DBConnect access = new DBConnect();
    StringRecordset rs = access.executeQuery(sb);
    return rs;
  }

  @Override
  public synchronized StringRecordset makeNewUserRoleConnectionAndFetchId(Role role) throws Exception {
    try {
      DBExecute.getProxy().addUserRoleConnection(role);
    } catch (Exception e) {
      throw new Exception(getClass().getName() + ".makeNewUserRoleConnectionAndFetchId(Role role): Could not make a new user role connection");
    }

    StringBuffer sb = new StringBuffer();
    sb.append("SELECT ");
    sb.append(" MAX(" + USERROLES_ID_PK + ") maxId");
    sb.append(" FROM ");
    sb.append(TABLE_USERROLES);

    DBConnect access = new DBConnect();
    StringRecordset rs = access.executeQuery(sb);
    return rs;
  }

  @Override
  public synchronized StringRecordset makeNewUserWeekEntryAndRetriveTheId(String calendarWeekId, int weekCommentId) throws Exception {
    try {
      DBExecute.getProxy().makeNewUserWeekId(calendarWeekId, weekCommentId);
    } catch (Exception e) {
      throw new Exception(getClass().getName() + ".makeNewUserWeekEntryAndRetriveTheId(String calendarWeekId, int weekCommentId): Could not make a new UserWeekId");
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

  @Override
  public synchronized StringRecordset makeProjectAndFetchId(Project project) throws Exception {
    try {
      DBExecute.getProxy().makeNewProject(project);
    } catch (Exception e) {
      throw new Exception(getClass().getName() + ".makeProjectAndFetchId(Project project): Could not make a new Project: " + e.getMessage());
    }

    StringBuffer sb = new StringBuffer();
    sb.append("SELECT ");
    sb.append(" max(" + PROJECT_ID_PK + ") maxId");
    sb.append(" FROM ");
    sb.append(TABLE_PROJECT);

    DBConnect access = new DBConnect();
    StringRecordset rs = access.executeQuery(sb);
    return rs;
  }

  @Override
  public String getProperty(String key) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT `key`, value FROM ITR_PROPERTY WHERE UPPER(`key`) = UPPER('").append(key).append("')");
    DBConnect access = new DBConnect();
    StringRecordset rs = access.executeQuery(sb);
    if(!rs.getEOF()) {      
      return rs.getField("VALUE");
    }
    return null;
  }

  @Override
  public Map<String, String> getProperties() throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT `key`, value FROM ITR_PROPERTY");
    DBConnect access = new DBConnect();
    StringRecordset rs = access.executeQuery(sb);
    Map<String, String> retval = new HashMap<>();
    while (!rs.getEOF()) {      
      retval.put(rs.getField("KEY"), rs.getField("VALUE"));
      rs.moveNext();
    }
    
    return retval;
  }
}