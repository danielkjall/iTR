<%@ page session="true"%>
<%@ page import="com.intiro.itr.util.ITRCalendar, com.intiro.itr.ITRResources, com.intiro.itr.logic.superadmin.users.UserEdit"%>
<%
  	response.setHeader("Cache-Control", "no-cache");
  	response.setHeader("Pragma", "no-cache");
  	response.addHeader("Cache-Control","no-store");

    String firstName = null;
    String lastName = null;
    String languageId = "-1";
    String skinId = "-1";
    String companyId = "-1";
    String adminUserId = "-1";
    String roleId = "-1";
    String loginId = null;
    String newPassword = null;
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

    if (save != null && save.length() > 0) {
      bSave = true;
    }
    else if (delete != null && delete.length() > 0) {
      bDelete = true;
    }

      UserEdit userEditor = (UserEdit) session.getAttribute(ITRResources.ITR_MODIFIED_USER);

      if (bDelete) {
        boolean retVal = userEditor.getModifiedUser().delete();

        if (retVal == false) {
          new Exception("Can not delete User with id = " + userEditor.getModifiedUser().getUserId() + ". ");
        }
      }
      else if (bSave) {
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
        }
        if (request.getParameter("vacationovertime") != null && request.getParameter("vacationovertime").length() > 0) {
          vacationovertime = request.getParameter("vacationovertime");
        }
        if (request.getParameter("role") != null && request.getParameter("role").length() > 0) {
          roleId = request.getParameter("role");
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

            userEditor.getModifiedUser().setActivatedDate(now);
          }
          else { //User has been deactivated

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

      response.sendRedirect("changeUsersQuery.jsp");
%>