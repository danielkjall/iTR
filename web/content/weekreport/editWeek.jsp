<%@ page session="true"%>
<%@ page import="com.intiro.itr.util.xml.XMLCombo.Entry, java.util.*, com.intiro.itr.ITRResources, com.intiro.itr.util.personalization.*, com.intiro.itr.logic.weekreport.*"%>

<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.addHeader("Cache-Control", "no-store");
    
    String monHours = "0";
    String tueHours = "0";
    String wedHours = "0";
    String thuHours = "0";
	String friHours = "0";
    String satHours = "0";
    String sunHours = "0";
    String timeTypeId = "0";
    String projSubId = "0";
    String projSubCode = "0";
    String row = "0";
    boolean copyFromSubmitted = false;
    String mode = request.getParameter("mode");
    String weekComment = "";
    Vector weekReports = new Vector();

	  UserProfile userProfile = (UserProfile) session.getAttribute(ITRResources.ITR_USER_PROFILE);
	  WeekReport aWeek = (WeekReport) session.getAttribute(ITRResources.ITR_WEEK_REPORT);

      /*START ACTION, Find out what action is called and perform it*/
      String action = request.getParameter("action");

      /*START THE WEEKREPORT*/
      if (action != null && action.equalsIgnoreCase("start")) {

        //Fetch week reports from session
        weekReports = (Vector) session.getAttribute(ITRResources.ITR_WEEK_REPORTS);

        int weekReportsIndex = Integer.parseInt(request.getParameter("row"));
        aWeek = (WeekReport) weekReports.get(weekReportsIndex);
        session.setAttribute(ITRResources.ITR_WEEK_REPORT, aWeek);
        copyFromSubmitted = Boolean.valueOf(request.getParameter("copySubmitted")).booleanValue();

        //If weekreport have no rows and user want to copy from submitted.
        if (aWeek.getRows() != null && aWeek.getRows().size() == 0 && copyFromSubmitted) {
          aWeek.loadWeekWithLatestSubmittedWeek();
        }
      }

      /*ADD A ROW*/
      else if (action != null && action.equalsIgnoreCase("addrow")) {

        /*set parameters*/
        String mo = request.getParameter("mo");
        String tu = request.getParameter("tu");
        String we = request.getParameter("we");
        String th = request.getParameter("th");
        String fr = request.getParameter("fr");
        String sa = request.getParameter("sa");
        String su = request.getParameter("su");
        
        if (mo != null && mo.length() > 0) {
          monHours = mo;
        }
        if (tu != null && tu.length() > 0) {
          tueHours = tu;
        }
        if (we != null && we.length() > 0) {
          wedHours = we;
        }
        if (th != null && th.length() > 0) {
          thuHours = th;
        }
        if (fr != null && fr.length() > 0) {
          friHours = fr;
        }
        if (sa != null && sa.length() > 0) {
          satHours = sa;
        }
        if (su != null && su.length() > 0) {
          sunHours = su;
        }
        timeTypeId = request.getParameter("timeTypeId");

        aWeek.getEditRow().setMonday(Double.parseDouble(monHours));
        aWeek.getEditRow().setTuesday(Double.parseDouble(tueHours));
        aWeek.getEditRow().setWednesday(Double.parseDouble(wedHours));
        aWeek.getEditRow().setThursday(Double.parseDouble(thuHours));
        aWeek.getEditRow().setFriday(Double.parseDouble(friHours));
        aWeek.getEditRow().setSaturday(Double.parseDouble(satHours));
        aWeek.getEditRow().setSunday(Double.parseDouble(sunHours));
        aWeek.getEditRow().setTimeTypeId(timeTypeId);
        aWeek.getEditRow().loadTimeType(timeTypeId);

        //set parameters
        weekComment = request.getParameter("comments");
        
        aWeek.setWeekComment(weekComment);

        /*Create a new row*/
        Row newRow = aWeek.getEditRow().cloneToRow();

        /*Add the new row to your WeekReportEditor*/
        aWeek.addRow(newRow);

        /*Recalculate summary rows*/
        aWeek.getSumRows().calcSum(aWeek.getRows());
        
        session.setAttribute(ITRResources.ITR_WEEK_REPORT, aWeek);
      }

      /*PROJECT SELECTED*/
      else if (action != null && action.equalsIgnoreCase("projSelected")) {

        /*Set parameters*/
        projSubId = request.getParameter("projSubId");
        
        /*Edit row*/
        aWeek.getEditRow().getProject().setProjectActivityId(projSubId);

        // aWeek.getEditRow().getProject().setProjectSubCode(projSubCode);
        session.setAttribute(ITRResources.ITR_WEEK_REPORT, aWeek);
      }

      /*EDIT A ROW*/
      else if (action != null && action.equalsIgnoreCase("editRow")) {

        row = request.getParameter("row");
        weekComment = request.getParameter("comments");
        aWeek.setWeekComment(weekComment);

        /*Set row as editRow*/
        aWeek.getEditRow().load(aWeek.getRow(Integer.parseInt(row)));

        /*Remove the selected row*/
        aWeek.removeRow(Integer.parseInt(row));

        /*Recalculate summary rows*/
        aWeek.getSumRows().calcSum(aWeek.getRows());

        session.setAttribute(ITRResources.ITR_WEEK_REPORT, aWeek);
      }

      /*REMOVE A ROW*/
      else if (action != null && action.equalsIgnoreCase("removeRow")) {

        row = request.getParameter("row");
        weekComment = request.getParameter("comments");

        aWeek.setWeekComment(weekComment);

		log("Rows before = "+aWeek.getRows().size());
        /*Remove the selected row*/
        aWeek.removeRow(Integer.parseInt(row));
   		log("Rows after = "+aWeek.getRows().size());

        /*Recalculate summary rows*/
        aWeek.getSumRows().calcSum(aWeek.getRows());
        
        session.setAttribute(ITRResources.ITR_WEEK_REPORT, aWeek);
      }
      else if (action != null && action.equalsIgnoreCase("cancelSubmit")) {
        aWeek.setSubmitErrorOccurred(false);
      }
      
      /*END ACTION*/
