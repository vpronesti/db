package boundary;

import bean.BeanRichiestaFilamentiRegione;
import bean.BeanRispostaFilamenti;
import entity.TipoFigura;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static util.DBAccess.REGISTRATO;

/**
 * test per il requisito funzionale n. 8
 * controllare errore
 */
@RunWith(value = Parameterized.class)
public class InterfacciaRicercaFilamentiRegioneInputTest {
    private final boolean expected;
    private String userId;
    private double longCentroide;
    private double latiCentroide;
    private double dimensione;
    private TipoFigura tipoFigura;
    
    @Parameterized.Parameters
    public static Collection<Object[]> getTestParameters() {
        return Arrays.asList(new Object[][] {
            {true, REGISTRATO, new Double(5.0004370), new Double(0.084881000), new Double(0.5), TipoFigura.CERCHIO},
            // la dimensione non puo' essere negativa
            {false, REGISTRATO, new Double(5.0004370), new Double(0.084881000), new Double(-0.5), TipoFigura.CERCHIO},
            
            {true, REGISTRATO, new Double(5.0004370), new Double(0.084881000), new Double(0.5), TipoFigura.QUADRATO},
            // la dimensione non puo' essere negativa
            {false, REGISTRATO, new Double(5.0004370), new Double(0.084881000), new Double(-0.5), TipoFigura.QUADRATO}
        });
    }
    
    public InterfacciaRicercaFilamentiRegioneInputTest(boolean expected, 
                String userId, double longCentroide, double latiCentroide, 
                double dimensione, TipoFigura tipoFigura) {
        this.expected = expected;
        this.userId = userId;
        this.longCentroide = longCentroide;
        this.latiCentroide = latiCentroide;
        this.dimensione = dimensione;
        this.tipoFigura = tipoFigura;
    }
    
    @Test
    public void testRicercaFilamentiRegione() {
        InterfacciaRicercaFilamentiRegione interfacciaFilamentiRegione = 
                new InterfacciaRicercaFilamentiRegione(userId);
        BeanRichiestaFilamentiRegione beanRichiesta = 
                new BeanRichiestaFilamentiRegione(longCentroide, 
                        latiCentroide, dimensione, tipoFigura);
        BeanRispostaFilamenti beanRisposta = interfacciaFilamentiRegione.ricercaFilamentiRegione(beanRichiesta);
        
        assertEquals("errore", beanRisposta.isInputValido() && beanRisposta.isAzioneConsentita(), expected);
    }
}
