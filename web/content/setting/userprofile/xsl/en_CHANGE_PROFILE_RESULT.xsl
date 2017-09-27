<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"  xmlns:fo="http://www.w3.org/1999/XSL/Format">
<xsl:template match="/">
<html>
<head>
<meta HTTP-EQUIV="Content-Type" CONTENT="text/html;CHARSET=iso-8859-1"/>
<meta NAME="Expires" Content="Now"/>
<meta HTTP-EQUIV="Pragma" CONTENT="no_cache"/>
<title>Change Password Result</title>
<link rel="stylesheet" href="../include/ITR.CSS" />
</head>
<body bgcolor="#eeeeee" topmargin="2" leftmargin="0" rightmargin="0" marginwidth="0" marginheight="0" >
<center>
<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr bgcolor="#aaaacc" align="center">
       	<td class="Title1">Result</td>
	</tr>
</table>
<form method="POST" action="TimePortal"  target="_top" name="personalinfo">
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
          <td  class="NormalBold" width="50%" align="left">Tried to change your profile :</td>
          <td width="50%" align="left"> 
            <xsl:value-of select="response/message" />
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
            <input type="submit" name="activator" value=" Click to activate your new profile " class="input"/>
            </td>
        </tr>
      </table>
</p>
</form>
</center>
</body>
</html>
</xsl:template>
</xsl:stylesheet>