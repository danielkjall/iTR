<%@ page session="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
	<head>
		<link REL="stylesheet" TYPE="text/css" HREF="include/ITR.CSS"/>
		<meta http-equiv="Content-Type" content="text/html;CHARSET=iso-8859-1"/>
		<title>ITR - View weeks</title>
	</head>
	<body bgcolor="#f3f3f3" leftmargin="0" rightmargin="0" topmargin="0" marginwidth="0" marginheight="0">
		<form action="#" name="weeks">
			<table border="0" cellpadding="0" cellspacing="0" width="100%">
				<tr bgcolor="#aaaacc" align="center">
					<td class="Title1">Available Reports</td>
				</tr>
			</table>
			<p>
				<br x="x"/>
			</p>
			<table WIDTH="100%" border="0" cellpadding="1" cellspacing="1">

				<!--Start black line, if submitted colspan = 12, else colspan = 13-->
				<tr><td colspan="2"><table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td bgcolor="#000000"><img src="images/sp.gif" height="1" width="1"/></td></tr></table></td></tr>
				<!--End black line -->

				<tr BGCOLOR="#C4C4C4" class="NormalBold">
					<td width="20%">Name</td>
					<td width="80%">Description</td>
				</tr>
	
<!--
				<tr>
					<td class="Normal">
						<a href="GeneralQueryProfileView">General report</a>
					</td>
					<td class="Normal">
						A generell time report,  on time spent on different projects and activities.
					</td>
				</tr>

				<tr>
					<td class="Normal">
						<a href="VacationQueryProfileView">Vacation report</a>
					</td>
					<td class="Normal">
						Here you can get information about your how long your vacation is and how much remain.
					</td>
				</tr>
-->
				<tr>
					<td class="Normal">
						<a href="monthlyReportQuery.jsp">Monthly report</a>
					</td>
					<td class="Normal">
						Billing information and total revenue per month.
					</td>
				</tr>
	
				<!--Start black line, if submitted colspan = 12, else colspan = 13-->
				<tr><td colspan="2"><table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td bgcolor="#000000"><img src="images/sp.gif" height="1" width="1"/></td></tr></table></td></tr>
				<!--End black line -->

			</table>
		</form>
	</body>
</html>