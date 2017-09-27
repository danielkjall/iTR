/**
 * Title:         ITR
 * Description:
 * Copyright:     Copyright (c) 2001
 * Company:       Intiro Development AB
 * @author        Daniel Kjall
 * @version       1.0
 */
package com.intiro.itr.ui.superadmin.contacts;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.intiro.itr.ITRResources;
import com.intiro.itr.logic.superadmin.contacts.ContactEdit;
import com.intiro.itr.ui.ITRServlet;
import com.intiro.itr.ui.constants.Commands;
import com.intiro.itr.ui.constants.URLs;
import com.intiro.itr.ui.error.NoSessionException;
import com.intiro.itr.util.ErrorHandler;
import com.intiro.itr.util.ITRCalendar;
import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.toolbox.log.IntiroLog;

/**
 * Servlet that handles saving or adding of a user.
 *
 * This servlet does not display anything, it collects the information from the incoming form
 * and calls the save method on the user, followed by a redirecting of the logged in superadministator
 * to UserQueryView.
 */
public class ContactEditActivatorView extends ITRServlet implements URLs, Commands {

  //~ Methods ..........................................................................................................

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): entered doGet");
    }

    String contactId = "-1";
    String companyId = "-1";
    String position = null;
    String friendLevel = null;
    String description = null;
    String firstContact = null;
    String firstName = null;
    String lastName = null;
    String knownById = "-1";
    String save = request.getParameter("btnSave");
    String delete = request.getParameter("btnDelete");
    boolean bSave = false;
    boolean bDelete = false;

    //Find out what button was pressed
    //String mode = "error";

    if (save != null && save.length() > 0) {
      //mode = save.trim();
      bSave = true;
    }
    else if (delete != null && delete.length() > 0) {
      //mode = delete.trim();
      bDelete = true;
    }
    try {
      //Page page = new Page(request);
      //page = null;

      if (IntiroLog.t()) {
        IntiroLog.trace(getClass(), getClass().getName() + ".doGet(): Page created");
      }

      HttpSession session = request.getSession(false);
      //UserProfile userProfile = (UserProfile) session.getAttribute(ITRResources.ITR_USER_PROFILE);
      //userProfile = null;

      ContactEdit contactEditor = (ContactEdit) session.getAttribute(ITRResources.ITR_MODIFIED_CONTACT);

      if (bDelete) {
        if (IntiroLog.t()) {
          IntiroLog.trace(getClass(), getClass().getName() + ".doGet(): Deleting Contact");
        }

        boolean retVal = contactEditor.getModifiedContact().delete();

        if (retVal == false) {
          new Exception("Can not delete Contact with id = " + contactEditor.getModifiedContact().getId() + ". ");
        }
      }
      else if (bSave) {
        if (IntiroLog.t()) {
          IntiroLog.trace(getClass(), getClass().getName() + ".doGet(): Saving contact");
        }
        if (request.getParameter("contactid") != null && request.getParameter("contactid").length() > 0) {
          contactId = request.getParameter("contactid");
        }
        if (request.getParameter("company") != null && request.getParameter("company").length() > 0) {
          companyId = request.getParameter("company");
        }
        if (request.getParameter("position") != null && request.getParameter("position").length() > 0) {
          position = request.getParameter("position");
        }
        if (request.getParameter("friendlevel") != null && request.getParameter("friendlevel").length() > 0) {
          friendLevel = request.getParameter("friendlevel");
        }
        if (request.getParameter("desc") != null && request.getParameter("desc").length() > 0) {
          description = request.getParameter("desc");
        }
        if (request.getParameter("firstcontact") != null && request.getParameter("firstcontact").length() > 0) {
          firstContact = request.getParameter("firstcontact");
        }
        if (request.getParameter("firstname") != null && request.getParameter("firstname").length() > 0) {
          firstName = request.getParameter("firstname");
        }
        if (request.getParameter("lastname") != null && request.getParameter("lastname").length() > 0) {
          lastName = request.getParameter("lastname");
        }
        if (request.getParameter("knownbyuser") != null && request.getParameter("knownbyuser").length() > 0) {
          knownById = request.getParameter("knownbyuser");
        }
        if (IntiroLog.t()) {
          IntiroLog.trace(getClass(), getClass().getName() + ".doGet(): VALUES BEFORE TRYING TO SET THEM IN THE CONTACT: <br x=\"x\"/> contactId = " + contactId + ", companyId = " + companyId + ", position = " + position + ", friendLevel = " + friendLevel + ", description = " + description + ", firstContact = " + firstContact + ", firstName = " + firstName + ", lastName = " + lastName + ", knownById = " + knownById);
        }

        contactEditor.getModifiedContact().setITR_CompanyId(Integer.parseInt(companyId));
        contactEditor.getModifiedContact().setPosition(position);
        contactEditor.getModifiedContact().setFriendLevel(friendLevel);
        contactEditor.getModifiedContact().setDescription(description);

        try {
          contactEditor.getModifiedContact().setFirstContact(new ITRCalendar(firstContact));
        } catch (Exception e) {
          if (IntiroLog.t()) {
            IntiroLog.trace(getClass(), getClass().getName() + ".doGet(): The date First Contact was given in a faulty way. firstContact=" + firstContact);
          }
        }

        contactEditor.getModifiedContact().setFirstName(firstName);
        contactEditor.getModifiedContact().setLastName(lastName);
        contactEditor.getModifiedContact().setKnownByUser_Id(Integer.parseInt(knownById));

        //Save the modified user to database.
        contactEditor.getModifiedContact().save();
      }

      handleSaveOfContact(request, response, getServletContext());

      return;
    } catch (NoSessionException e) {
      if (IntiroLog.i()) {
        IntiroLog.info(getClass(), getClass().getName() + ".doGet(): Nosession exception caught in " + getClass().getName());
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