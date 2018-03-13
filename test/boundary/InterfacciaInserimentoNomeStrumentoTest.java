package boundary;

import bean.BeanStrumentoSatellite;
import dao.StrumentoDao;
import java.sql.Connection;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import util.DBAccess;

/**
 * test per il requisito funzionale n. 3.4
 */
@RunWith(value = Parameterized.class)
public class InterfacciaInserimentoNomeStrumentoTest {
    private final boolean expected;
    private String nome;
    private String satellite;

    public InterfacciaInserimentoNomeStrumentoTest(boolean expected, 
            String nome, String satellite) {
        this.expected = expected;
        this.nome = nome;
        this.satellite = satellite;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> getTestParameters() {
        return Arrays.asList(new Object[][] {
            // strumento non esistente, satellite esistente
            {true, "Str", "Herschel"},
            // strumento esistente e satellite iserito, coppia gia' esistente
            {false, "SPIRE", "Herschel"},
            // strumento esistente e satellite iserito, coppia non esistente
            {true, "MIPS", "Herschel"},
            // strumento gia'inserito satellite non esistente
            {false, "MIPS", "satZZ"}
        });
    }
    
    @Test
    public void testRegistrazione() {
        InterfacciaInserimentoNomeStrumento interfacciaInserimentoStrumento = 
                new InterfacciaInserimentoNomeStrumento("a");
        BeanStrumentoSatellite beanStrumento = new BeanStrumentoSatellite(nome, satellite);
        boolean res = interfacciaInserimentoStrumento.inserisciNomeStrumento(beanStrumento);
        if (res) {
            this.rimuoviModifica();
        }
        assertEquals("errore", res, expected);
    }
    
    private void rimuoviModifica() {
        Connection conn = DBAccess.getInstance().getConnection();
        StrumentoDao.getInstance().rimuoviSatelliteStrumento(conn, 
                new BeanStrumentoSatellite(nome, satellite));
        DBAccess.getInstance().closeConnection(conn);
    }
}
