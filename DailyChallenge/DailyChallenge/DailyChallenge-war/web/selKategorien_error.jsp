<%-- 
    Document   : selKategorien_error.jsp
    Created on : 14.03.2015, 15:57:50
    Author     : José Montes
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Daily Challenge</title>
    </head>
    <body>
        <h1>Es tut uns leid, <%= (String)request.getSession().getAttribute("anwendername")%>!</h1>
        <p>Etwas lief falsch bei der Auswahl deiner Kategorien!</p>
        <a href="/DailyChallenge-war/profil.jsp" style="font-size: 18px; font-weight: bold; color: #3e84c0">Dein Profil!</a>
    </body>
</html>
