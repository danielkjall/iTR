<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:template match="/">
		<html>
			<head>
				<meta HTTP-EQUIV="Content-Type" CONTENT="text/html;CHARSET=iso-8859-1"/>
				<meta NAME="Expires" Content="Now"/>
				<meta HTTP-EQUIV="Pragma" CONTENT="no_cache"/>
				<title>Submit Result</title>
				<link rel="stylesheet" href="../include/ITR.CSS"/>
			</head>
			<body bgcolor="#eeeeee" topmargin="2" leftmargin="0" rightmargin="0" marginwidth="0" marginheight="0">
				<center>
					<table border="0" cellpadding="0" cellspacing="0" width="100%">
						<tr bgcolor="#aaaacc" align="center">
							<td class="Title1">Submit Result</td>
						</tr>
					</table>
					<p>
						<table cellspacing="2" cellpadding="0" border="0" width="300" align="center">
							<tr>
								<td height="30" align="right" valign="bottom" colspan="2" class="NormalBold"> 
            |<a href="OnlineHelp">Help</a>| </td>
							</tr>
							<tr>
								<td colspan="2">
									<table border="0" cellpadding="0" cellspacing="0" width="100%">
										<tr>
											<td bgcolor="#000000">
												<img src="images/sp.gif" height="1" width="1"/>
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td colspan="2" class="Normal" width="100%" align="center">
									<table width="100%" border="0" cellspacing="0" cellpadding="1" bgcolor="#ffffff">
										<tr>
											<td class="NormalBold">&#160;Date:</td>
											<td nowrap="true" class="Normal">
												<xsl:value-of select="/response/month"/>&#160;
												<xsl:value-of select="/response/year"/>
<!-- Hidden field with week comments for keeping it in the request to the next page.-->
												<form action="" name="hiddenForm" method="post">
													<input type="hidden" name="comments"/>
														<xsl:attribute name="value"><xsl:value-of select="/response/weekcomment"/></xsl:attribute>
												</form>
											</td>
											<td class="NormalBold">&#160;Week:</td>
											<td nowrap="true" class="Normal">
												<xsl:value-of select="/response/weekno"/> (<xsl:value-of select="/response/weekpart"/>)&#160;
										</td>
										</tr>
										<tr>
											<td nowrap="true" class="NormalBold">&#160;Range:</td>
											<td nowrap="true" colspan="3" class="Normal">
												<xsl:value-of select="/response/date"/>&#160;
										</td>
										</tr>
										<tr>
											<td nowrap="true" class="NormalBold">&#160;Expected hours:</td>
											<td nowrap="true" colspan="3" class="Normal">
												<xsl:value-of select="/response/expectedhours"/>&#160;
										</td>
										</tr>
										<tr>
											<td nowrap="true" class="NormalBold">&#160;Normal hours:</td>
											<td nowrap="true" colspan="3" class="Normal">
												<xsl:value-of select="/response/normalhours"/>&#160;
										</td>
										</tr>
									</table>
									</td>
									</tr>
									<tr>
									<td colspan="2" align="center" class="Normal">
									<br x="x"/>
									Tried to submit your week report, 
									but your normal hours does not 
									corresponed to expected hours.
									<br x="x"/>
									<br x="x"/>
									Do you still want to submit your week report?
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<table border="0" cellpadding="0" cellspacing="0" width="100%">
										<tr>
											<td bgcolor="#000000">
												<img src="images/sp.gif" height="1" width="1"/>
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td colspan="2" align="center">
									<input class="input" type="button" name="yes" value="&#160;Yes&#160;" onclick="window.location.href='SaveSubmitWeekReport?action=forceSubmit'"/>
									&#160;&#160;
									<input class="input" type="button" name="no" value="&#160;No&#160;" onclick="window.location.href='WeekReportEditorView?action=cancelSubmit'"/>
								</td>
							</tr>
						</table>
					</p>
				</center>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>
