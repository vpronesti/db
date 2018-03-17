package boundary;

import bean.BeanIdFilamento;
import bean.BeanRispostaStelleFilamento;
import bean.BeanUtente;
import control.GestoreRicercaStelleFilamento;
import dao.UtenteDao;
import java.sql.Connection;
import util.DBAccess;

/**
 * REQ-9
 */
public class InterfacciaRicercaStelleFilamento {
    private GestoreRicercaStelleFilamento controllerFilamento;
    private String userId;
    
    public InterfacciaRicercaStelleFilamento(String userId) {
        this.userId = userId;
    }
    
    private boolean controllaBean(BeanIdFilamento idFil) {
        boolean res = true;
        if (idFil.getSatellite() == null || idFil.getSatellite().isEmpty())
            res = false;
        return res;
    }
    
    public BeanRispostaStelleFilamento ricercaStelleFilamento(BeanIdFilamento idFil) {
        Connection conn = DBAccess.getInstance().getConnection();
        boolean azioneConsentita = UtenteDao.getInstance().queryEsistenzaUtente(conn, new BeanUtente(this.userId));
        DBAccess.getInstance().closeConnection(conn);
        if (azioneConsentita) {
            if (this.controllaBean(idFil)) {
                controllerFilamento = new GestoreRicercaStelleFilamento(this);
                return controllerFilamento.ricercaStelleFilamento(idFil);
            } else {
                return new BeanRispostaStelleFilamento(false, true);
            }
        } else {
            return new BeanRispostaStelleFilamento(false, false);
        }
    }    
}
