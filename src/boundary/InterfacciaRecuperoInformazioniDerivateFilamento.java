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
    
    private boolean controllaBean(BeanInformazioniFilamento beanFil) {
        boolean res = true;
        if (beanFil.getSatellite() == null || beanFil.getSatellite().isEmpty())
            res = false;
        return res;
    }
    
    public boolean recuperaInfoFilamento(BeanInformazioniFilamento beanFil) {
        if (this.controllaBean(beanFil)) {
            controllerFilamento = new GestoreRecuperoInformazioniFilamento(this);
            return controllerFilamento.recuperaInfoFilamento(beanFil);
        } else {
            return false;
        }
    }
}
