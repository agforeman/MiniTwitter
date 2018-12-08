/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;
import java.io.Serializable;
/**
 *
 * @author Paul Brown
 */
public class UserFollow implements Serializable {
    
    private int userID;
    private int followedUserID;
    private String date;
    
    public UserFollow() {
        
        userID = 0;
        followedUserID = 0;
        date = "";
    }
    public int getuserID() {
        return this.userID;
    }
    public void setuserID(int userID)
    {
        this.userID = userID;
    }
    public int getfollowedUserID() {
        return this.followedUserID;
    }
    public void setfollowedUserID(int followedUserID) 
    {
        this.followedUserID = followedUserID;
    }
    public String getdate() 
    {
        return this.date;
    }
    public void setdate(String date)
    {
        this.date = date;
    }
}
