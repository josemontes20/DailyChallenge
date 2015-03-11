<%-- 
    Document   : profil
    Created on : 01.03.2015, 22:01:57
    Author     : José Montes
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Dein Profil</title>
    </head>
    <body>
        
        <% 
            //Browser-Cache löschen
            response.addHeader("Cache-Control", "no-cache");
            response.addHeader("Cache-Control", "no-store");
            response.addDateHeader("Expires", 0);
            response.addHeader("Pragma", "no-cache");
            
            String user = (String) session.getAttribute("anwendername");
            
            if(user.equals("null")){
                response.sendRedirect("/DailyChallenge-war/login.jsp");
            }
            
        %>
        
        <h1>Profileinstellungen!</h1>
        
        
        <form class="form-horizontal" method="post" action="/DailyChallenge-war/mainservlet?step=abmelden">
            <p><input type="submit" value="Abmelden"/></p>
        </form>
        
        <form class="form-horizontal" method="post" action="/DailyChallenge-war/mainpage.jsp">
            <p><input type="submit" value="haupt"/></p>
        </form>
        
        <form class="form-horizontal" method="post" action="/DailyChallenge-war/mainservlet?step=loeschen_profil">
            <p><input type="submit" value="Löschen"/></p>
        </form>
    </body>
</html>
