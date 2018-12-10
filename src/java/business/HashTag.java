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
public class HashTag implements Serializable {
    
    private int id;
    private String hashText;
    private int hashCount;
    
    public void HashTag() {
        
        id = 0;
        hashText = "";
        hashCount = 0;
    }
    public int getid() 
    {
        return this.id;
    }
    public void setid(int id)
    {
        this.id = id;
    }
    public String gethashText()
    {
        return this.hashText;
    }
    public void sethashText(String text)
    {
        this.hashText = text;
    }
    public int gethashCount()
    {
        return this.hashCount;
    }
    public void sethashCount(int count)
    {
        this.hashCount = count;
    }
    
}
