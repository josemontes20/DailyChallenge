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
        <%  
            String anwendername = "Leer";
            if(session.getAttribute("anwendername") == null){
                 response.sendRedirect("/DailyChallenge-war/login_error.html");
            }
            else anwendername = (String) session.getAttribute("anwendername");
        %>
            
        <h1>Herzlich Willkommen zu Daily Challenge, <%= anwendername %>!</h1>
        <h2>Deine aktuelle Challenges:</h2>
        <ol>
            <li>...</li> 
            <li>...</li> 
            <li>...</li> 
        </ol>
        <form class="form-horizontal" method="post" action="/DailyChallenge-war/mainservlet?step=profil">
            <p><input type="submit" value="Profil bearbeiten, <%= anwendername%>!"/></p>
        </form>
        
    </body>
</html>
