package Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import model.Challenge;
import model.Anwender;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import model.Kategorie;

/**
 *
 * @author Poloczek
 */
@Stateless
@LocalBean
public class ChallengeBean {

    @PersistenceContext (name = "DailyDB")
    private EntityManager em;
    
    public List<Challenge> getAllChallengesByUser(Long id){
        TypedQuery<Challenge> challenges = em.createNamedQuery("Challenge.findByAnwenderId", Challenge.class)
                .setParameter("id", id);
        return challenges.getResultList();
    }
    
    public List<Challenge> getAllChallengesByUser(Anwender anwender){
        return getAllChallengesByUser(anwender.getId());
    }
    
    public String parseDateToString(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String parsedString = formatter.format(date);
        return parsedString;
    }
    
    public Date parseStringToDate(String date) throws ParseException{
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedDate = formatter.parse(date);
        return parsedDate;
    }
    
}