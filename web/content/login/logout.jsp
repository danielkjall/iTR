<%@ page session="true"%>
<%
    session.invalidate();
    //response.sendRedirect("login.jsp");
%>

<html>
    <head>
        <META 
     HTTP-EQUIV="Refresh"
     CONTENT="0; URL=http://<%=request.getServerName()%>:<%=request.getServerPort()%>/WebApp/login.jsp">
    </head>
    <body>
        Du loggas nu ut...

    </body>
    
</html>
