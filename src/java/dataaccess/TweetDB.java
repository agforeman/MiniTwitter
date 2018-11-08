/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;
import business.Tweet;
import business.User;
import business.UserTweetInfo;
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
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        
        String preparedSQL = "INSERT INTO "
                           + "tweets(composerEmail, message, mentions) "
                           + "VALUES (?,?,?)";
        
        try {
            ps = connection.prepareStatement(preparedSQL);
            ps.setString(1, tweet.getcomposerEmail());
            ps.setString(2, tweet.getMessage());
            ps.setBoolean(3, tweet.getMentions());            
            
            ps.executeUpdate();
            return true;
        } catch (SQLException  e) {
            for (Throwable t : e)
                t.printStackTrace();
            return false;
        } catch (Exception e) {
            return false;
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    public static boolean delete(String tweetID) throws ClassNotFoundException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        
        String preparedSQL = "DELETE FROM tweets "
                           + "WHERE id = ?";
        
        try {
            ps = connection.prepareStatement(preparedSQL);
            ps.setInt(1, Integer.parseInt(tweetID));
            
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            for (Throwable t : e)
                t.printStackTrace();
            return false;
        } catch (Exception e) {
            return false;
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    public static int numberOfUserTweets(User user) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        int numberOfTweets = 0;
        
        String preparedSQL = "SELECT numberOfTweets "
                           + "FROM tweet_number_view "
                           + "WHERE emailAddress = ? ";
        
        try {
            ps = connection.prepareStatement(preparedSQL);
            ps.setString(1, user.getemail());
          
            
            rs = ps.executeQuery();
            while(rs.next()) {
                numberOfTweets = rs.getInt("numberOfTweets");
            }
            return numberOfTweets;
        } catch (SQLException  e) {
            for (Throwable t : e)
                t.printStackTrace();
            return numberOfTweets;
        } catch (Exception e) {
            return numberOfTweets;
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    public static ArrayList<UserTweetInfo> selectTweetsByUser(String email) 
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
            
        String preparedSQL = "SELECT * FROM user_feed_view "
                           + "WHERE emailAddress = ? "
                           + "OR userMentionedEmail = ?";
        
        try {
            ps = connection.prepareStatement(preparedSQL);
            ps.setString(1, email);
            ps.setString(2, email);
            
            rs = ps.executeQuery();
            ArrayList<UserTweetInfo> tweets = new ArrayList<UserTweetInfo>();
            
            while(rs.next()) {
                UserTweetInfo tweet = new UserTweetInfo();
                tweet.settweetid(rs.getInt("tweetID"));
                tweet.setemailAddress(rs.getString("emailAddress"));
                tweet.setusername(rs.getString("username"));
                tweet.setfullname(rs.getString("fullname"));
                tweet.setmessage(rs.getString("message"));
                tweet.setmentions(rs.getString("userMentionedEmail"));
                tweet.setdate(rs.getString("date"));
                
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
