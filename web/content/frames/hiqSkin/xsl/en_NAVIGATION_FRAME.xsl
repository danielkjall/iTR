<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:template match="/">
		<HTML>
			<HEAD>
				<TITLE>topNavigationFrame</TITLE>
				<META NAME="generator" CONTENT="ITR time portal"/>
				<LINK REL="stylesheet" TYPE="text/css" HREF="../include/ITR.CSS"/>
				<script language="JavaScript" src="../helpers/navBar.js"/>
			</HEAD>
			<BODY BGCOLOR="#FFFFFF" MARGINHEIGHT="0" MARGINWIDTH="0" LEFTMARGIN="2" TEXT="#333300" TOPMARGIN="0" BACKGROUND="images/skins/hiq/menu_back.gif">
				<xsl:attribute name="onload">init(<xsl:value-of select="/response/nom"/>)</xsl:attribute>
				<div id="menu_bg_image" style="position:absolute; left:2px; top:261px; z-index:-99">
					<img src="images/skins/hiq/menu_ur.gif" width="164" height="171"/>
				</div>


				<xsl:choose>
					<xsl:when test="/response/menubasic = 'true'">
				<div class="title" id="head" style="top: 0px;background-color: #cccccc; layer-background-color: #cccccc">
					<TABLE WIDTH="150" CELLPADDING="0" CELLSPACING="0" BORDER="0" height="20">
						<tr>
							<td HEIGHT="1" COLSPAN="5" BGCOLOR="#000000">
								<IMG src="images/sp.gif" width="1" height="1"/>
							</td>
						</tr>
						<TR>
							<TD WIDTH="1" BGCOLOR="#000000">
								<IMG SRC="images/sp.gif" WIDTH="1" HEIGHT="1"/>
							</TD>
							<TD width="13">&#32;</TD>
							<TD NOWRAP="YES" width="129" class="portalParagraph"> TimePortal</TD>
							<TD WIDTH="7" VALIGN="top">
								<IMG SRC="images/sp.gif" WIDTH="1" HEIGHT="1"/>
							</TD>
							<TD WIDTH="1" BGCOLOR="#000000">
								<IMG SRC="images/sp.gif" WIDTH="1" HEIGHT="1"/>
							</TD>
						</TR>
						<!--<tr><td HEIGHT="1" COLSPAN="5" BGCOLOR="#000000"><IMG src="images/sp.gif" width="1" height="1"/></td></tr>-->
					</TABLE>
				</div>
				<!-- Start of Time Report Title -->
				<div class="title" id="title1" style="top: 20px">
					<TABLE WIDTH="150" CELLPADDING="0" CELLSPACING="0" BORDER="0" height="20">
						<tr>
							<td HEIGHT="1" COLSPAN="5" BGCOLOR="#000000">
								<IMG src="images/sp.gif" width="1" height="1"/>
							</td>
						</tr>
						<TR>
							<TD WIDTH="1" BGCOLOR="#000000">
								<IMG SRC="images/sp.gif" WIDTH="1" HEIGHT="1"/>
							</TD>
							<TD>
								<A HREF="#" onClick="toggle(1,36); return false; MM_nbGroup('out');" onMouseOver="MM_nbGroup('over','pic1','../images/navBarDownArrowOver.gif','',1)" onMouseOut="MM_nbGroup('out')">
									<IMG name="pic1" SRC="images/navBarUpArrow.gif" WIDTH="13" HEIGHT="13" BORDER="0" ALT=""/>
								</A>
							</TD>
							<TD NOWRAP="YES">
								<A class="parent" onMouseOver="MM_nbGroup('over','pic1','../images/navBarDownArrowOver.gif','',1)" onMouseOut="MM_nbGroup('out')" onClick="toggle(1,36);return false" HREF="#">Time Report</A>
							</TD>
							<TD WIDTH="148"/>
							<TD WIDTH="1" BGCOLOR="#000000">
								<IMG SRC="images/sp.gif" WIDTH="1" HEIGHT="1"/>
							</TD>
						</TR>
						<!--<tr><td HEIGHT="1" COLSPAN="5" BGCOLOR="#000000"><IMG src="images/sp.gif" width="1" height="1"/></td></tr>-->
					</TABLE>
				</div>
				<!-- End of Time Report Title-->
				<!-- Start of Time Report submenu1-->
				<div class="submenu" id="submenu1" style="top: 40px">
					<table width="150" cellpadding="0" cellspacing="0" border="0">
						<tr>
							<td HEIGHT="1" COLSPAN="3" BGCOLOR="#000000">
								<IMG src="images/sp.gif" width="1" height="1"/>
							</td>
						</tr>
						<tr>
							<TD WIDTH="1" BGCOLOR="#000000">
								<IMG SRC="images/sp.gif" WIDTH="1" HEIGHT="1"/>
							</TD>
							<td width="148">
								<IMG SRC="images/navBarBullet.gif" WIDTH="16" HEIGHT="8" BORDER="0" ALT=""/>
								<A CLASS="childLink" target="mainFrame" href="WeekReportListView?mode=submitted"> Submitted</A>
							</td>
							<TD WIDTH="1" BGCOLOR="#000000">
								<IMG SRC="images/sp.gif" WIDTH="1" HEIGHT="1"/>
							</TD>
						</tr>
						<tr>
							<TD WIDTH="1" BGCOLOR="#000000">
								<IMG SRC="images/sp.gif" WIDTH="1" HEIGHT="1"/>
							</TD>
							<td width="148">
								<IMG SRC="images/navBarBullet.gif" WIDTH="16" HEIGHT="8" BORDER="0" ALT=""/>
								<A CLASS="childLink" target="mainFrame" href="WeekReportListView?mode=todo"> Todo</A>
							</td>
							<TD WIDTH="1" BGCOLOR="#000000">
								<IMG SRC="images/sp.gif" WIDTH="1" HEIGHT="1"/>
							</TD>
						</tr>
						<tr>
							<TD WIDTH="1" BGCOLOR="#000000">
								<IMG SRC="images/sp.gif" WIDTH="1" HEIGHT="1"/>
							</TD>
							<td width="148">
								<IMG SRC="images/navBarBullet.gif" WIDTH="16" HEIGHT="8" BORDER="0" ALT=""/>
								<A CLASS="childLink" target="mainFrame" href="WeekReportListView?mode=future"> Future</A>
							</td>
							<TD WIDTH="1" BGCOLOR="#000000">
								<IMG SRC="images/sp.gif" WIDTH="1" HEIGHT="1"/>
							</TD>
						</tr>
					</table>
				</div>
				<!-- End of Time Report submenu1-->
				<!-- Start of Settings Title-->
				<div class="title" id="title2" style="top: 40px">
					<TABLE WIDTH="150" CELLPADDING="0" CELLSPACING="0" BORDER="0" height="20">
						<tr>
							<td HEIGHT="1" COLSPAN="5" BGCOLOR="#000000">
								<IMG src="images/sp.gif" width="1" height="1"/>
							</td>
						</tr>
						<TR>
							<TD WIDTH="1" BGCOLOR="#000000">
								<IMG SRC="images/sp.gif" WIDTH="1" HEIGHT="1"/>
							</TD>
							<TD>
								<A HREF="#" onclick="toggle(2,36);return false;MM_nbGroup('out');" onMouseOver="MM_nbGroup('over','pic2','../images/navBarDownArrowOver.gif','',1)" onMouseOut="MM_nbGroup('out')">
									<IMG name="pic2" SRC="images/navBarUpArrow.gif" WIDTH="13" HEIGHT="13" BORDER="0" ALT=""/>
								</A>
							</TD>
							<TD NOWRAP="YES">
								<A class="parent" onMouseOver="MM_nbGroup('over','pic2','../images/navBarDownArrowOver.gif','',1)" onMouseOut="MM_nbGroup('out')" onClick="toggle(2,36);return false" HREF="#">Settings</A>
							</TD>
							<TD WIDTH="148"/>
							<TD WIDTH="1" BGCOLOR="#000000">
								<IMG SRC="images/sp.gif" WIDTH="1" HEIGHT="1"/>
							</TD>
						</TR>
						<!--<tr><td HEIGHT="1" COLSPAN="5" BGCOLOR="#000000"><IMG src="images/sp.gif" width="1" height="1"/></td></tr>-->
					</TABLE>
				</div>
				<!-- End of Settings Title-->
				<!-- Start of Settings submenu2-->
				<div class="submenu" id="submenu2" style="top: 60px">
					<table width="150" cellpadding="0" cellspacing="0" border="0">
						<tr>
							<td HEIGHT="1" COLSPAN="3" BGCOLOR="#000000">
								<IMG src="images/sp.gif" width="1" height="1"/>
							</td>
						</tr>
						<tr>
							<TD WIDTH="1" BGCOLOR="#000000">
								<IMG SRC="images/sp.gif" WIDTH="1" HEIGHT="1"/>
							</TD>
							<td width="148">
								<IMG SRC="images/navBarBullet.gif" WIDTH="16" HEIGHT="8" BORDER="0" ALT=""/>
								<a HREF="PasswordChangerView" target="mainFrame" CLASS="childLink"> Change Password</a>
								<br x="x"/>
							</td>
							<TD WIDTH="1" BGCOLOR="#000000">
								<IMG SRC="images/sp.gif" WIDTH="1" HEIGHT="1"/>
							</TD>
						</tr>
						<tr>
							<TD WIDTH="1" BGCOLOR="#000000">
								<IMG SRC="images/sp.gif" WIDTH="1" HEIGHT="1"/>
							</TD>
							<td width="148">
								<IMG SRC="images/navBarBullet.gif" WIDTH="16" HEIGHT="8" BORDER="0" ALT=""/>
								<a HREF="ProfileChangerView" target="mainFrame" CLASS="childLink"> User Settings</a>
								<br x="x"/>
							</td>
							<TD WIDTH="1" BGCOLOR="#000000">
								<IMG SRC="images/sp.gif" WIDTH="1" HEIGHT="1"/>
							</TD>
						</tr>
					</table>
				</div>
				<!-- End of Settings submenu2-->
				<!-- Start of Generate Report Title3-->
				<div class="title" id="title3" style="top: 60px">
					<TABLE WIDTH="150" CELLPADDING="0" CELLSPACING="0" BORDER="0" height="21">
						<tr>
							<td HEIGHT="1" COLSPAN="5" BGCOLOR="#000000">
								<IMG src="images/sp.gif" width="1" height="1"/>
							</td>
						</tr>
						<TR>
							<TD WIDTH="1" BGCOLOR="#000000">
								<IMG SRC="images/sp.gif" WIDTH="1" HEIGHT="1"/>
							</TD>
							<TD>
								<A HREF="#" onClick="toggle(3,36); return false" onMouseOver="MM_nbGroup('over','pic3','../images/navBarRightArrowOver.gif','',1)" onMouseOut="MM_nbGroup('out')">
									<IMG name="pic3" SRC="images/navBarUpArrow.gif" WIDTH="13" HEIGHT="13" BORDER="0" ALT=""/>
								</A>
							</TD>
							<TD NOWRAP="YES">
								<A class="parent" onMouseOver="MM_nbGroup('over','pic3','../images/navBarRightArrowOver.gif','',1)" onMouseOut="MM_nbGroup('out')" HREF="ListReportsView" target="mainFrame">Generate Reports</A>
							</TD>
							<TD WIDTH="148"/>
							<TD WIDTH="1" BGCOLOR="#000000">
								<IMG SRC="images/sp.gif" WIDTH="1" HEIGHT="1"/>
							</TD>
						</TR>
						<tr>
							<td HEIGHT="1" COLSPAN="5" BGCOLOR="#000000">
								<IMG src="images/sp.gif" width="1" height="1"/>
							</td>
						</tr>
					</TABLE>
				</div>
				<!-- End of Generate Report Title3-->
					</xsl:when>
				</xsl:choose>


				<xsl:choose>
					<xsl:when test="/response/menuadmin = 'true'">

				<!-- Start of Admin Title4-->
				<div class="title" id="title4" style="top: 80px">
					<TABLE WIDTH="150" CELLPADDING="0" CELLSPACING="0" BORDER="0" height="20">
						<tr>
							<td HEIGHT="1" COLSPAN="5" BGCOLOR="#000000">
								<IMG src="images/sp.gif" width="1" height="1"/>
							</td>
						</tr>
						<TR>
							<TD WIDTH="1" BGCOLOR="#000000">
								<IMG SRC="images/sp.gif" WIDTH="1" HEIGHT="1"/>
							</TD>
							<TD>
								<A HREF="#" onclick="toggle(4,36);return false;MM_nbGroup('out');" onMouseOver="MM_nbGroup('over','pic4','../images/navBarDownArrowOver.gif','',1)" onMouseOut="MM_nbGroup('out')">
									<IMG name="pic4" SRC="images/navBarUpArrow.gif" WIDTH="13" HEIGHT="13" BORDER="0" ALT=""/>
								</A>
							</TD>
							<TD NOWRAP="YES">
								<A class="parent" onMouseOver="MM_nbGroup('over','pic4','../images/navBarDownArrowOver.gif','',1)" onMouseOut="MM_nbGroup('out')" onClick="toggle(4,36);return false" HREF="#">Admin</A>
							</TD>
							<TD WIDTH="148"/>
							<TD WIDTH="1" BGCOLOR="#000000">
								<IMG SRC="images/sp.gif" WIDTH="1" HEIGHT="1"/>
							</TD>
						</TR>
						<tr>
							<td HEIGHT="1" COLSPAN="5" BGCOLOR="#000000">
								<IMG src="images/sp.gif" width="1" height="1"/>
							</td>
						</tr>
					</TABLE>
				</div>
				<!-- End of Admin Title4-->
				<!-- Start of Admin submenu5-->
				<div class="submenu" id="submenu4" style="top: 100px">
					<table width="150" cellpadding="0" cellspacing="0" border="0">
						<!--<tr>
							<td HEIGHT="1" COLSPAN="3" BGCOLOR="#000000">
								<IMG src="images/sp.gif" width="1" height="1"/>
							</td>
						</tr>-->
						<tr>
							<TD WIDTH="1" BGCOLOR="#000000">
								<IMG SRC="images/sp.gif" WIDTH="1" HEIGHT="1"/>
							</TD>
							<td width="148">
								<IMG SRC="images/navBarBullet.gif" WIDTH="16" HEIGHT="8" BORDER="0" ALT=""/>
								<a HREF="ApproveWeeksView" target="mainFrame" CLASS="childLink"> Approve weeks </a>
								<br x="x"/>
							</td>
							<TD WIDTH="1" BGCOLOR="#000000">
								<IMG SRC="images/sp.gif" WIDTH="1" HEIGHT="1"/>
							</TD>
						</tr>
						<tr>
							<td HEIGHT="1" COLSPAN="5" BGCOLOR="#000000">
								<IMG src="images/sp.gif" width="1" height="1"/>
							</td>
						</tr>
					</table>
				</div>
				<!-- End of Admin submenu4-->
					</xsl:when>
				</xsl:choose>



				<xsl:choose>
					<xsl:when test="/response/menusuperadmin = 'true'">

				<!-- Start of SuperAdmin Title5-->
				<div class="title" id="title5" style="top: 100px">
					<TABLE WIDTH="150" CELLPADDING="0" CELLSPACING="0" BORDER="0" height="20">
						<TR>
							<TD WIDTH="1" BGCOLOR="#000000">
								<IMG SRC="images/sp.gif" WIDTH="1" HEIGHT="1"/>
							</TD>
							<TD>
								<A HREF="#" onclick="toggle(5,36);return false;MM_nbGroup('out');" onMouseOver="MM_nbGroup('over','pic5','../images/navBarDownArrowOver.gif','',1)" onMouseOut="MM_nbGroup('out')">
									<IMG name="pic5" SRC="images/navBarUpArrow.gif" WIDTH="13" HEIGHT="13" BORDER="0" ALT=""/>
								</A>
							</TD>
							<TD NOWRAP="YES">
								<A class="parent" onMouseOver="MM_nbGroup('over','pic5','../images/navBarDownArrowOver.gif','',1)" onMouseOut="MM_nbGroup('out')" onClick="toggle(5,36);return false" HREF="#">Super Admin</A>
							</TD>
							<TD WIDTH="148"/>
							<TD WIDTH="1" BGCOLOR="#000000">
								<IMG SRC="images/sp.gif" WIDTH="1" HEIGHT="1"/>
							</TD>
						</TR>
						<tr>
							<td HEIGHT="1" COLSPAN="5" BGCOLOR="#000000">
								<IMG src="images/sp.gif" width="1" height="1"/>
							</td>
						</tr>
					</TABLE>
				</div>
				<!-- End of SuperAdmin Title4-->
				
				<!-- Start of SuperAdmin submenu5-->
				<div class="submenu" id="submenu5" style="top: 120px">
					<table width="150" cellpadding="0" cellspacing="0" border="0">
						<!--<tr>
							<td HEIGHT="1" COLSPAN="3" BGCOLOR="#000000">
								<IMG src="images/sp.gif" width="1" height="1"/>
							</td>
						</tr>-->
						<tr>
							<TD WIDTH="1" BGCOLOR="#000000">
								<IMG SRC="images/sp.gif" WIDTH="1" HEIGHT="1"/>
							</TD>
							<td width="148">
								<IMG SRC="images/navBarBullet.gif" WIDTH="16" HEIGHT="8" BORDER="0" ALT=""/>
								Users
								<br x="x"/>
							</td>
							<TD WIDTH="1" BGCOLOR="#000000">
								<IMG SRC="images/sp.gif" WIDTH="1" HEIGHT="1"/>
							</TD>
						</tr>
						<tr>
							<TD WIDTH="1" BGCOLOR="#000000">
								<IMG SRC="images/sp.gif" WIDTH="1" HEIGHT="1"/>
							</TD>
							<td width="148">
								<!--<IMG SRC="images/navBarBullet.gif" WIDTH="16" HEIGHT="8" BORDER="0" ALT=""/>-->
								<a HREF="UserQueryView" target="mainFrame" CLASS="childLink">&#160;&#160;&#160;&#160;&#160;&#160; Modify User </a>
								<br x="x"/>
							</td>
							<TD WIDTH="1" BGCOLOR="#000000">
								<IMG SRC="images/sp.gif" WIDTH="1" HEIGHT="1"/>
							</TD>
						</tr>
						<tr>
							<TD WIDTH="1" BGCOLOR="#000000">
								<IMG SRC="images/sp.gif" WIDTH="1" HEIGHT="1"/>
							</TD>
							<td width="148">
								<!--<IMG SRC="images/navBarBullet.gif" WIDTH="16" HEIGHT="8" BORDER="0" ALT=""/>-->
								<a HREF="EmailQueryView?action=users" target="mainFrame" CLASS="childLink"> &#160;&#160;&#160;&#160;&#160;&#160; User emails </a>
								<br x="x"/>
							</td>
							<TD WIDTH="1" BGCOLOR="#000000">
								<IMG SRC="images/sp.gif" WIDTH="1" HEIGHT="1"/>
							</TD>
						</tr>
						<tr>
							<TD WIDTH="1" BGCOLOR="#000000">
								<IMG SRC="images/sp.gif" WIDTH="1" HEIGHT="1"/>
							</TD>
							<td width="148">
								<!--<IMG SRC="images/navBarBullet.gif" WIDTH="16" HEIGHT="8" BORDER="0" ALT=""/>-->
								<a HREF="PhoneQueryView?action=users" target="mainFrame" CLASS="childLink"> &#160;&#160;&#160;&#160;&#160;&#160; User phones </a>
								<br x="x"/>
							</td>
							<TD WIDTH="1" BGCOLOR="#000000">
								<IMG SRC="images/sp.gif" WIDTH="1" HEIGHT="1"/>
							</TD>
						</tr>

						<tr>
							<TD WIDTH="1" BGCOLOR="#000000">
								<IMG SRC="images/sp.gif" WIDTH="1" HEIGHT="1"/>
							</TD>
							<td width="148">
								<IMG SRC="images/navBarBullet.gif" WIDTH="16" HEIGHT="8" BORDER="0" ALT=""/>
								<a HREF="CompanyQueryView" target="mainFrame" CLASS="childLink"> Companies </a>
								<br x="x"/>
							</td>
							<TD WIDTH="1" BGCOLOR="#000000">
								<IMG SRC="images/sp.gif" WIDTH="1" HEIGHT="1"/>
							</TD>
						</tr>
						<tr>
							<TD WIDTH="1" BGCOLOR="#000000">
								<IMG SRC="images/sp.gif" WIDTH="1" HEIGHT="1"/>
							</TD>
							<td width="148">
								<IMG SRC="images/navBarBullet.gif" WIDTH="16" HEIGHT="8" BORDER="0" ALT=""/>
								Projects
								<br x="x"/>
							</td>
							<TD WIDTH="1" BGCOLOR="#000000">
								<IMG SRC="images/sp.gif" WIDTH="1" HEIGHT="1"/>
							</TD>
						</tr>
						<tr>
							<TD WIDTH="1" BGCOLOR="#000000">
								<IMG SRC="images/sp.gif" WIDTH="1" HEIGHT="1"/>
							</TD>
							<td width="148">
								<!--<IMG SRC="images/navBarBullet.gif" WIDTH="16" HEIGHT="8" BORDER="0" ALT=""/>-->
								<a HREF="ProjectQueryView" target="mainFrame" CLASS="childLink">  &#160;&#160;&#160;&#160;&#160;&#160; Modify Project </a>
								<br x="x"/>
							</td>
							<TD WIDTH="1" BGCOLOR="#000000">
								<IMG SRC="images/sp.gif" WIDTH="1" HEIGHT="1"/>
							</TD>
						</tr>
						<tr>
							<TD WIDTH="1" BGCOLOR="#000000">
								<IMG SRC="images/sp.gif" WIDTH="1" HEIGHT="1"/>
							</TD>
							<td width="148">
								<!--<IMG SRC="images/navBarBullet.gif" WIDTH="16" HEIGHT="8" BORDER="0" ALT=""/>-->
								<a HREF="ProjectActivitiesQueryView" target="mainFrame" CLASS="childLink"> &#160;&#160;&#160;&#160;&#160;&#160; Project activity </a>
								<br x="x"/>
							</td>
							<TD WIDTH="1" BGCOLOR="#000000">
								<IMG SRC="images/sp.gif" WIDTH="1" HEIGHT="1"/>
							</TD>
						</tr>
						<tr>
							<TD WIDTH="1" BGCOLOR="#000000">
								<IMG SRC="images/sp.gif" WIDTH="1" HEIGHT="1"/>
							</TD>
							<td width="148">
								<!--<IMG SRC="images/navBarBullet.gif" WIDTH="16" HEIGHT="8" BORDER="0" ALT=""/>-->
								<a HREF="ProjectMembersQueryView" target="mainFrame" CLASS="childLink"> &#160;&#160;&#160;&#160;&#160;&#160; Project members </a>
								<br x="x"/>
							</td>
							<TD WIDTH="1" BGCOLOR="#000000">
								<IMG SRC="images/sp.gif" WIDTH="1" HEIGHT="1"/>
							</TD>
						</tr>

						<tr>
							<TD WIDTH="1" BGCOLOR="#000000">
								<IMG SRC="images/sp.gif" WIDTH="1" HEIGHT="1"/>
							</TD>
							<td width="148">
								<IMG SRC="images/navBarBullet.gif" WIDTH="16" HEIGHT="8" BORDER="0" ALT=""/>
								<a HREF="ActivityQueryView" target="mainFrame" CLASS="childLink"> Activities </a>
								<br x="x"/>
							</td>
							<TD WIDTH="1" BGCOLOR="#000000">
								<IMG SRC="images/sp.gif" WIDTH="1" HEIGHT="1"/>
							</TD>
						</tr>
						
						
