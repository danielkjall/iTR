package com.intiro.itr.ui.superadmin.projectmember;

import java.util.StringTokenizer;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.intiro.itr.ITRResources;
import com.intiro.itr.logic.superadmin.projectmember.ProjectMembersEditor;
import com.intiro.itr.ui.ITRServlet;
import com.intiro.itr.ui.constants.Commands;
import com.intiro.itr.ui.constants.URLs;
import com.intiro.itr.util.ErrorHandler;
import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.itr.util.log.IntiroLog;

/**
 * Servlet that handles saving or adding of an activity to a project.
 *
 * This servlet does not display anything, it collects the information from the incoming form and calls the save method on the user,
 * followed by a redirecting of the logged in superadministator to ProjectQueryView.
 */
public class ProjectMembersActivatorView extends ITRServlet implements URLs, Commands {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): entered doGet");
    }

    String projId = "-1";
    String available = null;
    String assigned = null;
    int rate = 0;
    boolean active = false;
    boolean projectAdmin = false;

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
      ProjectMembersEditor pme = (ProjectMembersEditor) session.getAttribute(ITRResources.ITR_PROJECTMEMBERS_EDITOR);

      if (request.getParameter("projectid") != null && request.getParameter("projectid").length() > 0) {
        projId = request.getParameter("projectid");

        if (IntiroLog.d()) {
          IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): VALUE BEFORE TRYING TO STORE: <br x=\"x\"/> projId = " + projId);
        }
      }
      if (request.getParameter("activeMember") != null && request.getParameter("activeMember").length() > 0) {
        if (request.getParameter("activeMember").equals("on")) {
          active = true;
        } else {
          active = false;
        }
      }

      pme.setActive(active);

      if (request.getParameter("projectAdmin") != null && request.getParameter("projectAdmin").length() > 0) {
        if (request.getParameter("projectAdmin").equals("on")) {
          projectAdmin = true;
        } else {
          projectAdmin = false;
        }
      }

      pme.setProjectAdmin(projectAdmin);

      if (request.getParameter("rate") != null && request.getParameter("rate").length() > 0) {
        try {
          rate = Integer.parseInt(request.getParameter("rate"));
        } catch (Exception e) {
          if (IntiroLog.d()) {
            IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): Parsing the rate filed to an int failed. err=" + e);
          }
        }

        pme.setRate(rate);
      }
      if (request.getParameter("availableList") != null && request.getParameter("availableList").length() > 0) {
        available = request.getParameter("availableList");

        if (IntiroLog.d()) {
          IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): VALUE BEFORE TRYING TO STORE: <br x=\"x\"/> available = " + available);
        }

        StringTokenizer st = new StringTokenizer(available, ",");
        int[] tmp = new int[st.countTokens()];
        int i = 0;

        while (st.hasMoreTokens()) {
          tmp[i++] = Integer.parseInt(st.nextToken());
        }

        pme.moveFromAssignedToAvailable(tmp);
      }
      if (request.getParameter("assignedList") != null && request.getParameter("assignedList").length() > 0) {
        assigned = request.getParameter("assignedList");

        if (IntiroLog.d()) {
          IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): VALUE BEFORE TRYING TO STORE: <br x=\"x\"/> assigned = " + assigned);
        }

        StringTokenizer st = new StringTokenizer(assigned, ",");
        int[] tmp2 = new int[st.countTokens()];
        int i = 0;

        while (st.hasMoreTokens()) {
          tmp2[i++] = Integer.parseInt(st.nextToken());
        }

        pme.moveFromAvailableToAssigned(tmp2);
      }
      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): VALUES BEFORE TRYING TO STORE THEM: <br x=\"x\"/> projId = " + projId + ", available = " + available + ", assigned = " + assigned);
      }

      pme.save();
      handleSaveOfProjectMembers(request, response, getServletContext());
      /*
       } 
       catch (NoSessionException e) {
       if (IntiroLog.i()) {
       IntiroLog.info(getClass(), getClass().getName() + ".doGet(): Nosession exception caught in " + getClass().getName());
       }

       reAuthenticate(request, response, getServletContext(), "No Session - reauthenticate");

       return;
       */
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
