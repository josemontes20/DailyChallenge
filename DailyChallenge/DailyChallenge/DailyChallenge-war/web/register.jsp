<%-- 
    Document   : register
    Created on : 25.02.2015, 11:30:29
    Author     : José Montes
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registrierung</title>
    </head>
    <body>
        <h1>Melde dich hier an!</h1>
        <form method="post" action="/DailyChallenge-war/mainservlet?step=registrieren">
            <p> Username: <input type="text" name="username" /></p>
            <p> EMail: <input type="text" name="email" /></p>
            <p> Password: <input type="password" name="userpassword" /></p>
            <p> Repeat Password: <input type="password" name="userpassword2" /></p>
            <p><input type="submit" value="Registrieren" /></p>
        </form>
    </body>
</html>
