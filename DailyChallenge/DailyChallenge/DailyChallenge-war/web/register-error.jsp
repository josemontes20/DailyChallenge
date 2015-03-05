<%-- 
    Document   : register-error
    Created on : 01.03.2015, 22:05:23
    Author     : José Montes
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registrierung fehlgeschlagen!</title>
    </head>
    <body>
        <h1>NOT OK!</h1>
        <%     
             String errorAusgabe = (String) request.getAttribute("error");  
        %>
    </body>
    
    <button type="button"><a href="/DailyChallenge-war/register.jsp"> <%= errorAusgabe%> Probier es nochmal :-) </a></button>
</html>
