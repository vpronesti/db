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
    
    private boolean controllaBean(BeanRichiestaSegmentoContorno beanRichiesta) {
        boolean res = true;
        if (beanRichiesta.getSatellite() == null || beanRichiesta.getSatellite().isEmpty())
            res = false;
        return res;
    }
    
    public BeanRispostaSegmentoContorno ricercaDistanzaSegmentoContorno(BeanRichiestaSegmentoContorno beanRichiesta) {
        if (this.controllaBean(beanRichiesta)) {
            controllerFilamento = new GestoreRicercaDistanzaSegmentoContorno(this);
            return controllerFilamento.ricercaDistanzaSegmentoContorno(beanRichiesta);
        } else {
            return new BeanRispostaSegmentoContorno(false);
        }
    }      
}
