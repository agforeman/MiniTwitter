/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;
import business.HashTag;
import java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Paul Brown
 */
public class HashTagDB {
    public static boolean insert(HashTag hashTag) throws ClassNotFoundException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        
        String preparedSQL = "INSERT INTO "
                           + "hashtag(hashtagText, hashtagCount) "
                           + "VALUES (?,?)";
        
        try {
            ps = connection.prepareStatement(preparedSQL);
            ps.setString(1, hashTag.gethashText()); 
            ps.setInt(2, hashTag.gethashCount());
            
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
    
    public static ArrayList<HashTag> getHashTags() 
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
            
        String preparedSQL = "SELECT * FROM hashtag "
                           + "ORDER BY hashTagCount DESC ";
        
        try {
            ps = connection.prepareStatement(preparedSQL);
            
            rs = ps.executeQuery();
            ArrayList<HashTag> hashTags = new ArrayList<HashTag>();
            
            while(rs.next()) {
                HashTag hashTag = new HashTag();
                hashTag.setid(rs.getInt("id"));
                hashTag.sethashText(rs.getString("hashtagText"));
                hashTag.sethashCount(rs.getInt("hashtagCount"));
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
    
    public static boolean update(HashTag hash) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
            
        String preparedSQL = 
            "UPDATE hashtag SET "
            + "hashtagCount = ? "   
            + "WHERE id = ?";
            
        try {
            ps = connection.prepareStatement(preparedSQL);
            ps.setInt(1, hash.gethashCount());
            ps.setInt(2, hash.getid());

            ps.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            for (Throwable t : e)
                t.printStackTrace();
            return false;
        }finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
       
    }
    
}
