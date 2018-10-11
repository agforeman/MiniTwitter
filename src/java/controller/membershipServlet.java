/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import business.user;
import dataaccess.UserDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;



public class membershipServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
   

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        String url = "/login.jsp";
        if(action.equals("signup"))
        {
            // get parameters from the request
            String fullName = request.getParameter("fullname");
            String userName = request.getParameter("username");
            String email = request.getParameter("email");
            String birthDate = request.getParameter("birthdate");
            String questionNo = request.getParameter("security_questions");
            String answer = request.getParameter("security_answer");
            String password = request.getParameter("password");

            // store data in User object
            user user = new user();
            user.setfullname(fullName);
            user.setusername(userName);
            user.setemail(email);        
            user.setbirthdate(birthDate);        
            user.setquestionno(questionNo);
            user.setanswer(answer);
            user.setpassword(password);
            // store User object in request
            request.setAttribute("user", user);
            
            try {
                UserDB.insert(user);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(membershipServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            // forward request to JSP
            url = "/home.jsp";
        }
        
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        doPost(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
