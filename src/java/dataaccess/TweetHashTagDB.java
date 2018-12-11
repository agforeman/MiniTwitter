/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;
import business.TweetHashTag;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Paul Brown
 */
public class TweetHashTagDB {
    public static boolean insert(TweetHashTag tweetHash) throws ClassNotFoundException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        
        String preparedSQL = "INSERT INTO "
                           + "tweetHashtag(tweetID, hashtagID) "
                           + "VALUES (?,?)";
        
        try {
            ps = connection.prepareStatement(preparedSQL);
            ps.setInt(1, tweetHash.gettweetid()); 
            ps.setInt(2, tweetHash.gethashid());
            
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
    
    public static ArrayList<TweetHashTag> selectHashTagIDsByTweet(int tweetID) 
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
            
        String preparedSQL = "SELECT * FROM tweetHashtag "
                           + "WHERE tweetID = ?";
        
        try {
            ps = connection.prepareStatement(preparedSQL);
            ps.setInt(1, tweetID);
            rs = ps.executeQuery();
            ArrayList<TweetHashTag> hashTags = new ArrayList<>();
            
            while(rs.next()) {
                TweetHashTag hashTag = new TweetHashTag();
                hashTag.settweetid(rs.getInt("tweetID"));
                hashTag.sethashid(rs.getInt("hashtagID"));
                hashTags.add(hashTag);
            }
            connection.close();
            return hashTags;
            
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
    
    public static ArrayList<TweetHashTag> selectOnHashTagID(int hashID) 
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
            
        String preparedSQL = "SELECT * FROM tweetHashtag "
                           + "WHERE hashtagID = ?";
        
        try {
            ps = connection.prepareStatement(preparedSQL);
            ps.setInt(1, hashID);
            rs = ps.executeQuery();
            ArrayList<TweetHashTag> hashTags = new ArrayList<>();
            
            while(rs.next()) {
                TweetHashTag hashTag = new TweetHashTag();
                hashTag.settweetid(rs.getInt("tweetID"));
                hashTag.sethashid(rs.getInt("hashtagID"));
                hashTags.add(hashTag);
            }
            connection.close();
            return hashTags;
            
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
    
    public static boolean delete(int tweetID) throws ClassNotFoundException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        
        String preparedSQL = "DELETE FROM tweetHashtag "
                           + "WHERE tweetID = ?";
        
        try {
            ps = connection.prepareStatement(preparedSQL);
            ps.setInt(1, tweetID);
            
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
