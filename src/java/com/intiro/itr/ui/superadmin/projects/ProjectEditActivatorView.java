package com.intiro.itr.ui.superadmin.projects;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.intiro.itr.ITRResources;
import com.intiro.itr.logic.superadmin.projects.ProjectEditor;
import com.intiro.itr.ui.ITRServlet;
import com.intiro.itr.ui.constants.Commands;
import com.intiro.itr.ui.constants.URLs;
import com.intiro.itr.ui.error.NoSessionException;
import com.intiro.itr.util.ErrorHandler;
import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.itr.util.log.IntiroLog;

/**
 * Servlet that handles saving or adding of a user.
 *
 * This servlet does not display anything, it collects the information from the incoming form and calls the save method on the user,
 * followed by a redirecting of the logged in superadministator to ProjectQueryView.
 */
public class ProjectEditActivatorView extends ITRServlet implements URLs, Commands {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): entered doGet");
    }

    //PrintWriter out = null;
    String code = null;
    String name = null;
    String companyId = "-1";
    String activated = null;
    String adminProj = null;
    String contract = null;
    String desc = null;
    String tech = null;
    String fromDate = null;
    String toDate = null;
    String save = request.getParameter("btnSave");
    String delete = request.getParameter("btnDelete");
    boolean bSave = false;
    boolean bDelete = false;

    //Find out what button was pressed
    //String mode = "error";
    if (save != null && save.length() > 0) {
      //mode = save.trim();
      bSave = true;
    } else if (delete != null && delete.length() > 0) {
      //mode = delete.trim();
      bDelete = true;
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
      ProjectEditor projectEditor = (ProjectEditor) session.getAttribute(ITRResources.ITR_MODIFIED_PROJECT);

      if (bDelete) {
        if (IntiroLog.d()) {
          IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): Deleting project");
        }

        boolean retVal = projectEditor.getModifiedProject().delete();

        if (retVal == false) {
          new Exception("Can not delete Project with id = " + projectEditor.getModifiedProject().getProjectId() + ". ");
        }
      } else if (bSave) {
        if (IntiroLog.d()) {
          IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): Saving project");
        }
        if (request.getParameter("code") != null && request.getParameter("code").length() > 0) {
          code = request.getParameter("code");
        }
        if (request.getParameter("name") != null && request.getParameter("name").length() > 0) {
          name = request.getParameter("name");
        }
        if (request.getParameter("companyid") != null && request.getParameter("companyid").length() > 0) {
          companyId = request.getParameter("companyid");
        }
        if (request.getParameter("activated") != null && request.getParameter("activated").length() > 0) {
          activated = request.getParameter("activated");
        }
        if (request.getParameter("adminproj") != null && request.getParameter("adminproj").length() > 0) {
          adminProj = request.getParameter("adminproj");
        }
        if (request.getParameter("contract") != null && request.getParameter("contract").length() > 0) {
          contract = request.getParameter("contract");
        }
        if (request.getParameter("fromdate") != null && request.getParameter("fromdate").length() > 0) {
          fromDate = request.getParameter("fromdate");
        }
        if (request.getParameter("todate") != null && request.getParameter("todate").length() > 0) {
          toDate = request.getParameter("todate");
        }
        if (request.getParameter("desc") != null && request.getParameter("desc").length() > 0) {
          desc = request.getParameter("desc");
        }
        if (request.getParameter("tech") != null && request.getParameter("tech").length() > 0) {
          tech = request.getParameter("tech");
        }
        if (IntiroLog.d()) {
          IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): VALUES BEFORE TRYING TO SET THEM IN THE PROJECTPROFILE: <br x=\"x\"/> code = " + code + ", name = " + name + ", companyId = " + companyId + ", activated = " + activated + ", adminProj = " + adminProj + ", fromDate = " + fromDate + ", toDate = " + toDate + ", desc = " + desc + ", tech = " + tech);
        }

        projectEditor.getModifiedProject().setProjectCode(code);
        projectEditor.getModifiedProject().setProjectName(name);
        projectEditor.getModifiedProject().setCompanyId(companyId);

        String activateProject = "false";

        if (activated != null && activated.equalsIgnoreCase("on")) {
          activateProject = "true";
        }

        projectEditor.getModifiedProject().setActivated(activateProject);

        String adminProject = "false";

        if (adminProj != null && adminProj.equalsIgnoreCase("on")) {
          adminProject = "true";
        }

        projectEditor.getModifiedProject().setAdminProject(adminProject);

        String contractProj = "false";

        if (contract != null && contract.equalsIgnoreCase("on")) {
          contractProj = "true";
        }

        projectEditor.getModifiedProject().setContract(contractProj);
        projectEditor.getModifiedProject().setFromDate(fromDate);
        projectEditor.getModifiedProject().setToDate(toDate);
        projectEditor.getModifiedProject().setProjectDesc(desc);
        projectEditor.getModifiedProject().setTechnique(tech);

        //Save the modified project to database.
        projectEditor.getModifiedProject().save();

        if (IntiroLog.d()) {
          IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): project = " + projectEditor.getModifiedProject());
        }
      }

      handleSaveOfProject(request, response, getServletContext());

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
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".doPost(): Entering doPost");
    }

    doGet(request, response);
  }
}
