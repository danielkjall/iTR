package com.intiro.itr.db;

import com.intiro.itr.logic.activity.Activity;
import com.intiro.itr.logic.company.Company;
import com.intiro.itr.logic.contacts.Contacts;
import com.intiro.itr.logic.email.Email;
import com.intiro.itr.logic.phone.PhoneNumber;
import com.intiro.itr.logic.project.Project;
import com.intiro.itr.logic.project.ProjectActivity;
import com.intiro.itr.logic.project.ProjectMember;
import com.intiro.itr.util.StringRecordset;
import com.intiro.itr.util.cache.ItrCache;
import com.intiro.itr.util.cache.LockHelper;
import com.intiro.itr.util.log.IntiroLog;
import com.intiro.itr.util.personalization.Role;
import com.intiro.itr.util.personalization.UserProfile;
import java.lang.reflect.Proxy;

public class DBQueriesAdmin implements DBQueriesAdminInterface, DBConstants {

  private ThreadLocal<InvocationHandlerSetting> m_personalInvocationSetting = new ThreadLocal<>();

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

  protected DBQueriesAdmin() {
  }

  public static DBQueriesAdminInterface getProxy(InvocationHandlerSetting s) {
    DBQueriesAdmin db = new DBQueriesAdmin();
    db.setCallSetup(s);
    return (DBQueriesAdminInterface) Proxy.newProxyInstance(db.getClass().getClassLoader(), new Class<?>[]{DBQueriesAdminInterface.class}, new ItrInvocationHandler(db));
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
      String statisticKey = getClass().getName() + ".addCompanyAndGetId";
      InvocationHandlerSetting s = InvocationHandlerSetting.create(statisticKey);
      DBExecute.getProxy(s).addCompany(company);
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
      String statisticKey = getClass().getName() + ".addEmailAndGetId";
      InvocationHandlerSetting s = InvocationHandlerSetting.create(statisticKey);
      DBExecute.getProxy(s).addEmail(email);
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
      String statisticKey = getClass().getName() + ".addPhoneNumberAndGetId";
      InvocationHandlerSetting s = InvocationHandlerSetting.create(statisticKey);
      DBExecute.getProxy(s).addPhoneNumber(phone);
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
  public StringRecordset loadProjectActivitiesForProject(int projectId) throws Exception {

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
  public synchronized StringRecordset makeActivityAndFetchId(Activity activity) throws Exception {
    try {
      String statisticKey = getClass().getName() + ".makeActivityAndFetchId";
      InvocationHandlerSetting s = InvocationHandlerSetting.create(statisticKey);
      DBExecute.getProxy(s).addActivity(activity);
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
  public synchronized StringRecordset makeNewContactAndFetchId(Contacts contact) throws Exception {
    try {
      String statisticKey = getClass().getName() + ".makeNewContactAndFetchId";
      InvocationHandlerSetting s = InvocationHandlerSetting.create(statisticKey);
      DBExecute.getProxy(s).addContacts(contact);
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
      String statisticKey = getClass().getName() + ".makeNewProjectActivityAndFetchId";
      InvocationHandlerSetting s = InvocationHandlerSetting.create(statisticKey);
      DBExecute.getProxy(s).addProjectActivity(projectActivity);
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
      String statisticKey = getClass().getName() + ".makeNewProjectMemberAndFetchId";
      InvocationHandlerSetting s = InvocationHandlerSetting.create(statisticKey);
      DBExecute.getProxy(s).addProjectMember(projectMember);
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
      String statisticKey = getClass().getName() + ".makeNewUserAndFetchId";
      InvocationHandlerSetting s = InvocationHandlerSetting.create(statisticKey);
      DBExecute.getProxy(s).makeNewUserProfile(user);
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
      String statisticKey = getClass().getName() + ".makeNewUserRoleConnectionAndFetchId";
      InvocationHandlerSetting s = InvocationHandlerSetting.create(statisticKey);
      DBExecute.getProxy(s).addUserRoleConnection(role);
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
  public synchronized StringRecordset makeProjectAndFetchId(Project project) throws Exception {
    try {
      String statisticKey = getClass().getName() + ".makeProjectAndFetchId";
      InvocationHandlerSetting s = InvocationHandlerSetting.create(statisticKey);
      DBExecute.getProxy(s).makeNewProject(project);
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
}
