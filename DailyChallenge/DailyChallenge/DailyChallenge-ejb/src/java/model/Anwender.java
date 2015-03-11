package model;

import java.util.List;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
    
    
    @ManyToMany
    @JoinTable(name = "anwender_kategorien")
    private List<Kategorie> anwender_kategorien;            
    
    // Konstruktoren
    public Anwender() {
    }

    public Anwender(String username, String userpassword, String email, int score) {
        this.username = username;
        this.userpassword = userpassword;
        this.email = email;
        this.score = score;
    }

    // Getter und Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<Kategorie> getAnwender_kategorien() {
        return anwender_kategorien;
    }

    public void setAnwender_kategorien(List<Kategorie> anwender_kategorien) {
        this.anwender_kategorien = anwender_kategorien;
    }
    
    // Andere Methoden
    @Override
    public int hashCode(){
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

   @Override
   public boolean equals(Object object){
        if (!(object instanceof Anwender)){
            return false;
        }
        Anwender other = (Anwender) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))){
            return false;
        }
        return true;
    }
    
}

