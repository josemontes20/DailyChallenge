/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Anwender;

/**
 * @author José Montes
 */
@WebServlet(urlPatterns = {"/mainservlet"})
public class MainServlet extends HttpServlet {
    
    @EJB
    AnwenderBean bean;
        
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            
        String tempStep = request.getParameter("step");
        Anwender user;
        HttpSession session = null;
        
        if(tempStep.equalsIgnoreCase("anmelden")){
             user = bean.loginUser(request.getParameter("username"),
                                       request.getParameter("userpassword"));
            
            if (user != null){
                session = request.getSession();
                session.setMaxInactiveInterval(30*60);
                session.setAttribute("anwendername", user.getUsername());
                
                response.sendRedirect("/DailyChallenge-war/mainpage.jsp");
            }else{
                response.sendRedirect("/DailyChallenge-war/login_error.html");
            }
        
            
        }else if(tempStep.equalsIgnoreCase("registrieren")){
            
            String registerResult = bean.registerValide(
                                    request.getParameter("username"),
                                    request.getParameter("email"),
                                    request.getParameter("userpassword"),
                                    request.getParameter("userpassword2"));
            
            switch (registerResult) {
                case "OK":
                    {
                        int iniscore = 0;
                        user = bean.createUser(
                                    request.getParameter("username"),
                                    request.getParameter("userpassword"),
                                    request.getParameter("email"),
                                    iniscore);
                    
                        session = request.getSession();
                        session.setMaxInactiveInterval(30*60);
                        session.setAttribute("anwendername", user.getUsername());
            
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
            request.getRequestDispatcher("/profil.jsp").forward(request, response); 
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
