package boundary;

import bean.BeanRichiestaFilamentiRegione;
import bean.BeanRispostaFilamenti;
import control.GestoreRicercaFilamentiRegione;
import entity.TipoFigura;

/**
 * REQ-8
 */
public class InterfacciaRicercaFilamentiRegione {
    private GestoreRicercaFilamentiRegione controllerFilamento;
    private String userId;
    
    public InterfacciaRicercaFilamentiRegione(String userId) {
        this.userId = userId;
    }
    
    private boolean controllaBean(BeanRichiestaFilamentiRegione beanRichiesta) {
        boolean res;
        if (beanRichiesta.getDimensione() > 0) {
            res = true;
        } else {
            res = false;
        }
        return res;
    }
    
    public BeanRispostaFilamenti ricercaFilamentiRegione(BeanRichiestaFilamentiRegione beanRichiesta) {
        if (this.controllaBean(beanRichiesta)) {
            controllerFilamento = new GestoreRicercaFilamentiRegione(this);
            if (beanRichiesta.getTipoFigura() == TipoFigura.CERCHIO) {
                return controllerFilamento.ricercaFilamentiCerchio(beanRichiesta);
            } else {
                return controllerFilamento.ricercaFilamentiQuadrato(beanRichiesta);
            }            
        } else {
            return new BeanRispostaFilamenti(false);
        }
    }    
}
