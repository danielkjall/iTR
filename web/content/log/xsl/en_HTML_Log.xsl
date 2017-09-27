<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:param name="LoggingLevel"/>
	<xsl:template match="/">
		<html>
			<head>
				<title>Log view</title>
				<meta HTTP-EQUIV="Content-Type" CONTENT="text/html;CHARSET=iso-8859-1"/>
				<meta NAME="Expires" Content="Now"/>
				<meta HTTP-EQUIV="Pragma" CONTENT="no_cache"/>
				<link rel="stylesheet" href="../include/ITR.CSS"/>
			</head>
			<body background="images/s60back.jpg" bgcolor="#f3f3f3" leftmargin="0" rightmargin="0" topmargin="0" marginwidth="0" marginheight="0">
				<center>
					<table width="90%" border="0" cellpadding="1" cellspacing="0" bgcolor="black">
						<tr>
							<td>
								<table width="100%" border="0" height="170" bgcolor="white" cellpadding="5">
									<tr>
										<td colspan="5" align="center">
											<h1 class="Title1" align="center">Log file, logginglevel <xsl:value-of select="$LoggingLevel"/>
											</h1>
											<form name="log">
												<table width="100%" border="0" bgcolor="white" cellpadding="5">
													<tr class="NormalBold">
														<td align="left"> Specify the highest level you want to see:&#32; 
                    </td>
													</tr>
													<tr class="NormalBold">
														<td align="left">
			  1 
                      <input type="radio" name="LoggingLevel" value="1"/>
                      2 
                      <input type="radio" name="LoggingLevel" value="2"/>
                      3 
                      <input type="radio" name="LoggingLevel" value="3"/>
                      4 
                      <input type="radio" name="LoggingLevel" value="4"/>
                      5 
                      <input type="radio" name="LoggingLevel" value="5"/>
                      6 
                      <input type="radio" name="LoggingLevel" value="6"/>
														</td>
													</tr>
													<tr class="NormalBold">
														<td align="left">
															<input class="Normal" type="submit" name="Submit" value="Filter log"/>
														</td>
													</tr>
												</table>
											</form>
										</td>
									</tr>
									<tr>
										<td class="Title5TableBlue">Class</td>
										<td class="Title5TableBlue">Level</td>
										<td class="Title5TableBlue">Message</td>
										<td class="Title5TableBlue">Time</td>
										<td class="Title5TableBlue">Log Name</td>
										<td class="Title5TableBlue">ThreadGroup</td>
										<td class="Title5TableBlue">Thread</td>
									</tr>
									<xsl:for-each select="LOGFILE/LOGEVENT">
										<xsl:choose>
											<xsl:when test="(LL &#60;= $LoggingLevel)">
												<tr>
													<xsl:choose>
														<xsl:when test="LL='2'">
															<xsl:attribute name="class">ErrorLog</xsl:attribute>
														</xsl:when>
														<xsl:when test="LL='1'">
															<xsl:attribute name="class">CriticalErrorLog</xsl:attribute>
														</xsl:when>
														<xsl:otherwise>
															<xsl:attribute name="class">DefaultLog</xsl:attribute>
														</xsl:otherwise>
													</xsl:choose>
													<td>
														<xsl:value-of select="C"/>
													</td>
													<td>
														<xsl:value-of select="LL"/>
													</td>													
													<td>
														<xsl:value-of select="M"/>
													</td>
													<td>
														<xsl:value-of select="T"/>
													</td>
													<td>
														<xsl:value-of select="L"/>
													</td>
													<td>
														<xsl:value-of select="TG"/>
													</td>
													<td>
														<xsl:value-of select="TH"/>
													</td>
													
												</tr>
											</xsl:when>
										</xsl:choose>
									</xsl:for-each>
								</table>
							</td>
						</tr>
					</table>
				</center>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>
