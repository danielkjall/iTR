/**
 * Title:         ITR
 * Description:
 * Copyright:     Copyright (c) 2001
 * Company:       Intiro Development AB
 * @author        Daniel Kjall
 * @version       1.0
 */
package com.intiro.itr.ui.superadmin.users;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.intiro.itr.ITRResources;
import com.intiro.itr.logic.superadmin.users.PhoneEditor;
import com.intiro.itr.ui.ITRServlet;
import com.intiro.itr.ui.constants.Commands;
import com.intiro.itr.ui.constants.URLs;
import com.intiro.itr.util.ErrorHandler;
import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.toolbox.log.IntiroLog;

/**
 * This servlet handles saving of Emails.
 */
public class PhoneSaveActivator extends ITRServlet implements URLs, Commands {

  //~ Methods ..........................................................................................................

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): entered doGet");
    }

    //PrintWriter out = null;

    try {
      //out = response.getWriter();

      //Create an output page
      //Page page = new Page(request);
      //page = null;

      if (IntiroLog.t()) {
        IntiroLog.trace(getClass(), getClass().getName() + ".doGet(): Page created");
      }

      HttpSession session = request.getSession(false);
      UserProfile userProfile = (UserProfile) session.getAttribute(ITRResources.ITR_USER_PROFILE);
      response.setContentType(userProfile.getClientInfo().getContentType());

      //Get Email editor from session
      PhoneEditor xmlCarrier = (PhoneEditor) session.getAttribute(ITRResources.ITR_PHONE_NUMBER_EDITOR);

      //Save the emails
      xmlCarrier.save();

      String mode = request.getParameter("mode");
      handleSaveOfUserPhoneNumbers(request, response, getServletContext(), mode);

      /*
       //Create an XSLFormatedArea and add it to the page
       XSLFormatedArea xslAnswer = new XSLFormatedArea(xmlCarrier, SUPERADMIN_USERS_PHONE_SAVE_HTML_XSL);
       page.add(xslAnswer);
       //Display the page
       page.display(out);
       IntiroLog.detail(getClass().getName()+".doGet(): Page displayed");
       if(out != null) out.flush();
       */
      /*
       } catch (NoSessionException e) {
       if (IntiroLog.i()) {
       IntiroLog.info(getClass(), getClass().getName() + ".doGet(): Nosession exception caught in " + getClass().getName());
       }

       reAuthenticate(request, response, getServletContext(), "No Session - reauthenticate");

       return;
       */
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

      return;
    }
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".doPost(): Entering doPost");
    }

    doGet(request, response);
  }
}