<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:template match="/">
		<html>
			<head>
				<link REL="stylesheet" TYPE="text/css" HREF="../include/ITR.CSS"/>
				<meta HTTP-EQUIV="Content-Type" CONTENT="text/html;CHARSET=iso-8859-1"/>
				<meta NAME="Expires" Content="Now"/>
				<meta HTTP-EQUIV="Pragma" CONTENT="no_cache"/>
				<title>ITR - General Report</title>
			</head>
			<body bgcolor="#f3f3f3" leftmargin="0" rightmargin="0" topmargin="0" marginwidth="0" marginheight="0">
				<form action="#" name="weeks">
					<table border="0" cellpadding="0" cellspacing="0" width="100%">
						<tr bgcolor="#aaaacc" align="center">
							<td class="Title1">General Report</td>
						</tr>
					</table>
					<p>
						<br x="x"/>
					</p>
					<table WIDTH="100%" border="0" cellpadding="1" cellspacing="1">
						<!--Start black line-->
						<tr>
							<td colspan="12">
								<table border="0" cellpadding="0" cellspacing="0" width="100%">
									<tr>
										<td bgcolor="#000000">
											<img src="images/sp.gif" height="1" width="1"/>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<!--End black line -->
						<tr BGCOLOR="#C4C4C4" class="NormalBold">
							<td width="4%">Year</td>
							<td width="3%">Qua.</td>
							<td width="4%">Month</td>
							<td width="4%">Week</td>
							<td width="12%">Project name</td>
							<td width="15%">Activity description</td>
							<td width="15%">Name</td>
							<td width="5%">Project admin</td>
							<td width="8%" align="right">Rate</td>
							<td width="8%" align="right">Expected hours</td>
							<td width="8%" align="right">Worked hours</td>
							<td width="15%" align="right">Week revenue</td>
						</tr>
						<xsl:for-each select="/response/row">
							<tr>
							<xsl:choose>
								<xsl:when test="i mod 2 = 0"><xsl:attribute name="bgcolor">#fafafa</xsl:attribute></xsl:when>
							</xsl:choose>
								<td class="Normal"><xsl:value-of select="year"/></td>
								<td class="Normal"><xsl:value-of select="kvartal"/></td>
								<td class="Normal"><xsl:value-of select="month"/></td>
								<td class="Normal"><xsl:value-of select="week"/></td>
								<td class="Normal"><xsl:value-of select="projectname"/></td>
								<td class="Normal"><xsl:value-of select="projectcodename"/></td>
								<td class="Normal"><xsl:value-of select="username"/></td>
								<td class="Normal"><xsl:value-of select="projadmin"/></td>
								<td class="Normal" align="right"><xsl:value-of select="rate"/></td>
								<td class="Normal" align="right"><xsl:value-of select="expectedh"/></td>
								<td class="Normal" align="right"><xsl:value-of select="workedh"/></td>
								<td class="Normal" align="right"><xsl:value-of select="revenue"/></td>
							</tr>
						</xsl:for-each>
						<!--Start black line-->
						<tr>
							<td colspan="12">
								<table border="0" cellpadding="0" cellspacing="0" width="100%">
									<tr>
										<td bgcolor="#000000">
											<img src="images/sp.gif" height="1" width="1"/>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<!--End black line -->
						<!--<tr bgcolor="#C4C4C4">-->
						<tr>
							<td colspan="8"></td>
							<td class="NormalBold" align="right"><xsl:value-of select="/response/avgrate"/></td>
							<td class="NormalBold" align="right"><xsl:value-of select="/response/sumexpectedh"/></td>
							<td class="NormalBold" align="right"><xsl:value-of select="/response/sumworkedh"/></td>
							<td class="NormalBold" align="right"><xsl:value-of select="/response/sumrevenues"/></td>

						</tr>

					</table>
				</form>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>
