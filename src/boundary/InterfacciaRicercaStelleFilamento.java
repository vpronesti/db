package boundary;

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
    
    public BeanRispostaStelleFilamento ricercaStelleFilamento(int idFil) {
        controllerFilamento = new GestoreRicercaStelleFilamento(this);
        return controllerFilamento.ricercaStelleFilamento(idFil);
    }    
}
