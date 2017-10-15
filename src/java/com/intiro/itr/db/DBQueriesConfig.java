package com.intiro.itr.db;

import com.intiro.itr.db.vo.ProjectPropertyVO;
import com.intiro.itr.db.vo.CalendarWeekVO;
import com.intiro.itr.util.ItrUtil;
import com.intiro.itr.util.StringRecordset;
import com.intiro.itr.util.cache.ItrCache;
import com.intiro.itr.util.cache.LockHelper;
import com.intiro.itr.util.log.IntiroLog;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class DBQueriesConfig implements DBQueriesConfigInterface, DBConstants {

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

  protected DBQueriesConfig() {
  }

  public static DBQueriesConfigInterface getProxy(InvocationHandlerSetting s) {
    DBQueriesConfig db = new DBQueriesConfig();
    db.setCallSetup(s);
    return (DBQueriesConfigInterface) Proxy.newProxyInstance(db.getClass().getClassLoader(), new Class<?>[]{DBQueriesConfigInterface.class}, new ItrInvocationHandler(db));
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
  public CalendarWeekVO getCalendarWeek(String fromDate) throws Exception {

    StringBuffer sb = new StringBuffer();
    sb.append("SELECT ");
    sb.append(" * ");
    sb.append(" FROM ");
    sb.append(TABLE_CALENDARWEEK);
    sb.append(" WHERE ");
    sb.append(TABLE_CALENDARWEEK_DOT + CALENDARWEEK_FROM_DATE + " = '").append(fromDate).append("'");
    DBConnect access = new DBConnect();
    StringRecordset rs = access.executeQuery(sb);
    CalendarWeekVO vo = new CalendarWeekVO();

    if (!rs.getEOF()) {
      vo.loadFromRS(rs);
      rs.moveNext();
    }
    return vo;
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
  public ProjectPropertyVO getProjectProperty(String projId) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT ");
    sb.append(" * ");
    sb.append(" FROM ");
    sb.append(TABLE_PROJECT);
    sb.append(" WHERE id = " + projId);

    DBConnect access = new DBConnect();
    StringRecordset rs = access.executeQuery(sb);
    ProjectPropertyVO vo = new ProjectPropertyVO();

    if (!rs.getEOF()) {
      vo.loadFromRS(rs);
      rs.moveNext();
    }

    return vo;
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

  @Override
  public String getProperty(String key) throws Exception {
    Map<String, String> map = getProperties();
    if (map == null || map.isEmpty()) {
      return null;
    }
    return map.get(key);
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
