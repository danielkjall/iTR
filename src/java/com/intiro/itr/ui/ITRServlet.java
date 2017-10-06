package com.intiro.itr.ui;

import java.util.StringTokenizer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.intiro.itr.ITRResources;
import com.intiro.itr.util.ErrorHandler;
import com.intiro.itr.util.log.IntiroLog;

public class ITRServlet extends HttpServlet {

  protected static final String FORWARD_CHANGEPASSWORDACTI = "ChangePasswordActivator";
  protected static final String FORWARD_CHANGEPASSWORDVIEW = "ChangePasswordView";
  protected static final String FORWARD_CHANGEPROFILE = "ChangeProfileView";
  protected static final String FORWARD_CORNERFRAME = "CornerFrame";
  protected static final String FORWARD_ERRORVIEWER = "ErrorViewer";
  protected static final String FORWARD_LOGINVERIFIER = "LoginVerifier";
  protected static final String FORWARD_LOGINVIEW = "LoginView";
  protected static final String FORWARD_LOGVIEW = "LogView";
  protected static final String FORWARD_MAINFRAME = "MainFrame";
  protected static final String FORWARD_NAVIGATIONFRAME = "NavigationFrame";
  protected static final String FORWARD_TIMEPORTAL = "TimePortal";
  protected static final String FORWARD_TOPFRAME = "TopFrame";
  protected static final String REDIRECT_ACTIVITY_QUERY = "ActivityQueryView";
  protected static final String REDIRECT_APPROVE_WEEKREPORTS = "ApproveWeeksView";
  protected static final String REDIRECT_CHANGEPASSWORDACTI = "ChangePasswordActivator";
  protected static final String REDIRECT_CHANGEPASSWORDVIEW = "ChangePasswordView";
  protected static final String REDIRECT_CHANGEPROFILE = "ChangeProfileView";
  protected static final String REDIRECT_COMPANY_QUERY = "CompanyQueryView";
  protected static final String REDIRECT_CONTACT_QUERY = "ContactQueryView";
  protected static final String REDIRECT_CORNERFRAME = "CornerFrame";
  protected static final String REDIRECT_ERRORVIEWER = "ErrorViewer";
  protected static final String REDIRECT_LOGINVERIFIER = "LoginVerifier";
  protected static final String REDIRECT_LOGINVIEW = "LoginView";
  protected static final String REDIRECT_LOGVIEW = "LogView";
  protected static final String REDIRECT_MAINFRAME = "MainFrame";
  protected static final String REDIRECT_NAVIGATIONFRAME = "NavigationFrame";
  protected static final String REDIRECT_PROJACTIVITY_QUERY = "ProjectActivitiesQueryView";
  protected static final String REDIRECT_PROJECTMEMBER_QUERY = "ProjectMembersQueryView";
  protected static final String REDIRECT_PROJECT_QUERY = "ProjectQueryView";
  protected static final String REDIRECT_TIMEPORTAL = "TimePortal";
  protected static final String REDIRECT_TOPFRAME = "TopFrame";
  protected static final String REDIRECT_USERS_EMAILS_QUERY = "EmailQueryView";
  protected static final String REDIRECT_USERS_PHONES_QUERY = "PhoneQueryView";
  protected static final String REDIRECT_USER_QUERY = "UserQueryView";
  protected static final String REDIRECT_WEEKREPORTS_SUBMITTED = "viewWeeks?mode=submitted";
  protected static final String REDIRECT_WEEKREPORTS_TODO = "viewWeeks?mode=todo";

