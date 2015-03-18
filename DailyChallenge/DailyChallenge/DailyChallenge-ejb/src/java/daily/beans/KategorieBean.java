package daily.beans;

import daily.model.Kategorie;
import daily.model.Anwender;

import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/*
    Diese Bean verwaltet die Kategorie-Entitys.
    - Anlegen, Suchen und Anwender-Kategorie-Beziehung verwalten
 */

@Stateless
@LocalBean
public class KategorieBean {

    @PersistenceContext (name = "DailyDB")
    private EntityManager em;
    
    // Erstellen einer Kategorie in der Kategorie-Tabelle
    public Kategorie createKategorie(String beschreibung){
        Kategorie k = new Kategorie(beschreibung);

        em.setFlushMode(FlushModeType.AUTO);
        em.persist(k);
        k = em.merge(k);
        em.flush();
        
        return k;
    }
    
    // Alle vorhandenen Kategorien zurückgeben
    public List<Kategorie> getAllKategorien(){
        TypedQuery<Kategorie> kategorien = em.createNamedQuery("Kategorie.getAllKategorie", Kategorie.class);
        return kategorien.getResultList();
    }
    
    // Alle Kategorien eines Anwenders zurückgeben aus der Anwender_Kategorie-Tabelle (Join-Tabelle)
    public List<Kategorie> getAllKategorienByUser(Long id){
        TypedQuery<Kategorie> kategorien = em.createNamedQuery("Kategorie.getAllKategorieByUser", Kategorie.class)
                .setParameter("id", id);
        return kategorien.getResultList();
    }
    
    // Alle Kategorien eines Anwenders zurückgeben aus der Anwender_Kategorie-Tabelle (Join-Tabelle)
    public List<Kategorie> getAllKategorienByUser(Anwender anwender){
        return getAllKategorienByUser(anwender.getId());
    }
    
    // Finden einer Kategorie anhand des Namens
    public Kategorie getKategorieByName (String name){
        TypedQuery<Kategorie> kategorie = em.createNamedQuery("Kategorie.getKategorieByName", Kategorie.class).setParameter("name", name);
        return kategorie.getResultList().get(0);
    }
    
}