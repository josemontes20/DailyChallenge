package Controller;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import model.Kategorie;
import model.Anwender;

/**
 * @author José Montes
 * 
 * Diese Bean ist für das Registrieren, Einloggen und Abmelden von Anwendern zuständig.
 */

@Stateless
@LocalBean
public class AnwenderBean{

    @PersistenceContext (name = "DailyDB")
    private EntityManager em;
    
    /*Diese Methode ist für das Anlegen eines Anwenders in der Anwender-Tabelle verantwortlich.*/
    public Anwender createUser(String username, String userpassword, String email, int score){
        Anwender user = new Anwender(username, userpassword, email, score);

        em.setFlushMode(FlushModeType.AUTO);   
        em.persist(user);
        user = em.merge(user);
        em.flush();
        
        return user;
        
    }
    
    /*Mithilfe dieser Methode wird überprüft, ob die eingegebenen Anmeldedaten in der Anwender-Twbelle vorhanden sind.*/
    public Anwender loginUser(String username, String userpassword){
        if(username == null || userpassword == null || username.isEmpty() || userpassword.isEmpty()){
            return null;
        } else {
            TypedQuery<Anwender> abfrage = em.createNamedQuery("Anwender.findByUserAndPassword", Anwender.class)
            .setParameter("username", username)
            .setParameter("userpassword", userpassword);
            
            if (abfrage.getResultList().isEmpty()) {
                return null;
            }
            
            return abfrage.getSingleResult();
        }
    }
    
    /*Diese Methode überprüft sämtliche Eingaben die der Anwender bei der Registrierung eingibt.
    Dabei werden sämtliche Designanforderung der [BA 1/SA 1] umgesetzt.
    
    Das eingegebene Passwort und die geforderte Wiederholung desPassworts dürfen nicht leer sein.
        Weiterhin müssen beide eingegebnen Passwörter übereinstimmen.
    
    Bei der Registrierung darf das Username-Feld nicht leer sein.
        In der inneren IF-ELSE-Verzweigung wird überprüft, ob der eingegebene Username in der Anwender-Tabelle vorhanden ist.
    
    Bei der Registrierung muss die eingebene Email validiert werden. Hierfür wird die checkMailPattern-Methode aufgerufen.
        Bei valider Eingaben wird weiterhin überprüft, ob die EMail in der Anwender-Tabelle bereits vorhanden ist.
    */
    
    public String registerValide(String username, String email, String passwort, String passwordRepeat){
        if (passwort.isEmpty() || passwordRepeat.isEmpty() || (!(passwort.equals(passwordRepeat))) ){
            return "PASSWORD NOT OK";
        }
        
        if ((!username.isEmpty())){
               TypedQuery<Anwender> abfrageUser = em.createNamedQuery("Anwender.findByName", Anwender.class)
                                              .setParameter("username", username);
               
               if (!(abfrageUser.getResultList().isEmpty())){
                   return "USERNAME REPEAT";
               }
               
        } else {
            return "USERNAME NOT NULL";
        }

        if(checkMailPattern(email)){
            TypedQuery<Anwender> abfrageEmail = em.createNamedQuery("Anwender.GetEmail", Anwender.class)
                                                .setParameter("email", email);
            
                if(abfrageEmail.getResultList().isEmpty()){
                    return "OK";
                }
                else{
                    return "EMAIL REPEAT";
                } 
        } else {
             return "EMAIL NOT OK";
        }
    }
    
    // Überprüfung des Mail-Formats mithilfe eines endlichen Automats
    public boolean checkMailPattern(String email){
        String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(EMAIL_REGEX);
    }
    
    //Anwender wird aus der ANWENDER-Tabelle geloescht
    public int deleteUser(String username){
        Query q = em.createNamedQuery("Anwender.deleteUser", Anwender.class).setParameter("username", username);
        return q.executeUpdate();
    }
    
    //Anwender über Username finde
    public Anwender findByName(String username){
        TypedQuery<Anwender> user = em.createNamedQuery("Anwender.findByName", Anwender.class).setParameter("username", username);
        return user.getSingleResult();
    }
    
    public void updateKategories(Anwender anwender, List<Kategorie> kategorien){
        anwender.getAnwender_kategorien().clear();
        em.setFlushMode(FlushModeType.AUTO);
        
        for (Kategorie k : kategorien) {
            k.getAnwender().add(anwender);
            anwender.getAnwender_kategorien().add(k);
            // em.persist(k);
            em.merge(k);
        }
        em.merge(anwender);
        em.flush();
    }
}