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
                           + "tweets(composerEmail, message, mentions, hashtags) "
                           + "VALUES (?,?,?,?)";
        
        try {
            ps = connection.prepareStatement(preparedSQL);
            ps.setString(1, tweet.getcomposerEmail());
            ps.setString(2, tweet.getMessage());
            ps.setBoolean(3, tweet.getMentions()); 
            ps.setBoolean(4, tweet.gethashTags());
            
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
    public static int numberOfFollowers(String email){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        int numberOfFollowers = 0;
        
        String preparedSQL = "SELECT followed "
                           + "FROM user_followed_number_view "
                           + "WHERE emailAddress = ? ";
        
        try {
            ps = connection.prepareStatement(preparedSQL);
            ps.setString(1, email);
          
            
            rs = ps.executeQuery();
            while(rs.next()) {
                numberOfFollowers = rs.getInt("followed");
            }
            return numberOfFollowers;
        } catch (SQLException  e) {
            for (Throwable t : e)
                t.printStackTrace();
            return numberOfFollowers;
        } catch (Exception e) {
            return numberOfFollowers;
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    public static int numberOfFollowing(String email){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        int numberOfFollowing = 0;
        
        String preparedSQL = "SELECT following "
                           + "FROM user_following_number_view "
                           + "WHERE emailAddress = ? ";
        
        try {
            ps = connection.prepareStatement(preparedSQL);
            ps.setString(1, email);
          
            
            rs = ps.executeQuery();
            while(rs.next()) {
                numberOfFollowing = rs.getInt("following");
            }
            return numberOfFollowing;
        } catch (SQLException  e) {
            for (Throwable t : e)
                t.printStackTrace();
            return numberOfFollowing;
        } catch (Exception e) {
            return numberOfFollowing;
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
                           + "WHERE userEmail = ? "
                           + "ORDER BY date DESC";
        
        try {
            ps = connection.prepareStatement(preparedSQL);
            ps.setString(1, email);
            
            rs = ps.executeQuery();
            ArrayList<UserTweetInfo> tweets = new ArrayList<UserTweetInfo>();
            
            while(rs.next()) {
                UserTweetInfo tweet = new UserTweetInfo();
                tweet.settweetid(rs.getInt("tweetID"));
                tweet.setemailAddress(rs.getString("emailAddress"));
                tweet.setusername(rs.getString("username"));
                tweet.setfullname(rs.getString("fullname"));
                tweet.setmessage(rs.getString("message"));
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
    public static UserTweetInfo selectTweetByID(int tweetID) 
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
            
        String preparedSQL = "SELECT * FROM user_feed_view "
                           + "WHERE tweetID = ?";
        
        try {
            ps = connection.prepareStatement(preparedSQL);
            ps.setInt(1, tweetID);
            
            rs = ps.executeQuery();
            
            if(rs.next()) {
                UserTweetInfo tweet = new UserTweetInfo();
                tweet.settweetid(rs.getInt("tweetID"));
                tweet.setemailAddress(rs.getString("emailAddress"));
                tweet.setusername(rs.getString("username"));
                tweet.setfullname(rs.getString("fullname"));
                tweet.setmessage(rs.getString("message"));
                tweet.setdate(rs.getString("date"));
                
                return tweet;
            }
            connection.close();
            
            
        } catch (SQLException e) {
            for (Throwable t : e)
                t.printStackTrace();
            return null;
        } finally {
            DBUtil.closePreparedStatement(ps);
            DBUtil.closeResultSet(rs);
            pool.freeConnection(connection);
        }
        return null;
    }
    public static ArrayList<UserTweetInfo> getNewTweets(String email){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
            
        String preparedSQL = "SELECT * FROM new_tweet_view "
                           + "WHERE userEmail = ? "
                           + "AND last_login_time < date "
                           + "ORDER BY date DESC";
        
        try {
            ps = connection.prepareStatement(preparedSQL);
            ps.setString(1, email);
            
            rs = ps.executeQuery();
            ArrayList<UserTweetInfo> tweets = new ArrayList<UserTweetInfo>();
            
            while(rs.next()) {
                UserTweetInfo tweet = new UserTweetInfo();
                tweet.settweetid(rs.getInt("tweetID"));
                tweet.setemailAddress(rs.getString("emailAddress"));
                tweet.setusername(rs.getString("username"));
                tweet.setfullname(rs.getString("fullname"));
                tweet.setmessage(rs.getString("message"));
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
