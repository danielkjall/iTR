<%@ page session="true"%>
<%@ page import="java.util.*,com.intiro.itr.ITRResources, com.intiro.itr.logic.admin.*, com.intiro.itr.util.personalization.*,com.intiro.itr.logic.weekreport.*"%>
<%
response.setHeader("Cache-Control", "no-cache");
response.setHeader("Pragma", "no-cache");
response.addHeader("Cache-Control","no-store");
%>

<%
      UserProfile userProfile = (UserProfile) session.getAttribute(ITRResources.ITR_USER_PROFILE);

      ApproveWeeks xmlCarrier = new ApproveWeeks(userProfile, "Approve");

      /*Load the week reports and save them in the session object*/
      ArrayList weeks = xmlCarrier.load();
      session.setAttribute(ITRResources.ITR_WEEK_REPORTS, weeks);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
	<head>
		<link REL="stylesheet" TYPE="text/css" HREF="include/ITR.CSS"/>
		<meta HTTP-EQUIV="Content-Type" CONTENT="text/html;CHARSET=iso-8859-1"/>
		<title>ITR - Weeks to approve</title>
	</head>
	<body bgcolor="#f3f3f3" leftmargin="0" rightmargin="0" topmargin="0" marginwidth="0" marginheight="0">
		<form action="#" name="weeks">
			<table border="0" cellpadding="0" cellspacing="0" width="100%">
				<tr bgcolor="#aaaacc" align="center">
					<td class="Title1"> Weeks to Approve</td>
				</tr>
			</table>
			<p>
			<table WIDTH="800" border="0" cellpadding="1" cellspacing="1" align="center">
				<!--Start black line-->
				<tr>
					<td colspan="12">
						<table border="0" cellpadding="0" cellspacing="0" width="100%">
							<tr>
								<td bgcolor="#000000"><img src="images/sp.gif" height="1" width="1"/></td>
							</tr>
						</table>
					</td>
				</tr>
				<!--End black line -->
				<tr BGCOLOR="#C4C4C4" class="NormalBold">
					<td width="20%">User</td>
					<td width="10%">Week</td>
					<td width="55%">Week comment</td>
					<td align="right" width="5%">Total</td>
					<td align="right" width="5%">Exp.</td>
					<td align="right" width="5%">Diff.</td>
				</tr>
				<%
				String userName = "";
				String userNameTemp = "";
				for(int i=0; i<weeks.size(); i++) {
					WeekReport aWeek = (WeekReport) weeks.get(i);
					userNameTemp = aWeek.getUserProfile().getFirstName()+" "+aWeek.getUserProfile().getLastName();
					if(!userName.equalsIgnoreCase(userNameTemp)) {
						userName = userNameTemp;
					%>
					<!-- Black line -->
					<tr><td colspan="6"><table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td bgcolor="#000000"><img src="images/sp.gif" height="1" width="1"/></td></tr></table></td></tr>

					<!-- Print out the users name -->
					<tr bgcolor="#C4C4C4"><td class="NormalBold" colspan="6" align="center"><%=aWeek.getUserProfile().getFirstName()+" "+aWeek.getUserProfile().getLastName()%></td></tr>
					
					<!-- Black line -->
					<tr><td colspan="6"><table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td bgcolor="#000000"><img src="images/sp.gif" height="1" width="1"/></td></tr></table></td></tr>
				<%}%>
					<tr	<%if(i%2 == 0) { out.write("bgcolor=\"#fafafa\""); }%> >
						<td class="Normal">
						  <%=aWeek.getUserProfile().getFirstName() + " " + aWeek.getUserProfile().getLastName()%>
						</td>
						<td class="Normal">
							<a href="editWeek.jsp?action=start&row=<%=i%>&mode=<%=aWeek.getMode()%>">
							<%="(" + aWeek.getFromDate().getYearDisplay()+ ") " + aWeek.getFromDate().getWeekOfYear()+ "_" +aWeek.getFromDate().getWeekPart()%>
							</a>
						</td>
						<td class="Normal">
							<%=aWeek.getWeekCommentShort()%>
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
				<!--Start black line-->
				<tr>
					<td colspan="12">
						<table border="0" cellpadding="0" cellspacing="0" width="100%">
							<tr>
								<td bgcolor="#000000"><img src="images/sp.gif" height="1" width="1"/></td>
							</tr>
						</table>
					</td>
				</tr>
				<!--End black line -->
			</table>
		</form>
	</body>
</html>