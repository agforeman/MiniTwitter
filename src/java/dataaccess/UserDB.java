/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;
import business.User;
import java.io.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDB {
    public static boolean insert(User user) throws ClassNotFoundException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
            
            String preparedSQL = "INSERT INTO "
                               + "user(fullname, username, emailAddress, "
                               + "birthdate, password, questionNo, answer) "
                               + "VALUES (?,?,?,?,?,?,?)";
        try {
            ps = connection.prepareStatement(preparedSQL);
            ps.setString(1, user.getfullname());
            ps.setString(2, user.getusername());
            ps.setString(3, user.getemail());
            ps.setString(4, user.getbirthdate());
            ps.setString(5, user.getpassword());
            ps.setInt(6, Integer.parseInt(user.getquestionno()));
            ps.setString(7, user.getanswer());
            
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
    public static User search(String emailAddress) 
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet results = null;
            
        String preparedSQL = 
            "SELECT userID, emailAddress FROM user " +
            "WHERE emailAddress = ?";
        
        try {
            ps = connection.prepareStatement(preparedSQL);
            ps.setString(1, emailAddress);
            results = ps.executeQuery();
            
            if (results.next()){
                int userID = results.getInt("userID");
                results.close();
                
                preparedSQL = 
                    "SELECT * FROM user " +
                    "WHERE userID = " + userID;
                
                results = ps.executeQuery(preparedSQL);
                if(results.next()){
                    User user = new User();
                    user.setfullname(results.getString(2));
                    user.setusername(results.getString(3));
                    user.setemail(results.getString(4));
                    user.setbirthdate(results.getString(5));
                    user.setpassword(results.getString(6));
                    user.setquestionno(results.getString(7));
                    user.setanswer(results.getString(8));
                    
                    results.close();
                    return user;
                }              
            } else {
                results.close();
                return null;
            }
            
        } catch (SQLException e) {
            for (Throwable t : e)
                t.printStackTrace();
            return null;
        } finally {
            DBUtil.closePreparedStatement(ps);
            DBUtil.closeResultSet(results);
            pool.freeConnection(connection);
        }
        return null;
    }
    
    public static ArrayList<User> selectUsers() 
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
            
        String preparedSQL = "SELECT * FROM user_view";
        
        try {
            ps = connection.prepareStatement(preparedSQL);
            rs = ps.executeQuery();
            ArrayList<User> users = new ArrayList<User>();
            
            while(rs.next()) {
                User user = new User();
                user.setfullname(rs.getString("fullname"));
                user.setusername(rs.getString("username"));
                user.setemail(rs.getString("emailAddress"));
                
                users.add(user);
            }
            connection.close();
            return users;
            
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
    
    public static boolean update(User user) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
            
        String preparedSQL = 
            "UPDATE User SET "
            + "fullname = ?, "
            + "emailAddress = ?, "
            + "birthdate = ?, "
            + "password = ?, "
            + "questionNo = ?,"
            + "answer = ?"
            + "WHERE emailAddress = ?";
            
        try {
            ps = connection.prepareStatement(preparedSQL);
            ps.setString(1, user.getfullname());
            ps.setString(2, user.getemail());
            ps.setString(3, user.getbirthdate());
            ps.setString(4, user.getpassword());
            ps.setString(5, user.getquestionno());
            ps.setString(6, user.getanswer());
            ps.setString(7, user.getemail());
            
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
