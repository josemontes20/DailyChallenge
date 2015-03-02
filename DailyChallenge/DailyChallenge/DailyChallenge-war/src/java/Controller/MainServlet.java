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
            if (request.getParameter("username") != null &&
                request.getParameter("email") != null &&
                request.getParameter("userpassword").equals(request.getParameter("userpassword2")))
            {    
                int iniscore = 0;            
                Anwender user = bean.createUser(request.getParameter("username"),
                                            request.getParameter("userpassword"), 
                                            request.getParameter("email"),
                                            iniscore);             
                request.getRequestDispatcher("/Hauptseite.jsp").forward(request, response);
            }
            else
            {
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
