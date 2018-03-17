package boundary;

import bean.BeanRichiestaStelleRegione;
import bean.BeanRispostaStelleRegione;
import bean.BeanUtente;
import control.GestoreRicercaStelleRegione;
import dao.UtenteDao;
import java.sql.Connection;
import util.DBAccess;

/**
 * REQ-10
 */
public class InterfacciaRicercaStelleRegione {
    private GestoreRicercaStelleRegione controllerFilamento;
    private String userId;
    
    public InterfacciaRicercaStelleRegione(String userId) {
        this.userId = userId;
    }
    
    public BeanRispostaStelleRegione ricercaStelleRegione(BeanRichiestaStelleRegione beanRichiesta) {
        Connection conn = DBAccess.getInstance().getConnection();
        boolean azioneConsentita = UtenteDao.getInstance().queryEsistenzaUtente(conn, new BeanUtente(this.userId));
        DBAccess.getInstance().closeConnection(conn);
        if (azioneConsentita) {
            controllerFilamento = new GestoreRicercaStelleRegione(this);
            return controllerFilamento.ricercaStelleRegione(beanRichiesta);
        } else {
            return new BeanRispostaStelleRegione(false);
        }
    }    
}
