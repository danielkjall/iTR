<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"  xmlns:fo="http://www.w3.org/1999/XSL/Format">
<xsl:template match="/">
<html>
<head>
<meta HTTP-EQUIV="Content-Type" CONTENT="text/html;CHARSET=iso-8859-1"/>
<meta NAME="Expires" Content="Now"/>
<meta HTTP-EQUIV="Pragma" CONTENT="no_cache"/>
<script language="JavaScript" src="../../helpers/en_validate.js"/>
<script language="JavaScript" >
function validate(theForm)  {
	//firstName
         if (!isNotEmpty(theForm.firstName, "First name"))
            return false;
         
	//last Name
	  else if (!isNotEmpty(theForm.lastName, "Last name"))
            return false;
	  
	//skin
	 else if ( !isSelected(theForm.skin, "Skin") )
            return false;
	 
	//language
        else if ( !isSelected(theForm.language, "Language") )
            return false;
	  else 
	     return true;
}
</script>
<title>Change Profile</title>
<link rel="stylesheet" href="../include/ITR.CSS" />
</head>
<body bgcolor="#eeeeee" topmargin="2" leftmargin="0" rightmargin="0" marginwidth="0" marginheight="0" >
<a name="top"></a>
<center>
<table border="0" cellpadding="0" cellspacing="0" width="100%">
<tr bgcolor="#aaaacc" align="center">
        <td class="Title1">Change Profile</td>
</tr></table>

<form method="POST" action="ProfileChangerActivator" name="personalinfo"  onsubmit="return validate(this)">
<p>
      <table cellspancing="0" cellpadding="0" border="0" width="300">
        <tr> 
          <td height="30" align="right" valign="bottom" colspan="2" class="NormalBold"> 
            |<a href="OnlineHelp">Help</a>| </td>
        </tr>
        <tr> 
          <td colspan="2" align="center"> <img src="images/grey_line.jpg" /> 
          </td>
        </tr>
        <tr> 
          <td class="NormalBold" align="left" width="50%"> First name </td>
          <td align="left" width="50%"> 
            <input type="text" name="firstName" size="20" maxlength="20" class="input">
            <xsl:attribute name="value"> <xsl:value-of select="/response/firstname"/> 
            </xsl:attribute> </input>
	   </td>
        </tr>
        <tr> 
          <td  class="NormalBold" align="left" width="50%"> Last name </td>
          <td align="left" width="50%"> 
            <input type="text" name="lastName" size="20" maxlength="20" class="input">
            <xsl:attribute name="value"> <xsl:value-of select="/response/lastname"/> 
            </xsl:attribute> </input></td>
        </tr>
        <tr> 
          <td colspan="2" align="center"> <img src="images/grey_line.jpg" /> 
          </td>
        </tr>
        <tr> 
          <td class="NormalBold"  align="left" width="50%"> Language </td>
          <td align="left" width="50%"> 
		<select name="language" class="input">
		   <xsl:for-each select="response/language/item">
		       <option>
		         <xsl:if test="selected">
		          <xsl:attribute name="selected">
		           yes
		          </xsl:attribute>
		          </xsl:if>
		          <xsl:attribute name="value"><xsl:value-of select="value"/></xsl:attribute>
		          <xsl:value-of select="text"/>
		      </option>
		   </xsl:for-each>
             </select>
             </td>
        </tr>
	 <tr> 
          <td class="NormalBold"  align="left" width="50%"> Skin </td>
          <td align="left" width="50%"> 
		<select name="skin" class="input">
		   <xsl:for-each select="response/skin/item">
		       <option>
		         <xsl:if test="selected">
		          <xsl:attribute name="selected">
		           yes
		          </xsl:attribute>
		          </xsl:if>
		          <xsl:attribute name="value"><xsl:value-of select="value"/></xsl:attribute>
		          <xsl:value-of select="text"/>
		      </option>
		   </xsl:for-each>
             </select>
             </td>
        </tr>
        <tr> 
          <td colspan="2" align="center"> <img src="images/grey_line.jpg" /> 
          </td>
        </tr>
        <tr> 
          <td align="left"> </td>
          <td align="left"> 
            <input type="submit" name="Change" value=" Change " class="input"/>
            <input type="button" name="Cancel" value=" Cancel " onclick="window.location.href='MainFrame'" class="input"/>
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