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
import static util.DBAccess.AMMINISTRATORE;
import static util.DBAccess.NONREGISTRATO;
import static util.DBAccess.REGISTRATO;

/**
 * test per il requisito funzionale n. 9
 */
@RunWith(value = Parameterized.class)
public class InterfacciaRicercaStelleFilamentoTest {
    private final boolean expected;
    private String userId;
    private int id;
    private String satellite;
    
    @Parameterized.Parameters
    public static Collection<Object[]> getTestParameters() {
        return Arrays.asList(new Object[][] {
            // utente amministratore
            // filamento esistente con stelle interne
            {true, AMMINISTRATORE, 4178, "Herschel"},
            // filamento esistente senza stelle interne
            {true, AMMINISTRATORE, 45, "Herschel"},
            // filamento non esistente
            {false, AMMINISTRATORE, 46, "Herschel"},
            
            // utente registrato
            {true, REGISTRATO, 45, "Herschel"},
            // filamento non esistente
            {false, REGISTRATO, 46, "Herschel"},
            
            // utente non registrato
            {false, NONREGISTRATO, 45, "Herschel"},
            // filamento non esistente
            {false, NONREGISTRATO, 46, "Herschel"}
        });
    }
    
    public InterfacciaRicercaStelleFilamentoTest(boolean expected, 
                String userId, int id, String satellite) {
        this.expected = expected;
        this.userId = userId;
        this.id = id;
        this.satellite = satellite;
    }
    
    @Test
    public void testRicercaStelleFilamento() {
        InterfacciaRicercaStelleFilamento interfacciaStelleFilamento = 
                new InterfacciaRicercaStelleFilamento(userId);
        BeanIdFilamento beanRichiesta = new BeanIdFilamento(id, satellite);
        BeanRispostaStelleFilamento beanRisposta = interfacciaStelleFilamento.ricercaStelleFilamento(beanRichiesta);
        boolean res;
        if (beanRisposta.isAzioneConsentita())
            res = this.controllaRisposta(beanRisposta);
        else
            res = false;
        assertEquals("errore", res, expected);
    }
    
    private boolean controllaRisposta(BeanRispostaStelleFilamento beanRisp) {
        boolean res = true;
        if (beanRisp.isFilamentoEsiste()) {
            if (beanRisp.getTotaleStelleTrovate() > 0) {
                double totalePercentuale = 0;
                Set<String> tipiStella = beanRisp.getTipiStellaPercentuale().keySet();
                for (String s : tipiStella) {
                    totalePercentuale += beanRisp.getTipiStellaPercentuale().get(s);
                }
                if (totalePercentuale != 100) {
                    System.out.println("totale percentuale: " + totalePercentuale);
                    res = false;
                }
            }
        } else {
            res = false;
        }
        return res;
    }
}
