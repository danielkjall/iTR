<%@ page session="true"%>
<%@ page import="com.intiro.itr.logic.phone.PhoneNumber, com.intiro.itr.logic.email.Email, com.intiro.itr.util.xml.XMLCombo.Entry, com.intiro.itr.ITRResources, com.intiro.itr.util.personalization.*, com.intiro.itr.logic.superadmin.contacts.ContactEdit"%>
<%
	  UserProfile userProfile = ( UserProfile )session.getAttribute( ITRResources.ITR_USER_PROFILE );
      //START ACTION, Find out what action is called and perform it
      String edit = request.getParameter( "btnEdit" );
      String add = request.getParameter( "btnAdd" );
      String delete = request.getParameter( "btnDelete" );
      String view = request.getParameter( "btnView" );
      String activate = request.getParameter( "btnActivate" );

      //Find out what button was pressed
      String mode = "error";

      if ( edit != null && edit.length() > 0 ) {
        mode = edit.trim();
      } else if ( add != null && add.length() > 0 ) {
        mode = add.trim();
      } else if ( delete != null && delete.length() > 0 ) {
        mode = delete.trim();
      } else if ( view != null && view.length() > 0 ) {
        mode = view.trim();
      } else if ( activate != null && activate.length() > 0 ) {
        mode = activate.trim();
      }

      //Fetch contactId
      String contactId = request.getParameter( "contact" );

      //Create xmlCarrier
      ContactEdit xmlCarrier = new ContactEdit( userProfile, mode, contactId );

      //Set modified contact in session
      session.setAttribute( ITRResources.ITR_MODIFIED_CONTACT, xmlCarrier );

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

			//Position
	  		if (!isValid(theForm.position, "Position"))
            	return false;

			//company
         	if (!isSelected(theForm.company, "Company"))
            	return false;

			//Description
	  		if (!isValid(theForm.desc, "Description"))
            	return false;
	  		if (!isNotToLong(theForm.desc, "Description", 255))
            	return false;

			// Change this to validate the date
			//first contact
	  		if (!isNotEmpty(theForm.firstcontact, "First Contact"))
            	return false;
 			if (!isValid(theForm.firstcontact, "First Contact"))
            	return false;
   	 
			//Friend level
	  		if (!isValid(theForm.friendlevel, "Friend Level"))
            	return false;
	 
			//known by user
        	if ( !isSelected(theForm.knownbyuser, "Known by User") )
            	return false;

        	return true;
		}
		</script>
		<title>Change Contact Profile</title>
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
		<form method="POST" action="changeContactsResult.jsp" name="personalinfo" onsubmit="return validate(this)">
		<p>
		<table cellspancing="5" cellpadding="5" border="0" width="800">
			<tr>
				<td height="30" align="right" valign="bottom" colspan="2" class="NormalBold">|<a href="OnlineHelp">Help</a>|</td>
			</tr>
			
			<!--Start grey line-->
			<tr><td colspan="2"><table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td bgcolor="#cccccc"><img src="images//sp.gif" height="1" width="1"/></td></tr></table></td></tr>
			<!--End grey line -->

			<tr>
				<td align="left" valign="top">
					<table cellspancing="0" cellpadding="0" border="0" width="400">
						<tr>
							<td align="left" colspan="2" class="NormalBold">Contact Profile</td>
						</tr>
						<tr>
							<td align="center" colspan="2">&#160;</td>
						</tr>
						<tr>
							<td align="left" width="25%"> First name 
								<input type="hidden" name="contactid" value="<%=xmlCarrier.getModifiedContact().getFirstName()%>"/>
							</td>
							<td align="left" width="75%">
								<%if(xmlCarrier.isInAddMode() || xmlCarrier.isInEditMode()) {%>
									<input type="text" name="firstname" size="30" maxlength="50" class="input" value="<%=xmlCarrier.getModifiedContact().getFirstName()%>"/>
								<%}%>
								<%if(xmlCarrier.isInViewMode() || xmlCarrier.isInDeleteMode()) {%>
									<%=xmlCarrier.getModifiedContact().getFirstName()%>
								<%}%>
							</td>
						</tr>
						<tr>
							<td align="left" width="25%"> Last name </td>
							<td align="left" width="75%">
								<%if(xmlCarrier.isInAddMode() || xmlCarrier.isInEditMode()) {%>
									<input type="text" name="lastname" size="30" maxlength="50" class="input" value="<%=xmlCarrier.getModifiedContact().getLastName()%>"/>
								<%}%>
								<%if(xmlCarrier.isInViewMode() || xmlCarrier.isInDeleteMode()) {%>
									<%=xmlCarrier.getModifiedContact().getLastName()%>
								<%}%>
							</td>
						</tr>
						<tr>
							<td align="left" width="25%"> Position </td>
							<td align="left" width="75%">
								<%if(xmlCarrier.isInAddMode() || xmlCarrier.isInEditMode()) {%>
									<input type="text" name="position" size="30" maxlength="50" class="input" value="<%=xmlCarrier.getModifiedContact().getPosition()%>"/>
								<%}%>
								<%if(xmlCarrier.isInViewMode() || xmlCarrier.isInDeleteMode()) {%>
									<%=xmlCarrier.getModifiedContact().getPosition()%>
								<%}%>
							</td>
						</tr>
						<tr>
							<td align="left" width="25%"> Company </td>
							<td align="left" width="75%">
								<%
								String readonly = "";
								if(xmlCarrier.isInViewMode() || xmlCarrier.isInDeleteMode()) {
									readonly = "readonly";
								}
								%>
								<select name="company" <%=readonly%> size="1" style="font-size: 10px">
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
					</table>
				</td>
				<td>
					<table cellspancing="0" cellpadding="0" border="0" width="400">
						<tr>
							<td align="left" colspan="2" class="NormalBold">Other </td>
						</tr>

						<tr><td align="center" colspan="2">&#160;</td></tr>

						<tr>
							<td align="left" width="25%"> First Contact </td>
							<td width="75%" align="left">
								<%if(xmlCarrier.isInAddMode() || xmlCarrier.isInEditMode()) {%>
									<input type="text" name="firstcontact" size="10" maxlength="10" class="input" value="<%=xmlCarrier.getModifiedContact().getFirstContact().getCalendarInStoreFormat()%>"/>&#160;<i>(yyyy-mm-dd)</i>
								<%}%>
								<%if(xmlCarrier.isInViewMode() || xmlCarrier.isInDeleteMode()) {%>
									<%=xmlCarrier.getModifiedContact().getFirstContact().getCalendarInStoreFormat()%>
								<%}%>
							</td>
						</tr>
						<tr>
							<td align="left" width="25%"> Friend Level </td>
							<td width="75%" align="left">
								<%if(xmlCarrier.isInAddMode() || xmlCarrier.isInEditMode()) {%>
									<input type="text" name="friendlevel" size="30" maxlength="50" class="input" value="<%=xmlCarrier.getModifiedContact().getFriendLevel()%>"/>
								<%}%>
								<%if(xmlCarrier.isInViewMode() || xmlCarrier.isInDeleteMode()) {%>
									<%=xmlCarrier.getModifiedContact().getFriendLevel()%>
								<%}%>
							</td>
						</tr>
						<tr>
							<td align="left" width="25%"> Known by User </td>
							<td align="left" width="75%">
								<%
								readonly = "";
								if(xmlCarrier.isInViewMode() || xmlCarrier.isInDeleteMode()) {
									readonly = "readonly";
								}
								%>
								<select name="knownbyuser" <%=readonly%> size="1" style="font-size: 10px">
									<%
									for(int i=0;i<xmlCarrier.getKnownByUserCombo().getEntries().size(); i++) {
										Entry anEntry = (Entry)xmlCarrier.getKnownByUserCombo().getEntries().get(i);
									%>
										<option	<%if(anEntry.isSelected()) {out.write("selected");}%> <%=" value=\""+anEntry.getValue()+"\"" %>>
											<%=anEntry.getText()%>
										</option>
									<%}%>
								</select>
							</td>
						</tr>
						
						<tr><td align="center" colspan="2">&#160;</td></tr>
						
					</table>
				</td>
			</tr>

			<!--Start grey line-->
			<tr><td colspan="2"><table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td bgcolor="#cccccc"><img src="images//sp.gif" height="1" width="1"/></td></tr></table></td></tr>
			<!--End grey line -->

			<tr>
				<td align="left" valign="top" colspan="2">
					<table cellspancing="0" cellpadding="0" border="0" width="400">
						<tr>
							<td align="left" colspan="2" class="NormalBold"> Description </td>
						</tr>
						
						<tr><td align="center" colspan="2">&#160;</td></tr>
						
						<tr>
							<td align="left" width="100%" colspan="2">
								<%if(xmlCarrier.isInAddMode() || xmlCarrier.isInEditMode()) {%>
									<textarea rows="3" cols="50" name="desc" class="input"><%=xmlCarrier.getModifiedContact().getDescription()%></textarea>		
								<%}%>
								<%if(xmlCarrier.isInViewMode() || xmlCarrier.isInDeleteMode()) {%>
									<%=xmlCarrier.getModifiedContact().getDescription()%>
								<%}%>
							</td>
						</tr>
						
						<tr><td align="center" colspan="2">&#160;</td></tr>
						
					</table>
				</td>
			</tr>

			<!--Start grey line-->
			<tr><td colspan="2"><table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td bgcolor="#cccccc"><img src="images//sp.gif" height="1" width="1"/></td></tr></table></td></tr>
			<!--End grey line -->

			<tr>
				<td valign="top">
					<table cellspancing="0" cellpadding="0" border="0" width="400">
						<tr>
							<td align="left" colspan="2" class="NormalBold">Phone numbers</td>
						</tr>
						
						<tr><td align="center" colspan="2">&#160;</td></tr>
						<%
						for(int i=0;i<xmlCarrier.getModifiedContact().getPhoneNumbers().size(); i++) {
							PhoneNumber aPhone = xmlCarrier.getModifiedContact().getPhoneNumber(i);
						%>
							<tr>
								<td align="left" width="25%">
									<%=aPhone.getPhoneDescription()%>
								</td>
								<td align="left" width="75%">
									<%=aPhone.getRegion().getCountry().getCountryCode()%> <%=aPhone.getRegion().getRegionCode()%> - <%=aPhone.getPhoneNumber()%>
								</td>
							</tr>
						<%}%>
						
						<tr><td colspan="2">&#160;</td></tr>
											
					</table>
				</td>
				<td valign="top">
					<table cellspancing="0" cellpadding="0" border="0" width="400">
						<tr>
							<td align="left" colspan="2" class="NormalBold">Email addresses</td>
						</tr>
						
						<tr><td align="center" colspan="2">&#160;</td></tr>
						<%
						for(int i=0;i<xmlCarrier.getModifiedContact().getEmails().size(); i++) {
							Email anEmail = xmlCarrier.getModifiedContact().getEmail(i);
						%>
							<tr>
								<td align="left" width="25%">
									<%=anEmail.getDescription()%>
								</td>
								<td align="left" width="75%">
									<a href="mailto:<%=anEmail.getAddress()%>"><%=anEmail.getAddress()%></a>
								</td>
							</tr>
						<%}%>

						<tr><td colspan="2">&#160;</td></tr>
						<tr><td align="center" colspan="2">&#160;</td></tr>
						
					</table>
				</td>
			</tr>

			<!--Start grey line-->
			<tr><td colspan="2"><table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td bgcolor="#cccccc"><img src="images//sp.gif" height="1" width="1"/></td></tr></table></td></tr>
			<!--End grey line -->

			<tr>
				<td align="right" colspan="2">
					<%if(xmlCarrier.isInDeleteMode()) {%>
						<input type="button" name="btnDelete" value=" Delete " class="input" onclick="window.location.href='changeContactsResult.jsp?btnDelete=Delete'"/>&#160;
					<%}%>
					<%if(xmlCarrier.isInAddMode() || xmlCarrier.isInEditMode()) {%>
						<input type="submit" name="btnSave" value=" Save " class="input"/>&#160;
					<%}%>
					<input type="button" name="Cancel" value=" Cancel " onclick="window.location.href='changeContactsQuery.jsp'" class="input"/>
				</td>
			</tr>
		</table>
		</form>
		</center>
	</body>
</html>
