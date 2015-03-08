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
                        <li><a href="/DailyChallenge-war/register.jsp" style="font-size: 18px; font-weight: bold; color: #3e84c0">Registrieren</a></li> 
                    </ul>
                    <ul class="nav navbar-nav navbar-right" style="margin-right: 0px;">
                        <li style="font-size: 18px; font-weight: bold; margin-top: 12px; color: #646464">Bereits Registriert?</li>
                        <li><a href="/DailyChallenge-war/Login.jsp" style="font-size: 18px; font-weight: bold; color: #3e84c0">Anmelden!</a></li>
                    </ul>
                </div>
            </div>
        </nav>
        <!-- Inhalt -->
        <div class="container">
            <div class="jumbotron">
                <h2 style="margin-left: 17%;">Melde dich hier an!</h2>
                <br>
                <form class="form-horizontal" method="post" action="/DailyChallenge-war/mainservlet?step=registrieren">
                    <div class="form-group">
                        <label for="User" class="control-label col-xs-2">Benutzername:</label>
                        <div class="col-xs-10">
                            <input name="username" type="text" class="form-control" id="User" placeholder="Benutzname">  
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="Mail" class="control-label col-xs-2">E-Mail:</label>
                        <div class="col-xs-10">
                            <input name="email" type="text" class="form-control" id="Mail" placeholder="E-Mail">  
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="password" class="control-label col-xs-2">Passwort:</label>
                        <div class="col-xs-10">
                            <input name="userpassword" type="password" class="form-control" id="password" placeholder="Passwort">  
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="passwordrepeat" class="control-label col-xs-2">Passwort:</label>
                        <div class="col-xs-10">
                            <input name="userpassword2" type="password" class="form-control" id="passwordrepeat" placeholder="Passwort wiederholen">  
                        </div>
                    </div>
                    <p><input class="btn btn-lg btn-primary" type="submit" value="Anmelden" style="margin-left: 17%;"/></p>
                </form>
            </div>
        </div>
    </body>
</html>
