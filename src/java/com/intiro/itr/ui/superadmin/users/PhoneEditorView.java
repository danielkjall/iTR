package com.intiro.itr.ui.superadmin.users;

import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.intiro.itr.ITRResources;
import com.intiro.itr.logic.superadmin.users.PhoneEditor;
import com.intiro.itr.ui.ITRServlet;
import com.intiro.itr.ui.Page;
import com.intiro.itr.ui.constants.Commands;
import com.intiro.itr.ui.constants.URLs;
import com.intiro.itr.ui.error.NoSessionException;
import com.intiro.itr.ui.xsl.XSLFormatedArea;
import com.intiro.itr.util.ErrorHandler;
import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.itr.util.log.IntiroLog;

public class PhoneEditorView extends ITRServlet implements URLs, Commands {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): entered doGet");
    }

    PrintWriter out;

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

      PhoneEditor xmlCarrier = null;
      String action = request.getParameter("action");

      // Check if it is emails for a Contact or a User
      // The mode parameter can be either "users" or "contacts"
      String mode = request.getParameter("mode");

      if (action != null && action.equalsIgnoreCase("start")) {
        int userId = Integer.parseInt(request.getParameter("userid"));
        xmlCarrier = new PhoneEditor(userProfile, userId, mode);
        session.setAttribute(ITRResources.ITR_PHONE_NUMBER_EDITOR, xmlCarrier);
      } else if (action != null && action.equalsIgnoreCase("removeRow")) {
        xmlCarrier = (PhoneEditor) session.getAttribute(ITRResources.ITR_PHONE_NUMBER_EDITOR);

        int rowIndex = Integer.parseInt(request.getParameter("row"));
        xmlCarrier.removePhoneNumber(rowIndex);
      } else if (action != null && action.equalsIgnoreCase("addRow")) {
        xmlCarrier = (PhoneEditor) session.getAttribute(ITRResources.ITR_PHONE_NUMBER_EDITOR);

        String desc = request.getParameter("desc");

        if (desc == null || desc.length() == 0) {
          desc = "Description is missing";
        }

        xmlCarrier.getNewPhoneNumber().getRegion().getCountry().setCountryId(Integer.parseInt(request.getParameter("countryid")));
        xmlCarrier.getNewPhoneNumber().getRegion().setRegionId(Integer.parseInt(request.getParameter("regionid")));
        xmlCarrier.getNewPhoneNumber().setPhoneDescription(desc);
        xmlCarrier.getNewPhoneNumber().setPhoneNumber(request.getParameter("phoneno"));
        xmlCarrier.addPhoneNumber();
        xmlCarrier.setEditingRow(false);
      } else if (action != null && action.equalsIgnoreCase("editRow")) {
        xmlCarrier = (PhoneEditor) session.getAttribute(ITRResources.ITR_PHONE_NUMBER_EDITOR);

        int row = Integer.parseInt(request.getParameter("row"));

        if (xmlCarrier.getEditingRow()) {
          xmlCarrier.addPhoneNumber();
        }

        xmlCarrier.setNewPhoneNumber(xmlCarrier.getPhoneNumber(row));
        xmlCarrier.removePhoneNumber(row);
        xmlCarrier.setEditingRow(true);
      } else {
        IntiroLog.criticalError(getClass(), getClass().getName() + ".doGet(): Could not find the action to perform");
      }

      XSLFormatedArea xslPhone = new XSLFormatedArea(xmlCarrier, SUPERADMIN_USERS_PHONE_EDITOR_HTML_XSL);
      page.add(xslPhone);

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
        IntiroLog.info(getClass(), getClass().getName() + ".doGet(): Nosession exception caught in " + getClass().getName());
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
