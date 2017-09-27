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
	  	 
	//fromDate
        if ( !isValid(theForm.fromdate, "From Date") )
            return false;
	//toDate
       else if ( !isValid(theForm.todate, "To Date") )
            return false;
	 else 
	     return true;
}
</script>
<title>General Query Profile</title>
<link rel="stylesheet" href="../include/ITR.CSS" />
</head>
<body bgcolor="#eeeeee" topmargin="2" leftmargin="0" rightmargin="0" marginwidth="0" marginheight="0" >
<a name="top"></a>
<center>
<table border="0" cellpadding="0" cellspacing="0" width="100%">
<tr bgcolor="#aaaacc" align="center">
        <td class="Title1">General Query Profile</td>
</tr></table>

<form method="POST" action="GeneralReportView" name="queryprofile"  onsubmit="return validate(this)">
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
          <td class="NormalBold" align="left" width="50%"> User: </td>
          <td align="left" width="50%"> 
            <select name="userid" class="input">
		   <xsl:for-each select="response/user/item">
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
             </select>	   </td>
        </tr>
        <tr> 
          <td  class="NormalBold" align="left" width="50%"> Project: </td>
          <td align="left" width="50%"> 
            <select name="projectid" class="input">
		   <xsl:for-each select="response/project/item">
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
          <td class="NormalBold"  align="left" width="50%"> Project code: </td>
          <td align="left" width="50%"> 
		<select name="projectcodeid" class="input">
		   <xsl:for-each select="response/projectcode/item">
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
          <td class="NormalBold"  align="left" width="50%"> From date: </td>
          <td align="left" width="50%"> 
            <input type="text" name="fromdate" size="10" maxlength="10" class="input">
            <xsl:attribute name="value"> <xsl:value-of select="/response/fromdate"/> 
            </xsl:attribute> </input></td>
 	</tr>
	<tr> 
          <td class="NormalBold"  align="left" width="50%"> To date: </td>
          <td align="left" width="50%"> 
            <input type="text" name="todate" size="10" maxlength="10" class="input">
            <xsl:attribute name="value"> <xsl:value-of select="/response/todate"/> 
            </xsl:attribute> </input></td>
 	</tr>
        <tr> 
          <td colspan="2" align="center"> <img src="images/grey_line.jpg" /> 
          </td>
        </tr>
        <tr> 
          <td></td>
          <td align="left"> 
            <input type="submit" name="Change" value=" Search " class="input"/>
            <input type="button" name="Cancel" value=" Cancel " onclick="javascript:window.history.back(-1)" class="input"/>
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