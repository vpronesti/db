package boundary;

import bean.BeanRichiestaStelleRegione;
import bean.BeanRispostaStelleRegione;
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
 * test per il requisito funzionale n. 10
 */
@RunWith(value = Parameterized.class)
public class InterfacciaRicercaStelleRegioneTest {
    private final boolean expected;
    private String userId;
    private double longCentr;
    private double latiCentr;
    private double latoA;
    private double latoB;
    
    @Parameterized.Parameters
    public static Collection<Object[]> getTestParameters() {
        return Arrays.asList(new Object[][] {
            // utente amministratore
            {true, AMMINISTRATORE, new Double(5.0004370), new Double(0.084881000), new Double(0.7), new Double(1.2)},
            {true, AMMINISTRATORE, new Double(5.06635), new Double(-0.043962), new Double(0.7), new Double(1.2)},
            {true, AMMINISTRATORE, new Double(9.783937), new Double(-0.374957), new Double(0.7), new Double(1.2)},
            // utente registrato
            {true, REGISTRATO, new Double(5.0004370), new Double(0.084881000), new Double(0.7), new Double(1.2)},
            // utente non esistente
            {false, NONREGISTRATO, new Double(5.0004370), new Double(0.084881000), new Double(0.7), new Double(1.2)},
        });
    }

    public InterfacciaRicercaStelleRegioneTest(boolean expected, String userId, 
            double longCentr, double latiCentr, double latoA, double latoB) {
        this.expected = expected;
        this.userId = userId;
        this.longCentr = longCentr;
        this.latiCentr = latiCentr;
        this.latoA = latoA;
        this.latoB = latoB;
    }
 
    @Test
    public void testRicercaStelleRegione() {
        InterfacciaRicercaStelleRegione interfacciaStelleRegione = 
                new InterfacciaRicercaStelleRegione(userId);
        BeanRichiestaStelleRegione beanRichiesta = new BeanRichiestaStelleRegione(longCentr, latiCentr, latoA, latoB);
        BeanRispostaStelleRegione beanRisposta = interfacciaStelleRegione.ricercaStelleRegione(beanRichiesta);
        boolean res;
        if (beanRisposta.isAzioneConsentita())
            res = this.controllaRisposta(beanRisposta);
        else
            res = false;
        assertEquals("errore", res, expected);
    }
    
    private boolean controllaRisposta(BeanRispostaStelleRegione beanRisp) {
        boolean res = true;
        if (beanRisp.getPercentualeStelleEsterne() + beanRisp.getPercentualeStelleInterne() != 100) {
            System.out.println("percentuale stelle interne: " + beanRisp.getPercentualeStelleInterne());
            System.out.println("percentuale stelle esterne: " + beanRisp.getPercentualeStelleEsterne());
            System.out.println("diff: " + (100 - beanRisp.getPercentualeStelleEsterne() + beanRisp.getPercentualeStelleInterne()));
            res = false;
        }
        /**
         * la somma delle percentuali dovrebbe essere 
         * 0 o 100 sia per stelle interne che estere
         */
        double totalePercentualeI = 0;
        Set<String> tipiStellaI = beanRisp.getTipiStellePercentualeInterne().keySet();
        for (String s : tipiStellaI) {
            totalePercentualeI += beanRisp.getTipiStellePercentualeInterne().get(s);
        }
        if (totalePercentualeI != 100 && totalePercentualeI != 0) {
            System.out.println("totale percentuale interne: " + totalePercentualeI);
            res = false;
        }
        double totalePercentualeE = 0;
        Set<String> tipiStellaE = beanRisp.getTipiStellePercentualeEsterne().keySet();
        for (String s : tipiStellaE) {
            totalePercentualeE += beanRisp.getTipiStellePercentualeEsterne().get(s);
        }
        if (totalePercentualeE != 100 && totalePercentualeE != 0) {
            System.out.println("totale percentuale esterne: " + totalePercentualeE);
            res = false;
        }
        return res;
    }
}
