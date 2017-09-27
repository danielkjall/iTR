<%@ page session="true"%>
<%@ page import="com.intiro.itr.util.xml.XMLCombo.Entry, com.intiro.itr.ITRResources, com.intiro.itr.util.personalization.*, com.intiro.itr.logic.superadmin.contacts.ContactsQueryProfile"%>
<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.addHeader("Cache-Control","no-store");
    UserProfile userProfile = (UserProfile) session.getAttribute(ITRResources.ITR_USER_PROFILE);
	ContactsQueryProfile xmlCarrier = new ContactsQueryProfile(userProfile);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
	<head>
		<meta HTTP-EQUIV="Content-Type" CONTENT="text/html;CHARSET=iso-8859-1"/>
		<script type="text/javascript">
			<%@ include file="../../../helpers/en_validate.js" %>
			function validate(theForm)  {
				
  				if ( !isSelected(theForm.contact, "Contact") )
   					return false;
				return true;
			}
		</script>
		<title>ITR - Modify Contacts</title>
		<link rel="stylesheet" href="include/ITR.CSS"/>
	</head>
	<body bgcolor="#eeeeee" topmargin="2" leftmargin="0" rightmargin="0" marginwidth="0" marginheight="0">
		<a name="top"/>
		<center>
			<table border="0" cellpadding="0" cellspacing="0" width="100%">
				<tr bgcolor="#aaaacc" align="center">
					<td class="Title1">Super Admin - Modify Contacts</td>
				</tr>
			</table>
			<p>
			<table cellspancing="0" cellpadding="0" border="0" width="300">
				<tr>
					<td height="30" align="right" valign="bottom" colspan="2" class="NormalBold">|<a href="OnlineHelp">Help</a>|</td>
				</tr>
			
				<!--Start grey line-->
				<tr><td colspan="2"><table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td bgcolor="#cccccc"><img src="images/sp.gif" height="1" width="1"/></td></tr></table></td></tr>
				<!--End grey line -->
	
				<tr><td>&#160;</td></tr>
				<form method="POST" action="changeContacts.jsp" name="queryprofile" onsubmit="return validate(this)">
				<tr>
					<td class="NormalBold" align="left" width="50%"> Contact </td>
					<td align="left" width="50%">
						<select name="contact" class="input">
						<%
						for(int i=0;i<xmlCarrier.getContactCombo().getEntries().size(); i++) {
							Entry anEntry = (Entry)xmlCarrier.getContactCombo().getEntries().get(i);
						%>
							<option	<%if(anEntry.isSelected()) {out.write("selected");}%> <%=" value=\""+anEntry.getValue()+"\"" %>>
								<%=anEntry.getText()%>
							</option>
						<%}%>
						</select>
					</td>
				</tr>
				
				<tr><td>&#160;</td></tr>
							
				<tr>
					<td colspan="2" align="right">
						<input type="button" name="btnAdd" value="&#160;&#160;&#160;Add&#160;&#160;&#160;" class="input" onclick="document.location.href='changeContacts.jsp?btnAdd=Add'">
						</input>
						&#160;
						<input type="submit" name="btnEdit" value="&#160;&#160;Edit&#160;&#160;" class="input"/>&#160;
						<input type="submit" name="btnView" value="&#160;&#160;View&#160;&#160;" class="input"/>&#160;
						<input type="submit" name="btnDelete" value="&#160;Delete&#160;" class="input"/>
					</td>
				</tr>
				</form>
	
				<tr><td>&#160;</td></tr>
				
				<!--Start grey line-->
				<tr><td colspan="2"><table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td bgcolor="#cccccc"><img src="images/sp.gif" height="1" width="1"/></td></tr></table></td></tr>
				<!--End grey line -->
	
				<tr><td>&#160;</td></tr>
	
				<tr>
					<td align="right" colspan="2">
						<input type="button" name="Cancel" value=" Cancel " onclick="window.location.href='../../frames/mainFrame.jsp'" class="input"/>
					</td>
				</tr>
			</table>
			</p>
		</center>
	</body>
</html>
