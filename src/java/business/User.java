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
    private String userName;
    private String email;
    private String birthDate;
    private String password;
    private String questionNo;
    private String answer;
    
    public User()
    {
        fullName = "";
        userName = "";
        email = "";
        birthDate = "";
        password = "";
        questionNo = "";
        answer = "";
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
    public String getUserName() 
    {
        return this.userName;
    }
    public void setUserName(String userName)
    {
        this.userName = userName;
    }
    public String getEmail()
    {
        return this.email;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }
    public String getBirthDate()
    {
        return this.birthDate;
    }
    public void setBirthDate(String birthDate)
    {
       this.birthDate = birthDate;
    }
    public String getPassword()
    {
        return this.password;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }
    public String getQuestionNo()
    {
        return this.questionNo;
    }
    public void setQuestionNo(String questionNo)
    {
        this.questionNo = questionNo;
    }
    public String getAnswer()
    {
        return this.answer;
    }
    public void setAnswer(String answer)
    {
        this.answer = answer;
    }
    @Override
    public String toString()
    {
      StringBuilder sb = new StringBuilder();
      sb.append(String.format("[%s,%s]", this.getFullName(), this.getEmail()));
      return sb.toString();
    }
    
    
}
