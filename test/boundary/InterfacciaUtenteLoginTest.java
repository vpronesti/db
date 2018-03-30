package boundary;

import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import static util.DBAccess.AMMINISTRATORE;
import static util.DBAccess.NONREGISTRATO;
import static util.DBAccess.REGISTRATO;

/**
 * test per il requisito funzionale n. 1
 */
@RunWith(value = Parameterized.class)
public class InterfacciaUtenteLoginTest {
    private final boolean expected;
    private final String userId;
    private final String password;
    
    @Parameters
    public static Collection<Object[]> getTestParameters() {
        return Arrays.asList(new Object[][] {
            // utente amministratore, la password coincide con l'user
            {true, AMMINISTRATORE, AMMINISTRATORE},
            {false, AMMINISTRATORE, REGISTRATO},
            
            // utente registrato, la password coincide con l'user
            {true, REGISTRATO, REGISTRATO},
            {false, REGISTRATO, AMMINISTRATORE},
            // utente non registrato
            {false, NONREGISTRATO, NONREGISTRATO}
        });
    }
    
    public InterfacciaUtenteLoginTest(boolean expected, 
            String userId, String password) {
        this.expected = expected;
        this.userId = userId;
        this.password = password;       
    }
    
    
    /**
     * controlla se agli utenti di tipo amministratore, 
     * registrato o non registato e' consentito o meno l'accesso
     */
    @Test
    public void testLogin() {
        InterfacciaUtenteLogin interfacciaUtenteLogin = 
                new InterfacciaUtenteLogin(userId, password);
        boolean res = interfacciaUtenteLogin.logIn();
        assertEquals("errore", res, expected);
    }
}
