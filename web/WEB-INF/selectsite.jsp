<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%-- 
    Document   : selectsite
    Created on : Jul 17, 2014, 11:22:00 AM
    Author     : Sumitro
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>



<!DOCTYPE html>
<html>
    <head>
        <title>PDS - Select Site</title>
        <link rel="stylesheet" type="text/css" href="css/basic.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
    </head>
    <body>
        <%
            int dfn = (Integer) session.getAttribute("dfn");
            String lastname = (String) session.getAttribute("lastname");
            String firstname = (String) session.getAttribute("firstname");
        %>
        <h1>
            Welcome <%= lastname%>,<%=firstname%>
            <br>
            Patient DFN: <%= dfn%>
            <h3>Please select from one of the following Hospitals</h3>
        </h1>
        <%
            Connection conn = null;

            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String url = "jdbc:mysql://localhost/test";
            conn = DriverManager.getConnection(url, "pdsuser", "pdsadmin");
            System.out.println("From JSP: connection closed? " + conn.isClosed());

            String query = "select b.siteid, b.sitename, b.ipaddress, b.port from test.patient a, test.site b, test.patsite c where a.dfn = c.dfn and b.siteid = c.siteid and a.dfn = " + dfn;
            System.out.println("From JSP: " + query);

            ResultSet rs = null;
            Statement statement;
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
        %>

        <TABLE align="center" cellpadding="5" border="1" style="background-color: #ffffcc;">
            <TR>
                <%String blah =rs.getString(2);%>            
                 <td><a href="ApptDetailServlet?VAMC=<%=rs.getString(1)%>"><%=blah%></a></td>
            </TR>
            <% } %>
            <%
                rs.close();
                conn.close();
                System.out.println("From JSP: connection closed? " + conn.isClosed());
            %>
    </body>
</html>
