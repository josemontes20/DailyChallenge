package daily.servlet;

import daily.beans.ChallengeBean;
import daily.beans.KategorieBean;
import daily.beans.AnwenderBean;
import daily.model.Anwender;
import daily.model.Kategorie;
import daily.model.Challenge;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/frontservlet"})
public class FrontServlet extends HttpServlet {
    
    @EJB
    AnwenderBean anwBean;
    
    @EJB
    KategorieBean katBean;
        
    @EJB
    ChallengeBean chaBean;
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String tempStep = request.getParameter("step");
        
        // Erstellen der Klasse, die sich um Registrierung, Anmeldung und Abmeldung kümmert.
        UserManagement usermanagement = new UserManagement(anwBean, katBean, chaBean);
        
        // Erstellen der Klasse, die sich um die näheren Profileinstellungen kümmert.
        ProfileSettings profile = new ProfileSettings(anwBean, katBean, chaBean);
        
        
        if(tempStep.equalsIgnoreCase("anmelden")){
            usermanagement.login(request, response);

            
        }else if(tempStep.equalsIgnoreCase("registrieren")){
            usermanagement.register(request, response);
            
            
        }else if(tempStep.equalsIgnoreCase("abmelden")){
            usermanagement.logout(request, response);
            
            
        }else if (tempStep.equalsIgnoreCase("profil")){
            profile.redirectToProfile(request, response);
            
            
        }else if(tempStep.equalsIgnoreCase("profil_loeschen")){
            profile.deleteProfile(request, response);

            
        }else if(tempStep.equalsIgnoreCase("kategorien_auswaehlen")){
            profile.updateUserKategories(request, response);
            
            
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }


}
