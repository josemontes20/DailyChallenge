package daily.test.beans;

import daily.beans.AnwenderBean;
import org.junit.Test;
import static org.junit.Assert.*;

/*
    Diese Testklasse testet die Methoden der AnwenderBean.
*/
public class Test_AnwenderBean {
    
    public Test_AnwenderBean() {
    }
    
    /**
     * Test zur Überprüfung des korrekten Verhaltens der checkMailPattern()-Methode.
     */
    @Test
    public void testCheckMailPattern() throws Exception
    {
        System.out.println("Überprüfung der E-Mail-Validierungs-Methode!(AnwenderBean)");
        // Test-Kandidat
        AnwenderBean anwBean = new AnwenderBean();
        
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
    
}
