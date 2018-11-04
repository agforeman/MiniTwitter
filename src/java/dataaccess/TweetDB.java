/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;
import business.Tweet;
import business.User;
import java.io.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Alex
 */
public class TweetDB {
    public static boolean insert(Tweet tweet) throws ClassNotFoundException {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String dbURL = "jdbc:mysql://localhost:3306/twitterdb";
            String username = "root";
            String password = "root";
            Connection connection = DriverManager.getConnection(dbURL, username, password);
            Statement statement = connection.createStatement();
            
            String preparedSQL = 
                    "INSERT INTO " +
                    "tweets(composerEmail, message, mentions) "
                    + "VALUES ( '" + tweet.getcomposerEmail() + "', '"
                                   + tweet.getMessage() + "', '"
                                   + tweet.getMentions()+ "')";
            
            statement.executeUpdate(preparedSQL);
        } catch (SQLException  e) {
            for (Throwable t : e)
                t.printStackTrace();
            return false;
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    
    public static ArrayList<Tweet> selectTweetsByUser(String email) 
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
            
        String preparedSQL = "SELECT * FROM user_tweet_view "
                           + "WHERE emailAddress = ?";
        
        try {
            ps = connection.prepareStatement(preparedSQL);
            ps.setString(1, email);
            
            rs = ps.executeQuery();
            ArrayList<Tweet> tweets = new ArrayList<Tweet>();
            
            while(rs.next()) {
                Tweet tweet = new Tweet();
                tweet.setMessage(rs.getString("message"));
                tweet.setcomposerEmail(rs.getString("emailAddress"));
                
                tweets.add(tweet);
            }
            connection.close();
            return tweets;
            
        } catch (SQLException e) {
            for (Throwable t : e)
                t.printStackTrace();
            return null;
        } finally {
            DBUtil.closePreparedStatement(ps);
            DBUtil.closeResultSet(rs);
            pool.freeConnection(connection);
        }
    }
}
