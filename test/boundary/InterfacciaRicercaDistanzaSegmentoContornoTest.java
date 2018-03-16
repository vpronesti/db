package boundary;

import bean.BeanRichiestaSegmentoContorno;
import bean.BeanRispostaSegmentoContorno;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * test per il requisito funzionale n. 11
 */
@RunWith(value = Parameterized.class)
public class InterfacciaRicercaDistanzaSegmentoContornoTest {
    private final boolean expected;
    private int idFil;
    private String satellite;
    private int idSeg;
    
    @Parameterized.Parameters
    public static Collection<Object[]> getTestParameters() {
        return Arrays.asList(new Object[][] {
            // segmento non esistente
            {false, 45, "Herschel", 2},
            {true, 45, "Herschel", 26},
        });
    }

    public InterfacciaRicercaDistanzaSegmentoContornoTest(boolean expected, 
                int idFil, String satellite, int idSeg) {
        this.expected = expected;
        this.idFil = idFil;
        this.satellite = satellite;
        this.idSeg = idSeg;
    }
    
    @Test
    public void testRicercaDistanzaSegmentoContorno() {
        InterfacciaRicercaDistanzaSegmentoContorno interfacciaSegmentoContorno = 
                new InterfacciaRicercaDistanzaSegmentoContorno("a");
        BeanRichiestaSegmentoContorno beanRichiesta = new BeanRichiestaSegmentoContorno(idFil, satellite, idSeg);
        BeanRispostaSegmentoContorno beanRisposta = interfacciaSegmentoContorno.ricercaDistanzaSegmentoContorno(beanRichiesta);
        boolean res = beanRisposta.isSegmentoEsiste();
        assertEquals("errore", res, expected);
    }
}
