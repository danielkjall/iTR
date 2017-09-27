<%@ page session="true"%>
<%@ page import="com.intiro.itr.ITRResources, com.intiro.itr.util.personalization.*,com.intiro.itr.logic.weekreport.*"%>
<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.addHeader("Cache-Control","no-store");
    UserProfile userProfile = (UserProfile) session.getAttribute(ITRResources.ITR_USER_PROFILE);
    WeekReport aWeek = (WeekReport) session.getAttribute(ITRResources.ITR_WEEK_REPORT);
    String mode = (String)request.getAttribute("mode");
    
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
<head>
	<link REL="stylesheet" TYPE="text/css" HREF="include/ITR.CSS"/>
	<title>ITR - View/Approve Week</title>
</head>
<body bgcolor="#f3f3f3" leftmargin="0" rightmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<form action="" name="rejectAccept" method="post">
	<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<tr bgcolor="#aaaacc" align="center">
			<td class="Title1">
			<%		 
			if (aWeek.getMode().equalsIgnoreCase("approve") && !mode.equalsIgnoreCase("View")) out.write("Approve");
	    	else out.write("View");	
	    	%> week (<%=aWeek.getUserProfile().getFirstName()+" "+aWeek.getUserProfile().getLastName()%>)</td>
		</tr>
	</table>
	<br/>
	<div align="right">
		<table border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td colspan="3" bgcolor="#000000"><img src="images/blackLight.gif" height="1" width="1"/></td>
			</tr>
			<tr>
				<td bgcolor="#000000"><img src="images/sp.gif" height="1" width="1"/></td>
				<td>
					<table border="0" cellspacing="0" cellpadding="1" bgcolor="#ffffff">
						<%
						if(aWeek.getMode().equalsIgnoreCase("approve") && !mode.equalsIgnoreCase("View")) {%>
								<tr>
									<td nowrap="true" class="NormalBold" align="left">&#160;User:</td>
									<td nowrap="true" class="Normal" colspan="3" align="left">
										<%=aWeek.getUserProfile().getFirstName()%> <%=aWeek.getUserProfile().getLastName()%></td>
								</tr>
						<%}%>
						<tr>
							<td nowrap="true" class="NormalBold" align="left">&#160;Date:</td>
							<td nowrap="true" class="Normal" align="left">
								<%=aWeek.getFromDate().getMonthNameShort()%> <%=aWeek.getFromDate().getYear()%>
							</td>
							<td nowrap="true" class="NormalBold" >&#160;Week:</td>
							<td nowrap="true" class="Normal" align="left">
								<%=aWeek.getFromDate().getWeekOfYear()%> (<%=aWeek.getFromDate().getWeekPart()%>)&#160;
							</td>
						</tr>
						<tr>
							<td nowrap="true" class="NormalBold" align="left">&#160;Range:</td>
							<td nowrap="true" colspan="3" class="Normal">
							<%=aWeek.getFromDate().getCalendarInStoreFormat()%> - <%=aWeek.getToDate().getCalendarInStoreFormat()%>&#160;
							</td>
						</tr>
					</table>
				</td>
				<td bgcolor="#000000"><img src="images/blackLight.gif" height="1" width="1"/></td>
			</tr>
			<tr>
				<td colspan="3" bgcolor="#000000"><img src="images/blackLight.gif" height="1" width="1"/></td>
			</tr>
		</table>
	</div>
	<br/>
	<table width="100%" border="0" cellpadding="1" cellspacing="2">
		<tr>
			<td colspan="11">
				<table border="0" cellpadding="0" cellspacing="0" width="100%">
					<tr><td bgcolor="#000000"><img src="images/sp.gif" height="1" width="1"/></td></tr>
				</table>
			</td>
		</tr>
		<tr class="NormalBold">
			<td width="15%" BGCOLOR="#C4C4C4">Project Code</td>
			<td width="30%" BGCOLOR="#C4C4C4">Description</td>
			<td width="10%" BGCOLOR="#C4C4C4">Time Type</td>
			<td width="5%" align="right" BGCOLOR="#C4C4C4">Mon</td>
			<td width="5%" align="right" BGCOLOR="#C4C4C4">Tue</td>
			<td width="5%" align="right" BGCOLOR="#C4C4C4">Wed</td>
			<td width="5%" align="right" BGCOLOR="#C4C4C4">Thu</td>
			<td width="5%" align="right" BGCOLOR="#C4C4C4">Fri</td>
			<td width="5%" align="right" BGCOLOR="#FF2222">Sat</td>
			<td width="5%" align="right" BGCOLOR="#FF2222">Sun</td>
			<td width="5%" align="right" BGCOLOR="#C4C4C4">Total</td>
		</tr>
		<%
		for(int i=0; i<aWeek.getRows().size(); i++) {
			Row aRow = (Row) aWeek.getRow(i);
		%>
			<tr	<%if(i%2==0) out.write("bgcolor='#fafafa'");%>>
				<td>
					<%=aRow.getProject().getProjectCode()%>-<%=aRow.getProject().getProjectActivity().getCode()%>
				</td>
				<td class="Normal">
					<%=aRow.getProject().getProjectName()%>&#32;(<%=aRow.getProject().getProjectActivity().getDescription()%>)</td>
				<td class="Normal">
					<%=aRow.getTimeType()%>
				</td>
				<td align="right" class="Normal">
					<%if(aRow.getMonday() != 0) out.write(""+aRow.getMonday());%>
				</td>
				<td align="right" class="Normal">
					<%if(aRow.getTuesday() != 0) out.write(""+aRow.getTuesday());%>				
				</td>
				<td align="right" class="Normal">
					<%if(aRow.getWednesday() != 0) out.write(""+aRow.getWednesday());%>
				</td>
				<td align="right" class="Normal">
					<%if(aRow.getThursday() != 0) out.write(""+aRow.getThursday());%>
				</td>
				<td align="right" class="Normal">
					<%if(aRow.getFriday() != 0) out.write(""+aRow.getFriday());%>
				</td>
				<td align="right" class="Normal">
					<%if(aRow.getSaturday() != 0) out.write(""+aRow.getSaturday());%>
				</td>
				<td align="right" class="Normal">
					<%if(aRow.getSunday() != 0) out.write(""+aRow.getSunday());%>
				</td>
				<td align="right" class="NormalBold">
					<%=aRow.getRowSum()%>
				</td>
			</tr>
		<%}%>
		<tr>
			<td colspan="11">&#160;</td>
		</tr>
		<tr bgcolor="#E5E5E5">
			<td nowrap="true" class="NormalBold">Total</td>
			<td/>
			<td/>
			<td nowrap="true" align="right" class="NormalBold">
				<%=aWeek.getSumRows().getTotalRow().getMonday()%>
			</td>
			<td nowrap="true" align="right" class="NormalBold">
				<%=aWeek.getSumRows().getTotalRow().getTuesday()%>
			</td>
			<td nowrap="true" align="right" class="NormalBold">
				<%=aWeek.getSumRows().getTotalRow().getWednesday()%>
			</td>
			<td nowrap="true" align="right" class="NormalBold">
				<%=aWeek.getSumRows().getTotalRow().getThursday()%>
			</td>
			<td nowrap="true" align="right" class="NormalBold">
				<%=aWeek.getSumRows().getTotalRow().getFriday()%>
			</td>
			<td nowrap="true" align="right" class="NormalBold">
				<%=aWeek.getSumRows().getTotalRow().getSaturday()%>
			</td>
			<td nowrap="true" align="right" class="NormalBold">
				<%=aWeek.getSumRows().getTotalRow().getSunday()%>
			</td>
			<td nowrap="true" align="right" class="NormalBold">
				<%=aWeek.getSumRows().getTotalRow().getRowSum()%>
			</td>
		</tr>
		<tr bgcolor="#E5E5E5">
			<td nowrap="true" class="NormalBold">Expected Hours</td>
			<td/>
			<td/>
			<td nowrap="true" align="right" class="NormalBold">
				<%=aWeek.getSumRows().getExpectedRow().getMonday()%>
			</td>
			<td nowrap="true" align="right" class="NormalBold">
				<%=aWeek.getSumRows().getExpectedRow().getTuesday()%>
			</td>
			<td nowrap="true" align="right" class="NormalBold">
				<%=aWeek.getSumRows().getExpectedRow().getWednesday()%>
			</td>
			<td nowrap="true" align="right" class="NormalBold">
				<%=aWeek.getSumRows().getExpectedRow().getThursday()%>
			</td>
			<td nowrap="true" align="right" class="NormalBold">
				<%=aWeek.getSumRows().getExpectedRow().getFriday()%>
			</td>
			<td nowrap="true" align="right" class="NormalBold">
				<%=aWeek.getSumRows().getExpectedRow().getSaturday()%>
			</td>
			<td nowrap="true" align="right" class="NormalBold">
				<%=aWeek.getSumRows().getExpectedRow().getSunday()%>
			</td>
			<td nowrap="true" align="right" class="NormalBold">
				<%=aWeek.getSumRows().getExpectedRow().getRowSum()%>
			</td>
		</tr>
		<tr bgcolor="#E5E5E5">
			<td nowrap="true" class="NormalBold">Difference</td>
			<td/>
			<td/>
			<td nowrap="true" align="right" class="NormalBold">
				<%=aWeek.getSumRows().getDifferenceRow().getMonday()%>
			</td>
			<td nowrap="true" align="right" class="NormalBold">
				<%=aWeek.getSumRows().getDifferenceRow().getTuesday()%>
			</td>
			<td nowrap="true" align="right" class="NormalBold">
				<%=aWeek.getSumRows().getDifferenceRow().getWednesday()%>
			</td>
			<td nowrap="true" align="right" class="NormalBold">
				<%=aWeek.getSumRows().getDifferenceRow().getThursday()%>
			</td>
			<td nowrap="true" align="right" class="NormalBold">
				<%=aWeek.getSumRows().getDifferenceRow().getFriday()%>
			</td>
			<td nowrap="true" align="right" class="NormalBold">
				<%=aWeek.getSumRows().getDifferenceRow().getSaturday()%>
			</td>
			<td nowrap="true" align="right" class="NormalBold">
				<%=aWeek.getSumRows().getDifferenceRow().getSunday()%>
			</td>
			<td nowrap="true" align="right" class="NormalBold">
				<%=aWeek.getSumRows().getDifferenceRow().getRowSum()%>
			</td>
		</tr>
		<tr>
			<td colspan="11">
				<table border="0" cellpadding="0" cellspacing="0" width="100%">
					<tr><td bgcolor="#000000"><img src="images/sp.gif" height="1" width="1"/></td></tr>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan="11">&#160;</td>
		</tr>
		<tr>
			<td colspan="11">
				<table border="0" width="100%" cellspacing="0" cellpadding="0">
					<tr>
						<td nowrap="true" valign="top" class="NormalBold" width="1%">Week comment:&#160;</td>
						<!-- It has to be possible to edit the week comment when doing accept/reject!!-->
						<td nowrap="true">
							<textarea class="input" id="comments" name="comments" rows="15" cols="60"><%=aWeek.getWeekComment()%></textarea>
						</td>
						<td valign="top">
                                                    <%
                                                    if(aWeek.getMode().equalsIgnoreCase("approve") || mode.equalsIgnoreCase("View")) {
                                                    %>
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td colspan="3" bgcolor="#000000"><img src="images/blackLight.gif" height="1" width="1"/></td>
						</tr>
						<tr>
							<td bgcolor="#000000"><img src="images/sp.gif" height="1" width="1"/></td>
							<td>
								<table border="0" width="100%" bgcolor="#E5E5E5" cellspacing="0" cellpadding="0">
									<tr>
										<td align="center" height="40">
                                                                                    <% 
                                                                                    if(!mode.equalsIgnoreCase("View"))
                                                                                        {
                                                                                    %>
											<input class="input" type="button" name="btnApprove" value="Approve" onclick="JavaScript: document.location.href=('approveWeekResult.jsp?btnApprove=Approve&comments=' + escape(document.rejectAccept.comments.value))">
		 									&#160;&#160;					
                                                                                        <%}%>
											<input class="input" type="button" name="btnReject" value="Reject"  onclick="JavaScript: document.location.href=('approveWeekResult.jsp?btnReject=Reject&comments=' + escape(document.rejectAccept.comments.value))">
											&#160;&#160;
											<input class="input" type="button" name="btnCancel" value="Cancel" onclick="window.location.href='mainFrame.jsp'"/>
										</td>
									</tr>
								</table>
							</td>
							<td bgcolor="#000000"><img src="images/blackLight.gif" height="1" width="1"/></td>
						</tr>
						<tr>
							<td colspan="3" bgcolor="#000000"><img src="images/blackLight.gif" height="1" width="1"/></td>
						</tr>
					</table>
                                        <%}%>
                                                    &#160;</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</form>
	</body>
</html>