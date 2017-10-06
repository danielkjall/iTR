package com.intiro.itr.ui.generatereport.monthly;

import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.intiro.itr.ITRResources;
import com.intiro.itr.logic.generatereport.monthly.MonthlyReport;
import com.intiro.itr.ui.ITRServlet;
import com.intiro.itr.ui.Page;
import com.intiro.itr.ui.constants.Commands;
import com.intiro.itr.ui.constants.URLs;
import com.intiro.itr.ui.error.NoSessionException;
import com.intiro.itr.ui.xsl.XSLFormatedArea;
import com.intiro.itr.util.ErrorHandler;
import com.intiro.itr.util.ITRCalendar;
import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.itr.util.log.IntiroLog;

public class MonthlyReportView extends ITRServlet implements URLs, Commands {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): entered doGet");
    }

    PrintWriter out;

    try {
      out = response.getWriter();

      String userId = request.getParameter("userid");

      if (userId == null || userId.equalsIgnoreCase("null")) {
        userId = "";
      }

      String projectId = request.getParameter("projectid");

      if (projectId == null || projectId.equalsIgnoreCase("null")) {
        projectId = "";
      }

      String projectCodeId = request.getParameter("projectcodeid");

      if (projectCodeId == null || projectCodeId.equalsIgnoreCase("null")) {
        projectCodeId = "";
      }

      String year = request.getParameter("year");
      String month = request.getParameter("month");
      String fromDate = "";
      String toDate = "";

      if (month == null || month.equalsIgnoreCase("null")) {
        ITRCalendar fromSelectedDate = new ITRCalendar(year + "-01-01");
        fromDate = fromSelectedDate.getCalendarInStoreFormat();

        ITRCalendar toSelectedDate = new ITRCalendar(year + "-12-01");
        toDate = toSelectedDate.getCalendarInStoreFormat();
      } else {
        ITRCalendar selectedDate = new ITRCalendar(year + "-" + month + "-01");
        fromDate = selectedDate.getCalendarInStoreFormat();
        selectedDate.nextMonth();
        toDate = selectedDate.getCalendarInStoreFormat();
      }

      Page page = new Page(request);
      HttpSession session = request.getSession(false);
      UserProfile userProfile = (UserProfile) session.getAttribute(ITRResources.ITR_USER_PROFILE);
      response.setContentType(userProfile.getClientInfo().getContentType());

      MonthlyReport xmlCarrier = new MonthlyReport(userProfile, userId, projectId, projectCodeId, fromDate, toDate);
      xmlCarrier.load();

      XSLFormatedArea xslProj = new XSLFormatedArea(xmlCarrier, MONTHLY_REPORT_HTML_XSL);
      page.add(xslProj);

      //Display the page
      page.display(out);

      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): Page displayed");
      }
      if (out != null) {
        out.flush();
      }
    } catch (NoSessionException e) {
      if (IntiroLog.i()) {
        IntiroLog.info(getClass(), getClass().getName() + ".Nosession exception caught in " + getClass().getName());
      }

      reAuthenticate(request, response, getServletContext(), "No Session - reauthenticate");

      return;
    } catch (Exception exception) {
      IntiroLog.criticalError(getClass(), getClass().getName() + ".doGet(): An Error occured when trying to display " + getClass().getName(), exception);

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
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".doPost(): Entering doPost");
    }

    doGet(request, response);
  }
}
