<%@ page session="true"%>
<%@ page import="com.intiro.itr.ITRResources, com.intiro.itr.logic.superadmin.users.EmailEditor"%>
<%
  	response.setHeader("Cache-Control", "no-cache");
  	response.setHeader("Pragma", "no-cache");
  	response.addHeader("Cache-Control","no-store");
  	
    //Get Email editor from session
    EmailEditor xmlCarrier = (EmailEditor) session.getAttribute(ITRResources.ITR_EMAIL_EDITOR);

    //Save the emails
    xmlCarrier.save();

    String mode = request.getParameter("mode");
    response.sendRedirect("changeEmailsQuery.jsp?action=" + mode);
%>