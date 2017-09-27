/**
 * Title:         ITR
 * Description:
 * Copyright:     Copyright (c) 2001
 * Company:       Intiro Development AB
 * @author        Daniel Kjall
 * @version       1.0
 */
package com.intiro.itr.ui.generatereport.vacation;

import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.intiro.itr.ITRResources;
import com.intiro.itr.logic.generatereport.vacation.VacationReport;
import com.intiro.itr.ui.ITRServlet;
import com.intiro.itr.ui.Page;
import com.intiro.itr.ui.constants.Commands;
import com.intiro.itr.ui.constants.URLs;
import com.intiro.itr.ui.error.NoSessionException;
import com.intiro.itr.ui.xsl.XSLFormatedArea;
import com.intiro.itr.util.ErrorHandler;
import com.intiro.itr.util.ITRCalendar;
import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.toolbox.log.IntiroLog;

public class VacationReportView extends ITRServlet implements URLs, Commands {

  //~ Methods ..........................................................................................................

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): entered doGet");
    }

    PrintWriter out = null;

    try {
      out = response.getWriter();

      String userId = request.getParameter(Commands.XSLFIELD_USERID);

      if (userId == null || userId.equalsIgnoreCase("null")) {
        userId = null;
      }

      String projectId = request.getParameter(Commands.XSLFIELD_PROJECTID);

      if (projectId == null || projectId.equalsIgnoreCase("null")) {
        projectId = null;
      }

      String companyId = request.getParameter(Commands.XSLFIELD_COMPANYID);

      if (companyId == null || companyId.equalsIgnoreCase("null")) {
        companyId = null;
      }

      String year = request.getParameter(Commands.XSLFIELD_YEAR);

      if (year == null || year.equalsIgnoreCase("null") || year.equalsIgnoreCase("")) {
        year = String.valueOf(new ITRCalendar().getYear());
      }

      /*Create an output page*/
      Page page = new Page(request);

      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): Page created");
      }

      HttpSession session = request.getSession(false);
      UserProfile userProfile = (UserProfile) session.getAttribute(ITRResources.ITR_USER_PROFILE);
      response.setContentType(userProfile.getClientInfo().getContentType());

      VacationReport xmlCarrier = new VacationReport(userProfile, userId, projectId, companyId, year);
      xmlCarrier.load();

      XSLFormatedArea xslReport = new XSLFormatedArea(xmlCarrier, VACATION_REPORT_HTML_XSL);
      page.add(xslReport);

      //Display the page
      page.display(out);

      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): Page displayed");
      }
      if (out != null) {
        out.flush();
      }
    } catch (NoSessionException e) {
      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".Nosession exception caught in " + getClass().getName());
      }

      reAuthenticate(request, response, getServletContext(), "No Session - reauthenticate");

      return;
    } catch (Exception exception) {
      if (IntiroLog.ce()) {
        IntiroLog.criticalError(getClass(), getClass().getName() + ".doGet(): An Error occured when trying to display " + getClass().getName(), exception);
      }

      UserProfile userProfile = (UserProfile) request.getSession(false).getAttribute(ITRResources.ITR_USER_PROFILE);
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
    return;
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".doPost(): Entering doPost");
    }

    doGet(request, response);
  }
}