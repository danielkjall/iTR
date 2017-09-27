<%@ page session="true"%>
<%@ page import="com.intiro.itr.util.xml.XMLCombo.Entry, com.intiro.itr.ITRResources, com.intiro.itr.util.personalization.*, com.intiro.itr.logic.superadmin.users.PhoneQueryProfile"%>
<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.addHeader("Cache-Control","no-store");
    UserProfile userProfile = (UserProfile) session.getAttribute(ITRResources.ITR_USER_PROFILE);
	PhoneQueryProfile xmlCarrier = new PhoneQueryProfile(userProfile);
	
	String action = request.getParameter( "action" );
    boolean isContact = ( action != null && action.equals( "contacts" ) ) ? true : false;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
	<head>
		<meta HTTP-EQUIV="Content-Type" CONTENT="text/html;CHARSET=iso-8859-1"/>
		<meta NAME="Expires" Content="Now"/>
		<meta HTTP-EQUIV="Pragma" CONTENT="no_cache"/>
		<script type="text/javascript">
			function validate(theForm)  {
				return true;
			}
		</script>
		<title>ITR - Super admin - Modify Emails</title>
		<link rel="stylesheet" href="include/ITR.CSS"/>
	</head>
	<body bgcolor="#eeeeee" topmargin="2" leftmargin="0" rightmargin="0" marginwidth="0" marginheight="0">
		<a name="top"/>
		<center>
			<table border="0" cellpadding="0" cellspacing="0" width="100%">
				<tr bgcolor="#aaaacc" align="center">
					<td class="Title1">Super Admin - Modify Emails</td>
				</tr>
			</table>
			<form method="POST" action="changeEmails.jsp" name="queryprofile" onsubmit="return validate(this)">
				<input type="hidden" name="mode" value="<%=xmlCarrier.getAction()%>"/>
				<input type="hidden" name="action" value="start"/>
				<p>
					<table cellspancing="0" cellpadding="0" border="0" width="300">
						<tr>
							<td height="30" align="right" valign="bottom" colspan="2" class="NormalBold">|<a href="OnlineHelp">Help</a>|</td>
						</tr>
					
						<!--Start grey line-->
						<tr><td colspan="2"><table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td bgcolor="#cccccc"><img src="images/sp.gif" height="1" width="1"/></td></tr></table></td></tr>
						<!--End grey line -->
					
						<tr><td>&#160;</td></tr>
						
						<tr>
							<td class="NormalBold" align="left" width="50%"> Users </td>
							<td align="left" width="50%">
								<select name="userid" class="input">
									<%
									for(int i=0;i<xmlCarrier.getUsersCombo().getEntries().size(); i++) {
										Entry anEntry = (Entry)xmlCarrier.getUsersCombo().getEntries().get(i);
									%>
										<option	<%if(anEntry.isSelected()) {out.write("selected");}%> <%=" value=\""+anEntry.getValue()+"\"" %>>
											<%=anEntry.getText()%>
										</option>
									<%}%>
								</select>
								
							</td>
						</tr>
						
						<tr><td>&#160;</td></tr>
						
						<!--Start grey line-->
 						<tr><td colspan="2"><table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td bgcolor="#cccccc"><img src="images/sp.gif" height="1" width="1"/></td></tr></table></td></tr>
						<!--End grey line -->

						<tr><td>&#160;</td></tr>
						
						<tr>
							<td colspan="2" align="right">
								<input type="submit" name="btnAdd" value="Modify email addresses" class="input"/>&#160;
								<input type="button" name="Cancel" value=" Cancel " onclick="javascript:window.location.href='../../frames/mainFrame.jsp'" class="input"/>
							</td>
						</tr>
					</table>
				</p>
			</form>
		</center>
	</body>
</html>