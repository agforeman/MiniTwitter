/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;
import business.Tweet;
import business.User;
import business.UserTweetInfo;
import business.UserMention;
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
 * @author Paul Brown
 */
public class UserMentionDB {
    public static boolean insert(UserMention tweet) throws ClassNotFoundException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        
        String preparedSQL = "INSERT INTO "
                           + "usermentions(composerEmail, tweetID, UserMentionedEmail) "
                           + "VALUES (?,?,?)";
        
        try {
            ps = connection.prepareStatement(preparedSQL);
            ps.setString(1, tweet.getemailAddress());
            ps.setInt(2, tweet.gettweetid());
            ps.setString(3, tweet.getuserMentionedEmail());
            
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
        
        String preparedSQL = "DELETE FROM usermentions "
                           + "WHERE tweetID = ?";
        
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
    
}

