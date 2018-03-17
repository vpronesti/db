package boundary;

import bean.BeanRichiestaNumeroSegmenti;
import bean.BeanRispostaFilamenti;
import bean.BeanUtente;
import control.GestoreRicercaNumeroSegmenti;
import dao.UtenteDao;
import java.sql.Connection;
import util.DBAccess;

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
        if (inizio < 0 || fine < 0)
            res = false;
        if (inizio > fine)
            res = false;
        if (fine < inizio)
            res = false;
        if (fine - inizio <= 2)
            res = false;
        return res;
    } 
    
    public BeanRispostaFilamenti ricercaNumeroSegmenti(BeanRichiestaNumeroSegmenti beanRichiesta) {
        BeanRispostaFilamenti beanRisposta;
        Connection conn = DBAccess.getInstance().getConnection();
        boolean azioneConsentita = UtenteDao.getInstance().queryEsistenzaUtente(conn, new BeanUtente(this.userId));
        DBAccess.getInstance().closeConnection(conn);
        if (azioneConsentita) {
            if (this.controllaBean(beanRichiesta)) {
                controllerFilamento = new GestoreRicercaNumeroSegmenti(this);
                beanRisposta = controllerFilamento.ricercaNumeroSegmenti(beanRichiesta);
            } else {
                beanRisposta = new BeanRispostaFilamenti(false, true);
            }
        } else {
            beanRisposta = new BeanRispostaFilamenti(false, false);
        }
        return beanRisposta;
    }
}
