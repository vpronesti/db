package boundary;

import bean.BeanIdFilamento;
import bean.BeanRispostaStellaFilamento;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * test per il requisito funzionale n. 12
 */
@RunWith(value = Parameterized.class)
public class InterfacciaRicercaDistanzaStellaFilamentoTest {
    private final boolean expected;
    private int id;
    private String satellite;
    
    @Parameterized.Parameters
    public static Collection<Object[]> getTestParameters() {
        return Arrays.asList(new Object[][] {
            
            {true, 45, "Herschel"},
            // filamento non esistente
            {false, 45, "Spitzer"},
        });
    }

    public InterfacciaRicercaDistanzaStellaFilamentoTest(boolean expected, int id, String satellite) {
        this.expected = expected;
        this.id = id;
        this.satellite = satellite;
    }
    
    @Test
    public void testRicercaDistanzaStellaFilamento() {
        InterfacciaRicercaDistanzaStellaFilamento interfacciaStellaFilamento = 
                new InterfacciaRicercaDistanzaStellaFilamento("a");
        BeanIdFilamento beanRichiesta = new BeanIdFilamento(id, satellite);
        BeanRispostaStellaFilamento beanRisposta = interfacciaStellaFilamento.ricercaDistanzaStellaFilamento(beanRichiesta);
        assertEquals("errore", beanRisposta.isFilamentoEsiste(), expected);
    }
}
