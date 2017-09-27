<%@ page session="true"%>
<%@ page import="com.intiro.itr.ITRResources, com.intiro.itr.logic.superadmin.users.PhoneEditor"%>
<%
      //Get Email editor from session
      PhoneEditor xmlCarrier = (PhoneEditor) session.getAttribute(ITRResources.ITR_PHONE_NUMBER_EDITOR);

      //Save the emails
      xmlCarrier.save();

      String mode = request.getParameter("mode");
      response.sendRedirect("changePhonesQuery.jsp?action="+mode);
%>