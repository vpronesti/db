package boundary;

import bean.BeanRichiestaNumeroSegmenti;
import bean.BeanRispostaFilamenti;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static util.DBAccess.REGISTRATO;

/**
 * test per il requisito funzionale n. 7
 */
@RunWith(value = Parameterized.class)
public class InterfacciaRicercaNumeroSegmentiInputTest {
    private final boolean expected;
    private String userId;
    private int inizioIntervallo;
    private int fineIntervallo;
    
    @Parameterized.Parameters
    public static Collection<Object[]> getTestParameters() {
        return Arrays.asList(new Object[][] {
            // il numero di segmenti non puo' essere negativo
            {false, REGISTRATO, -23, -2},
            {false, REGISTRATO, -23, 10},
            // l'intervallo non puo' essere definito al contrario
            {false, REGISTRATO, 10, 2},
            // l'ampiezza dell'intervallo deve essere di almeno 2
            {false, REGISTRATO, 5, 6},
            {false, REGISTRATO, 5, 7},
            {true, REGISTRATO, 5, 8}
        });
    }
    
    public InterfacciaRicercaNumeroSegmentiInputTest(boolean expected, 
                String userId, int inizioIntervallo, int fineIntervallo) {
        this.expected = expected;
        this.userId = userId;
        this.inizioIntervallo = inizioIntervallo;
        this.fineIntervallo = fineIntervallo;
    }
    
    /**
     * controlla che il vincolo sull'ampiezza dell'intervallo sia rispettato
     */
    @Test
    public void testRicercaNumeroSegmenti() {
        InterfacciaRicercaNumeroSegmenti interfacciaNumeroSegmenti = 
                new InterfacciaRicercaNumeroSegmenti(userId);
        BeanRichiestaNumeroSegmenti beanRichiesta = new BeanRichiestaNumeroSegmenti(inizioIntervallo, fineIntervallo);
        BeanRispostaFilamenti beanRisposta = interfacciaNumeroSegmenti.ricercaNumeroSegmenti(beanRichiesta);
        
        assertEquals("errore", beanRisposta.isInputValido() && beanRisposta.isAzioneConsentita(), expected);
    }
    
    
}