%>
<%
      if (mode != null && (mode.equalsIgnoreCase("submitted") || mode.equalsIgnoreCase("approve") || mode.equalsIgnoreCase("View"))) {
        session.setAttribute(ITRResources.ITR_WEEK_REPORT, aWeek);
        request.setAttribute("mode", mode);
        getServletConfig().getServletContext().getRequestDispatcher("/content/weekreport/approveWeek.jsp").forward(request, response);
        return;
      }
      //If we come down here we need to display something:
      else {%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
<head>
<link REL="stylesheet" TYPE="text/css" HREF="include/ITR.CSS"/>
<meta HTTP-EQUIV="Content-Type" CONTENT="text/html;CHARSET=iso-8859-1"/>
<title>ITR - Edit Week</title>
<script type="text/javascript">
	<%@ include file="../../helpers/en_validate.js" %>

	function validateAdd(theForm) {
		if ( !isSelected(theForm.timeTypeId, "Time Type") )
			return false;

		if (theForm.mo != null) {
			if ( !isNumeric(theForm.mo, "Monday") )
				return false;
		}

		if (theForm.tu != null) {
			if ( !isNumeric(theForm.tu, "Tuesday") )
				return false;
		}

		if (theForm.we != null) {
			if ( !isNumeric(theForm.we, "Wednesday") )
				return false;
		}

		if (theForm.th != null) {
			if ( !isNumeric(theForm.th, "Thursday") )
				return false;
		}

		if (theForm.fr != null) {
			if ( !isNumeric(theForm.fr, "Friday") )
				return false;
		}

		if (theForm.sa != null) {
			if ( !isNumeric(theForm.sa, "Saturday") )
				return false;
		}

		if (theForm.su != null) {
			if ( !isNumeric(theForm.su, "Sunday") )
				return false;
		}

		if (!validateSubmit(document.saveSubmit)) {
			return false;
		}
		document.addARow.comments.value = document.saveSubmit.comments.value;
		
		return true;
	}

	function validateSubmit(theForm) {
		if ( !isValid(theForm.comments, "Week Comment") )
			return false;

		if ( !isNotToLong(theForm.comments, "Week Comment", 255) )
			return false;

		return true;
	}
</script>
</head>
<body bgcolor="#f3f3f3" leftmargin="0" rightmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<tr bgcolor="#aaaacc" align="center">
			<td class="Title1">Edit week (<%=aWeek.getUserProfile().getFirstName()+" "+aWeek.getUserProfile().getLastName()%>)</td>
		</tr>
	</table>
	<br/>
	<table border="0" width="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td valign="bottom" nowrap="true">
			<%
			String projectOnClick = "document.location.href='projectSelector.jsp?mode="+mode+"&comments=' + escape(document.saveSubmit.comments.value)";
			String activityOnClick = "document.location.href='activitySelector.jsp?mode="+mode+"&projId=" + aWeek.getEditRow().getProject().getProjectId() + "&comments=' + escape(document.saveSubmit.comments.value)";
			%>
				<span class="NormalBold">Select: </span>
				<input class="input" type="button" name="project" value="project" onclick="<%=projectOnClick%>"/>&#160;
				<input class="input" type="button" name="activity" value="activity"
				<%
				//The activity button should only be active if there is a project already specified:
				if(aWeek.getEditRow().getProject().getProjectId() == "-1"){
					out.write("disabled" + ">");
				} else {
					out.write("onclick="+activityOnClick+">");				
				}
				%>
				</input>
			</td>
			<td align="right">
				<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td colspan="3" bgcolor="#000000"><img src="images/blackLight.gif" height="1" width="1"/></td>
					</tr>
					<tr>
						<td bgcolor="#000000"><img src="images/blackLight.gif" height="1" width="1"/></td>
						<td>
							<table border="0" cellspacing="0" cellpadding="1" bgcolor="#ffffff">
								<tr>
									<td class="NormalBold" align="left">&#160;Date:</td>
									<td nowrap="true" class="Normal">
										<%=aWeek.getFromDate().getYear()%> <%=aWeek.getFromDate().getMonthNameShort()%>
									</td>
									<td class="NormalBold">&#160;Week:</td>
									<td nowrap="true" class="Normal">
										<%=aWeek.getFromDate().getWeekOfYear()%> (<%=aWeek.getFromDate().getWeekPart()%>)&#160;
									</td>
								</tr>
								<tr>
									<td nowrap="true" class="NormalBold" align="left">&#160;Range:</td>
									<td nowrap="true" colspan="3" class="Normal">
										<%=aWeek.getFromDate().getCalendarInStoreFormat() + "-" + aWeek.getToDate().getCalendarInStoreFormat()%>
									</td>
								</tr>
							</table>
						</td>
						<td bgcolor="#000000"><img src="images/blackLight.gif" height="1" width="1"/></td>
					</tr>
					<tr>
						<td colspan="3" bgcolor="#000000"><img src="images/blackLight.gif" height="1" width="1"/></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<table WIDTH="100%" border="0">
	<form action="editWeek.jsp" name="addARow" enctype="text/plain" method="get" onsubmit="return validateAdd(this)">
		<input type="hidden" name="comments" value=""/>
		<input type="hidden" name="action" value="addRow"/>
    	<input type="hidden" name="mode" value="<%=mode%>"/>
		<tr>
			<td colspan="12">
				<table border="0" cellpadding="0" cellspacing="0" width="100%">
					<tr>
						<td bgcolor="#000000"><img src="images/sp.gif" height="1" width="1"/></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr class="NormalBold">
			<td nowrap="true" width="15%" bgcolor="#C4C4C4">Project Code</td>
			<td nowrap="true" width="30%" bgcolor="#C4C4C4">Description</td>
			<td nowrap="true" width="10%" bgcolor="#C4C4C4">Time Type</td>
			<%
			String monBgColor = "#C4C4C4";
			String tueBgColor = "#C4C4C4";
			String wedBgColor = "#C4C4C4";
			String thuBgColor = "#C4C4C4";
			String friBgColor = "#C4C4C4";
			String satBgColor = "#C4C4C4";
			String sunBgColor = "#C4C4C4";
			if(aWeek.getEditRow().isMonWeekend()) monBgColor = "#FF2222";
			if(aWeek.getEditRow().isTueWeekend()) tueBgColor = "#FF2222";
			if(aWeek.getEditRow().isWedWeekend()) wedBgColor = "#FF2222";
			if(aWeek.getEditRow().isThuWeekend()) thuBgColor = "#FF2222";
			if(aWeek.getEditRow().isFriWeekend()) friBgColor = "#FF2222";
			if(aWeek.getEditRow().isSatWeekend()) satBgColor = "#FF2222";
			if(aWeek.getEditRow().isSunWeekend()) sunBgColor = "#FF2222";
			%>
			<td nowrap="true" width="5%" align="right" bgcolor="<%=monBgColor%>">Mon</td>
			<td nowrap="true" width="5%" align="right" bgcolor="<%=tueBgColor%>">Tue</td>
			<td nowrap="true" width="5%" align="right" bgcolor="<%=wedBgColor%>">Wed</td>
			<td nowrap="true" width="5%" align="right" bgcolor="<%=thuBgColor%>">Thu</td>
			<td nowrap="true" width="5%" align="right" bgcolor="<%=friBgColor%>">Fri</td>
			<td nowrap="true" width="5%" align="right" bgcolor="#FF2222">Sat</td>
			<td nowrap="true" width="5%" align="right" bgcolor="#FF2222">Sun</td>
			<td nowrap="true" width="5%" align="right" bgcolor="#C4C4C4">&nbsp;</td>
			<td nowrap="true" width="5%" bgcolor="#C4C4C4">Action</td>
		</tr>
		<tr>
			<td nowrap="true">
				<input type="hidden" name="projId" id="<%=aWeek.getEditRow().getProject().getProjectId()%>">
				</input>
				<input type="hidden" name="projCode" id="<%=aWeek.getEditRow().getProject().getProjectCode()%>">
				</input>
				<input type="hidden" name="projSubId" id="<%=aWeek.getEditRow().getProject().getProjectActivity().getId()%>">
				</input>
				<input type="hidden" name="projSubCode" id="<%=aWeek.getEditRow().getProject().getProjectActivity().getCode()%>">
				</input>
				<%
				if(aWeek.getEditRow().getProject().getProjectCode().length() > 0) out.write(aWeek.getEditRow().getProject().getProjectCode());
				if(aWeek.getEditRow().getProject().getProjectCode().length() > 0) out.write("-" + aWeek.getEditRow().getProject().getProjectActivity().getCode());
				%>
			</td>
			<td>
				<input type="hidden" name="projDesc" id="<%=aWeek.getEditRow().getProject().getProjectDesc()%>">
				</input>
				<%=aWeek.getEditRow().getProject().getProjectName()%>&#32;
				<%if(aWeek.getEditRow().getProject().getProjectDesc().length() > 0) {%>
				(<%=aWeek.getEditRow().getProject().getProjectActivity().getDescription()%>)
				<%}%>
			</td>
			<td>
				<font COLOR="#000000">
				<select name="timeTypeId" size="1" style="font-size: 10px">
					<%
					aWeek.getEditRow().getTimeTypeCombo().setSelectedValue(aWeek.getEditRow().getTimeTypeId());
					for(int i=0;i<aWeek.getEditRow().getTimeTypeCombo().getEntries().size(); i++) {
						Entry anEntry = (Entry)aWeek.getEditRow().getTimeTypeCombo().getEntries().get(i);
                                            if(anEntry.getValue() == "null") {
                                                }else{
                                                if(Integer.parseInt(anEntry.getValue())==1){
					%>
						<option	<%if(anEntry.isSelected()) {out.write("selected");}%> <%=" value=\""+anEntry.getValue()+"\"" %>>
							<%=anEntry.getText()%>
						</option>
					<%      }
                                             }
                                        }%>
				</select>
				</font>
			</td>
			<td align="right"><%if(aWeek.getEditRow().isMonExist()) {%><input type="text" name="mo" size="4" class="input" maxlength="4" value="<%=aWeek.getEditRow().getMonday()%>"/><%}%></td>
			<td align="right"><%if(aWeek.getEditRow().isTueExist()) {%><input type="text" name="tu" size="4" class="input" maxlength="4" value="<%=aWeek.getEditRow().getTuesday()%>"/><%}%></td>
			<td align="right"><%if(aWeek.getEditRow().isWedExist()) {%><input type="text" name="we" size="4" class="input" maxlength="4" value="<%=aWeek.getEditRow().getWednesday()%>"/><%}%></td>
			<td align="right"><%if(aWeek.getEditRow().isThuExist()) {%><input type="text" name="th" size="4" class="input" maxlength="4" value="<%=aWeek.getEditRow().getThursday()%>"/><%}%></td>
			<td align="right"><%if(aWeek.getEditRow().isFriExist()) {%><input type="text" name="fr" size="4" class="input" maxlength="4" value="<%=aWeek.getEditRow().getFriday()%>"/><%}%></td>
			<td align="right"><%if(aWeek.getEditRow().isSatExist()) {%><input type="text" name="sa" size="4" class="input" maxlength="4" value="<%=aWeek.getEditRow().getSaturday()%>"/><%}%></td>
			<td align="right"><%if(aWeek.getEditRow().isSunExist()) {%><input type="text" name="su" size="4" class="input" maxlength="4" value="<%=aWeek.getEditRow().getSunday()%>"/><%}%></td>
			<td>&nbsp;</td>
			<td><%if(aWeek.getEditRow().getProject().getProjectDesc().length() > 0) {%><input type="submit" name="subaction" value="Add" class="input"/><%}%></td>
		</tr>
		<tr>
			<td colspan="12">
				<table border="0" cellpadding="0" cellspacing="0" width="100%">
					<tr>
						<td bgcolor="#000000"><img src="images/sp.gif" height="1" width="1"/></td>
					</tr>
				</table>
			</td>
		</tr>
	</form>
	<form action=""  enctype="text/plain" name="saveSubmit" method="POST" onsubmit="return validateSubmit(this)">
		<tr class="NormalBold">
			<td width="15%" bgcolor="#C4C4C4">Project Code</td>
			<td width="30%" bgcolor="#C4C4C4">Description</td>
			<td width="10%" bgcolor="#C4C4C4">Time Type</td>
			<td nowrap="true" width="5%" align="right" bgcolor="<%=monBgColor%>">Mon</td>
			<td nowrap="true" width="5%" align="right" bgcolor="<%=tueBgColor%>">Tue</td>
			<td nowrap="true" width="5%" align="right" bgcolor="<%=wedBgColor%>">Wed</td>
			<td nowrap="true" width="5%" align="right" bgcolor="<%=thuBgColor%>">Thu</td>
			<td nowrap="true" width="5%" align="right" bgcolor="<%=friBgColor%>">Fri</td>
			<td nowrap="true" width="5%" align="right" bgcolor="#FF2222">Sat</td>
			<td nowrap="true" width="5%" align="right" bgcolor="#FF2222">Sun</td>
			<td nowrap="true" width="5%" align="right" bgcolor="#C4C4C4">Total</td>
			<td nowrap="true" width="5%" bgcolor="#C4C4C4">Action</td>
		</tr>
		<%for(int i=0; i<aWeek.getRows().size(); i++) {
			Row aRow = aWeek.getRow(i);
			
			//if the row is marked for removal, we skip it:
			if(aRow.isRemoved()) continue;
			
			//Background color:
			String bgColor = "";
			if(i%2==0) bgColor = "#fafafa";
		%>
			<tr bgcolor="<%=bgColor%>">
				<td>
					<%String href = "JavaScript: document.location.href=('editWeek.jsp?mode="+mode+"&action=editRow&row="+i+"&comments=' + escape(document.saveSubmit.comments.value))";%>
					<a href="<%=href%>">
						<%=aRow.getProject().getProjectCode() + "-" + aRow.getProject().getProjectActivity().getCode()%>
					</a>
				</td>
				<td class="Normal"><%=aRow.getProject().getProjectName() + "&#32;(" + aRow.getProject().getProjectActivity().getDescription()%>)</td>
				<td class="Normal"><%=aRow.getTimeType()%></td>
				<td align="right" class="Normal"><%if(aRow.isMonExist()) out.write(""+aRow.getMonday());%></td>
				<td align="right" class="Normal"><%if(aRow.isTueExist()) out.write(""+aRow.getTuesday());%></td>
				<td align="right" class="Normal"><%if(aRow.isWedExist()) out.write(""+aRow.getWednesday());%></td>
				<td align="right" class="Normal"><%if(aRow.isThuExist()) out.write(""+aRow.getThursday());%></td>
				<td align="right" class="Normal"><%if(aRow.isFriExist()) out.write(""+aRow.getFriday());%></td>
				<td align="right" class="Normal"><%if(aRow.isSatExist()) out.write(""+aRow.getSaturday());%></td>
				<td align="right" class="Normal"><%if(aRow.isSunExist()) out.write(""+aRow.getSunday());%></td>
				<td align="right" class="NormalBold"><%=aRow.getRowSum()%></td>
				<%String removeHref = "JavaScript:document.location.href=('editWeek.jsp?mode="+mode+"&action=removeRow&row="+i+"&comments=' + escape(document.saveSubmit.comments.value))";%>
				<td><a href="<%=removeHref%>"><img src="images/delete.gif" alt="Delete" border="0"/></a><td>
			</tr>
		<%}%>

		<tr bgcolor="#E5E5E5">
			<td nowrap="true" class="NormalBold">Total</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td nowrap="true" align="right" class="NormalBold"><%=aWeek.getSumRows().getTotalRow().getMonday()%></td>
			<td nowrap="true" align="right" class="NormalBold"><%=aWeek.getSumRows().getTotalRow().getTuesday()%></td>
			<td nowrap="true" align="right" class="NormalBold"><%=aWeek.getSumRows().getTotalRow().getWednesday()%></td>
			<td nowrap="true" align="right" class="NormalBold"><%=aWeek.getSumRows().getTotalRow().getThursday()%></td>
			<td nowrap="true" align="right" class="NormalBold"><%=aWeek.getSumRows().getTotalRow().getFriday()%></td>
			<td nowrap="true" align="right" class="NormalBold"><%=aWeek.getSumRows().getTotalRow().getSaturday()%></td>
			<td nowrap="true" align="right" class="NormalBold"><%=aWeek.getSumRows().getTotalRow().getSunday()%></td>
			<td nowrap="true" align="right" class="NormalBold"><%=aWeek.getSumRows().getTotalRow().getRowSum()%></td>
			<td>&nbsp;<td/>
		</tr>
		<tr bgcolor="#E5E5E5">
			<td nowrap="true" class="NormalBold">Expected Hours</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td nowrap="true" align="right" class="NormalBold"><%=aWeek.getSumRows().getExpectedRow().getMonday()%></td>
			<td nowrap="true" align="right" class="NormalBold"><%=aWeek.getSumRows().getExpectedRow().getTuesday()%></td>
			<td nowrap="true" align="right" class="NormalBold"><%=aWeek.getSumRows().getExpectedRow().getWednesday()%></td>
			<td nowrap="true" align="right" class="NormalBold"><%=aWeek.getSumRows().getExpectedRow().getThursday()%></td>
			<td nowrap="true" align="right" class="NormalBold"><%=aWeek.getSumRows().getExpectedRow().getFriday()%></td>
			<td nowrap="true" align="right" class="NormalBold"><%=aWeek.getSumRows().getExpectedRow().getSaturday()%></td>
			<td nowrap="true" align="right" class="NormalBold"><%=aWeek.getSumRows().getExpectedRow().getSunday()%></td>
			<td nowrap="true" align="right" class="NormalBold"><%=aWeek.getSumRows().getExpectedRow().getRowSum()%>	</td>
			<td>&nbsp;</td>
		</tr>
		<tr bgcolor="#E5E5E5">
			<td nowrap="true" class="NormalBold">Difference</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td nowrap="true" align="right" class="NormalBold"><%=aWeek.getSumRows().getDifferenceRow().getMonday()%></td>
			<td nowrap="true" align="right" class="NormalBold"><%=aWeek.getSumRows().getDifferenceRow().getTuesday()%></td>
			<td nowrap="true" align="right" class="NormalBold"><%=aWeek.getSumRows().getDifferenceRow().getWednesday()%></td>
			<td nowrap="true" align="right" class="NormalBold"><%=aWeek.getSumRows().getDifferenceRow().getThursday()%></td>
			<td nowrap="true" align="right" class="NormalBold"><%=aWeek.getSumRows().getDifferenceRow().getFriday()%></td>
			<td nowrap="true" align="right" class="NormalBold"><%=aWeek.getSumRows().getDifferenceRow().getSaturday()%></td>
			<td nowrap="true" align="right" class="NormalBold"><%=aWeek.getSumRows().getDifferenceRow().getSunday()%></td>
			<td nowrap="true" align="right" class="NormalBold"><%=aWeek.getSumRows().getDifferenceRow().getRowSum()%></td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td colspan="12">
				<table border="0" cellpadding="0" cellspacing="0" width="100%">
					<tr><td bgcolor="#000000"><img src="images/sp.gif" height="1" width="1"/></td></tr>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan="12">&#160;</td>
		</tr>
		<tr>
			<td colspan="12">
				<table border="0" width="100%" cellspacing="0" cellpadding="0">
					<tr>
						<td valign="top" class="NormalBold">Week comment:</td>
						<td nowrap="true">
							<textarea class="input" id="comments" name="comments" rows="15" cols="60"><%=aWeek.getWeekComment()%></textarea>
						</td>
						<td align="right" valign="top">
							<table border="1" width="300" cellspacing="0" cellpadding="0" bordercolor="#7D7D7D">
								<tr>
									<td>
										<table border="0" width="100%" bgcolor="#E5E5E5" cellspacing="0" cellpadding="0">
											<tr>
												<td align="center" height="40">
												
													<!--<input class="input" type="submit" name="btnSubmit" value="Submit"/>&#160;&#160;					-->
													<input class="input" type="button" name="btnSubmit" value="Submit" onclick="document.location.href='approveWeekResult.jsp?btnSubmit=Submit&comments=' + escape(document.saveSubmit.comments.value)"/>&#160;&#160;
													<input class="input" type="button" name="btnCancel" value="Cancel" onclick="window.location.href='../frames/mainFrame.jsp'"/>&#160;&#160;
													<input class="input" type="button" name="btnSave" value="&#160;Save&#160;" onclick="document.location.href='approveWeekResult.jsp?btnSave=Save&comments=' + escape(document.saveSubmit.comments.value)"/>
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		</form>
	</table>
	</body>
</html>
<%}%>