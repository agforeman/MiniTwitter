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
        String newMessage= null;
        int startInd = 0;
        while(message.indexOf("@", startInd)!= -1)
        {
            int indexOf = message.indexOf("@", startInd);
            int indexOfSpace = message.indexOf(" ", indexOf+1);
            if(indexOfSpace == -1) {
                indexOfSpace = message.length();
            }
            String mention = message.substring(indexOf, indexOfSpace);
            newMessage = message.replace(mention, "<html><font color='blue'>" + mention +
                              "</font></html>");
            startInd = indexOf+1;  
        }
        if(newMessage != null) {
            this.message = newMessage;
            newMessage = null;
        }
        else
            this.message = message;
    }
    public String getdate() {
        return this.date;
    }
    public void setdate(String date) {
        this.date = date;
    }
}
