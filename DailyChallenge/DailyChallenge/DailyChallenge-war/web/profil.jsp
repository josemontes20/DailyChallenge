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
            String logon = (String) request.getAttribute("logon");
            
            if(logon.equals("false")){
                
                //response.sendRedirect("/DailyChallenge-war/login.jsp");
            }
        %>
        
        <h1>Profileinstellungen!</h1>
        
        
        <form class="form-horizontal" method="post" action="/DailyChallenge-war/mainservlet?step=abmelden">
            <p><input type="submit" value="Abmelden"/></p>
            <p><%= logon%></p>
        </form>
        
    </body>
</html>
