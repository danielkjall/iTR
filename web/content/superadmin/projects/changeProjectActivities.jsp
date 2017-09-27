<%@ page session="true"%>
<%@ page import="com.intiro.itr.util.xml.XMLCombo.Entry, com.intiro.itr.ITRResources, com.intiro.itr.util.personalization.*, com.intiro.itr.logic.superadmin.projects.ProjectActivitiesEditor, com.intiro.itr.logic.project.ProjectActivity, com.intiro.itr.logic.activity.Activity"%>
<%
	  response.setHeader("Cache-Control", "no-cache");
	  response.setHeader("Pragma", "no-cache");
	  response.addHeader("Cache-Control","no-store");
	  
	  UserProfile userProfile = (UserProfile) session.getAttribute(ITRResources.ITR_USER_PROFILE);
      ProjectActivitiesEditor xmlCarrier = new ProjectActivitiesEditor(userProfile, Integer.parseInt(request.getParameter("projid")));
      session.setAttribute(ITRResources.ITR_PROJECTACTIVITIES_EDITOR, xmlCarrier);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
	<head>
		<meta HTTP-EQUIV="Content-Type" CONTENT="text/html;CHARSET=iso-8859-1"/>
		<script type="text/javascript">
			<%@ include file="../../../helpers/en_validate.js" %>
			function move(fromList, toList, all) {
			    for (var i = 0; i < fromList.options.length; i++) {
			        if (fromList.options[i].selected == true || all == true) {
			            // move entry
			            moveEntryFromTo(fromList, toList, i);
			            i=-1;
			        }
			    }
			
			    return true;
			}
			
			function moveEntryFromTo(fromList, toList, index) {
			    var newValue = fromList.options[index].value;
			    var newText = fromList.options[index].text;
			    var newOption = new Option(newText, newValue, false, false);
			
			    insertSorted(toList, newOption);
			    fromList.options[index] = null;
			}

			function insertSorted(toList, newOption) {
			    var tmpOption = null;
			    for (var i = 0; i < toList.options.length; i++) {
			//        if (newOption.value < toList.options[i].value) {
			        if (newOption.text < toList.options[i].text) {
			            // Insert here
			            tmpOption = toList[i];
			            toList.options[i] = newOption;
			            newOption = tmpOption;
			        }
			    }
			    toList.options[toList.length] = newOption;
			}

			function getList(list) {
			    var retval = "";
			    for (var i = 0; i < list.options.length; i++) {
			        retval = retval + "," + list.options[i].value;
			    }
			    if (retval.length > 0) {
			        retval = retval.substring(1, retval.length);
			    }
			   return retval;
			}
			
			
			function getAndStoreActivities(theForm) {
			    theForm.availableList.value = getList(theForm.available);
			    theForm.assignedList.value = getList(theForm.assigned);
			
			    return true;
			}

		</script>
		<title>Change Profile</title>
		<link rel="stylesheet" href="include/ITR.CSS"/>
	</head>
	<body bgcolor="#eeeeee" topmargin="2" leftmargin="0" rightmargin="0" marginwidth="0" marginheight="0">
		<a name="top"/>
		<center>
			<table border="0" cellpadding="0" cellspacing="0" width="100%">
				<tr bgcolor="#aaaacc" align="center">
					<td class="Title1"><%=xmlCarrier.getTitle()%></td>
				</tr>
			</table>
			<form method="POST" action="changeProjectActivitiesResult.jsp" name="activityinfo" onsubmit="return getAndStoreActivities(this)">
				<p>
					<input type="hidden" name="availableList" value=""></input>
					<input type="hidden" name="assignedList" value=""></input>
					<table cellspacing="5" cellpadding="5" border="0" width="800">
						<tr>
							<td height="30" align="right" valign="bottom" colspan="2" class="NormalBold">|<a href="OnlineHelp">Help</a>|</td>
						</tr>

						<!--Start grey line-->
						<tr><td colspan="2"><table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td bgcolor="#cccccc"><img src="images/sp.gif" height="1" width="1"/></td></tr></table></td></tr>
						<!--End grey line -->

						<tr>
							<td align="left" valign="top">
								<table cellspacing="0" cellpadding="0" border="0" width="400">
									<tr>
										<td align="left" colspan="2" class="NormalBold">Project Profile</td>
									</tr>
									<tr>
										<td align="center" colspan="2">&#160;</td>
									</tr>
									<tr>
										<td align="left" width="25%"> Code 
											<input type="hidden" name="projectid" value="<%=xmlCarrier.getModifiedProject().getProjectId()%>"/>
										</td>
										<td align="left" width="75%">
											<%=xmlCarrier.getModifiedProject().getProjectCode()%>
										</td>
									</tr>

									<tr>
										<td align="left" width="25%"> Name</td>
										<td align="left" width="75%">
											<%=xmlCarrier.getModifiedProject().getProjectName()%>
										</td>
									</tr>

									<tr>
										<td align="left" width="25%"> Company </td>
										<td align="left" width="75%">
											<%
											for(int i=0;i<xmlCarrier.getCompanyCombo().getEntries().size(); i++) {
												Entry anEntry = (Entry)xmlCarrier.getCompanyCombo().getEntries().get(i);
											%>
												<%if(anEntry.isSelected()) {out.write(anEntry.getText());}%>
											<%
											}
											%>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table cellspacing="0" cellpadding="0" border="0" width="400">
									<tr>
										<td align="left" colspan="2" class="NormalBold"> Description</td>
									</tr>
									<tr>
										<td align="center" colspan="2">&#160;</td>
									</tr>
									<tr>
										<td align="left" width="100%" colspan="2">
											<%=xmlCarrier.getModifiedProject().getProjectDesc()%>
										</td>
									</tr>

									<tr><td align="center" colspan="2">&#160;</td></tr>

								</table>
							</td>
						</tr>

						<!--Start grey line-->
						<tr><td colspan="2"><table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td bgcolor="#cccccc"><img src="images/sp.gif" height="1" width="1"/></td></tr></table></td></tr>
						<!--End grey line -->

						<!--START - ACTIVITIES COMBOBOXES AND ARROWS-->
						<tr>
							<td colspan="2" align="center">
                                <table cellspacing="0" cellpadding="0" border="0">
                                    <tr>
                                        <td>
											<table cellspacing="0" cellpadding="0" border="0">
											    <tr>
													<td align="center" class="NormalBold">Available Activities</td>
											    </tr>
												<tr>
											          <td align="center">
											            <select size="7" style="width: 250px" name="available" class="input" multiple="true">
											            	<%
															for(int i=0;i<xmlCarrier.getAvailableActivities().size(); i++) {
																Activity anActivity = (Activity)xmlCarrier.getAvailableActivities().get(i);
															%>
																<option	value="<%=anActivity.getId()%>"><%=anActivity.getCode()%>, <%=anActivity.getDescription()%></option>
															<%}%>
											            </select>
											        </td>
											    </tr>
											</table>
							            </td>
							            <td>
											<table cellspacing="2" cellpadding="0" border="0" width="100">
											    <tr>
											        <td align="center" colspan="2">&#160;</td>
											    </tr>
											    <tr><td align="center">
											            <input type="button" style="width: 90px;height: 22px;" name="btnAddSelected" value=" Add " class="input" onclick="move(document.activityinfo.available, document.activityinfo.assigned, false)"/>
											    </td></tr>
											    <tr><td align="center">
											            <input type="button" style="width: 90px;height: 22px;" name="btnAddAll" value=" Add All " class="input" onclick="move(document.activityinfo.available, document.activityinfo.assigned, true)"/>
											    </td></tr>
											    <tr><td align="center">
											            <input type="button" style="width: 90px;height: 22px;" name="btnRemoveAll" value=" Remove All " class="input" onclick="move(document.activityinfo.assigned, document.activityinfo.available, true)"/>
											    </td></tr>
											    <tr><td align="center">
											            <input type="button" style="width: 90px;height: 22px;" name="btnRemoveSingle" value=" Remove " class="input" onclick="move(document.activityinfo.assigned, document.activityinfo.available, false)"/>
											    </td></tr>
											</table>
							            </td>
							            <td>
											<table cellspacing="0" cellpadding="0" border="0">
												<tr>
													<td align="center" class="NormalBold">Assigned Activities</td>
											    </tr>
												<tr>
													<td align="center">
											            <select size="7" style="width: 250px" name="assigned" class="input" multiple="true">
											            	<%
															for(int i=0;i<xmlCarrier.getAssignedActivities().size(); i++) {
																ProjectActivity anActivity = (ProjectActivity)xmlCarrier.getAssignedActivities().get(i);
															%>
																<option	value="<%=anActivity.getId()%>"><%=anActivity.getCode()%>, <%=anActivity.getDescription()%></option>
															<%}%>
											            </select>
											        </td>
											    </tr>
											</table>
							            </td>
									</tr>
								</table>
							</td>
						</tr>
						<!--END - ACTIVITIES COMBOBOXES AND ARROWS-->

						<!--Start grey line-->
						<tr><td colspan="2"><table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td bgcolor="#cccccc"><img src="images/sp.gif" height="1" width="1"/></td></tr></table></td></tr>
						<!--End grey line -->

						<tr>
							<td align="right" colspan="2">
								<input type="submit" name="btnSave" value=" Save " class="input"/>&#160;
								<input type="button" name="Cancel" value=" Cancel " onclick="window.history.back(-1)" class="input"/>
							</td>
						</tr>
					</table>
				</p>
			</form>
		</center>
	</body>
</html>