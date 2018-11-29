/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;
import business.User;
import controller.tweetServlet;
import java.io.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDB {
    public static boolean insert(User user) throws ClassNotFoundException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
            
            String preparedSQL = "INSERT INTO "
                               + "user(fullname, username, emailAddress, "
                               + "birthdate, password, salt, questionNo, answer, "
                               + "profilePicture)"
                               + "VALUES (?,?,?,?,?,?,?,?)";
        try {
            ps = connection.prepareStatement(preparedSQL);
            ps.setString(1, user.getfullname());
            ps.setString(2, user.getusername());
            ps.setString(3, user.getemail());
            ps.setString(4, user.getbirthdate());
            ps.setString(5, user.getpassword());
            ps.setString(6, user.getsalt());
            ps.setInt(7, Integer.parseInt(user.getquestionno()));
            ps.setString(8, user.getanswer());
            ps.setBlob(9,user.getphoto());
            
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
        Blob blob;
        InputStream blobStream;
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
                    user.setsalt(results.getString(7));
                    user.setquestionno(results.getString(8));
                    user.setanswer(results.getString(9));
                    blob = results.getBlob(10);
                    if(blob != null) {
                        blobStream = blob.getBinaryStream();
                        user.setphoto(blobStream);
                        blob.free();
                       
                    }
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
            Blob blob = null;
            InputStream blobstream = null;
            
            while(rs.next()) {
                User user = new User();
                user.setfullname(rs.getString("fullname"));
                user.setusername(rs.getString("username"));
                user.setemail(rs.getString("emailAddress"));
                blob = rs.getBlob("profilePicture");
                    if(blob != null) {
                        blobstream = blob.getBinaryStream();
                        user.setphoto(blobstream);
                        blob.free();
                        try {
                            blobstream.reset();
                        }catch (IOException e) {
                            Logger.getLogger(tweetServlet.class.getName()).log(Level.SEVERE, null, e);
                        }
                    }
                
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
            + "birthdate = ?, "
            + "password = ?, "
            + "salt = ?, "    
            + "questionNo = ?,"
            + "answer = ?,"
            + "profilePicture = ?"    
            + "WHERE emailAddress = ?";
            
        try {
            ps = connection.prepareStatement(preparedSQL);
            ps.setString(1, user.getfullname());
            ps.setString(2, user.getbirthdate());
            ps.setString(3, user.getpassword());
            ps.setString(4, user.getsalt());
            ps.setString(5, user.getquestionno());
            ps.setString(6, user.getanswer());
            ps.setBlob(7, user.getphoto());
            ps.setString(8, user.getemail());
            
            
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
