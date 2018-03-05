package boundary;

import bean.BeanRichiestaStelleRegione;
import bean.BeanRispostaStelleRegione;
import control.GestoreRicercaStelleRegione;

/**
 * REQ-10
 */
public class InterfacciaRicercaStelleRegione {
    private GestoreRicercaStelleRegione controllerFilamento;
    private String userId;
    
    public InterfacciaRicercaStelleRegione(String userId) {
        this.userId = userId;
    }
    
    public BeanRispostaStelleRegione ricercaStelleRegione(BeanRichiestaStelleRegione beanRichiesta) {
        controllerFilamento = new GestoreRicercaStelleRegione(this);
        return controllerFilamento.ricercaStelleRegione(beanRichiesta);
    }    
}
