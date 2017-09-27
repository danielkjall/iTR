<%@ page session="true"%>
<%@ page import="com.intiro.itr.ITRResources, com.intiro.itr.util.personalization.*, com.intiro.itr.logic.setting.*"%>
<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.addHeader("Cache-Control","no-store");
    UserProfile userProfile = (UserProfile) session.getAttribute(ITRResources.ITR_USER_PROFILE);
	PasswordChanger passwordChanger = new PasswordChanger( userProfile );
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
<head>
<meta HTTP-EQUIV="Content-Type" CONTENT="text/html;CHARSET=iso-8859-1"/>
<script type="text/javascript">
<%@ include file="../../../helpers/en_validate.js" %>
function validate(theForm)  {
	//Both
	 if(theForm.newPassword.value != theForm.confirmPassword.value) {
		alert("The passwords must match each other");
	       return false;
	}

	//newPassword
         else if (!isNotEmpty(theForm.newPassword, "New password"))
            return false;
        
	//confirmPassword
	  else if (!isNotEmpty(theForm.confirmPassword, "Confirm password"))
            return false;

	//changeLoginId
        else if (!isNotEmpty(theForm.newLoginId, "Change login id"))
            return false;	 
	else 
	     return true;
}
</script>
<title>Change Password</title>
<link rel="stylesheet" href="include/ITR.CSS" />
</head>
<body bgcolor="#eeeeee" topmargin="2" leftmargin="0" rightmargin="0" marginwidth="0" marginheight="0" >
<center>
<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr bgcolor="#aaaacc" align="center">
       	<td class="Title1">Change Password</td>
	</tr>
</table>

<form method="POST" action="changePasswordResult.jsp" name="personalinfo" onsubmit="return validate(this)">
<p>
      <table cellspancing="0" cellpadding="0" border="0" width="300" align="center">
        <tr> 
          <td height="30" align="right" valign="bottom" colspan="2" class="NormalBold"> 
            |<a href="OnlineHelp">Help</a>| </td>
        </tr>
        <tr> 
          <td colspan="2"> 
            <div align="center"><img src="images/grey_line.jpg" /> </div>
          </td>
        </tr>
        <tr> 
          <td  class="NormalBold" width="50%" align="left">Change login id:</td>
          <td width="50%" align="left"> 
            <input type="text" name="newLoginId" size="20" maxlength="20" class="input" value="<%=passwordChanger.getUserProfile().getLoginId()%>"/>
          </td>
        </tr>
        <tr> 
          <td  class="NormalBold" width="50%" align="left">New password:</td>
          <td width="50%" align="left"> 
            <input type="password" name="newPassword" size="20" maxlength="20" value="" class="input"/>
          </td>
        </tr>
        <tr> 
          <td  class="NormalBold" width="50%" align="left">Confirm password:</td>
          <td width="50%" align="left"> 
            <input type="password" name="confirmPassword" size="20" maxlength="20" value="" class="input"/>
          </td>
        </tr>
        <tr> 
          <td colspan="2"> 
            <div align="center"><img src="images/grey_line.jpg" /></div>
          </td>
        </tr>
        <tr> 
          <td align="left" width="50%"> </td>
          <td align="left" width="50%"> 
            <input type="submit" name="Change" value=" Change " class="input"/>
            <input type="button" name="Cancel" value=" Cancel " onclick="window.location.href='../../frames/mainFrame.jsp'" class="input"/>
          </td>
        </tr>
      </table>
</p>
</form>
</center>
</body>
</html>