package boundary;

import bean.BeanIdFilamento;
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
public class InterfacciaRicercaFilamentiRegioneOutputTest {
    private boolean expected;
    private float longCentroide;
    private float latiCentroide;
    private float dimensione;
    private TipoFigura tipoFigura;
    
    @Parameterized.Parameters
    public static Collection<Object[]> getTestParameters() {
        return Arrays.asList(new Object[][] {
            {true, new Float(5.0004370), new Float(0.084881000), new Float(0.5), TipoFigura.CERCHIO}
        });
    }
    
    public InterfacciaRicercaFilamentiRegioneOutputTest(boolean expected, 
                float longCentroide, float latiCentroide, float dimensione, 
                TipoFigura tipoFigura) {
        this.expected = expected;
        this.longCentroide = longCentroide;
        this.latiCentroide = latiCentroide;
        this.dimensione = dimensione;
        this.tipoFigura = tipoFigura;
    }
    
    private boolean internoRegione(float glon, float glat) {
        boolean res = true;
        if (tipoFigura == TipoFigura.CERCHIO) {
            if (distanza(glon, glat, this.longCentroide, 
                        this.latiCentroide) 
                        > this.dimensione)
                    res = false;
        } else {
            if (glon > this.longCentroide + this.dimensione / 2)
                res = false;
            if (glon < this.longCentroide - this.dimensione / 2)
                res = false;
            if (glat > this.latiCentroide + this.dimensione / 2)
                res = false;
            if (glat< this.latiCentroide - this.dimensione / 2)
                res = false;
        }
        return res;
    }
    
    private boolean controllaFilamentiRegione(BeanRispostaFilamenti beanRisposta) {
        boolean res = true;
        Connection conn = DBAccess.getInstance().getConnection();
        ContornoDao contornoDao = ContornoDao.getInstance();
        Iterator<Filamento> i = beanRisposta.getFilamenti().iterator();
        while (i.hasNext() && res) {
            Filamento f = i.next();
            BeanIdFilamento id = new BeanIdFilamento(f.getIdFil(), f.getSatellite());
            List<Contorno> puntiContorno = contornoDao.queryPuntiContornoFilamento(conn, id);
            Iterator<Contorno> iC = puntiContorno.iterator();
            while (iC.hasNext()) {
                Contorno c = iC.next();
                if (!this.internoRegione(c.getgLonCont(), c.getgLonCont())) {
                    res = false;
                    break;
                }
            }
        }
        DBAccess.getInstance().closeConnection(conn);
        return res;
    }
    
    @Test
    public void testRicercaFilamentiRegione() {
        InterfacciaRicercaFilamentiRegione interfacciaFilamentiRegione = 
                new InterfacciaRicercaFilamentiRegione("a");
        BeanRichiestaFilamentiRegione beanRichiesta = new
         BeanRichiestaFilamentiRegione(longCentroide, latiCentroide, 
                 dimensione, tipoFigura);
        BeanRispostaFilamenti beanRisposta = 
                interfacciaFilamentiRegione.ricercaFilamentiRegione(beanRichiesta);
        boolean res = this.controllaFilamentiRegione(beanRisposta);
        assertEquals("errore", res, expected);
    }
}