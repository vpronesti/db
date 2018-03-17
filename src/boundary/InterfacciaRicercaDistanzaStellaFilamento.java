package boundary;

import bean.BeanIdFilamento;
import bean.BeanRispostaStellaFilamento;
import bean.BeanUtente;
import control.GestoreRicercaDistanzaStellaFilamento;
import dao.UtenteDao;
import java.sql.Connection;
import util.DBAccess;

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
        Connection conn = DBAccess.getInstance().getConnection();
        boolean azioneConsentita = UtenteDao.getInstance().queryEsistenzaUtente(conn, new BeanUtente(this.userId));
        DBAccess.getInstance().closeConnection(conn);
        if (azioneConsentita) {
            if (this.controllaBean(idFil)) {
                controllerFilamento = new GestoreRicercaDistanzaStellaFilamento(this);
                return controllerFilamento.ricercaDistanzaStellaFilamento(idFil);
            } else {
                return new BeanRispostaStellaFilamento(false, true);
            }
        } else {
            return new BeanRispostaStellaFilamento(false, false);
        }
    }
}
