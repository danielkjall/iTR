package com.intiro.itr.ui.weekreport;

import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.intiro.itr.ITRResources;
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
 * This servlet handles saving, submitting, rejecting and approving of a WeekReport.
 */
public class SaveSubmitWeekReport extends ITRServlet implements URLs, Commands {

  //~ Methods ..........................................................................................................
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): entered doGet");
    }

    PrintWriter out = null;
    String weekComment = "";
    //String mode = "";

    try {
      out = response.getWriter();

      //Create an output page
      Page page = new Page(request);

      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): Page created");
      }

      HttpSession session = request.getSession(false);
      UserProfile userProfile = (UserProfile) session.getAttribute(ITRResources.ITR_USER_PROFILE);
      response.setContentType(userProfile.getClientInfo().getContentType());

      //Get WeekReport from session
      WeekReport xmlCarrier = (WeekReport) session.getAttribute(ITRResources.ITR_WEEK_REPORT);

      //START ACTION, Find out what action is called and perform it
      String save = request.getParameter("btnSave");
      String submit = request.getParameter("btnSubmit");
      String approve = request.getParameter("btnApprove");
      String reject = request.getParameter("btnReject");
      String action = request.getParameter("action");

      // if weekComment is not set. Read it from request object and set it
      if (request.getParameter("comments") != null && request.getParameter("comments").length() > 0) {
        weekComment = request.getParameter("comments");
        xmlCarrier.setWeekComment(weekComment);
      }

      //SAVE THE WEEKREPORT
      if (save != null) {
        if (IntiroLog.d()) {
          IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): SAVE WEEKREPORT");
        }

        //mode = "save";
        xmlCarrier.save();

        //redirect user to todo page.
        handleSave(request, response, getServletContext());

        return;
      } //SUBMIT THE WEEKREPORT
      else if (submit != null) {
        if (IntiroLog.d()) {
          IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): FORCE SUBMITTING WEEKREPORT");
        }

        //mode = "submit";
        if (xmlCarrier.checkIfOkToSubmit()) {

          /*Submit the week report*/
          xmlCarrier.submit();

          //redirect user to submitted page.
          handleSubmit(request, response, getServletContext());

          return;
        }
      } //FORCE SUBMIT OF THE WEEKREPORT
      else if (action != null && action.equalsIgnoreCase("forceSubmit")) {
        if (IntiroLog.d()) {
          IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): SUBMITTING WEEKREPORT");
        }

        //mode = "submit";
        //Submit the week report
        xmlCarrier.submit();

        //redirect user to submitted page.
        handleSubmit(request, response, getServletContext());

        return;
      } // Reject a WeekReport
      else if (reject != null && reject.trim().equalsIgnoreCase("Reject")) {
        if (IntiroLog.d()) {
          IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): Rejecting a WeekReport");
        }

        //Reject the week report
        xmlCarrier.reject();

        //redirect admin to weeks to approve.
        handleReject(request, response, getServletContext());

        return;
      } //Approve a WeekReport
      else if (approve != null && approve.trim().equalsIgnoreCase("Approve")) {
        if (IntiroLog.d()) {
          IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): Approving a WeekReport");
        }

        //Approve a week report*/
        xmlCarrier.approve();

        //Redirect admin to weeks to approve
        handleApprove(request, response, getServletContext());

        return;
      } else {
        IntiroLog.criticalError(getClass(), getClass().getName() + ".doGet(): ACTION NOT FOUND");

        int log = IntiroLog.getInstance().getLoggingLevel();
        IntiroLog.getInstance().setLoggingLevel(6);

        Enumeration names = request.getParameterNames();

        while (names.hasMoreElements()) {
          String oneName = (String) names.nextElement();
          String oneValue = request.getParameter(oneName);

          if (oneValue != null) {
            if (IntiroLog.d()) {
              IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): " + oneName + " = " + oneValue);
            }
          }
        }

        IntiroLog.getInstance().setLoggingLevel(log);
      }

      /*END ACTION*/

      /*Create an XSLFormatedArea and add it to the page*/
      // If this code is reached we must show a warning page
      XSLFormatedArea xslAnswer = new XSLFormatedArea(xmlCarrier, RESULT_SAVE_SUBMIT_WEEK_XSL);
      page.add(xslAnswer);

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
