<%@ page session="true"%>
<%@ page import="com.intiro.itr.logic.weekreport.*, com.intiro.itr.ITRResources, com.intiro.itr.logic.weekreport.WeekReport, com.intiro.itr.util.personalization.UserProfile"%>
<%
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Pragma", "no-cache");
    response.addHeader("Cache-Control", "no-store");
    UserProfile userProfile = (UserProfile) session.getAttribute(ITRResources.ITR_USER_PROFILE);
    
 
	//If we dont have a userprofile the session has timed out, redirect to login page:
	if(userProfile == null) {
		session.setAttribute(com.intiro.itr.ITRResources.LOGIN_MESSAGE, "Session timeout, reauthenticate");
		response.sendRedirect("logout.jsp");
		return;
	}
    
    String year = request.getParameter( "year" );
    if(year == null) year="";
    if (year.length() == 0 ) {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        year = Integer.toString(cal.YEAR);
    }
    String mode = request.getParameter("mode");
    String title = "Todo";
    int colspan = 13;
    Weeks weeks = new Weeks(userProfile, mode);
    /*Load the week reports and save them in the session object*/
    session.setAttribute( ITRResources.ITR_WEEK_REPORTS, weeks.load(year) );

    if (mode.equalsIgnoreCase("Submitted")) {
      title = "Submitted";
      colspan = 12;
    } else if (mode.equalsIgnoreCase("future")) {
      title = "Future";
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
  <head>
    <link REL="stylesheet" TYPE="text/css" HREF="include/ITR.CSS"/>
    <meta HTTP-EQUIV="Content-Type" CONTENT="text/html;CHARSET=iso-8859-1"/>
    <meta NAME="Expires" Content="Now"/>
    <meta HTTP-EQUIV="Pragma" CONTENT="no_cache"/>
    <title>ITR - View weeks</title>
  </head>
  <body bgcolor="#f3f3f3" leftmargin="0" rightmargin="0" topmargin="0" marginwidth="0" marginheight="0">
    <form action="#" name="weeks">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr bgcolor="#aaaacc" align="center">
          <td class="Title1"><%=title%> Weeks</td>
        </tr>
      </table>
      <p>
        <br>
      </p>
      <table WIDTH="100%" border="0" cellpadding="1" cellspacing="1">
        <!--Start black line, if submitted colspan = 12, else colspan = 13  -->
        <tr>
          <td colspan="<%=colspan%>">
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
              <tr><td bgcolor="#000000"><img src="images/sp.gif" height="1" width="1"/></td></tr>
            </table>
          </td>
        </tr>
        <!--End black line -->
        <tr BGCOLOR="#C4C4C4" class="NormalBold">
          <td width="10%">Week</td>
          <%
              if (!mode.equalsIgnoreCase("Submitted")) {
          %>
          <td width="10%">Copy from submitted</td>
          <%}%>
          <td>Week comment</td>
          <td align="right" width="5%">Mon</td>
          <td align="right" width="5%">Tue</td>
          <td align="right" width="5%">Wed</td>
          <td align="right" width="5%">Thu</td>
          <td align="right" width="5%">Fri</td>
          <td align="right" width="5%">Sat</td>
          <td align="right" width="5%">Sun</td>
          <td align="right" width="5%">Total</td>
          <td align="right" width="5%">Exp.</td>
          <td align="right" width="5%">Diff.</td>
        </tr>
        <%
            int aYear = -1;
            for (int i = 0; i < weeks.getWeekReports().size(); i++) {
              WeekReport aWeek = (WeekReport) weeks.getWeekReports().get(i);
              String bgColor = "";
              if (i % 2 == 0) {
                bgColor = "#fafafa";
              }
              String href = "editWeek.jsp?action=start&row=" + i + "&mode=" + mode + "&copySubmitted=false";
              String copyHref = "editWeek.jsp?action=start&row=" + i + "&mode=" + mode + "&copySubmitted=true";

              if (aYear != aWeek.getFromDate().getYear()) {%>
        <tr><td colspan="<%=colspan%>" bgcolor="#FF2222" align="center" class="NormalBold"><%=aWeek.getFromDate().getYear()%></td></tr>
        <%	aYear = aWeek.getFromDate().getYear();
                    }%>
        <tr bgcolor="<%=bgColor%>">
          <td class="Normal">
            <a href="<%=href%>"><%="(" + aWeek.getFromDate().getYear() + ") " + aWeek.getFromDate().getWeekOfYear() + "_" + aWeek.getFromDate().getWeekPart()%></a>
          </td>
          <%if (!mode.equalsIgnoreCase("Submitted")) {%>
          <td class="Normal">
            <%if (aWeek.getSumRows().getTotalRow().getRowSum() == 0) {%>
            <a href="<%=copyHref%>">Copy</a>
            <%}%>
          </td>
          <%}%>
          <td class="Normal">
							<%=aWeek.getWeekCommentShort()%>							
          </td>
          <td align="right" class="Normal">
            <%=aWeek.getSumRows().getTotalRow().getMonday()%>
          </td>
          <td align="right" class="Normal">
            <%=aWeek.getSumRows().getTotalRow().getTuesday()%>
          </td>
          <td align="right" class="Normal">
            <%=aWeek.getSumRows().getTotalRow().getWednesday()%>
          </td>
          <td align="right" class="Normal">
            <%=aWeek.getSumRows().getTotalRow().getThursday()%>
          </td>
          <td align="right" class="Normal">
            <%=aWeek.getSumRows().getTotalRow().getFriday()%>
          </td>
          <td align="right" class="Normal">
            <%=aWeek.getSumRows().getTotalRow().getSaturday()%>
          </td>
          <td align="right" class="Normal">
            <%=aWeek.getSumRows().getTotalRow().getSunday()%>
          </td>
          <td bgcolor="#E5E5E5" align="right" class="NormalBold">
            <%=aWeek.getSumRows().getTotalRow().getRowSum()%>
          </td>
          <td bgcolor="#E5E5E5" align="right" class="NormalBold">
            <%=aWeek.getSumRows().getExpectedRow().getRowSum()%>
          </td>
          <td bgcolor="#E5E5E5" align="right" class="NormalBold">
            <%=aWeek.getSumRows().getDifferenceRow().getRowSum()%>
          </td>
        </tr>
        <%}%>
        <!--Start black line, if submitted colspan = 12, else colspan = 13-->
        <tr>
          <td colspan="<%=colspan%>">
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
              <tr><td bgcolor="#000000"><img src="images/sp.gif" height="1" width="1"/></td></tr>
            </table>
          </td>
        </tr>
        <!--End grey line -->
      </table>
    </form>
  </body>
</html>
