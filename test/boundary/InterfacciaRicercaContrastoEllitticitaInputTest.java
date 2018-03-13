package boundary;

import bean.BeanRichiestaContrastoEllitticita;
import bean.BeanRispostaContrastoEllitticita;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * test per il requisito funzionale n. 6
 */
@RunWith(value = Parameterized.class)
public class InterfacciaRicercaContrastoEllitticitaInputTest {
    private final boolean expected;
    private float brillanza;
    private float inizioIntervalloEllitticita;
    private float fineIntervalloEllitticita;
    
    @Parameterized.Parameters
    public static Collection<Object[]> getTestParameters() {
        return Arrays.asList(new Object[][] {
            // la brillanza non puo' essere negativa
            {false, -23, 2, 8},
            // i valori di ellitticita devono essere compresi tra 1 e 10 esclusi
            {false, 23, 1, 4},
            {false, 23, 3, 10},
            {true, 23, 3, 8},
            {false, -23, 1, 10}
        });
    }
    
    public InterfacciaRicercaContrastoEllitticitaInputTest(boolean expected, 
            float brillanza, float inizioIntervalloEllitticita, 
            float fineIntervalloEllitticita) {
        this.expected = expected;
        this.brillanza = brillanza;
        this.inizioIntervalloEllitticita = inizioIntervalloEllitticita;
        this.fineIntervalloEllitticita = fineIntervalloEllitticita;

    }
    
    @Test
    public void testRicercaContrastoEllitticita() {
        InterfacciaRicercaContrastoEllitticita interfacciaContrastoEllitticita = 
                new InterfacciaRicercaContrastoEllitticita("a");
        BeanRichiestaContrastoEllitticita beanRichiesta = new BeanRichiestaContrastoEllitticita(brillanza, inizioIntervalloEllitticita, fineIntervalloEllitticita);
        BeanRispostaContrastoEllitticita beanRisposta = interfacciaContrastoEllitticita.ricercaContrastoEllitticita(beanRichiesta);
        
        assertEquals("errore", beanRisposta.isInputValido(), expected);
    }
    
    
}
