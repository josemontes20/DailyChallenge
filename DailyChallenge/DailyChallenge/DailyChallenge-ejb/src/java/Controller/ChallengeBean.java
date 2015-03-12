package Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import model.Challenge;
import model.Anwender;
import Controller.KategorieBean;
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
    
    private List<Challenge> findChallengesByDate(Date d){
        // Vor dem Suchen muss geparst werden
        String parsedDate = parseDateToString(d);
        return findChallengesByDate(parsedDate);
    }
    
    private List<Challenge> findChallengesByDate(String parsedDate){
        TypedQuery<Challenge> challenges = em.createNamedQuery("Challenge.findChallengesByDate", Challenge.class)
                .setParameter("aktivAmDatum", parsedDate);
        return challenges.getResultList();
    }
    
    public List<Challenge> findUnusedChallenges(Kategorie k){
        TypedQuery<Challenge> challenges = em.createNamedQuery("Challenge.findUnusedChallengesByKategorie", Challenge.class)
                .setParameter("kategorie_id", k.getId());
        return challenges.getResultList();
    }
    
    /* Hole alle Challenges, die für heute angesetzt sind
    Sind für heute noch keine geladen worden, setzt in jeder Kategorie zufällige Challenge als Tageschallenge */
    public List<Challenge> getChallengesForToday(List<Kategorie> kategorien){
        Date today = Calendar.getInstance().getTime();
        if(findChallengesByDate(today).isEmpty()){
            //KategorieBean katBean = new KategorieBean();
            // List<Kategorie> kategorien = katBean.getAllKategorien();
            
            for (Kategorie k : kategorien) {
                List<Challenge> challenges = findUnusedChallenges(k);
                
                // Ziehe ein zufälliges Objekt aus der Challenge-Liste
                int randomNumber = (int)(Math.random() * challenges.size());
                Challenge c = challenges.get(randomNumber);
            
                // Bevor die Challenges geupdated werden können, muss das Datum in einen String geparst werden
                TypedQuery<Challenge> update = em.createNamedQuery("Challenge.updateAsTodaysChallenge", Challenge.class)
                        .setParameter("aktivAmDatum", parseDateToString(today));
                update.executeUpdate();
            }
        }
        return findChallengesByDate(today);
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