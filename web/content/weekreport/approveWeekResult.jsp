<%@ page session="true"%>
<%@ page import="java.util.*, com.intiro.itr.ITRResources, com.intiro.itr.logic.weekreport.WeekReport, com.intiro.itr.util.personalization.UserProfile, com.intiro.toolbox.log.IntiroLog"%>
<%@ page import="com.intiro.itr.common.JavaMail.*,javax.servlet.jsp.tagext.*"%>

<%@ page import="com.intiro.itr.util.personalization.*, com.intiro.itr.common.*"%>



<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.addHeader("Cache-Control","no-store");
    UserProfile userProfile = (UserProfile) session.getAttribute(ITRResources.ITR_USER_PROFILE);
    WeekReport aWeek = (WeekReport) session.getAttribute(ITRResources.ITR_WEEK_REPORT);
    String mode = (String)request.getAttribute("mode");
%>

<%
	String weekComment = "";

	//START ACTION, Find out what action is called and perform it
	String save = request.getParameter("btnSave");
	String submit = request.getParameter("btnSubmit");
	String approve = request.getParameter("btnApprove");
	String reject = request.getParameter("btnReject");
	String action = request.getParameter("action");

      // if weekComment is not set. Read it from request object and set it
      if (request.getParameter("comments") != null && request.getParameter("comments").length() > 0) {
        weekComment = request.getParameter("comments");
        aWeek.setWeekComment(weekComment);
      }

      //SAVE THE WEEKREPORT
      if (save != null) {

        //mode = "save";
        aWeek.save();

        //redirect user to todo page:
      	StringBuffer redirectURLPath = new StringBuffer("");
	    StringTokenizer tokens = new StringTokenizer(request.getRequestURL().toString(), "/");
	    for(int i=4; i<tokens.countTokens(); i++) {
	    	redirectURLPath.append("../");
	    }
	    redirectURLPath.append("viewWeeks.jsp?mode=todo");
	    response.sendRedirect(redirectURLPath.toString());

        return;
      }

      //SUBMIT THE WEEKREPORT
      else if (submit != null) {
        if (aWeek.checkIfOkToSubmit()) {

        	/*Submit the week report*/
          	aWeek.submit();

	        //redirect user to todo page:
	      	StringBuffer redirectURLPath = new StringBuffer("");
		    StringTokenizer tokens = new StringTokenizer(request.getRequestURL().toString(), "/");
	    	for(int i=4; i<tokens.countTokens(); i++) {
    			redirectURLPath.append("../");
	    	}
	    	redirectURLPath.append("viewWeeks.jsp?mode=todo");
	    	response.sendRedirect(redirectURLPath.toString());

          	return;
        }
      }

      //FORCE SUBMIT OF THE WEEKREPORT
      else if (action != null && action.equalsIgnoreCase("forceSubmit")) {

        //Stuid check to force:
        aWeek.checkIfOkToSubmit();

        //Submit the week report
        aWeek.submit();

        //redirect user to todo page:
      	StringBuffer redirectURLPath = new StringBuffer("");
	    StringTokenizer tokens = new StringTokenizer(request.getRequestURL().toString(), "/");
    	for(int i=4; i<tokens.countTokens(); i++) {
			redirectURLPath.append("../");
    	}
    	redirectURLPath.append("viewWeeks.jsp?mode=todo");
    	response.sendRedirect(redirectURLPath.toString());

        return;
      }

      // Reject a WeekReport
      else if (reject != null && reject.trim().equalsIgnoreCase("Reject")) {

        //Reject the week report
        aWeek.reject();

        //UserProfile currUser = aWeek.getUserProfile();
        //com.intiro.itr.logic.email.Email mail = currUser.getEmail(0);
        //String adress = mail.getAddress();

        //String body = "Felaktig vecka: " + aWeek.getFromDate().getWeekOfYear() + "\nKommentar: "  + aWeek.getWeekComment();
        //JavaMail m = new JavaMail();
   
        //From, to, subject, message as string
        //m.SendSimpleMail("itr@intiro.se", adress, "WEEK REJECTED!", body);
   
        

        //redirect admin to weeks to approve.
      	StringBuffer redirectURLPath = new StringBuffer("");
	    StringTokenizer tokens = new StringTokenizer(request.getRequestURL().toString(), "/");
    	for(int i=4; i<tokens.countTokens(); i++) {
			redirectURLPath.append("../");
    	}
    	redirectURLPath.append("admin.jsp");
    	response.sendRedirect(redirectURLPath.toString());

        return;
      }

      //Approve a WeekReport
      else if (approve != null && approve.trim().equalsIgnoreCase("Approve")) {

        //Approve a week report*/
        aWeek.approve();

        //redirect admin to weeks to approve.
      	StringBuffer redirectURLPath = new StringBuffer("");
	    StringTokenizer tokens = new StringTokenizer(request.getRequestURL().toString(), "/");
    	for(int i=4; i<tokens.countTokens(); i++) {
			redirectURLPath.append("../");
    	}
    	redirectURLPath.append("admin.jsp");
    	response.sendRedirect(redirectURLPath.toString());

        return;
      }
      else {
        if (IntiroLog.ce()) {
          IntiroLog.criticalError(getClass(), getClass().getName() + ".doGet(): ACTION NOT FOUND");
        }

        int log = IntiroLog.getInstance().getLoggingLevel();
        IntiroLog.getInstance().setLoggingLevel(6);

        Enumeration names = request.getParameterNames();

        while (names.hasMoreElements()) {
          String oneName = (String) names.nextElement();
          String oneValue = request.getParameter(oneName);

          if (oneValue != null) {
            if (IntiroLog.d()) {
              IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): " + oneName + " = " + oneValue);
            }
          }
        }

        IntiroLog.getInstance().setLoggingLevel(log);
      }

      /*END ACTION*/

      // If this code is reached we must show a warning page
