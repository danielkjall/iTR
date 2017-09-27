<%@ page session="true"%>
<%@ page import="com.intiro.itr.util.xml.XMLCombo.Entry, com.intiro.itr.ITRResources, com.intiro.itr.logic.generatereport.monthly.QueryProfile, com.intiro.itr.util.personalization.*"%>

<%
      UserProfile userProfile = ( UserProfile )session.getAttribute( ITRResources.ITR_USER_PROFILE );
      QueryProfile xmlCarrier = new QueryProfile( userProfile );
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
	<head>
		<meta HTTP-EQUIV="Content-Type" CONTENT="text/html;CHARSET=iso-8859-1"/>
		<script type="text/javascript">
			<%@ include file="../../../helpers/en_validate.js" %>
			function validate(theForm)  {
				if ( !isValid(theForm.year, "Year") )
					return false;
			
				if ( !isValid(theForm.month, "Month") )
					return false;
			
				return true;
			}
		</script>
		<title>Monthly Query Profile</title>
		<link rel="stylesheet" href="include/ITR.CSS"/>
	</head>
	<body bgcolor="#eeeeee" topmargin="2" leftmargin="0" rightmargin="0" marginwidth="0" marginheight="0">
		<a name="top"/>
		<center>
			<table border="0" cellpadding="0" cellspacing="0" width="100%">
				<tr bgcolor="#aaaacc" align="center">
					<td class="Title1">Monthly Query Profile</td>
				</tr>
			</table>
			<form method="POST" action="monthlyReport.jsp" name="queryprofile" onsubmit="return validate(this)">
				<p>
					<table cellspancing="0" cellpadding="0" border="0" width="300">
						<tr>
							<td height="30" align="right" valign="bottom" colspan="2" class="NormalBold">|<a href="OnlineHelp">Help</a>|</td>
						</tr>
						<tr>
							<td colspan="2" align="center">
								<img src="images/grey_line.jpg"/>
							</td>
						</tr>
						<tr>
							<td class="NormalBold" align="left" width="50%"> User: </td>
							<td align="left" width="50%">
							<select name="userid" class="input" style="width:170px">
								<%
								for(int i=0;i<xmlCarrier.getUsersCombo().getEntries().size(); i++) {
									Entry anEntry = (Entry)xmlCarrier.getUsersCombo().getEntries().get(i);
								%>
									<option	<%if(anEntry.isSelected()) {out.write("selected");}%> <%=" value=\""+anEntry.getValue()+"\"" %>>
										<%=anEntry.getText()%>
									</option>
								<%}%>
							</select>
							</td>
						</tr>
						<tr>
							<td class="NormalBold" align="left" width="50%"> Project: </td>
							<td align="left" width="50%">
								<select name="projectid" class="input" style="width:170px">
									<%
									for(int i=0;i<xmlCarrier.getProjectsCombo().getEntries().size(); i++) {
										Entry anEntry = (Entry)xmlCarrier.getProjectsCombo().getEntries().get(i);
									%>
										<option	<%if(anEntry.isSelected()) {out.write("selected");}%> <%=" value=\""+anEntry.getValue()+"\"" %>>
											<%=anEntry.getText()%>
										</option>
									<%}%>
								</select>
							</td>
						</tr>
						<tr>
							<td class="NormalBold" align="left" width="50%"> Project code: </td>
							<td align="left" width="50%">
								<select name="projectcodeid" class="input" style="width:170px">
									<%
									for(int i=0;i<xmlCarrier.getCodesCombo().getEntries().size(); i++) {
										Entry anEntry = (Entry)xmlCarrier.getCodesCombo().getEntries().get(i);
									%>
										<option	<%if(anEntry.isSelected()) {out.write("selected");}%> <%=" value=\""+anEntry.getValue()+"\"" %>>
											<%=anEntry.getText()%>
										</option>
									<%}%>
								</select>
							</td>
						</tr>
						<tr>
							<td class="NormalBold" align="left" width="50%"> Year: </td>
							<td align="left" width="50%">
								<select name="year" class="input" style="width:170px">
									<%
									for(int i=0;i<xmlCarrier.getYearsCombo().getEntries().size(); i++) {
										Entry anEntry = (Entry)xmlCarrier.getYearsCombo().getEntries().get(i);
									%>
										<option	<%if(anEntry.isSelected()) {out.write("selected");}%> <%=" value=\""+anEntry.getValue()+"\"" %>>
											<%=anEntry.getText()%>
										</option>
									<%}%>
								</select>
							</td>
						</tr>
						<tr>
							<td class="NormalBold" align="left" width="50%"> Month: </td>
							<td align="left" width="50%">
								<select name="month" class="input" style="width:170px">
									<%
									for(int i=0;i<xmlCarrier.getMonthsCombo().getEntries().size(); i++) {
										Entry anEntry = (Entry)xmlCarrier.getMonthsCombo().getEntries().get(i);
									%>
										<option	<%if(anEntry.isSelected()) {out.write("selected");}%> <%=" value=\""+anEntry.getValue()+"\"" %>>
											<%=anEntry.getText()%>
										</option>
									<%}%>
								</select>
							</td>
						</tr>
						<tr>
							<td colspan="2" align="center">
								<img src="images/grey_line.jpg"/>
							</td>
						</tr>
						<tr>
							<td/>
							<td align="left">
								<input type="submit" name="Change" value=" Search " class="input"/>&#160;
								<input type="button" name="Cancel" value=" Cancel " onclick="javascript:window.history.back(-1)" class="input"/>
							</td>
						</tr>
					</table>
				</p>
			</form>
		</center>
	</body>
</html>
