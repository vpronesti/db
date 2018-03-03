package boundary;

import bean.BeanRichiestaNumeroSegmenti;
import bean.BeanRispostaFilamenti;
import control.GestoreRicercaNumeroSegmenti;

/**
 * REQ-7
 */
public class InterfacciaRicercaNumeroSegmenti {
    private GestoreRicercaNumeroSegmenti controllerFilamento;
    private String userId;
    
    public InterfacciaRicercaNumeroSegmenti(String userId) {
        this.userId = userId;
    }
    
    public boolean controllaBean(BeanRichiestaNumeroSegmenti beanRichiesta) {
        boolean res = true;
        int inizio = beanRichiesta.getInizioIntervallo();
        int fine = beanRichiesta.getFineIntervallo();
        if (inizio > fine)
            res = false;
        if (fine < inizio)
            res = false;
        if (fine - inizio > 2)
            res = false;
        return res;
    } 
    
    public BeanRispostaFilamenti ricercaNumeroSegmenti(BeanRichiestaNumeroSegmenti beanRichiesta) {
        if (this.controllaBean(beanRichiesta)) {
            controllerFilamento = new GestoreRicercaNumeroSegmenti(this);
            return controllerFilamento.ricercaNumeroSegmenti(beanRichiesta);
        } else {
            System.out.println("Input non valido"); // aggiungere eccezione
            return null;
        }
    }
}
