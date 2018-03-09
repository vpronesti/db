package boundary;

import bean.BeanRichiestaContrastoEllitticita;
import bean.BeanRispostaContrastoEllitticita;
import control.GestoreRicercaContrastoEllitticita;

/**
 * REQ-6
 */
public class InterfacciaRicercaContrastoEllitticita {
    private GestoreRicercaContrastoEllitticita controllerFilamento;
    private String userId;
    
    public InterfacciaRicercaContrastoEllitticita(String userId) {
        this.userId = userId;
    }
    
    public boolean controllaBean(BeanRichiestaContrastoEllitticita beanRichiesta) {
        boolean res = true;
        if (beanRichiesta.getBrillanza() < 0)
            res = false;
        if (beanRichiesta.getInizioIntervalloEllitticita() <= 1)
            res = false;
        if (beanRichiesta.getInizioIntervalloEllitticita() >= 9)
            res = false;
        if (beanRichiesta.getFineIntervalloEllitticita() <= 1)
            res = false;
        if (beanRichiesta.getFineIntervalloEllitticita() >= 9)
            res = false;
        return res;
    } 
    
    public BeanRispostaContrastoEllitticita ricercaContrastoEllitticita(BeanRichiestaContrastoEllitticita beanRichiesta) {
        BeanRispostaContrastoEllitticita beanRisposta;
        if (this.controllaBean(beanRichiesta)) {
            controllerFilamento = new GestoreRicercaContrastoEllitticita(this);
            beanRisposta = controllerFilamento.ricercaContrastoEllitticita(beanRichiesta);
        } else {
            beanRisposta = new BeanRispostaContrastoEllitticita(false);
        }
        return beanRisposta;
    }
}
