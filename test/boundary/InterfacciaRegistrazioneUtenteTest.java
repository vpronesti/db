package boundary;

import bean.BeanUtente;
import dao.UtenteDao;
import java.sql.Connection;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import util.DBAccess;

/**
 * test per il requisito funzionale n. 2 e 3.2
 * per eseguire piu' di una volta il test occore rimuovere l'utente creato
 */
@RunWith(value = Parameterized.class)
public class InterfacciaRegistrazioneUtenteTest {
    private final boolean expected;
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
            {false, "u1", "u1", "utent", "password", "email1", "Registrato"},
            // password meno di 6 caratteri
            {false, "u1", "u1", "utente", "pass", "email1", "Registrato"},
            // tipo utente diverso da registrato o amministratore
            {false, "u1", "u1", "utente", "password", "email1", "Registra"},
            // utente definibile
            {true, "u1", "u1", "utente", "password", "email", "Registrato"},
            // utente gia' definito
            {false, "u1", "u1", "utente", "password", "email", "Registrato"}
        });
    }
    
    public InterfacciaRegistrazioneUtenteTest(boolean expected, String nome, 
            String cognome, String userId, String password, String email, 
            String tipo) {
        this.expected = expected;
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
                new InterfacciaRegistrazioneUtente("a");
        BeanUtente beanUtente = new BeanUtente(nome, cognome, userId, password, email, tipo);
        boolean res = interfacciaRegistrazioneUtente.definizioneUtente(beanUtente);
//        if (res) {
//            this.rimuoviModifica();
//        }
        assertEquals("errore", res, expected);
    }
    
//    private void rimuoviModifica() {
//        Connection conn = DBAccess.getInstance().getConnection();
//        UtenteDao.getInstance().rimuoviUtente(conn, 
//                new BeanUtente(nome, cognome, userId, password, email, tipo));
//        DBAccess.getInstance().closeConnection(conn);
//    }
}
