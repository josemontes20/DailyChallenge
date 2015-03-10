package model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * @author Poloczek
 */

@Entity
@NamedQueries(
{
    @NamedQuery(name = "Challenge.findById", query = "SELECT c FROM Challenge c WHERE c.id = :id")
    
})
public class Challenge implements Serializable {
   
    private static final long serialVersionUID = 1L;
   
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column
    private String beschreibung;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "kategorie_id")
    private Kategorie kategorie;
    public Challenge() {
    }

    public Challenge(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public Kategorie getKategorie() {
        return kategorie;
    }

    public void setKategorie(Kategorie kategorie) {
        this.kategorie = kategorie;
    }
    
}
