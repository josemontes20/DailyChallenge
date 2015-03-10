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
            //Browser-Cache löschen
            response.addHeader("Cache-Control", "no-cache");
            response.addHeader("Cache-Control", "no-store");
            response.addDateHeader("Expires", 0);
            response.addHeader("Pragma", "no-cache");
            
            String user = (String)session.getAttribute("anwendername");
            
            if(user.equals("null")){
                 response.sendRedirect("/DailyChallenge-war/login.jsp");
            }
            
        %>
            
        <h1>Herzlich Willkommen zu Daily Challenge, <%= user %>!</h1>
        <h2>Deine aktuelle Challenges:</h2>
        <ol>
            <li>...</li> 
            <li>...</li> 
            <li>...</li> 
        </ol>
        <form class="form-horizontal" method="post" action="/DailyChallenge-war/mainservlet?step=profil">
            <p><input type="submit" value="Profil bearbeiten, <%= user%>!"/></p>
        </form>
        
        <form class="form-horizontal" method="post" action="/DailyChallenge-war/mainservlet?step=abmelden">
            <p><input type="submit" value="Abmelden"/></p>
            <p><%= user%></p>
        </form>
        
    </body>
</html>
