<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:template match="/">
		<html>
			<head>
				<meta HTTP-EQUIV="Content-Type" CONTENT="text/html;CHARSET=iso-8859-1"/>
				<meta NAME="Expires" Content="Now"/>
				<meta HTTP-EQUIV="Pragma" CONTENT="no_cache"/>
				<script language="JavaScript" src="../../helpers/en_validate.js"/>
				<script language="JavaScript">
				function validate(theForm)  {
					     return true;
				}
				</script>
				<title>Vacation Query Profile</title>
				<link rel="stylesheet" href="../include/ITR.CSS"/>
			</head>
			<body bgcolor="#eeeeee" topmargin="2" leftmargin="0" rightmargin="0" marginwidth="0" marginheight="0">
				<a name="top"/>
				<center>
					<table border="0" cellpadding="0" cellspacing="0" width="100%">
						<tr bgcolor="#aaaacc" align="center">
							<td class="Title1">Vacation Query Profile</td>
						</tr>
					</table>
					<form method="POST" action="VacationReportView" name="queryprofile" onsubmit="return validate(this)">
						<p>
							<table cellspancing="0" cellpadding="0" border="0" width="300">
								<tr>
									<td height="30" align="right" valign="bottom" colspan="2" class="NormalBold">|<a href="OnlineHelp">Help</a>|</td>
								</tr>
								<tr>
									<td colspan="2" align="center">
										<img src="images/grey_line.jpg"/>
									</td>
								</tr>
								<tr>
									<td class="NormalBold" align="left" width="50%"> Company: </td>
									<td align="left" width="50%">
										<select name="companyid" class="input" style="width:170px">
											<xsl:for-each select="response/company/item">
												<option>
													<xsl:if test="selected">
														<xsl:attribute name="selected">yes</xsl:attribute>
													</xsl:if>
													<xsl:attribute name="value"><xsl:value-of select="value"/></xsl:attribute>
													<xsl:value-of select="text"/>
												</option>
											</xsl:for-each>
										</select>
									</td>
								</tr>
								<tr>
									<td class="NormalBold" align="left" width="50%"> Project: </td>
									<td align="left" width="50%">
										<select name="projectid" class="input" style="width:170px">
											<xsl:for-each select="response/project/item">
												<option>
													<xsl:if test="selected">
														<xsl:attribute name="selected">yes</xsl:attribute>
													</xsl:if>
													<xsl:attribute name="value"><xsl:value-of select="value"/></xsl:attribute>
													<xsl:value-of select="text"/>
												</option>
											</xsl:for-each>
										</select>
									</td>
								</tr>
								<tr>
									<td class="NormalBold" align="left" width="50%"> User: </td>
									<td align="left" width="50%">
										<select name="userid" class="input" style="width:170px">
											<xsl:for-each select="response/user/item">
												<option>
													<xsl:if test="selected">
														<xsl:attribute name="selected">yes</xsl:attribute>
													</xsl:if>
													<xsl:attribute name="value"><xsl:value-of select="value"/></xsl:attribute>
													<xsl:value-of select="text"/>
												</option>
											</xsl:for-each>
										</select>
									</td>
								</tr>
								<tr>
									<td class="NormalBold" align="left" width="50%"> Year: </td>
									<td align="left" width="50%">
										<select name="year" class="input" style="width:170px">
											<xsl:for-each select="response/year/item">
												<option>
													<xsl:if test="selected">
														<xsl:attribute name="selected">yes</xsl:attribute>
													</xsl:if>
													<xsl:attribute name="value"><xsl:value-of select="value"/></xsl:attribute>
													<xsl:value-of select="text"/>
												</option>
											</xsl:for-each>
										</select>
									</td>
								</tr>
								<tr>
									<td colspan="2" align="center">
										<img src="images/grey_line.jpg"/>
									</td>
								</tr>
								<tr>
									<td/>
									<td>
										<input type="submit" name="Search " value="Search " class="input"/>&#160;
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
