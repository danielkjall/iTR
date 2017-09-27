<%@ page session="true"%>
<%@ page isErrorPage="true" %>

<%@ page import="java.io.*, com.intiro.itr.ITRResources, com.intiro.itr.util.personalization.*"%>

<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.addHeader("Cache-Control", "no-store");
	
    UserProfile userProfile = (UserProfile) session.getAttribute(ITRResources.ITR_USER_PROFILE);
	
	//If we dont have a userprofile the session has timed out, redirect to login page:
	if(userProfile == null) {
		session.setAttribute(com.intiro.itr.ITRResources.LOGIN_MESSAGE, "Session timeout, reauthenticate");
		response.sendRedirect("login.jsp");
		return;
	}

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
	<script type="text/javascript">
	if(parent.location.href!=window.location.href) {
		parent.location.href = window.location.href;
	}
</script>
	
	<body bgcolor=#ffffff>
		<font class="h2">JSP Error Page</font>
		<p> An exception was thrown: <b> <%= exception %></b>
		<p> With the following stack trace:
		<pre class="NormalBold">
			<%
			    ByteArrayOutputStream ostr = new ByteArrayOutputStream();
			    exception.printStackTrace(new PrintStream(ostr));
			    out.print(ostr);
			%>
		</pre>
		<p>
		<hr width=80%>
		<a href="../login/login.jsp" class="NormalBold">Login again?</a>
	</body>
</html>

