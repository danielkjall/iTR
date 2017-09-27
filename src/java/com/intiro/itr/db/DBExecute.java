/**
 * Title:         ITR
 * Description:
 * Copyright:     Copyright (c) 2001
 * Company:       Intiro Development AB
 * @author        Daniel Kjäll
 * @version       1.0
 */
package com.intiro.itr.db;

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
import com.intiro.itr.util.personalization.Role;
import com.intiro.itr.util.personalization.UserProfile;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DBExecute implements DBConstants {

  // ~ Instance/static variables ........................................................................................

  private UserProfile userProfile;

  // ~ Constructors .....................................................................................................

  /**
   * Constructor I for DBExecute.
   * 
   */
  public DBExecute() {
    // empty
  }

  /**
   * Constructor II for DBExecute.
   * 
   * @param userProfile
   *          the userProfile.
   */
  public DBExecute(UserProfile userProfile) {
    this.userProfile = userProfile;
  }

  // ~ Methods ..........................................................................................................

  /**
   * Return the settings for the ITR.
   */
  public boolean changePassword(String newLoginId, String newPassword) throws Exception {

    /* Create sql query */
    StringBuffer sb = new StringBuffer();
    sb.append("UPDATE ");
    sb.append(TABLE_USER);
    sb.append(" SET ");
    sb.append(USER_PASSWORD + " = " + SINGLE_QUOTE + newPassword + SINGLE_QUOTE + COMMA);
    sb.append(USER_LOGINID + " = " + SINGLE_QUOTE + newLoginId + SINGLE_QUOTE);
    sb.append(" WHERE ");
    sb.append(USER_ID_PK + " = " + userProfile.getUserId());

    /* Create connection to database */
    DBConnect access = new DBConnect();

    /* Get boolean */
    boolean retval = access.executeUpdate(sb);

    /* Return boolean */
    return retval;
  }

  public synchronized boolean deactivateProjectMember(int id) throws Exception {

    StringBuffer sb = new StringBuffer();
    sb.append("UPDATE ITR_ProjectMembers SET \n");
    sb.append("    Active = " + false + " \n");
    sb.append("WHERE \n");
    sb.append("    Id = " + id + " \n");

    DBConnect access = new DBConnect();
    boolean retval = access.executeUpdate(sb);

    return retval;
  }

  public synchronized boolean deleteActivity(int activityId) throws Exception {

    // Create delete entryrows sql query
    StringBuffer sbDeleteEntryRows = new StringBuffer();
    sbDeleteEntryRows.append("DELETE FROM ");
    sbDeleteEntryRows.append(TABLE_ENTRYROW);
    sbDeleteEntryRows.append(" WHERE ");
    sbDeleteEntryRows.append(ENTRYROW_PROJECTCODEID_FK + " = " + activityId);

    // Create delete projectcode/activities sql query
    StringBuffer sbDeleteActivities = new StringBuffer();
    sbDeleteActivities.append("DELETE FROM ");
    sbDeleteActivities.append(TABLE_PROJECTCODES);
    sbDeleteActivities.append(" WHERE ");
    sbDeleteActivities.append(PROJECTCODES_PROJECTCODEID_FK + " = " + activityId);

    // Create sql query
    StringBuffer sbDeleteActivity = new StringBuffer();
    sbDeleteActivity.append("DELETE FROM ");
    sbDeleteActivity.append(TABLE_PROJECTCODE);
    sbDeleteActivity.append(" WHERE ");
    sbDeleteActivity.append(PROJECTCODE_ID_PK + " = " + activityId);

    /* Create connection to database */
    DBConnect access = new DBConnect();

    // Delete the connections to entryrow
    access.executeUpdate(sbDeleteEntryRows);

    // Delete the connections to projects.
    access.executeUpdate(sbDeleteActivities);

    /* Get boolean */
    boolean retval = access.executeUpdate(sbDeleteActivity);

    /* Return boolean */
    return retval;
  }

  public synchronized boolean deleteCompany(int companyId) throws Exception {

    // Create sql query
    StringBuffer sb = new StringBuffer();
    sb.append("DELETE FROM ");
    sb.append(TABLE_COMPANY);
    sb.append(" WHERE ");
    sb.append(COMPANY_ID_PK + " = " + companyId);

    /* Create connection to database */
    DBConnect access = new DBConnect();

    /* Get boolean */
    boolean retval = access.executeUpdate(sb);

    /* Return boolean */
    return retval;
  }

  /**
   * Delete a contact.
   */
  public boolean deleteContact(Contacts contact) throws Exception {

    StringBuffer sb = new StringBuffer();

    /* Create sql query */
    sb.append("DELETE FROM ");
    sb.append("   contact ");
    sb.append("WHERE ");
    sb.append("   Id = " + contact.getId());

    /* Create connection to database */
    DBConnect access = new DBConnect();

    /* Get boolean */
    boolean retval = access.executeUpdate(sb);

    /* Return boolean */
    return retval;
  }

  /**
   * Delete email.
   */
  public boolean deleteEmail(Email email) throws Exception {

    StringBuffer sb = new StringBuffer();

    /* Create sql query */
    sb.append("DELETE FROM ");
    sb.append(TABLE_EMAIL);
    sb.append(" WHERE ");
    sb.append(EMAIL_ID_PK + " = " + email.getId());

    /* Create connection to database */
    DBConnect access = new DBConnect();

    /* Get boolean */
    boolean retval = access.executeUpdate(sb);

    /* Return boolean */
    return retval;
  }

  /**
   * Delete phone number.
   */
  public boolean deletePhoneNumber(PhoneNumber phone) throws Exception {

    StringBuffer sb = new StringBuffer();

    /* Create sql query */
    sb.append("DELETE FROM ");
    sb.append(TABLE_PHONE);
    sb.append(" WHERE ");
    sb.append(PHONE_ID_PK + " = " + phone.getPhoneId());

    /* Create connection to database */
    DBConnect access = new DBConnect();

    /* Get boolean */
    boolean retval = access.executeUpdate(sb);

    /* Return boolean */
    return retval;
  }

  public synchronized boolean deleteProject(int projectId) throws Exception {

    boolean retval = false;

    // Create delete entryrows sql query
    StringBuffer sbDeleteEntryRows = new StringBuffer();
    sbDeleteEntryRows.append("DELETE FROM ");
    sbDeleteEntryRows.append(TABLE_ENTRYROW);
    sbDeleteEntryRows.append(" WHERE ");
    sbDeleteEntryRows.append(ENTRYROW_PROJECTID_FK + " = " + projectId);

    // Create delete projectmembers sql query
    StringBuffer sbDeleteProjectMembers = new StringBuffer();
    sbDeleteProjectMembers.append("DELETE FROM ");
    sbDeleteProjectMembers.append(TABLE_PROJECTMEMBERS);
    sbDeleteProjectMembers.append(" WHERE ");
    sbDeleteProjectMembers.append(PROJECTMEMBERS_PROJECTID_FK + " = " + projectId);

    // Create delete projectcode/activities sql query
    StringBuffer sbDeleteActivities = new StringBuffer();
    sbDeleteActivities.append("DELETE FROM ");
    sbDeleteActivities.append(TABLE_PROJECTCODES);
    sbDeleteActivities.append(" WHERE ");
    sbDeleteActivities.append(PROJECTCODES_PROJECTID_FK + " = " + projectId);

    // Create delete project sql query
    StringBuffer sbDeleteProject = new StringBuffer();
    sbDeleteProject.append("DELETE FROM ");
    sbDeleteProject.append(TABLE_PROJECT);
    sbDeleteProject.append(" WHERE ");
    sbDeleteProject.append(PROJECT_ID_PK + " = " + projectId);

    /* Create connection to database */
    DBConnect access = new DBConnect();

    /* Delete activities connected to project */
    access.executeUpdate(sbDeleteActivities);

    /* Delete projectmembers connected to project */
    access.executeUpdate(sbDeleteProjectMembers);

    /* Delete entryrows connected to project */
    access.executeUpdate(sbDeleteEntryRows);

    /* Delete project */
    retval = access.executeUpdate(sbDeleteProject);

    /* Return boolean */
    return retval;
  }

  public synchronized boolean deleteProjectActivity(int id) throws Exception {

    // Create delete entryrows sql query
    StringBuffer sb = new StringBuffer();
    sb.append("DELETE FROM ");
    sb.append(TABLE_PROJECTCODES);
    sb.append(" WHERE ");
    sb.append(PROJECTCODES_ID_PK + " = " + id);

    /* Create connection to database */
    DBConnect access = new DBConnect();

    /* Get boolean */
    boolean retval = access.executeUpdate(sb);

    /* Return boolean */
    return retval;
  }

  public synchronized boolean deleteProjectMember(int id) throws Exception {

    StringBuffer sb = new StringBuffer();
    sb.append("DELETE FROM ITR_ProjectMembers ");
    sb.append("WHERE ");
    sb.append("    Id = " + id);

    /* Create connection to database */
    DBConnect access = new DBConnect();

    /* Get boolean */
    boolean retval = access.executeUpdate(sb);

    /* Return boolean */
    return retval;
  }

  /**
   * Delete a row.
   */
  public boolean deleteRow(Row row) throws Exception {

    StringBuffer sb = new StringBuffer();

    /* Create sql query */
    sb.append("DELETE FROM ");
    sb.append(TABLE_ENTRYROW);
    sb.append(" WHERE ");
    sb.append(ENTRYROW_ID_PK + " = " + row.getRowEntryId());

    /* Create connection to database */
    DBConnect access = new DBConnect();

    /* Get boolean */
    boolean retval = access.executeUpdate(sb);

    /* Return boolean */
    return retval;
  }

  public synchronized boolean deleteUser(int userId) throws Exception {

    // Create delete entryrows sql query
    StringBuffer sbDeleteEntryRows = new StringBuffer();
    sbDeleteEntryRows.append("DELETE FROM ");
    sbDeleteEntryRows.append(TABLE_ENTRYROW);
    sbDeleteEntryRows.append(" WHERE ");
    sbDeleteEntryRows.append(ENTRYROW_USERID_FK + " = " + userId);

    // Create delete userroles sql query
    StringBuffer sbDeleteUserRoles = new StringBuffer();
    sbDeleteUserRoles.append("DELETE FROM ");
    sbDeleteUserRoles.append(TABLE_USERROLES);
    sbDeleteUserRoles.append(" WHERE ");
    sbDeleteUserRoles.append(USERROLES_USERID_FK + " = " + userId);

    // Create delete emails sql query
    StringBuffer sbDeleteEmails = new StringBuffer();
    sbDeleteEmails.append("DELETE FROM ");
    sbDeleteEmails.append(TABLE_EMAIL);
    sbDeleteEmails.append(" WHERE ");
    sbDeleteEmails.append(EMAIL_USERID_FK + " = " + userId);

    // Create delete Phones sql query
    StringBuffer sbDeletePhones = new StringBuffer();
    sbDeletePhones.append("DELETE FROM ");
    sbDeletePhones.append(TABLE_PHONE);
    sbDeletePhones.append(" WHERE ");
    sbDeletePhones.append(PHONE_USERID_FK + " = " + userId);

    // Create sql query
    StringBuffer sbDeleteUser = new StringBuffer();
    sbDeleteUser.append("DELETE FROM ");
    sbDeleteUser.append(TABLE_USER);
    sbDeleteUser.append(" WHERE ");
    sbDeleteUser.append(USER_ID_PK + " = " + userId);

    /* Create connection to database */
    DBConnect access = new DBConnect();

    // Delete the connections to entryrow
    access.executeUpdate(sbDeleteEntryRows);

    // Delete the connections to emails
    access.executeUpdate(sbDeleteEmails);

    // Delete the connections to phones
    access.executeUpdate(sbDeletePhones);

    // Delete the connections to userroles
    access.executeUpdate(sbDeleteUserRoles);

    /* Get boolean */
    boolean retval = access.executeUpdate(sbDeleteUser);

    /* Return boolean */
    return retval;
  }

  /**
   * Insert a row.
   */
  public boolean insertRow(Row row, int weekReportId) throws Exception {

    StringBuffer sb = new StringBuffer();

    /* Create sql query */
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
    sb.append(row.getMonday() + COMMA);
    sb.append(row.getTuesday() + COMMA);
    sb.append(row.getWednesday() + COMMA);
    sb.append(row.getThursday() + COMMA);
    sb.append(row.getFriday() + COMMA);
    sb.append(row.getSaturday() + COMMA);
    sb.append(row.getSunday() + COMMA);
    sb.append(row.getRowSum() + COMMA);
    sb.append(row.getTimeTypeId() + COMMA);
    sb.append(row.getProject().getProjectActivityId() + COMMA);
    sb.append(row.getProject().getProjectId() + COMMA);
    sb.append(weekReportId + COMMA);
    sb.append(row.getUserProfile().getUserId());
    sb.append(" ) ");

    /* Create connection to database */
    DBConnect access = new DBConnect();

    /* Get boolean */
    boolean retval = access.executeUpdate(sb);

    /* Return boolean */
    return retval;
  }

  public boolean updateActivity(Activity activity) throws Exception {

    // Create sql query
    StringBuffer sb = new StringBuffer();
    sb.append("UPDATE ");
    sb.append("ITR_ProjectCode");
    sb.append(" SET ");
    sb.append("Description = ");
    sb.append(" '" + activity.getDescription() + "' , ");
    sb.append("Code = ");
    sb.append(" '" + activity.getCode() + "' ");
    sb.append(" WHERE ");
    sb.append(" id = " + activity.getId());

    DBConnect access = new DBConnect();
    boolean retval = access.executeUpdate(sb);

    return retval;
  }

  private String getNowWithTime() {
    Calendar cal = Calendar.getInstance();
    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return format1.format(cal.getTime());      
  }
  
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
      sb.append("'" + getNowWithTime() + "'");
    }
    else 
    {
      sb.append("null");
    }
    
    sb.append(" WHERE ");
    sb.append(USERWEEK_ID_PK + " = " + userWeekId);

    /* Create connection to database */
    DBConnect access = new DBConnect();

    /* Get boolean */
    boolean retval = access.executeUpdate(sb);

    /* Return boolean */
    return retval;
  }

  public boolean updateComment(int commentId, String comment) throws Exception {
    /* Create sql query */
    StringBuffer sb = new StringBuffer();
    sb.append("UPDATE ");
    sb.append(TABLE_COMMENT);
    sb.append(" SET ");
    sb.append(COMMENT_COMMENT + " = " + SINGLE_QUOTE + comment + SINGLE_QUOTE);
    sb.append(" WHERE ");
    sb.append(COMMENT_ID_PK + " = " + commentId);

    System.out.println("sql updating comment = " + sb.toString());

    /* Create connection to database */
    DBConnect access = new DBConnect();

    /* Get boolean */
    boolean retval = access.executeUpdate(sb);

    /* Return boolean */
    return retval;
  }

  public boolean updateCompany(Company company) throws Exception {
    // Create sql query
    StringBuffer sb = new StringBuffer();
    sb.append("UPDATE ");
    sb.append(TABLE_COMPANY);
    sb.append(" SET ");
    sb.append(COMPANY_NAME + " = " + SINGLE_QUOTE + company.getName() + SINGLE_QUOTE + COMMA);
    sb.append(COMPANY_VISITADDRESS_ROW1 + " = " + SINGLE_QUOTE + company.getVisitAddressRow1() + SINGLE_QUOTE + COMMA);
    sb.append(COMPANY_VISITADDRESS_ROW2 + " = " + SINGLE_QUOTE + company.getVisitAddressRow2() + SINGLE_QUOTE + COMMA);
    sb.append(COMPANY_VISITADDRESS_ROW3 + " = " + SINGLE_QUOTE + company.getVisitAddressRow3() + SINGLE_QUOTE + COMMA);
    sb.append(COMPANY_INVOICEADDRESS_ROW1 + " = " + SINGLE_QUOTE + company.getInvoiceAddressRow1() + SINGLE_QUOTE + COMMA);
    sb.append(COMPANY_INVOICEADDRESS_ROW2 + " = " + SINGLE_QUOTE + company.getInvoiceAddressRow2() + SINGLE_QUOTE + COMMA);
    sb.append(COMPANY_INVOICEADDRESS_ROW3 + " = " + SINGLE_QUOTE + company.getInvoiceAddressRow3() + SINGLE_QUOTE);
    sb.append(" WHERE ");
    sb.append(COMPANY_ID_PK + " = " + company.getCompanyId());

    /* Create connection to database */
    DBConnect access = new DBConnect();

    /* Get boolean */
    boolean retval = access.executeUpdate(sb);

    /* Return boolean */
    return retval;
  }

  public boolean updateContacts(Contacts contacts) throws Exception {
    // Create sql query
    StringBuffer sb = new StringBuffer();
    sb.append("UPDATE ");
    sb.append("Contact");
    sb.append(" SET ");
    sb.append("ITR_CompanyId = ");
    sb.append(" " + contacts.getITR_CompanyId() + " , ");
    sb.append("Position = ");
    sb.append(" '" + contacts.getPosition() + "' , ");
    sb.append("FriendLevel = ");
    sb.append(" '" + contacts.getFriendLevel() + "' , ");
    sb.append("Description = ");
    sb.append(" '" + contacts.getDescription() + "' , ");
    sb.append("FirstContact = ");
    sb.append(" '" + contacts.getFirstContact().getCalendarInStoreFormat() + "' ,");
    sb.append("FirstName = ");
    sb.append(" '" + contacts.getFirstName() + "' , ");
    sb.append("LastName = ");
    sb.append(" '" + contacts.getLastName() + "' , ");
    sb.append("KnownByUser_Id = ");
    sb.append(" " + contacts.getKnownByUser_Id());
    sb.append(" WHERE ");
    sb.append(" id = " + contacts.getId());

    DBConnect access = new DBConnect();
    boolean retval = access.executeUpdate(sb);

    return retval;
  }

  public boolean updateEmail(Email email) throws Exception {
    // Create sql query
    StringBuffer sb = new StringBuffer();
    sb.append("UPDATE ");
    sb.append(TABLE_EMAIL);
    sb.append(" SET ");
    sb.append(EMAIL_EMAIL + " = " + SINGLE_QUOTE + email.getAddress() + SINGLE_QUOTE + COMMA);
    sb.append(EMAIL_DESCRIPTION + " = " + SINGLE_QUOTE + email.getDescription() + SINGLE_QUOTE + COMMA);
    sb.append(EMAIL_USERID_FK + " = " + email.getUserId() + COMMA);
    sb.append(EMAIL_CONTACTID + " = " + email.getContactId());
    sb.append(" WHERE ");
    sb.append(EMAIL_ID_PK + " = " + email.getId());

    /* Create connection to database */
    DBConnect access = new DBConnect();

    /* Get boolean */
    boolean retval = access.executeUpdate(sb);

    /* Return boolean */
    return retval;
  }

  public boolean updatePhoneNumber(PhoneNumber phone) throws Exception {
    // Create sql query
    StringBuffer sb = new StringBuffer();
    sb.append("UPDATE ");
    sb.append(TABLE_PHONE);
    sb.append(" SET ");
    sb.append(PHONE_PHONENUMBER + " = " + phone.getPhoneNumber() + COMMA);
    sb.append(PHONE_DESCRIPTION + " = " + SINGLE_QUOTE + phone.getPhoneDescription() + SINGLE_QUOTE + COMMA);
    sb.append(PHONE_USERID_FK + " = " + phone.getPhoneUserId() + COMMA);
    sb.append(PHONE_CONTACTID_FK + " = " + phone.getPhoneContactId());
    sb.append(" WHERE ");
    sb.append(PHONE_ID_PK + " = " + phone.getPhoneId());

    /* Create connection to database */
    DBConnect access = new DBConnect();

    /* Get boolean */
    boolean retval = access.executeUpdate(sb);

    /* Return boolean */
    return retval;
  }

  public boolean updatePhoneRegion(PhoneRegion region) throws Exception {
    // Create sql query
    StringBuffer sb = new StringBuffer();
    sb.append("UPDATE ");
    sb.append(TABLE_PHONEREGIONCODE);
    sb.append(" SET ");
    sb.append(PHONEREGIONCODE_REGIONCODE + " = " + SINGLE_QUOTE + region.getRegionCode() + SINGLE_QUOTE + COMMA);
    sb.append(PHONEREGIONCODE_REGIONNAME + " = " + SINGLE_QUOTE + region.getRegionName() + SINGLE_QUOTE + COMMA);
    sb.append(PHONEREGIONCODE_PHONECOUNTRYCODEID_FK + " = " + region.getCountryId());
    sb.append(" WHERE ");
    sb.append(PHONEREGIONCODE_ID_PK + " = " + region.getRegionId());

    /* Create connection to database */
    DBConnect access = new DBConnect();

    /* Get boolean */
    boolean retval = access.executeUpdate(sb);

    /* Return boolean */
    return retval;
  }

  public boolean updateProject(Project project) throws Exception {
    // Create sql query
    StringBuffer sb = new StringBuffer();
    sb.append("UPDATE ");
    sb.append(TABLE_PROJECT);
    sb.append(" SET ");
    sb.append(PROJECT_ACTIVE + " = " + project.getActivated() + COMMA);
    sb.append(PROJECT_COMPANYID_FK + " = " + project.getCompanyId() + COMMA);
    sb.append(PROJECT_DESCRIPTION + " = " + SINGLE_QUOTE + project.getProjectDesc() + SINGLE_QUOTE + COMMA);
    sb.append(PROJECT_FROMDATE + " = '" + project.getFromDate().getCalendarInStoreFormat() + "'" + COMMA);
    sb.append(PROJECT_MAINCODE + " = " + project.getProjectCode() + COMMA);
    sb.append(PROJECT_NAME + " = " + SINGLE_QUOTE + project.getProjectName() + SINGLE_QUOTE + COMMA);
    sb.append(PROJECT_ADMINPROJECT + " = " + project.getAdminProject() + COMMA);
    sb.append(PROJECT_CONTRACT + " = " + project.getContract() + COMMA);
    sb.append(PROJECT_TECHNIQUE + " = " + SINGLE_QUOTE + project.getTechnique() + SINGLE_QUOTE + COMMA);
    sb.append(PROJECT_TODATE + " = " + "'" + project.getToDate().getCalendarInStoreFormat() + "'");
    sb.append(" WHERE ");
    sb.append(PROJECT_ID_PK + " = " + project.getProjectId());

    /* Create connection to database */
    DBConnect access = new DBConnect();

    /* Get boolean */
    boolean retval = access.executeUpdate(sb);

    /* Return boolean */
    return retval;
  }

  public boolean updateProjectActivity(ProjectActivity projectActivity) throws Exception {
    // Create sql query
    StringBuffer sb = new StringBuffer();
    sb.append("UPDATE ");
    sb.append("ITR_ProjectCodes ");
    sb.append("SET ");
    sb.append("ITR_ProjectId = " + projectActivity.getProjectId() + ", ");
    sb.append("ITR_ProjectCodeId = " + projectActivity.getProjectCodeId() + " ");
    sb.append("WHERE ");
    sb.append("id = " + projectActivity.getId());

    DBConnect access = new DBConnect();
    boolean retval = access.executeUpdate(sb);

    return retval;
  }

  public boolean updateUserWeekComment(int inWeekReportId, int inWeekCommentId) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("UPDATE ITR_UserWeek SET");
    sb.append(" ITR_CommentId = " + inWeekCommentId);
    sb.append(" WHERE ");
    sb.append(" Id = " + inWeekReportId);

    DBConnect access = new DBConnect();
    boolean retval = access.executeUpdate(sb);

    return retval;
  }

  public boolean updateProjectMember(ProjectMember projectMember) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("UPDATE ITR_ProjectMembers SET ");
    sb.append("    ITR_ProjectId = " + projectMember.getITR_ProjectId() + ", ");
    sb.append("    ITR_UserId = " + projectMember.getITR_UserId() + ", ");
    sb.append("    Rate = " + projectMember.getRate() + ", ");
    sb.append("    Active = " + projectMember.getActive() + ", ");
    sb.append("    ProjectAdmin = " + projectMember.getProjectAdmin() + " ");
    sb.append("WHERE ");
    sb.append("    Id = " + projectMember.getId());

    DBConnect access = new DBConnect();
    boolean retval = access.executeUpdate(sb);

    return retval;
  }

  /**
   * Update a row.
   */
  public boolean updateRow(Row row, int weekReportId) throws Exception {
    StringBuffer sb = new StringBuffer();

    /* Create sql query */
    sb.append("UPDATE ");
    sb.append(TABLE_ENTRYROW);
    sb.append(" SET ");
    sb.append(ENTRYROW_MO_HOURS + " = " + row.getMonday() + COMMA);
    sb.append(ENTRYROW_TU_HOURS + " = " + row.getTuesday() + COMMA);
    sb.append(ENTRYROW_WE_HOURS + " = " + row.getWednesday() + COMMA);
    sb.append(ENTRYROW_TH_HOURS + " = " + row.getThursday() + COMMA);
    sb.append(ENTRYROW_FR_HOURS + " = " + row.getFriday() + COMMA);
    sb.append(ENTRYROW_SA_HOURS + " = " + row.getSaturday() + COMMA);
    sb.append(ENTRYROW_SU_HOURS + " = " + row.getSunday() + COMMA);
    sb.append(ENTRYROW_HOURS_SUM + " = " + row.getRowSum() + COMMA);
    sb.append(ENTRYROW_TIMETYPEID_FK + " = " + row.getTimeTypeId() + COMMA);
    sb.append(ENTRYROW_PROJECTCODEID_FK + " = " + row.getProject().getProjectActivityId() + COMMA);
    sb.append(ENTRYROW_PROJECTID_FK + " = " + row.getProject().getProjectId() + COMMA);
    sb.append(ENTRYROW_USERWEEKID_FK + " = " + weekReportId + COMMA);
    sb.append(ENTRYROW_USERID_FK + " = " + row.getUserProfile().getUserId());
    sb.append(" WHERE ");
    sb.append(TABLE_ENTRYROW_DOT + ENTRYROW_ID_PK + " = " + row.getRowEntryId());

    /* Create connection to database */
    DBConnect access = new DBConnect();

    /* Get boolean */
    boolean retval = access.executeUpdate(sb);

    /* Return boolean */
    return retval;
  }

  public boolean updateSubmitInWeek(int userWeekId, boolean status) throws Exception {
    /* Create sql query */
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
    }
    else 
    {
      sb.append("null");
    }

    sb.append(" WHERE ");
    sb.append(USERWEEK_ID_PK).append(" = ").append(userWeekId);

    /* Create connection to database */
    DBConnect access = new DBConnect();

    /* Get boolean */
    boolean retval = access.executeUpdate(sb);

    /* Return boolean */
    return retval;
  }

  /**
   * Update a UserProfile.
   */
  public boolean updateUserProfile(UserProfile profile) throws Exception {
    /* Create sql query */
    StringBuffer sb = new StringBuffer();
    sb.append("UPDATE ");
    sb.append(TABLE_USER);
    sb.append(" SET ");
    sb.append(USER_FIRSTNAME + " = " + SINGLE_QUOTE + profile.getFirstName() + SINGLE_QUOTE + COMMA);
    sb.append(USER_LASTNAME + " = " + SINGLE_QUOTE + profile.getLastName() + SINGLE_QUOTE + COMMA);
    sb.append(USER_LANGUAGEID_FK + " = " + profile.getLanguageId() + COMMA);
    sb.append(USER_LOGINID + " = " + SINGLE_QUOTE + profile.getLoginId() + SINGLE_QUOTE + COMMA);
    sb.append(USER_PASSWORD + " = " + SINGLE_QUOTE + profile.getPassword() + SINGLE_QUOTE + COMMA);
    sb.append(USER_COMPANYID_FK + " = " + profile.getCompanyId() + COMMA);
    sb.append(USER_VACATION_DEFAULT_DAYS + " = " + profile.getDefaultVacationDays() + COMMA);
    sb.append(USER_VACATION_USED_DAYS + " = " + profile.getUsedVacationDays() + COMMA);
    sb.append(USER_VACATION_SAVED_DAYS + " = " + profile.getSavedVacationDays() + COMMA);
    sb.append(USER_OVERTIME_VACATION_HOURS + " = " + profile.getOvertimeVacationHours() + COMMA);
    sb.append(USER_OVERTIME_MONEY_HOURS + " = " + profile.getOvertimeMoneyHours() + COMMA);

    if (profile.getActivated()) {
      sb.append(USER_ACTIVATED + " = " + TRUE_ACCESS + COMMA);
    } else {
      sb.append(USER_ACTIVATED + " = " + FALSE_ACCESS + COMMA);
    }

    sb.append(USER_ACTIVATED_DATE + " = " + "'" + profile.getActivatedDate().getCalendarInStoreFormat() + "'" + COMMA);
    sb.append(USER_DEACTIVATED_DATE + " = " + "'" + profile.getDeActivatedDate().getCalendarInStoreFormat() + "'" + COMMA);
    sb.append(USER_CREATED_DATE + " = " + "'" + profile.getCreatedDate().getCalendarInStoreFormat() + "'" + COMMA);
    sb.append(USER_REPORT_APPROVERID_FK + " = " + profile.getReportApproverId() + COMMA);
    sb.append(USER_SKINID_FK + " = " + profile.getSkinId());
    sb.append(" WHERE ");
    sb.append(USER_ID_PK + " = " + profile.getUserId());

    /* Create connection to database */
    DBConnect access = new DBConnect();

    /* Get boolean */
    boolean retval = access.executeUpdate(sb);

    /* Return boolean */
    return retval;
  }

  public boolean updateUserRoleConnection(Role role) throws Exception {
    // Create sql query
    StringBuffer sb = new StringBuffer();
    sb.append("UPDATE ");
    sb.append(TABLE_USERROLES);
    sb.append(" SET ");
    sb.append(USERROLES_ROLESID_FK + " = " + role.getRoleId());
    sb.append(" WHERE ");
    sb.append(USERROLES_ID_PK + " = " + role.getId());

    /* Create connection to database */
    DBConnect access = new DBConnect();

    /* Get boolean */
    boolean retval = access.executeUpdate(sb);

    /* Return boolean */
    return retval;
  }

  boolean addActivity(Activity activity) throws Exception {
    // Create sql query
    StringBuffer sb = new StringBuffer();
    sb.append("INSERT INTO ");
    sb.append("ITR_ProjectCode");
    sb.append(" ( ");
    sb.append("Description, ");
    sb.append("Code ");
    sb.append(" ) ");
    sb.append(" VALUES ");
    sb.append(" ( ");
    sb.append("'" + activity.getDescription() + "' , ");
    sb.append("'" + activity.getCode() + "' ");
    sb.append(" ) ");

    DBConnect access = new DBConnect();
    boolean retval = access.executeUpdate(sb);

    return retval;
  }

  synchronized boolean addCompany(Company company) throws Exception {
    // Create sql query
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
    sb.append(" (" + SINGLE_QUOTE + company.getName() + SINGLE_QUOTE + COMMA);
    sb.append(" " + SINGLE_QUOTE + company.getVisitAddressRow1() + SINGLE_QUOTE + COMMA);
    sb.append(" " + SINGLE_QUOTE + company.getVisitAddressRow2() + SINGLE_QUOTE + COMMA);
    sb.append(" " + SINGLE_QUOTE + company.getVisitAddressRow3() + SINGLE_QUOTE + COMMA);
    sb.append(" " + SINGLE_QUOTE + company.getInvoiceAddressRow1() + SINGLE_QUOTE + COMMA);
    sb.append(" " + SINGLE_QUOTE + company.getInvoiceAddressRow2() + SINGLE_QUOTE + COMMA);
    sb.append(" " + SINGLE_QUOTE + company.getInvoiceAddressRow3() + SINGLE_QUOTE + ") ");

    /* Create connection to database */
    DBConnect access = new DBConnect();

    /* Get boolean */
    boolean retval = access.executeUpdate(sb);

    /* Return boolean */
    return retval;
  }

  // ************************************************************************************
  // ************************* Handling the CONTACT table ***************************
  // ************************************************************************************
  boolean addContacts(Contacts contacts) throws Exception {
    // Create sql query
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
    sb.append(contacts.getITR_CompanyId() + " , ");
    sb.append("'" + contacts.getPosition() + "' , ");
    sb.append("'" + contacts.getFriendLevel() + "' , ");
    sb.append("'" + contacts.getDescription() + "' , ");
    sb.append("'" + contacts.getFirstContact().getCalendarInStoreFormat() + "' ,");
    sb.append("'" + contacts.getFirstName() + "' , ");
    sb.append("'" + contacts.getLastName() + "' , ");
    sb.append(contacts.getKnownByUser_Id());
    sb.append(" ) ");

    DBConnect access = new DBConnect();
    boolean retval = access.executeUpdate(sb);

    return retval;
  }

  synchronized boolean addEmail(Email email) throws Exception {
    // Create sql query
    StringBuffer sb = new StringBuffer();
    sb.append("INSERT INTO ");
    sb.append(TABLE_EMAIL);
    sb.append(" (" + EMAIL_EMAIL + COMMA);
    sb.append(" " + EMAIL_DESCRIPTION + COMMA);
    sb.append(" " + EMAIL_USERID_FK + COMMA);
    sb.append(" " + EMAIL_CONTACTID + ") ");
    sb.append(" VALUES ");
    sb.append(" (" + SINGLE_QUOTE + email.getAddress() + SINGLE_QUOTE + COMMA);
    sb.append(" " + SINGLE_QUOTE + email.getDescription() + SINGLE_QUOTE + COMMA);
    sb.append(" " + email.getUserId() + COMMA);
    sb.append(" " + email.getContactId() + ") ");

    /* Create connection to database */
    DBConnect access = new DBConnect();

    /* Get boolean */
    boolean retval = access.executeUpdate(sb);

    /* Return boolean */
    return retval;
  }

  synchronized boolean addPhoneNumber(PhoneNumber phone) throws Exception {
    // Create sql query
    StringBuffer sb = new StringBuffer();
    sb.append("INSERT INTO ");
    sb.append(TABLE_PHONE);
    sb.append(" (" + PHONE_PHONENUMBER + COMMA);
    sb.append(" " + PHONE_DESCRIPTION + COMMA);
    sb.append(" " + PHONE_USERID_FK + COMMA);
    sb.append(" " + PHONE_REGIONID_FK + COMMA);
    sb.append(" " + PHONE_CONTACTID_FK + ") ");
    sb.append(" VALUES ");
    sb.append(" (" + phone.getPhoneNumber() + COMMA);
    sb.append(" " + SINGLE_QUOTE + phone.getPhoneDescription() + SINGLE_QUOTE + COMMA);
    sb.append(" " + phone.getPhoneUserId() + COMMA);
    sb.append(" " + phone.getRegion().getRegionId() + COMMA);
    sb.append(" " + phone.getPhoneContactId() + ") ");

    /* Create connection to database */
    DBConnect access = new DBConnect();

    /* Get boolean */
    boolean retval = access.executeUpdate(sb);

    /* Return boolean */
    return retval;
  }

  // ************************************************************************************
  // ******************** Handling the PROJECT ACTIVITIES table *********************
  // ************************************************************************************
  boolean addProjectActivity(ProjectActivity projectActivity) throws Exception {
    // Create sql query
    StringBuffer sb = new StringBuffer();
    sb.append("INSERT INTO ");
    sb.append("ITR_ProjectCodes");
    sb.append(" ( ");
    sb.append("ITR_ProjectId, ");
    sb.append("ITR_ProjectCodeId ");
    sb.append(" ) ");
    sb.append(" VALUES ");
    sb.append(" ( ");
    sb.append(projectActivity.getProjectId() + " , ");
    sb.append(projectActivity.getProjectCodeId() + " ");
    sb.append(" ) ");

    DBConnect access = new DBConnect();
    boolean retval = access.executeUpdate(sb);

    return retval;
  }

  // ************************************************************************************
  // ********************* Handling the PROJECT MEMBERS table ***********************
  // ************************************************************************************
  boolean addProjectMember(ProjectMember projectMember) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("INSERT INTO ITR_ProjectMembers ( ");
    sb.append("    ITR_ProjectId, ");
    sb.append("    ITR_UserId, ");
    sb.append("    Rate, ");
    sb.append("    Active, ");
    sb.append("    ProjectAdmin ");
    sb.append(") VALUES ( ");
    sb.append(projectMember.getITR_ProjectId() + ", ");
    sb.append(projectMember.getITR_UserId() + ", ");
    sb.append(projectMember.getRate() + ", ");
    sb.append(projectMember.getActive() + ", ");
    sb.append(projectMember.getProjectAdmin() + " ");
    sb.append(") ");

    DBConnect access = new DBConnect();
    boolean retval = access.executeUpdate(sb);

    return retval;
  }

  synchronized boolean addUserRoleConnection(Role role) throws Exception {

    // Create sql query
    StringBuffer sb = new StringBuffer();
    sb.append("INSERT INTO ");
    sb.append(TABLE_USERROLES);
    sb.append(" (" + USERROLES_ROLESID_FK + COMMA);
    sb.append(" " + USERROLES_USERID_FK + ") ");
    sb.append(" VALUES ");
    sb.append(" (" + role.getRoleId() + COMMA);
    sb.append(role.getUserId() + ") ");

    /* Create connection to database */
    DBConnect access = new DBConnect();

    /* Get boolean */
    boolean retval = access.executeUpdate(sb);

    /* Return boolean */
    return retval;
  }

  synchronized boolean makeNewComment(String comment) throws Exception {
    /* Create sql query */
    StringBuffer sb = new StringBuffer();
    sb.append("INSERT INTO ");
    sb.append(TABLE_COMMENT);
    sb.append(" (" + COMMENT_COMMENT + ") ");
    sb.append(" VALUES ");
    sb.append("(" + SINGLE_QUOTE + comment + SINGLE_QUOTE + ")");

    /* Create connection to database */
    DBConnect access = new DBConnect();

    /* Get boolean */
    boolean retval = access.executeUpdate(sb);

    /* Return boolean */
    return retval;
  }

  synchronized boolean makeNewProject(Project project) throws Exception {

    // Create sql query
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
    sb.append("    " + project.getCompanyId() + ", \n");
    sb.append("    '" + project.getProjectName() + "', \n");
    sb.append("    '" + project.getProjectDesc() + "', \n");
    sb.append("    '" + project.getTechnique() + "', \n");
    sb.append("    '" + project.getFromDate().getCalendarInStoreFormat() + "', \n");
    sb.append("    '" + project.getToDate().getCalendarInStoreFormat() + "', \n");
    sb.append("    " + project.getContract() + ", \n");
    sb.append("    " + project.getActivated() + ", \n");
    sb.append("    '" + project.getProjectCode() + "', \n");
    sb.append("    " + project.getAdminProject() + " \n");
    sb.append(") \n");

    /* Create connection to database */
    DBConnect access = new DBConnect();

    /* Get boolean */
    boolean retval = access.executeUpdate(sb);

    /* Return boolean */
    return retval;
  }

  /**
   * Make new UserProfile.
   */
  synchronized boolean makeNewUserProfile(UserProfile profile) throws Exception {

    /* Create sql query */
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
    sb.append(SINGLE_QUOTE + profile.getFirstName() + SINGLE_QUOTE + COMMA);
    sb.append(SINGLE_QUOTE + profile.getLastName() + SINGLE_QUOTE + COMMA);
    sb.append(profile.getLanguageId() + COMMA);
    sb.append(SINGLE_QUOTE + profile.getLoginId() + SINGLE_QUOTE + COMMA);
    sb.append(SINGLE_QUOTE + profile.getPassword() + SINGLE_QUOTE + COMMA);
    sb.append(profile.getCompanyId() + COMMA);
    sb.append(profile.getDefaultVacationDays() + COMMA);
    sb.append(profile.getUsedVacationDays() + COMMA);
    sb.append(profile.getSavedVacationDays() + COMMA);
    sb.append(profile.getOvertimeVacationHours() + COMMA);
    sb.append(profile.getOvertimeMoneyHours() + COMMA);
    sb.append(profile.getReportApproverId() + COMMA);

    if (profile.getActivated()) {
      sb.append(TRUE_ACCESS + COMMA);
    } else {
      sb.append(FALSE_ACCESS + COMMA);
    }

    sb.append("'" + profile.getActivatedDate().getCalendarInStoreFormat() + "'" + COMMA);
    sb.append("'" + profile.getDeActivatedDate().getCalendarInStoreFormat() + "'" + COMMA);
    sb.append("'" + profile.getCreatedDate().getCalendarInStoreFormat() + "'" + COMMA);
    sb.append(profile.getSkinId());
    sb.append(" ) ");

    /* Create connection to database */
    DBConnect access = new DBConnect();

    /* Get boolean */
    boolean retval = access.executeUpdate(sb);

    /* Return boolean */
    return retval;
  }

  synchronized boolean makeNewUserWeekId(String calendarWeekId, int weekCommentId) throws Exception {

    /* Create sql query */
    StringBuffer sb = new StringBuffer();
    sb.append("INSERT INTO ");
    sb.append(TABLE_USERWEEK);
    sb.append(" ( ");
    sb.append(USERWEEK_CALENDARWEEK_ID_FK + COMMA);
    sb.append(USERWEEK_COMMENTID_FK);
    sb.append(" ) ");
    sb.append(" VALUES ");
    sb.append(" ( ");
    sb.append(calendarWeekId + COMMA);
    sb.append(weekCommentId);
    sb.append(" ) ");

    /* Create connection to database */
    DBConnect access = new DBConnect();

    /* Get boolean */
    boolean retval = access.executeUpdate(sb);

    /* Return boolean */
    return retval;
  }
}