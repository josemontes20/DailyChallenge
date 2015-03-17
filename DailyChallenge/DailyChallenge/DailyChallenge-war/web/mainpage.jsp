<%@page import="model.Anwender"%>
<%@page import="model.Kategorie"%>
<%@page import="java.util.List"%>
<%@page import="model.Challenge"%>
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

            Anwender a = (Anwender) session.getAttribute("anwender");   
            
            String abmelden = (String) request.getSession().getAttribute("abmelden");
            
            if (abmelden.equals("true")) {
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
                        <li><a href="/DailyChallenge-war/profil.jsp" style="font-size: 18px; font-weight: bold; color: #3e84c0">Profileinstellungen</a></li> 
                    </ul>

                    <ul class="nav navbar-nav navbar-right" style="margin-right: 0px;">
                        <li style="font-size: 18px; font-weight: bold; margin-top: 12px; color: #646464">Nicht <%= a.getUsername()%>?</li>
                        <li><a href="/DailyChallenge-war/mainservlet?step=abmelden" style="font-size: 18px; font-weight: bold; color: #3e84c0">Abmelden!</a></li>
                    </ul>
                </div>
            </div>
        </nav>
        <!-- Inhalt -->
        <div class="container">
            <div class="jumbotron" style="margin-bottom: 20px;">
                <h1>Servus, <%= a.getUsername()%>!</h1>
                <h2>Deine heutigen Challenges!</h2>
            </div>
                <%
                    // Hier wird die Challenge-Liste des Users übergeben   
                    List<Challenge> challenges = (List) request.getSession().getAttribute("challenges");
                    
                    if (!challenges.isEmpty()) {
                        for (Challenge cha : challenges) {
                %>  <div class="jumbotron"  style="margin-bottom: 10px;">
                    <p> <%= (String) cha.getKategorie().getName()%> </p>
                    <p> <%= cha.getBeschreibung()%> </p>   
                </div>
                <%
                    }
                } else {
                %>  <div class="jumbotron">
                    <p> Noch keine Challenges? Dann schnell in deinem <a href="profil.jsp">Profil</a> Kategorien wählen!</p> 
                </div><%
                    }
                %>            
        </div>
    </body>
</html>
