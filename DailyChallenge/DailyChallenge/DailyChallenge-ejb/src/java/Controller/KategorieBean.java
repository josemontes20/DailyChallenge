package Controller;

import model.Kategorie;
import model.Anwender;

import java.util.Collection;
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
    
    public Collection<Kategorie> getAllKategorien(){
        TypedQuery<Kategorie> kategorien = em.createNamedQuery("Kategorie.getAllKategorie", Kategorie.class);        
        return kategorien.getResultList();
    }
    
    public Collection<Kategorie> getAllKategorienByUser(Long id){
        TypedQuery<Kategorie> kategorien = em.createNamedQuery("Kategorie.getAllKategorieByUser", Kategorie.class)
                .setParameter("id", id);
        return kategorien.getResultList();
    }
    
    public Collection<Kategorie> getAllKategorienByUser(Anwender anwender){
        return getAllKategorienByUser(anwender.getId());
    }
    
}