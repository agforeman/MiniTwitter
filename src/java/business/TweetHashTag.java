/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Paul Brown
 */
public class TweetHashTag implements Serializable {
    
    private int tweetid;
    private int hashid;
    
    public void HashTag() {
        
        tweetid = 0;
        hashid = 0;
    }
    public int gettweetid() 
    {
        return this.tweetid;
    }
    public void settweetid(int tweetid)
    {
        this.tweetid = tweetid;
    }
    public int gethashid()
    {
        return this.hashid;
    }
    public void sethashid(int hashid)
    {
        this.hashid = hashid;
    } 
}
