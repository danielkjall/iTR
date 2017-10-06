package com.intiro.itr.ui;

import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.intiro.itr.ITRResources;
import com.intiro.itr.logic.XMLLog;
import com.intiro.itr.ui.constants.URLs;
import com.intiro.itr.ui.xsl.XSLFormatedArea;
import com.intiro.itr.util.ErrorHandler;
import com.intiro.itr.util.personalization.ClientInfo;
import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.itr.util.log.IntiroLog;

public class LogView extends ITRServlet implements URLs {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {

    /*Change the loggingLevel*/
    int originalLogLevel = IntiroLog.getInstance().getLoggingLevel();
    IntiroLog.getInstance().setLoggingLevel(1);

    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): Entering");
    }

    PrintWriter out = null;

    try {

      /*Fetching output stream*/
      out = response.getWriter();

      /*Create an output page*/
      Page page = new Page();

      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): Page created");
      }

      /*Fetching session and ClientInfo*/
      HttpSession session = request.getSession(true);

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

      //Get the log as an htmltable
      XMLLog xmlLog = new XMLLog(userProfile);
      XSLFormatedArea xslLogTable = new XSLFormatedArea(xmlLog, LOG_HTML_XSL);

      /*Check to see if logginglevel is given*/
      String loggingLevel = request.getParameter("LoggingLevel");

      if (loggingLevel == null || loggingLevel.equals("")) {
        loggingLevel = "1";
      }

      /*Add a parameter to the XSL page*/
      xslLogTable.addParameter("LoggingLevel", loggingLevel);

      /*Add XSLFormatedArea*/
      page.add(xslLogTable);

      /*Display page*/
      page.display(out);

      if (out != null) {
        out.flush();
      }
    } catch (Exception exception) {
      IntiroLog.criticalError(getClass(), getClass().getName() + ".doGet(): Entering catch Exception");
      /*Create errorHandler*/
      UserProfile userProfile = (UserProfile) request.getSession(false).getAttribute(ITRResources.ITR_USER_PROFILE);
      ErrorHandler errorHandler = null;

      try {
        errorHandler = new ErrorHandler(userProfile);
      } catch (Exception e) {
        System.out.println("message: " + e.getMessage());
        e.printStackTrace();
      }

      errorHandler.setErrorMessage("A problem occured when trying to display LogView page.");
      errorHandler.setException(exception);
      handleError(request, response, getServletContext(), errorHandler);

    } finally {
      IntiroLog.getInstance().setLoggingLevel(originalLogLevel);
    }
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".doPost(): Entering doPost");
    }

    doGet(request, response);
  }
}
