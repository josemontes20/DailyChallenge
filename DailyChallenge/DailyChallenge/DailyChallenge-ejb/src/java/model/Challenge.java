package model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;

/**
 *
 * @author Poloczek
 */

@Entity
@NamedQueries(
{
    /*
    @NamedQuery(name = "Anwender.findById", query = "SELECT a FROM Anwender a WHERE a.id = :id"),
    @NamedQuery(name = "Anwender.findByUser", query = "SELECT a FROM Anwender a WHERE a.username = :username"),
    @NamedQuery(name = "Anwender.findByUserAndPassword", query = "SELECT a FROM Anwender a WHERE a.username = :username AND a.userpassword = :userpassword"),
    @NamedQuery(name = "Anwender.GetScore", query = "SELECT a.score FROM Anwender a WHERE a.username = :username"),
    @NamedQuery(name = "Anwender.GetEmail", query = "SELECT a.email FROM Anwender a WHERE a.email = :email")
    */
})
public class Challenge implements Serializable {
   
    private static final long serialVersionUID = 1L;
   
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column
    private String beschreibung;

    public Challenge() {
    }

    public Challenge(Long id, String beschreibung) {
        this.id = id;
        this.beschreibung = beschreibung;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
