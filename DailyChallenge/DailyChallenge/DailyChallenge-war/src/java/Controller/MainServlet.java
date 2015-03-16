/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import model.Anwender;
import model.Kategorie;
import model.Challenge;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author José Montes
 */
@WebServlet(urlPatterns = {"/mainservlet"})
public class MainServlet extends HttpServlet {
    
    @EJB
    AnwenderBean anwbean;
    
    @EJB
    KategorieBean katBean;
        
    @EJB
    ChallengeBean chaBean;
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String tempStep = request.getParameter("step");
        
        if(tempStep.equalsIgnoreCase("anmelden")){
             Anwender user = anwbean.loginUser(request.getParameter("username"),
                                       request.getParameter("userpassword"));
            
            if (user != null){
                request.getSession().setMaxInactiveInterval(30*60);
                request.getSession().setAttribute("anwendername", user.getUsername());
                
              //Hier werden die Challenges des Users übergeben
              List<Challenge> challenges = chaBean.getChallengesForToday(katBean.getAllKategorienByUser(user.getId()));
              request.getSession().setAttribute("challenges", challenges);
                
            // List<Challenge> challenges = chaBean.getAllChallengesByUser(user);
            // request.getSession().setAttribute("challenges", challenges);
                
                //Hier werden die Kategorien der Session übergeben
                List<Kategorie> kategorien = katBean.getAllKategorien();
                request.getSession().setAttribute("kategorien", kategorien);
                
                response.sendRedirect("/DailyChallenge-war/mainpage.jsp");
                
            }else{
                response.sendRedirect("/DailyChallenge-war/login_error.html");
            }
        
            
        }else if(tempStep.equalsIgnoreCase("registrieren")){
            
            String registerResult = anwbean.registerValide(
                                    request.getParameter("username"),
                                    request.getParameter("email"),
                                    request.getParameter("userpassword"),
                                    request.getParameter("userpassword2"));
            
            switch (registerResult) {
                case "OK":
                    {
                        int iniscore = 0;
                        Anwender user = anwbean.createUser(
                                    request.getParameter("username"),
                                    request.getParameter("userpassword"),
                                    request.getParameter("email"),
                                    iniscore);
                    
                        request.getSession().setMaxInactiveInterval(30*60);
                        request.getSession().setAttribute("anwendername", user.getUsername());
                        
                        List<Challenge> challenges = chaBean.getChallengesForToday
                                                    (katBean.getAllKategorienByUser(user.getId()));
                        
              
                        request.getSession().setAttribute("challenges", challenges);
                    
                        response.sendRedirect("/DailyChallenge-war/mainpage.jsp");
                        break;
                    }
                case "USERNAME NOT NULL":
                    {
                        String errorUser = "Du hast keinen Benutzernamen eingegeben!";
                        request.getSession().setAttribute("error", errorUser);
                        response.sendRedirect("/DailyChallenge-war/register_error.jsp");
                        break;
                    }
                case "USERNAME REPEAT":
                    {
                        String errorUser = "Dein Benutzername wird leider schon verwendet!";
                        request.getSession().setAttribute("error", errorUser);
                        response.sendRedirect("/DailyChallenge-war/register_error.jsp");
                        break;
                    }
                case "EMAIL NOT OK":
                    {
                        String errorEmail = "Gib bitte eine gültige E-Mail Adresse ein!";
                        request.getSession().setAttribute("error", errorEmail);
                        response.sendRedirect("/DailyChallenge-war/register_error.jsp");
                        break;
                    }
                case "EMAIL REPEAT":
                    {
                        String errorEmail = "Es gibt bereits einen Benutzer mit dieser E-Mail Adresse!";
                        request.getSession().setAttribute("error", errorEmail);
                        response.sendRedirect("/DailyChallenge-war/register_error.jsp");
                        break;
                    }
                case "PASSWORD NOT OK":
                    {
                        String errorPassword = "Es gab einen Fehler mit deinem Passwort!";
                        request.getSession().setAttribute("error", errorPassword);
                        response.sendRedirect("/DailyChallenge-war/register_error.jsp");
                        break;
                    }
                default:
                    {
                        String errorRegister = "Deine Registrierung ist leider fehlgeschlagen!";
                        request.getSession().setAttribute("error", errorRegister);
                        response.sendRedirect("/DailyChallenge-war/register_error.jsp");
                        break;
                    }
            }
        }else if(tempStep.equalsIgnoreCase("abmelden")){
            
            request.getSession().setAttribute("anwendername", "null");
            response.sendRedirect("/DailyChallenge-war/login.jsp");
            
            //Aufgrund eines GlassFish-Bugs, wird eine NullPointerException geworfen.
            /*try{
                request.getSession(false).invalidate();
                response.sendRedirect("/DailyChallenge-war/login.jsp");
            }catch(Exception ex){
                System.out.println("GlassFishServerBug:" + ex);
            }*/
                             
        }else if (tempStep.equalsIgnoreCase("profil")){
            
            response.sendRedirect("/DailyChallenge-war/profil.jsp");
            
            
        }else if(tempStep.equalsIgnoreCase("loeschen_profil")){
            int deleteStatus = anwbean.deleteUser((String)request.getSession().getAttribute("anwendername"));
            
            if(deleteStatus == 1){
                request.getSession().setAttribute("anwendername", "null");
                response.sendRedirect("/DailyChallenge-war/userdel.jsp");
            }else{
                response.sendRedirect("/DailyChallenge-war/userdel_error.jsp");
            }
            
        }else if(tempStep.equalsIgnoreCase("select_kategorien")){
            
            Anwender a = anwbean.findByName((String) request.getSession().getAttribute("anwendername"));
            String selectKategorien[] = request.getParameterValues("SELKategorien");

            List<Kategorie> neueKategorien = new ArrayList<>();
            if ((selectKategorien != null && selectKategorien.length > 0) || selectKategorien == null){
                if (selectKategorien != null){
                        for (String selectKat : selectKategorien) {
                            neueKategorien.add(katBean.getKategorieByName(selectKat));
                        }
                }
                anwbean.updateKategories(a, neueKategorien);
                
                //Hier werden die Challenges des Users übergeben
                List<Challenge> challenges = chaBean.getChallengesForToday(katBean.getAllKategorienByUser(a.getId()));
                request.getSession().setAttribute("challenges", challenges);
                response.sendRedirect("/DailyChallenge-war/mainpage.jsp");
            } else {
                response.sendRedirect("/DailyChallenge-war/selKategorien_error.jsp");
            }
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
