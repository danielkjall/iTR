<%@ page session="true"%>
<%@ page import="com.intiro.itr.util.StringRecordset, com.intiro.itr.ITRResources, com.intiro.itr.util.personalization.*, com.intiro.itr.logic.weekreport.*, com.intiro.itr.logic.*"%>

<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.addHeader("Cache-Control", "no-store");
	
    UserProfile userProfile = (UserProfile) session.getAttribute(ITRResources.ITR_USER_PROFILE);

    //We have to set the week comment 
    //(the user can supply a comment before pressing "select project":
    WeekReport aWeek = ( WeekReport )session.getAttribute( ITRResources.ITR_WEEK_REPORT );
    if ( request.getParameter( "comments" ) != null && request.getParameter( "comments" ).length() > 0 ) {
      aWeek.setWeekComment( request.getParameter( "comments" ) );
      session.setAttribute(ITRResources.ITR_WEEK_REPORT, aWeek);
    }
    String mode = (String)request.getAttribute("mode");

    ProjectSelector selector = new ProjectSelector( userProfile );
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
	<head>
		<link REL="stylesheet" TYPE="text/css" HREF="include/ITR.CSS"/>
		<meta HTTP-EQUIV="Content-Type" CONTENT="text/html;CHARSET=iso-8859-1"/>
		<title>ITR - Select project</title>
	</head>
	<body bgcolor="#f3f3f3" leftmargin="0" rightmargin="0" topmargin="0" marginwidth="0" marginheight="0">
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
			<tr bgcolor="#aaaacc" align="center">
				<td class="Title1">Select Project</td>
			</tr>
		</table>
		<p>
			<br x="x"/>
		</p>
		
		<table WIDTH="100%" border="0" cellspacing="2" cellpadding="1">
			<tr>
				<td colspan="3">
					<table border="0" cellpadding="0" cellspacing="0" width="100%">
						<tr><td bgcolor="#000000"><img src="images/sp.gif" height="1" width="1"/></td></tr>
					</table>
				</td>
			</tr>
			<tr BGCOLOR="#C4C4C4" class="NormalBold">
				<td width="10%">Code</td>
				<td width="20%">Name</td>
				<td width="70%">Description</td>
			</tr>
			<%
			StringRecordset rs = selector.getProjTable().getResultset();
			int trIndex = 0;
			 while (!rs.getEOF()) {
				String bgColor = "";
				if(trIndex%2==0) bgColor = "#fafafa";
			%>
				<tr <%=bgColor%>>
					<td class="Normal">
						<a href="activitySelector.jsp?mode=<%=mode%>&projId=<%=rs.getField("id")%>"><%=rs.getField("MainCode")%></a>
					</td>
					<td class="Normal">
						<%=rs.getField("Name")%>
					</td>
					<td class="Normal">
						<%=rs.getField("Description")%>
					</td>
				</tr>
			<%
				 rs.moveNext();
				 trIndex++;
			}
			%>
			<tr>
				<td colspan="3">
					<table border="0" cellpadding="0" cellspacing="0" width="100%">
						<tr><td bgcolor="#000000"><img src="images/sp.gif" height="1" width="1"/></td></tr>
					</table>
				</td>
			</tr>
		</table>
	</body>
</html>