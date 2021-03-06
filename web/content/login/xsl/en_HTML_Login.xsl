<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:param name="loginMessage" select="'Welcome'"/>
	<xsl:template match="/">
		<html>
			<head>
				<title>Login to ITR</title>
				<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
				<link rel="stylesheet" href="../include/ITR.CSS"/>
				<script language="javascript">
if(parent.location.href!=window.location.href){
parent.location.href = window.location.href;
}
</script>
			</head>
			<body bgcolor="#eeeeee" leftmargin="0" rightmargin="0" topmargin="0" marginwidth="0" marginheight="0">
				<table cellspacing="0" cellpadding="0" border="0" width="100%" background="images/top_bg_login.gif">
					<tr>
						<td>
							<div id="logo" align="left" style="top: 0px;">
								<img src="images/timeportal_login.gif" width="168" height="80" border="0"/>
							</div>
						</td>
						<td>
							<div id="carphoneguy" align="right" style="top: 0px;">
								<img src="images/itr_top_login.gif" width="450" height="80"/>
							</div>
						</td>
					</tr>
				</table>
				<p/>
				<div id="Layer2" style="position:absolute; z-index:2; left: 150px; top: 80px">
					<img src="images/stonehenge_bg.jpg" width="700" height="490"/>
				</div>
				<div id="Layer1" style="position:absolute; width:255px; height:115px; z-index:5; left: 350px; top: 200px" align="center">
					<h2>Welcome to the login screen for ITR userinterface.</h2>
					<form method="POST" action="LoginVerifier">
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
									<table width="100%" border="0" cellspacing="0" cellpadding="3" bgcolor="#cccccc">
										<tr>
											<td>
												<font size="1"><b>Login</b></font>
											</td>
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
									<table width="250" border="0" cellspacing="0" cellpadding="3" height="72" bgcolor="#ededed">
										<tr>
											<td>
												<font size="1">Username:</font>
											</td>
											<td>
												<input type="text" name="loginId" class="input" size="10" maxlength="20" value=""/>
											</td>
										</tr>
										<tr>
											<td>
												<font size="1">Password:</font>
											</td>
											<td>
												<input type="password" name="password" class="input" size="10" maxlength="20" value=""/>
											</td>
										</tr>
										<tr>
											<td colspan="2" align="center">
												<xsl:value-of select="$loginMessage"/>
											</td>
										</tr>
										<tr>
											<td colspan="2" align="center">
												<input type="submit" name="Login" value="Login" class="input"/>
											</td>
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
					</form>
				</div>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>
