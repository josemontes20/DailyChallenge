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
                request.setAttribute("anwendername", user.getUserName());
                request.getRequestDispatcher("/HauptSeite.jsp").forward(request, response); 
            }else{
                request.getRequestDispatcher("/Login-Error.html");
            }
        
            
        }else if(tempStep.equalsIgnoreCase("registrieren")){
            String uebergabTEMP = "Leer";
            
            String registerResult = bean.registerValide(
                                    request.getParameter("username"),
                                    request.getParameter("email"),
                                    request.getParameter("userpassword"),
                                    request.getParameter("userpassword2"));
            
            if(registerResult.equals("OK")){
                int iniscore = 0;            
                Anwender user = bean.createUser(
                                            request.getParameter("username"),
                                            request.getParameter("userpassword"), 
                                            request.getParameter("email"),
                                            iniscore);
                
                request.setAttribute("anwendername", request.getParameter("username"));
                request.getRequestDispatcher("/HauptSeite.jsp").forward(request, response);
            }
            else if (registerResult.equals("USERNAME NOT NULL")){
                uebergabTEMP = "USER";
                request.setAttribute("uebergabTEMP", uebergabTEMP);
                
                String errorUser = "Username darf nicht NULL sein!";
                request.setAttribute("errorUser", errorUser);
                request.getRequestDispatcher("/register-error.jsp").forward(request, response);
                errorUser = null;
            }
            else if (registerResult.equals("USERNAME REPEAT")){
                uebergabTEMP = "USER";
                request.setAttribute("uebergabTEMP", uebergabTEMP);
                
                String errorUser = "Username ist bereits vergeben!";
                request.setAttribute("errorUser", errorUser);
                request.getRequestDispatcher("/register-error.jsp").forward(request, response);
                errorUser = null;
            }
            else if (registerResult.equals("EMAIL NOT OK")){
                uebergabTEMP = "EMAIL";
                request.setAttribute("uebergabTEMP", uebergabTEMP);
                
                String errorEmail = "Email ist nicht valide!";
                request.setAttribute("errorEmail", errorEmail);
                request.getRequestDispatcher("/register-error.jsp").forward(request, response);
                errorEmail = null;   
            }
            else if (registerResult.equals("EMAIL REPEAT")){
                uebergabTEMP = "EMAIL";
                request.setAttribute("uebergabTEMP", uebergabTEMP);
                
                String errorEmail = "Email ist bereits vergeben!";
                request.setAttribute("errorEmail", errorEmail);
                request.getRequestDispatcher("/register-error.jsp").forward(request, response);
                errorEmail = null; 
            }
            else if (registerResult.equals("PASSWORD NOT OK")){
                uebergabTEMP = "PASSWORD";
                request.setAttribute("uebergabTEMP", uebergabTEMP);
                
                String errorPassword = "Passwort darf nicht NULL sein und muss richtig wiederholt werden!";
                request.setAttribute("errorPassword", errorPassword);
                request.getRequestDispatcher("/register-error.jsp").forward(request, response);
            }
            else{
                uebergabTEMP = "REGISTRIERUNG";
                request.setAttribute("uebergabTEMP", uebergabTEMP);
                
                String errorRegister = "Registrierung ist fehlgeschlagen!";
                request.setAttribute("errorRegister", errorRegister);
                request.getRequestDispatcher("/register-error.jsp").forward(request, response);
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
