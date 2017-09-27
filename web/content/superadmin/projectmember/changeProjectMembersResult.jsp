<%@ page session="true"%>
<%@ page import="java.util.StringTokenizer, com.intiro.itr.ITRResources, com.intiro.itr.logic.superadmin.projectmember.*"%>
<%
  	response.setHeader("Cache-Control", "no-cache");
  	response.setHeader("Pragma", "no-cache");
  	response.addHeader("Cache-Control","no-store");
	ProjectMembersEditor pme = (ProjectMembersEditor) session.getAttribute(ITRResources.ITR_PROJECTMEMBERS_EDITOR);
	String projId = "-1";
    String available = null;
    String assigned = null;
    int rate = 0;
    boolean active = false;
    boolean projectAdmin = false;

      if (request.getParameter("projectid") != null && request.getParameter("projectid").length() > 0) {
        projId = request.getParameter("projectid");

      }
      if (request.getParameter("activeMember") != null && request.getParameter("activeMember").length() > 0) {
        if (request.getParameter("activeMember").equals("on")) {
          active = true;
        }
        else {
          active = false;
        }
      }

      pme.setActive(active);

      if (request.getParameter("projectAdmin") != null && request.getParameter("projectAdmin").length() > 0) {
        if (request.getParameter("projectAdmin").equals("on")) {
          projectAdmin = true;
        }
        else {
          projectAdmin = false;
        }
      }

      pme.setProjectAdmin(projectAdmin);

      if (request.getParameter("rate") != null && request.getParameter("rate").length() > 0) {
        rate = Integer.parseInt(request.getParameter("rate"));
        pme.setRate(rate);
      }
      if (request.getParameter("availableList") != null && request.getParameter("availableList").length() > 0) {
        available = request.getParameter("availableList");

        StringTokenizer st = new StringTokenizer(available, ",");
        int[] tmp = new int[st.countTokens()];
        int i = 0;

        while (st.hasMoreTokens()) {
          tmp[i++] = Integer.parseInt(st.nextToken());
        }

        pme.moveFromAssignedToAvailable(tmp);
      }
      if (request.getParameter("assignedList") != null && request.getParameter("assignedList").length() > 0) {
        assigned = request.getParameter("assignedList");

        StringTokenizer st = new StringTokenizer(assigned, ",");
        int[] tmp2 = new int[st.countTokens()];
        int i = 0;

        while (st.hasMoreTokens()) {
          tmp2[i++] = Integer.parseInt(st.nextToken());
        }

        pme.moveFromAvailableToAssigned(tmp2);
      }

      pme.save();
      
      response.sendRedirect("changeProjectMembersQuery.jsp");
%>