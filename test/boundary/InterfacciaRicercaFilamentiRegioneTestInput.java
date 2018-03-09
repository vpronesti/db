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

/**
 * test per il requisito funzionale n. 8
 * controllare errore
 */
@RunWith(value = Parameterized.class)
public class InterfacciaRicercaFilamentiRegioneTestInput {
    private final boolean expected;
    private float longCentroide;
    private float latiCentroide;
    private float dimensione;
    private TipoFigura tipoFigura;
    
    @Parameterized.Parameters
    public static Collection<Object[]> getTestParameters() {
        return Arrays.asList(new Object[][] {
            {true, 5.0004370, 0.084881000, 0.5, TipoFigura.CERCHIO},
            // la dimensione non puo' essere negativa
            {false, 5.0004370, 0.084881000, -0.5, TipoFigura.CERCHIO}
        });
    }
    
    public InterfacciaRicercaFilamentiRegioneTestInput(boolean expected, 
                float longCentroide, float latiCentroide, float dimensione, 
                TipoFigura tipoFigura) {
        this.expected = expected;
        this.longCentroide = longCentroide;
        this.latiCentroide = latiCentroide;
        this.dimensione = dimensione;
        this.tipoFigura = tipoFigura;
    }
    
    @Test
    public void testRicercaFilamentiRegione() {
        InterfacciaRicercaFilamentiRegione interfacciaFilamentiRegione = 
                new InterfacciaRicercaFilamentiRegione("a");
        BeanRichiestaFilamentiRegione beanRichiesta = 
                new BeanRichiestaFilamentiRegione(longCentroide, 
                        latiCentroide, dimensione, tipoFigura);
        BeanRispostaFilamenti beanRisposta = interfacciaFilamentiRegione.ricercaFilamentiRegione(beanRichiesta);
        
        assertEquals("errore", beanRisposta.isInputValido(), expected);
    }
    
    
}
