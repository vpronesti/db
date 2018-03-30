package boundary;

import bean.BeanRichiestaContrastoEllitticita;
import bean.BeanRispostaContrastoEllitticita;
import entity.Filamento;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static util.DBAccess.NONREGISTRATO;
import static util.DBAccess.REGISTRATO;

/**
 * test per il requisito funzionale n. 6
 */
@RunWith(value = Parameterized.class)
public class InterfacciaRicercaContrastoEllitticitaOutputTest {
    private final boolean expected;
    private String userId;
    private double brillanza;
    private double contrasto;
    private double inizioIntervalloEllitticita;
    private double fineIntervalloEllitticita;
    
    @Parameterized.Parameters
    public static Collection<Object[]> getTestParameters() {
        return Arrays.asList(new Object[][] {
            {true, REGISTRATO, 23, 3, 8},
            {false, NONREGISTRATO, 23, 3, 8}
        });
    }
    
    public InterfacciaRicercaContrastoEllitticitaOutputTest(boolean expected, String userId, 
            double brillanza, double inizioIntervalloEllitticita, 
            double fineIntervalloEllitticita) {
        this.expected = expected;
        this.userId = userId;
        this.brillanza = brillanza;
        this.contrasto = 1 + this.brillanza/100;
        this.inizioIntervalloEllitticita = inizioIntervalloEllitticita;
        this.fineIntervalloEllitticita = fineIntervalloEllitticita;

    }
    
    /**
     * per tutti i filamenti ottenuti dal DB controlla che questi 
     * abbiano contrasto maggiore di quello specificato
     */
    @Test
    public void testRicercaBrillanza() {
        InterfacciaRicercaContrastoEllitticita interfacciaContrastoEllitticita = 
                new InterfacciaRicercaContrastoEllitticita(userId);
        BeanRichiestaContrastoEllitticita beanRichiesta = new BeanRichiestaContrastoEllitticita(brillanza, inizioIntervalloEllitticita, fineIntervalloEllitticita);
        BeanRispostaContrastoEllitticita beanRisposta = interfacciaContrastoEllitticita.ricercaContrastoEllitticita(beanRichiesta);
        boolean res;
        if (beanRisposta.isAzioneConsentita())
            res = this.controllaBrillanza(beanRisposta);
        else
            res = false;
        assertEquals("errore", res, expected);
    }
        
    private boolean controllaBrillanza(BeanRispostaContrastoEllitticita beanRisposta) {
        boolean res = true;
        Iterator<Filamento> i = beanRisposta.getListaFilamenti().iterator();
        while (i.hasNext()) {
            Filamento f = i.next();
            if (f.getContrasto() < this.contrasto) {
                res = false;
                break;
            }
        }
        return res;
    }
    
    /**
     * per tutti i filamenti ottenuti dal DB controlla che questi 
     * abbiano ellitticita' nel range specificato
     */
    @Test
    public void testRicercaEllitticita() {
        InterfacciaRicercaContrastoEllitticita interfacciaContrastoEllitticita = 
                new InterfacciaRicercaContrastoEllitticita(userId);
        BeanRichiestaContrastoEllitticita beanRichiesta = new BeanRichiestaContrastoEllitticita(brillanza, inizioIntervalloEllitticita, fineIntervalloEllitticita);
        BeanRispostaContrastoEllitticita beanRisposta = interfacciaContrastoEllitticita.ricercaContrastoEllitticita(beanRichiesta);
        boolean res;
        if (beanRisposta.isAzioneConsentita())
            res = this.controllaEllitticita(beanRisposta);
        else
            res = false;
        assertEquals("errore", res, expected);
    }
        
    private boolean controllaEllitticita(BeanRispostaContrastoEllitticita beanRisposta) {
        boolean res = true;
        Iterator<Filamento> i = beanRisposta.getListaFilamenti().iterator();
        while (i.hasNext()) {
            Filamento f = i.next();
            double ellitticita = f.getEllitticita();
            if (ellitticita < this.inizioIntervalloEllitticita) {
                res = false;
                break;
            }
            if (ellitticita > this.fineIntervalloEllitticita) {
                res = false;
                break;
            }
        }
        return res;
    }
}
