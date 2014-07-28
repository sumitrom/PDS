<%-- 
    Document   : connected
    Created on : Feb 24, 2014, 5:55:54 PM
    Author     : longview
--%>

<%@page import="org.sumi.rpc.procedures.RemoteProcedure"%>
<%@page import="org.sumi.rpc.RPCClient"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="http://code.jquery.com/mobile/1.4.2/jquery.mobile-1.4.2.min.css">
        <script src="http://code.jquery.com/jquery-1.10.2.min.js"></script>
        <script src="http://code.jquery.com/mobile/1.4.2/jquery.mobile-1.4.2.min.js"></script>
    </head>

    <%

        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        if (session.getAttribute("uname") == null) {
            response.sendRedirect("login.jsp");
        }

        String conninfo = (String) session.getAttribute("conninfo");
    %>

    <body>
        Logged into: <%=conninfo%>
    </body>
</html>