<!-- the Contacts part is removed. Just enable this section to enable it again
						<tr>
							<TD WIDTH="1" BGCOLOR="#000000">
								<IMG SRC="images/sp.gif" WIDTH="1" HEIGHT="1"/>
							</TD>
							<td width="148">
								<IMG SRC="images/navBarBullet.gif" WIDTH="16" HEIGHT="8" BORDER="0" ALT=""/>
								Contacts
								<br x="x"/>
							</td>
							<TD WIDTH="1" BGCOLOR="#000000">
								<IMG SRC="images/sp.gif" WIDTH="1" HEIGHT="1"/>
							</TD>
						</tr>
						<tr>
							<TD WIDTH="1" BGCOLOR="#000000">
								<IMG SRC="images/sp.gif" WIDTH="1" HEIGHT="1"/>
							</TD>
							<td width="148">
								<a HREF="ContactQueryView" target="mainFrame" CLASS="childLink">&#160;&#160;&#160;&#160;&#160;&#160; Modify Contacts </a>
								<br x="x"/>
							</td>
							<TD WIDTH="1" BGCOLOR="#000000">
								<IMG SRC="images/sp.gif" WIDTH="1" HEIGHT="1"/>
							</TD>
						</tr>
						<tr>
							<TD WIDTH="1" BGCOLOR="#000000">
								<IMG SRC="images/sp.gif" WIDTH="1" HEIGHT="1"/>
							</TD>
							<td width="148">
								<a HREF="EmailQueryView?action=contacts" target="mainFrame" CLASS="childLink"> &#160;&#160;&#160;&#160;&#160;&#160; Contact emails </a>
								<br x="x"/>
							</td>
							<TD WIDTH="1" BGCOLOR="#000000">
								<IMG SRC="images/sp.gif" WIDTH="1" HEIGHT="1"/>
							</TD>
						</tr>
						<tr>
							<TD WIDTH="1" BGCOLOR="#000000">
								<IMG SRC="images/sp.gif" WIDTH="1" HEIGHT="1"/>
							</TD>
							<td width="148">
								<a HREF="PhoneQueryView?action=contacts" target="mainFrame" CLASS="childLink"> &#160;&#160;&#160;&#160;&#160;&#160; Contact phones </a>
								<br x="x"/>
							</td>
							<TD WIDTH="1" BGCOLOR="#000000">
								<IMG SRC="images/sp.gif" WIDTH="1" HEIGHT="1"/>
							</TD>
						</tr>
-->						
						<tr>
							<td HEIGHT="1" COLSPAN="5" BGCOLOR="#000000">
								<IMG src="images/sp.gif" width="1" height="1"/>
							</td>
						</tr>
					</table>
				</div>
				<!-- End of SuperAdmin submenu5-->
					</xsl:when>
				</xsl:choose>
			</BODY>
		</HTML>
	</xsl:template>
</xsl:stylesheet>