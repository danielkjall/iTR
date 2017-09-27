<%@ page session="true"%>
<%@ page import="com.intiro.itr.ITRResources, com.intiro.itr.util.personalization.*, com.intiro.itr.logic.superadmin.companies.*"%>
<%
  response.setHeader("Cache-Control", "no-cache");
  response.setHeader("Pragma", "no-cache");
  response.addHeader("Cache-Control","no-store");

	String name = null;
    String visitAddressRow1 = null;
    String visitAddressRow2 = null;
    String visitAddressRow3 = null;
    String invoiceAddressRow1 = null;
    String invoiceAddressRow2 = null;
    String invoiceAddressRow3 = null;

    
      /*Get user profile*/
      UserProfile userProfile = (UserProfile) session.getAttribute(ITRResources.ITR_USER_PROFILE);

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
      }
      else if (delete != null && delete.length() > 0) {
        mode = delete.trim();
        bDelete = true;
      }
      if (bDelete) {
        CompanyEditor companyEditor = (CompanyEditor) session.getAttribute(ITRResources.ITR_MODIFIED_COMPANY);
        boolean retVal = companyEditor.getModifiedCompany().delete();

        if (retVal == false) {
          new Exception("Can not delete Company with company id = " + companyEditor.getModifiedCompany().getCompanyId() + ". ");
        }
      }
      else if (bSave) {
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

      response.sendRedirect("changeCompaniesQuery.jsp");
%>