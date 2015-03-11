package model;

import java.util.List;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
/**
 * @author José Montes
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Kategorie.getAllKategorie", query = "SELECT k FROM Kategorie k"),
    @NamedQuery(name = "Kategorie.getAllKategorieByUser", query = "SELECT k FROM Kategorie k INNER JOIN k.anwender a WHERE a.id = :id"),
})
public class Kategorie implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name="name", nullable=false, unique = true, length = 20)
    private String name;
    
    @OneToMany(mappedBy="kategorie", cascade={CascadeType.ALL})
    private List<Challenge> challenges;
   
    @ManyToMany(mappedBy = "kategorie") // <- Name der Spalte in "ANWENDER_KATEGORIEN", 
    private List<Anwender> anwender;    //    muss übereinstimmen mit dem Variablennamen in Anwender.java
    
    // Konstruktoren
    public Kategorie() {
    }
    
    public Kategorie(String name) {
        this.name = name;
    }
    
    public Kategorie(String name, List<Challenge> challenges) {
        this.name = name;
        this.challenges = challenges;
    }

    // Getter und Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public List<Anwender> getAnwender() {
        return anwender;
    }

    public void setAnwender(List<Anwender> anwender) {
        this.anwender = anwender;
    }

    public String getName() {
        return name;
    }
    
    public List<Challenge> getChallenges() {
        return challenges;
    }

    public void setChallenges(List<Challenge> challenges) {
        this.challenges = challenges;
    }
    
    // Andere Methoden
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (name != null ? name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Kategorie)) {
            return false;
        }
        Kategorie other = (Kategorie) object;
        if ((this.name == null && other.name != null) || (this.name != null && !this.name.equals(other.name))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Kategorie[ name=" + name + " ]";
    }
    
}
