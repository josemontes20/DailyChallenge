package Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import model.Challenge;
import model.Anwender;
import java.util.ArrayList;
import java.util.Calendar;
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
    
    private Challenge findChallenge(Date d, Kategorie k){
        String parsedDate = parseDateToString(d); // Vor dem Suchen muss das Date-Objekt in ein String geparst werden
        return findChallenge(parsedDate, k);
    }
    
    private Challenge findChallenge(String parsedDate, Kategorie k){
        TypedQuery<Challenge> challengesQuery = em.createNamedQuery("Challenge.findChallengeByDateAndKategorie", Challenge.class)
                .setParameter("aktivAmDatum", parsedDate)
                .setParameter("kategorie_id", k.getId());
        
        List<Challenge> challenges = challengesQuery.getResultList();
        if (challenges != null && !challenges.isEmpty() && challenges.get(0) != null) {
            return challengesQuery.getResultList().get(0);
        }
        return null;
    }
    
    public List<Challenge> findUnusedChallenges(Kategorie k){
        TypedQuery<Challenge> challenges = em.createNamedQuery("Challenge.findUnusedChallengesByKategorie", Challenge.class)
                .setParameter("kategorie_id", k.getId());
        return challenges.getResultList();
    }

    // Suche Challenges für den heutigen Tag
    // Wenn keine für Heute vorhanden sind, setze welche für Heute
    public List<Challenge> getChallengesForToday(List<Kategorie> kategorien){
        List<Challenge> challenges = new ArrayList<>(); // Challenges, die der Anwender möchte
        Date today = Calendar.getInstance().getTime();  // Datum von Heute zum Überprüfen in der DB

        for (Kategorie kat : kategorien) {
            Challenge c = findChallenge(today, kat);    // Suche die Challenge für Heute in allein meinen Kategorien
            if (c == null) {                            // Wenn nicht vorhanden, weise ein zufälliges für Heute zus
                List<Challenge> unusedChallenges = findUnusedChallenges(kat);
                int randomNumber = (int)(Math.random() * unusedChallenges.size());
                c = unusedChallenges.get(randomNumber);
                
                c.setAktivAmDatum(parseDateToString(today));
                em.persist(c);
                em.flush();
            }
            challenges.add(c);
        }
        
        return challenges;
    }
    
    private List<Challenge> getAllChallengesByUser(Long id){
        TypedQuery<Challenge> challenges = em.createNamedQuery("Challenge.findByAnwenderId", Challenge.class)
                .setParameter("id", id);
        return challenges.getResultList();
    }
    
    public List<Challenge> getAllChallengesByUser(Anwender anwender){
        return getAllChallengesByUser(anwender.getId());
    }
    
    private String parseDateToString(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String parsedString = formatter.format(date);
        return parsedString;
    }
    
    private Date parseStringToDate(String date) throws ParseException{
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedDate = formatter.parse(date);
        return parsedDate;
    }

}