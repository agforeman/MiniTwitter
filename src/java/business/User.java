/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;
import java.io.Serializable;

/**
 *
 * @javabean for User Entity
 */
public class User implements Serializable {
    //define attributes fullname, ...
    
    //define set/get methods for all attributes.
    private String fullName;
    private String email;
    public User()
    {
        fullName = "";
        email = "";
    }
    public User(String fromString)
    {
        String[] data = fromString.replace("[", "").split(",");
        this.setFullName(data[0]);
        this.setEmail(data[1]);
    }
    public String getFullName()
    {
        return this.fullName;
    }
    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }
    public String getEmail()
    {
        return this.email;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }
    @Override
    public String toString()
    {
      StringBuilder sb = new StringBuilder();
      sb.append(String.format("[%s,%s]", this.getFullName(), this.getEmail()));
      return sb.toString();
    }
    
    
}
