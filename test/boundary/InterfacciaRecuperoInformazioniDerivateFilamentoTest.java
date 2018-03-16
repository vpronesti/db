package boundary;

import bean.BeanIdFilamento;
import bean.BeanInformazioniFilamento;
import bean.BeanRichiestaFilamentiRegione;
import bean.BeanRispostaFilamenti;
import dao.ContornoDao;
import entity.Contorno;
import entity.Filamento;
import entity.TipoFigura;
import java.sql.Connection;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import util.DBAccess;
import static util.DistanzaEuclidea.distanza;

/**
 * test per il requisito funzionale n. 8
 */
@RunWith(value = Parameterized.class)
public class InterfacciaRecuperoInformazioniDerivateFilamentoTest {
    private boolean expected;
    private int id;
    private String satellite;
    
    @Parameterized.Parameters
    public static Collection<Object[]> getTestParameters() {
        return Arrays.asList(new Object[][] {
            {true, 45, "Herschel"}
        });
    }
    
    public InterfacciaRecuperoInformazioniDerivateFilamentoTest(boolean expected, 
                int id, String satellite) {
        this.expected = expected;
        this.id = id;
        this.satellite = satellite;
    }
    
    private boolean controllaFilamento(BeanInformazioniFilamento beanFil) {
        boolean res = true;
        Connection conn = DBAccess.getInstance().getConnection();
        ContornoDao contornoDao = ContornoDao.getInstance();
        double longMedia = 0;
        double latiMedia = 0;
        List<Contorno> puntiContorno = contornoDao.queryPuntiContornoFilamento(conn, new BeanIdFilamento(beanFil.getIdFil(), beanFil.getSatellite()));
        if (puntiContorno.size() == 0) {
            if (beanFil.getMaxGLatContorno() != 0 || beanFil.getMaxGLonContorno() != 0)
                res = false;
        } else {

            Iterator<Contorno> i = puntiContorno.iterator();
            while (i.hasNext()) {
                Contorno c = i.next();
                longMedia += c.getgLonCont();
                latiMedia += c.getgLatCont();
            }
            longMedia /= (double) puntiContorno.size();
            latiMedia /= (double) puntiContorno.size();
            if (beanFil.getMaxGLatContorno() != latiMedia || beanFil.getMaxGLonContorno() != longMedia)
                res = false;
        }
        
        DBAccess.getInstance().closeConnection(conn);
        if (!res) {
            System.out.println("valori java: long = " + longMedia + " lat = " + latiMedia);
            System.out.println("valori db: long = " + beanFil.getgLonCentroide() + " lat = " + beanFil.getMaxGLatContorno());
        }
        return res;
    }
    
    @Test
    public void testRicercaFilamenti() {
        InterfacciaRecuperoInformazioniDerivateFilamento interfacciaFilamento = 
                new InterfacciaRecuperoInformazioniDerivateFilamento("a");
        BeanInformazioniFilamento beanRichiesta = new
         BeanInformazioniFilamento(id, satellite);
        boolean res = 
                interfacciaFilamento.recuperaInfoFilamento(beanRichiesta);
        boolean chk = this.controllaFilamento(beanRichiesta);
        assertEquals("errore", res == chk, expected);
    }
}