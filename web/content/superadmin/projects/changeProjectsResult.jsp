<%@ page session="true"%>
<%@ page import="com.intiro.itr.ITRResources, com.intiro.itr.logic.superadmin.projects.*"%>
<%
  	response.setHeader("Cache-Control", "no-cache");
  	response.setHeader("Pragma", "no-cache");
  	response.addHeader("Cache-Control","no-store");

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
    if (save != null && save.length() > 0) {
      bSave = true;
    }
    else if (delete != null && delete.length() > 0) {
      bDelete = true;
    }

  ProjectEditor projectEditor = (ProjectEditor) session.getAttribute(ITRResources.ITR_MODIFIED_PROJECT);

  if (bDelete) {
    boolean retVal = projectEditor.getModifiedProject().delete();

    if (retVal == false) {
      new Exception("Can not delete Project with id = " + projectEditor.getModifiedProject().getProjectId() + ". ");
    }
  }
  else if (bSave) {
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
  }
  response.sendRedirect("changeProjectsQuery.jsp");
%>