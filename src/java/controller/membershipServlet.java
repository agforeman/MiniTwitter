/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import business.User;
import com.mysql.cj.util.StringUtils;
import dataaccess.UserDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.util.Random;





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
            // Flags
            boolean success = true;
            boolean isAvailable = true;
            boolean inputValid = true;
            
            // get parameters from the request
            String fullName = request.getParameter("fullname");
            String userName = request.getParameter("username");
            String email = request.getParameter("email");
            String birthDate = request.getParameter("birthdate");
            String questionNo = request.getParameter("security_questions");
            String answer = request.getParameter("security_answer");
            String password = request.getParameter("password");
            String confirm = request.getParameter("confirm_password");
            
            // store data in User object
            User user = new User();
            user.setfullname(fullName);
            user.setusername(userName);
            user.setemail(email);        
            user.setbirthdate(birthDate);        
            user.setquestionno(questionNo);
            user.setanswer(answer);
            user.setpassword(password);
            
            // store User object in request
            request.setAttribute("user", user); // TODO: Do we need to add the user to both the request and session below.
                
            
            // Check to make sure that this email is available
            isAvailable = check_email_available(user.getemail());
            if(!isAvailable) {
                request.setAttribute("signupError", "This email is not available."
                        + " Please try a different one.");
            }
            
            // If the email is available. Otherwise skip this test because we are
            // already going to return an error, and this test is more complex than
            // the previous (waste of time).
            if (isAvailable) {
                // Check to make sure all the data sent by the user is valid
                StringBuilder errors = new StringBuilder();
                errors.append("One or more input fields were invalid when trying to sign you up!");
                inputValid = validate_user_input(user, confirm, errors);
                if (!inputValid) {
                    request.setAttribute("signupError", errors);
                }
            }
            
            if(!isAvailable || !inputValid) {
                success = false;
            } 
            else {
                // Remove the signup error from the request since we have successfully
                // validated that the user should be added to the database.
                request.removeAttribute("signupError");
                try {
                    // Try to store the user into the database
                    UserDB.insert(user);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(membershipServlet.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(membershipServlet.class.getName()).log(Level.SEVERE, null, ex);
                }

                // Get the session from the request and store the user in it.
                HttpSession session = request.getSession();
                session.setAttribute("user", user); // TODO: Should adding the user to the request be removed?
            }
            
            // forward request to JSP
            url = success ? "/home.jsp" : "/signup.jsp";
        }
        else if(action.equals("login"))
        {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String remember = request.getParameter("remember");
            
            HttpSession session = request.getSession();
            User user = UserDB.search(email);  //search for user by email in database.
            
            
            //no user found in database
            if(user == null)
            {
                request.setAttribute("loginError", "No user found. If this is"
                        + " your first time, please use the Signup link");
            }
            //if user exists in database, check email and password for user
            else if(user.getemail().equalsIgnoreCase(email) && user.getpassword().equals(password))
            {
                session.setAttribute("user", user); //once creds are confirmed, set the user session attribute.
                request.removeAttribute("loginError"); //removes lingering login errors for a user.
                /*if(remember != null)
                {
                    Cookie c = new Cookie("emailCookie", email);
                    c.setMaxAge(60*60*24*365*2);
                    c.setPath("/");
                    response.addCookie(c);
                }*/
            }
            else
            {
                request.setAttribute("loginError","Login failed! Check password");
                url = "/login.jsp";
            }
        }
        //If user signs out of account, invalidate session and send to login page
        else if(action.equals("logout")) {
            HttpSession session = request.getSession();
            session.invalidate();
            
            url = "/login.jsp";
        }
        else if(action.equals("forgot")) {
            String email = request.getParameter("email");
            String questionNo = request.getParameter("security_questions");
            String answer = request.getParameter("security_answer");
            
            //HttpSession session = request.getSession();
            User user = UserDB.search(email);  //search for user by email in database.
            
            //no user found in database
            if(user == null)
            {
                request.setAttribute("forgotMessage", "No user found. If this is"
                        + " your first time, please use the Signup link");
                url="/forgotpassword.jsp";
            }
            else if(user.getemail().equalsIgnoreCase(email) && user.getquestionno().equals(questionNo)
                    && user.getanswer().equalsIgnoreCase(answer))
            {
                //session.setAttribute("user", user); //once creds are confirmed, set the user session attribute.
               String newPassword = generatePassword(); //generate new password
               user.setpassword(newPassword);

                // update user in the database
                UserDB.update(user);
                request.setAttribute("forgotMessage", "Email has been sent!"); //removes lingering login errors for a user.
                url="/forgotpassword.jsp";
            }
            else
            {    
                request.setAttribute("forgotMessage", "Incorrect information. Please try again");
                url = "/forgotpassword.jsp";
            }
                
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

    /*****************************************************************
     *                     validate_user_input()                     *
     *****************************************************************
     * Helper function for the signup action in DoPost. Returns true *
     * if the input received from the user is valid. Tests all fields*
     * to ensure they are not empty as well as testing that the two  *
     * passwords match.                                              *
     *                                                               *
     * @param User user User object                                  *
     * @param String confirm Confirm password info                   *
     * @param StringBuilder errors Error message to be built         *
     * @return boolean                                               *
     *****************************************************************/
    private boolean validate_user_input(User user, String confirm, StringBuilder errors){
        boolean valid = true;
        ArrayList<String> userInput = new ArrayList();
        userInput.add(user.getfullname());
        userInput.add(user.getusername());
        userInput.add(user.getemail());
        userInput.add(user.getbirthdate());
        userInput.add(user.getquestionno());
        userInput.add(user.getanswer());
        userInput.add(user.getpassword());
        userInput.add(confirm);
        
        valid = check_for_empty(userInput, errors);
        valid = check_password_match(userInput, errors) && valid;
        
        return valid;
    }
    
    /*****************************************************************
     *                    check_email_available()                    *
     *****************************************************************
     * Helper function for the signup action in DoPost. Returns true *
     * if the specified email is not in the database (thus available)*
     *                                                               *
     * @param String email Users email address                       *
     * @return boolean                                               *
     *****************************************************************/
    private boolean check_email_available(String email) {
        // Search the database for this email address
        User test = UserDB.search(email);
        // If the email doesn't exist user will be null so return true
        return test == null;
    }
    
    /*****************************************************************
     *                      check_for_empty()                        *
     *****************************************************************
     * Helper function for the validate_user_input. Returns true if  *
     * none of the user's input was blank.                           *
     *                                                               *
     * @param ArrayList<String> input User input                     *
     * @param StringBuilder errors Error message to be built         *
     * @return boolean                                               *
     *****************************************************************/
    private boolean check_for_empty(ArrayList<String> input, StringBuilder errors) {
        boolean valid = true;
        ArrayList<String> errorList = new ArrayList();
        if (StringUtils.isNullOrEmpty(input.get(0))) {
            errorList.add("Name");
            valid = false;
        }
        if (StringUtils.isNullOrEmpty(input.get(1))) {
            errorList.add("Username");
            valid = false;
        }
        if (StringUtils.isNullOrEmpty(input.get(2))) {
            errorList.add("Email");
            valid = false;
        }
        if (StringUtils.isNullOrEmpty(input.get(3))) {
            errorList.add("Birthday");
            valid = false;
        }
        if (StringUtils.isNullOrEmpty(input.get(4))) {
            errorList.add("Security Question");
            valid = false;
        }
        if (StringUtils.isNullOrEmpty(input.get(5))) {
            errorList.add("Security Answer");
            valid = false;
        }
        if (StringUtils.isNullOrEmpty(input.get(6))) {
            errorList.add("Password");
            valid = false;
        }
        if (StringUtils.isNullOrEmpty(input.get(7))) {
            errorList.add("Confirm Password");
            valid = false;
        }
        if (!valid) {
            errors.append("The following fields were empty when they "
                    + "reached the server: ");
            int i;
            for(i = 0; i < errorList.size() - 1; i++) {
                errors.append(errorList.get(i));
                errors.append(", ");
            }
            errors.append(errorList.get(i));
            errors.append("!");
        }
        
        return valid;
    }
    
    /*****************************************************************
     *                    check_password_match()                     *
     *****************************************************************
     * Helper function for the validate_user_input. Returns true if  *
     * none of the passwords match.                                  *
     *                                                               *
     * @param ArrayList<String> input User input                     *
     * @param StringBuilder errors Error message to be built         *
     * @return boolean                                               *
     *****************************************************************/
    private boolean check_password_match(ArrayList<String> input, StringBuilder errors) {
        boolean valid = true;
        if(!input.get(6).equals(input.get(7))) {
            errors.append("The passwords did not match when they made "
                    + "it to the server.");
            valid = false;
        }
        return valid;
    }
    
    /*****************************************************************
     *                    generatePassword()                     *
     *****************************************************************
     * Helper function for forgot password action. Creates a randomly*
     * generated password with 8 characters and returns it.          *
     *                                                               *
     * @return string                                                *
     *****************************************************************/
    protected String generatePassword() {
        String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder temp = new StringBuilder();
        Random rnd = new Random();
        while (temp.length() < 8) { // length of the random string.
            int index = (int) (rnd.nextFloat() * CHARS.length());
            temp.append(CHARS.charAt(index));
        }
        String newPass = temp.toString();
        return newPass;

    }
}
