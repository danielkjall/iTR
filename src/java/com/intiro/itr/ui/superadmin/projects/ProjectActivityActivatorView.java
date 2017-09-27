/**
 * Title:         ITR
 * Description:
 * Copyright:     Copyright (c) 2001
 * Company:       Intiro Development AB
 * @author        Olof Altenstedt
 * @version       1.0
 */
package com.intiro.itr.ui.superadmin.projects;

import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.intiro.itr.ITRResources;
import com.intiro.itr.logic.superadmin.projects.ProjectActivitiesEditor;
import com.intiro.itr.ui.ITRServlet;
import com.intiro.itr.ui.constants.Commands;
import com.intiro.itr.ui.constants.URLs;
import com.intiro.itr.util.ErrorHandler;
import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.toolbox.log.IntiroLog;

/**
 * Servlet that handles saving or adding of an activity to a project.
 *
 * This servlet does not display anything, it collects the information from the incoming form
 * and calls the save method on the user, followed by a redirecting of the logged in superadministator
 * to ProjectQueryView.
 */
public class ProjectActivityActivatorView extends ITRServlet implements URLs, Commands {

  //~ Methods ..........................................................................................................

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): entered doGet");
    }

    String projId = "-1";
    String available = null;
    String assigned = null;

    try {

      /*Create an output page*/
      //Page page = new Page(request);
      //page = null;
      if (IntiroLog.t()) {
        IntiroLog.trace(getClass(), getClass().getName() + ".doGet(): Page created");
      }

      HttpSession session = request.getSession(false);

      /*Get user profile*/
      //UserProfile userProfile = (UserProfile) session.getAttribute(ITRResources.ITR_USER_PROFILE);
      //userProfile = null;
      ProjectActivitiesEditor pae = (ProjectActivitiesEditor) session.getAttribute(ITRResources.ITR_PROJECTACTIVITIES_EDITOR);

      if (request.getParameter("projectid") != null && request.getParameter("projectid").length() > 0) {
        projId = request.getParameter("projectid");

        if (IntiroLog.t()) {
          IntiroLog.trace(getClass(), getClass().getName() + ".doGet(): VALUE BEFORE TRYING TO STORE: <br x=\"x\"/> projId = " + projId);
        }
      }
      if (request.getParameter("availableList") != null && request.getParameter("availableList").length() > 0) {
        available = request.getParameter("availableList");

        if (IntiroLog.t()) {
          IntiroLog.trace(getClass(), getClass().getName() + ".doGet(): VALUE BEFORE TRYING TO STORE: <br x=\"x\"/> available = " + available);
        }

        StringTokenizer st = new StringTokenizer(available, ",");
        int[] tmp = new int[st.countTokens()];
        int i = 0;

        while (st.hasMoreTokens()) {
          tmp[i++] = Integer.parseInt(st.nextToken());
        }

        pae.moveFromAssignedToAvailable(tmp);
      }
      if (request.getParameter("assignedList") != null && request.getParameter("assignedList").length() > 0) {
        assigned = request.getParameter("assignedList");

        if (IntiroLog.t()) {
          IntiroLog.trace(getClass(), getClass().getName() + ".doGet(): VALUE BEFORE TRYING TO STORE: <br x=\"x\"/> assigned = " + assigned);
        }

        StringTokenizer st = new StringTokenizer(assigned, ",");
        int[] tmp2 = new int[st.countTokens()];
        int i = 0;

        while (st.hasMoreTokens()) {
          tmp2[i++] = Integer.parseInt(st.nextToken());
        }

        pae.moveFromAvailableToAssigned(tmp2);
      }
      if (IntiroLog.t()) {
        IntiroLog.trace(getClass(), getClass().getName() + ".doGet(): VALUES BEFORE TRYING TO STORE THEM: <br x=\"x\"/> projId = " + projId + ", available = " + available + ", assigned = " + assigned);
      }

      pae.save();
      handleSaveOfProjectActivities(request, response, getServletContext());
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
    return;
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".doPost(): Entering doPost");
    }

    doGet(request, response);
  }
}