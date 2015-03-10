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
    UserControl bean;
        
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            
        String tempStep = request.getParameter("step");
      
        if(tempStep.equalsIgnoreCase("anmelden")){
            Anwender user = bean.loginUser(request.getParameter("username"),
                                       request.getParameter("userpassword"));
            
            if (user != null){
                HttpSession session = request.getSession();
                session.setMaxInactiveInterval(30*60);
                session.setAttribute("anwendername", user.getUserName());
                
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
                        Anwender user = bean.createUser(
                                    request.getParameter("username"),
                                    request.getParameter("userpassword"),
                                    request.getParameter("email"),
                                    iniscore);
                    
                        HttpSession session = request.getSession();
                        session.setMaxInactiveInterval(30*60);
                        session.setAttribute("anwendername", user.getUserName());

                        response.sendRedirect("/DailyChallenge-war/mainpage.jsp");
                        break;
                    }
                case "USERNAME NOT NULL":
                    {
                        String errorUser = "Du hast keinen Benutzernamen eingegeben!";
                        request.setAttribute("error", errorUser);
                        request.getRequestDispatcher("/register_error.jsp").forward(request, response);
                        break;
                    }
                case "USERNAME REPEAT":
                    {
                        String errorUser = "Dein Benutzername wird leider schon verwendet!";
                        request.setAttribute("error", errorUser);
                        request.getRequestDispatcher("/register_error.jsp").forward(request, response);
                        break;
                    }
                case "EMAIL NOT OK":
                    {
                        String errorEmail = "Gib bitte eine gültige E-Mail Adresse ein!";
                        request.setAttribute("error", errorEmail);
                        request.getRequestDispatcher("/register_error.jsp").forward(request, response);
                        break;
                    }
                case "EMAIL REPEAT":
                    {
                        String errorEmail = "Es gibt bereits einen Benutzer mit dieser E-Mail Adresse!";
                        request.setAttribute("error", errorEmail);
                        request.getRequestDispatcher("/register_error.jsp").forward(request, response);
                        break;
                    }
                case "PASSWORD NOT OK":
                    {
                        String errorPassword = "Es gab einen Fehler mit deinem Passwort!";
                        request.setAttribute("error", errorPassword);
                        request.getRequestDispatcher("/register_error.jsp").forward(request, response);
                        break;
                    }
                default:
                    {
                        String errorRegister = "Deine Registrierung ist leider fehlgeschlagen!";
                        request.setAttribute("error", errorRegister);
                        request.getRequestDispatcher("/register_error.jsp").forward(request, response);
                        break;
                    }
            }
        }else if(tempStep.equalsIgnoreCase("abmelden")){
            request.removeAttribute("logon");
            request.setAttribute("logon", "false");
           
            //Aufgrund eines GlassFish-Bugs, wird eine NullPointerException geworfen.
            /*try{
                request.getSession().invalidate();
            }catch(Exception ex){
                System.out.println("GlassFishServerBug:" + ex);
            }*/
            
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Cache-Control", "no-store");
            response.setDateHeader("Expires", 0);
            response.setHeader("Pragma", "no-cache");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            //response.sendRedirect("/DailyChallenge-war/login.jsp");           
                          
        }else if (tempStep.equalsIgnoreCase("profil")){
            
            request.setAttribute("logon", "true");
            request.getRequestDispatcher("/profil.jsp").forward(request, response);
            //response.sendRedirect("/DailyChallenge-war/profil.jsp");
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
