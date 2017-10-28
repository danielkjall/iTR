package com.intiro.itr.ui.superadmin.users;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.intiro.itr.ITRResources;
import com.intiro.itr.logic.superadmin.users.UserEdit;
import com.intiro.itr.ui.ITRServlet;
import com.intiro.itr.ui.constants.Commands;
import com.intiro.itr.ui.constants.URLs;
import com.intiro.itr.ui.error.NoSessionException;
import com.intiro.itr.util.ErrorHandler;
import com.intiro.itr.util.ITRCalendar;
import com.intiro.itr.util.ItrUtil;
import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.itr.util.log.IntiroLog;

/**
 * Servlet that handles saving or adding of a user.
 *
 * This servlet does not display anything, it collects the information from the incoming form and calls the save method on the user,
 * followed by a redirecting of the logged in superadministator to UserQueryView.
 */
public class UserEditActivatorView extends ITRServlet implements URLs, Commands {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): entered doGet");
    }

    //PrintWriter out = null;
    //String userId = "-1";
    String firstName = null;
    String lastName = null;
    String languageId = "-1";
    String skinId = "-1";
    String companyId = "-1";
    String adminUserId = "-1";
    String roleId = "-1";
    String loginId = null;
    String newPassword = null;
    //String confirmPassword = null;
    String activated = null;
    String yearlyVacation = "0";
    String savedVacation = "0";
    String usedVacation = "0";
    String moneyovertime = "0";
    String vacationovertime = "0";
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

      //out = response.getWriter();

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
      UserEdit userEditor = (UserEdit) session.getAttribute(ITRResources.ITR_MODIFIED_USER);

      if (bDelete) {
        if (IntiroLog.d()) {
          IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): Deleting user");
        }

        boolean retVal = userEditor.getModifiedUser().delete();

        if (retVal == false) {
          new Exception("Can not delete User with id = " + userEditor.getModifiedUser().getUserId() + ". ");
        }
      } else if (bSave) {
        if (IntiroLog.d()) {
          IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): Saving user");
        }
        if (request.getParameter("userid") != null && request.getParameter("userid").length() > 0) {
          //userId = request.getParameter("userid");
        }
        if (request.getParameter("firstname") != null && request.getParameter("firstname").length() > 0) {
          firstName = request.getParameter("firstname");
        }
        if (request.getParameter("lastname") != null && request.getParameter("lastname").length() > 0) {
          lastName = request.getParameter("lastname");
        }
        if (request.getParameter("language") != null && request.getParameter("language").length() > 0) {
          languageId = request.getParameter("language");
        }
        if (request.getParameter("skin") != null && request.getParameter("skin").length() > 0) {
          skinId = request.getParameter("skin");
        }
        if (request.getParameter("company") != null && request.getParameter("company").length() > 0) {
          companyId = request.getParameter("company");
        }
        if (request.getParameter("adminuser") != null && request.getParameter("adminuser").length() > 0) {
          adminUserId = request.getParameter("adminuser");
        }
        if (request.getParameter("newloginId") != null && request.getParameter("newloginId").length() > 0) {
          loginId = request.getParameter("newloginId");
        }
        if (request.getParameter("newpassword") != null && request.getParameter("newpassword").length() > 0) {
          newPassword = request.getParameter("newpassword");
        }
        if (request.getParameter("confirmpassword") != null && request.getParameter("confirmpassword").length() > 0) {
          //confirmPassword = request.getParameter("confirmpassword");
        }
        if (request.getParameter("activated") != null && request.getParameter("activated").length() > 0) {
          activated = request.getParameter("activated");
        }
        if (request.getParameter("yearlyvacation") != null && request.getParameter("yearlyvacation").length() > 0) {
          yearlyVacation = request.getParameter("yearlyvacation");
        }
        if (request.getParameter("savedvacation") != null && request.getParameter("savedvacation").length() > 0) {
          savedVacation = request.getParameter("savedvacation");
        }
        if (request.getParameter("usedvacation") != null && request.getParameter("usedvacation").length() > 0) {
          usedVacation = request.getParameter("usedvacation");
        }
        if (request.getParameter("moneyovertime") != null && request.getParameter("moneyovertime").length() > 0) {
          moneyovertime = request.getParameter("moneyovertime");
          if(ItrUtil.isStrContainingValue(moneyovertime) ){
            moneyovertime = moneyovertime.replace(',','.');
          }
        }
        if (request.getParameter("vacationovertime") != null && request.getParameter("vacationovertime").length() > 0) {
          vacationovertime = request.getParameter("vacationovertime");
          if(ItrUtil.isStrContainingValue(vacationovertime) ){
            vacationovertime = vacationovertime.replace(',','.');
          }
        }
        if (request.getParameter("role") != null && request.getParameter("role").length() > 0) {
          roleId = request.getParameter("role");
        }
        if (IntiroLog.d()) {
          IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): VALUES BEFORE TRYING TO SET THEM IN THE USERPROFILE: <br x=\"x\"/> firstName = " + firstName + ", lastName = " + lastName + ", languageId = " + languageId + ", skinId = " + skinId + ", companyId = " + companyId + ", adminUserId = " + adminUserId + ", loginId = " + loginId + ", newPassword = " + newPassword + ", activated = " + activated + ", savedVacation = " + savedVacation + ", usedVacation = " + usedVacation + ", savedVacation = " + savedVacation + ", moneyovertime = " + moneyovertime + ", vacationovertime = " + vacationovertime + ", roleId = " + roleId);
        }

        userEditor.getModifiedUser().setFirstName(firstName);
        userEditor.getModifiedUser().setLastName(lastName);
        userEditor.getModifiedUser().getRole().setRoleId(Integer.parseInt(roleId));
        userEditor.getModifiedUser().setLanguageId(languageId);
        userEditor.getModifiedUser().setSkinId(skinId);
        userEditor.getModifiedUser().setCompanyId(companyId);
        userEditor.getModifiedUser().setReportApproverId(adminUserId);
        userEditor.getModifiedUser().setLoginId(loginId);

        //PASSWORD
        //only change password if something is changed
        if (newPassword != null && newPassword.length() > 0) {
          userEditor.getModifiedUser().setPassword(newPassword);
        }
        if (IntiroLog.d()) {
          IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): activated = " + activated + ", (activated != null) = " + (activated != null));
        }

        //ACTIVATED
        boolean activateUser = false;

        if (activated != null && activated.equalsIgnoreCase("on")) {
          activateUser = true;
        }
        //find out if status has changed.
        if (userEditor.getModifiedUser().getActivated() != activateUser) {
          ITRCalendar now = new ITRCalendar();

          //Has user been activated
          if (activateUser == true) {
            if (IntiroLog.d()) {
              IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): DATE TO SET FOR ACTIVATEDDATE, now = " + now);
            }

            userEditor.getModifiedUser().setActivatedDate(now);
          } else { //User has been deactivated
            if (IntiroLog.d()) {
              IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): DATE TO SET FOR DEACTIVATEDDATE, now = " + now);
            }

            userEditor.getModifiedUser().setDeActivatedDate(now);
          }

          userEditor.getModifiedUser().setActivated(String.valueOf(activateUser));
        }

        userEditor.getModifiedUser().setDefaultVacationDays(Integer.parseInt(yearlyVacation));
        userEditor.getModifiedUser().setUsedVacationDays(Integer.parseInt(usedVacation));
        userEditor.getModifiedUser().setSavedVacationDays(Integer.parseInt(savedVacation));
        userEditor.getModifiedUser().setOvertimeMoneyHours(Double.parseDouble(moneyovertime));
        userEditor.getModifiedUser().setOvertimeVacationHours(Double.parseDouble(vacationovertime));

        //Save the modified user to database.
        userEditor.getModifiedUser().save();
      }

      handleSaveOfUser(request, response, getServletContext());

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
