/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import business.User;
import Util.MailUtilYahoo;
import business.UserTweetInfo;
import com.mysql.cj.util.StringUtils;
import dataaccess.TweetDB;
import dataaccess.UserDB;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.util.Random;
import java.security.SecureRandom;
import java.util.Base64;
import javax.mail.*;
import javax.servlet.annotation.MultipartConfig;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;

@MultipartConfig(fileSizeThreshold=1024*1024*2,
                 maxFileSize=1024*1024*10,
                 maxRequestSize=1024*1024*20)
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
            javax.servlet.http.Part photo = request.getPart("photo");
            InputStream inputPhoto = null; //input stream of photo upload
            String salt = "";
            String hPass = ""; //salted and hashed password
            
            if(photo != null){
                inputPhoto = photo.getInputStream();
            }
            
            //Salt and Hash password
            salt = generatePassword(32);  //32 indicates bytes
            password += salt; //add salt to existing password
            confirm += salt;
            try {
               hPass = hashPassword(password);
               confirm = hashPassword(confirm);
            }catch (NoSuchAlgorithmException ex) {
                System.out.println(ex);
            }
            
            // store data in User object
            User user = new User();
            user.setfullname(fullName);
            user.setusername(userName);
            user.setemail(email);        
            user.setbirthdate(birthDate);        
            user.setquestionno(questionNo);
            user.setanswer(answer);
            user.setpassword(hPass);
            user.setsalt(salt);
            user.setphoto(inputPhoto);
            
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
                errors.append("One or more input fields were invalid when trying to sign you up! \n");
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
            String hPass = "";
            String salt = "";
            
            HttpSession session = request.getSession();
            User user = UserDB.search(email);  //search for user by email in database.

            //no user found in database
            if(user == null)
            {
                request.setAttribute("loginError", "No user found. If this is"
                        + " your first time, please use the Signup link");
            }
            else
            {
                hPass = user.getpassword(); //hashed password
                salt = user.getsalt(); //user salt
                
                //generate hash password with user inputted password and existing salt.
                try {
                    password = hashPassword(password + salt);
                 }catch (NoSuchAlgorithmException ex) {
                    System.out.println(ex);
                }
            
                //verify email and password of user
                if(user.getemail().equalsIgnoreCase(email) && user.getpassword().equals(password))
                {
                    session.setAttribute("user", user); //once creds are confirmed, set the user session attribute.
                    request.removeAttribute("loginError"); //removes lingering login errors for a user.
                    if(remember != null)
                    {
                        Cookie c = new Cookie("emailCookie", email);
                        c.setMaxAge(60*60*24*365*2);
                        c.setPath("/");
                        response.addCookie(c);
                    }
                    // UPDATE NOTIFICATIONS
                    ArrayList<String> new_followers;
                    ArrayList<UserTweetInfo> new_tweets;
                    
                    new_followers = UserDB.getNewFollowers(email);
                    new_tweets = TweetDB.getNewTweets(email);
                    
                    session.setAttribute("newFollowers", new_followers);
                    session.setAttribute("newTweets", new_tweets);
                    
                    // UPDATE last_login_time here?
                    UserDB.updateLoginTime(email);
                    
                    url = "/home.jsp";
                }
                else
                {
                    request.setAttribute("loginError","Login failed! Check password");
                    url = "/login.jsp";
                }
            }
        }
        //If user signs out of account, invalidate session and send to login page
        else if(action.equals("logout")) {
            HttpSession session = request.getSession();
            session.invalidate();
            
            url = "/login.jsp";
        }
        //Forgot password action
        else if(action.equals("forgot")) {
            String email = request.getParameter("email");
            String questionNo = request.getParameter("security_questions");
            String answer = request.getParameter("security_answer");
            User user = UserDB.search(email);  //search for user by email in database.
            
            //no user found in database
            if(user == null)
            {
                request.setAttribute("forgotMessage", "No user found. If this is"
                        + " your first time, please use the Signup link");
                url="/login.jsp";
            }
            //Check user email, security question and answer
            else if(user.getemail().equalsIgnoreCase(email) && user.getquestionno().equals(questionNo)
                    && user.getanswer().equalsIgnoreCase(answer))
            {
               String newPassword = generatePassword(4); //generate new password(4 bytes)
               String salt = generatePassword(32); //32 byte salt
               String hPass = "";
               //hash salted password
                try {
                    hPass = hashPassword(newPassword + salt);
                }catch (NoSuchAlgorithmException ex) {
                    System.out.println(ex);
                }
                user.setsalt(salt); //set new salt to User object
               user.setpassword(hPass); //set new hash password to User object

                // update user in the database
                UserDB.update(user);
                
                //send user an email with new password
                sendEmail(user.getemail(), newPassword, user.getfullname());

                request.setAttribute("forgotMessage", "Email has been sent!"); //let user know email has been sent
                url="/login.jsp";
            }
            else
            {    
                request.setAttribute("forgotMessage", "Incorrect information. Please try again");
                url = "/forgotpassword.jsp";
            }
                
        }
        else if (action.equals("update")) {
            // Flags
            boolean success = true;
            boolean inputValid = true;
            
            // get parameters from the request
            String fullName = request.getParameter("fullname");
            String birthDate = request.getParameter("birthdate");
            String questionNo = request.getParameter("security_questions");
            String answer = request.getParameter("security_answer");
            String password = request.getParameter("password");
            String confirm = request.getParameter("confirm_password");
            javax.servlet.http.Part photo = request.getPart("photo");
            InputStream inputPhoto = null; //input stream of photo upload
            
            if(photo != null){
                inputPhoto = photo.getInputStream();
            }

            //get user session and set user object from session
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            
            //update user fields
            user.setfullname(fullName);        
            user.setbirthdate(birthDate);        
            user.setquestionno(questionNo);
            user.setanswer(answer);
            user.setpassword(password);
            user.setphoto(inputPhoto);
            
            // Check to make sure all the data sent by the user is valid
            StringBuilder errors = new StringBuilder();
            errors.append("One or more input fields were invalid when trying to update!");
            inputValid = validate_user_input(user, confirm, errors);
            if (!inputValid) {
                request.setAttribute("signupError", errors);
                url = "/signup.jsp";
            }
            else {
                // salt and hash password
                // update user in the database
                // display successful update to user and update session with new info
                String salt = generatePassword(32); //32 bytes
                String hPass = "";
                try {
                    hPass = hashPassword(password + salt);
                }catch (NoSuchAlgorithmException ex) {
                    System.out.println(ex);
                }
                user.setsalt(salt);
                user.setpassword(hPass);
                UserDB.update(user);
                request.setAttribute("signupError", "Update Successful!");
                session.setAttribute("user", user);
                url = "/signup.jsp";
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
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        // Test if there is a user signed in.
        User user = (User) session.getAttribute("user");
        
        String url = "/login.jsp";
        
        // If no user go to login
        if (user == null){
            url = "/login.jsp";
            action = "no_user";
        } else if (action == null) {
            // If user but no action go home
            url = "/home.jsp";
        } else if(action.equals("get_users")) {            
            ArrayList<User> users;
            users = UserDB.selectUsers();
            session.setAttribute("users", users);
            
            url = "/home.jsp";
        }
        
        getServletContext()
            .getRequestDispatcher(url)
            .forward(request, response);
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

    // <editor-fold defaultstate="collapsed" desc="User methods. Click on the + sign on the left to edit the code.">
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
     *                    generatePassword()                         *
     *****************************************************************
     * Function that generates a new password or salt. Creates a     * 
     * randomly generated string of n bytes and returns it.          *
     *                                                               *
     * @param bytes number of bytes for new password                 *
     * @return string                                                *
     *****************************************************************/
    protected String generatePassword(int bytes) {
        
        Random r = new SecureRandom();
        byte[] tempBytes = new byte[bytes];
        r.nextBytes(tempBytes);
        return Base64.getEncoder().encodeToString(tempBytes);

    }
    
     /****************************************************************
     *                    hashPassword()                             *
     *****************************************************************
     * Helper function for hash password action. This code uses      * 
     * SHA-256 to hash a password.                                   *
     *                                                               *
     * @param String password user password + salt                   *
     * @throws NoSuchAlgorithmException                              *
     * @return string                                                *
     *****************************************************************/
    
    protected static String hashPassword(String password)
            throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.reset();
        md.update(password.getBytes());
        byte[] mdArray = md.digest();
        StringBuilder sb = new StringBuilder(mdArray.length *2);
        for(byte b: mdArray) {
            int v = b & 0xff;
            if(v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString();
    }
    
    /*****************************************************************
     *                    sendEmail()                                *
     *****************************************************************
     * Helper function for the forgot action in DoPost. Sends an     *
     * email to the user with their new password                     *
     *                                                               *
     * @param String email Users email address                       *
     * @param String password Users new password                     *
     * @param String name Users full name                            *
     * @return None                                                  *
     *****************************************************************/
    private void sendEmail(String email, String password, String name) {
    
        String to = email;
        String from = "twitproject16@yahoo.com";
        String subject = "New Password";
        String body = "Dear " + name + ",\n\n" +
        "You recently requested a password change. Here is your new password: \n\n" +
        password +
        "\n\nHave a great day!\n\n" +
        "Twitter Team\n" +
        "Alex Foreman & Paul Brown";
        boolean isBodyHTML = false;

        try
        {
            MailUtilYahoo.sendMail(to, from, subject, body, isBodyHTML);
        }
        catch (MessagingException e)
        {
            this.log(
                "Unable to send email. \n" +
                "Here is the email you tried to send: \n" +
                "=====================================\n" +
                "TO: " + email + "\n" +
                "FROM: " + from + "\n" +
                "SUBJECT: " + subject + "\n" +
                "\n" +
                body + "\n\n");
        }            
    }
} // </editor-fold>
