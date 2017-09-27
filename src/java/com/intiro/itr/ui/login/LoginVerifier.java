package com.intiro.itr.ui.login;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.intiro.itr.ITRResources;
import com.intiro.itr.ui.ITRServlet;
import com.intiro.itr.ui.constants.Commands;
import com.intiro.itr.ui.constants.URLs;
import com.intiro.itr.util.ErrorHandler;
import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.toolbox.log.IntiroLog;

public class LoginVerifier extends ITRServlet implements URLs, Commands {

  // ~ Methods
  // ..........................................................................................................

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
    doPost(request, response);
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".doPost(): entered");
    }

    /* Creating session */
    HttpSession session = request.getSession(false);

    if (session == null) {
      reAuthenticate(request, response, getServletContext(), "No session - reauthenticate");

      return;
    }
    try {

      /* Fetching the user profile */
      UserProfile userProfile = (UserProfile) session.getAttribute(ITRResources.ITR_USER_PROFILE);
      if (userProfile == null) {
        userProfile = new UserProfile();
      }

      /* Fetch loginId and password */
      userProfile.setPassword(request.getParameter(XSLFIELD_PASSWORD));
      userProfile.setLoginId(request.getParameter(XSLFIELD_LOGIN_ID));

      /* Login user */
      if (userProfile.login(request.getParameter(XSLFIELD_LOGIN_ID), request.getParameter(XSLFIELD_PASSWORD))) {

        /* redirect user to TimePortal */
        if (IntiroLog.d()) {
          IntiroLog.detail(getClass(), getClass().getName() + ".doPost(): Redirecting user to: /content/frames/frameset.jsp");
        }

        //set the userprofile in session:
        session.setAttribute(ITRResources.ITR_USER_PROFILE, userProfile);

        //redirect to framset:
        StringBuffer redirectURLPath = new StringBuffer(getRelativeURLRoot(request));
        //redirectURLPath.append("content/frames/frameset.jsp");
        redirectURLPath.append("frameset.jsp");
        response.sendRedirect(redirectURLPath.toString());

        return;
      } else { /* Login attempt failed */
        if (IntiroLog.d()) {
          IntiroLog.detail(getClass(), getClass().getName() + ".doPost(): Login failed");
        }

        /* redirect to LOGIN_PAGE */
        reAuthenticate(request, response, getServletContext(), "Wrong userid or password!");

        return;
      }
    } catch (Exception exception) {
      if (IntiroLog.ce()) {
        IntiroLog.criticalError(getClass(), getClass().getName() + ".doPost(): Something went wrong during Login Verification exception = " + exception.getClass().getName(),
                exception);
      }

      /* Create errorHandler */
      UserProfile userProfile = (UserProfile) session.getAttribute(ITRResources.ITR_USER_PROFILE);
      ErrorHandler errorHandler = null;

      try {
        errorHandler = new ErrorHandler(userProfile);
      } catch (Exception e) {
        System.out.println("message: " + e.getMessage());
      }

      errorHandler.setErrorMessage("A problem occured when trying to display the LoginVerifier page.");
      errorHandler.setException(exception);
      handleError(request, response, getServletContext(), errorHandler);

      return;
    }
  }
}
