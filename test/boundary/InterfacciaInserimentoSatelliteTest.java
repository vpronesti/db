package boundary;

import bean.BeanSatellite;
import bean.BeanUtente;
import dao.SatelliteDao;
import dao.UtenteDao;
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
import static util.DBAccess.AMMINISTRATORE;
import static util.DBAccess.NONREGISTRATO;
import static util.DBAccess.REGISTRATO;

/**
 * test per il requisito funzionale n. 3.3
 */
@RunWith(value = Parameterized.class)
public class InterfacciaInserimentoSatelliteTest {
    private final boolean expected;
    private String userId;
    private String nome;
    private LocalDate primaOsservazione;
    private LocalDate termineOperazione;
    private String agenzia;

    public InterfacciaInserimentoSatelliteTest(boolean expected, 
            String userId, String nome, LocalDate primaOsservazione, 
            LocalDate termineOperazione, String agenzia) {
        this.expected = expected;
        this.userId = userId;
        this.nome = nome;
        this.primaOsservazione = primaOsservazione;
        this.termineOperazione = termineOperazione;
        this.agenzia = agenzia;
    }
    
    @Parameterized.Parameters
    public static Collection<Object[]> getTestParameters() {
        return Arrays.asList(new Object[][] {
            // satellite da inserire
            {true, AMMINISTRATORE, "sat", LocalDate.of(2016, Month.OCTOBER, 15), LocalDate.of(2016, Month.OCTOBER, 15), "a2"},
            // satellite gia' esistente
            {false, AMMINISTRATORE, "Herschel", LocalDate.of(2016, Month.OCTOBER, 15), LocalDate.of(2016, Month.OCTOBER, 15), "a2"},
            // satellite da inserire ma la data di fine e' prima dell'inizio
            {false, AMMINISTRATORE, "sat", LocalDate.of(2016, Month.OCTOBER, 15), LocalDate.of(2016, Month.OCTOBER, 3), "a2"},
            // la data di fine non e' obbligatoria
            {true, AMMINISTRATORE, "sat4", LocalDate.of(2016, Month.OCTOBER, 15), null, "a1"}, 
            
            // i seguenti input vengono inseriti da utenti non amministratori quindi i test falliscono
            {false, REGISTRATO, "sat", LocalDate.of(2016, Month.OCTOBER, 15), LocalDate.of(2016, Month.OCTOBER, 15), "a2"},
            {false, REGISTRATO, "Herschel", LocalDate.of(2016, Month.OCTOBER, 15), LocalDate.of(2016, Month.OCTOBER, 15), "a2"},
            {false, REGISTRATO, "sat", LocalDate.of(2016, Month.OCTOBER, 15), LocalDate.of(2016, Month.OCTOBER, 3), "a2"},
            {false, REGISTRATO, "sat4", LocalDate.of(2016, Month.OCTOBER, 15), null, "a1"},
            {false, NONREGISTRATO, "sat", LocalDate.of(2016, Month.OCTOBER, 15), LocalDate.of(2016, Month.OCTOBER, 15), "a2"},
            {false, NONREGISTRATO, "Herschel", LocalDate.of(2016, Month.OCTOBER, 15), LocalDate.of(2016, Month.OCTOBER, 15), "a2"},
            {false, NONREGISTRATO, "sat", LocalDate.of(2016, Month.OCTOBER, 15), LocalDate.of(2016, Month.OCTOBER, 3), "a2"},
            {false, NONREGISTRATO, "sat4", LocalDate.of(2016, Month.OCTOBER, 15), null, "a1"}
        });
    }
    
    @Test
    public void testRegistrazione() {
        InterfacciaInserimentoSatellite interfacciaInserimentoSatellite = 
                new InterfacciaInserimentoSatellite(userId);
        BeanSatellite beanSatellite = new BeanSatellite(nome, primaOsservazione, termineOperazione, agenzia);
        Connection conn = DBAccess.getInstance().getConnection();
        boolean azioneConsentita = UtenteDao.getInstance().queryEsistenzaUtente(conn, new BeanUtente(this.userId));
        DBAccess.getInstance().closeConnection(conn);
        boolean res = interfacciaInserimentoSatellite.inserisciSatellite(beanSatellite);
        if (res) {
            this.rimuoviModifica();
        }
        assertEquals("errore", res && azioneConsentita, expected);
    }
    
    private void rimuoviModifica() {
        Connection conn = DBAccess.getInstance().getConnection();
        SatelliteDao.getInstance().rimuoviSatellite(conn, 
                new BeanSatellite(nome, primaOsservazione, termineOperazione, agenzia));
        DBAccess.getInstance().closeConnection(conn);
    }
}
