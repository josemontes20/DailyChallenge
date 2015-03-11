package Controller;

import java.util.Collection;
import model.Challenge;
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
public class ChallengeBean {

    @PersistenceContext (name = "DailyDB")
    private EntityManager em;
    
    public Collection<Challenge> getAllChallengesByUser(Long id){
        TypedQuery<Challenge> challenges = em.createNamedQuery("Challenge.findByAnwenderId", Challenge.class)
                .setParameter("id", id);
        return challenges.getResultList();
    }
    
}