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
        System.out.println("Überprüfung der E-Mail-Validierungs-Methode!(AnwenderBean)");
        
        // Test Eingabe_1, Erwarteter Rückgabewert = true
        String mail1 = "test@test.com";
        // Test 1
        boolean expResult1 = true;
        boolean result1 = anwBean.checkMailPattern(mail1);
        assertEquals(expResult1, result1);
        
        // Test Eingabe_2, Erwarteter Rückgabewert = true
        String mail2 = "test.2@test.test.com";
        // Test 2
        boolean expResult2 = true;
        boolean result2 = anwBean.checkMailPattern(mail2);
        assertEquals(expResult2, result2);
        
        // Test Eingabe_3, Erwarteter Rückgabewert = false
        String mail3 = "test.test.com";
        // Test 3
        boolean expResult3 = false;
        boolean result3 = anwBean.checkMailPattern(mail3);
        assertEquals(expResult3, result3);
        
        System.out.println("Überprüfung der E-Mail-Validierungs-Methode abgeschlossen!");
    }
    
    
    /**
     * Test zur Überprüfung des korrekten Verhaltens der validateRegistration()-Methode.
     * @throws java.lang.Exception
     */
    @Test
    public void testvalidateRegistration() throws Exception{
        System.out.println("Start Test validateRegistration!");
        
        //Test Eingabe_1, Erwarteter Wert: USERNAME NOT NULL
        String user_1 = "";
        String email_1="test@test.de";
        String password_1 = "test";
        String password_2 = "test";
        
        String expResult1 = "USERNAME NOT NULL";
        String result1 = anwBean.validateRegistration(user_1, email_1, password_1, password_2);
        assertEquals(expResult1, result1);
        
        //Test Eingabe_2, Erwarteter Wert: PASSWORD NOT OK
        String user_2="Test";
        String email_2="test@test.de";
        String password_3 = "test";
        String password_4 = "123";
        
        String expResult_2 = "PASSWORD NOT OK";
        String result_2 = anwBean.validateRegistration(user_2, email_2, password_3, password_4);
        assertEquals(expResult_2, result_2); 
        
        System.out.println("Ende Test validateRegistration!");
    }
}
