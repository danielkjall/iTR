<%@ page session="true"%>
<%@ page import="com.intiro.itr.util.ITRCalendar, com.intiro.itr.ITRResources, com.intiro.itr.logic.generatereport.monthly.MonthlyReport, com.intiro.itr.logic.generatereport.monthly.MonthlyReport.ReportRow, com.intiro.itr.util.personalization.*"%>

<%
      String userId = request.getParameter("userid");
      if (userId == null || userId.equalsIgnoreCase("null")) {
        userId = "";
      }

      String projectId = request.getParameter("projectid");
      if (projectId == null || projectId.equalsIgnoreCase("null")) {
        projectId = "";
      }

      String projectCodeId = request.getParameter("projectcodeid");
      if (projectCodeId == null || projectCodeId.equalsIgnoreCase("null")) {
        projectCodeId = "";
      }

      String year = request.getParameter("year");
      String month = request.getParameter("month");
      String fromDate = "";
      String toDate = "";

      if (month == null || month.equalsIgnoreCase("null")) {
        ITRCalendar fromSelectedDate = new ITRCalendar(year + "-01-01");
        fromDate = fromSelectedDate.getCalendarInStoreFormat();

        ITRCalendar toSelectedDate = new ITRCalendar(year + "-12-31");
        toDate = toSelectedDate.getCalendarInStoreFormat();
      }
      else {
        ITRCalendar selectedDate = new ITRCalendar(year + "-" + month + "-01");
        fromDate = selectedDate.getCalendarInStoreFormat();
        selectedDate.nextMonth();
        toDate = selectedDate.getCalendarInStoreFormat();
      }

      UserProfile userProfile = (UserProfile) session.getAttribute(ITRResources.ITR_USER_PROFILE);
      MonthlyReport xmlCarrier = new MonthlyReport(userProfile, userId, projectId, projectCodeId, fromDate, toDate);
      xmlCarrier.load();
      
      String colspan = "7";
      if(xmlCarrier.getUserProfile().getRole().isAdmin() || xmlCarrier.getUserProfile().getRole().isSuperAdmin()) {
      	colspan = "9";
      }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
	<head>
		<link REL="stylesheet" TYPE="text/css" HREF="include/ITR.CSS"/>
		<meta HTTP-EQUIV="Content-Type" CONTENT="text/html;CHARSET=iso-8859-1"/>
		<title>ITR - General Report</title>
		<script type="text/javascript">
			function setProject(projectId)  {
				document.queryprofile.projectid.value = projectId;
				document.queryprofile.submit();
			}
			function setUser(userId)  {
				document.queryprofile.userid.value = userId;
				document.queryprofile.submit();
			}
			function setProjectCode(projectCodeId)  {
				document.queryprofile.projectcodeid.value = projectCodeId;
				document.queryprofile.submit();
			}
			function nextMonth()  {
				month = document.queryprofile.month.value;
				year = document.queryprofile.year.value;
				if(month == "null" || month == "") month = "01";
				else if(month == "01") month = "02";
				else if(month == "02") month = "03";
				else if(month == "03") month = "04";
				else if(month == "04") month = "05";
				else if(month == "05") month = "06";
				else if(month == "06") month = "07";
				else if(month == "07") month = "08";
				else if(month == "08") month = "09";
				else if(month == "09") month = "10";
				else if(month == "10") month = "11";
				else if(month == "11") month = "12";
				else if(month == "12") {
					month = "01";
					year = year -(-1);
				}
				document.queryprofile.month.value = month;
				document.queryprofile.year.value = year;
				document.queryprofile.submit();
			}
			function previousMonth()  {
				month = document.queryprofile.month.value;
				year = document.queryprofile.year.value;
				if(month == "null" || month == "") month = "12";
				else if(month == "01") {
					month = "12";
					year = year - 1;
				}
				else if(month == "02") month = "01";
				else if(month == "03") month = "02";
				else if(month == "04") month = "03";
				else if(month == "05") month = "04";
				else if(month == "06") month = "05";
				else if(month == "07") month = "06";
				else if(month == "08") month = "07";
				else if(month == "09") month = "08";
				else if(month == "10") month = "09";
				else if(month == "11") month = "10";
				else if(month == "12") month = "11";						
				document.queryprofile.month.value = month;
				document.queryprofile.year.value = year;
				document.queryprofile.submit();
			}
			function allMonths()  {
				document.queryprofile.month.value = "null";
				document.queryprofile.submit();
			}
		</script>
		<style>
			A {
			    COLOR: blue;
			    TEXT-DECORATION: none
			}
		</style>
	</head>
	<body bgcolor="#f3f3f3" leftmargin="0" rightmargin="0" topmargin="0" marginwidth="0" marginheight="0">
		<form method="POST" action="monthlyReport.jsp" name="queryprofile">
			<table border="0" cellpadding="0" cellspacing="0" width="100%">
				<tr bgcolor="#aaaacc" align="center">
					<td class="Title1">Monthly Report</td>
				</tr>
			</table>
			<input type="hidden" name="year" value="<%=year%>">
			<input type="hidden" name="month" value="<%=month%>">
			<input type="hidden" name="userid" value="<%=xmlCarrier.getUserId()%>">
			<input type="hidden" name="projectid" value="<%=xmlCarrier.getProjectId()%>">
			<input type="hidden" name="projectcodeid"  value="<%=xmlCarrier.getProjectCodeId()%>">
			<br/>
			<div align="right">
				<table border="0" cellpadding="0" cellspacing="0">
					<tr><td colspan="3" bgcolor="#000000"><img src="images/blackLight.gif" height="1" width="1"/></td></tr>
					<tr><td bgcolor="#000000"><img src="images/sp.gif" height="1" width="1"/></td><td>
							<table border="0" cellspacing="0" cellpadding="1" bgcolor="#ffffff">
								<tr>
									<td class="NormalBold">&#160;Year:</td>
									<td nowrap="true" class="Normal" >
										<%=year%>&#160;
									</td>
									<td class="NormalBold">&#160;Month:</td>
									<td nowrap="true" class="Normal">
										<%=(month.equalsIgnoreCase("null"))?"All months":month%>&#160; <a href="#" onClick="javascript:previousMonth();">(<)</a> <a href="#" onClick="javascript:nextMonth();">(>)</a> <a href="#" onClick="javascript:allMonths();">(All)</a>
									</td>
									<td class="NormalBold">&#160;User:</td>
									<td nowrap="true" class="Normal">
										<%=xmlCarrier.getUser()%>&#160;
									</td>
									<td class="NormalBold">&#160;Project:</td>
									<td nowrap="true" class="Normal">
										<%=xmlCarrier.getProject()%>&#160;
									</td>
									<td class="NormalBold">&#160;Project code:</td>
									<td nowrap="true" class="Normal">
										<%=xmlCarrier.getProjectCode()%>&#160;
											</td>
										</tr>
									</table>
								</td>
								<td bgcolor="#000000"><img src="images/blackLight.gif" height="1" width="1"/></td>
							</tr>
							<tr><td colspan="3" bgcolor="#000000"><img src="images/blackLight.gif" height="1" width="1"/></td></tr>
						</table>
					</div>
					<br/>
					
					<table WIDTH="100%" border="0" cellpadding="1" cellspacing="1">

						<!--Start black line-->
						<tr><td colspan="<%=colspan%>"><table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td bgcolor="#000000"><img src="images/sp.gif" height="1" width="1"/></td></tr></table></td></tr>
						<!--End black line -->

						<tr BGCOLOR="#C4C4C4" class="NormalBold">
							<td width="" nowrap="true">Project name</td>
							<td width="" nowrap="true">Name</td>
							<td width="" nowrap="true">Activity code</td>
							<td width="" nowrap="true">Time type</td>
							<%if(xmlCarrier.getUserProfile().getRole().isAdmin() || xmlCarrier.getUserProfile().getRole().isSuperAdmin()) {%>
								<td width="" nowrap="true" align="right">Rate</td>
							<%}%>
							<td width="" nowrap="true" align="right">Worked hours</td>
							<%if(xmlCarrier.getUserProfile().getRole().isAdmin() || xmlCarrier.getUserProfile().getRole().isSuperAdmin()) {%>
								<td width="" nowrap="true" align="right">Revenue</td>
							<%}%>
						</tr>
						<%
						for(int i=0;i<xmlCarrier.getRows().size();i++) {
							String bgColor = "";
							if(i%2==0) bgColor = "#fafafa";
							ReportRow aRow = (ReportRow) xmlCarrier.getRows().get(i);
						%>
						<tr bgcolor="<%=bgColor%>">
							<td class="Normal" nowrap="true">
								<a href="#" onClick="javascript:setProject('<%=aRow.getRowProjectId()%>');"><%=aRow.getRowProjectName()%></a>
							</td>
							<td class="Normal" nowrap="true">
								<a href="#" onClick="javascript:setUser('<%=aRow.getRowUserId()%>');"><%=aRow.getFirstName()%> <%=aRow.getRowLastName()%> (<%=aRow.getRowLoginId()%>)</a>
							</td>
							<td class="Normal" nowrap="true">
								<a href="#" onClick="javascript:setProjectCode('<%=aRow.getRowProjectCodeId()%>');"><%=aRow.getRowProjectCodeDesc()%> </a>
							</td>
							<td class="Normal" nowrap="true">
								<%=aRow.getRowTimeTypeDesc()%>									
							</td>
							<%if(xmlCarrier.getUserProfile().getRole().isAdmin() || xmlCarrier.getUserProfile().getRole().isSuperAdmin()) {%>
								<td class="Normal" nowrap="true" align="right"><%=aRow.getRowRate()%></td>
							<%}%>
							<td class="Normal" nowrap="true" align="right">
								<%=aRow.getRowSumWorkedHours()%>
							</td>
							<%if(xmlCarrier.getUserProfile().getRole().isAdmin() || xmlCarrier.getUserProfile().getRole().isSuperAdmin()) {%>
								<td class="Normal" nowrap="true" align="right"><%=aRow.getRowRevenue()%></td>
							<%}%>
					</tr>
				<%}%>

				<!--Start black line-->
				<tr><td colspan="<%=colspan%>"><table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td bgcolor="#000000"><img src="images/sp.gif" height="1" width="1"/></td></tr></table></td></tr>
				<!--End black line -->

				<tr>
					<td class="Normal" nowrap="true">
						<a href="#" onClick="javascript:setProject('')">All projects</a>
					</td>
					<td class="Normal" nowrap="true">
						<%
						//Only admin and super admin can see all users:
						if(userProfile.getRole().isAdmin() || userProfile.getRole().isSuperAdmin()){%>
							<a href="#" onClick="javascript:setUser('')">All users</a>
						<%}%>
					</td>
					<td class="Normal" nowrap="true">
						<a href="#" onClick="javascript:setProjectCode('')">All activity codes</a>
					</td>
					<td>&#160;</td>
					<%if(xmlCarrier.getUserProfile().getRole().isAdmin() || xmlCarrier.getUserProfile().getRole().isSuperAdmin()) {%>
						<td class="NormalBold" align="right"><%=xmlCarrier.getAverageRate()%></td>
					<%}%>
					<td class="NormalBold" align="right"><%=xmlCarrier.getSumWorkedHours()%></td>
					<%if(xmlCarrier.getUserProfile().getRole().isAdmin() || xmlCarrier.getUserProfile().getRole().isSuperAdmin()) {%>
						<td class="NormalBold" align="right"><%=xmlCarrier.getSumRevenue()%></td>
					<%}%>
				</tr>
				<tr>
					<td colspan="<%=colspan%>" class="NormalItalic">To narrow the search further, click on any of the entries in the first three columns.</td>
				</tr>
			</table>
		</form>
	</body>
</html>
