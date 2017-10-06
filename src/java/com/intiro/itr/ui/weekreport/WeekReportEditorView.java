package com.intiro.itr.ui.weekreport;

import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.intiro.itr.ITRResources;
import com.intiro.itr.logic.weekreport.Row;
import com.intiro.itr.logic.weekreport.WeekReport;
import com.intiro.itr.ui.ITRServlet;
import com.intiro.itr.ui.Page;
import com.intiro.itr.ui.constants.Commands;
import com.intiro.itr.ui.constants.URLs;
import com.intiro.itr.ui.error.NoSessionException;
import com.intiro.itr.ui.xsl.XSLFormatedArea;
import com.intiro.itr.util.ErrorHandler;
import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.itr.util.log.IntiroLog;

/**
 * This servlet creates the week report to look at or to edit.
 */
public class WeekReportEditorView extends ITRServlet implements URLs, Commands {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): entered doGet");
    }

    PrintWriter out = null;
    String monHours = "0";
    String tueHours = "0";
    String wedHours = "0";
    String thuHours = "0";
    String friHours = "0";
    String satHours = "0";
    String sunHours = "0";
    String timeTypeId = "0";
    String projSubId = "0";
    //String projSubCode = "0";
    String row = "0";
    boolean copyFromSubmitted = false;
    String mode = request.getParameter("mode");
    String weekComment = "";
    ArrayList weekReports;

    try {
      out = response.getWriter();

      /*Create an output page*/
      Page page = new Page(request);

      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): Page created");
      }

      HttpSession session = request.getSession(false);
      UserProfile userProfile = (UserProfile) session.getAttribute(ITRResources.ITR_USER_PROFILE);
      response.setContentType(userProfile.getClientInfo().getContentType());

      /*Get WeekReport from session*/
      WeekReport xmlCarrier = (WeekReport) session.getAttribute(ITRResources.ITR_WEEK_REPORT);

      /*START ACTION, Find out what action is called and perform it*/
      String action = request.getParameter("action");

      /*START THE WEEKREPORT*/
      if (action != null && action.equalsIgnoreCase("start")) {

        //Fetch week reports from session
        weekReports = (ArrayList) session.getAttribute(ITRResources.ITR_WEEK_REPORTS);

        int weekReportsIndex = Integer.parseInt(request.getParameter("row"));
        xmlCarrier = (WeekReport) weekReports.get(weekReportsIndex);
        session.setAttribute(ITRResources.ITR_WEEK_REPORT, xmlCarrier);
        copyFromSubmitted = Boolean.valueOf(request.getParameter("copySubmitted")).booleanValue();

        //If weekreport have no rows and user want to copy from submitted.
        if (xmlCarrier.getRows() != null && xmlCarrier.getRows().size() == 0 && copyFromSubmitted) {
          xmlCarrier.loadWeekWithLatestSubmittedWeek();
        }
      } /*ADD A ROW*/ else if (action != null && action.equalsIgnoreCase("addrow")) {

        /*set parameters*/
        if (request.getParameter("mo") != null && request.getParameter("mo").length() > 0) {
          monHours = request.getParameter("mo");
        }
        if (request.getParameter("tu") != null && request.getParameter("tu").length() > 0) {
          tueHours = request.getParameter("tu");
        }
        if (request.getParameter("we") != null && request.getParameter("we").length() > 0) {
          wedHours = request.getParameter("we");
        }
        if (request.getParameter("th") != null && request.getParameter("th").length() > 0) {
          thuHours = request.getParameter("th");
        }
        if (request.getParameter("fr") != null && request.getParameter("fr").length() > 0) {
          friHours = request.getParameter("fr");
        }
        if (request.getParameter("sa") != null && request.getParameter("sa").length() > 0) {
          satHours = request.getParameter("sa");
        }
        if (request.getParameter("su") != null && request.getParameter("su").length() > 0) {
          sunHours = request.getParameter("su");
        }
        if (request.getParameter("timeTypeId") != null && request.getParameter("timeTypeId").length() > 0) {
          timeTypeId = request.getParameter("timeTypeId");
        }

        xmlCarrier.getEditRow().setMonday(Double.parseDouble(monHours));
        xmlCarrier.getEditRow().setTuesday(Double.parseDouble(tueHours));
        xmlCarrier.getEditRow().setWednesday(Double.parseDouble(wedHours));
        xmlCarrier.getEditRow().setThursday(Double.parseDouble(thuHours));
        xmlCarrier.getEditRow().setFriday(Double.parseDouble(friHours));
        xmlCarrier.getEditRow().setSaturday(Double.parseDouble(satHours));
        xmlCarrier.getEditRow().setSunday(Double.parseDouble(sunHours));
        xmlCarrier.getEditRow().setTimeTypeId(timeTypeId);
        xmlCarrier.getEditRow().loadTimeType(timeTypeId);

        //set parameters
        if (request.getParameter("comments") != null && request.getParameter("comments").length() > 0) {
          weekComment = request.getParameter("comments");
        }

        xmlCarrier.setWeekComment(weekComment);

        if (IntiroLog.d()) {
          IntiroLog.detail(getClass(), getClass().getName() + ".doPost(): addRow: weekComment=" + weekComment + "  request.getParameter(comments)=" + request.getParameter("comments"));
        }

        /*Create a new row*/
        Row newRow = xmlCarrier.getEditRow().cloneToRow();

        /*Add the new row to your WeekReportEditor*/
        xmlCarrier.addRow(newRow);

        if (IntiroLog.d()) {
          IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): action = " + action + ", newRow.toString() = " + newRow.toString());
        }

        /*Recalculate summary rows*/
        xmlCarrier.getSumRows().calcSum(xmlCarrier.getRows());

        if (IntiroLog.d()) {
          IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): action = " + action + ", xmlCarrier.getEditRow().toString() = " + xmlCarrier.getEditRow().toString());
        }
      } /*PROJECT SELECTED*/ else if (action != null && action.equalsIgnoreCase("projSelected")) {

        /*Set parameters*/
        if (request.getParameter("projSubId") != null && request.getParameter("projSubId").length() > 0) {
          projSubId = request.getParameter("projSubId");
        }
        if (request.getParameter("projSubCode") != null && request.getParameter("projSubCode").length() > 0) {
          //projSubCode = request.getParameter("projSubCode");
        }

        /*Edit row*/
        xmlCarrier.getEditRow().getProject().setProjectActivityId(projSubId);

        //            xmlCarrier.getEditRow().getProject().setProjectSubCode(projSubCode);
        if (IntiroLog.d()) {
          IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): action = " + action + ", xmlCarrier.getEditRow().toString() = " + xmlCarrier.getEditRow().toString());
        }
      } /*EDIT A ROW*/ else if (action != null && action.equalsIgnoreCase("editRow")) {

        /*set parameters*/
        if (request.getParameter("row") != null && request.getParameter("row").length() > 0) {
          row = request.getParameter("row");
        }
        if (IntiroLog.d()) {
          IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): action = " + action + ", row = " + row);
        }
        if (IntiroLog.d()) {
          IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): action = " + action + ", request.getParameter(row) = " + request.getParameter("row"));
        }
        //set parameters
        if (request.getParameter("comments") != null && request.getParameter("comments").length() > 0) {
          weekComment = request.getParameter("comments");
        }

        xmlCarrier.setWeekComment(weekComment);

        /*Set row as editRow*/
        xmlCarrier.getEditRow().load(xmlCarrier.getRow(Integer.parseInt(row)));

        /*Remove the selected row*/
        xmlCarrier.removeRow(Integer.parseInt(row));

        /*Recalculate summary rows*/
        xmlCarrier.getSumRows().calcSum(xmlCarrier.getRows());

        if (IntiroLog.d()) {
          IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): action = " + action + ", xmlCarrier.getEditRow().toString() = " + xmlCarrier.getEditRow().toString());
        }
      } /*REMOVE A ROW*/ else if (action != null && action.equalsIgnoreCase("removeRow")) {

        /*set parameters*/
        if (request.getParameter("row") != null && request.getParameter("row").length() > 0) {
          row = request.getParameter("row");
        }
        //set parameters
        if (request.getParameter("comments") != null && request.getParameter("comments").length() > 0) {
          weekComment = request.getParameter("comments");
        }

        xmlCarrier.setWeekComment(weekComment);

        /*Remove the selected row*/
        xmlCarrier.removeRow(Integer.parseInt(row));

        /*Recalculate summary rows*/
        xmlCarrier.getSumRows().calcSum(xmlCarrier.getRows());
      } else if (action != null && action.equalsIgnoreCase("cancelSubmit")) {
        xmlCarrier.setSubmitErrorOccurred(false);
      }

      /*END ACTION*/

 /*Create an XSLFormatedArea and add it to the page*/
      XSLFormatedArea xslWeek = null;

      if (mode != null && mode.equalsIgnoreCase("submitted")) {
        //xslWeek = new XSLFormatedArea(xmlCarrier, REPORT_VIEW_APPROVE_WEEK_HTML_XSL);
        session.setAttribute(ITRResources.ITR_WEEK_REPORT, xmlCarrier);
        getServletConfig().getServletContext().getRequestDispatcher("../content/weekreport/approveWeek.jsp").forward(request, response);
      } else if (mode != null && mode.equalsIgnoreCase("approve")) {
        xslWeek = new XSLFormatedArea(xmlCarrier, REPORT_VIEW_APPROVE_WEEK_HTML_XSL);
      } else {
        xslWeek = new XSLFormatedArea(xmlCarrier, REPORT_EDIT_WEEK_HTML_XSL);
      }

      page.add(xslWeek);

      /*Display the page*/
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
