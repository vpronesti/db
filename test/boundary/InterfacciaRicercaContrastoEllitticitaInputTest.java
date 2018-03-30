package boundary;

import bean.BeanRichiestaContrastoEllitticita;
import bean.BeanRispostaContrastoEllitticita;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static util.DBAccess.AMMINISTRATORE;
import static util.DBAccess.NONREGISTRATO;
import static util.DBAccess.REGISTRATO;

/**
 * test per il requisito funzionale n. 6
 */
@RunWith(value = Parameterized.class)
public class InterfacciaRicercaContrastoEllitticitaInputTest {
    private final boolean expected;
    private String userId;
    private double brillanza;
    private double inizioIntervalloEllitticita;
    private double fineIntervalloEllitticita;
    
    @Parameterized.Parameters
    public static Collection<Object[]> getTestParameters() {
        return Arrays.asList(new Object[][] {
            // la brillanza non puo' essere negativa
            {false, REGISTRATO, -23, 2, 8},
            // i valori di ellitticita devono essere compresi tra 1 e 10 esclusi
            {false, REGISTRATO, 23, 1, 4},
            {false, REGISTRATO, 23, 3, 10},
            {true, REGISTRATO, 23, 3, 8},
            {false, REGISTRATO, -23, 1, 10},
            {true, AMMINISTRATORE, 23, 3, 8},
            {false, NONREGISTRATO, 23, 3, 8},
        });
    }
    
    public InterfacciaRicercaContrastoEllitticitaInputTest(boolean expected, 
            String userId, double brillanza, double inizioIntervalloEllitticita, 
            double fineIntervalloEllitticita) {
        this.expected = expected;
        this.userId = userId;
        this.brillanza = brillanza;
        this.inizioIntervalloEllitticita = inizioIntervalloEllitticita;
        this.fineIntervalloEllitticita = fineIntervalloEllitticita;

    }
    
    /**
     * controlla che i vincoli sui valori di brillanza 
     * ed ellitticita' siano rispettati
     */
    @Test
    public void testRicercaContrastoEllitticita() {
        InterfacciaRicercaContrastoEllitticita interfacciaContrastoEllitticita = 
                new InterfacciaRicercaContrastoEllitticita(userId);
        BeanRichiestaContrastoEllitticita beanRichiesta = new BeanRichiestaContrastoEllitticita(brillanza, inizioIntervalloEllitticita, fineIntervalloEllitticita);
        BeanRispostaContrastoEllitticita beanRisposta = interfacciaContrastoEllitticita.ricercaContrastoEllitticita(beanRichiesta);
        
        assertEquals("errore", beanRisposta.isInputValido() && beanRisposta.isAzioneConsentita(), expected);
    }
    
    
}
