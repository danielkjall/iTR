package com.intiro.itr.ui.admin;

import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.intiro.itr.ITRResources;
import com.intiro.itr.logic.admin.ApproveWeeks;
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
 * This servlet creates a list of weeks.
 */
public class ApproveWeeksView extends ITRServlet implements URLs, Commands {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
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

      ApproveWeeks xmlCarrier = new ApproveWeeks(userProfile, "Approve");

      /*Load the week reports and save them in the session object*/
      session.setAttribute(ITRResources.ITR_WEEK_REPORTS, xmlCarrier.load());

      XSLFormatedArea xslWeeks = new XSLFormatedArea(xmlCarrier, ADMIN_APPROVE_WEEKS_HTML_XSL);
      page.add(xslWeeks);

      //Display the page
      page.display(out);

      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): Page displayed");
      }
      if (out != null) {
        out.flush();
      }
    } catch (NoSessionException e) {
      IntiroLog.criticalError(getClass(), getClass().getName() + ".doGet(): Nosession exception caught in " + getClass().getName());
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
    return;
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".doPost(): Entering doPost");
    }

    doGet(request, response);
  }
}
