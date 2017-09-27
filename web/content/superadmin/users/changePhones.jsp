<%@ page session="true"%>
<%@ page import="com.intiro.itr.logic.superadmin.users.PhoneEditor, com.intiro.itr.logic.phone.PhoneNumber, com.intiro.itr.util.xml.XMLCombo.Entry, com.intiro.itr.ITRResources, com.intiro.itr.util.personalization.*"%>
<%
      UserProfile userProfile = ( UserProfile )session.getAttribute( ITRResources.ITR_USER_PROFILE );
      
      PhoneEditor xmlCarrier = null;
      String action = request.getParameter("action");

      // Check if it is emails for a Contact or a User
      // The mode parameter can be either "users" or "contacts"
      String mode = request.getParameter("mode");

      if (action != null && action.equalsIgnoreCase("start")) {
        int userId = Integer.parseInt(request.getParameter("userid"));
        xmlCarrier = new PhoneEditor(userProfile, userId, mode);
        session.setAttribute(ITRResources.ITR_PHONE_NUMBER_EDITOR, xmlCarrier);
      }
      else if (action != null && action.equalsIgnoreCase("removeRow")) {
        xmlCarrier = (PhoneEditor) session.getAttribute(ITRResources.ITR_PHONE_NUMBER_EDITOR);

        int rowIndex = Integer.parseInt(request.getParameter("row"));
        xmlCarrier.removePhoneNumber(rowIndex);
      }
      else if (action != null && action.equalsIgnoreCase("addRow")) {
        xmlCarrier = (PhoneEditor) session.getAttribute(ITRResources.ITR_PHONE_NUMBER_EDITOR);

        String desc = request.getParameter("desc");

        if (desc == null || desc.length() == 0) {
          desc = "Description is missing";
        }

        xmlCarrier.getNewPhoneNumber().getRegion().getCountry().setCountryId(Integer.parseInt(request.getParameter("countryid")));
        xmlCarrier.getNewPhoneNumber().getRegion().setRegionId(Integer.parseInt(request.getParameter("regionid")));
        xmlCarrier.getNewPhoneNumber().setPhoneDescription(desc);
        xmlCarrier.getNewPhoneNumber().setPhoneNumber(request.getParameter("phoneno"));
        xmlCarrier.addPhoneNumber();
        xmlCarrier.setEditingRow(false);
      }
      else if (action != null && action.equalsIgnoreCase("editRow")) {
        xmlCarrier = (PhoneEditor) session.getAttribute(ITRResources.ITR_PHONE_NUMBER_EDITOR);

        int row = Integer.parseInt(request.getParameter("row"));

        if (xmlCarrier.getEditingRow()) {
          xmlCarrier.addPhoneNumber();
        }

        xmlCarrier.setNewPhoneNumber(xmlCarrier.getPhoneNumber(row));
        xmlCarrier.removePhoneNumber(row);
        xmlCarrier.setEditingRow(true);
      }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
	<head>
		<meta HTTP-EQUIV="Content-Type" CONTENT="text/html;CHARSET=iso-8859-1"/>
		<script type="text/javascript">
			<%@ include file="../../../helpers/en_validate.js" %>
			function validate(theForm)  {
				//Description
				if ( !isNotEmpty(theForm.desc, "Description") )
				    return false;
				if ( !isValid(theForm.desc, "Description") )
				    return false;

				//Phone number
				if ( !isNotEmpty(theForm.phoneno, "Phone number") )
				    return false;
				if ( !isNumeric(theForm.phoneno, "Phone number") )
				    return false;
				
				//Region
				if ( !isSelected(theForm.regionid, "Region") )
				    return false;
				
				//Country
				if ( !isSelected(theForm.countryid, "Country") )
				    return false;
				
				return true;
			}
		</script>
		<title>Edit Phone Numbers</title>
		<link rel="stylesheet" href="include/ITR.CSS"/>
	</head>
	<body bgcolor="#eeeeee" topmargin="2" leftmargin="0" rightmargin="0" marginwidth="0" marginheight="0">
		<center>
			<table border="0" cellpadding="0" cellspacing="0" width="100%">
				<tr bgcolor="#aaaacc" align="center">
					<td class="Title1">Edit Phone Numbers</td>
				</tr>
			</table>
			<p>
				<table cellspancing="0" cellpadding="0" border="0" width="500" align="center">
					<tr>
						<td align="left" valign="bottom" class="Title4" colspan="3">Phone numbers for <%=xmlCarrier.getModeDesc()%>:&#160;&#160;&#160;&#160;&#160;<%=xmlCarrier.getFirstName()%>&#160;<%=xmlCarrier.getLastName()%></td>
					</tr>
					<tr>
						<td align="right" valign="bottom" colspan="5" class="NormalBold">
								|<a href="OnlineHelp">Help</a>| 
							</td>
					</tr>

					<!--Start grey line-->
					<tr><td colspan="5"><table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td bgcolor="#cccccc"><img src="images/sp.gif" height="1" width="1"/></td></tr></table></td></tr>
					<!--End grey line -->

					<tr bgcolor="#aaaacc">
						<td class="NormalBold" width="30%" align="left">Description</td>
						<td class="NormalBold" width="20%" align="left">Phone No.</td>
						<td class="NormalBold" width="20%" align="left">Region code</td>
						<td class="NormalBold" width="20%" align="left">Country code</td>
						<td class="NormalBold" width="10%" align="left">Action</td>
					</tr>
					<form method="POST" action="changePhones.jsp" name="phoneinfo" onsubmit="return validate(this)">
						<tr>
							<td class="NormalBold" width="20%" align="left">
								<input type="text" size="20" maxlength="20" class="input" name="desc" value="<%=xmlCarrier.getNewPhoneNumber().getPhoneDescription()%>"/>
							</td>
							<td width="20%" align="left">
								<input type="text" size="20" maxlength="20" name="phoneno" class="input" value="<%=xmlCarrier.getNewPhoneNumber().getPhoneNumber()%>"/>
							</td>
							<td align="left" width="20%">
								<select name="regionid" class="input">
									<%
									for(int i=0;i<xmlCarrier.getRegionCombo().getEntries().size(); i++) {
										Entry anEntry = (Entry)xmlCarrier.getRegionCombo().getEntries().get(i);
									%>
										<option	<%if(anEntry.isSelected()) {out.write("selected");}%> <%=" value=\""+anEntry.getValue()+"\"" %>>
											<%=anEntry.getText()%>
										</option>
									<%}%>
								</select>													
							</td>
							<td align="left" width="20%">
								<select name="countryid" class="input">
									<%
									for(int i=0;i<xmlCarrier.getCountryCombo().getEntries().size(); i++) {
										Entry anEntry = (Entry)xmlCarrier.getCountryCombo().getEntries().get(i);
									%>
										<option	<%if(anEntry.isSelected()) {out.write("selected");}%> <%=" value=\""+anEntry.getValue()+"\"" %>>
											<%=anEntry.getText()%>
										</option>
									<%}%>
								</select>													
							</td>
							<td width="20%" align="left">
								<a><input type="submit" name="btnAddRow" value="add"/></a>
								<input type="hidden" name="action" value="addRow"/>
							</td>
						</tr>
					</form>
					
					<tr><td colspan="5">&#160;</td></tr>

					<!--Start grey line-->
					<tr><td colspan="5"><table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td bgcolor="#cccccc"><img src="images/sp.gif" height="1" width="1"/></td></tr></table></td></tr>
					<!--End grey line -->

					<form method="POST" action="changePhonesResult.jsp" name="phone">
						<input type="hidden" name="mode" value="<%=xmlCarrier.getMode()%>"/>
						<%
						String bgColor = "";
						for(int i=0;i<xmlCarrier.getPhones().size();i++) {
							PhoneNumber aPhone = xmlCarrier.getPhoneNumber(i);
							if(i%2==0) bgColor="#fafafa";
							if(!aPhone.isRemoved()) {%>
								<tr bgcolor="<%=bgColor%>">
									<td class="NormalBold" width="20%" align="left">
										<a href="changePhones.jsp?action=editRow&#38;row=<%=i%>"><%=aPhone.getPhoneDescription()%></a>
									</td>
									<td width="20%" align="left">
										<%=aPhone.getRegion().getCountry().getCountryCode()%><%=aPhone.getRegion().getRegionCode()%>-<%=aPhone.getPhoneNumber()%>
									</td>
									<td align="left" width="20%">
										<%=aPhone.getRegion().getRegionName()%>
									</td>
									<td align="left" width="20%">
										<%=aPhone.getRegion().getCountry().getCountryName()%>										
									</td>
									<td width="20%" align="left">
										<a href="changePhones.jsp?action=removeRow&#38;row=<%=i%>><img src="images/delete.gif" alt="Delete" border="0"/></a>
									</td>
								</tr>
						<%	}
						}%>

						<!--Start grey line-->
						<tr><td colspan="5"><table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td bgcolor="#cccccc"><img src="images/sp.gif" height="1" width="1"/></td></tr></table></td></tr>
						<!--End grey line -->

						<tr><td colspan="5">&#160;</td></tr>
						
						<tr>
							<td align="left" colspan="4"/>
							<td align="right">
								<input type="submit" name="Save" value=" Save " class="input"/>&#160;
								<input type="button" name="Cancel" value=" Cancel " class="input" onclick="window.location.href='changePhonesQuery.jsp?action=<%=xmlCarrier.getMode()%>'"/>
							</td>
						</tr>
					</form>
				</table>
			</p>
		</center>
	</body>
</html>