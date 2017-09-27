<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:param name="userName"/>
	<xsl:template match="/">
		<html>
			<head>
				<title>CarPortal</title>
				<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
				<link rel="stylesheet" href="../include/ITR.CSS" TYPE="text/css" REL="stylesheet"/>
				<style type="text/css">
					<!--
.TopHeader {  font-family: Verdana, Arial, Helvetica, sans-serif; font-size: 12pt; font-style: normal; line-height: normal; font-weight: bold; font-variant: normal; color: #CCCCFF; text-decoration: none; letter-spacing: 3px}
-->
				</style>
			</head>
			<body bgcolor="#ffffff" background="images/skins/hiq/top_back.gif" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
				<div id="menu_bg_image" style="position:absolute; left:40px; top:7px">
					<!--Black border table start-->
					<table border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td colspan="3" bgcolor="#000000">
								<img src="images/blackdot.gif" height="1" width="1"/>
							</td>
						</tr>
						<tr>
							<td bgcolor="#000000">
								<img src="images/blackdot.gif" height="1" width="1"/>
							</td>
							<td>
								<!--Menu and header table start-->
								<table width="100%" border="0" cellspacing="0" cellpadding="1" bgcolor="#cccccc">
									<tr>
										<td class="NormalBold">&#160;User data</td>
									</tr>
								</table>
								<!--Menu and Header table end-->
							</td>
							<td bgcolor="#000000">
								<img src="images/blackdot.gif" height="1" width="1"/>
							</td>
						</tr>
						<tr>
							<td colspan="3" bgcolor="#000000">
								<img src="images/blackdot.gif" height="1" width="1"/>
							</td>
						</tr>
						<tr>
							<td bgcolor="#000000">
								<img src="images/blackdot.gif" height="1" width="1"/>
							</td>
							<td>
								<!--Body table start-->
								<table width="250" border="0" cellspacing="0" cellpadding="0" bgcolor="#ededed">
									<tr>
										<td class="NormalBold">&#160;Username:</td>
										<td class="Normal"><xsl:value-of select="/response/name"/>&#160;</td>
									</tr>
									<tr>
										<td class="NormalBold">&#160;Company:</td>
										<td class="Normal"><xsl:value-of select="/response/company"/>&#160;</td>
									</tr>
									<tr>
										<td class="NormalBold">&#160;Role:</td>
										<td class="Normal"><xsl:value-of select="/response/role"/>&#160;</td>
									</tr>
								</table>
								<!--Body table end-->
							</td>
							<td bgcolor="#000000">
								<img src="images/blackdot.gif" height="1" width="1"/>
							</td>
						</tr>
						<tr>
							<td colspan="3" bgcolor="#000000">
								<img src="images/blackdot.gif" height="1" width="1"/>
							</td>
						</tr>
					</table>
					<!--Black border table end-->
				</div>
				<div align="right">
					<img src="images/skins/hiq/itr_top.jpg" width="450" height="80"/>
				</div>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>
