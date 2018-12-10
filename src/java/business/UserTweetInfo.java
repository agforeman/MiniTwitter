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
    private String mentions;
    private String date;
    
    public UserTweetInfo() {
        tweetid = 0;
        emailAddress = "";
        username = "";
        fullname = "";
        message = "";
        mentions = "";
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
        String at = "@";
        String hashtag = "#";
        
        message = colorText(message, at);
        message = colorText(message, hashtag);
        
        this.message = message;
    }
    public String getmentions() {
        return this.mentions;
    }
    public void setmentions(String mentions) {
        this.mentions = mentions;
    }
    public String getdate() {
        return this.date;
    }
    public void setdate(String date) {
        this.date = date;
    }
    
    //helper function for coloring hashtags and user mentions
    public String colorText(String message, String character)
    {
        String newMessage= null;
        int startInd = 0;
        while(message.indexOf(character, startInd)!= -1)
        {
            int indexOf = message.indexOf(character, startInd);
            int indexOfSpace = message.indexOf(" ", indexOf+1);
            if(indexOfSpace == -1) {
                indexOfSpace = message.length();
            }
            String mention = message.substring(indexOf, indexOfSpace);
            newMessage = message.replaceFirst(mention, "<a class='blueX'>" + mention +
                              "</a>");
            message = newMessage;
            //21 equals the amount of added HTML chars. We want the new starting index
            //to be after the inserted modified code. This handles more than one mention.
            startInd = indexOf+21+mention.length();
        }
        return message;
    }
}


