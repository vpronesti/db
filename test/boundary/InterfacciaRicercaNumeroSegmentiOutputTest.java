package boundary;

import bean.BeanIdFilamento;
import bean.BeanRichiestaNumeroSegmenti;
import bean.BeanRispostaFilamenti;
import dao.SegmentoDao;
import entity.Filamento;
import java.sql.Connection;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import util.DBAccess;
import static util.DBAccess.AMMINISTRATORE;
import static util.DBAccess.NONREGISTRATO;
import static util.DBAccess.REGISTRATO;

/**
 * test per il requisito funzionale n. 7
 */
@RunWith(value = Parameterized.class)
public class InterfacciaRicercaNumeroSegmentiOutputTest {
    private final boolean expected;
    private String userId;
    private int inizioIntervallo;
    private int fineIntervallo;
    
    @Parameterized.Parameters
    public static Collection<Object[]> getTestParameters() {
        return Arrays.asList(new Object[][] {
            {true, AMMINISTRATORE, 5, 8},
            {true, REGISTRATO, 5, 8},
            {false, NONREGISTRATO, 5, 8}
        });
    }
    
    public InterfacciaRicercaNumeroSegmentiOutputTest(boolean expected, 
                String userId, int inizioIntervallo, int fineIntervallo) {
        this.expected = expected;
        this.userId = userId;
        this.inizioIntervallo = inizioIntervallo;
        this.fineIntervallo = fineIntervallo;
    }
    
    /**
     * controlla che tutti i filamenti restituiti abbiano un 
     * numero di segmenti compreso nel range stabilito
     */    
    @Test
    public void testRicercaNumeroSegmenti() {
        InterfacciaRicercaNumeroSegmenti interfacciaNumeroSegmenti = 
                new InterfacciaRicercaNumeroSegmenti(userId);
        BeanRichiestaNumeroSegmenti beanRichiesta = new BeanRichiestaNumeroSegmenti(inizioIntervallo, fineIntervallo);
        BeanRispostaFilamenti beanRisposta = interfacciaNumeroSegmenti.ricercaNumeroSegmenti(beanRichiesta);
        boolean res;
        if (beanRisposta.isAzioneConsentita())
            res = this.controllaNumeroSegmenti(beanRisposta);
        else
            res = false;
        assertEquals("errore", res, expected);
    }
    
    private boolean controllaNumeroSegmenti(BeanRispostaFilamenti beanRisposta) {
        boolean res = true;
        Connection conn = DBAccess.getInstance().getConnection();
        SegmentoDao segmentoDao = SegmentoDao.getInstance();
        Iterator<Filamento> i = beanRisposta.getListaFilamenti().iterator();
        while (i.hasNext()) {
            Filamento f = i.next();
            BeanIdFilamento idFil = new BeanIdFilamento(f.getIdFil(), f.getSatellite());
            int numSegm = segmentoDao.queryNumeroSegmentiFilamento(conn, idFil);
            if (numSegm < inizioIntervallo) {
                res = false;
                break;
            }
            if (numSegm > fineIntervallo) {
                res = false;
                break;
            }
        }
        DBAccess.getInstance().closeConnection(conn);
        return res;
    }
}