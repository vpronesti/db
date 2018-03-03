package boundary;

import bean.BeanInformazioniFilamento;
import control.GestoreRecuperoInformazioniFilamento;

/**
 * REQ-5
 */
public class InterfacciaRecuperoInformazioniDerivateFilamento {
    private GestoreRecuperoInformazioniFilamento controllerFilamento;
    private String userId;
    
    public InterfacciaRecuperoInformazioniDerivateFilamento(String userId) {
        this.userId = userId;
    }
    public boolean recuperaInfoFilamento(BeanInformazioniFilamento beanFil) {
        controllerFilamento = new GestoreRecuperoInformazioniFilamento(this);
        return controllerFilamento.recuperaInfoFilamento(beanFil);
    }
}
