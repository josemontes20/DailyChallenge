package model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author José Montes
 */

@Entity
@NamedQueries(
{
    @NamedQuery(name = "Anwender.findById", query = "SELECT a FROM Anwender a WHERE a.id = :id"),
    @NamedQuery(name = "Anwender.findByUser", query = "SELECT a FROM Anwender a WHERE a.username = :username"),
    @NamedQuery(name = "Anwender.findByUserAndPassword", query = "SELECT a FROM Anwender a WHERE a.username = :username AND a.userpassword = :userpassword"),
    @NamedQuery(name = "Anwender.GetScore", query = "SELECT a.score FROM Anwender a WHERE a.username = :username"),
    @NamedQuery(name = "Anwender.GetEmail", query = "SELECT a.email FROM Anwender a WHERE a.email = :email")
})

public class Anwender implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column 
    private String username;
    
    @Column 
    private String userpassword;
    
    @Column 
    private String email;
    
    @Column 
    private int score;
    
    
    public Anwender (){
    }
    
    public Anwender (String username, String userpassword, String email, int score){
        this.username = username;
        this.userpassword = userpassword;
        this.email = email;
        this.score = score;  
    }
    
    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }
        
    public void setUserName(String username){
        this.username = username;
    }
    
    public String getUserName(){
        return this.username;
    }
    
    public void setUserPassword(String userpassword){
        this.userpassword = userpassword;
    }
    
    public String getUserPassword(){
        return this.userpassword;
    }
    
    public void setUserEmail(String email){
        this.email = email;
    }
    
    public String getUserEmail(){
        return this.email;
    }
    
    public void setUserScore (int score){
        this.score = score;
    }
    
    public int getUserScore (int score){
        return this.score;
    }
    
    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

   @Override
   public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Anwender))
        {
            return false;
        }
        Anwender other = (Anwender) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
        {
            return false;
        }
        return true;
    }
    
}

