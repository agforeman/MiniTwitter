/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.io.Serializable;

/**
 *
 * @author Alex
 */
public class UserTweetInfo implements Serializable {
    //Define attributes
    private int tweetid;
    private String emailAddress;
    private String username;
    private String fullname;
    private String message;
    private String date;
    
    public UserTweetInfo() {
        tweetid = 0;
        emailAddress = "";
        username = "";
        fullname = "";
        message = "";
        date = "";
    }
    
    public int gettweetid() {
        return this.tweetid;
    }
    public void settweetid(int tweetid) {
        this.tweetid = tweetid;
    }
    public String getemailAddress() {
        return this.emailAddress;
    }
    public void setemailAddress(String email) {
        this.emailAddress = email;
    }
    public String getusername() {
        return this.username;
    }
    public void setusername(String username) {
        this.username = username;
    }
    public String getfullname() {
        return this.fullname;
    }
    public void setfullname(String fullname) {
        this.fullname = fullname;
    }
    public String getmessage() {
        return this.message;
    }
    public void setmessage(String message) {
        this.message = message;
    }
    public String getdate() {
        return this.date;
    }
    public void setdate(String date) {
        this.date = date;
    }
}
