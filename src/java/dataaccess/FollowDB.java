/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;
import business.UserFollow;
import java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Paul Brown
 */
public class FollowDB {
    public static boolean insert(UserFollow UserFollow) throws ClassNotFoundException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        
        String preparedSQL = "INSERT INTO "
                           + "follow(userID, followedUserID) "
                           + "VALUES (?,?)";
        
        try {
            ps = connection.prepareStatement(preparedSQL);
            ps.setInt(1, UserFollow.getuserID());
            ps.setInt(2, UserFollow.getfollowedUserID());            
            
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
    
    public static boolean delete(UserFollow follow) throws ClassNotFoundException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        int followID = follow.getfollowedUserID();
        int userID = follow.getuserID();
        
        String preparedSQL = "DELETE FROM follow "
                           + "WHERE userID = ? "
                           + "AND followedUserID = ?";
        
        try {
            ps = connection.prepareStatement(preparedSQL);
            ps.setInt(1, userID);
            ps.setInt(2, followID);
            
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
    
    public static ArrayList<UserFollow> selectFollowsByUser(int userID) 
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
            
        String preparedSQL = "SELECT * FROM follow "
                           + "WHERE userID = ? ";
        
        try {
            ps = connection.prepareStatement(preparedSQL);
            ps.setInt(1,userID);
            
            rs = ps.executeQuery();
            ArrayList<UserFollow> follows = new ArrayList<UserFollow>();
            
            while(rs.next()) {
                UserFollow follow = new UserFollow();
                follow.setuserID(rs.getInt("userID"));
                follow.setfollowedUserID(rs.getInt("followedUserID"));
                follow.setdate(rs.getString("followDate"));
                follows.add(follow);
            }
            connection.close();
            return follows;
            
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
