<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
<xsl:template match="/">
<html>
<head>
<title>Time Portal</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
</head>
<frameset rows="80,586*" cols="175,837*" frameborder="NO" border="0" framespacing="0"> 
  <frame name="cornerFrame" scrolling="NO" noresize="yes" src="CornerFrame" />
  <frame name="topFrame" scrolling="NO" noresize="yes" src="TopFrame" />
   <frame name="leftFrame" src="NavigationFrame" frameborder="NO" scrolling="NO" />
  <frame name="mainFrame" src="MainFrame" />
</frameset>
<noframes>
<body bgcolor="#FFFFFF">
I'm afraid you can't view this site because your browser doesn't support frames.
</body>
</noframes> 
</html>
</xsl:template>
</xsl:stylesheet>