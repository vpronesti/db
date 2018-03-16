package boundary;

import bean.BeanIdFilamento;
import bean.BeanRispostaStelleFilamento;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * test per il requisito funzionale n. 9
 */
@RunWith(value = Parameterized.class)
public class InterfacciaRicercaStelleFilamentoTest {
    private final boolean expected;
    private int id;
    private String satellite;
    
    @Parameterized.Parameters
    public static Collection<Object[]> getTestParameters() {
        return Arrays.asList(new Object[][] {
            
            {true, 45, "Herschel"},
            // filamento non esistente
            {false, 46, "Herschel"}
        });
    }
    
    public InterfacciaRicercaStelleFilamentoTest(boolean expected, 
                int id, String satellite) {
        this.expected = expected;
        this.id = id;
        this.satellite = satellite;
    }
    
    private boolean controllaRisposta(BeanRispostaStelleFilamento beanRisp) {
        boolean res = true;
        if (beanRisp.isContornoFilamento()) {
            double totalePercentuale = 0;
            Set<String> tipiStella = beanRisp.getTipiStellaPercentuale().keySet();
            for (String s : tipiStella) {
                totalePercentuale += beanRisp.getTipiStellaPercentuale().get(s);
            }
            if (totalePercentuale != 100) {
                System.out.println("totale percentuale: " + totalePercentuale);
                res = false;
            }
        } else {
            res = false;
        }
        return res;
    } 
    
    @Test
    public void testRicercaStelleFilamento() {
        InterfacciaRicercaStelleFilamento interfacciaStelleFilamento = 
                new InterfacciaRicercaStelleFilamento("a");
        BeanIdFilamento beanRichiesta = new BeanIdFilamento(id, satellite);
        BeanRispostaStelleFilamento beanRisposta = interfacciaStelleFilamento.ricercaStelleFilamento(beanRichiesta);
        boolean res = this.controllaRisposta(beanRisposta);
        assertEquals("errore", res, expected);
    }
}
