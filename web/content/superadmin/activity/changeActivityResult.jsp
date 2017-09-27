<%@ page session="true"%>
<%@ page import="com.intiro.itr.ITRResources, com.intiro.itr.logic.superadmin.activity.*"%>
<%
  response.setHeader("Cache-Control", "no-cache");
  response.setHeader("Pragma", "no-cache");
  response.addHeader("Cache-Control","no-store");
    
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
  }
  else if (delete != null && delete.length() > 0) {
    mode = delete.trim();
    bDelete = true;
  }

  //Get activityEditor
  ActivityEditor activityEditor = (ActivityEditor) session.getAttribute(ITRResources.ITR_MODIFIED_PROJECT);

  if (bDelete) {

    boolean retVal = activityEditor.getModifiedActivity().delete();

    if (retVal == false) {
      new Exception("Can not delete Activity with id = " + activityEditor.getModifiedActivity().getId() + ". ");
    }
  }
  else if (bSave) {
    if (request.getParameter("code") != null && request.getParameter("code").length() > 0) {
      code = request.getParameter("code");
    }
    if (request.getParameter("description") != null && request.getParameter("description").length() > 0) {
      desc = request.getParameter("description");
    }

    activityEditor.getModifiedActivity().setCode(code);
    activityEditor.getModifiedActivity().setDescription(desc);

    //Save the modified activity to database.
    activityEditor.getModifiedActivity().save();
  }

  response.sendRedirect("changeActivityQuery.jsp");
%>
