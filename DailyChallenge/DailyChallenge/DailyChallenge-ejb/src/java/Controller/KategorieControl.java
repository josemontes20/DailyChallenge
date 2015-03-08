package Controller;


import java.util.Collection;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.Kategorie;

/**
 *
 * @author Poloczek
 * 
 * Diese Bean ist für das Auslesen und Einschreiben in Kategorien zuständig.
 */

@Stateless
@LocalBean
public class KategorieControl {
    
    @PersistenceContext (name = "DailyDB")
    private EntityManager em;
    
    public Collection<Kategorie> findAllKategorie(){
        return em.createNamedQuery("Kategorie.findAllKategorie").getResultList();
    }
    
}
