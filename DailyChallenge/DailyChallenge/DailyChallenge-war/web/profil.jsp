<%@page import="daily.model.Anwender"%>
<%@page import="daily.model.Kategorie"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Daily Challenge - Profil</title>
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

            Anwender a = (Anwender) request.getSession().getAttribute("anwender");

            String abmelden = (String) request.getSession().getAttribute("abmelden");

            if (abmelden.equals("true")) {
                response.sendRedirect("/DailyChallenge-war/login.jsp");
            }

        %>

        <!-- Navbar -->
        <nav class="navbar navbar-default navbar-fixed-top">
            <div class="container" style="padding-left: 35px;">
                <div class="navbar-header">
                    <a class="navbar-brand" href="mainpage.jsp">
                        <img src="images/pfeil.png" style="height: 50px; width: auto;">
                    </a>
                </div>
                <div>
                    <ul class="nav navbar-nav">
                        <li><a href="/DailyChallenge-war/mainpage.jsp" style="font-size: 18px; font-weight: bold; color: #3e84c0">Hauptseite</a></li> 
                    </ul>

                    <ul class="nav navbar-nav navbar-right" style="margin-right: 0px;">
                        <li style="font-size: 18px; font-weight: bold; margin-top: 14px; color: #646464">Nicht <%= a.getUsername()%>?</li>
                        <li><a href="/DailyChallenge-war/frontservlet?step=abmelden" style="font-size: 18px; font-weight: bold; color: #3e84c0">Abmelden!</a></li>
                    </ul>
                </div>
            </div>
        </nav>
        <div class="container">
            <!-- Überschrift -->
            <div class="jumbotron" style="margin-bottom: 10px;">
                <h1>Profileinstellungen!</h1>        
            </div>
            <!-- Auswahl der Kategorien -->
            <div class="jumbotron" style="padding-top: 2%;padding-bottom: 2%;">
                <h2>Kategorien</h2>
                <form class="form-horizontal" method="post" action="/DailyChallenge-war/frontservlet?step=kategorien_auswaehlen">
                <%
                    List<Kategorie> kategorien = (List) request.getSession().getAttribute("kategorien");
                    
                    if (kategorien != null && !kategorien.isEmpty()) {

                            for (Kategorie kat : kategorien) {

                                boolean containsKategorie = false;

                                for (Kategorie katUser : a.getAnwender_kategorien()) {
                                    if (katUser.getName().equals(kat.getName())) {
                                        containsKategorie = true;
                                    }
                                }
                    %>


                    <% if (containsKategorie) {%>
                    <p> <input type="checkbox" checked name="SELKategorien" value="<%=kat.getName()%>"/>  <%=kat.getName()%></p>
                        <% } else {%>
                    <p> <input type="checkbox" name="SELKategorien" value="<%= kat.getName()%>"/>  <%=kat.getName()%></p> <%
                            }
                        }
                    %>      
                    <p><input class="btn btn-lg btn-primary" type="submit" value="Speichern" style="margin-left: 2%;"/></p>
                </form>
                <%
                } else {
                %> <p> Keine Kategorien vorhanden! <p><%
                   }
                %>
            </div>
            <!-- Profil löschen -->
            <div class="jumbotron">      
                <a class="btn btn-lg btn-danger" href="/DailyChallenge-war/frontservlet?step=profil_loeschen">Profil endgültig löschen</a>
            </div>
        </div>
    </body>
</html>
