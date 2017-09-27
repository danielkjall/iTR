<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:template match="/">
		<html>
			<head>
				<link REL="stylesheet" TYPE="text/css" HREF="../include/ITR.CSS"/>
				<meta HTTP-EQUIV="Content-Type" CONTENT="text/html;CHARSET=iso-8859-1"/>
				<meta NAME="Expires" Content="Now"/>
				<meta HTTP-EQUIV="Pragma" CONTENT="no_cache"/>
				<title>ITR - Vacation Report</title>
			</head>
			<body bgcolor="#f3f3f3" leftmargin="0" rightmargin="0" topmargin="0" marginwidth="0" marginheight="0">
				<form action="#" name="weeks">
					<table border="0" cellpadding="0" cellspacing="0" width="100%">
						<tr bgcolor="#aaaacc" align="center">
							<td class="Title1">Vacation Report <xsl:value-of select="/response/year"/>
							</td>
						</tr>
					</table>
					<br x="x"/>
					<!-- Start of summary box -->
					<table border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td colspan="3" bgcolor="#000000">
								<img src="images/sp.gif" height="1" width="1"/>
							</td>
						</tr>
						<tr>
							<td bgcolor="#000000">
								<img src="images/sp.gif" height="1" width="1"/>
							</td>
							<td>
								<!--Menu and header table start-->
								<table width="100%" border="0" cellspacing="0" cellpadding="1" bgcolor="#cccccc">
									<tr>
										<td class="NormalBold">&#160;Summary</td>
									</tr>
								</table>
								<!--Menu and Header table end-->
							</td>
							<td bgcolor="#000000">
								<img src="images/sp.gif" height="1" width="1"/>
							</td>
						</tr>
						<tr>
							<td colspan="3" bgcolor="#000000">
								<img src="images/sp.gif" height="1" width="1"/>
							</td>
						</tr>
						<tr>
							<td bgcolor="#000000">
								<img src="images/sp.gif" height="1" width="1"/>
							</td>
							<td>
								<table width="250" border="0" cellspacing="0" cellpadding="0" bgcolor="#ffffff">
									<tr>
										<td class="Normal">&#160;Total OT:&#160;</td>
										<td class="Normal" align="right">
											<xsl:value-of select="/response/sumovertimeh"/> hours
													</td>
									</tr>
									<tr>
										<td class="Normal">&#160;OT as money:&#160;</td>
										<td class="Normal" align="right">
											<xsl:value-of select="/response/summoneyh"/> hours
													</td>
									</tr>
									<tr>
										<td>
											<img src="images/sp.gif" height="1" width="1"/>
										</td>
										<td bgcolor="#000000">
											<img src="images/sp.gif" height="1" width="1"/>
										</td>
									</tr>
									<tr>
										<td class="Normal">&#160;Remaining OT:&#160;</td>
										<td class="Normal" align="right">
											<xsl:value-of select="/response/sumvacationh"/> hours
													</td>
									</tr>
									<tr>
										<td colspan="2" bgcolor="#000000">
											<img src="images/sp.gif" height="1" width="1"/>
										</td>
									</tr>
									<tr>
										<td class="Normal">&#160;Worked hours:&#160;</td>
										<td class="Normal" align="right">
											<xsl:value-of select="/response/sumworkedh"/> hours
													</td>
									</tr>
								</table>
							</td>
							<td bgcolor="#000000">
								<img src="images/sp.gif" height="1" width="1"/>
							</td>
						</tr>
						<tr>
							<td colspan="3" bgcolor="#000000">
								<img src="images/sp.gif" height="1" width="1"/>
							</td>
						</tr>
					</table>
					<!-- End of summary box -->
					<br x="x"/>
					<!-- Start of table-->
					<table border="0" cellpadding="1" cellspacing="1" width="100%">
						<!--Start black line-->
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
						<!--End black line -->
						<tr BGCOLOR="#C4C4C4">
							<td class="Title4">User information</td>
							<td class="Title4">Hour statistics for your search criteria</td>
						</tr>
						<xsl:for-each select="/response/vacationtable">
							<tr>
								<td colspan="2">&#160;</td>
							</tr>
							<tr>
								<td valign="top">
									<table border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td colspan="3" bgcolor="#000000">
												<img src="images/sp.gif" height="1" width="1"/>
											</td>
										</tr>
										<tr>
											<td bgcolor="#000000">
												<img src="images/sp.gif" height="1" width="1"/>
											</td>
											<td>
												<!--Menu and header table start-->
												<table width="100%" border="0" cellspacing="0" cellpadding="1" bgcolor="#cccccc">
													<tr>
														<td class="NormalBold">&#160;<xsl:value-of select="username"/>, <xsl:value-of select="company"/>
														</td>
													</tr>
												</table>
												<!--Menu and Header table end-->
											</td>
											<td bgcolor="#000000">
												<img src="images/sp.gif" height="1" width="1"/>
											</td>
										</tr>
										<tr>
											<td colspan="3" bgcolor="#000000">
												<img src="images/sp.gif" height="1" width="1"/>
											</td>
										</tr>
										<tr>
											<td bgcolor="#000000">
												<img src="images/sp.gif" height="1" width="1"/>
											</td>
											<td>
												<table width="250" border="0" cellspacing="0" cellpadding="0" bgcolor="#ffffff">
													<tr>
														<td class="Normal">&#160;Yearly vacation:&#160;</td>
														<td class="Normal" align="right">
															<xsl:value-of select="defaultdays"/> days
													</td>
													</tr>
													<tr>
														<td class="Normal">&#160;Saved vacation:&#160;</td>
														<td class="Normal" align="right">
															<xsl:value-of select="saveddays"/> days
													</td>
													</tr>
													<tr>
														<td class="Normal">&#160;Used vacation:&#160;</td>
														<td class="Normal" align="right">
															<xsl:value-of select="useddays"/> days
													</td>
													</tr>
													<tr>
														<td>
															<img src="images/sp.gif" height="1" width="1"/>
														</td>
														<td bgcolor="#000000">
															<img src="images/sp.gif" height="1" width="1"/>
														</td>
													</tr>
													<tr>
														<td class="Normal">&#160;Remaining vacation:&#160;</td>
														<td class="Normal" align="right">
															<xsl:value-of select="remainingdays"/> days
													</td>
													</tr>
													<tr>
														<td colspan="2" bgcolor="#000000">
															<img src="images/sp.gif" height="1" width="1"/>
														</td>
													</tr>
													<tr>
														<td class="Normal">&#160;Total OT:&#160;</td>
														<td class="Normal" align="right">
															<xsl:value-of select="totalh"/> hours
													</td>
													</tr>
													<tr>
														<td class="Normal">&#160;OT as money:&#160;</td>
														<td class="Normal" align="right">
															<xsl:value-of select="moneyh"/> hours
													</td>
													</tr>
													<tr>
														<td>
															<img src="images/sp.gif" height="1" width="1"/>
														</td>
														<td bgcolor="#000000">
															<img src="images/sp.gif" height="1" width="1"/>
														</td>
													</tr>
													<tr>
														<td class="Normal">&#160;Remaining OT:&#160;</td>
														<td class="Normal" align="right">
															<xsl:value-of select="vacationh"/> hours
													</td>
													</tr>
												</table>
											</td>
											<td bgcolor="#000000">
												<img src="images/sp.gif" height="1" width="1"/>
											</td>
										</tr>
										<tr>
											<td colspan="3" bgcolor="#000000">
												<img src="images/sp.gif" height="1" width="1"/>
											</td>
										</tr>
									</table>
								</td>
								<td valign="top" align="right">
									<table border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td colspan="3" bgcolor="#000000">
												<img src="images/sp.gif" height="1" width="1"/>
											</td>
										</tr>
										<tr>
											<td bgcolor="#000000">
												<img src="images/sp.gif" height="1" width="1"/>
											</td>
											<td>
												<table width="500" border="0" cellpadding="1" cellspacing="2">
													<tr BGCOLOR="#C4C4C4" class="NormalBold">
														<td width="200">Project</td>
														<td width="200">Time type</td>
														<td width="100" align="right">Worked hours</td>
													</tr>
													<xsl:for-each select="row">
														<tr>
															<xsl:choose>
																<xsl:when test="i mod 2 = 0">
																	<xsl:attribute name="bgcolor">#fafafa</xsl:attribute>
																</xsl:when>
															</xsl:choose>
															<td class="Normal">
																<xsl:value-of select="projectname"/>
															</td>
															<td class="Normal">
																<xsl:value-of select="timetype"/>
															</td>
															<td class="Normal" align="right">
																<xsl:value-of select="workedh"/>
															</td>
														</tr>
													</xsl:for-each>
													<tr>
														<td colspan="3" class="NormalBold" align="right">
															<xsl:value-of select="sumworkedh"/>
														</td>
													</tr>
												</table>
											</td>
											<td bgcolor="#000000">
												<img src="images/sp.gif" height="1" width="1"/>
											</td>
										</tr>
										<tr>
											<td colspan="3" bgcolor="#000000">
												<img src="images/sp.gif" height="1" width="1"/>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</xsl:for-each>
						<!--Start black line-->
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
						<!--End black line -->
					</table>
				</form>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>
