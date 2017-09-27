<%@ page session="true"%>
<%@ page import="java.util.StringTokenizer, com.intiro.itr.ITRResources, com.intiro.itr.logic.superadmin.projects.*"%>
<%
  	response.setHeader("Cache-Control", "no-cache");
  	response.setHeader("Pragma", "no-cache");
  	response.addHeader("Cache-Control","no-store");

    String projId = "-1";
    String available = null;
    String assigned = null;

      ProjectActivitiesEditor pae = (ProjectActivitiesEditor) session.getAttribute(ITRResources.ITR_PROJECTACTIVITIES_EDITOR);

      if (request.getParameter("projectid") != null && request.getParameter("projectid").length() > 0) {
        projId = request.getParameter("projectid");
      }
      if (request.getParameter("availableList") != null && request.getParameter("availableList").length() > 0) {
        available = request.getParameter("availableList");

        StringTokenizer st = new StringTokenizer(available, ",");
        int[] tmp = new int[st.countTokens()];
        int i = 0;

        while (st.hasMoreTokens()) {
          tmp[i++] = Integer.parseInt(st.nextToken());
        }

        pae.moveFromAssignedToAvailable(tmp);
      }
      if (request.getParameter("assignedList") != null && request.getParameter("assignedList").length() > 0) {
        assigned = request.getParameter("assignedList");

        StringTokenizer st = new StringTokenizer(assigned, ",");
        int[] tmp2 = new int[st.countTokens()];
        int i = 0;

        while (st.hasMoreTokens()) {
          tmp2[i++] = Integer.parseInt(st.nextToken());
        }

        pae.moveFromAvailableToAssigned(tmp2);
      }

      pae.save();
      response.sendRedirect("changeProjectActivitiesQuery.jsp");
%>