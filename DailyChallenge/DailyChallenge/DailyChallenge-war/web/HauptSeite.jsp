<%-- 
    Document   : HauptSeite
    Created on : 01.03.2015, 20:28:34
    Author     : José Montes
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Daily Challenge Startseite</title>
    </head>
    <body>
        <h1>Herzlich Willkommen zu Daily Challenge!</h1>
        
        <% 
            String anwendername = (String) request.getAttribute("anwendername");
        %>
        
        <button type="button"><a href="/DailyChallenge-war/profil.jsp"> Dein Profil, <%= anwendername%>! </a></button>
        
    </body>
</html>
