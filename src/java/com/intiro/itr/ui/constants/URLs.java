/**
 * Title:         iTR
 * Description:
 * Copyright:     Copyright (c) 2001
 * Company:       Intiro Development AB
 * @author        Daniel Kjall
 * @version       1.0
 */
package com.intiro.itr.ui.constants;

/**
 * This interface holds every URL in the iTR.
 * The URLs are specified from the Base URL (/ITR/...).
 */
public interface URLs {

  //~ Instance/static variables ........................................................................................

  //Admin
  public static final String ADMIN_APPROVE_WEEKS_HTML_XSL = "content/admin/xsl/HTML_APPROVE_WEEKS.xsl";
  public static final String CHANGE_PASSWORD_HTML_XSL = "content/setting/changepassword/xsl/CHANGE_PASSWORD.xsl";
  public static final String CHANGE_PASSWORD_RESULT_HTML_XSL = "content/setting/changepassword/xsl/CHANGE_PASSWORD_RESULT.xsl";

  //CHANGE PASSWORD
  public static final String CHANGE_PASSWORD_XML = "content/frames/xml/EMPTY.xml";

  //CHANGE PROFILE
  public static final String CHANGE_PROFILE_HTML_XSL = "content/setting/userprofile/xsl/CHANGE_PROFILE.xsl";
  public static final String CHANGE_PROFILE_RESULT_HTML_XSL = "content/setting/userprofile/xsl/CHANGE_PROFILE_RESULT.xsl";
  public static final String CORNER_HTML_XSL = "content/frames/xsl/CORNER_FRAME.xsl";
  public static final String CORNER_XML = "content/frames/xml/EMPTY.xml";
  public static final String ERROR_DEBUG_HTML_XSL = "content/error/xsl/ERRORVIEWER_DEBUG.xsl";

  //Error
  public static final String ERROR_NORMAL_HTML_XSL = "content/error/xsl/ERRORVIEWER_NORMAL.xsl";

  //FRAMES XML
  public static final String FRAMESET_XML = "content/frames/xml/EMPTY.xml";
  public static final String FRAMESET_XSL = "content/frames/xsl/FRAMESET.xsl";
  public static final String GENERAL_QUERY_PROFILE_HTML_XSL = "content/generatereport/general/xsl/HTML_GENERAL_QUERY_PROFILE.xsl";
  public static final String GENERAL_REPORT_HTML_XSL = "content/generatereport/general/xsl/HTML_GENERAL_REPORT.xsl";

  //XSL used for the online help.
  public static final String HELP_HTML_XSL = "content/help/xsl/help.xsl";

  //HELP
  public static final String HELP_XML = "content/help/xml/help.xml";

  //These URLs all define scripts that can be added to a page object
  public static final String JAVASCRIPT_AUTHORITY = "authority.inc";
  public static final String JAVASCRIPT_CLOSEWIN = "closeWin.inc";

  //Location of Javascript includes
  public static final String JAVASCRIPT_DIR = "helpers/";
  public static final String JAVASCRIPT_FRAME = "frame.inc";
  public static final String JAVASCRIPT_GLOBAL = "global.inc";
  public static final String JAVASCRIPT_NAVIGATION = "navigation.inc";
  public static final String JAVASCRIPT_VALIDATE = "validate.inc";
  public static final String JAVASCRIPT_VALIDATEDATE = "validatedate.inc";

  //Generate reports
  public static final String LIST_REPORTS_HTML_XSL = "content/generatereport/xsl/HTML_LIST_REPORTS.xsl";
  public static final String LIST_REPORTS_XML = "content/generatereport/xml/LIST_REPORTS.xml";
  public static final String LOGIN_HTML_XSL = "content/login/xsl/HTML_Login.xsl";

  //LOGIN
  public static final String LOGIN_XML = "content/login/xml/Login.xml";
  public static final String LOGOUT_HTML_XSL = "content/logout/xsl/HTML_Logout.xsl";

  //LOGOUT
  public static final String LOGOUT_XML = "content/frames/xml/EMPTY.xml";

  //XSL used to display the log file in HTML.
  public static final String LOG_HTML_XSL = "content/log/xsl/HTML_Log.xsl";
  public static final String MAINFRAME_HTML_XSL = "content/frames/xsl/MAIN_FRAME.xsl";
  public static final String MAIN_XML = "content/frames/xml/EMPTY.xml";
  public static final String MONTHLY_QUERY_PROFILE_HTML_XSL = "content/generatereport/monthly/xsl/HTML_MONTHLY_QUERY_PROFILE.xsl";
  public static final String MONTHLY_REPORT_HTML_XSL = "content/generatereport/monthly/xsl/HTML_MONTHLY_REPORT.xsl";

  //FRAMES
  //FRAMES XSL
  public static final String NAVIGATION_HTML_XSL = "content/frames/xsl/NAVIGATION_FRAME.xsl";
  public static final String NAVIGATION_XML = "content/frames/xml/EMPTY.xml";

  //Edit weekreport
  public static final String REPORT_EDIT_WEEK_HTML_XSL = "content/weekreport/xsl/HTML_EDIT_WEEK.xsl";
  public static final String REPORT_EDIT_WEEK_XML = "content/weekreport/xml/EDIT_WEEK.xml";
  public static final String REPORT_VIEW_APPROVE_WEEK_HTML_XSL = "content/weekreport/xsl/HTML_VIEW_APPROVE_WEEK.xsl";

