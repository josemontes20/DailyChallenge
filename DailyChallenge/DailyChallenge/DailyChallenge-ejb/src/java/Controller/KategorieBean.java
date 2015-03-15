package Controller;

import model.Kategorie;
import model.Anwender;

import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
    
    public List<Kategorie> getAllKategorienByUser(Long id){
        TypedQuery<Kategorie> kategorien = em.createNamedQuery("Kategorie.getAllKategorieByUser", Kategorie.class)
                .setParameter("id", id);
        return kategorien.getResultList();
    }
    
    public List<Kategorie> getAllKategorienByUser(Anwender anwender){
        return getAllKategorienByUser(anwender.getId());
    }
    
    public Kategorie getKategorieByName (String name){
        TypedQuery<Kategorie> kategorie = em.createNamedQuery("Kategorie.getKategorieByName", Kategorie.class).setParameter("name", name);
        return kategorie.getResultList().get(0);
    }
    
    public boolean deleteKategorienByUser (Long userId){
        //Abfrage, welche Kategorien vom User abonniert waren
        Query queryOld = em.createNamedQuery("Kategorie.getAllKategorieByUser", Kategorie.class).setParameter("id", userId);
        int userKat = queryOld.getMaxResults();
                
        
        //Löschen durchführen
        Query q = em.createNamedQuery("Kategorie.deleteKategorienByUser", Kategorie.class).setParameter("id", userId);
        int delStat = q.executeUpdate();
        
        return delStat == userKat;
        
    }

}