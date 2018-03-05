package boundary;

import bean.BeanRichiestaSegmentoContorno;
import bean.BeanRispostaSegmentoContorno;
import control.GestoreRicercaDistanzaSegmentoContorno;

/**
 * REQ-11
 */
public class InterfacciaRicercaDistanzaSegmentoContorno {
    private GestoreRicercaDistanzaSegmentoContorno controllerFilamento;
    private String userId;
    
    public InterfacciaRicercaDistanzaSegmentoContorno(String userId) {
        this.userId = userId;
    }
    
    public BeanRispostaSegmentoContorno ricercaDistanzaSegmentoContorno(BeanRichiestaSegmentoContorno beanRichiesta) {
        controllerFilamento = new GestoreRicercaDistanzaSegmentoContorno(this);
        return controllerFilamento.ricercaDistanzaSegmentoContorno(beanRichiesta);
    }      
}
