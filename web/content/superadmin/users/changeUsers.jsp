<%@ page session="true"%>
<%@ page import="com.intiro.itr.logic.email.Email, com.intiro.itr.logic.phone.PhoneNumber, com.intiro.itr.util.xml.XMLCombo.Entry, com.intiro.itr.ITRResources, com.intiro.itr.util.personalization.*, com.intiro.itr.logic.superadmin.users.UserEdit"%>
<%
      UserProfile userProfile = ( UserProfile )session.getAttribute( ITRResources.ITR_USER_PROFILE );

      //START ACTION, Find out what action is called and perform it
      String edit = request.getParameter( "btnEdit" );
      String add = request.getParameter( "btnAdd" );
      String delete = request.getParameter( "btnDelete" );
      String view = request.getParameter( "btnView" );
      String activate = request.getParameter( "btnActivate" );
      boolean bAdd = false;
      boolean bActivate = false;

      //Find out what button was pressed
      String mode = "error";

      if ( edit != null && edit.length() > 0 ) {
        mode = edit.trim();
      } else if ( add != null && add.length() > 0 ) {
        mode = add.trim();
        bAdd = true;
      } else if ( delete != null && delete.length() > 0 ) {
        mode = delete.trim();
      } else if ( view != null && view.length() > 0 ) {
        mode = view.trim();
      } else if ( activate != null && activate.length() > 0 ) {
        mode = activate.trim();
        bActivate = true;
      }

      //Fetch userid
      String userId = "-1";

      if ( bActivate ) {
      	// if user have clicked Activate button. Put him in Edit mode and let him activate the user manually.
        userId = request.getParameter( "deactivateduser" );
        mode = "Edit";
      } else if ( !bAdd ) {
        userId = request.getParameter( "activateduser" );
      }

      //Create xmlCarrier
      UserEdit xmlCarrier = new UserEdit( userProfile, mode, userId );

      //Set modified user in session
      session.setAttribute( ITRResources.ITR_MODIFIED_USER, xmlCarrier );
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
	<head>
		<meta HTTP-EQUIV="Content-Type" CONTENT="text/html;CHARSET=iso-8859-1"/>
		<script type="text/javascript">
		<%@ include file="../../../helpers/en_validate.js" %>
		function validate(theForm)  {
			//firstName
		         if (!isNotEmpty(theForm.firstname, "First name"))
		            return false;
		   	  if (!isValid(theForm.firstname, "First name"))
		            return false;
		
			//last Name
			  if (!isNotEmpty(theForm.lastname, "Last name"))
		            return false;
			  if (!isValid(theForm.lastname, "Last name"))
		            return false;
		
			//Roles
		        if ( !isSelected(theForm.role, "Role") )
		            return false;
			  
			//skin
			 if ( !isSelected(theForm.skin, "Skin") )
		            return false;
			 
			//language
		        if ( !isSelected(theForm.language, "Language") )
		            return false;
			
			//company
		        if ( !isSelected(theForm.company, "Company") )
		            return false;
			
			//Approver of reports
		        if ( !isSelected(theForm.adminuser, "Approver") )
		            return false;
		
			//password Both must match
			 if(theForm.newpassword.value != theForm.confirmpassword.value) {
				alert("The passwords must match each other");
			       return false;
			}
		
		       if (!isValid(theForm.newpassword, "New password"))
		            return false;	 
		
		       if (!isValid(theForm.confirmpassword, "Confirm password"))
		            return false;	 
		
		
			//changeLoginId
		       if (!isNotEmpty(theForm.newloginId, "Change login id"))
		            return false;	 
		       if (!isValid(theForm.newloginId, "Change login id"))
		            return false;	 
		        
			//yearly vacation
		       if (!isNotEmpty(theForm.yearlyvacation, "Yearly vacation"))
		            return false;	 
		       if (!isNumeric(theForm.yearlyvacation, "Yearly vacation"))
		            return false;	 
		
		
			//Saved vacation
		       if (!isNotEmpty(theForm.savedvacation, "Saved vacation"))
		            return false;	 
		       if (!isNumeric(theForm.savedvacation, "Saved vacation"))
		            return false;	 
		
		
			//Used vacation
		       if (!isNotEmpty(theForm.usedvacation, "Used vacation"))
		            return false;	 
		       if (!isNumeric(theForm.usedvacation, "Used vacation"))
		            return false;	 
		
			//OT money
		       if (!isNotEmpty(theForm.moneyovertime, "Money overtime"))
		            return false;	 
		       if (!isNumeric(theForm.moneyovertime, "Money overtime"))
		            return false;	 
		
			//OT vacation
		       if (!isNotEmpty(theForm.vacationovertime, "Vacation overtime"))
		            return false;	 
		       if (!isNumeric(theForm.vacationovertime, "Vacation overtime"))
		            return false;	 
		
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
					<td class="Title1"><%=xmlCarrier.getTitle()%> User</td>
				</tr>
			</table>
			<form method="POST" action="changeUsersResult.jsp" name="personalinfo" onsubmit="return validate(this)">
				<p>
					<table cellspancing="5" cellpadding="5" border="0" width="800">
						<tr>
							<td height="30" align="right" valign="bottom" colspan="2" class="NormalBold">|<a href="OnlineHelp">Help</a>|</td>
						</tr>
						
						<!--Start grey line-->
						<tr><td colspan="2"><table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td bgcolor="#cccccc"><img src="images/sp.gif" height="1" width="1"/></td></tr></table></td></tr>
						<!--End grey line -->

						<tr>
							<td align="left" valign="top">
								<table cellspancing="0" cellpadding="0" border="0" width="400">
									<tr>
										<td align="left" colspan="2" class="NormalBold">User Profile</td>
									</tr>
									<tr>
										<td align="center" colspan="2">&#160;</td>
									</tr>
									<tr>
										<td align="left" width="25%"> First name 
											<input type="hidden" name="userid" value="<%=xmlCarrier.getModifiedUser().getUserId()%>"/>
										</td>
										<td align="left" width="75%">
											<%if(xmlCarrier.isInAddMode() || xmlCarrier.isInEditMode()) {%>
												<input type="text" name="firstname" size="20" maxlength="20" class="input" value="<%=xmlCarrier.getModifiedUser().getFirstName()%>"/>
											<%}%>
											<%if(xmlCarrier.isInViewMode() || xmlCarrier.isInDeleteMode()) {%>
												<%=xmlCarrier.getModifiedUser().getFirstName()%>
											<%}%>
										</td>
									</tr>
									<tr>
										<td align="left" width="25%"> Last name </td>
										<td align="left" width="75%">
											<%if(xmlCarrier.isInAddMode() || xmlCarrier.isInEditMode()) {%>
												<input type="text" name="lastname" size="20" maxlength="20" class="input" value="<%=xmlCarrier.getModifiedUser().getLastName()%>"/>
											<%}%>
											<%if(xmlCarrier.isInViewMode() || xmlCarrier.isInDeleteMode()) {%>
												<%=xmlCarrier.getModifiedUser().getLastName()%>
											<%}%>
										</td>
									</tr>
									<tr>
										<td align="left" width="25%"> Role </td>
										<td align="left" width="75%">
											<%
											String readonly = "";
											if(xmlCarrier.isInViewMode() || xmlCarrier.isInDeleteMode()) {
												readonly = "disabled";
											}
											%>
											<select name="role" <%=readonly%> class="input">
												<%
												for(int i=0;i<xmlCarrier.getRolesCombo().getEntries().size(); i++) {
													Entry anEntry = (Entry)xmlCarrier.getRolesCombo().getEntries().get(i);
												%>
													<option	<%if(anEntry.isSelected()) {out.write("selected");}%> <%=" value=\""+anEntry.getValue()+"\"" %>>
														<%=anEntry.getText()%>
													</option>
												<%}%>
											</select>													
										</td>
									</tr>
									<tr>
										<td align="left" width="25%"> Language </td>
										<td align="left" width="75%">
											<%
											readonly = "";
											if(xmlCarrier.isInViewMode() || xmlCarrier.isInDeleteMode()) {
												readonly = "disabled";
											}
											%>
											<select name="language" <%=readonly%> class="input">
												<%
												for(int i=0;i<xmlCarrier.getLanguageCombo().getEntries().size(); i++) {
													Entry anEntry = (Entry)xmlCarrier.getLanguageCombo().getEntries().get(i);
												%>
													<option	<%if(anEntry.isSelected()) {out.write("selected");}%> <%=" value=\""+anEntry.getValue()+"\"" %>>
														<%=anEntry.getText()%>
													</option>
												<%}%>
											</select>													
										</td>
									</tr>
									<tr>
										<td align="left" width="25%"> Skin </td>
										<td align="left" width="75%">
											<%
											readonly = "";
											if(xmlCarrier.isInViewMode() || xmlCarrier.isInDeleteMode()) {
												readonly = "disabled";
											}
											%>
											<select name="skin" <%=readonly%> class="input">
												<%
												for(int i=0;i<xmlCarrier.getSkinsCombo().getEntries().size(); i++) {
													Entry anEntry = (Entry)xmlCarrier.getSkinsCombo().getEntries().get(i);
												%>
													<option	<%if(anEntry.isSelected()) {out.write("selected");}%> <%=" value=\""+anEntry.getValue()+"\"" %>>
														<%=anEntry.getText()%>
													</option>
												<%}%>
											</select>
										</td>
									</tr>
									<tr>
										<td align="left" width="25%"> Company </td>
										<td align="left" width="75%">
											<%
											readonly = "";
											if(xmlCarrier.isInViewMode() || xmlCarrier.isInDeleteMode()) {
												readonly = "disabled";
											}
											%>
											<select name="company" <%=readonly%> class="input">
												<%
												for(int i=0;i<xmlCarrier.getCompanyCombo().getEntries().size(); i++) {
													Entry anEntry = (Entry)xmlCarrier.getCompanyCombo().getEntries().get(i);
												%>
													<option	<%if(anEntry.isSelected()) {out.write("selected");}%> <%=" value=\""+anEntry.getValue()+"\"" %>>
														<%=anEntry.getText()%>
													</option>
												<%}%>
											</select>
										</td>
									</tr>
									<tr>
										<td align="left" width="25%"> Approver of reports </td>
										<td align="left" width="75%">
											<%
											readonly = "";
											if(xmlCarrier.isInViewMode() || xmlCarrier.isInDeleteMode()) {
												readonly = "disabled";
											}
											%>
											<select name="adminuser" <%=readonly%> class="input">
												<%
												for(int i=0;i<xmlCarrier.getApproverCombo().getEntries().size(); i++) {
													Entry anEntry = (Entry)xmlCarrier.getApproverCombo().getEntries().get(i);
												%>
													<option	<%if(anEntry.isSelected()) {out.write("selected");}%> <%=" value=\""+anEntry.getValue()+"\"" %>>
														<%=anEntry.getText()%>
													</option>
												<%}%>
											</select>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table cellspancing="0" cellpadding="0" border="0" width="400">
									<tr>
										<td align="left" colspan="2" class="NormalBold">System Access </td>
									</tr>
									<tr>
										<td align="center" colspan="2">&#160;</td>
									</tr>
									<tr>
										<td align="left" width="25%"> Login id </td>
										<td width="75%" align="left">
											<%if(xmlCarrier.isInAddMode() || xmlCarrier.isInEditMode()) {%>
												<input type="text" name="newloginId" size="10" maxlength="10" class="input" value="<%=xmlCarrier.getModifiedUser().getLoginId()%>"/>
											<%}%>
											<%if(xmlCarrier.isInViewMode() || xmlCarrier.isInDeleteMode()) {%>
												<%=xmlCarrier.getModifiedUser().getLoginId()%>
											<%}%>
										</td>
									</tr>
									<%if(xmlCarrier.isInAddMode() || xmlCarrier.isInEditMode()) {%>
										<tr>
											<td align="left" width="25%"> New password:</td>
											<td width="75%" align="left">
												<input type="password" name="newpassword" size="10" maxlength="10" value="" class="input"/>
											</td>
										</tr>
										<tr>
											<td align="left" width="25%"> Confirm password:</td>
											<td width="75%" align="left">
												<input type="password" name="confirmpassword" size="10" maxlength="10" value="" class="input"/>
											</td>
										</tr>
									<%}%>
									<tr>
										<td align="left" width="25%"> Activated </td>
										<td align="left" width="75%">
											<%
											String checked = "";
											readonly = "";
											if(xmlCarrier.getModifiedUser().getActivated()) {
												checked = "checked";
											}
											if(xmlCarrier.isInViewMode() || xmlCarrier.isInDeleteMode()) {
												readonly = "disabled";
											}
											%>
											<input type="checkbox" name="activated" class="input" <%=checked%> <%=readonly%> />
										</td>
									</tr>
									<tr>
										<td align="left" width="25%"> Activation date </td>
										<td align="left" width="75%">
											<%=xmlCarrier.getModifiedUser().getActivatedDate().getCalendarInStoreFormat()%>
										</td>
									</tr>
									<tr>
										<td align="left" width="25%"> Deactivated date </td>
										<td align="left" width="75%">
											<%=xmlCarrier.getModifiedUser().getDeActivatedDate().getCalendarInStoreFormat()%>
										</td>
									</tr>
									<tr>
										<td align="left" width="25%"> Created date </td>
										<td align="left" width="75%">
											<%=xmlCarrier.getModifiedUser().getCreatedDate().getCalendarInStoreFormat()%>
										</td>
									</tr>

									<tr><td align="center" colspan="2">&#160;</td></tr>

								</table>
							</td>
						</tr>

						<!--Start grey line-->
						<tr><td colspan="2"><table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td bgcolor="#cccccc"><img src="images/sp.gif" height="1" width="1"/></td></tr></table></td></tr>
						<!--End grey line -->

						<tr>
							<td align="right" valign="top">
								<table cellspancing="0" cellpadding="0" border="0" width="400">
									<tr>
										<td align="left" colspan="2" class="NormalBold">Vacation</td>
									</tr>
									<tr>
										<td align="center" colspan="2">&#160;</td>
									</tr>
									<tr>
										<td align="left" width="25%"> Yearly vacation </td>
										<td align="left" width="75%">
											<%if(xmlCarrier.isInAddMode() || xmlCarrier.isInEditMode()) {
											%>+ <input type="text" name="yearlyvacation" size="4" maxlength="4" class="input" value="<%=xmlCarrier.getModifiedUser().getDefaultVacationDays()%>"/> days
											<%}
											else {
											%>+ <%=xmlCarrier.getModifiedUser().getDefaultVacationDays()%> days
											<%}%>
										</td>
									</tr>
									<tr>
										<td align="left" width="25%"> Saved vacation </td>
										<td align="left" width="75%">
											<%if(xmlCarrier.isInAddMode() || xmlCarrier.isInEditMode()) {
											%>+ <input type="text" name="savedvacation" size="4" maxlength="4" class="input" value="<%=xmlCarrier.getModifiedUser().getSavedVacationDays()%>"/> days
											<%}
											else {
											%>+ <%=xmlCarrier.getModifiedUser().getSavedVacationDays()%> days
											<%}%>
										</td>
									</tr>
									<tr>
										<td align="left" width="25%"> Used vacation </td>
										<td align="left" width="75%">
											<%if(xmlCarrier.isInAddMode() || xmlCarrier.isInEditMode()) {
											%>-&#160;&#160;<input type="text" name="usedvacation" size="4" maxlength="4" class="input" value="<%=xmlCarrier.getModifiedUser().getUsedVacationDays()%>"/> days
											<%}
											else {
											%>-&#160;&#160;<%=xmlCarrier.getModifiedUser().getUsedVacationDays()%> days
											<%}%>
										</td>
									</tr>
									<tr>
										<td colspan="2">
											<table border="0" cellpadding="0" cellspacing="0" width="100%">
												<tr>
													<td width="25%"></td>
													<td bgcolor="#cccccc" width="20%">
														<img src="images/sp.gif" height="1" width="1"/>
													</td>
													<td width="55%"></td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td align="left" width="25%"> Remaining vacation </td>
										<td align="left" width="75%">= <%=xmlCarrier.getModifiedUser().getRemainingVacationDays()%> days
										</td>
									</tr>
									<tr>
										<td align="center" colspan="2">&#160;</td>
									</tr>
								</table>
							</td>
							<td valign="top">
								<table cellspancing="0" cellpadding="0" border="0" width="400">
									<tr>
										<td align="left" colspan="2" class="NormalBold">Overtime</td>
									</tr>
									<tr>
										<td align="center" colspan="2">&#160;</td>
									</tr>
									<tr>
										<td align="left" width="25%"> OT money </td>
										<td align="left" width="75%">
											<%if(xmlCarrier.isInAddMode() || xmlCarrier.isInEditMode()) {
											%>+ <input type="text" name="moneyovertime" size="4" maxlength="4" class="input" value="<%=xmlCarrier.getModifiedUser().getOvertimeMoneyHours()%>"/> hours
											<%}
											else {
											%>+ <%=xmlCarrier.getModifiedUser().getOvertimeMoneyHours()%> hours
											<%}%>
										</td>
									</tr>
									<tr>
										<td align="left" width="25%">OT vacation </td>
										<td align="left" width="75%">
											<%if(xmlCarrier.isInAddMode() || xmlCarrier.isInEditMode()) {
											%>+ <input type="text" name="vacationovertime" size="4" maxlength="4" class="input" value="<%=xmlCarrier.getModifiedUser().getOvertimeVacationHours()%>"/> hours
											<%}
											else {
											%>+ <%=xmlCarrier.getModifiedUser().getOvertimeVacationHours()%> hours
											<%}%>
										</td>
									</tr>
									<tr>
										<td colspan="2">
											<table border="0" cellpadding="0" cellspacing="0" width="100%">
												<tr>
													<td width="25%"></td>
													<td bgcolor="#cccccc" width="20%">
														<img src="images/sp.gif" height="1" width="1"/>
													</td>
													<td width="55%"></td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td align="left" width="25%">OT Total </td>
										<%
										double totalHours = xmlCarrier.getModifiedUser().getOvertimeMoneyHours() + xmlCarrier.getModifiedUser().getOvertimeMoneyHours();
										%>
										<td align="left" width="75%">= <%=totalHours%> hours
										</td>
									</tr>
								</table>
							</td>
						</tr>

						<!--Start grey line-->
						<tr><td colspan="2"><table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td bgcolor="#cccccc"><img src="images/sp.gif" height="1" width="1"/></td></tr></table></td></tr>
						<!--End grey line -->

						<tr>
							<td valign="top">
								<table cellspancing="0" cellpadding="0" border="0" width="400">
									<tr>
										<td align="left" colspan="2" class="NormalBold">Phone numbers</td>
									</tr>
									<tr>
										<td align="center" colspan="2">&#160;</td>
									</tr>
									<%
									for(int i=0; i<xmlCarrier.getModifiedUser().getPhoneNumbers().size();i++) {
										PhoneNumber aPhone = xmlCarrier.getModifiedUser().getPhoneNumber(i);
									%>
									<tr>
										<td align="left" width="25%">
											<%=aPhone.getPhoneDescription()%>
										</td>
										<td align="left" width="75%">
											<%=aPhone.getRegion().getCountry().getCountryCode()%>&#160;<%=aPhone.getRegion().getRegionCode()%>&#160;-&#160;<%=aPhone.getPhoneNumber()%>
										</td>
									</tr>
									<%
									}
									%>

									<tr><td colspan="2">&#160;</td></tr>
									
								</table>
							</td>
							<td valign="top">
								<table cellspancing="0" cellpadding="0" border="0" width="400">
									<tr>
										<td align="left" colspan="2" class="NormalBold">Email addresses</td>
									</tr>
									<tr>
										<td align="center" colspan="2">&#160;</td>
									</tr>
									<%
									for(int i=0; i<xmlCarrier.getModifiedUser().getEmails().size();i++) {
										Email anEmail = xmlCarrier.getModifiedUser().getEmail(i);
									%>
									<tr>
										<td align="left" width="25%">
											<%=anEmail.getDescription()%>
										</td>
										<td align="left" width="75%">
											<%=anEmail.getAddress()%>&#160;
										</td>
									</tr>
									<%
									}
									%>
									<tr><td colspan="2">&#160;</td></tr>
									<tr><td align="center" colspan="2">&#160;</td></tr>
								</table>
							</td>
						</tr>

                                                                        
                                                                        
                                                                        
                                                                        
                                                                        
                                                                        
                                                                        
                                                                        
                                                <!--Start grey line-->
						<tr><td colspan="2"><table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td bgcolor="#cccccc"><img src="images/sp.gif" height="1" width="1"/></td></tr></table></td></tr>
						<!--End grey line -->

						<tr>
							<td valign="top">
								<table cellspancing="0" cellpadding="0" border="0" width="400">
									<tr>
										<td align="left" colspan="2" class="NormalBold">iTRGamification options</td>
									</tr>
									<tr>
										<td align="center" colspan="2">&#160;</td>
									</tr>
									<tr>
										<td align="left" width="100%">
											<%
											checked = "";
											readonly = "";
											if(xmlCarrier.getModifiedUser().getGamificationSetting().isWantReminderMail()) {
												checked = "checked";
											}
											if(xmlCarrier.isInViewMode() || xmlCarrier.isInDeleteMode()) {
												readonly = "disabled";
											}
											%>
                                                                                        <label><input type="checkbox" name="cbReminderMail" class="input" <%=checked%> <%=readonly%> />Want reminder mail</label>
										</td>
									</tr>
                                                                        <tr>
										<td align="left" width="100%">
											<%
											checked = "";
											readonly = "";
											if(xmlCarrier.getModifiedUser().getGamificationSetting().isWantLateMail()) {
												checked = "checked";
											}
											if(xmlCarrier.isInViewMode() || xmlCarrier.isInDeleteMode()) {
												readonly = "disabled";
											}
											%>
											<label><input type="checkbox" name="cbLateMail" class="input" <%=checked%> <%=readonly%> />Want late mail</label>
										</td>
									</tr>

                                                                        <tr>
										<td align="left" width="100%">
											<%
											checked = "";
											readonly = "";
											if(xmlCarrier.getModifiedUser().getGamificationSetting().isWantAchievementMail()) {
												checked = "checked";
											}
											if(xmlCarrier.isInViewMode() || xmlCarrier.isInDeleteMode()) {
												readonly = "disabled";
											}
											%>
                                                                                        <label><input type="checkbox" name="cbAchievementMail" class="input" <%=checked%> <%=readonly%> />Want achievement mail</label>
										</td>
									</tr>

                                                                        <tr>
										<td align="left" width="100%">
											<%
											checked = "";
											readonly = "";
											if(xmlCarrier.getModifiedUser().getGamificationSetting().isWantPointMail()) {
												checked = "checked";
											}
											if(xmlCarrier.isInViewMode() || xmlCarrier.isInDeleteMode()) {
												readonly = "disabled";
											}
											%>
											<label><input type="checkbox" name="cbPointMail" class="input" <%=checked%> <%=readonly%> />Want point mail</label>
										</td>
									</tr>
                                                        
                                                                        <tr>
										<td align="left" width="100%">
											Email: <input type="text" name="txtEmail" size="100" maxlength="200" class="input" value="<%=xmlCarrier.getModifiedUser().getGamificationSetting().getEmail()%>"/>&#160;
										</td>
									</tr>
                                                                                
                                                                        <tr><td colspan="2">&#160;</td></tr>
									
								</table>
							</td>
							<td valign="top">
								<table cellspancing="0" cellpadding="0" border="0" width="400">
									<tr><td align="center" colspan="2">&#160;</td></tr>
								</table>
							</td>
						</tr>
                                                                        
                                                                        
                                                                        
                                                                        
                                                                        
                                                                        
                                                                        
                                                                        
						<!--Start grey line-->
						<tr><td colspan="2"><table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td bgcolor="#cccccc"><img src="images/sp.gif" height="1" width="1"/></td></tr></table></td></tr>
						<!--End grey line -->

						<tr>
							<td align="right" colspan="2">
								<%
								if(xmlCarrier.isInDeleteMode()) {
								%>
									<input type="button" name="btnDelete" value=" Delete " class="input" onclick="window.location.href='changeUsersResult.jsp?btnDelete=Delete'"/>&#160;
								<%
								}
								if(xmlCarrier.isInAddMode() || xmlCarrier.isInEditMode()) {
								%>
									<input type="submit" name="btnSave" value=" Save " class="input"/>&#160;
								<%
								}
								%>
								<input type="button" name="Cancel" value=" Cancel " onclick="window.history.back(-1)" class="input"/>
							</td>
						</tr>
					</table>
				</p>
			</form>
		</center>
	</body>
</html>