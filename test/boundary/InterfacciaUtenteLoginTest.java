package boundary;

import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class InterfacciaUtenteLoginTest {
    private final boolean expected;
    private final String userId;
    private final String password;
    
    @Parameters
    public static Collection<Object[]> getTestParameters() {
        return Arrays.asList(new Object[][] {
            // utente amministratore
            {true, "a", "a"},
            // utente registrato
            {true, "z", "z"},
            // utente non registrato
            {false, "w", "w"}
        });
    }
    
    public InterfacciaUtenteLoginTest(boolean expected, 
            String userId, String password) {
        this.expected = expected;
        this.userId = userId;
        this.password = password;       
    }
    
    @Test
    public void testLogin() {
        InterfacciaUtenteLogin interfacciaUtenteLogin = 
                new InterfacciaUtenteLogin(userId, password);
        boolean res = interfacciaUtenteLogin.logIn();
        assertEquals("errore nella definizione", res, expected);
    }
}
