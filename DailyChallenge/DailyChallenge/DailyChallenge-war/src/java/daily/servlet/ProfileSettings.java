package daily.servlet;

import daily.beans.AnwenderBean;
import daily.beans.ChallengeBean;
import daily.beans.KategorieBean;
import daily.model.Anwender;
import daily.model.Challenge;
import daily.model.Kategorie;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProfileSettings {

    AnwenderBean anwBean;
    KategorieBean katBean;
    ChallengeBean chaBean;
 
    public ProfileSettings(AnwenderBean anwBean, KategorieBean katBean, ChallengeBean chaBean){
        this.anwBean = anwBean;
        this.katBean = katBean;
        this.chaBean = chaBean;
    }    

    // Wechselt zur Profil.jsp Seite
    public void redirectToProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("/DailyChallenge-war/profil.jsp");
    }

    // Löscht den Anwender aus der Anwender-Tabelle
    public void deleteProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
        int number_of_deleted_User = anwBean.deleteUser((Anwender)request.getSession().getAttribute("anwender"));

        // Muss eins sein, sonst Fehler!
        // Wird aber automatisch dadurch abgefangen, da der Anwendername eindeutig ist.
        if(number_of_deleted_User == 1){
            request.getSession().setAttribute("abmelden", "null");
            response.sendRedirect("/DailyChallenge-war/userdel.jsp");
        }else{
            response.sendRedirect("/DailyChallenge-war/userdel_error.jsp");
        }
            
    }

    // Aktualisiert die Anwender_Kategorie-Tabelle anhand der ausgewählten Kategorien
    public void updateUserKategories(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // aktuellen Anwender und seine gewählten Kategorien auslesen
        Anwender anwender = (Anwender) request.getSession().getAttribute("anwender");
        String selectKategorien[] = request.getParameterValues("SELKategorien");

        // Umwandeln des StringArrays in eine Kategorie-ArrayList
        List<Kategorie> neueKategorien = new ArrayList<>();
        if ((selectKategorien != null && selectKategorien.length > 0) || selectKategorien == null){
            if (selectKategorien != null){
                for (String selectKat : selectKategorien) {
                    neueKategorien.add(katBean.getKategorieByName(selectKat));
                }
            }
            
            // In der Anwender_Kategorie-Tabelle aktualisieren
            anwBean.updateKategories(anwender, neueKategorien);

            // Anschließend auch die Challenges aktualisieren (da neue Kategorien gewählt wurden)
            List<Challenge> challenges = chaBean.getChallengesForToday(katBean.getAllKategorienByUser(anwender.getId()));

            request.getSession().setAttribute("challenges", challenges);
            request.getSession().setAttribute("anwender", anwender);

            response.sendRedirect("/DailyChallenge-war/mainpage.jsp");

        } else {
            response.sendRedirect("/DailyChallenge-war/selKategorien_error.jsp");
        }
    
    }
    
    
    
    
    
}
