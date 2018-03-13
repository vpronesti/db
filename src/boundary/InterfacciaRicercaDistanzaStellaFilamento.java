package boundary;

import bean.BeanIdFilamento;
import bean.BeanRispostaStellaFilamento;
import control.GestoreRicercaDistanzaStellaFilamento;

/**
 * REQ-12
 */
public class InterfacciaRicercaDistanzaStellaFilamento {
    private GestoreRicercaDistanzaStellaFilamento controllerFilamento;
    private String userId;
    
    public InterfacciaRicercaDistanzaStellaFilamento(String userId) {
        this.userId = userId;
    }
    
    private boolean controllaBean(BeanIdFilamento idFil) {
        boolean res = true;
        if (idFil.getSatellite() == null || idFil.getSatellite().isEmpty())
            res = false;
        return res;
    }
    
    public BeanRispostaStellaFilamento ricercaDistanzaStellaFilamento(BeanIdFilamento idFil) {
        if (this.controllaBean(idFil)) {
            controllerFilamento = new GestoreRicercaDistanzaStellaFilamento(this);
            return controllerFilamento.ricercaDistanzaStellaFilamento(idFil);
        } else {
            return new BeanRispostaStellaFilamento(false);
        }
    }
}