  @Override
  public void doGet(HttpServletRequest inRequest, HttpServletResponse inResponse) throws ServletException {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): Entering");
    }
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".doPost(): Entering");
    }

    doGet(request, response);
  }

  protected synchronized void handleApprove(HttpServletRequest inRequest, HttpServletResponse response, ServletContext inContext) {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".handleApprove(): Entering");
    }
    try {

      // Redirect admin to REDIRECT_APPROVE_WEEKREPORTS
      //response.sendRedirect(ITRResources.getDefaultProtocolForITR() + ITRResources.getDefaultWebITRRootDir() + REDIRECT_APPROVE_WEEKREPORTS);
      response.sendRedirect(REDIRECT_APPROVE_WEEKREPORTS);
    } catch (Exception e) {
      IntiroLog.criticalError(getClass(), getClass().getName() + ".handleApprove(): Couldn't redirect to servlet = " + REDIRECT_APPROVE_WEEKREPORTS, e);
    }
  }

  protected void handleError(HttpServletRequest request, HttpServletResponse response, ServletContext inContext, ErrorHandler errorHandler) {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".handleError(): Entering");
    }
    try {

      /* Get session */
      HttpSession session = request.getSession(false);

      if (session == null) {
        request.getSession(true);
      }
      /* Set errorhandler */
      if (errorHandler != null) {
        session.setAttribute(ITRResources.ERROR_HANDLER, errorHandler);
      }
      /* Redirect to error viewer */
      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".handleError(): Redirecting to" + ITRResources.getDefaultProtocolForITR() + ITRResources.getDefaultWebITRRootDir()
                + REDIRECT_ERRORVIEWER);
      }

      //response.sendRedirect(ITRResources.getDefaultProtocolForITR() + ITRResources.getDefaultWebITRRootDir() + REDIRECT_ERRORVIEWER);
      response.sendRedirect(REDIRECT_ERRORVIEWER);
    } catch (Exception e) {
      IntiroLog.criticalError(getClass(), getClass().getName() + ".handleError(): Couldn't forward to servlet = " + FORWARD_ERRORVIEWER, e);
    }
  }

  protected synchronized void handleModificationOfCompany(HttpServletRequest inRequest, HttpServletResponse response, ServletContext inContext) {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".handleSaveOfCompany(): Entering");
    }
    try {

      // Redirect to REDIRECT_COMPANY_QUERY.
      //response.sendRedirect(ITRResources.getDefaultProtocolForITR() + ITRResources.getDefaultWebITRRootDir() + REDIRECT_COMPANY_QUERY);
      response.sendRedirect(REDIRECT_COMPANY_QUERY);
    } catch (Exception e) {
      IntiroLog.criticalError(getClass(), getClass().getName() + ".handleSaveOfCompany(): Couldn't redirect to servlet = " + REDIRECT_COMPANY_QUERY, e);
    }
  }

  protected synchronized void handleReject(HttpServletRequest inRequest, HttpServletResponse response, ServletContext inContext) {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".handleReject(): Entering");
    }
    try {

      // Redirect admin to REDIRECT_APPROVE_WEEKREPORTS
      //response.sendRedirect(ITRResources.getDefaultProtocolForITR() + ITRResources.getDefaultWebITRRootDir() + REDIRECT_APPROVE_WEEKREPORTS);
      response.sendRedirect(REDIRECT_APPROVE_WEEKREPORTS);
    } catch (Exception e) {
      IntiroLog.criticalError(getClass(), getClass().getName() + ".handleReject(): Couldn't redirect to servlet = " + REDIRECT_APPROVE_WEEKREPORTS, e);
    }
  }

  protected synchronized void handleSave(HttpServletRequest inRequest, HttpServletResponse response, ServletContext inContext) {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".handleSave(): Entering");
    }
    try {

      // Forward to REDIRECT_WEEKREPORTS_TODO.
      //response.sendRedirect(ITRResources.getDefaultProtocolForITR() + ITRResources.getDefaultWebITRRootDir() + REDIRECT_WEEKREPORTS_TODO);
      response.sendRedirect(REDIRECT_WEEKREPORTS_TODO);
    } catch (Exception e) {
      IntiroLog.criticalError(getClass(), getClass().getName() + ".handleSave(): Couldn't redirect to servlet = " + REDIRECT_WEEKREPORTS_TODO, e);
    }
  }

  protected synchronized void handleSaveOfActivity(HttpServletRequest inRequest, HttpServletResponse response, ServletContext inContext) {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".handleSaveOfActivity(): Entering");
    }
    try {

      // Redirect to REDIRECT_ACTIVITY_QUERY.
      //response.sendRedirect(ITRResources.getDefaultProtocolForITR() + ITRResources.getDefaultWebITRRootDir() + REDIRECT_ACTIVITY_QUERY);
      response.sendRedirect(REDIRECT_ACTIVITY_QUERY);
    } catch (Exception e) {
      IntiroLog.criticalError(getClass(), getClass().getName() + ".handleSaveOfActivity(): Couldn't redirect to servlet = " + REDIRECT_ACTIVITY_QUERY, e);
    }
  }

  protected synchronized void handleSaveOfContact(HttpServletRequest inRequest, HttpServletResponse response, ServletContext inContext) {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".handleSaveOfContact(): Entering");
    }
    try {

      // Redirect to REDIRECT_CONTACT_QUERY.
      //response.sendRedirect(ITRResources.getDefaultProtocolForITR() + ITRResources.getDefaultWebITRRootDir() + REDIRECT_CONTACT_QUERY);
      response.sendRedirect(REDIRECT_CONTACT_QUERY);
    } catch (Exception e) {
      IntiroLog.criticalError(getClass(), getClass().getName() + ".handleSaveOfUser(): Couldn't redirect to servlet = " + REDIRECT_CONTACT_QUERY, e);
    }
  }

  protected synchronized void handleSaveOfProject(HttpServletRequest inRequest, HttpServletResponse response, ServletContext inContext) {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".handleSaveOfProject(): Entering");
    }
    try {

      // Redirect to REDIRECT_PROJECT_QUERY.
      //response.sendRedirect(ITRResources.getDefaultProtocolForITR() + ITRResources.getDefaultWebITRRootDir() + REDIRECT_PROJECT_QUERY);
      response.sendRedirect(REDIRECT_PROJECT_QUERY);
    } catch (Exception e) {
      IntiroLog.criticalError(getClass(), getClass().getName() + ".handleSaveOfProject(): Couldn't redirect to servlet = " + REDIRECT_PROJECT_QUERY, e);
    }
  }

  protected synchronized void handleSaveOfProjectActivities(HttpServletRequest inRequest, HttpServletResponse response, ServletContext inContext) {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".handleSaveOfProjectActivities(): Entering");
    }
    try {

      // Redirect to REDIRECT_PROJACTIVITY_QUERY.
      //response.sendRedirect(ITRResources.getDefaultProtocolForITR() + ITRResources.getDefaultWebITRRootDir() + REDIRECT_PROJACTIVITY_QUERY);
      response.sendRedirect(REDIRECT_PROJACTIVITY_QUERY);
    } catch (Exception e) {
      IntiroLog.criticalError(getClass(), getClass().getName() + ".handleSaveOfProjectActivities(): Couldn't redirect to servlet = " + REDIRECT_PROJACTIVITY_QUERY, e);
    }
  }

  protected synchronized void handleSaveOfProjectMembers(HttpServletRequest inRequest, HttpServletResponse response, ServletContext inContext) {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".handleSaveOfProjectMembers(): Entering");
    }
    try {

      // Redirect to REDIRECT_PROJECTMEMBER_QUERY.
      //response.sendRedirect(ITRResources.getDefaultProtocolForITR() + ITRResources.getDefaultWebITRRootDir() + REDIRECT_PROJECTMEMBER_QUERY);
      response.sendRedirect(REDIRECT_PROJECTMEMBER_QUERY);
    } catch (Exception e) {
      IntiroLog.criticalError(getClass(), getClass().getName() + ".handleSaveOfProjectActivities(): Couldn't redirect to servlet = " + REDIRECT_PROJECTMEMBER_QUERY, e);
    }
  }

  protected synchronized void handleSaveOfUser(HttpServletRequest inRequest, HttpServletResponse response, ServletContext inContext) {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".handleSaveOfUser(): Entering");
    }
    try {

      // Redirect to REDIRECT_USER_QUERY.
      //response.sendRedirect(ITRResources.getDefaultProtocolForITR() + ITRResources.getDefaultWebITRRootDir() + REDIRECT_USER_QUERY);
      response.sendRedirect(REDIRECT_USER_QUERY);
    } catch (Exception e) {
      IntiroLog.criticalError(getClass(), getClass().getName() + ".handleSaveOfUser(): Couldn't redirect to servlet = " + REDIRECT_USER_QUERY, e);
    }
  }

  protected synchronized void handleSaveOfUserEmails(HttpServletRequest inRequest, HttpServletResponse response, ServletContext inContext, String mode) {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".handleSaveOfUserEmails(): Entering");
    }
    try {

      // Redirect to REDIRECT_USERS_EMAILS_QUERY.
      //response.sendRedirect(ITRResources.getDefaultProtocolForITR() + ITRResources.getDefaultWebITRRootDir() + REDIRECT_USERS_EMAILS_QUERY + "?action=" + mode);
      response.sendRedirect(REDIRECT_USERS_EMAILS_QUERY + "?action=" + mode);
    } catch (Exception e) {
      IntiroLog.criticalError(getClass(), getClass().getName() + ".handleSaveOfUserEmails(): Couldn't redirect to servlet = " + REDIRECT_USERS_EMAILS_QUERY, e);
    }
  }

  protected synchronized void handleSaveOfUserPhoneNumbers(HttpServletRequest inRequest, HttpServletResponse response, ServletContext inContext, String mode) {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".handleSaveOfUserPhoneNumbers(): Entering");
    }
    try {

      // Redirect to REDIRECT_USER_QUERY.
      //response.sendRedirect(ITRResources.getDefaultProtocolForITR() + ITRResources.getDefaultWebITRRootDir() + REDIRECT_USERS_PHONES_QUERY + "?action=" + mode);
      response.sendRedirect(REDIRECT_USERS_PHONES_QUERY + "?action=" + mode);
    } catch (Exception e) {
      IntiroLog.criticalError(getClass(), getClass().getName() + ".handleSaveOfUserPhoneNumbers(): Couldn't redirect to servlet = " + REDIRECT_USERS_PHONES_QUERY, e);
    }
  }

  protected synchronized void handleSubmit(HttpServletRequest inRequest, HttpServletResponse response, ServletContext inContext) {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".handleSubmit(): Entering");
    }
    try {

      // Forward to REDIRECT_WEEKREPORTS_SUBMITTED.
      //response.sendRedirect(ITRResources.getDefaultProtocolForITR() + ITRResources.getDefaultWebITRRootDir() + REDIRECT_WEEKREPORTS_SUBMITTED);
      response.sendRedirect(REDIRECT_WEEKREPORTS_SUBMITTED);
    } catch (Exception e) {
      IntiroLog.criticalError(getClass(), getClass().getName() + ".handleSubmit(): Couldn't redirect to servlet = " + REDIRECT_WEEKREPORTS_SUBMITTED, e);
    }
  }

  protected String getRelativeURLRoot(HttpServletRequest request) {
    StringBuffer retVal = new StringBuffer("");
    StringTokenizer tokens = new StringTokenizer(request.getRequestURI(), "/");
    for (int i = 4; i < tokens.countTokens(); i++) {
      retVal.append("../");
    }
    return retVal.toString();
  }

  protected synchronized void reAuthenticate(HttpServletRequest request, HttpServletResponse response, ServletContext inContext, String message) {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".reAuthenticate(): Entering");
    }
    try {

      // Forward to LoginView.
      HttpSession session = request.getSession(true);
      session.setAttribute(ITRResources.LOGIN_MESSAGE, message);

      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".reAuthenticate(): Redirecting to /content/login/login.jsp");
      }

      StringBuffer redirectURLPath = new StringBuffer(getRelativeURLRoot(request));
      redirectURLPath.append("login.jsp");
      response.sendRedirect(redirectURLPath.toString());

    } catch (Exception e) {
      IntiroLog.criticalError(getClass(), getClass().getName() + ".reAuthenticate(): Couldn't redirect to servlet = /login/login.jsp", e);
    }
  }
}
