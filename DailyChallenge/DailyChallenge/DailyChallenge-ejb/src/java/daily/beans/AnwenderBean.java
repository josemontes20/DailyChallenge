package daily.beans;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import daily.model.Kategorie;
import daily.model.Anwender;

/*
    Diese Bean verwaltet die Anwender-Entitys.
    - Registrierung, Anmeldung, Löschen und allgemeine Validierung (Passwort, E-Mail)
*/
@Stateless
@LocalBean
public class AnwenderBean{

    @PersistenceContext (name = "DailyDB")
    private EntityManager em;
    
    // Diese Methode ist für das Anlegen eines Anwenders in der Anwender-Tabelle verantwortlich.
    public Anwender createUser(String username, String userpassword, String email, int score){
        Anwender user = new Anwender(username, userpassword, email, score);

        em.setFlushMode(FlushModeType.AUTO);   
        em.persist(user);
        user = em.merge(user);
        em.flush();
        
        return user;
        
    }
    
    // Mithilfe dieser Methode wird überprüft, ob die eingegebenen Anmeldedaten in der Anwender-Tabelle vorhanden sind.
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
    
    // Diese Methode überprüft sämtliche Eingaben die der Anwender bei der Registrierung eingibt.
    public String registerValide(String username, String email, String passwort, String passwordRepeat){
        
        // Das eingegebene Passwort und die geforderte Wiederholung des Passworts dürfen nicht leer sein.
        // Weiterhin müssen beide eingegebnen Passwörter übereinstimmen.
        if (passwort.isEmpty() || passwordRepeat.isEmpty() || (!(passwort.equals(passwordRepeat))) ){
            return "PASSWORD NOT OK";
        }
        
        // Bei der Registrierung darf das Anwendername-Feld nicht leer sein.
        if ((!username.isEmpty())){
               
            // Überprüfen, ob der Anwendername bereits vorhanden ist.
            TypedQuery<Anwender> abfrageUser = em.createNamedQuery("Anwender.findByName", Anwender.class)
                                                    .setParameter("username", username);
        
               if (!(abfrageUser.getResultList().isEmpty())){
                   return "USERNAME REPEAT";
               }
               
        } else {
            return "USERNAME NOT NULL";
        }

        // E-Mail validieren
        if(checkMailPattern(email)){
            
            // Überprüfen, ob E-Mail-Adresse bereits vorhanden ist.
            TypedQuery<Anwender> abfrageEmail = em.createNamedQuery("Anwender.GetEmail", Anwender.class)
                                                .setParameter("email", email);
            
                if(abfrageEmail.getResultList().isEmpty()){
                    return "OK"; // <-- Komplette Registrierung war Ok
                }
                else{
                    return "EMAIL REPEAT";
                } 
                
        } else {
             return "EMAIL NOT OK";
        }
    }
    
    // Überprüfung des E-Mail-Formats.
    public boolean checkMailPattern(String email){
        String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(EMAIL_REGEX);
    }
    
    // Anwender wird aus der Anwender-Tabelle geloescht.
    public int deleteUser(Anwender a){
        Query q = em.createNamedQuery("Anwender.deleteUser", Anwender.class).setParameter("username", a.getUsername());
        return q.executeUpdate();
    }
    
    // Anwender über Anwendernamen finden.
    public Anwender findByName(Anwender a){
        TypedQuery<Anwender> user = em.createNamedQuery("Anwender.findByName", Anwender.class).setParameter("username", a.getUsername());
        return user.getResultList().get(0);
    }
    
    // Die abonnierten Kategorien eines Anwenders aktualisieren.
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