%>		
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
	<head>
		<title>Submit Result</title>
		<link rel="stylesheet" href="include/ITR.CSS"/>
	</head>
	<body bgcolor="#eeeeee" topmargin="2" leftmargin="0" rightmargin="0" marginwidth="0" marginheight="0">
		<center>
			<table border="0" cellpadding="0" cellspacing="0" width="100%">
				<tr bgcolor="#aaaacc" align="center">
					<td class="Title1">Submit Result</td>
				</tr>
			</table>
			<p>
				<table cellspacing="2" cellpadding="0" border="0" width="300" align="center">
					<tr>
						<td height="30" align="right" valign="bottom" colspan="2" class="NormalBold"> |<a href="OnlineHelp">Help</a>| </td>
					</tr>
					<tr>
						<td colspan="2">
							<table border="0" cellpadding="0" cellspacing="0" width="100%">
								<tr><td bgcolor="#000000"><img src="images/sp.gif" height="1" width="1"/></td></tr>
							</table>
						</td>
					</tr>
					<tr>
						<td colspan="2" class="Normal" width="100%" align="center">
							<!-- Hidden field with week comments for keeping it in the request to the next page.-->
							<form action="" name="hiddenForm" method="post">
								<input type="hidden" name="comments" value="<%=aWeek.getWeekComment()%>"/>
							</form>

							<table width="100%" border="0" cellspacing="0" cellpadding="1" bgcolor="#ffffff">
								<tr>
									<td align="left" class="NormalBold">&#160;Date:</td>
									<td align="left" nowrap="true" class="Normal"><%=aWeek.getFromDate().getYear()%> <%=aWeek.getFromDate().getMonthNameShort()%></td>
									<td align="left" class="NormalBold">&#160;Week:</td>
									<td align="left" nowrap="true" class="Normal"><%=aWeek.getFromDate().getWeekOfYear()%> (<%=aWeek.getFromDate().getWeekPart()%>)&#160;</td>
								</tr>
								<tr>
									<td align="left" nowrap="true" class="NormalBold">&#160;Range:</td>
									<td align="left" nowrap="true" colspan="3" class="Normal"><%=aWeek.getFromDate().getCalendarInStoreFormat() + " -" + aWeek.getToDate().getCalendarInStoreFormat()%></td>
								</tr>
								<tr>
									<td align="left" nowrap="true" class="NormalBold">&#160;Expected hours:</td>
									<td align="left" nowrap="true" colspan="3" class="Normal"><%=aWeek.getSumRows().getExpectedRow().getRowSum()%></td>
								</tr>
								<tr>
									<td align="left" nowrap="true" class="NormalBold">&#160;Normal hours:</td>
									<td align="left" nowrap="true" colspan="3" class="Normal"><%aWeek.getSumRows().calcSum(aWeek.getRows());%><%=aWeek.getSumRows().getTotalRow().getRowSum()%></td>
								</tr>
							</table>
							</td>
							</tr>
							<tr>
							<td colspan="2" align="center" class="Normal">
							<br x="x"/>
							Tried to submit your week report, 
							but your normal hours does not 
							corresponed to expected hours.
							<br x="x"/>
							<br x="x"/>
							Do you still want to submit your week report?
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<table border="0" cellpadding="0" cellspacing="0" width="100%">
								<tr><td bgcolor="#000000"><img src="images/sp.gif" height="1" width="1"/></td></tr>
							</table>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center">
							<input class="input" type="button" name="yes" value="&#160;Yes&#160;" onclick="window.location.href='approveWeekResult.jsp?mode=<%=mode%>&action=forceSubmit'"/>
							&#160;&#160;
							<input class="input" type="button" name="no" value="&#160;No&#160;" onclick="window.location.href='editWeek.jsp?mode=<%=mode%>&action=cancelSubmit'"/>
						</td>
					</tr>
				</table>
			</p>
		</center>
	</body>
</html>