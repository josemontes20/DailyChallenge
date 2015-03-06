package Controller;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import model.Anwender;

/**
 * @author José Montes
 */

@Stateless
@LocalBean
public class UserControl{

    @PersistenceContext (name = "DailyDB")
    private EntityManager em;
    
    public Anwender createUser(String username, String userpassword, String email, int score){
       
        em.setFlushMode(FlushModeType.AUTO);   
        Anwender user = new Anwender(username, userpassword, email, score);
        em.persist(user);
        user = em.merge(user);
        em.flush();
        return user;
        
    }
    
    public Anwender loginUser(String username, String userpassword){
        
        if(username == null || userpassword == null || username.isEmpty() || userpassword.isEmpty()){
            return null;
        }else{
            TypedQuery<Anwender> abfrage = em.createNamedQuery("Anwender.findByUserAndPassword", Anwender.class)
            .setParameter("username", username)
            .setParameter("userpassword", userpassword);
            
            if (abfrage.getResultList().isEmpty())
        {
            return null;
        }
        return abfrage.getSingleResult();
        }
    }
    
    public String registerValide(String username, String email, String passwort, String passwordRepeat){
        if ((passwort != null && passwordRepeat != null) && passwort.equals(passwordRepeat) && (!passwort.equals(""))){
           
            if (username != null && (!username.equals(""))){
               TypedQuery<Anwender> abfrageUser = em.createNamedQuery("Anwender.findByUser", Anwender.class)
                                              .setParameter("username", username);
               
               if (abfrageUser.getResultList().isEmpty()){
                   
                   if(checkMailPattern(email)){
                       TypedQuery<Anwender> abfrageEmail = em.createNamedQuery("Anwender.GetEmail", Anwender.class)
                                              .setParameter("email", email);
                       if(abfrageEmail.getResultList().isEmpty()){
                           return "OK";
                       }else{
                           return "EMAIL REPEAT";
                       } 
                   }else{
                       return "EMAIL NOT OK";
                   }
               }else{
                   return "USERNAME REPEAT";
               }
           }else{
               return "USERNAME NOT NULL";
           }
        }else{
            return "PASSWORD NOT OK";
        }
    }
    
    /** Überprüfung des Mail-Formats. */
    protected boolean checkMailPattern(String login)
    {
        String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return login.matches(EMAIL_REGEX);
    }
}
