<%@ page session="true"%>
<%
response.setHeader("Cache-Control", "no-cache");
response.setHeader("Pragma", "no-cache");
response.addHeader("Cache-Control","no-store");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
<head>
<title>Time Portal</title>
<meta HTTP-EQUIV="Content-Type" CONTENT="text/html;CHARSET=iso-8859-1"/>

</head>
<frameset rows="80,586*" cols="168,837*" frameborder="NO" border="0" framespacing="0"> 
  <frame name="cornerFrame" scrolling="NO" noresize="yes" src="cornerFrame.jsp" />
  <frame name="top" id="top" scrolling="NO" noresize="yes" src="topFrame.jsp" />
  <frame name="navigation" id="navigation" frameborder="NO" scrolling="NO" src="navigationBar.jsp" />
  <frame name="basefrm" id="content" src="viewWeeks.jsp?mode=todo" />
</frameset>
<noframes>
<body bgcolor="#FFFFFF">
I'm afraid you can't view this site because your browser doesn't support frames.
</body>
</noframes> 
</html>
