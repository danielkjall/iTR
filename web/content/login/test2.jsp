<%@ page session="true"%>
<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.addHeader("Cache-Control", "no-store");
	String action = request.getParameter("action");
	String mondaytest = (String)request.getAttribute("monday");
	out.print("mondaytest: "+mondaytest);
	out.print("action: "+action);

    String sunHours = "0";

    /*ADD A ROW*/
    if (action != null && action.equalsIgnoreCase("addrow")) {

        /*set parameters*/
        String su = request.getParameter("sunday");
        out.write(", su: "+su);
        if (su != null && su.length() > 0) {
          sunHours = su;
        }
      }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
<head>
<link REL="stylesheet" TYPE="text/css" HREF="include/ITR.CSS"/>
<meta HTTP-EQUIV="Content-Type" CONTENT="text/html;CHARSET=iso-8859-1"/>
<title>ITR - Edit Week</title>
<script type="text/javascript">
	<%@ include file="../../helpers/en_validate.js" %>

	function validateAdd(theForm) {
		return true;
	}
</script>
</head>
<body bgcolor="#f3f3f3" leftmargin="0" rightmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<form action="test2.jsp" name="addARow" enctype="text/plain" method="get" onsubmit="return validateAdd(this)">
	<table WIDTH="100%" border="0">
		<tr>
			<td align="right"><input type="text" name="sunday" size="4" class="input" maxlength="4" value="<%=sunHours%>"/></td>
			<td><input type="submit" name="subaction" value="Add" class="input"/><input type="hidden" name="action" value="addRow"/></td>
		</tr>
	</table>
	</form>
	</body>
</html>