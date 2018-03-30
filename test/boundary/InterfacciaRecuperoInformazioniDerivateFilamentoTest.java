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
import static util.DBAccess.AMMINISTRATORE;
import static util.DBAccess.NONREGISTRATO;
import static util.DBAccess.REGISTRATO;

/**
 * test per il requisito funzionale n. 5
 */
@RunWith(value = Parameterized.class)
public class InterfacciaRecuperoInformazioniDerivateFilamentoTest {
    private boolean expected;
    private String userId;
    private int id;
    private String nome;
    private String satellite;
    private boolean ricercaId;
    
    @Parameterized.Parameters
    public static Collection<Object[]> getTestParameters() {
        return Arrays.asList(new Object[][] {
            // valore atteso, tipo utente, idfil, nome filamento (ignorato) perche' la ricerca e' per id, satellite, ricerca per id
            {true, REGISTRATO, 45, "", "Herschel", true},
            // filamento non esistente
            {false, REGISTRATO, 1, "", "Herschel", true},
            // satellite non esistente
            {false, REGISTRATO, 45, "", "Herchel!!!", true},
            {true, AMMINISTRATORE, 45, "", "Herschel", true},
            {false, NONREGISTRATO, 45, "", "Herschel", true},
            
            // valore atteso, tipo utente, idfil (ignorato) perche' la ricerca e' per nome, nome filamento, satellite, ricerca per nome
            {true, REGISTRATO, 0, "HiGALFil013.9201-1.2385", "Herschel", false},
            // filamento non esistente
            {false, REGISTRATO, 0, "abc", "Herschel", false},
            // satellite non esistente
            {false, REGISTRATO, 0, "HiGALFil013.9201-1.2385", "Herchel!!!", false},
            {true, AMMINISTRATORE, 0, "HiGALFil013.9201-1.2385", "Herschel", false},
            {false, NONREGISTRATO, 0, "HiGALFil013.9201-1.2385", "Herschel", false}
        });
    }
    
    public InterfacciaRecuperoInformazioniDerivateFilamentoTest(boolean expected,
                String userId, int id, String nome, String satellite, boolean ricercaId) {
        this.expected = expected;
        this.userId = userId;
        this.id = id;
        this.nome = nome;
        this.satellite = satellite;
        this.ricercaId = ricercaId;
    }
        
    private boolean controllaFilamento(BeanInformazioniFilamento beanFil) {
        boolean res = true;
        Connection conn = DBAccess.getInstance().getConnection();
        ContornoDao contornoDao = ContornoDao.getInstance();
        // controllo per il centroide
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
        // se il centroide e' ok si fa il controllo per l'estensione
        if (res) {
            BeanInformazioniFilamento bIF = new BeanInformazioniFilamento(beanFil.getIdFil(), beanFil.getSatellite());
            contornoDao.queryEstensioneContorno(conn, bIF);
            if (bIF.getMaxGLatContorno() != beanFil.getMaxGLatContorno() || 
                    bIF.getMinGLatContorno() != beanFil.getMinGLatContorno() || 
                    bIF.getMaxGLonContorno() != beanFil.getMaxGLonContorno() || 
                    bIF.getMinGLonContorno() != beanFil.getMinGLonContorno())
                res = false;
        }
        
        DBAccess.getInstance().closeConnection(conn);
        return res;
    }
    
    @Test
    public void testRicercaFilamenti() {
        InterfacciaRecuperoInformazioniDerivateFilamento interfacciaFilamento = 
                new InterfacciaRecuperoInformazioniDerivateFilamento(userId);
        BeanInformazioniFilamento beanRichiesta;
        if (ricercaId) {
            beanRichiesta = new BeanInformazioniFilamento(id, satellite);
        } else {
            beanRichiesta = new BeanInformazioniFilamento(nome, satellite);
        }
        Connection conn = DBAccess.getInstance().getConnection();
        boolean azioneConsentita = UtenteDao.getInstance().queryEsistenzaUtente(conn, new BeanUtente(this.userId));
        DBAccess.getInstance().closeConnection(conn);
        boolean res = 
                interfacciaFilamento.recuperaInfoFilamento(beanRichiesta);
        boolean chk = this.controllaFilamento(beanRichiesta);
        assertEquals("errore", res == chk && azioneConsentita, expected);
    }
}