  //View weeks
  public static final String REPORT_VIEW_WEEKS_HTML_XSL = "content/weekreport/xsl/HTML_VIEW_WEEKS.xsl";
  public static final String REPORT_VIEW_WEEKS_XML = "content/weekreport/xml/VIEW_WEEKS.xml";
  public static final String RESULT_SAVE_SUBMIT_WEEK_XSL = "content/weekreport/xsl/SAVE_SUBMIT_WEEKREPORT_RESULT.xsl";

  //Select Projects
  public static final String SELECT_PROJECT_HTML_XSL = "content/weekreport/xsl/HTML_SELECT_PROJECT.xsl";
  public static final String SELECT_PROJECT_XML = "content/weekreport/xml/SELECT_PROJECTS.xml";

  //Select sub code Projects
  public static final String SELECT_SUB_CODE_PROJECT_HTML_XSL = "content/weekreport/xsl/HTML_SELECT_SUB_CODE_PROJECT.xsl";
  public static final String SELECT_SUB_CODE_PROJECT_XML = "content/weekreport/xml/SELECT_SUB_CODE_PROJECTS.xml";
  public static final String SUPERADMIN_ACTIVITY_EDITOR_HTML_XSL = "content/superadmin/activity/xsl/CHANGE_ACTIVITY.xsl";

  //ACTIVITY
  public static final String SUPERADMIN_ACTIVITY_QUERY_HTML_XSL = "content/superadmin/activity/xsl/ACTIVITY_QUERY.xsl";
  public static final String SUPERADMIN_COMPANIES_EDITOR_HTML_XSL = "content/superadmin/companies/xsl/CHANGE_COMPANIES.xsl";

  //Companies
  public static final String SUPERADMIN_COMPANIES_QUERY_HTML_XSL = "content/superadmin/companies/xsl/COMPANIES_QUERY.xsl";

  //CONTACT
  public static final String SUPERADMIN_CONTACT_CHANGE_HTML_XSL = "content/superadmin/contacts/xsl/ADMIN_CHANGE_CONTACT.xsl";
  public static final String SUPERADMIN_CONTACT_QUERY_HTML_XSL = "content/superadmin/contacts/xsl/CONTACT_QUERY.xsl";

  //PHONE
  public static final String SUPERADMIN_PHONES_EDITOR_XML = "content/superadmin/users/xml/userPhone.xml";
  public static final String SUPERADMIN_PROJECTMEMBERS_EDITOR_HTML_XSL = "content/superadmin/projectmember/xsl/PROJECTMEMBERS_EDITOR.xsl";

  //PROJECTMEMBERS
  public static final String SUPERADMIN_PROJECTMEMBERS_QUERY_HTML_XSL = "content/superadmin/projectmember/xsl/PROJECTMEMBERS_QUERY.xsl";
  public static final String SUPERADMIN_PROJECTS_ACTIVITIES_EDITOR_HTML_XSL = "content/superadmin/projects/xsl/CHANGE_PROJECTS_ACTIVITIES.xsl";
  public static final String SUPERADMIN_PROJECTS_ACTIVITIES_QUERY_HTML_XSL = "content/superadmin/projects/xsl/PROJECTS_ACTIVITIES_QUERY.xsl";
  public static final String SUPERADMIN_PROJECTS_EDITOR_HTML_XSL = "content/superadmin/projects/xsl/CHANGE_PROJECTS.xsl";

  //PROJECTS
  public static final String SUPERADMIN_PROJECTS_QUERY_HTML_XSL = "content/superadmin/projects/xsl/PROJECTS_QUERY.xsl";

  //SuperAdmin
  //USER
  public static final String SUPERADMIN_USERS_CHANGE_HTML_XSL = "content/superadmin/users/xsl/ADMIN_CHANGE_USERS.xsl";
  public static final String SUPERADMIN_USERS_CHANGE_XML = "content/superadmin/users/xml/modifyUser.xml";

  //EMAIL
  public static final String SUPERADMIN_USERS_EMAIL_EDITOR_HTML_XSL = "content/superadmin/users/xsl/EMAILS_EDITOR.xsl";
  public static final String SUPERADMIN_USERS_EMAIL_EDITOR_XML = "content/superadmin/users/xml/userEmail.xml";
  public static final String SUPERADMIN_USERS_EMAIL_QUERY_HTML_XSL = "content/superadmin/users/xsl/EMAILS_QUERY.xsl";
  public static final String SUPERADMIN_USERS_EMAIL_SAVE_HTML_XSL = "content/superadmin/users/xsl/EMAILS_SAVE.xsl";
  public static final String SUPERADMIN_USERS_PHONE_EDITOR_HTML_XSL = "content/superadmin/users/xsl/PHONES_EDITOR.xsl";
  public static final String SUPERADMIN_USERS_PHONE_QUERY_HTML_XSL = "content/superadmin/users/xsl/PHONES_QUERY.xsl";
  public static final String SUPERADMIN_USERS_PHONE_SAVE_HTML_XSL = "content/superadmin/users/xsl/PHONES_SAVE.xsl";
  public static final String SUPERADMIN_USERS_QUERY_HTML_XSL = "content/superadmin/users/xsl/USERS_QUERY.xsl";
  public static final String SUPERADMIN_USERS_QUERY_XML = "content/superadmin/users/xml/queryUser.xml";
  public static final String TOP_HTML_XSL = "content/frames/xsl/TOP_FRAME.xsl";
  public static final String TOP_XML = "content/frames/xml/EMPTY.xml";
  public static final String VACATION_QUERY_PROFILE_HTML_XSL = "content/generatereport/vacation/xsl/HTML_VACATION_QUERY_PROFILE.xsl";
  public static final String VACATION_REPORT_HTML_XSL = "content/generatereport/vacation/xsl/HTML_VACATION_REPORT.xsl";
}