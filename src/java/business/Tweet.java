/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @javabean for Tweet Entity
 */
public class Tweet implements Serializable {
    // define attributes
    private String composerEmail;
    private String message;
    private String mentions;
    
    public Tweet() {
        composerEmail = "";
        message = "";
        mentions = "";
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
}
