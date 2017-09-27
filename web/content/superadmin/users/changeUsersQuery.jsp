<%@ page session="true"%>
<%@ page import="com.intiro.itr.util.xml.XMLCombo.Entry, com.intiro.itr.ITRResources, com.intiro.itr.util.personalization.*, com.intiro.itr.logic.superadmin.users.UserQueryProfile"%>
<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.addHeader("Cache-Control","no-store");
    UserProfile userProfile = (UserProfile) session.getAttribute(ITRResources.ITR_USER_PROFILE);
	UserQueryProfile xmlCarrier = new UserQueryProfile(userProfile);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
	<head>
		<meta HTTP-EQUIV="Content-Type" CONTENT="text/html;CHARSET=iso-8859-1"/>
		<meta NAME="Expires" Content="Now"/>
		<meta HTTP-EQUIV="Pragma" CONTENT="no_cache"/>
		<script type="text/javascript">
			<%@ include file="../../../helpers/en_validate.js" %>
			function validateDeactivated(theForm)  {
				//Deactivated user
    				if ( !isSelected(theForm.deactivateduser, "Deactivated user") )
        					return false;
 					return true;
			}
			
			function validateActivated(theForm)  {
				//Activated user
    				if ( !isSelected(theForm.activateduser, "Activated user") )
        					return false;
 					return true;
			}
		</script>
		<title>ITR - Super admin - Modify Users</title>
		<link rel="stylesheet" href="include/ITR.CSS"/>
	</head>
	<body bgcolor="#eeeeee" topmargin="2" leftmargin="0" rightmargin="0" marginwidth="0" marginheight="0">
		<a name="top"/>
		<center>
			<table border="0" cellpadding="0" cellspacing="0" width="100%">
				<tr bgcolor="#aaaacc" align="center">
					<td class="Title1">Super Admin - Modify Users</td>
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
						
						<form method="POST" action="changeUsers.jsp" name="queryprofile" onsubmit="return validateActivated(this)">
						<tr>
							<td class="NormalBold" align="left" width="50%"> Activated Users </td>
							<td align="left" width="50%">
								<select name="activateduser" class="input">
									<%
									for(int i=0;i<xmlCarrier.getActivatedUsersCombo().getEntries().size(); i++) {
										Entry anEntry = (Entry)xmlCarrier.getActivatedUsersCombo().getEntries().get(i);
									%>
										<option	<%if(anEntry.isSelected()) {out.write("selected");}%> <%=" value=\""+anEntry.getValue()+"\"" %>>
											<%=anEntry.getText()%>
										</option>
									<%}%>
								</select>
							</td>
						</tr>
						
						<!--
						<tr>
							<td class="NormalBold" align="left" width="50%"> Copy from user: </td>
							<td align="left" width="50%">
								<input type="checkbox" name="copyuser" class="input"/>
							</td>
						</tr>
						-->
					
						<tr><td>&#160;</td></tr>
						
						<tr>
							<td colspan="2" align="right">
								<input type="button" name="btnAdd" value="&#160;&#160;&#160;Add&#160;&#160;&#160;" class="input" onclick="document.location.href='changeUsers.jsp?btnAdd=Add'">
								</input>
								&#160;
								<input type="submit" name="btnEdit" value="&#160;&#160;Edit&#160;&#160;" class="input"/>&#160;
								<input type="submit" name="btnView" value="&#160;&#160;View&#160;&#160;" class="input"/>&#160;
								<input type="submit" name="btnDelete" value="&#160;Delete&#160;" class="input"/>
							</td>
						</tr>
						</form>
						<form method="POST" action="changeUsers.jsp" name="queryprofile" onsubmit="return validateDeactivated(this)">
						
						<tr><td>&#160;</td></tr>

						<!--Start grey line-->
						<tr><td colspan="2"><table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td bgcolor="#cccccc"><img src="images/sp.gif" height="1" width="1"/></td></tr></table></td></tr>
						<!--End grey line -->

						<tr><td>&#160;</td></tr>
						
						<tr>
							<td class="NormalBold" align="left" width="50%"> Deactivated Users: </td>
							<td align="left" width="50%">
								<select name="deactivateduser" class="input">
									<%
									for(int i=0;i<xmlCarrier.getDeActivatedUsersCombo().getEntries().size(); i++) {
										Entry anEntry = (Entry)xmlCarrier.getDeActivatedUsersCombo().getEntries().get(i);
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
								<input type="submit" name="btnActivate" value="Activate" class="input"/>&#160;
								<!--<input type="submit" name="btnView" value="&#160;&#160;View&#160;&#160;" class="input"/>-->
							</td>
						</tr>
						
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
						</form>
					</table>
				</p>
		</center>
	</body>
</html>
	