package com.intiro.itr.ui.weekreport;

import com.intiro.itr.ITRResources;
import com.intiro.itr.logic.ProjectSelector;
import com.intiro.itr.logic.weekreport.WeekReport;
import com.intiro.itr.ui.*;
import com.intiro.itr.ui.constants.*;
import com.intiro.itr.ui.error.NoSessionException;
import com.intiro.itr.ui.xsl.*;
import com.intiro.itr.util.*;
import com.intiro.itr.util.personalization.*;
import com.intiro.itr.util.xml.*;
import com.intiro.itr.util.log.IntiroLog;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ProjectSelectorView extends ITRServlet implements URLs, Commands {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): entered doGet");
    }

    PrintWriter out = null;

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

      WeekReport xmlCarrier = (WeekReport) session.getAttribute(ITRResources.ITR_WEEK_REPORT);

      //set parameters
      if (request.getParameter("comments") != null && request.getParameter("comments").length() > 0) {
        xmlCarrier.setWeekComment(request.getParameter("comments"));
      }

      DynamicXMLCarrier projSelector = new ProjectSelector(userProfile);
      XSLFormatedArea xslProj = new XSLFormatedArea(projSelector, SELECT_PROJECT_HTML_XSL);
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
      IntiroLog.criticalError(getClass(),
              getClass().getName() + ".doGet(): An Error occured when trying to display "
              + getClass().getName(), exception);

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
  public void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".doPost(): Entering doPost");
    }

    doGet(request, response);
  }
}
