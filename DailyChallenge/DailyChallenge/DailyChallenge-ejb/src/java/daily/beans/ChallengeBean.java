package daily.beans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import daily.model.Challenge;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import daily.model.Kategorie;


/*
    Diese Bean verwaltet die Challenge-Entitys.
    - Finden von Tages-Challenges, Zurücksetzen des Datums aller Challenges einer Kategorie, wenn alle verwendet worden sind. 
*/

@Stateless
@LocalBean
public class ChallengeBean {

    @PersistenceContext (name = "DailyDB")
    private EntityManager em;
    
    // Finden einer Challenge nach Datum und Kategorie
    private Challenge findChallenge(Date d, Kategorie k){
        String parsedDate = parseDateToString(d); // Vor dem Suchen muss das Date-Objekt in ein String geparst werden
        return findChallenge(parsedDate, k);
    }
    
    // Finden einer Challenge nach Datum und Kategorie
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
    
    // Alle noch nicht verwendeten Challenges einer Kategorie finden
    public List<Challenge> findUnusedChallenges(Kategorie k){
        TypedQuery<Challenge> challenges = em.createNamedQuery("Challenge.findUnusedChallengesByKategorie", Challenge.class)
                .setParameter("kategorie_id", k.getId());
        return challenges.getResultList();
    }

    // Suche Challenges für den heutigen Tag
    // Wenn keine für Heute vorhanden sind, setze welche für Heute
    public List<Challenge> getChallengesForToday(List<Kategorie> kategorien){
        List<Challenge> challenges = new ArrayList<>(); // Challenges, die der Anwender erhält
        Date today = Calendar.getInstance().getTime(); 

        for (Kategorie kat : kategorien) {
            Challenge c = findChallenge(today, kat);    // Suche die Challenge für Heute in allein meinen Kategorien
            if (c == null) {                            // Wenn nicht vorhanden, weise ein zufälliges für Heute zus
                List<Challenge> unusedChallenges = findUnusedChallenges(kat);
                
                // Falls es keine unbenutzten Challenges mehr gibt, setze alle 
                // Challenges dieser Kategorie zurück.
                if (unusedChallenges.isEmpty()) {
                    unusedChallenges = resetChallenges(kat);
                }
                
                // Von allen unbenutzten Challenges eine zufällige Challenge auswählen.
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

    // Das Datum aller Challenges einer Kategorie zurücksetzen
    private List<Challenge> resetChallenges(Kategorie k) {
        TypedQuery<Challenge> challengesQuery = em.createNamedQuery("Challenge.findChallengeByKategorie", Challenge.class)
                .setParameter("kategorie_id", k.getId());

        em.setFlushMode(FlushModeType.AUTO);
        List<Challenge> challenges = challengesQuery.getResultList();
        for (Challenge c : challenges) {
            c.setAktivAmDatum(null);
            em.merge(c);
        }
        em.flush();
        return challenges;
    }
    
    // Ein Datum in das yyyy-mm-dd String-Format umwandeln
    private String parseDateToString(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String parsedString = formatter.format(date);
        return parsedString;
    }
}