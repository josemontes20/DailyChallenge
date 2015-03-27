package daily.test.beans;

import daily.beans.AnwenderBean;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/*
    Diese Testklasse testet die Methoden der AnwenderBean.
*/
public class Test_AnwenderBean {
    static AnwenderBean anwBean;
    
    @BeforeClass
    public static void initTest(){
        // Test-Kandidat
        anwBean = new AnwenderBean();
    }
    
    /**
     * Test zur Überprüfung des korrekten Verhaltens der checkMailPattern()-Methode.
     * @throws java.lang.Exception
     */
    @Test
    public void testCheckMailPattern() throws Exception{
        System.out.println("\n" + "Start Ueberpruefung der E-Mail-Validierungs-Methode!" + "\n");
        
        // Test Eingabe_1, Erwarteter Rückgabewert = true
        String mail1 = "test@test.com";
        // Test 1
        boolean result1 = anwBean.checkMailPattern(mail1);
        assertTrue(result1);
        System.out.println("Ueberprüfung der E-Mail-Validierungs-Methode: Test 1 erfolgreich!");
        
        // Test Eingabe_2, Erwarteter Rückgabewert = true
        String mail2 = "test.2@test.test.com";
        // Test 2
        boolean result2 = anwBean.checkMailPattern(mail2);
        assertTrue(result2);
        System.out.println("Ueberprüfung der E-Mail-Validierungs-Methode: Test 2 erfolgreich!");
         
        // Test Eingabe_3, Erwarteter Rückgabewert = false
        String mail3 = "test.test.com";
        // Test 3
        boolean result3 = anwBean.checkMailPattern(mail3);
        assertFalse(result3);
        System.out.println("Ueberprüfung der E-Mail-Validierungs-Methode: Test 3 erfolgreich!");
        
        System.out.println("\n" + "Ende Ueberpruefung der E-Mail-Validierungs-Methode!");
    }
    
    
    /**
     * Test zur Überprüfung des korrekten Verhaltens der validateRegistration()-Methode.
     * @throws java.lang.Exception
     */
    @Test
    public void testvalidateRegistration() throws Exception{
        System.out.println("\n" + "Start Ueberpruefung validateRegistration!" + "\n");
        
        //Test Eingabe_1, Erwarteter Wert: USERNAME NOT NULL
        String user_1 = "";
        String email_1="test@test.de";
        String password_1 = "test";
        String password_2 = "test";
        
        String expResult1 = "USERNAME NOT NULL";
        String result1 = anwBean.validateRegistration(user_1, email_1, password_1, password_2);
        assertEquals(expResult1, result1);
        System.out.println("Ueberprüfung der validateRegistration-Methode: Test 1 erfolgreich!");
        
        //Test Eingabe_2, Erwarteter Wert: PASSWORD NOT OK
        String user_2="Test";
        String email_2="test@test.de";
        String password_3 = "test";
        String password_4 = "123";
        
        String expResult_2 = "PASSWORD NOT OK";
        String result_2 = anwBean.validateRegistration(user_2, email_2, password_3, password_4);
        assertEquals(expResult_2, result_2); 
        System.out.println("Ueberprüfung der validateRegistration-Methode: Test 2 erfolgreich!");
        
        System.out.println("\n" + "Ende Ueberpruefung validateRegistration!");
    }
}
