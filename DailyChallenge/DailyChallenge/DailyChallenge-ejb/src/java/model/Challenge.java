package model;

import java.io.Serializable;
import java.text.DateFormat;
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
    @NamedQuery(name = "Challenge.findByAnwenderId", query = "SELECT c FROM Challenge c INNER JOIN c.kategorie k, k.anwender a WHERE a.id = :id AND k.id = c.kategorie.id"),
    @NamedQuery(name = "Challenge.findChallengeByDateAndKategorie", query = "SELECT c FROM Challenge c WHERE c.aktivAmDatum = :aktivAmDatum AND c.kategorie.id = :kategorie_id"),
    @NamedQuery(name = "Challenge.findChallengeByKategorie", query = "SELECT c FROM Challenge c WHERE c.kategorie.id = :kategorie_id"),
    @NamedQuery(name = "Challenge.findUnusedChallengesByKategorie", query = "SELECT c FROM Challenge c WHERE c.aktivAmDatum IS NULL AND c.kategorie.id = :kategorie_id"),
    @NamedQuery(name = "Challenge.updateAsTodaysChallenge", query = "UPDATE Challenge c SET c.aktivAmDatum = :aktivAmDatum")
})
public class Challenge implements Serializable {
   
    private static final long serialVersionUID = 1L;
   
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column
    private String beschreibung;
    
    @Column
    private String aktivAmDatum;
            
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "kategorie_id")
    private Kategorie kategorie;

    // Konstruktoren
    public Challenge() {
    }

    public Challenge(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    // Getter und Setter
    public String getAktivAmDatum() {
        return aktivAmDatum;
    }

    public void setAktivAmDatum(String aktivAmDatum) {
        this.aktivAmDatum = aktivAmDatum;
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