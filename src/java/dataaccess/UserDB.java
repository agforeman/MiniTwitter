/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;
import business.User;
import java.io.*;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDB {
    public static boolean insert(User user) throws ClassNotFoundException {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String dbURL = "jdbc:mysql://localhost:3306/twitterdb";
            String username = "root";
            String password = "root";
            Connection connection = DriverManager.getConnection(dbURL, username, password);
            Statement statement = connection.createStatement();
            
            String preparedSQL = 
                    "INSERT INTO " +
                    "user(fullname, username, emailAddress, " +
                    "birthdate, password, questionNo, answer) "
                    + "VALUES ( '" + user.getfullname() + "', '"
                                   + user.getusername() + "', '"
                                   + user.getemail()+ "', '"
                                   + user.getbirthdate()+ "', '"
                                   + user.getpassword()+ "', '"
                                   + user.getquestionno()+ "', '"
                                   + user.getanswer()+ "')";
            
            statement.executeUpdate(preparedSQL);
        } catch (SQLException e) {
            for (Throwable t : e)
                t.printStackTrace();
            return false;
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    public static User search(String emailAddress) 
    {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String dbURL = "jdbc:mysql://localhost:3306/twitterdb";
            String username = "root";
            String password = "root";
            Connection connection = DriverManager.getConnection(dbURL, username, password);
            Statement statement = connection.createStatement();
            
            String preparedSQL = 
                    "SELECT userID, emailAddress FROM user " +
                    "WHERE emailAddress = '"+ emailAddress +"'";
            
            ResultSet results = statement.executeQuery(preparedSQL);
            if (results.next()){
                int userID = results.getInt("userID");
                results.close();
                
                preparedSQL = 
                    "SELECT * FROM user " +
                    "WHERE userID = " + userID;
                
                results = statement.executeQuery(preparedSQL);
                if(results.next()){
                    User user = new User();
                    user.setfullname(results.getString(2));
                    user.setusername(results.getString(3));
                    user.setemail(results.getString(4));
                    user.setbirthdate(results.getString(5));
                    user.setpassword(results.getString(6));
                    
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

        } catch (Exception e) {

        }
        return null;
    }
    
}
