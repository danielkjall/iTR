<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:template match="/">
		<html>
			<head>
				<link REL="stylesheet" TYPE="text/css" HREF="../include/ITR.CSS"/>
				<meta HTTP-EQUIV="Content-Type" CONTENT="text/html;CHARSET=iso-8859-1"/>
				<meta NAME="Expires" Content="Now"/>
				<meta HTTP-EQUIV="Pragma" CONTENT="no_cache"/>
				<title>ITR - View weeks</title>
			</head>
			<body bgcolor="#f3f3f3" leftmargin="0" rightmargin="0" topmargin="0" marginwidth="0" marginheight="0">
				<form action="#" name="weeks">
					<table border="0" cellpadding="0" cellspacing="0" width="100%">
						<tr bgcolor="#aaaacc" align="center">
							<td class="Title1">
								<xsl:value-of select="/response/title"/> Weeks</td>
						</tr>
					</table>
					<p>
						<br x="x"/>
					</p>
					<table WIDTH="100%" border="0" cellpadding="1" cellspacing="1">
						<!--Start black line, if submitted colspan = 12, else colspan = 13  -->
						<tr>
							<td>
								<xsl:attribute name="colspan"><xsl:choose><xsl:when test="/response/title != 'Submitted'">13</xsl:when></xsl:choose><xsl:choose><xsl:when test="/response/title = 'Submitted'">12</xsl:when></xsl:choose></xsl:attribute>
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
							<td width="5%">Week</td>
							<xsl:choose>
								<xsl:when test="/response/title != 'Submitted'">
									<td width="10%">Copy from submitted</td>
								</xsl:when>
							</xsl:choose>
							<td>Week comment</td>
							<td align="right" width="5%">Mon</td>
							<td align="right" width="5%">Tue</td>
							<td align="right" width="5%">Wed</td>
							<td align="right" width="5%">Thu</td>
							<td align="right" width="5%">Fri</td>
							<td align="right" width="5%">Sat</td>
							<td align="right" width="5%">Sun</td>
							<td align="right" width="5%">Total</td>
							<td align="right" width="5%">Exp.</td>
							<td align="right" width="5%">Diff.</td>
						</tr>
						<xsl:for-each select="/response/week">
							<tr>
								<xsl:choose>
									<xsl:when test="i mod 2 = 0">
										<xsl:attribute name="bgcolor">#fafafa</xsl:attribute>
									</xsl:when>
								</xsl:choose>
								<td class="Normal">
									<a>
										<xsl:attribute name="href">
										WeekReportEditorView?action=start&#38;row=<xsl:value-of select="i"/>&#38;mode=<xsl:value-of select="/response/mode"/>&#38;copySubmitted=false
										</xsl:attribute>
										<xsl:value-of select="weekno"/>_<xsl:value-of select="weekpart"/>
									</a>
								</td>
								<xsl:choose>
									<xsl:when test="/response/title != 'Submitted'">
										<td class="Normal">
											<xsl:choose>
												<xsl:when test="sumrows/totalrow/totalsum = '0.0'">
													<a>
														<xsl:attribute name="href">
												WeekReportEditorView?action=start&#38;row=<xsl:value-of select="i"/>&#38;mode=<xsl:value-of select="/response/mode"/>&#38;copySubmitted=true
												</xsl:attribute>
												Copy
											</a>
												</xsl:when>
											</xsl:choose>
										</td>
									</xsl:when>
								</xsl:choose>
								<td class="Normal">
									<xsl:value-of select="weekcomment"/>
								</td>
								<td align="right" class="Normal">
									<xsl:value-of select="sumrows/totalrow/mo"/>
								</td>
								<td align="right" class="Normal">
									<xsl:value-of select="sumrows/totalrow/tu"/>
								</td>
								<td align="right" class="Normal">
									<xsl:value-of select="sumrows/totalrow/we"/>
								</td>
								<td align="right" class="Normal">
									<xsl:value-of select="sumrows/totalrow/th"/>
								</td>
								<td align="right" class="Normal">
									<xsl:value-of select="sumrows/totalrow/fr"/>
								</td>
								<td align="right" class="Normal">
									<xsl:value-of select="sumrows/totalrow/sa"/>
								</td>
								<td align="right" class="Normal">
									<xsl:value-of select="sumrows/totalrow/su"/>
								</td>
								<td bgcolor="#E5E5E5" align="right" class="NormalBold">
									<xsl:value-of select="sumrows/totalrow/totalsum"/>
								</td>
								<td bgcolor="#E5E5E5" align="right" class="NormalBold">
									<xsl:value-of select="sumrows/expectedrow/expectedsum"/>
								</td>
								<td bgcolor="#E5E5E5" align="right" class="NormalBold">
									<xsl:value-of select="sumrows/diffrow/diffsum"/>
								</td>
							</tr>
						</xsl:for-each>
						<!--Start black line, if submitted colspan = 12, else colspan = 13-->
						<tr>
							<td>
								<xsl:attribute name="colspan"><xsl:choose><xsl:when test="/response/title != 'Submitted'">13</xsl:when></xsl:choose><xsl:choose><xsl:when test="/response/title = 'Submitted'">12</xsl:when></xsl:choose></xsl:attribute>
								<table border="0" cellpadding="0" cellspacing="0" width="100%">
									<tr>
										<td bgcolor="#000000">
											<img src="images/sp.gif" height="1" width="1"/>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<!--End grey line -->
					</table>
				</form>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>
