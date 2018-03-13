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

/**
 * test per il requisito funzionale n. 6
 */
@RunWith(value = Parameterized.class)
public class InterfacciaRicercaContrastoEllitticitaOutputTest {
    private final boolean expected;
    private float brillanza;
    private float contrasto;
    private float inizioIntervalloEllitticita;
    private float fineIntervalloEllitticita;
    
    @Parameterized.Parameters
    public static Collection<Object[]> getTestParameters() {
        return Arrays.asList(new Object[][] {
            {true, 23, 3, 8}
        });
    }
    
    public InterfacciaRicercaContrastoEllitticitaOutputTest(boolean expected, 
            float brillanza, float inizioIntervalloEllitticita, 
            float fineIntervalloEllitticita) {
        this.expected = expected;
        this.brillanza = brillanza;
        this.contrasto = 1 + this.brillanza/100;
        this.inizioIntervalloEllitticita = inizioIntervalloEllitticita;
        this.fineIntervalloEllitticita = fineIntervalloEllitticita;

    }
    
    private boolean controllaBrillanza(BeanRispostaContrastoEllitticita beanRisposta) {
        boolean res = true;
        Iterator<Filamento> i = beanRisposta.getListaFilamenti().iterator();
        while (i.hasNext()) {
            Filamento f = i.next();
            if (f.getContrast() < this.contrasto) {
                res = false;
                break;
            }
        }
        return res;
    }
    
    @Test
    public void testRicercaBrillanza() {
        InterfacciaRicercaContrastoEllitticita interfacciaContrastoEllitticita = 
                new InterfacciaRicercaContrastoEllitticita("a");
        BeanRichiestaContrastoEllitticita beanRichiesta = new BeanRichiestaContrastoEllitticita(brillanza, inizioIntervalloEllitticita, fineIntervalloEllitticita);
        BeanRispostaContrastoEllitticita beanRisposta = interfacciaContrastoEllitticita.ricercaContrastoEllitticita(beanRichiesta);
        boolean res = this.controllaBrillanza(beanRisposta);
        assertEquals("errore", res, expected);
    }
    
    private boolean controllaEllitticita(BeanRispostaContrastoEllitticita beanRisposta) {
        boolean res = true;
        Iterator<Filamento> i = beanRisposta.getListaFilamenti().iterator();
        while (i.hasNext()) {
            Filamento f = i.next();
            float ellitticita = f.getEllipticity();
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
    
    @Test
    public void testRicercaEllitticita() {
        InterfacciaRicercaContrastoEllitticita interfacciaContrastoEllitticita = 
                new InterfacciaRicercaContrastoEllitticita("a");
        BeanRichiestaContrastoEllitticita beanRichiesta = new BeanRichiestaContrastoEllitticita(brillanza, inizioIntervalloEllitticita, fineIntervalloEllitticita);
        BeanRispostaContrastoEllitticita beanRisposta = interfacciaContrastoEllitticita.ricercaContrastoEllitticita(beanRichiesta);
        boolean res = this.controllaEllitticita(beanRisposta);
        assertEquals("errore", res, expected);
    }
    
    
}
