package boundary;

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

/**
 * test per il requisito funzionale n. 7
 */
@RunWith(value = Parameterized.class)
public class InterfacciaRicercaNumeroSegmentiTestOutput {
    private final boolean expected;
    private int inizioIntervallo;
    private int fineIntervallo;
    
    @Parameterized.Parameters
    public static Collection<Object[]> getTestParameters() {
        return Arrays.asList(new Object[][] {
            {true, 5, 8}
        });
    }
    
    public InterfacciaRicercaNumeroSegmentiTestOutput(boolean expected, 
                int inizioIntervallo, int fineIntervallo) {
        this.expected = expected;
        this.inizioIntervallo = inizioIntervallo;
        this.fineIntervallo = fineIntervallo;
    }
    
    private boolean controllaNumeroSegmenti(BeanRispostaFilamenti beanRisposta) {
        boolean res = true;
        Connection conn = DBAccess.getInstance().getConnection();
        SegmentoDao segmentoDao = SegmentoDao.getInstance();
        Iterator<Filamento> i = beanRisposta.getFilamenti().iterator();
        while (i.hasNext()) {
            int idFil = i.next().getIdFil();
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
    
    @Test
    public void testRicercaNumeroSegmenti() {
        InterfacciaRicercaNumeroSegmenti interfacciaNumeroSegmenti = 
                new InterfacciaRicercaNumeroSegmenti("a");
        BeanRichiestaNumeroSegmenti beanRichiesta = new BeanRichiestaNumeroSegmenti(inizioIntervallo, fineIntervallo);
        BeanRispostaFilamenti beanRisposta = interfacciaNumeroSegmenti.ricercaNumeroSegmenti(beanRichiesta);
        boolean res = this.controllaNumeroSegmenti(beanRisposta);
        assertEquals("errore", res, expected);
    }
}