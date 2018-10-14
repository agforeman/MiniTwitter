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
    private String fullname;
    private String username;
    private String emailAddress;
    private String birthdate;
    private String password;
    private String questionNo;
    private String answer;
    
    public User()
    {
        fullname = "";
        username = "";
        emailAddress = "";
        birthdate = "";
        password = "";
        questionNo = "";
        answer = "";
    }
    //setters and getters
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
  
}
