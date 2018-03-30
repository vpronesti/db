package boundary;

import bean.BeanUtente;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static util.DBAccess.AMMINISTRATORE;
import static util.DBAccess.NONREGISTRATO;
import static util.DBAccess.REGISTRATO;

/**
 * test per il requisito funzionale n. 2 e 3.2
 * per eseguire piu' di una volta il test occorre rimuovere l'utente creato
 */
@RunWith(value = Parameterized.class)
public class InterfacciaRegistrazioneUtenteTest {
    private final boolean expected;
    private String userIdAmministratore;
    private String nome;
    private String cognome;
    private String userId;
    private String password;
    private String email;
    private String tipo;
    
    @Parameterized.Parameters
    public static Collection<Object[]> getTestParameters() {
        return Arrays.asList(new Object[][] {
            // userId meno di 6 caratteri
            {false, NONREGISTRATO, "u1", "u1", "utent", "password", "email1", "Registrato"},
            // password meno di 6 caratteri
            {false, NONREGISTRATO, "u1", "u1", "utente", "pass", "email1", "Registrato"},
            // tipo utente diverso da registrato o amministratore
            {false, NONREGISTRATO, "u1", "u1", "utente", "password", "email1", "Registra"},
            // utente definibile ma un utente non amministratore non puo' inserire un nuovo utente
            {false, NONREGISTRATO, "u1", "u1", "utente", "password", "email", "Registrato"},
            // utente gia' definito
            {false, NONREGISTRATO, "u1", "u1", "utente", "password", "email", "Registrato"},
            
            // userId meno di 6 caratteri
            {false, REGISTRATO, "u1", "u1", "utent", "password", "email1", "Registrato"},
            // password meno di 6 caratteri
            {false, REGISTRATO, "u1", "u1", "utente", "pass", "email1", "Registrato"},
            // tipo utente diverso da registrato o amministratore
            {false, REGISTRATO, "u1", "u1", "utente", "password", "email1", "Registra"},
            // utente definibile ma un utente non amministratore non puo' inserire un nuovo utente
            {false, REGISTRATO, "u1", "u1", "utente", "password", "email", "Registrato"},
            // utente gia' definito
            {false, REGISTRATO, "u1", "u1", "utente", "password", "email", "Registrato"},
            
            
            // si deve inserire il nome
            {false, AMMINISTRATORE, "", "u1", "utente", "password", "email1", "Registrato"},
            // si deve inserire il cognome
            {false, AMMINISTRATORE, "u1", "", "utente", "password", "email1", "Registrato"},
            // inserire l'userId
            {false, AMMINISTRATORE, "u1", "u1", "", "password", "email1", "Registrato"},
            // inserire la password
            {false, AMMINISTRATORE, "u1", "u1", "utente", "", "email1", "Registrato"},
            // inserire l'email
            {false, AMMINISTRATORE, "u1", "u1", "utente", "password", "", "Registrato"},
            // inserire il tipo di utente
            {false, AMMINISTRATORE, "u1", "u1", "utente", "password", "email1", ""},
            
            // userId meno di 6 caratteri
            {false, AMMINISTRATORE, "u1", "u1", "utent", "password", "email1", "Registrato"},
            // password meno di 6 caratteri
            {false, AMMINISTRATORE, "u1", "u1", "utente", "pass", "email1", "Registrato"},
            // tipo utente diverso da registrato o amministratore
            {false, AMMINISTRATORE, "u1", "u1", "utente", "password", "email1", "Registra"},
            // utente definibile
            {true, AMMINISTRATORE, "u1", "u1", "utente", "password", "email", "Registrato"},
            // utente gia' definito
            {false, AMMINISTRATORE, "u1", "u1", "utente", "password", "email", "Registrato"}
            

        });
    }
    
    public InterfacciaRegistrazioneUtenteTest(boolean expected, 
            String userIdAmminitratore, String nome, String cognome, 
            String userId, String password, String email, String tipo) {
        this.expected = expected;
        this.userIdAmministratore = userIdAmminitratore;
        this.nome = nome;
        this.cognome = cognome;
        this.userId = userId;
        this.password = password;       
        this.email = email;
        this.tipo = tipo;
    }
    
    @Test
    public void testRegistrazione() {
        InterfacciaRegistrazioneUtente interfacciaRegistrazioneUtente = 
                new InterfacciaRegistrazioneUtente(userIdAmministratore);
        BeanUtente beanUtente = new BeanUtente(nome, cognome, userId, password, email, tipo);
        boolean res = interfacciaRegistrazioneUtente.definizioneUtente(beanUtente);
        assertEquals("errore", res, expected);
    }
}
