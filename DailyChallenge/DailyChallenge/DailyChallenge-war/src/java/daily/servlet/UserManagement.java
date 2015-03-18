package daily.servlet;

import daily.beans.AnwenderBean;
import daily.beans.ChallengeBean;
import daily.beans.KategorieBean;
import daily.model.Anwender;
import daily.model.Challenge;
import daily.model.Kategorie;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserManagement {
    
    AnwenderBean anwBean;
    KategorieBean katBean;
    ChallengeBean chaBean;
 
    public UserManagement(AnwenderBean anwBean, KategorieBean katBean, ChallengeBean chaBean){
        this.anwBean = anwBean;
        this.katBean = katBean;
        this.chaBean = chaBean;
    }

    // Meldet einen Anwender mit seinen Daten an.
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {    
        
        // Überprüfen der Anmeldeinformationen
        Anwender anwender = anwBean.loginUser(request.getParameter("username"),
                                  request.getParameter("userpassword"));

        if (anwender == null){
           response.sendRedirect("/DailyChallenge-war/login_error.html");
        }else{ 
           request.getSession().setMaxInactiveInterval(30*60);
           request.getSession().setAttribute("anwender", anwender);

           //Hier werden die Challenges für den User geladen
           List<Challenge> challenges = chaBean.getChallengesForToday(katBean.getAllKategorienByUser(anwender.getId()));
           request.getSession().setAttribute("challenges", challenges);

           //Hier werden die Kategorien der Session übergeben
           List<Kategorie> kategorien = katBean.getAllKategorien();
           request.getSession().setAttribute("kategorien", kategorien);

           //Attribut setzen, ob Anwender abgemeldet ist
           request.getSession().setAttribute("abmelden", "false");

           response.sendRedirect("/DailyChallenge-war/mainpage.jsp");
        }
        
    }
    
    // Registriert einen Anwender mit seinen eingegebenen Daten
    public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Eingabedaten überprüfen, Rückgabe-String bestimmt weiteren Verlauf
        String registerResult = anwBean.validateRegistration(
                                    request.getParameter("username"),
                                    request.getParameter("email"),
                                    request.getParameter("userpassword"),
                                    request.getParameter("userpassword2"));
        
        String errorMessage = "";
            
        switch (registerResult) {
            case "OK":
                // Derzeit noch nicht in Verwendung
                int iniscore = 0;

                // Anwender erstellen
                Anwender anwender = anwBean.createUser(
                            request.getParameter("username"),
                            request.getParameter("userpassword"),
                            request.getParameter("email"),
                            iniscore);
                
                // 30 x 60 Sekunden = 30 Minuten
                request.getSession().setMaxInactiveInterval(30*60);

                // Heutigen Challenges laden
                List<Challenge> challenges = chaBean.getChallengesForToday
                                            (katBean.getAllKategorienByUser(anwender.getId()));

                // Alle Kategorien laden
                List<Kategorie> kategorien = katBean.getAllKategorien();

                // Der Session wird der Anwender, seine Challenges und alle Kategorien übergeben
                request.getSession().setAttribute("anwender", anwender);
                request.getSession().setAttribute("challenges", challenges);
                request.getSession().setAttribute("kategorien", kategorien);
                
                // Attribut setzen, ob Anwender abgemeldet ist (nein)
                request.getSession().setAttribute("abmelden", "false");

                response.sendRedirect("/DailyChallenge-war/mainpage.jsp");
                return;
                
            case "USERNAME NOT NULL":
                errorMessage = "Du hast keinen Benutzernamen eingegeben!";
                break;
                
            case "USERNAME REPEAT":
                errorMessage = "Dein Benutzername wird leider schon verwendet!";
                break;
                
            case "EMAIL NOT OK":
                errorMessage = "Gib bitte eine gültige E-Mail Adresse ein!";
                break;
                
            case "EMAIL REPEAT":
                errorMessage = "Es gibt bereits einen Benutzer mit dieser E-Mail Adresse!";
                break;
                
            case "PASSWORD NOT OK":
                errorMessage = "Es gab einen Fehler mit deinem Passwort!";
                break;
                
            default:
                errorMessage = "Deine Registrierung ist leider fehlgeschlagen!";
                break;
        }

        // Führt zur Fehlerseite mit entsprechender Fehlermeldung
        request.getSession().setAttribute("error", errorMessage);
        response.sendRedirect("/DailyChallenge-war/register_error.jsp");
        return;
        
    }

    // Meldet den Anwender ab
    public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().setAttribute("abmelden", "true");
        response.sendRedirect("/DailyChallenge-war/login.jsp");
            
        // Aufgrund eines GlassFish-Bugs, wird eine NullPointerException geworfen.
        /*try{
            request.getSession(false).invalidate();
            response.sendRedirect("/DailyChallenge-war/login.jsp");
        }catch(Exception ex){
            System.out.println("GlassFishServerBug:" + ex);
        }*/
    }
    
}
