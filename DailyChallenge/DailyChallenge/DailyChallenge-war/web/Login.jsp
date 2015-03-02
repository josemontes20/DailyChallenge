<%-- 
    Document   : Login
    Created on : 01.03.2015, 21:52:55
    Author     : José Montes
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>LOGIN Daily Challenge</title>
    </head>
    <body>
        <h1>Melde dich an!</h1>
        <form form method="post" action="/DailyChallenge-war/mainservlet?step=anmelden">
            <p> Username: <input type="text" name="username" /></p>
            <p> Password: <input type="password" name="userpassword" /></p>
            <p><input type="submit" value="Anmelden" /></p>
        </form>
    </body>
</html>
