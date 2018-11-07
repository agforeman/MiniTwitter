/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

/**
 *
 * @author Paul Brown
 */
public class UserMention {
    
    private String composerEmail;
    private int tweetid;
    private String userMentionedEmail;
    
    public UserMention()
    {
        composerEmail = "";
        tweetid = 0;
        userMentionedEmail = "";
    }
    
    public String getemailAddress() {
        return this.composerEmail;
    }
    public void setemailAddress(String email) {
        this.composerEmail = email;
    }
    public int gettweetid() {
        return this.tweetid;
    }
    public void settweetid(int ID) {
        this.tweetid = ID;
    }
    public String getuserMentionedEmail() {
        return this.userMentionedEmail;
    }
    public void setuserMentionedEmail(String mentionedEmail) {
        this.userMentionedEmail = mentionedEmail;
    }
    
    
}
