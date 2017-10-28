<%@ page session="true"%>
<%@ page import="com.intiro.itr.util.StringRecordset, com.intiro.itr.ITRResources, com.intiro.itr.util.personalization.*, com.intiro.itr.logic.weekreport.*, com.intiro.itr.logic.*"%>

<%
    synchronized (this) {
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.addHeader("Cache-Control", "no-store");

        UserProfile userProfile = (UserProfile) session.getAttribute(ITRResources.ITR_USER_PROFILE);

        //Load project properties from the project id:
        WeekReport aWeek = (WeekReport) session.getAttribute(ITRResources.ITR_WEEK_REPORT);
        aWeek.getEditRow().getProject().load(request.getParameter("projId"));
        session.setAttribute(ITRResources.ITR_WEEK_REPORT, aWeek);
        String mode = (String) request.getAttribute("mode");

        ProjectSubCodeSelector activitySelector = new ProjectSubCodeSelector(userProfile, request.getParameter("projId"));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
    <head>
        <link REL="stylesheet" TYPE="text/css" HREF="include/ITR.CSS"/>
        <meta HTTP-EQUIV="Content-Type" CONTENT="text/html;CHARSET=iso-8859-1"/>
        <title>iTR - Select project</title>
    </head>
    <body bgcolor="#f3f3f3" leftmargin="0" rightmargin="0" topmargin="0" marginwidth="0" marginheight="0">
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
            <tr bgcolor="#aaaacc" align="center">
                <td class="Title1">Select Project Subcode</td>
            </tr>
        </table>
        <br x="x"/>
        <div align="right">
            <table border="0" cellpadding="0" cellspacing="0">
                <tr><td colspan="3" bgcolor="#000000"><img src="images/blackLight.gif" height="1" width="1"/></td></tr>
                <tr>
                    <td bgcolor="#000000"><img src="images/blackLight.gif" height="1" width="1"/></td>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="1" bgcolor="#ffffff">
                            <tr>
                                <td nowrap="true" width="50%" class="NormalBold">&#160;Project code&#160;</td>
                                <td nowrap="true" width="50%" class="Normal">
                                    <%=aWeek.getEditRow().getProject().getProjectCode()%>&#160;
                                </td>
                            </tr>
                            <tr>
                                <td nowrap="true" width="50%" class="NormalBold">&#160;Project Name&#160;</td>
                                <td nowrap="true" width="50%" class="Normal">
                                    <%=aWeek.getEditRow().getProject().getProjectName()%>&#160;
                                </td>
                            </tr>
                        </table>
                    </td>
                    <td bgcolor="#000000"><img src="images/blackLight.gif" height="1" width="1"/></td>
                </tr>
                <tr>
                    <td bgcolor="#000000"><img src="images/blackLight.gif" height="1" width="1"/></td>				
                </tr>
            </table>
        </div>
        <table WIDTH="100%" border="0">
            <tr>
                <td colspan="2">
                    <table border="0" cellpadding="0" cellspacing="0" width="100%">
                        <tr><td bgcolor="#000000"><img src="images/sp.gif" height="1" width="1"/></td></tr>
                    </table>
                </td>
            </tr>
            <tr BGCOLOR="#C4C4C4" class="NormalBold">
                <td width="10%">Code</td>
                <td width="90%">Description</td>
            </tr>
            <%
                StringRecordset rs = activitySelector.getProjTable().getResultset();
                int trIndex = 0;
                while (!rs.getEOF()) {
                    String bgColor = "";
                    if (trIndex % 2 == 0) {
                        bgColor = "#fafafa";
                    }
            %>
            <tr <%=bgColor%>>
                <td class="Normal">
                    <a href="editWeek.jsp?mode=<%=mode%>&action=projSelected&projSubId=<%=rs.getField("id")%>&projSubCode=<%=rs.getField("code")%>"><%=rs.getField("code")%></a>
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
                <td colspan="2">
                    <table border="0" cellpadding="0" cellspacing="0" width="100%">
                        <tr><td bgcolor="#000000"><img src="images/sp.gif" height="1" width="1"/></td></tr>
                    </table>
                </td>
            </tr>
        </table>
    </body>
</html>
<%
    }
%>
