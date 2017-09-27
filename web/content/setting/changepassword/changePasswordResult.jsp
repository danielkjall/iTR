<%@ page session="true"%>
<%@ page import="com.intiro.itr.ITRResources, com.intiro.itr.ui.constants.Commands, com.intiro.itr.util.personalization.*, com.intiro.itr.logic.setting.*"%>
<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.addHeader("Cache-Control","no-store");
    UserProfile userProfile = (UserProfile) session.getAttribute(ITRResources.ITR_USER_PROFILE);
	PasswordSaverActivator save = new PasswordSaverActivator( userProfile );
    save.changePassword( request.getParameter( Commands.XSLFIELD_NEW_LOGINID ), request.getParameter( Commands.XSLFIELD_NEW_PASSWORD ) );
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
<head>
<meta HTTP-EQUIV="Content-Type" CONTENT="text/html;CHARSET=iso-8859-1"/>
<title>Change Password Result</title>
<link rel="stylesheet" href="include/ITR.CSS" />
</head>
<body bgcolor="#eeeeee" topmargin="2" leftmargin="0" rightmargin="0" marginwidth="0" marginheight="0" >
<center>
<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr bgcolor="#aaaacc" align="center">
       	<td class="Title1">Result</td>
	</tr>
</table>
<p>
      <table cellspancing="0" cellpadding="0" border="0" width="300" align="center">
        <tr> 
          <td height="30" align="right" valign="bottom" colspan="2" class="NormalBold"> 
            |<a href="OnlineHelp">Help</a>| </td>
        </tr>
        <tr><td colspan="2"><div align="center"><img src="images/grey_line.jpg" /> </div></td></tr>
        <tr> 
          <td  class="NormalBold" width="50%" align="left">Tried to change your password :</td>
          <td width="50%" align="left">
          	<%if (save.isSuccess())
          		out.write(save.getSuccessMessage());
          	else
          		out.write(save.getFailureMessage());
          	%>
          </td>
        </tr>
        <tr><td colspan="2"><div align="center"><img src="images/grey_line.jpg" /></div></td></tr>
      </table>
</p>
</center>
</body>
</html>