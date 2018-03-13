package boundary;

import bean.BeanIdFilamento;
import bean.BeanRispostaStelleFilamento;
import control.GestoreRicercaStelleFilamento;

/**
 * REQ-9
 */
public class InterfacciaRicercaStelleFilamento {
    private GestoreRicercaStelleFilamento controllerFilamento;
    private String userId;
    
    public InterfacciaRicercaStelleFilamento(String userId) {
        this.userId = userId;
    }
    
    private boolean controllaBean(BeanIdFilamento idFil) {
        boolean res = true;
        if (idFil.getSatellite() == null || idFil.getSatellite().isEmpty())
            res = false;
        return res;
    }
    
    public BeanRispostaStelleFilamento ricercaStelleFilamento(BeanIdFilamento idFil) {
        if (this.controllaBean(idFil)) {
            controllerFilamento = new GestoreRicercaStelleFilamento(this);
            return controllerFilamento.ricercaStelleFilamento(idFil);
        } else {
            return new BeanRispostaStelleFilamento(false);
        }
    }    
}
