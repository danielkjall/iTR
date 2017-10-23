package com.intiro.itr.db;

import com.intiro.itr.util.logger.LoggerVO;
import com.intiro.itr.logic.activity.Activity;
import com.intiro.itr.logic.company.Company;
import com.intiro.itr.logic.contacts.Contacts;
import com.intiro.itr.logic.email.Email;
import com.intiro.itr.logic.phone.PhoneNumber;
import com.intiro.itr.logic.phone.PhoneRegion;
import com.intiro.itr.logic.project.Project;
import com.intiro.itr.logic.project.ProjectActivity;
import com.intiro.itr.logic.project.ProjectMember;
import com.intiro.itr.logic.weekreport.Row;
import com.intiro.itr.util.log.IntiroLog;
import com.intiro.itr.util.personalization.Role;
import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.itr.util.statistics.StatisticVO;
import com.intiro.itr.util.statistics.StatisticsVO;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class DBExecute implements DBConstants, DbExecuteInterface {

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

  private DBExecute() {
  }

  public static DbExecuteInterface getProxy(InvocationHandlerSetting s) {
    DBExecute db = new DBExecute();
    db.setCallSetup(s);
    return (DbExecuteInterface) Proxy.newProxyInstance(db.getClass().getClassLoader(), new Class<?>[]{DbExecuteInterface.class}, new ItrInvocationHandler(db));
  }

  @Override
  public boolean changePassword(String userId, String newLoginId, String newPassword) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("UPDATE ");
    sb.append(TABLE_USER);
    sb.append(" SET ");
    sb.append(USER_PASSWORD + " = " + SINGLE_QUOTE).append(newPassword).append(SINGLE_QUOTE + COMMA);
    sb.append(USER_LOGINID + " = " + SINGLE_QUOTE).append(newLoginId).append(SINGLE_QUOTE);
    sb.append(" WHERE ");
    sb.append(USER_ID_PK + " = ").append(userId);

    DBConnect access = new DBConnect();
    boolean retval = access.executeUpdate(sb);
    return retval;
  }

  @Override
  public synchronized boolean deactivateProjectMember(int id) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("UPDATE ITR_ProjectMembers SET \n");
    sb.append("  Active = ").append(false).append(" \n");
    sb.append("WHERE \n");
    sb.append("  Id = ").append(id).append(" \n");

    DBConnect access = new DBConnect();
    boolean retval = access.executeUpdate(sb);
    return retval;
  }

  @Override
  public synchronized boolean deleteActivity(int activityId) throws Exception {
    // Create delete entryrows sql query
    StringBuffer sbDeleteEntryRows = new StringBuffer();
    sbDeleteEntryRows.append("DELETE FROM ");
    sbDeleteEntryRows.append(TABLE_ENTRYROW);
    sbDeleteEntryRows.append(" WHERE ");
    sbDeleteEntryRows.append(ENTRYROW_PROJECTCODEID_FK + " = ").append(activityId);

    // Create delete projectcode/activities sql query
    StringBuffer sbDeleteActivities = new StringBuffer();
    sbDeleteActivities.append("DELETE FROM ");
    sbDeleteActivities.append(TABLE_PROJECTCODES);
    sbDeleteActivities.append(" WHERE ");
    sbDeleteActivities.append(PROJECTCODES_PROJECTCODEID_FK + " = ").append(activityId);

    // Create sql query
    StringBuffer sbDeleteActivity = new StringBuffer();
    sbDeleteActivity.append("DELETE FROM ");
    sbDeleteActivity.append(TABLE_PROJECTCODE);
    sbDeleteActivity.append(" WHERE ");
    sbDeleteActivity.append(PROJECTCODE_ID_PK + " = ").append(activityId);

    DBConnect access = new DBConnect();
    access.executeUpdate(sbDeleteEntryRows);
    access.executeUpdate(sbDeleteActivities);
    boolean retval = access.executeUpdate(sbDeleteActivity);
    return retval;
  }

  @Override
  public synchronized boolean deleteCompany(int companyId) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("DELETE FROM ");
    sb.append(TABLE_COMPANY);
    sb.append(" WHERE ");
    sb.append(COMPANY_ID_PK + " = ").append(companyId);

    DBConnect access = new DBConnect();
    boolean retval = access.executeUpdate(sb);
    return retval;
  }

  @Override
  public boolean deleteContact(Contacts contact) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("DELETE FROM ");
    sb.append("  contact ");
    sb.append("WHERE ");
    sb.append("  Id = ").append(contact.getId());

    DBConnect access = new DBConnect();
    boolean retval = access.executeUpdate(sb);
    return retval;
  }

  @Override
  public boolean deleteEmail(Email email) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("DELETE FROM ");
    sb.append(TABLE_EMAIL);
    sb.append(" WHERE ");
    sb.append(EMAIL_ID_PK + " = ").append(email.getId());

    DBConnect access = new DBConnect();
    boolean retval = access.executeUpdate(sb);
    return retval;
  }

  @Override
  public boolean deletePhoneNumber(PhoneNumber phone) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("DELETE FROM ");
    sb.append(TABLE_PHONE);
    sb.append(" WHERE ");
    sb.append(PHONE_ID_PK + " = ").append(phone.getPhoneId());

    DBConnect access = new DBConnect();
    boolean retval = access.executeUpdate(sb);
    return retval;
  }

  @Override
  public synchronized boolean deleteProject(int projectId) throws Exception {
    boolean retval = false;

    // Create delete entryrows sql query
    StringBuffer sbDeleteEntryRows = new StringBuffer();
    sbDeleteEntryRows.append("DELETE FROM ");
    sbDeleteEntryRows.append(TABLE_ENTRYROW);
    sbDeleteEntryRows.append(" WHERE ");
    sbDeleteEntryRows.append(ENTRYROW_PROJECTID_FK + " = ").append(projectId);

    // Create delete projectmembers sql query
    StringBuffer sbDeleteProjectMembers = new StringBuffer();
    sbDeleteProjectMembers.append("DELETE FROM ");
    sbDeleteProjectMembers.append(TABLE_PROJECTMEMBERS);
    sbDeleteProjectMembers.append(" WHERE ");
    sbDeleteProjectMembers.append(PROJECTMEMBERS_PROJECTID_FK + " = ").append(projectId);

    // Create delete projectcode/activities sql query
    StringBuffer sbDeleteActivities = new StringBuffer();
    sbDeleteActivities.append("DELETE FROM ");
    sbDeleteActivities.append(TABLE_PROJECTCODES);
    sbDeleteActivities.append(" WHERE ");
    sbDeleteActivities.append(PROJECTCODES_PROJECTID_FK + " = ").append(projectId);

    // Create delete project sql query
    StringBuffer sbDeleteProject = new StringBuffer();
    sbDeleteProject.append("DELETE FROM ");
    sbDeleteProject.append(TABLE_PROJECT);
    sbDeleteProject.append(" WHERE ");
    sbDeleteProject.append(PROJECT_ID_PK + " = ").append(projectId);

    DBConnect access = new DBConnect();

    /* Delete activities connected to project */
    access.executeUpdate(sbDeleteActivities);

    /* Delete projectmembers connected to project */
    access.executeUpdate(sbDeleteProjectMembers);

    /* Delete entryrows connected to project */
    access.executeUpdate(sbDeleteEntryRows);

    /* Delete project */
    retval = access.executeUpdate(sbDeleteProject);
    return retval;
  }

  @Override
  public synchronized boolean deleteProjectActivity(int id) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("DELETE FROM ");
    sb.append(TABLE_PROJECTCODES);
    sb.append(" WHERE ");
    sb.append(PROJECTCODES_ID_PK + " = ").append(id);

    DBConnect access = new DBConnect();
    boolean retval = access.executeUpdate(sb);
    return retval;
  }

  @Override
  public synchronized boolean deleteProjectMember(int id) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("DELETE FROM ITR_ProjectMembers ");
    sb.append("WHERE ");
    sb.append("  Id = ").append(id);

    DBConnect access = new DBConnect();
    boolean retval = access.executeUpdate(sb);
    return retval;
  }

  @Override
  public boolean deleteRow(Row row) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("DELETE FROM ");
    sb.append(TABLE_ENTRYROW);
    sb.append(" WHERE ");
    sb.append(ENTRYROW_ID_PK + " = ").append(row.getRowEntryId());

    DBConnect access = new DBConnect();
    boolean retval = access.executeUpdate(sb);
    return retval;
  }

  @Override
  public synchronized boolean deleteUser(int userId) throws Exception {
    // Create delete entryrows sql query
    StringBuffer sbDeleteEntryRows = new StringBuffer();
    sbDeleteEntryRows.append("DELETE FROM ");
    sbDeleteEntryRows.append(TABLE_ENTRYROW);
    sbDeleteEntryRows.append(" WHERE ");
    sbDeleteEntryRows.append(ENTRYROW_USERID_FK + " = ").append(userId);

    // Create delete userroles sql query
    StringBuffer sbDeleteUserRoles = new StringBuffer();
    sbDeleteUserRoles.append("DELETE FROM ");
    sbDeleteUserRoles.append(TABLE_USERROLES);
    sbDeleteUserRoles.append(" WHERE ");
    sbDeleteUserRoles.append(USERROLES_USERID_FK + " = ").append(userId);

    // Create delete emails sql query
    StringBuffer sbDeleteEmails = new StringBuffer();
    sbDeleteEmails.append("DELETE FROM ");
    sbDeleteEmails.append(TABLE_EMAIL);
    sbDeleteEmails.append(" WHERE ");
    sbDeleteEmails.append(EMAIL_USERID_FK + " = ").append(userId);

    // Create delete Phones sql query
    StringBuffer sbDeletePhones = new StringBuffer();
    sbDeletePhones.append("DELETE FROM ");
    sbDeletePhones.append(TABLE_PHONE);
    sbDeletePhones.append(" WHERE ");
    sbDeletePhones.append(PHONE_USERID_FK + " = ").append(userId);

    // Create sql query
    StringBuffer sbDeleteUser = new StringBuffer();
    sbDeleteUser.append("DELETE FROM ");
    sbDeleteUser.append(TABLE_USER);
    sbDeleteUser.append(" WHERE ");
    sbDeleteUser.append(USER_ID_PK + " = ").append(userId);

    DBConnect access = new DBConnect();

    // Delete the connections to entryrow
    access.executeUpdate(sbDeleteEntryRows);

    // Delete the connections to emails
    access.executeUpdate(sbDeleteEmails);

    // Delete the connections to phones
    access.executeUpdate(sbDeletePhones);

    // Delete the connections to userroles
    access.executeUpdate(sbDeleteUserRoles);
    boolean retval = access.executeUpdate(sbDeleteUser);
    return retval;
  }

  @Override
  public boolean insertRow(Row row, int weekReportId) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("INSERT INTO ");
    sb.append(TABLE_ENTRYROW);
    sb.append(" ( ");
    sb.append(ENTRYROW_MO_HOURS + COMMA);
    sb.append(ENTRYROW_TU_HOURS + COMMA);
    sb.append(ENTRYROW_WE_HOURS + COMMA);
    sb.append(ENTRYROW_TH_HOURS + COMMA);
    sb.append(ENTRYROW_FR_HOURS + COMMA);
    sb.append(ENTRYROW_SA_HOURS + COMMA);
    sb.append(ENTRYROW_SU_HOURS + COMMA);
    sb.append(ENTRYROW_HOURS_SUM + COMMA);
    sb.append(ENTRYROW_TIMETYPEID_FK + COMMA);
    sb.append(ENTRYROW_PROJECTCODEID_FK + COMMA);
    sb.append(ENTRYROW_PROJECTID_FK + COMMA);
    sb.append(ENTRYROW_USERWEEKID_FK + COMMA);
    sb.append(ENTRYROW_USERID_FK);
    sb.append(" ) ");
    sb.append(" VALUES ");
    sb.append(" ( ");
    sb.append(row.getMonday()).append(COMMA);
    sb.append(row.getTuesday()).append(COMMA);
    sb.append(row.getWednesday()).append(COMMA);
    sb.append(row.getThursday()).append(COMMA);
    sb.append(row.getFriday()).append(COMMA);
    sb.append(row.getSaturday()).append(COMMA);
    sb.append(row.getSunday()).append(COMMA);
    sb.append(row.getRowSum()).append(COMMA);
    sb.append(row.getTimeTypeId()).append(COMMA);
    sb.append(row.getProject().getProjectActivityId()).append(COMMA);
    sb.append(row.getProject().getProjectId()).append(COMMA);
    sb.append(weekReportId).append(COMMA);
    sb.append(row.getUserProfile().getUserId());
    sb.append(" ) ");

    DBConnect access = new DBConnect();
    boolean retval = access.executeUpdate(sb);
    return retval;
  }

  @Override
  public boolean updateActivity(Activity activity) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("UPDATE ");
    sb.append("ITR_ProjectCode");
    sb.append(" SET ");
    sb.append("Description = ");
    sb.append(" '").append(activity.getDescription()).append("' , ");
    sb.append("Code = ");
    sb.append(" '").append(activity.getCode()).append("' ");
    sb.append(" WHERE ");
    sb.append(" id = ").append(activity.getId());

    DBConnect access = new DBConnect();
    boolean retval = access.executeUpdate(sb);
    return retval;
  }

  private String getNowWithTime() {
    Calendar cal = Calendar.getInstance();
    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return format1.format(cal.getTime());
  }

  @Override
  public boolean updateApprovedInWeek(int userWeekId, boolean status) throws Exception {
    /* Create sql query */
    StringBuffer sb = new StringBuffer();
    sb.append("UPDATE ");
    sb.append(TABLE_USERWEEK);
    sb.append(" SET ");
    sb.append(USERWEEK_APPROVED + " = ");

    if (status) {
      sb.append(TRUE_ACCESS);
    } else {
      sb.append(FALSE_ACCESS);
    }

    sb.append(", ");
    sb.append(USERWEEK_APPROVEDDATE + " = ");
    if (status) {
      sb.append("'").append(getNowWithTime()).append("'");
    } else {
      sb.append("null");
    }

    sb.append(" WHERE ");
    sb.append(USERWEEK_ID_PK + " = ").append(userWeekId);

    DBConnect access = new DBConnect();
    boolean retval = access.executeUpdate(sb);
    return retval;
  }

  @Override
  public boolean updateComment(int commentId, String comment) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("UPDATE ");
    sb.append(TABLE_COMMENT);
    sb.append(" SET ");
    sb.append(COMMENT_COMMENT + " = " + SINGLE_QUOTE).append(comment).append(SINGLE_QUOTE);
    sb.append(" WHERE ");
    sb.append(COMMENT_ID_PK + " = ").append(commentId);

    DBConnect access = new DBConnect();
    boolean retval = access.executeUpdate(sb);
    return retval;
  }

  @Override
  public boolean updateCompany(Company company) throws Exception {
    // Create sql query
    StringBuffer sb = new StringBuffer();
    sb.append("UPDATE ");
    sb.append(TABLE_COMPANY);
    sb.append(" SET ");
    sb.append(COMPANY_NAME + " = " + SINGLE_QUOTE).append(company.getName()).append(SINGLE_QUOTE + COMMA);
    sb.append(COMPANY_VISITADDRESS_ROW1 + " = " + SINGLE_QUOTE).append(company.getVisitAddressRow1()).append(SINGLE_QUOTE + COMMA);
    sb.append(COMPANY_VISITADDRESS_ROW2 + " = " + SINGLE_QUOTE).append(company.getVisitAddressRow2()).append(SINGLE_QUOTE + COMMA);
    sb.append(COMPANY_VISITADDRESS_ROW3 + " = " + SINGLE_QUOTE).append(company.getVisitAddressRow3()).append(SINGLE_QUOTE + COMMA);
    sb.append(COMPANY_INVOICEADDRESS_ROW1 + " = " + SINGLE_QUOTE).append(company.getInvoiceAddressRow1()).append(SINGLE_QUOTE + COMMA);
    sb.append(COMPANY_INVOICEADDRESS_ROW2 + " = " + SINGLE_QUOTE).append(company.getInvoiceAddressRow2()).append(SINGLE_QUOTE + COMMA);
    sb.append(COMPANY_INVOICEADDRESS_ROW3 + " = " + SINGLE_QUOTE).append(company.getInvoiceAddressRow3()).append(SINGLE_QUOTE);
    sb.append(" WHERE ");
    sb.append(COMPANY_ID_PK + " = ").append(company.getCompanyId());

    DBConnect access = new DBConnect();
    boolean retval = access.executeUpdate(sb);
    return retval;
  }

  @Override
  public boolean updateContacts(Contacts contacts) throws Exception {
    // Create sql query
    StringBuffer sb = new StringBuffer();
    sb.append("UPDATE ");
    sb.append("Contact");
    sb.append(" SET ");
    sb.append("ITR_CompanyId = ");
    sb.append(" ").append(contacts.getITR_CompanyId()).append(" , ");
    sb.append("Position = ");
    sb.append(" '").append(contacts.getPosition()).append("' , ");
    sb.append("FriendLevel = ");
    sb.append(" '").append(contacts.getFriendLevel()).append("' , ");
    sb.append("Description = ");
    sb.append(" '").append(contacts.getDescription()).append("' , ");
    sb.append("FirstContact = ");
    sb.append(" '").append(contacts.getFirstContact().getCalendarInStoreFormat()).append("' ,");
    sb.append("FirstName = ");
    sb.append(" '").append(contacts.getFirstName()).append("' , ");
    sb.append("LastName = ");
    sb.append(" '").append(contacts.getLastName()).append("' , ");
    sb.append("KnownByUser_Id = ");
    sb.append(" ").append(contacts.getKnownByUser_Id());
    sb.append(" WHERE ");
    sb.append(" id = ").append(contacts.getId());

    DBConnect access = new DBConnect();
    boolean retval = access.executeUpdate(sb);

    return retval;
  }

  @Override
  public boolean updateEmail(Email email) throws Exception {
    // Create sql query
    StringBuffer sb = new StringBuffer();
    sb.append("UPDATE ");
    sb.append(TABLE_EMAIL);
    sb.append(" SET ");
    sb.append(EMAIL_EMAIL + " = " + SINGLE_QUOTE).append(email.getAddress()).append(SINGLE_QUOTE + COMMA);
    sb.append(EMAIL_DESCRIPTION + " = " + SINGLE_QUOTE).append(email.getDescription()).append(SINGLE_QUOTE + COMMA);
    sb.append(EMAIL_USERID_FK + " = ").append(email.getUserId()).append(COMMA);
    sb.append(EMAIL_CONTACTID + " = ").append(email.getContactId());
    sb.append(" WHERE ");
    sb.append(EMAIL_ID_PK + " = ").append(email.getId());

    DBConnect access = new DBConnect();
    boolean retval = access.executeUpdate(sb);
    return retval;
  }

  @Override
  public boolean updatePhoneNumber(PhoneNumber phone) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("UPDATE ");
    sb.append(TABLE_PHONE);
    sb.append(" SET ");
    sb.append(PHONE_PHONENUMBER + " = ").append(phone.getPhoneNumber()).append(COMMA);
    sb.append(PHONE_DESCRIPTION + " = " + SINGLE_QUOTE).append(phone.getPhoneDescription()).append(SINGLE_QUOTE + COMMA);
    sb.append(PHONE_USERID_FK + " = ").append(phone.getPhoneUserId()).append(COMMA);
    sb.append(PHONE_CONTACTID_FK + " = ").append(phone.getPhoneContactId());
    sb.append(" WHERE ");
    sb.append(PHONE_ID_PK + " = ").append(phone.getPhoneId());

    DBConnect access = new DBConnect();
    boolean retval = access.executeUpdate(sb);
    return retval;
  }

  @Override
  public boolean updatePhoneRegion(PhoneRegion region) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("UPDATE ");
    sb.append(TABLE_PHONEREGIONCODE);
    sb.append(" SET ");
    sb.append(PHONEREGIONCODE_REGIONCODE + " = " + SINGLE_QUOTE).append(region.getRegionCode()).append(SINGLE_QUOTE + COMMA);
    sb.append(PHONEREGIONCODE_REGIONNAME + " = " + SINGLE_QUOTE).append(region.getRegionName()).append(SINGLE_QUOTE + COMMA);
    sb.append(PHONEREGIONCODE_PHONECOUNTRYCODEID_FK + " = ").append(region.getCountryId());
    sb.append(" WHERE ");
    sb.append(PHONEREGIONCODE_ID_PK + " = ").append(region.getRegionId());

    DBConnect access = new DBConnect();
    boolean retval = access.executeUpdate(sb);
    return retval;
  }

  @Override
  public boolean updateProject(Project project) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("UPDATE ");
    sb.append(TABLE_PROJECT);
    sb.append(" SET ");
    sb.append(PROJECT_ACTIVE + " = ").append(project.getActivated()).append(COMMA);
    sb.append(PROJECT_COMPANYID_FK + " = ").append(project.getCompanyId()).append(COMMA);
    sb.append(PROJECT_DESCRIPTION + " = " + SINGLE_QUOTE).append(project.getProjectDesc()).append(SINGLE_QUOTE + COMMA);
    sb.append(PROJECT_FROMDATE + " = '").append(project.getFromDate().getCalendarInStoreFormat()).append("'" + COMMA);
    sb.append(PROJECT_MAINCODE + " = ").append(project.getProjectCode()).append(COMMA);
    sb.append(PROJECT_NAME + " = " + SINGLE_QUOTE).append(project.getProjectName()).append(SINGLE_QUOTE + COMMA);
    sb.append(PROJECT_ADMINPROJECT + " = ").append(project.getAdminProject()).append(COMMA);
    sb.append(PROJECT_CONTRACT + " = ").append(project.getContract()).append(COMMA);
    sb.append(PROJECT_TECHNIQUE + " = " + SINGLE_QUOTE).append(project.getTechnique()).append(SINGLE_QUOTE + COMMA);
    sb.append(PROJECT_TODATE + " = '").append(project.getToDate().getCalendarInStoreFormat()).append("'");
    sb.append(" WHERE ");
    sb.append(PROJECT_ID_PK + " = ").append(project.getProjectId());

    DBConnect access = new DBConnect();
    boolean retval = access.executeUpdate(sb);
    return retval;
  }

  @Override
  public boolean updateProjectActivity(ProjectActivity projectActivity) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("UPDATE ");
    sb.append("ITR_ProjectCodes ");
    sb.append("SET ");
    sb.append("ITR_ProjectId = ").append(projectActivity.getProjectId()).append(", ");
    sb.append("ITR_ProjectCodeId = ").append(projectActivity.getProjectCodeId()).append(" ");
    sb.append("WHERE ");
    sb.append("id = ").append(projectActivity.getId());

    DBConnect access = new DBConnect();
    boolean retval = access.executeUpdate(sb);
    return retval;
  }

  @Override
  public boolean updateUserWeekComment(int inWeekReportId, int inWeekCommentId) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("UPDATE ITR_UserWeek SET");
    sb.append(" ITR_CommentId = ").append(inWeekCommentId);
    sb.append(" WHERE ");
    sb.append(" Id = ").append(inWeekReportId);

    DBConnect access = new DBConnect();
    boolean retval = access.executeUpdate(sb);
    return retval;
  }

  @Override
  public boolean updateProjectMember(ProjectMember projectMember) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("UPDATE ITR_ProjectMembers SET ");
    sb.append("  ITR_ProjectId = ").append(projectMember.getITR_ProjectId()).append(", ");
    sb.append("  ITR_UserId = ").append(projectMember.getITR_UserId()).append(", ");
    sb.append("  Rate = ").append(projectMember.getRate()).append(", ");
    sb.append("  Active = ").append(projectMember.getActive()).append(", ");
    sb.append("  ProjectAdmin = ").append(projectMember.getProjectAdmin()).append(" ");
    sb.append("WHERE ");
    sb.append("  Id = ").append(projectMember.getId());

    DBConnect access = new DBConnect();
    boolean retval = access.executeUpdate(sb);
    return retval;
  }

  @Override
  public boolean updateRow(Row row, int weekReportId) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("UPDATE ");
    sb.append(TABLE_ENTRYROW);
    sb.append(" SET ");
    sb.append(ENTRYROW_MO_HOURS + " = ").append(row.getMonday()).append(COMMA);
    sb.append(ENTRYROW_TU_HOURS + " = ").append(row.getTuesday()).append(COMMA);
    sb.append(ENTRYROW_WE_HOURS + " = ").append(row.getWednesday()).append(COMMA);
    sb.append(ENTRYROW_TH_HOURS + " = ").append(row.getThursday()).append(COMMA);
    sb.append(ENTRYROW_FR_HOURS + " = ").append(row.getFriday()).append(COMMA);
    sb.append(ENTRYROW_SA_HOURS + " = ").append(row.getSaturday()).append(COMMA);
    sb.append(ENTRYROW_SU_HOURS + " = ").append(row.getSunday()).append(COMMA);
    sb.append(ENTRYROW_HOURS_SUM + " = ").append(row.getRowSum()).append(COMMA);
    sb.append(ENTRYROW_TIMETYPEID_FK + " = ").append(row.getTimeTypeId()).append(COMMA);
    sb.append(ENTRYROW_PROJECTCODEID_FK + " = ").append(row.getProject().getProjectActivityId()).append(COMMA);
    sb.append(ENTRYROW_PROJECTID_FK + " = ").append(row.getProject().getProjectId()).append(COMMA);
    sb.append(ENTRYROW_USERWEEKID_FK + " = ").append(weekReportId).append(COMMA);
    sb.append(ENTRYROW_USERID_FK + " = ").append(row.getUserProfile().getUserId());
    sb.append(" WHERE ");
    sb.append(TABLE_ENTRYROW_DOT + ENTRYROW_ID_PK + " = ").append(row.getRowEntryId());

    DBConnect access = new DBConnect();
    boolean retval = access.executeUpdate(sb);
    return retval;
  }

  @Override
  public boolean updateSubmitInWeek(int userWeekId, boolean status) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("UPDATE ");
    sb.append(TABLE_USERWEEK);
    sb.append(" SET ");
    sb.append(USERWEEK_SUBMITTED + " = ");

    if (status) {
      sb.append(TRUE_ACCESS);
    } else {
      sb.append(FALSE_ACCESS);
    }

    sb.append(", ");
    sb.append(USERWEEK_SUBMITTEDDATE).append(" = ");
    if (status) {
      sb.append("'").append(getNowWithTime()).append("'");
    } else {
      sb.append("null");
    }

    sb.append(" WHERE ");
    sb.append(USERWEEK_ID_PK).append(" = ").append(userWeekId);

    DBConnect access = new DBConnect();
    boolean retval = access.executeUpdate(sb);
    return retval;
  }

  @Override
  public boolean updateUserProfile(UserProfile profile) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("UPDATE ");
    sb.append(TABLE_USER);
    sb.append(" SET ");
    sb.append(USER_FIRSTNAME + " = " + SINGLE_QUOTE).append(profile.getFirstName()).append(SINGLE_QUOTE + COMMA);
    sb.append(USER_LASTNAME + " = " + SINGLE_QUOTE).append(profile.getLastName()).append(SINGLE_QUOTE + COMMA);
    sb.append(USER_LANGUAGEID_FK + " = ").append(profile.getLanguageId()).append(COMMA);
    sb.append(USER_LOGINID + " = " + SINGLE_QUOTE).append(profile.getLoginId()).append(SINGLE_QUOTE + COMMA);
    sb.append(USER_PASSWORD + " = " + SINGLE_QUOTE).append(profile.getPassword()).append(SINGLE_QUOTE + COMMA);
    sb.append(USER_COMPANYID_FK + " = ").append(profile.getCompanyId()).append(COMMA);
    sb.append(USER_VACATION_DEFAULT_DAYS + " = ").append(profile.getDefaultVacationDays()).append(COMMA);
    sb.append(USER_VACATION_USED_DAYS + " = ").append(profile.getUsedVacationDays()).append(COMMA);
    sb.append(USER_VACATION_SAVED_DAYS + " = ").append(profile.getSavedVacationDays()).append(COMMA);
    sb.append(USER_OVERTIME_VACATION_HOURS + " = ").append(profile.getOvertimeVacationHours()).append(COMMA);
    sb.append(USER_OVERTIME_MONEY_HOURS + " = ").append(profile.getOvertimeMoneyHours()).append(COMMA);

    if (profile.getActivated()) {
      sb.append(USER_ACTIVATED + " = " + TRUE_ACCESS + COMMA);
    } else {
      sb.append(USER_ACTIVATED + " = " + FALSE_ACCESS + COMMA);
    }

    sb.append(USER_ACTIVATED_DATE + " = '").append(profile.getActivatedDate().getCalendarInStoreFormat()).append("'" + COMMA);
    sb.append(USER_DEACTIVATED_DATE + " = '").append(profile.getDeActivatedDate().getCalendarInStoreFormat()).append("'" + COMMA);
    sb.append(USER_CREATED_DATE + " = '").append(profile.getCreatedDate().getCalendarInStoreFormat()).append("'" + COMMA);
    sb.append(USER_REPORT_APPROVERID_FK + " = ").append(profile.getReportApproverId()).append(COMMA);
    sb.append(USER_SKINID_FK + " = ").append(profile.getSkinId());
    sb.append(" WHERE ");
    sb.append(USER_ID_PK + " = ").append(profile.getUserId());

    DBConnect access = new DBConnect();
    boolean retval = access.executeUpdate(sb);
    return retval;
  }

  @Override
  public boolean updateUserRoleConnection(Role role) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("UPDATE ");
    sb.append(TABLE_USERROLES);
    sb.append(" SET ");
    sb.append(USERROLES_ROLESID_FK + " = ").append(role.getRoleId());
    sb.append(" WHERE ");
    sb.append(USERROLES_ID_PK + " = ").append(role.getId());

    DBConnect access = new DBConnect();
    boolean retval = access.executeUpdate(sb);
    return retval;
  }

  @Override
  public boolean addActivity(Activity activity) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("INSERT INTO ");
    sb.append("ITR_ProjectCode");
    sb.append(" ( ");
    sb.append("Description, ");
    sb.append("Code ");
    sb.append(" ) ");
    sb.append(" VALUES ");
    sb.append(" ( ");
    sb.append("'").append(activity.getDescription()).append("' , ");
    sb.append("'").append(activity.getCode()).append("' ");
    sb.append(" ) ");

    DBConnect access = new DBConnect();
    boolean retval = access.executeUpdate(sb);
    return retval;
  }

  @Override
  public boolean addCompany(Company company) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("INSERT INTO ");
    sb.append(TABLE_COMPANY);
    sb.append(" (" + COMPANY_NAME + COMMA);
    sb.append(" " + COMPANY_VISITADDRESS_ROW1 + COMMA);
    sb.append(" " + COMPANY_VISITADDRESS_ROW2 + COMMA);
    sb.append(" " + COMPANY_VISITADDRESS_ROW3 + COMMA);
    sb.append(" " + COMPANY_INVOICEADDRESS_ROW1 + COMMA);
    sb.append(" " + COMPANY_INVOICEADDRESS_ROW2 + COMMA);
    sb.append(" " + COMPANY_INVOICEADDRESS_ROW3 + ") ");
    sb.append(" VALUES ");
    sb.append(" (" + SINGLE_QUOTE).append(company.getName()).append(SINGLE_QUOTE + COMMA);
    sb.append(" " + SINGLE_QUOTE).append(company.getVisitAddressRow1()).append(SINGLE_QUOTE + COMMA);
    sb.append(" " + SINGLE_QUOTE).append(company.getVisitAddressRow2()).append(SINGLE_QUOTE + COMMA);
    sb.append(" " + SINGLE_QUOTE).append(company.getVisitAddressRow3()).append(SINGLE_QUOTE + COMMA);
    sb.append(" " + SINGLE_QUOTE).append(company.getInvoiceAddressRow1()).append(SINGLE_QUOTE + COMMA);
    sb.append(" " + SINGLE_QUOTE).append(company.getInvoiceAddressRow2()).append(SINGLE_QUOTE + COMMA);
    sb.append(" " + SINGLE_QUOTE).append(company.getInvoiceAddressRow3()).append(SINGLE_QUOTE + ") ");

    DBConnect access = new DBConnect();
    boolean retval = access.executeUpdate(sb);
    return retval;
  }

  @Override
  public boolean addContacts(Contacts contacts) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("INSERT INTO ");
    sb.append("Contact");
    sb.append(" ( ");
    sb.append("ITR_CompanyId, ");
    sb.append("Position, ");
    sb.append("FriendLevel, ");
    sb.append("Description, ");
    sb.append("FirstContact, ");
    sb.append("FirstName, ");
    sb.append("LastName, ");
    sb.append("KnownByUser_Id ");
    sb.append(" ) ");
    sb.append(" VALUES ");
    sb.append(" ( ");
    sb.append(contacts.getITR_CompanyId()).append(" , ");
    sb.append("'").append(contacts.getPosition()).append("' , ");
    sb.append("'").append(contacts.getFriendLevel()).append("' , ");
    sb.append("'").append(contacts.getDescription()).append("' , ");
    sb.append("'").append(contacts.getFirstContact().getCalendarInStoreFormat()).append("' ,");
    sb.append("'").append(contacts.getFirstName()).append("' , ");
    sb.append("'").append(contacts.getLastName()).append("' , ");
    sb.append(contacts.getKnownByUser_Id());
    sb.append(" ) ");

    DBConnect access = new DBConnect();
    boolean retval = access.executeUpdate(sb);
    return retval;
  }

  @Override
  public boolean addEmail(Email email) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("INSERT INTO ");
    sb.append(TABLE_EMAIL);
    sb.append(" (" + EMAIL_EMAIL + COMMA);
    sb.append(" " + EMAIL_DESCRIPTION + COMMA);
    sb.append(" " + EMAIL_USERID_FK + COMMA);
    sb.append(" " + EMAIL_CONTACTID + ") ");
    sb.append(" VALUES ");
    sb.append(" (" + SINGLE_QUOTE).append(email.getAddress()).append(SINGLE_QUOTE + COMMA);
    sb.append(" " + SINGLE_QUOTE).append(email.getDescription()).append(SINGLE_QUOTE + COMMA);
    sb.append(" ").append(email.getUserId()).append(COMMA);
    sb.append(" ").append(email.getContactId()).append(") ");

    DBConnect access = new DBConnect();
    boolean retval = access.executeUpdate(sb);
    return retval;
  }

  @Override
  public boolean addPhoneNumber(PhoneNumber phone) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("INSERT INTO ");
    sb.append(TABLE_PHONE);
    sb.append(" (" + PHONE_PHONENUMBER + COMMA);
    sb.append(" " + PHONE_DESCRIPTION + COMMA);
    sb.append(" " + PHONE_USERID_FK + COMMA);
    sb.append(" " + PHONE_REGIONID_FK + COMMA);
    sb.append(" " + PHONE_CONTACTID_FK + ") ");
    sb.append(" VALUES ");
    sb.append(" (").append(phone.getPhoneNumber()).append(COMMA);
    sb.append(" " + SINGLE_QUOTE).append(phone.getPhoneDescription()).append(SINGLE_QUOTE + COMMA);
    sb.append(" ").append(phone.getPhoneUserId()).append(COMMA);
    sb.append(" ").append(phone.getRegion().getRegionId()).append(COMMA);
    sb.append(" ").append(phone.getPhoneContactId()).append(") ");

    DBConnect access = new DBConnect();
    boolean retval = access.executeUpdate(sb);
    return retval;
  }

  @Override
  public boolean addProjectActivity(ProjectActivity projectActivity) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("INSERT INTO ");
    sb.append("ITR_ProjectCodes");
    sb.append(" ( ");
    sb.append("ITR_ProjectId, ");
    sb.append("ITR_ProjectCodeId ");
    sb.append(" ) ");
    sb.append(" VALUES ");
    sb.append(" ( ");
    sb.append(projectActivity.getProjectId()).append(" , ");
    sb.append(projectActivity.getProjectCodeId()).append(" ");
    sb.append(" ) ");

    DBConnect access = new DBConnect();
    boolean retval = access.executeUpdate(sb);
    return retval;
  }

  @Override
  public boolean addProjectMember(ProjectMember projectMember) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("INSERT INTO ITR_ProjectMembers ( ");
    sb.append("    ITR_ProjectId, ");
    sb.append("    ITR_UserId, ");
    sb.append("    Rate, ");
    sb.append("    Active, ");
    sb.append("    ProjectAdmin ");
    sb.append(") VALUES ( ");
    sb.append(projectMember.getITR_ProjectId()).append(", ");
    sb.append(projectMember.getITR_UserId()).append(", ");
    sb.append(projectMember.getRate()).append(", ");
    sb.append(projectMember.getActive()).append(", ");
    sb.append(projectMember.getProjectAdmin()).append(" ");
    sb.append(") ");

    DBConnect access = new DBConnect();
    boolean retval = access.executeUpdate(sb);
    return retval;
  }

  @Override
  public boolean addUserRoleConnection(Role role) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("INSERT INTO ");
    sb.append(TABLE_USERROLES);
    sb.append(" (" + USERROLES_ROLESID_FK + COMMA);
    sb.append(" " + USERROLES_USERID_FK + ") ");
    sb.append(" VALUES ");
    sb.append(" (").append(role.getRoleId()).append(COMMA);
    sb.append(role.getUserId()).append(") ");

    DBConnect access = new DBConnect();
    boolean retval = access.executeUpdate(sb);
    return retval;
  }

  @Override
  public boolean makeNewComment(String comment) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("INSERT INTO ");
    sb.append(TABLE_COMMENT);
    sb.append(" (" + COMMENT_COMMENT + ") ");
    sb.append(" VALUES ");
    sb.append("(" + SINGLE_QUOTE).append(comment).append(SINGLE_QUOTE + ")");

    DBConnect access = new DBConnect();
    boolean retval = access.executeUpdate(sb);
    return retval;
  }

  @Override
  public boolean makeNewProject(Project project) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("INSERT INTO ITR_Project ( \n");
    sb.append("    ITR_CompanyId, \n");
    sb.append("    Name, \n");
    sb.append("    Description, \n");
    sb.append("    Technique, \n");
    sb.append("    FromDate, \n");
    sb.append("    ToDate, \n");
    sb.append("    Contract, \n");
    sb.append("    Active, \n");
    sb.append("    MainCode, \n");
    sb.append("    AdminProject \n");
    sb.append(") VALUES ( \n");
    sb.append("    ").append(project.getCompanyId()).append(", \n");
    sb.append("    '").append(project.getProjectName()).append("', \n");
    sb.append("    '").append(project.getProjectDesc()).append("', \n");
    sb.append("    '").append(project.getTechnique()).append("', \n");
    sb.append("    '").append(project.getFromDate().getCalendarInStoreFormat()).append("', \n");
    sb.append("    '").append(project.getToDate().getCalendarInStoreFormat()).append("', \n");
    sb.append("    ").append(project.getContract()).append(", \n");
    sb.append("    ").append(project.getActivated()).append(", \n");
    sb.append("    '").append(project.getProjectCode()).append("', \n");
    sb.append("    ").append(project.getAdminProject()).append(" \n");
    sb.append(") \n");

    DBConnect access = new DBConnect();
    boolean retval = access.executeUpdate(sb);
    return retval;
  }

  @Override
  public boolean makeNewUserProfile(UserProfile profile) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("INSERT INTO ");
    sb.append(TABLE_USER);
    sb.append(" ( ");
    sb.append(USER_FIRSTNAME + COMMA);
    sb.append(USER_LASTNAME + COMMA);
    sb.append(USER_LANGUAGEID_FK + COMMA);
    sb.append(USER_LOGINID + COMMA);
    sb.append(USER_PASSWORD + COMMA);
    sb.append(USER_COMPANYID_FK + COMMA);
    sb.append(USER_VACATION_DEFAULT_DAYS + COMMA);
    sb.append(USER_VACATION_USED_DAYS + COMMA);
    sb.append(USER_VACATION_SAVED_DAYS + COMMA);
    sb.append(USER_OVERTIME_VACATION_HOURS + COMMA);
    sb.append(USER_OVERTIME_MONEY_HOURS + COMMA);
    sb.append(USER_REPORT_APPROVERID_FK + COMMA);
    sb.append(USER_ACTIVATED + COMMA);
    sb.append(USER_ACTIVATED_DATE + COMMA);
    sb.append(USER_DEACTIVATED_DATE + COMMA);
    sb.append(USER_CREATED_DATE + COMMA);
    sb.append(USER_SKINID_FK);
    sb.append(" ) ");
    sb.append(" VALUES ");
    sb.append(" ( ");
    sb.append(SINGLE_QUOTE).append(profile.getFirstName()).append(SINGLE_QUOTE + COMMA);
    sb.append(SINGLE_QUOTE).append(profile.getLastName()).append(SINGLE_QUOTE + COMMA);
    sb.append(profile.getLanguageId()).append(COMMA);
    sb.append(SINGLE_QUOTE).append(profile.getLoginId()).append(SINGLE_QUOTE + COMMA);
    sb.append(SINGLE_QUOTE).append(profile.getPassword()).append(SINGLE_QUOTE + COMMA);
    sb.append(profile.getCompanyId()).append(COMMA);
    sb.append(profile.getDefaultVacationDays()).append(COMMA);
    sb.append(profile.getUsedVacationDays()).append(COMMA);
    sb.append(profile.getSavedVacationDays()).append(COMMA);
    sb.append(profile.getOvertimeVacationHours()).append(COMMA);
    sb.append(profile.getOvertimeMoneyHours()).append(COMMA);
    sb.append(profile.getReportApproverId()).append(COMMA);

    if (profile.getActivated()) {
      sb.append(TRUE_ACCESS + COMMA);
    } else {
      sb.append(FALSE_ACCESS + COMMA);
    }

    sb.append("'").append(profile.getActivatedDate().getCalendarInStoreFormat()).append("'" + COMMA);
    sb.append("'").append(profile.getDeActivatedDate().getCalendarInStoreFormat()).append("'" + COMMA);
    sb.append("'").append(profile.getCreatedDate().getCalendarInStoreFormat()).append("'" + COMMA);
    sb.append(profile.getSkinId());
    sb.append(" ) ");

    DBConnect access = new DBConnect();
    boolean retval = access.executeUpdate(sb);
    return retval;
  }

  @Override
  public boolean makeNewUserWeekId(String calendarWeekId, int weekCommentId) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("INSERT INTO ");
    sb.append(TABLE_USERWEEK);
    sb.append(" ( ");
    sb.append(USERWEEK_CALENDARWEEK_ID_FK + COMMA);
    sb.append(USERWEEK_COMMENTID_FK);
    sb.append(" ) ");
    sb.append(" VALUES ");
    sb.append(" ( ");
    sb.append(calendarWeekId).append(COMMA);
    sb.append(weekCommentId);
    sb.append(" ) ");

    DBConnect access = new DBConnect();
    boolean retval = access.executeUpdate(sb);
    return retval;
  }

  @Override
  public void saveLog(List<LoggerVO> list) throws Exception {
    if (list == null || list.isEmpty()) {
      return;
    }

    String sqlQuery = "INSERT INTO ITR_LOG (itr_userid, anropstidims, felmeddelande, inparameter, utparameter, metodnamn, sessionid, timestamp) values (?,?,?,?,?,?,?,?)";
    Connection conn = null;
    PreparedStatement pstmt = null;

    try {
      conn = DBConnect.getConnection();
      pstmt = conn.prepareStatement(sqlQuery);

      for (LoggerVO log : list) {
        int i = 1;
        pstmt.setInt(i++, log.getUserId());
        pstmt.setInt(i++, log.getAnropstidInMs());
        pstmt.setString(i++, log.getFelmeddelande());
        pstmt.setString(i++, log.getInParameter());
        pstmt.setString(i++, log.getUtParameter());
        pstmt.setString(i++, log.getMetodnamn());
        pstmt.setString(i++, log.getSessionId());
        java.sql.Timestamp t = new java.sql.Timestamp(log.getTimestamp().getTime());
        pstmt.setTimestamp(i++, t);
        pstmt.addBatch();
      }
      pstmt.executeBatch();
    } catch (SQLException e) {
      throw e;
    } finally {
      if (pstmt != null) {
        pstmt.close();
      }
      if (conn != null) {
        conn.close();
      }
    }
  }

  @Override
  public void saveStatistics(StatisticsVO statistics) throws Exception {
    if (statistics == null || statistics.getStatistics() == null || statistics.getStatistics().isEmpty()) {
      return;
    }

    String sqlQuery = "INSERT INTO itr_statistics(hits, action, methodcalled, status, timestamp) values (?,?,?,?,?)";

    Connection conn = null;
    PreparedStatement pstmt = null;

    try {
      conn = DBConnect.getConnection();
      pstmt = conn.prepareStatement(sqlQuery);

      for (StatisticVO log : statistics.getStatistics()) {
        int i = 1;
        pstmt.setInt(i++, log.getHits());
        pstmt.setString(i++, log.getAction());
        pstmt.setString(i++, log.getMethodCalled());
        pstmt.setString(i++, log.getStatus());
        java.sql.Timestamp t = new java.sql.Timestamp(log.getTimestamp().getTime());
        pstmt.setTimestamp(i++, t);
        pstmt.addBatch();
      }
      pstmt.executeBatch();
    } catch (SQLException e) {
      throw e;
    } finally {
      if (pstmt != null) {
        pstmt.close();
      }
      if (conn != null) {
        conn.close();
      }
    }
  }
}
