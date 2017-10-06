package com.intiro.itr.ui.login;

import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.intiro.itr.ITRResources;
import com.intiro.itr.ui.ITRServlet;
import com.intiro.itr.ui.Page;
import com.intiro.itr.ui.constants.URLs;
import com.intiro.itr.ui.xsl.XSLFormatedArea;
import com.intiro.itr.util.ErrorHandler;
import com.intiro.itr.util.personalization.ClientInfo;
import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.itr.util.xml.StaticXMLCarrier;
import com.intiro.itr.util.log.IntiroLog;

/**
 * Shows the login dialog.
 */
public class LoginView extends ITRServlet implements URLs {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): Entering");
    }
    /*   IntiroLog.getInstance().setLoggingLevel(6);
     ITRCalendar now = new ITRCalendar();
     try{
     now.loadCalendarWithDate("2000-12-10");
     }catch(Exception e){}
     IntiroLog.getInstance().setLoggingLevel(1);
     */
 /*Setting ITRResources.getWebitrRootDir() if not set through itr.properties file.*/
 /*WebitrRootDir is set when the first visitor comes to the itr.*/
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): ITRResources.getDefaultWebitrRootDir() = " + ITRResources.getDefaultWebITRRootDir());
    }
    if (ITRResources.getDefaultWebITRRootDir() == null || ITRResources.getDefaultWebITRRootDir().length() == 0) {
      StringBuffer sb = new StringBuffer();
      sb.append(request.getHeader("host"));
      sb.append(request.getContextPath());
      sb.append("/");
      ITRResources.setDefaultWebITRRootDir(sb.toString());

      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): Setting ITRResources.getRealitrRootDir() to " + sb.toString());
      }
    }

    /*Creating HttpSession*/
    HttpSession session = request.getSession(false);

    if (session == null) {
      session = request.getSession(true);
    }

    PrintWriter out = null;

    try {

      /*Fetching output stream*/
      out = response.getWriter();

      /*Create an output page*/
      Page page = new Page(request);

      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): Page created");
      }

      /*Creating ClientInfo and storing it in Session*/
      UserProfile userProfile = null;

      if ((userProfile = (UserProfile) session.getAttribute(ITRResources.ITR_USER_PROFILE)) == null) {
        userProfile = new UserProfile();

        ClientInfo clientInfo = new ClientInfo();
        clientInfo.init(request, response);
        userProfile.setClientInfo(clientInfo);
        session.setAttribute(ITRResources.ITR_USER_PROFILE, userProfile);
      }

      /*Setting contentType*/
      response.setContentType(userProfile.getClientInfo().getContentType());

      /*Creating XSLFormatedArea*/
      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): Creating XSLFormatedArea");
      }

      StaticXMLCarrier xmlCarrier = new StaticXMLCarrier(LOGIN_XML, userProfile);

      /*Create XSLFormatedArea*/
      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): Creating XSLFormatedArea");
      }

      XSLFormatedArea xslArea = new XSLFormatedArea(xmlCarrier, LOGIN_HTML_XSL);

      /*Try to retrieve login message from session or request*/
      if ((String) request.getAttribute(ITRResources.LOGIN_MESSAGE) != null) {
        xslArea.addParameter("loginMessage", "'" + (String) request.getAttribute(ITRResources.LOGIN_MESSAGE) + "'");
      } else if (session.getAttribute(ITRResources.LOGIN_MESSAGE) != null) {
        xslArea.addParameter("loginMessage", "'" + session.getAttribute(ITRResources.LOGIN_MESSAGE) + "'");
      }

      /*Add XSLFormatedArea to page*/
      page.add(xslArea);

      /*Display page*/
      page.display(out);

      if (out != null) {
        out.flush();
      }
      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): OutputStream is flushed");
      }
    } catch (Exception exception) {
      IntiroLog.criticalError(getClass(), getClass().getName() + ".doGet(): Entering catch Exception: " + exception.getMessage());

      /*Create errorHandler*/
      UserProfile userProfile = (UserProfile) session.getAttribute(ITRResources.ITR_USER_PROFILE);
      ErrorHandler errorHandler = null;

      try {
        errorHandler = new ErrorHandler(userProfile);
      } catch (Exception e) {
        System.out.println("message: " + e.getMessage());
        e.printStackTrace();
      }

      errorHandler.setErrorMessage("A problem occured when trying to display the " + getClass().getName() + " page.");
      errorHandler.setException(exception);
      handleError(request, response, getServletContext(), errorHandler);

    }
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
    doGet(request, response);
  }
}
