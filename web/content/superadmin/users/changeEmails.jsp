<%@ page session="true"%>
<%@ page import="com.intiro.itr.logic.email.Email, com.intiro.itr.ITRResources, com.intiro.itr.util.personalization.*, com.intiro.itr.logic.superadmin.users.EmailEditor"%>
<%
      UserProfile userProfile = ( UserProfile )session.getAttribute( ITRResources.ITR_USER_PROFILE );
      EmailEditor xmlCarrier = null;
      String action = request.getParameter("action");

      // Check if it is emails for a Contact or a User,
      // The mode parameter can be either "users" or "contacts"
      String mode = request.getParameter("mode");

      if (action != null && action.equalsIgnoreCase("start")) {
        int userId = Integer.parseInt(request.getParameter("userid"));
        xmlCarrier = new EmailEditor(userProfile, userId, mode);
        session.setAttribute(ITRResources.ITR_EMAIL_EDITOR, xmlCarrier);
      }
      else if (action != null && action.equalsIgnoreCase("removeRow")) {
        xmlCarrier = (EmailEditor) session.getAttribute(ITRResources.ITR_EMAIL_EDITOR);

        int rowIndex = Integer.parseInt(request.getParameter("row"));
        xmlCarrier.removeEmail(rowIndex);
      }
      else if (action != null && action.equalsIgnoreCase("addRow")) {
        xmlCarrier = (EmailEditor) session.getAttribute(ITRResources.ITR_EMAIL_EDITOR);

        String desc = request.getParameter("desc");

        if (desc == null || desc.length() == 0) {
          desc = "Description is missing";
        }

        String email = request.getParameter("email");

        if (email == null || email.length() == 0) {
          email = "Email address is missing";
        }

        xmlCarrier.getNewEmail().setDescription(desc);
        xmlCarrier.getNewEmail().setAddress(email);
        xmlCarrier.addEmail();
        xmlCarrier.setEditingRow(false);
      }
      else if (action != null && action.equalsIgnoreCase("editRow")) {
        xmlCarrier = (EmailEditor) session.getAttribute(ITRResources.ITR_EMAIL_EDITOR);

        int row = Integer.parseInt(request.getParameter("row"));

        if (xmlCarrier.getEditingRow()) {
          xmlCarrier.addEmail();
        }

        xmlCarrier.setNewEmail(xmlCarrier.getEmail(row));
        xmlCarrier.removeEmail(row);
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

				//Email address
			        if ( !isNotEmpty(theForm.email, "Email Address") )
			            return false;
			       if ( !isValid(theForm.email, "Email Address") )
			            return false;

				return true;
			}
		</script>
		<title>Edit Email Adresses</title>
		<link rel="stylesheet" href="include/ITR.CSS"/>
	</head>
	<body bgcolor="#eeeeee" topmargin="2" leftmargin="0" rightmargin="0" marginwidth="0" marginheight="0">
		<center>
			<table border="0" cellpadding="0" cellspacing="0" width="100%">
				<tr bgcolor="#aaaacc" align="center">
					<td class="Title1">Edit Email Addresses</td>
				</tr>
			</table>
			
				<p>
					<table cellspancing="0" cellpadding="0" border="0" width="500" align="center">
						<tr>
							<td align="left" valign="bottom" class="Title4" colspan="3">E-mail addresses for <%=xmlCarrier.getModeDesc()%>:&#160;&#160;&#160;&#160;&#160;<%=xmlCarrier.getFirstName()%>&#160;<%=xmlCarrier.getLastName()%></td>
						</tr>
						<tr>
							<td align="right" valign="bottom" colspan="3" class="NormalBold">|<a href="OnlineHelp">Help</a>|</td>
						</tr>

						<!--Start grey line-->
						<tr><td colspan="3"><table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td bgcolor="#cccccc"><img src="images/sp.gif" height="1" width="1"/></td></tr></table></td></tr>
						<!--End grey line -->

					<form method="POST" action="changeEmails.jsp" name="emalinfo" onsubmit="return validate(this)">
						<input type="hidden" name="mode" value="<%=xmlCarrier.getMode()%>"/>
						<tr bgcolor="#aaaacc">
							<td class="NormalBold" width="50%" align="left">Description</td>
							<td class="NormalBold" width="40%" align="left">Address</td>
							<td class="NormalBold" width="10%" align="left">Action</td>
						</tr>
						<tr>
							<td class="Normal" width="40%" align="left">
								<input type="text" size="30" maxlength="30" class="input" name="desc" value="<%=xmlCarrier.getNewEmail().getDescription()%>"/>
							</td>
							<td width="40%" align="left">
								<input type="text" size="40" maxlength="40" class="input" name="email" value="<%=xmlCarrier.getNewEmail().getAddress()%>"/>
							</td>
							<td width="20%" align="left">
								<a><input type="submit" name="btnAddRow" value="add"/></a>
								<input type="hidden" name="action" value="addRow"/>
							</td>
						</tr>
					</form>
						
					<tr><td colspan="5">&#160;</td></tr>
					
					<!--Start grey line-->
					<tr><td colspan="3"><table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td bgcolor="#cccccc"><img src="images/sp.gif" height="1" width="1"/></td></tr></table></td></tr>
					<!--End grey line -->
					
					<form method="POST" action="changeEmailsResult.jsp" name="phone">
					<input type="hidden" name="mode" value="<%=xmlCarrier.getMode()%>"/>
					<%
					String bgColor = "";
					for(int i=0;i<xmlCarrier.getEmails().size();i++) {
						Email anEmail = xmlCarrier.getEmail(i);
						if(i%2==0) bgColor="#fafafa";
						if(!anEmail.isRemoved()) {%>
							<tr bgcolor="<%=bgColor%>">
								<td class="Normal" width="40%" align="left">
									<a href="changeEmails.jsp?action=editRow&#38;row=<%=i%>"><%=anEmail.getDescription()%></a>
								</td>
								<td width="40%" align="left">
									<%=anEmail.getAddress()%>
								</td>
								<td width="20%" align="left">
									<a href="changeEmails.jsp?action=removeRow&#38;row=<%=i%>><img src="images/delete.gif" alt="Delete" border="0"/></a>
								</td>
							</tr>
					<%	}
					}%>

					<!--Start grey line-->
					<tr><td colspan="3"><table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td bgcolor="#cccccc"><img src="images/sp.gif" height="1" width="1"/></td></tr></table></td></tr>
					<!--End grey line -->

					<tr><td colspan="5">&#160;</td></tr>
					
					<tr>
						<td/>
						<td colspan="2" align="right" >
							<input type="submit" name="Save" value=" Save " class="input"/>&#160;
							<input type="button" name="Cancel" value=" Cancel " class="input" onclick="window.location.href='changeEmailsQuery.jsp?action=<%=xmlCarrier.getMode()%>'"/>
						</td>
					</tr>
				</form>
			</table>
			</p>
		</center>
	</body>
</html>