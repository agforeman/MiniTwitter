/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import business.Tweet;
import business.User;
import business.UserTweetInfo;
import dataaccess.TweetDB;
import dataaccess.UserDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Alex
 */
public class tweetServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet tweetServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet tweetServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    
    /**
     * Handles the HTTP <code>GET</code> method.
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
        // TO DO GET USER NUMBER OF TWEETS
        int numberOfTweets = 0;
        
        String url = "/login.jsp";
        
        // If no user go to login
        if (user == null){
            url = "/login.jsp";
            action = "no_user";
        } else if (action == null) {
            // If user but no action go home
            url = "/home.jsp";
        } else if(action.equals("get_tweets")) {            
            ArrayList<UserTweetInfo> tweets;
            String email = user.getemail();
            tweets = TweetDB.selectTweetsByUser(email);
            numberOfTweets = TweetDB.numberOfUserTweets(user);
            
            session.setAttribute("tweets", tweets);
            session.setAttribute("numberOfTweets", numberOfTweets);
            
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        String action = request.getParameter("action");
        User user = (User) session.getAttribute("user");

        String url = "/home.jsp";
        
        if(action.equals("post_tweet")) {
            String composerEmail = user.getemail();
            String message = request.getParameter("user_tweet");
            String mentions = null; // TO DO PARSE MENTIONS FROM MESSAGE
            
            Tweet tweet = new Tweet();
            tweet.setcomposerEmail(composerEmail);
            tweet.setMessage(message);
            tweet.setMentions(mentions);
            
            try {
                TweetDB.insert(tweet);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(tweetServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(tweetServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            ArrayList<UserTweetInfo> tweets;
            user = (User) session.getAttribute("user");
            String email = user.getemail();
            tweets = TweetDB.selectTweetsByUser(email);
            session.setAttribute("tweets", tweets);
        }
        if(action.equals("delete_tweet")){
            // Get the id of this tweet from the request
            String tweetID = request.getParameter("tweetID");
            String mentions = null; // TO DO PARSE MENTIONS FROM MESSAGE
                        
            try {
                TweetDB.delete(tweetID);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(tweetServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(tweetServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            ArrayList<UserTweetInfo> tweets;
            user = (User) session.getAttribute("user");
            String email = user.getemail();
            tweets = TweetDB.selectTweetsByUser(email);
            session.setAttribute("tweets", tweets);
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
    }

}
