package boundary;

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
    
    public BeanRispostaStellaFilamento ricercaDistanzaStellaFilamento(int idFil) {
        controllerFilamento = new GestoreRicercaDistanzaStellaFilamento(this);
        return controllerFilamento.ricercaDistanzaStellaFilamento(idFil);
    }
}
