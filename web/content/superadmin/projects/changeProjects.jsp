<%@ page session="true"%>
<%@ page import="com.intiro.itr.util.xml.XMLCombo.Entry, com.intiro.itr.ITRResources, com.intiro.itr.util.personalization.*, com.intiro.itr.logic.superadmin.projects.ProjectEditor, com.intiro.itr.logic.project.ProjectMember, com.intiro.itr.logic.project.ProjectActivity"%>
<%
	  response.setHeader("Cache-Control", "no-cache");
	  response.setHeader("Pragma", "no-cache");
	  response.addHeader("Cache-Control","no-store");
	  
	  UserProfile userProfile = (UserProfile) session.getAttribute(ITRResources.ITR_USER_PROFILE);
      
      //START ACTION, Find out what action is called and perform it
      String edit = request.getParameter( "btnEdit" );
      String add = request.getParameter( "btnAdd" );
      String view = request.getParameter( "btnView" );
      boolean bAdd = false;
      
      //Find out what button was pressed
      String mode = "error";

      if ( edit != null && edit.length() > 0 ) {
        mode = edit.trim();
      } else if ( add != null && add.length() > 0 ) {
        mode = add.trim();
        bAdd = true;
      } else if ( view != null && view.length() > 0 ) {
        mode = view.trim();
      }

      String projId = "-1";

      if ( !bAdd ) {

        //Fetch projid
        projId = request.getParameter( "projid" );
      }
      
      //Create xmlCarrier
      ProjectEditor xmlCarrier = new ProjectEditor( userProfile, mode, projId );

      //Set modified project in session
      session.setAttribute( ITRResources.ITR_MODIFIED_PROJECT, xmlCarrier );

      
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
	<head>
		<meta HTTP-EQUIV="Content-Type" CONTENT="text/html;CHARSET=iso-8859-1"/>
		<script type="text/javascript">
		<%@ include file="../../../helpers/en_validate.js" %>
		function validate(theForm)  {
		
			//Code
			  if (!isNotEmpty(theForm.code, "Code"))
		            return false;
			  if (!isValid(theForm.code, "Code"))
		            return false;

			//Name
		         if (!isNotEmpty(theForm.name, "Name"))
		            return false;
		   	  if (!isValid(theForm.name, "Name"))
		            return false;
		
			//CompanyId
		        if ( !isSelected(theForm.companyid, "Company") )
		            return false;
			  
			//From date
		       if (!isValid(theForm.fromdate, "From date"))
		            return false;	 
		
			//To date
		        if (!isValid(theForm.todate, "To date"))
		            return false;	 
		        
			//Description
		       if (!isValid(theForm.desc, "Description"))
		            return false;	 
		
			//Technique
		       if (!isValid(theForm.tech, "Technique"))
		            return false;	 
		
		        return true;
		}
		
		function toggleProjectPrice() {
			field = document.all["projectPrice"];
			if (field.disabled==true) 
				field.disabled = false;	
			else
				field.disabled = true;	
		}
		</script>
		<title>Change Profile</title>
		<link rel="stylesheet" href="include/ITR.CSS"/>
	</head>
	<body bgcolor="#eeeeee" topmargin="2" leftmargin="0" rightmargin="0" marginwidth="0" marginheight="0">
		<a name="top"/>
		<center>
			<table border="0" cellpadding="0" cellspacing="0" width="100%">
				<tr bgcolor="#aaaacc" align="center">
					<td class="Title1"><%=xmlCarrier.getTitle()%> Project</td>
				</tr>
			</table>
			<form method="POST" action="changeProjectsResult.jsp" name="personalinfo" onsubmit="return validate(this)">
				<p>
					<table cellspancing="5" cellpadding="5" border="0" width="800">
						<tr>
							<td height="30" align="right" valign="bottom" colspan="2" class="NormalBold">|<a href="OnlineHelp">Help</a>|</td>
						</tr>
						
						<!--Start grey line-->
						<tr><td colspan="2"><table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td bgcolor="#cccccc"><img src="images/sp.gif" height="1" width="1"/></td></tr></table></td></tr>
						<!--End grey line -->
						
						<tr>
							<td align="left" valign="top">
								<table cellspancing="0" cellpadding="0" border="0" width="400">
									<tr>
										<td align="left" colspan="2" class="NormalBold">Project Profile</td>
									</tr>
									<tr>
										<td align="center" colspan="2">&#160;</td>
									</tr>
									<tr>
										<td align="left" width="25%"> Code 
											<input type="hidden" name="projectid" value="<%=xmlCarrier.getModifiedProject().getProjectId()%>"/>
										</td>
										<td align="left" width="75%">
											<%if(xmlCarrier.isInAddMode() || xmlCarrier.isInEditMode()) {%>
												<input type="text" name="code" size="20" maxlength="20" class="input" value="<%=xmlCarrier.getModifiedProject().getProjectCode()%>"/>
											<%}%>
											<%if(xmlCarrier.isInViewMode() || xmlCarrier.isInDeleteMode()) {%>
												<%=xmlCarrier.getModifiedProject().getProjectCode()%>
											<%}%>
										</td>
									</tr>
									<tr>
										<td align="left" width="25%"> Name</td>
										<td align="left" width="75%">
											<%if(xmlCarrier.isInAddMode() || xmlCarrier.isInEditMode()) {%>
												<input type="text" name="name" size="20" maxlength="20" class="input" value="<%=xmlCarrier.getModifiedProject().getProjectName()%>"/>
											<%}%>
											<%if(xmlCarrier.isInViewMode() || xmlCarrier.isInDeleteMode()) {%>
												<%=xmlCarrier.getModifiedProject().getProjectName()%>
											<%}%>
										</td>
									</tr>
									<tr>
										<td align="left" width="25%"> Company </td>
										<td align="left" width="75%">
										<%
										String readonly = "";
										if(xmlCarrier.isInViewMode() || xmlCarrier.isInDeleteMode()) {
											readonly = "disabled";
										}
										%>
										<select name="companyid" <%=readonly%> class="input">
											<%
											for(int i=0;i<xmlCarrier.getCompanyCombo().getEntries().size(); i++) {
												Entry anEntry = (Entry)xmlCarrier.getCompanyCombo().getEntries().get(i);
											%>
												<option	<%if(anEntry.isSelected()) {out.write("selected");}%> <%=" value=\""+anEntry.getValue()+"\"" %>>
													<%=anEntry.getText()%>
												</option>
											<%}%>
										</select>													
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table cellspancing="0" cellpadding="0" border="0" width="400">
									<tr>
										<td align="left" colspan="2" class="NormalBold"> Accessability </td>
									</tr>
									<tr>
										<td align="center" colspan="2">&#160;</td>
									</tr>
									<tr>
										<td align="left" width="25%"> Activated </td>
										<td align="left" width="75%">
											<%
											String checked = "";
											String disabled = "";
											if(xmlCarrier.getModifiedProject().getActivated()) {
												checked = "checked";
											}
											if(xmlCarrier.isInViewMode() || xmlCarrier.isInDeleteMode()) {
												disabled = "disabled";
											}
											%>
											<input type="checkbox" <%=checked%> <%=disabled%> name="activated" class="input"/>
										</td>
									</tr>
									<tr>
										<td align="left" width="25%"> Internal project </td>
										<td align="left" width="75%">
												<%
												checked = "";
												disabled = "";
												if(xmlCarrier.getModifiedProject().getAdminProject()) {
													checked = "checked";
												}
												if(xmlCarrier.isInViewMode() || xmlCarrier.isInDeleteMode()) {
													disabled = "disabled";
												}
												%>
												<input type="checkbox" <%=checked%> <%=disabled%> name="adminproj" class="input"/>
										</td>
									</tr>
									<tr>
										<td align="left" width="25%"> Fixed price </td>
										<td align="left" width="75%">
											<%
											checked = "";
											disabled = "";
											if(xmlCarrier.getModifiedProject().getContract()) {
												checked = "checked";
											}
											if(xmlCarrier.isInViewMode() || xmlCarrier.isInDeleteMode()) {
												disabled = "disabled";
											}
											%>
											<input type="checkbox" <%=checked%> <%=disabled%> name="contract" class="input"/>
											<input align="right" <%=disabled%> type="text" disabled="true" name="projectPrice" size="10" maxlength="10" class="input" value="<%=xmlCarrier.getModifiedProject().getFixedPrice()%>"/>Skr											
										</td>
									</tr>
									
									<tr>
										<td align="left" width="25%"> From date</td>
										<td align="left" width="75%">
											<%
											String fromDate = "";
											if (xmlCarrier.getModifiedProject().getFromDate() != null) {
												fromDate = xmlCarrier.getModifiedProject().getFromDate().getCalendarInStoreFormat();
											}
											if(xmlCarrier.isInAddMode() || xmlCarrier.isInEditMode()) {%>
												<input type="text" name="fromdate" size="20" maxlength="20" class="input" value="<%=fromDate%>"/>
											<%}%>
											<%if(xmlCarrier.isInViewMode() || xmlCarrier.isInDeleteMode()) {%>
												<%=fromDate%>
											<%}%>
										</td>
									</tr>
									<tr>
										<td align="left" width="25%"> To date</td>
										<td align="left" width="75%">
											<%
											String toDate = "";
											if (xmlCarrier.getModifiedProject().getToDate() != null) {
												toDate = xmlCarrier.getModifiedProject().getToDate().getCalendarInStoreFormat();
											}
											if(xmlCarrier.isInAddMode() || xmlCarrier.isInEditMode()) {%>
												<input type="text" name="todate" size="20" maxlength="20" class="input" value="<%=toDate%>"/>
											<%}%>
											<%if(xmlCarrier.isInViewMode() || xmlCarrier.isInDeleteMode()) {%>
												<%=toDate%>
											<%}%>
										</td>
									</tr>
								</table>
							</td>
						</tr>

						<!--Start grey line-->
						<tr><td colspan="2"><table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td bgcolor="#cccccc"><img src="images/sp.gif" height="1" width="1"/></td></tr></table></td></tr>
						<!--End grey line -->

						<tr>
							<td align="right" valign="top">
								<table cellspancing="0" cellpadding="0" border="0" width="400">
									<tr>
										<td align="left" colspan="2" class="NormalBold"> Description</td>
									</tr>
									<tr>
										<td align="center" colspan="2">&#160;</td>
									</tr>
									<tr>
										<td align="left" width="100%" colspan="2">
											<%if(xmlCarrier.isInAddMode() || xmlCarrier.isInEditMode()) {%>
												<textarea rows="3" cols="50" name="desc" class="input"><%=xmlCarrier.getModifiedProject().getProjectDesc()%></textarea>
											<%}%>
											<%if(xmlCarrier.isInViewMode() || xmlCarrier.isInDeleteMode()) {%>
												<%=xmlCarrier.getModifiedProject().getProjectDesc()%>
											<%}%>
										</td>
									</tr>
									<tr>
										<td align="center" colspan="2">&#160;</td>
									</tr>
								</table>
							</td>
							<td>
								<table cellspancing="0" cellpadding="0" border="0" width="400">
									<tr>
										<td align="left" colspan="2" class="NormalBold"> Technique</td>
									</tr>
									<tr>
										<td align="center" colspan="2">&#160;</td>
									</tr>
									<tr>
										<td align="left" width="100%" colspan="2">
											<%if(xmlCarrier.isInAddMode() || xmlCarrier.isInEditMode()) {%>
												<textarea rows="3" cols="50" name="tech" class="input"><%=xmlCarrier.getModifiedProject().getTechnique()%></textarea>
											<%}%>
											<%if(xmlCarrier.isInViewMode() || xmlCarrier.isInDeleteMode()) {%>
												<%=xmlCarrier.getModifiedProject().getTechnique()%>
											<%}%>
										</td>
									</tr>
									<tr>
										<td align="center" colspan="2">&#160;</td>
									</tr>
								</table>
							</td>
						</tr>

						<!--Start grey line-->
						<tr><td colspan="2"><table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td bgcolor="#cccccc"><img src="images/sp.gif" height="1" width="1"/></td></tr></table></td></tr>
						<!--End grey line -->

						<tr>
							<td valign="top">
								<table cellspancing="0" cellpadding="0" border="0" width="400">
									<tr>
										<td align="left" class="NormalBold">Project members</td>
									</tr>
									<tr>
										<td align="center">&#160;</td>
									</tr>
									<%
									if(xmlCarrier.isInViewMode() || xmlCarrier.isInDeleteMode() || xmlCarrier.isInEditMode()) {
										for(int i=0; i<xmlCarrier.getProjectMembers().size();i++) {
											ProjectMember aMember = (ProjectMember)xmlCarrier.getProjectMembers().get(i);
											if(aMember.getActive()) {
									%>
											<tr>
												<td align="left" width="50%"><%=aMember.getLastName()%>, <%=aMember.getFirstName()%> (<%=aMember.getLoginId()%>) - <%=aMember.getRate()%> SEK<%if(aMember.getProjectAdmin()) {%>, Project Administrator<%}%></td>
											</tr>
									<%
											}
										}
									}
									%>
									
									<tr><td>&#160;</td></tr>
									
								</table>
							</td>
							<td>
								<table cellspancing="0" cellpadding="0" border="0" width="400">
									<tr>
										<td align="left" valign="top" class="NormalBold">Activities</td>
									</tr>
									
									<tr><td align="center">&#160;</td></tr>
									<%
									if(xmlCarrier.isInViewMode() || xmlCarrier.isInDeleteMode() || xmlCarrier.isInEditMode()) {
										for(int i=0; i<xmlCarrier.getModifiedProject().getProjectActivities().size();i++) {
											ProjectActivity anActivity = (ProjectActivity)xmlCarrier.getModifiedProject().getProjectActivities().get(i);
									%>
											<tr>
												<td align="left"><%=anActivity.getCode()%>, <%=anActivity.getDescription()%></td>
											</tr>
									<%
										}
									}
									%>
									<tr>
										<td>&#160;</td>
									</tr>
								</table>
							</td>
						</tr>

						<!--Start grey line-->
						<tr><td colspan="2"><table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td bgcolor="#cccccc"><img src="images/sp.gif" height="1" width="1"/></td></tr></table></td></tr>
						<!--End grey line -->

						<tr>
							<td align="right" colspan="2">
								<%
								if(xmlCarrier.isInDeleteMode()) {
								%>
									<input type="button" name="btnDelete" value=" Delete " class="input" onclick="window.location.href='changeProjectsResult.jsp?btnDelete=Delete'"/>&#160;
								<%
								}
								if(xmlCarrier.isInAddMode() || xmlCarrier.isInEditMode()) {
								%>
									<input type="submit" name="btnSave" value=" Save " class="input"/>&#160;
								<%
								}
								%>
								<input type="button" name="Cancel" value=" Cancel " onclick="window.history.back(-1)" class="input"/>
							</td>
						</tr>
					</table>
				</p>
			</form>
		</center>
	</body>
</html>