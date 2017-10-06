package com.intiro.itr.ui.frames;

import com.intiro.itr.ITRResources;
import com.intiro.itr.logic.frames.Navigation;
import com.intiro.itr.ui.*;
import com.intiro.itr.ui.ITRServlet;
import com.intiro.itr.ui.constants.Commands;
import com.intiro.itr.ui.constants.URLs;
import com.intiro.itr.ui.error.NoSessionException;
import com.intiro.itr.ui.xsl.*;
import com.intiro.itr.util.*;
import com.intiro.itr.util.personalization.*;
import com.intiro.itr.util.log.IntiroLog;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class NavigationFrame extends ITRServlet implements URLs, Commands {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): entered");
    }

    PrintWriter out;

    try {

      /*Fetching output stream*/
      out = response.getWriter();

      /*Create an output page*/
      Page page = new Page(request);

      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): Page created");
      }

      /*Fetching session and ClientInfo*/
      HttpSession session = request.getSession(false);

      /*Fetching the user profile*/
      UserProfile userProfile = (UserProfile) session.getAttribute(ITRResources.ITR_USER_PROFILE);

      /*Setting content type*/
      response.setContentType(userProfile.getClientInfo().getContentType());

      /*Create XSLFormatedArea*/
      Navigation xmlCarrier = new Navigation(userProfile);
      XSLFormatedArea xslNavigation = new XSLFormatedArea(xmlCarrier, NAVIGATION_HTML_XSL);

      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): Adding xslformated area to page");
      }

      /*Add XSLFormatedArea to page*/
      page.add(xslNavigation);

      /*Display the page*/
      page.display(out);

      if (out != null) {
        out.flush();
      }
    } catch (NoSessionException e) {
      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".Nosession exception caught in " + getClass().getName());
      }

      reAuthenticate(request, response, getServletContext(), Commands.REAUTHENTICATE_MESSAGE);

      return;
    } catch (Exception exception) {
      IntiroLog.criticalError(getClass(),
              getClass().getName() + ".doGet(): An Error occured when trying to display "
              + getClass().getName(), exception);

      UserProfile userProfile = (UserProfile) request.getSession(false).getAttribute(ITRResources.ITR_USER_PROFILE);

      /*Create errorHandler*/
      ErrorHandler errorHandler = null;

      try {
        errorHandler = new ErrorHandler(userProfile);
      } catch (Exception e) {
        System.out.println("message: " + e.getMessage());
        e.printStackTrace();
      }

      errorHandler.setErrorMessage("A problem occured when trying to display the " + getClass().getName());
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
