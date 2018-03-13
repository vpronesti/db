package boundary;

import bean.BeanSatellite;
import dao.SatelliteDao;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import util.DBAccess;

/**
 * test per il requisito funzionale n. 3.3
 */
@RunWith(value = Parameterized.class)
public class InterfacciaInserimentoSatelliteTest {
    private final boolean expected;
    private String nome;
    private LocalDate primaOsservazione;
    private LocalDate termineOperazione;
    private String agenzia;

    public InterfacciaInserimentoSatelliteTest(boolean expected, 
            String nome, LocalDate primaOsservazione, 
            LocalDate termineOperazione, String agenzia) {
        this.expected = expected;
        this.nome = nome;
        this.primaOsservazione = primaOsservazione;
        this.termineOperazione = termineOperazione;
        this.agenzia = agenzia;
    }
    
    @Parameterized.Parameters
    public static Collection<Object[]> getTestParameters() {
        return Arrays.asList(new Object[][] {
            // satellite da inserire
            {true, "sat", LocalDate.of(2016, Month.OCTOBER, 15), LocalDate.of(2016, Month.OCTOBER, 15), "a2"},
            // satellite gia' esistente
            {false, "Herschel", LocalDate.of(2016, Month.OCTOBER, 15), LocalDate.of(2016, Month.OCTOBER, 15), "a2"},
            // satellite da inserire ma la data di fine e' prima dell'inizio
            {false, "sat", LocalDate.of(2016, Month.OCTOBER, 15), LocalDate.of(2016, Month.OCTOBER, 3), "a2"},
            // la data di fine non e' obbligatoria
            {true, "sat4", LocalDate.of(2016, Month.OCTOBER, 15), null, "a1"}
        });
    }
    
    @Test
    public void testRegistrazione() {
        InterfacciaInserimentoSatellite interfacciaInserimentoSatellite = 
                new InterfacciaInserimentoSatellite("a");
        BeanSatellite beanSatellite = new BeanSatellite(nome, primaOsservazione, termineOperazione, agenzia);
        boolean res = interfacciaInserimentoSatellite.inserisciSatellite(beanSatellite);
        if (res) {
            this.rimuoviModifica();
        }
        assertEquals("errore", res, expected);
    }
    
    private void rimuoviModifica() {
        Connection conn = DBAccess.getInstance().getConnection();
        SatelliteDao.getInstance().rimuoviSatellite(conn, 
                new BeanSatellite(nome, primaOsservazione, termineOperazione, agenzia));
        DBAccess.getInstance().closeConnection(conn);
    }
}
