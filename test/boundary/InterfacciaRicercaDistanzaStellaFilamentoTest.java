package boundary;

import bean.BeanIdFilamento;
import bean.BeanRispostaStellaFilamento;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static util.DBAccess.REGISTRATO;

/**
 * test per il requisito funzionale n. 12
 */
@RunWith(value = Parameterized.class)
public class InterfacciaRicercaDistanzaStellaFilamentoTest {
    private final boolean expected;
    private String userId;
    private int id;
    private String satellite;
    
    @Parameterized.Parameters
    public static Collection<Object[]> getTestParameters() {
        return Arrays.asList(new Object[][] {
            
            {true, REGISTRATO, 4178, "Herschel"},
            {true, REGISTRATO, 45, "Herschel"},
            // filamento non esistente
            {false, REGISTRATO, 45, "Spitzer"},
        });
    }

    public InterfacciaRicercaDistanzaStellaFilamentoTest(boolean expected, 
            String userId, int id, String satellite) {
        this.expected = expected;
        this.userId = userId;
        this.id = id;
        this.satellite = satellite;
    }
    
    @Test
    public void testRicercaDistanzaStellaFilamento() {
        InterfacciaRicercaDistanzaStellaFilamento interfacciaStellaFilamento = 
                new InterfacciaRicercaDistanzaStellaFilamento(userId);
        BeanIdFilamento beanRichiesta = new BeanIdFilamento(id, satellite);
        BeanRispostaStellaFilamento beanRisposta = interfacciaStellaFilamento.ricercaDistanzaStellaFilamento(beanRichiesta);
        boolean res;
        if (beanRisposta.isFilamentoEsiste() && beanRisposta.isAzioneConsentita()) {
            res = this.controllaRisposta(beanRisposta);
        } else {
            res = false;
        }
        assertEquals("errore", res, expected);
    }
    
    private boolean controllaRisposta(BeanRispostaStellaFilamento beanRisposta) {
        boolean res = true;
        if (beanRisposta.getListaDistanze().size() != beanRisposta.getListaStelle().size())
            res = false;
        return res;
    }
}
