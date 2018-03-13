package boundary;

import bean.BeanRichiestaNumeroSegmenti;
import bean.BeanRispostaFilamenti;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * test per il requisito funzionale n. 7
 */
@RunWith(value = Parameterized.class)
public class InterfacciaRicercaNumeroSegmentiInputTest {
    private final boolean expected;
    private int inizioIntervallo;
    private int fineIntervallo;
    
    @Parameterized.Parameters
    public static Collection<Object[]> getTestParameters() {
        return Arrays.asList(new Object[][] {
            // il numero di segmenti non puo' essere negativo
            {false, -23, -2},
            {false, -23, 10},
            // l'intervallo non puo' essere definito al contrario
            {false, 10, 2},
            // l'ampiezza dell'intervallo deve essere di almeno 2
            {false, 5, 6},
            {false, 5, 7},
            {true, 5, 8}
        });
    }
    
    public InterfacciaRicercaNumeroSegmentiInputTest(boolean expected, 
                int inizioIntervallo, int fineIntervallo) {
        this.expected = expected;
        this.inizioIntervallo = inizioIntervallo;
        this.fineIntervallo = fineIntervallo;
    }
    
    @Test
    public void testRicercaNumeroSegmenti() {
        InterfacciaRicercaNumeroSegmenti interfacciaNumeroSegmenti = 
                new InterfacciaRicercaNumeroSegmenti("a");
        BeanRichiestaNumeroSegmenti beanRichiesta = new BeanRichiestaNumeroSegmenti(inizioIntervallo, fineIntervallo);
        BeanRispostaFilamenti beanRisposta = interfacciaNumeroSegmenti.ricercaNumeroSegmenti(beanRichiesta);
        
        assertEquals("errore", beanRisposta.isInputValido(), expected);
    }
    
    
}
