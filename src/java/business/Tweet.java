/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;
import java.io.Serializable;
import java.util.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat; 
/**
 *
 * @javabean for Tweet Entity
 */
public class Tweet implements Serializable {
    // define attributes
    private String composerEmail;
    private String message;
    private String mentions;
    private String sDate;
    private Timestamp date;
    
    public Tweet() {
        composerEmail = "";
        message = "";
        mentions = "";
        date = null;
    }
    
    //setters and getters
    public String getcomposerEmail() {
        return this.composerEmail;
    }
    public void setcomposerEmail(String composerEmail) {
        this.composerEmail = composerEmail;
    }
    public String getMessage(){
        return this.message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getMentions() {
        return this.mentions;
    }
    public void setMentions(String mentions) {
        this.mentions = mentions;
    }
    public void setsDate(String date) {
        this.sDate = date;
    }
    public String getsDate() {
        return this.sDate;
    }
    public String getDate() {
        Timestamp ts = this.date;
        Date date = new Date();
        date.setTime(ts.getTime());
        String formattedDate = new SimpleDateFormat("yyyyMMdd hh:mm:ss").format(date);
        
        return formattedDate;
    } 
}
