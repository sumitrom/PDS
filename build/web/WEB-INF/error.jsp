<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="css/basic.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <title>Error Page</title>
    </head>
    <body>

        <br><br>

        <% String error_string = (String) session.getAttribute("err_msg");
            session.invalidate();
        %>

        <div id="container">

            <div id="loginbox" style="text-align: center">
                <h3><b>The following error occured: <%=error_string%></b></h3>

                <form class="box" method="LINK" action="login.jsp">
                    
                        <input type="submit" class="mybutton" value="Back to Login">
                    
                </form>
            </div> 
            <div id="footer">
                Copyright © Sumitro Majumdar, 2014
            </div>
        </div>
    </body>
</html>