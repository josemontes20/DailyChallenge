package Controller;

import model.Kategorie;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;

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
    
}
