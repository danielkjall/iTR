package com.intiro.itr.ui.superadmin.activity;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.intiro.itr.ITRResources;
import com.intiro.itr.logic.superadmin.activity.ActivityEditor;
import com.intiro.itr.ui.ITRServlet;
import com.intiro.itr.ui.constants.Commands;
import com.intiro.itr.ui.constants.URLs;
import com.intiro.itr.ui.error.NoSessionException;
import com.intiro.itr.util.ErrorHandler;
import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.itr.util.log.IntiroLog;

/**
 * Servlet that handles saving or adding of an activity.
 *
 * This servlet does not display anything, it collects the information from the incoming form and calls the save method on the user,
 * followed by a redirecting of the logged in superadministator to ActivityQueryView.
 */
public class ActivityEditActivatorView extends ITRServlet implements URLs, Commands {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): entered doGet");
    }

    String code = "";
    String desc = "";

    //START ACTION, Find out what action is called and perform it
    String save = request.getParameter("btnSave");
    String delete = request.getParameter("btnDelete");
    boolean bSave = false;
    boolean bDelete = false;

    //Find out what button was pressed
    String mode = "error";

    if (save != null && save.length() > 0) {
      mode = save.trim();
      bSave = true;
    } else if (delete != null && delete.length() > 0) {
      mode = delete.trim();
      bDelete = true;
    }
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): Mode = " + mode + ", bsave = " + bSave + ", bDelete = " + bDelete + ", save = " + save + ", delete = " + delete);
    }
    try {

      /*Create an output page*/
      //Page page = new Page(request);
      //page = null;
      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): Page created");
      }

      HttpSession session = request.getSession(false);

      /*Get user profile*/
      //UserProfile userProfile = (UserProfile) session.getAttribute(ITRResources.ITR_USER_PROFILE);
      //userProfile = null;
      //Get activityEditor
      ActivityEditor activityEditor = (ActivityEditor) session.getAttribute(ITRResources.ITR_MODIFIED_PROJECT);

      if (bDelete) {
        if (IntiroLog.d()) {
          IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): Deleting activity");
        }

        boolean retVal = activityEditor.getModifiedActivity().delete();

        if (retVal == false) {
          new Exception("Can not delete Activity with id = " + activityEditor.getModifiedActivity().getId() + ". ");
        }
      } else if (bSave) {
        if (IntiroLog.d()) {
          IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): Saving activity");
        }
        if (request.getParameter("code") != null && request.getParameter("code").length() > 0) {
          code = request.getParameter("code");
        }
        if (request.getParameter("description") != null && request.getParameter("description").length() > 0) {
          desc = request.getParameter("description");
        }

        activityEditor.getModifiedActivity().setCode(code);
        activityEditor.getModifiedActivity().setDescription(desc);

        if (IntiroLog.d()) {
          IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): before saving");
        }

        //Save the modified activity to database.
        activityEditor.getModifiedActivity().save();

        if (IntiroLog.d()) {
          IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): activity = " + activityEditor.getModifiedActivity());
        }
      }

      handleSaveOfActivity(request, response, getServletContext());

      return;
    } catch (NoSessionException e) {
      if (IntiroLog.i()) {
        IntiroLog.info(getClass(), getClass().getName() + ".doGet(): Nosession exception caught in " + getClass().getName());
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
    return;
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".doPost(): Entering doPost");
    }

    doGet(request, response);
  }
}
