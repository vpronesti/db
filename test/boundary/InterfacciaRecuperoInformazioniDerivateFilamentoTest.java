package boundary;

import bean.BeanIdFilamento;
import bean.BeanInformazioniFilamento;
import bean.BeanUtente;
import dao.ContornoDao;
import dao.UtenteDao;
import entity.Contorno;
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
import static util.UserId.AMMINISTRATORE;
import static util.UserId.NONREGISTRATO;
import static util.UserId.REGISTRATO;

/**
 * test per il requisito funzionale n. 8
 */
@RunWith(value = Parameterized.class)
public class InterfacciaRecuperoInformazioniDerivateFilamentoTest {
    private boolean expected;
    private String userId;
    private int id;
    private String satellite;
    
    @Parameterized.Parameters
    public static Collection<Object[]> getTestParameters() {
        return Arrays.asList(new Object[][] {
            {true, REGISTRATO, 45, "Herschel"},
            {true, AMMINISTRATORE, 45, "Herschel"},
            {false, NONREGISTRATO, 45, "Herschel"}
        });
    }
    
    public InterfacciaRecuperoInformazioniDerivateFilamentoTest(boolean expected,
                String userId, int id, String satellite) {
        this.expected = expected;
        this.userId = userId;
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
            if (beanFil.getgLatCentroide() != latiMedia || beanFil.getgLonCentroide()!= longMedia)
                res = false;
        }
        
        DBAccess.getInstance().closeConnection(conn);
        return res;
    }
    
    @Test
    public void testRicercaFilamenti() {
        InterfacciaRecuperoInformazioniDerivateFilamento interfacciaFilamento = 
                new InterfacciaRecuperoInformazioniDerivateFilamento(userId);
        BeanInformazioniFilamento beanRichiesta = new
         BeanInformazioniFilamento(id, satellite);
        Connection conn = DBAccess.getInstance().getConnection();
        boolean azioneConsentita = UtenteDao.getInstance().queryEsistenzaUtente(conn, new BeanUtente(this.userId));
        DBAccess.getInstance().closeConnection(conn);
        boolean res = 
                interfacciaFilamento.recuperaInfoFilamento(beanRichiesta);
        boolean chk = this.controllaFilamento(beanRichiesta);
        assertEquals("errore", res == chk && azioneConsentita, expected);
    }
}