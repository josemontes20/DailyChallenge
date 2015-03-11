<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Daily Challenge</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <!-- Bootstrap core CSS -->
        <link href="bootstrap/css/bootstrap.css" rel="stylesheet">
        <!-- Bootstrap theme -->
        <link href="bootstrap/css/bootstrap-theme.css" rel="stylesheet">
        <!-- Logo in Navbar -->
        <link href="bootstrap/css/logo-nav.css" rel="stylesheet">
    </head>
    <body>
        <%
            //Browser-Cache löschen
            response.addHeader("Cache-Control", "no-cache");
            response.addHeader("Cache-Control", "no-store");
            response.addDateHeader("Expires", 0);
            response.addHeader("Pragma", "no-cache");

            String user = (String) session.getAttribute("anwendername");

            if (user.equals("null")) {
                response.sendRedirect("/DailyChallenge-war/login.jsp");
            }

        %>

        <!-- Navbar -->
        <nav class="navbar navbar-default navbar-fixed-top">
            <div class="container" style="padding-left: 35px;">
                <div class="navbar-header">
                    <a class="navbar-brand" href="index.html">
                        <img src="images/pfeil.png" style="height: 50px; width: auto;">
                    </a>
                </div>
                <div>
                    <ul class="nav navbar-nav">
                        <li><a href="/DailyChallenge-war/profil.jsp" style="font-size: 18px; font-weight: bold; color: #3e84c0">Profil</a></li> 
                    </ul>
                    
                    <ul class="nav navbar-nav navbar-right" style="margin-right: 0px;">
                        <li style="font-size: 18px; font-weight: bold; margin-top: 12px; color: #646464">Nicht <%= user%>?</li>
                        <li><a href="/DailyChallenge-war/mainservlet?step=abmelden" style="font-size: 18px; font-weight: bold; color: #3e84c0">Abmelden!</a></li>
                    </ul>
                </div>
            </div>
        </nav>
        <!-- Inhalt -->
        <div class="container">
            <div class="jumbotron">
                <h1>Servus, <%= user%>!</h1>
                <h2>Deine heutigen Challenges!</h2>
                <ol>
                    <li>...</li> 
                    <li>...</li> 
                    <li>...</li> 
                </ol>


            </div>
        </div>
    </body>
</html>
