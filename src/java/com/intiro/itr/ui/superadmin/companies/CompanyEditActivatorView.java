package com.intiro.itr.ui.superadmin.companies;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.intiro.itr.ITRResources;
import com.intiro.itr.logic.superadmin.companies.CompanyEditor;
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
 * followed by a redirecting of the logged in superadministator to UserQueryView.
 */
public class CompanyEditActivatorView extends ITRServlet implements URLs, Commands {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): entered doGet");
    }

    String name = null;
    //String companyParent = null;
    String visitAddressRow1 = null;
    String visitAddressRow2 = null;
    String visitAddressRow3 = null;
    String invoiceAddressRow1 = null;
    String invoiceAddressRow2 = null;
    String invoiceAddressRow3 = null;

    try {

      /*Create an output page*/
      //Page page = new Page(request);
      //page = null;
      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): Page created");
      }

      HttpSession session = request.getSession(false);

      /*Get user profile*/
      UserProfile userProfile = (UserProfile) session.getAttribute(ITRResources.ITR_USER_PROFILE);

      /*Set content type*/
      response.setContentType(userProfile.getClientInfo().getContentType());

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
      if (bDelete) {
        CompanyEditor companyEditor = (CompanyEditor) session.getAttribute(ITRResources.ITR_MODIFIED_COMPANY);
        boolean retVal = companyEditor.getModifiedCompany().delete();

        if (retVal == false) {
          new Exception("Can not delete Company with company id = " + companyEditor.getModifiedCompany().getCompanyId() + ". ");
        }
      } else if (bSave) {
        if (request.getParameter("name") != null && request.getParameter("name").length() > 0) {
          name = request.getParameter("name");
        }
        if (request.getParameter("companyParent") != null && request.getParameter("companyParent").length() > 0) {
          //companyParent = request.getParameter("companyParent");
        }
        if (request.getParameter("visitAddressRow1") != null && request.getParameter("visitAddressRow1").length() > 0) {
          visitAddressRow1 = request.getParameter("visitAddressRow1");
        }
        if (request.getParameter("visitAddressRow2") != null && request.getParameter("visitAddressRow2").length() > 0) {
          visitAddressRow2 = request.getParameter("visitAddressRow2");
        }
        if (request.getParameter("visitAddressRow3") != null && request.getParameter("visitAddressRow3").length() > 0) {
          visitAddressRow3 = request.getParameter("visitAddressRow3");
        }
        if (request.getParameter("invoiceAddressRow1") != null && request.getParameter("invoiceAddressRow1").length() > 0) {
          invoiceAddressRow1 = request.getParameter("invoiceAddressRow1");
        }
        if (request.getParameter("invoiceAddressRow2") != null && request.getParameter("invoiceAddressRow2").length() > 0) {
          invoiceAddressRow2 = request.getParameter("invoiceAddressRow2");
        }
        if (request.getParameter("invoiceAddressRow3") != null && request.getParameter("invoiceAddressRow3").length() > 0) {
          invoiceAddressRow3 = request.getParameter("invoiceAddressRow3");
        }

        CompanyEditor companyEditor = (CompanyEditor) session.getAttribute(ITRResources.ITR_MODIFIED_COMPANY);
        companyEditor.getModifiedCompany().setName(name);
        companyEditor.getModifiedCompany().setInvoiceAddressRow1(invoiceAddressRow1);
        companyEditor.getModifiedCompany().setInvoiceAddressRow2(invoiceAddressRow2);
        companyEditor.getModifiedCompany().setInvoiceAddressRow3(invoiceAddressRow3);
        companyEditor.getModifiedCompany().setVisitAddressRow1(visitAddressRow1);
        companyEditor.getModifiedCompany().setVisitAddressRow2(visitAddressRow2);
        companyEditor.getModifiedCompany().setVisitAddressRow3(visitAddressRow3);

        //Save the modified project to database.
        companyEditor.getModifiedCompany().save();
      }

      handleModificationOfCompany(request, response, getServletContext());

      return;
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
