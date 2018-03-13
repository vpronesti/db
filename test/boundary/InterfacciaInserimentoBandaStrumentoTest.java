package boundary;

import bean.BeanStrumento;
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
public class InterfacciaInserimentoBandaStrumentoTest {
    private final boolean expected;
    private String nome;
    private float banda;

    public InterfacciaInserimentoBandaStrumentoTest(boolean expected, 
            String nome, float banda) {
        this.expected = expected;
        this.nome = nome;
        this.banda = banda;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> getTestParameters() {
        return Arrays.asList(new Object[][] {
            // strumento non esistente, banda non esistente
            {false, "Str", new Float(3.141)},
            // strumento esistente banda non esistente
            {true, "MIPS", new Float(3.2)},
            // strumento non esistente banda esistente
            {false, "Str", new Float(3.6)},
            // strumento esistente banda esistente
            {true, "MIPS", new Float(3.6)}
        });
    }
    
    @Test
    public void testRegistrazione() {
        InterfacciaInserimentoBandaStrumento interfacciaInserimentoBandaStrumento = 
                new InterfacciaInserimentoBandaStrumento("a");
        BeanStrumento beanStrumento = new BeanStrumento(nome, banda);
        boolean res = interfacciaInserimentoBandaStrumento.inserisciBandaStrumento(beanStrumento);
        if (res) {
            this.rimuoviModifica();
        }
        assertEquals("errore", res, expected);
    }
    
    private void rimuoviModifica() {
        Connection conn = DBAccess.getInstance().getConnection();
        StrumentoDao.getInstance().rimuoviBandaStrumento(conn, 
                new BeanStrumento(nome, banda));
        DBAccess.getInstance().closeConnection(conn);
    }
}
