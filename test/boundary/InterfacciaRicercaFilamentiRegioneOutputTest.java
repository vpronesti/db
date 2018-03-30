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
import static util.DBAccess.AMMINISTRATORE;
import static util.DBAccess.NONREGISTRATO;
import static util.DBAccess.REGISTRATO;
import static util.DistanzaEuclidea.distanza;

/**
 * test per il requisito funzionale n. 8
 */
@RunWith(value = Parameterized.class)
public class InterfacciaRicercaFilamentiRegioneOutputTest {
    private boolean expected;
    private String userId;
    private double longCentroide;
    private double latiCentroide;
    private double dimensione;
    private TipoFigura tipoFigura;
    
    @Parameterized.Parameters
    public static Collection<Object[]> getTestParameters() {
        return Arrays.asList(new Object[][] {
            {true, AMMINISTRATORE, new Double(5.0004370), new Double(0.084881000), new Double(0.5), TipoFigura.CERCHIO},
            {true, REGISTRATO, new Double(5.0004370), new Double(0.084881000), new Double(0.5), TipoFigura.CERCHIO},
            {true, AMMINISTRATORE, new Double(5.0004370), new Double(0.084881000), new Double(0.5), TipoFigura.QUADRATO},
            {true, REGISTRATO, new Double(5.0004370), new Double(0.084881000), new Double(0.5), TipoFigura.QUADRATO},
            {false, NONREGISTRATO, new Double(5.0004370), new Double(0.084881000), new Double(0.5), TipoFigura.QUADRATO}   
        });
    }
    
    public InterfacciaRicercaFilamentiRegioneOutputTest(boolean expected, 
                String userId, double longCentroide, double latiCentroide, 
                double dimensione, TipoFigura tipoFigura) {
        this.expected = expected;
        this.userId = userId;
        this.longCentroide = longCentroide;
        this.latiCentroide = latiCentroide;
        this.dimensione = dimensione;
        this.tipoFigura = tipoFigura;
    }
    
    @Test
    public void testRicercaFilamentiRegione() {
        InterfacciaRicercaFilamentiRegione interfacciaFilamentiRegione = 
                new InterfacciaRicercaFilamentiRegione(userId);
        BeanRichiestaFilamentiRegione beanRichiesta = new
         BeanRichiestaFilamentiRegione(longCentroide, latiCentroide, 
                 dimensione, tipoFigura);
        BeanRispostaFilamenti beanRisposta = 
                interfacciaFilamentiRegione.ricercaFilamentiRegione(beanRichiesta);
        boolean res;
        if (beanRisposta.isAzioneConsentita())
            res = this.controllaFilamentiRegione(beanRisposta);
        else
            res = false;
        assertEquals("errore", res, expected);
    }
    
    private boolean internoRegione(double glon, double glat) {
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
        Iterator<Filamento> i = beanRisposta.getListaFilamenti().iterator();
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
}