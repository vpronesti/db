package boundary;

import bean.BeanStrumento;
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
public class InterfacciaInserimentoBandaStrumentoTest {
    private final boolean expected;
    private String userId;
    private String nome;
    private double banda;

    public InterfacciaInserimentoBandaStrumentoTest(boolean expected, 
            String userId, String nome, double banda) {
        this.expected = expected;
        this.userId = userId;
        this.nome = nome;
        this.banda = banda;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> getTestParameters() {
        return Arrays.asList(new Object[][] {
            // strumento non esistente, banda non esistente
            {false, AMMINISTRATORE, "Str", new Double(3.141)},
            // strumento esistente banda non esistente
            {true, AMMINISTRATORE, "MIPS", new Double(3.2)},
            // strumento non esistente banda esistente
            {false, AMMINISTRATORE, "Str", new Double(3.6)},
            // strumento esistente banda esistente
            {true, AMMINISTRATORE, "MIPS", new Double(3.6)},
            // coppia strumento banda gia' esistente
            {false, AMMINISTRATORE, "MIPS", new Double(24)},
            
            {false, REGISTRATO, "Str", new Double(3.141)},
            {false, REGISTRATO, "MIPS", new Double(3.2)},
            {false, REGISTRATO, "Str", new Double(3.6)},
            {false, REGISTRATO, "MIPS", new Double(3.6)},
            {false, NONREGISTRATO, "Str", new Double(3.141)},
            {false, NONREGISTRATO, "MIPS", new Double(3.2)},
            {false, NONREGISTRATO, "Str", new Double(3.6)},
            {false, NONREGISTRATO, "MIPS", new Double(3.6)}
        });
    }
    
    @Test
    public void testRegistrazione() {
        InterfacciaInserimentoBandaStrumento interfacciaInserimentoBandaStrumento = 
                new InterfacciaInserimentoBandaStrumento(userId);
        BeanStrumento beanStrumento = new BeanStrumento(nome, banda);
        Connection conn = DBAccess.getInstance().getConnection();
        boolean azioneConsentita = UtenteDao.getInstance().queryEsistenzaAmministratore(conn, new BeanUtente(this.userId));
        DBAccess.getInstance().closeConnection(conn);
        boolean res = interfacciaInserimentoBandaStrumento.inserisciBandaStrumento(beanStrumento);
        if (res) {
            this.rimuoviModifica();
        }
        assertEquals("errore", res && azioneConsentita, expected);
    }
    
    private void rimuoviModifica() {
        Connection conn = DBAccess.getInstance().getConnection();
        StrumentoDao.getInstance().rimuoviBandaStrumento(conn, 
                new BeanStrumento(nome, banda));
        DBAccess.getInstance().closeConnection(conn);
    }
}
