package Controller;

import model.Kategorie;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Poloczek
 */
@Stateless
@LocalBean
public class KategorieBean {

    @PersistenceContext (name = "DailyDB")
    private EntityManager em;
    
    public Kategorie createKategorie(String beschreibung){
        Kategorie k = new Kategorie(beschreibung);

        em.setFlushMode(FlushModeType.AUTO);
        em.persist(k);
        k = em.merge(k);
        em.flush();
        
        return k;
    }
    
    public List<Kategorie> getAllKategorien(){
        TypedQuery<Kategorie> kategorien = em.createNamedQuery("Kategorie.getAllKategorie", Kategorie.class);
        return kategorien.getResultList();
    }
    
    public List<Kategorie> getAllKategorienVonAnwender(String username){
        throw new RuntimeException("Noch nicht implementiert");
    }
    
}