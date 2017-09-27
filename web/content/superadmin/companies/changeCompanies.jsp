<%@ page session="true"%>
<%@ page import="com.intiro.itr.util.xml.XMLCombo.Entry, com.intiro.itr.ITRResources, com.intiro.itr.util.personalization.*, com.intiro.itr.logic.superadmin.companies.CompanyEditor"%>
<%
	  UserProfile userProfile = ( UserProfile )session.getAttribute( ITRResources.ITR_USER_PROFILE );

      //START ACTION, Find out what action is called and perform it
      String edit = request.getParameter( "btnEdit" );
      String add = request.getParameter( "btnAdd" );
      String view = request.getParameter( "btnView" );
      String delete = request.getParameter( "btnDelete" );
      boolean bAdd = false;

      //Find out what button was pressed
      String mode = "error";

      if ( edit != null && edit.length() > 0 ) {
        mode = edit.trim();
        //bEdit = true;
      } else if ( add != null && add.length() > 0 ) {
        mode = add.trim();
        bAdd = true;
      } else if ( view != null && view.length() > 0 ) {
        mode = view.trim();
      } else if ( delete != null && delete.length() > 0 ) {
        mode = delete.trim();
      }

      String compId = "-1";

      if ( !bAdd ) {

      //Fetch compid
      compId = request.getParameter( "company" );
      }

      //Create xmlCarrier
      CompanyEditor xmlCarrier = new CompanyEditor( userProfile, mode, compId );

      //Set modified company in session
      session.setAttribute( ITRResources.ITR_MODIFIED_COMPANY, xmlCarrier );
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
	<head>
		<meta HTTP-EQUIV="Content-Type" CONTENT="text/html;CHARSET=iso-8859-1"/>
		<script type="text/javascript">
		<%@ include file="../../../helpers/en_validate.js" %>
		function validate(theForm)  {

		//Name
	         if (!isNotEmpty(theForm.name, "Name"))
	            return false;
	   	  if (!isValid(theForm.name, "Name"))
	            return false;

		//Company
	        //if ( !isSelected(theForm.companyparent, "Parent Company") )
	         //   return false;
		  
		//Visit address row1
	        if (!isValid(theForm.visitAddressRow1, "Visit Address (row 1)"))
	            return false;	 
	        
		//Visit address row2
	        if (!isValid(theForm.visitAddressRow2, "Visit Address (row 2)"))
	            return false;	 
	
		//Visit address row3
	        if (!isValid(theForm.visitAddressRow3, "Visit Address (row 3)"))
	            return false;	 
	
		//Invoice address row1
	        if (!isValid(theForm.invoiceAddressRow1, "Invoice Address (row 1)"))
	            return false;	 
	
		//Invoice address row2
	        if (!isValid(theForm.invoiceAddressRow2, "Invoice Address (row 2)"))
	            return false;	 
	
		//Invoice address row3
	        if (!isValid(theForm.invoiceAddressRow3, "Invoice Address (row 3)"))
	            return false;	 
	
	
	        return true;
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
					<td class="Title1"><%=xmlCarrier.getTitle()%> Company</td>
				</tr>
			</table>
			<form method="POST" action="changeCompaniesResult.jsp" name="personalinfo" onsubmit="return validate(this)">
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
										<td align="left" colspan="2" class="NormalBold">Company Profile</td>
									</tr>
									<tr>
										<td align="center" colspan="2">&#160;</td>
									</tr>
									<tr>
										<td align="left" width="25%"> Name</td>
										<td align="left" width="75%">
											<%if(xmlCarrier.isInAddMode() || xmlCarrier.isInEditMode()) {%>
												<input type="text" name="name" size="30" maxlength="20" class="input" value="<%=xmlCarrier.getModifiedCompany().getName()%>"/>
											<%}%>
											<%if(xmlCarrier.isInViewMode() || xmlCarrier.isInDeleteMode()) {%>
												<%=xmlCarrier.getModifiedCompany().getName()%>
											<%}%>
										</td>
									</tr>
									<tr>
										<td align="left" width="25%"> Parent company </td>
										<td align="left" width="75%">
											<%
											String readonly = "";
											if(xmlCarrier.isInViewMode() || xmlCarrier.isInDeleteMode()) {
												readonly = "readonly";
											}
											%>
											<select name="companyparent" <%=readonly%> size="1" style="font-size: 10px">
												<%
												for(int i=0;i<xmlCarrier.getCompanyParentCombo().getEntries().size(); i++) {
													Entry anEntry = (Entry)xmlCarrier.getCompanyParentCombo().getEntries().get(i);
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
									
									<tr><td align="left" colspan="2" class="NormalBold">&#160;</td></tr>
									
									<tr><td align="center" colspan="2">&#160;</td></tr>
									
								</table>
							</td>
						</tr>
						<tr>
							<td align="right" valign="top">
								<table cellspancing="0" cellpadding="0" border="0" width="400">
									<tr>
										<td align="left" width="25%"> Visit address </td>
										<td align="left" width="75%">
											<%
											if(xmlCarrier.isInAddMode() || xmlCarrier.isInEditMode()) {
											%>
												<input type="text" name="visitAddressRow1" size="30" maxlength="30" class="input" value="<%=xmlCarrier.getModifiedCompany().getVisitAddressRow1()%>"/>
											<%
											}
											else {
												out.write(xmlCarrier.getModifiedCompany().getVisitAddressRow1());
											}%>
										</td>
									</tr>
									<tr>
										<td align="left" width="25%">&#160;</td>
										<td align="left" width="75%">
											<%
											if(xmlCarrier.isInAddMode() || xmlCarrier.isInEditMode()) {
											%>
												<input type="text" name="visitAddressRow2" size="30" maxlength="30" class="input" value="<%=xmlCarrier.getModifiedCompany().getVisitAddressRow2()%>"/>
											<%
											}
											else {
												out.write(xmlCarrier.getModifiedCompany().getVisitAddressRow2());
											}%>
										</td>
									</tr>
									<tr>
										<td align="left" width="25%">&#160;</td>								
										<td align="left" width="75%" >
											<%
											if(xmlCarrier.isInAddMode() || xmlCarrier.isInEditMode()) {
											%>
												<input type="text" name="visitAddressRow3" size="30" maxlength="30" class="input" value="<%=xmlCarrier.getModifiedCompany().getVisitAddressRow3()%>"/>
											<%
											}
											else {
												out.write(xmlCarrier.getModifiedCompany().getVisitAddressRow3());
											}%>
										</td>
									</tr>

									<tr><td align="center" colspan="2">&#160;</td></tr>
									
								</table>
							</td>
							<td>
								<table cellspancing="0" cellpadding="0" border="0" width="400">
									<tr>
										<td align="left" width="25%"> Invoice address </td>										
										<td align="left" width="75%" colspan="2">
											<%
											if(xmlCarrier.isInAddMode() || xmlCarrier.isInEditMode()) {
											%>
												<input type="text" name="invoiceAddressRow1" size="30" maxlength="30" class="input" value="<%=xmlCarrier.getModifiedCompany().getInvoiceAddressRow1()%>"/>
											<%
											}
											else {
												out.write(xmlCarrier.getModifiedCompany().getInvoiceAddressRow1());
											}%>
										</td>
									</tr>
									<tr>
										<td align="left" width="25%">&#160;</td>
										<td align="left" width="75%">
											<%
											if(xmlCarrier.isInAddMode() || xmlCarrier.isInEditMode()) {
											%>
												<input type="text" name="invoiceAddressRow2" size="30" maxlength="30" class="input" value="<%=xmlCarrier.getModifiedCompany().getInvoiceAddressRow2()%>"/>
											<%
											}
											else {
												out.write(xmlCarrier.getModifiedCompany().getInvoiceAddressRow2());
											}%>
										</td>
									</tr>
									<tr>
										<td align="left" width="25%">&#160;</td>
										<td align="left" width="75%">
											<%
											if(xmlCarrier.isInAddMode() || xmlCarrier.isInEditMode()) {
											%>
												<input type="text" name="invoiceAddressRow3" size="30" maxlength="30" class="input" value="<%=xmlCarrier.getModifiedCompany().getInvoiceAddressRow3()%>"/>
											<%
											}
											else {
												out.write(xmlCarrier.getModifiedCompany().getInvoiceAddressRow3());
											}%>
										</td>
									</tr>
									
									<tr><td align="center" colspan="2">&#160;</td></tr>
									
								</table>
							</td>
						</tr>

						<!--Start grey line-->
						<tr><td colspan="2"><table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td bgcolor="#cccccc"><img src="images/sp.gif" height="1" width="1"/></td></tr></table></td></tr>
						<!--End grey line -->

						<tr>
							<td>
								<table cellspancing="0" cellpadding="0" border="0" width="400">
									<tr>
										<td align="left" colspan="2" class="NormalBold">Contacts</td>
									</tr>
									<tr>
										<td align="center" colspan="2">&#160;</td>
									</tr>
									<!--
									<xsl:for-each select="/response/company/contacts">
										<tr>
											<td align="left" width="25%">
												<xsl:value-of select="name"/>, <xsl:value-of select="position"/>
											</td>
										</tr>
									</xsl:for-each>
									-->
									
									<tr><td colspan="2">&#160;</td></tr>
									
								</table>
							</td>
							<td>
								<table cellspancing="0" cellpadding="0" border="0" width="400">
									<tr>
										<td align="left" colspan="2" class="NormalBold">Projects</td>
									</tr>
									
									<tr><td align="center" colspan="2">&#160;</td></tr>
									
									<!--
									<xsl:for-each select="/response/company/projects">
										<tr>
											<td align="left" width="25%">
												<xsl:value-of select="name"/>
											</td>
										</tr>
									</xsl:for-each>
									-->
									
									<tr><td colspan="2">&#160;</td></tr>
									
									<tr><td align="center" colspan="2">&#160;</td></tr>
									
								</table>
							</td>
						</tr>

						<!--Start grey line-->
						<tr><td colspan="2"><table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td bgcolor="#cccccc"><img src="images/sp.gif" height="1" width="1"/></td></tr></table></td></tr>
						<!--End grey line -->

						<tr>
							<td align="right" colspan="2">
								<xsl:choose>
									<xsl:when test="/response/mode = 'Add' or /response/mode = 'Edit'">
										<input type="submit" name="btnSave" value=" Save " class="input"/>&#160;
									</xsl:when>
									<xsl:when test="/response/mode = 'Delete'">
										<input type="button" name="btnDelete" value=" Delete " class="input" onclick="window.location.href='changeCompaniesResult.jsp?btnDelete=Delete'"/>&#160;
									</xsl:when>											
								</xsl:choose>
								<input type="button" name="Cancel" value=" Cancel " onclick="window.history.back(-1)" class="input"/>
							</td>
						</tr>
					</table>
				</p>
			</form>
		</center>
	</body>
</html>