package boundary;

import bean.BeanStrumentoSatellite;
import bean.BeanUtente;
import dao.StrumentoDao;
import dao.UtenteDao;
import java.sql.Connection;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import util.DBAccess;
import static util.DBAccess.AMMINISTRATORE;
import static util.DBAccess.NONREGISTRATO;
import static util.DBAccess.REGISTRATO;

/**
 * test per il requisito funzionale n. 3.4
 */
@RunWith(value = Parameterized.class)
public class InterfacciaInserimentoNomeStrumentoTest {
    private final boolean expected;
    private String userId;
    private String nome;
    private String satellite;

    public InterfacciaInserimentoNomeStrumentoTest(boolean expected, 
            String userId, String nome, String satellite) {
        this.expected = expected;
        this.userId = userId;
        this.nome = nome;
        this.satellite = satellite;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> getTestParameters() {
        return Arrays.asList(new Object[][] {
            // strumento non esistente, satellite esistente
            {true, AMMINISTRATORE, "Str", "Herschel"},
            // strumento esistente e satellite iserito, coppia gia' esistente
            {false, AMMINISTRATORE, "SPIRE", "Herschel"},
            // strumento esistente e satellite iserito, coppia non esistente
            {true, AMMINISTRATORE, "MIPS", "Herschel"},
            // strumento gia'inserito satellite non esistente
            {false, AMMINISTRATORE, "MIPS", "satZZ"},
            
            {false, REGISTRATO, "Str", "Herschel"},
            {false, REGISTRATO, "SPIRE", "Herschel"},
            {false, REGISTRATO, "MIPS", "Herschel"},
            {false, REGISTRATO, "MIPS", "satZZ"},
            {false, NONREGISTRATO, "Str", "Herschel"},
            {false, NONREGISTRATO, "SPIRE", "Herschel"},
            {false, NONREGISTRATO, "MIPS", "Herschel"},
            {false, NONREGISTRATO, "MIPS", "satZZ"}
        });
    }
    
    @Test
    public void testRegistrazione() {
        InterfacciaInserimentoNomeStrumento interfacciaInserimentoStrumento = 
                new InterfacciaInserimentoNomeStrumento(userId);
        BeanStrumentoSatellite beanStrumento = new BeanStrumentoSatellite(nome, satellite);
        Connection conn = DBAccess.getInstance().getConnection();
        boolean azioneConsentita = UtenteDao.getInstance().queryEsistenzaUtente(conn, new BeanUtente(this.userId));
        DBAccess.getInstance().closeConnection(conn);
        boolean res = interfacciaInserimentoStrumento.inserisciNomeStrumento(beanStrumento);
        if (res) {
            this.rimuoviModifica();
        }
        assertEquals("errore", res && azioneConsentita, expected);
    }
    
    private void rimuoviModifica() {
        Connection conn = DBAccess.getInstance().getConnection();
        StrumentoDao.getInstance().rimuoviSatelliteStrumento(conn, 
                new BeanStrumentoSatellite(nome, satellite));
        DBAccess.getInstance().closeConnection(conn);
    }
}
