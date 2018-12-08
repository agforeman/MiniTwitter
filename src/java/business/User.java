/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;
import java.io.Serializable;
import java.io.InputStream;
import java.sql.Blob;

/**
 *
 * @javabean for User Entity
 */
public class User implements Serializable {
    //define attributes fullname, ...
    
    //define set/get methods for all attributes.
    private int id;
    private String fullname;
    private String username;
    private String emailAddress;
    private String birthdate;
    private String password;
    private String salt;
    private String questionNo;
    private String answer;
    private InputStream photo;
    
    public User()
    {   
        id = 0;
        fullname = "";
        username = "";
        emailAddress = "";
        birthdate = "";
        password = "";
        salt = "";
        questionNo = "";
        answer = "";
        photo = null;
    }
    //setters and getters
    public int getid()
    {
        return this.id;
    }
    public void setid(int id)
    {
        this.id = id;
    }
    public String getfullname()
    {
        return this.fullname;
    }
    public void setfullname(String fullname)
    {
        this.fullname = fullname;
    }
    public String getusername() 
    {
        return this.username;
    }
    public void setusername(String userName)
    {
        this.username = userName;
    }
    public String getemail()
    {
        return this.emailAddress;
    }
    public void setemail(String email)
    {
        this.emailAddress = email;
    }
    public String getbirthdate()
    {
        return this.birthdate;
    }
    public void setbirthdate(String birthDate)
    {
       this.birthdate = birthDate;
    }
    public String getpassword()
    {
        return this.password;
    }
    public void setpassword(String password)
    {
        this.password = password;
    }
    public String getsalt()
    {
        return this.salt;
    }
    public void setsalt(String salt)
    {
        this.salt = salt;
    }
    public String getquestionno()
    {
        return this.questionNo;
    }
    public void setquestionno(String questionNo)
    {
        this.questionNo = questionNo;
    }
    public String getanswer()
    {
        return this.answer;
    }
    public void setanswer(String answer)
    {
        this.answer = answer;
    }
    public InputStream getphoto()
    {
        return this.photo;
    }
    public void setphoto(InputStream photo)
    {
        this.photo = photo;
    }
  
}
