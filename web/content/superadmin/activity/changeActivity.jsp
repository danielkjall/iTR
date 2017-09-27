<%@ page session="true"%>
<%@ page import="com.intiro.itr.ITRResources, com.intiro.itr.util.personalization.*, com.intiro.itr.logic.superadmin.activity.ActivityEditor"%>

<%
    UserProfile userProfile = (UserProfile) session.getAttribute(ITRResources.ITR_USER_PROFILE);

    //START ACTION, Find out what action is called and perform it
    String edit = request.getParameter("btnEdit");
    String add = request.getParameter("btnAdd");
    String view = request.getParameter("btnView");
    String delete = request.getParameter("btnDelete");
    //boolean bEdit = false;
    boolean bAdd = false;
    //boolean bView = false;
    //boolean bDelete = false;

    //Find out what button was pressed
    String mode = "error";

    if (edit != null && edit.length() > 0) {
      mode = edit.trim();
      //bEdit = true;
    } else if (add != null && add.length() > 0) {
      mode = add.trim();
      bAdd = true;
    } else if (view != null && view.length() > 0) {
      mode = view.trim();
      //bView = true;
    } else if (delete != null && delete.length() > 0) {
      mode = delete.trim();
      //bDelete = true;
    }

    String activityid = "-1";

    if (!bAdd) {

      //Fetch activityid
      activityid = request.getParameter("activity");
    }

    //Create xmlCarrier
    ActivityEditor xmlCarrier = new ActivityEditor(userProfile, mode, activityid);

    //Set modified project in session
    session.setAttribute(ITRResources.ITR_MODIFIED_PROJECT, xmlCarrier);
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
          //Description
          if (!isValid(theForm.description, "Description"))
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
          <td class="Title1"><%=xmlCarrier.getTitle()%> Activity</td>
        </tr>
      </table>
      <form method="POST" action="changeActivityResult.jsp" name="personalinfo" onsubmit="return validate(this)">
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
                    <td align="left" colspan="2" class="NormalBold">Activaty Profile</td>
                  </tr>
                  <tr>
                    <td align="center" colspan="2">&#160;</td>
                  </tr>
                  <tr>
                    <td align="left" width="25%"> Code
                      <input type="hidden" name="activityid" value="<%=xmlCarrier.getActivityId()%>"/>
                    </td>
                    <td align="left" width="75%">
                      <%if (xmlCarrier.isInAddMode() || xmlCarrier.isInEditMode()) {%>
                      <input type="text" name="code" size="20" maxlength="20" class="input" value="<%=xmlCarrier.getModifiedActivity().getCode()%>"/>
                      <%}%>
                      <%if (xmlCarrier.isInViewMode() || xmlCarrier.isInDeleteMode()) {%>
                      <%=xmlCarrier.getModifiedActivity().getCode()%>
                      <%}%>
                    </td>
                  </tr>
                  <tr>
                    <td align="left" width="25%"> Description</td>
                    <td align="left" width="75%">
                      <%if (xmlCarrier.isInAddMode() || xmlCarrier.isInEditMode()) {%>
                      <input type="text" name="description" size="20" maxlength="20" class="input" value ="<%=xmlCarrier.getModifiedActivity().getDescription()%>"/>
                      <%}%>
                      <%if (xmlCarrier.isInViewMode() || xmlCarrier.isInDeleteMode()) {%>
                      <%=xmlCarrier.getModifiedActivity().getDescription()%>
                      <%}%>
                    </td>
                  </tr>
                </table>
              </td>
              <td>
                <table cellspancing="0" cellpadding="0" border="0" width="400">
                  <tr>
                    <td align="left" colspan="2" class="NormalBold"></td>
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
                    String button = "";
                    if (xmlCarrier.isInDeleteMode()) {
                      button = "<input type=\"button\" name=\"btnDelete\" value=\" Delete \" class=\"input\" onclick=\"window.location.href='changeActivityResult.jsp?btnDelete=Delete'\"/>&#160;";
                    } else if (xmlCarrier.isInAddMode() || xmlCarrier.isInEditMode()) {
                      button = "<input type=\"submit\" name=\"btnSave\" value=\" Save \" class=\"input\"/>&#160;";
                    }
                    out.println(button);
                %>

                <input type="button" name="Cancel" value=" Cancel " onclick="window.location.href='changeActivityQuery.jsp'" class="input"/>
              </td>
            </tr>
          </table>
        </p>
      </form>
    </center>
  </body>
</html>