<!DOCTYPE html>
<html>

    <head>
        <link rel="stylesheet" type="text/css" href="css/basic.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>PDS</title>
    </head>
    <body>
        <div id="container">
            <div id="header">
                <h1>
                    Patient Direct Scheduling 
                </h1>
            </div>
            <div id="content">

                <div id="loginbox" style="text-align: center">


                    <form method="POST" action="LoginServlet">
                        <input type="text" tabindex="1" placeholder="Username" name="username" required class="myinput"><br>
                        <input type="password" tabindex="2" placeholder="Password" name="password" required class="myinput"><br>
                        <input type="submit" value="Login" tabindex="4" class="mybutton">  
                    </form>
                </div>
            </div>

            <div id="footer">
                Copyright © Sumitro Majumdar, 2014
            </div>
        </div>
    </body>
</html>

